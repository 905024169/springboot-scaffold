
package com.ztech.oauth;

import com.ztech.impl.Md5PasswordEncoderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;


/**
 * 配置授权服务器
 *
 * @date 2020-03-22
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 该对象用来支持 password 模式
    @Autowired
    AuthenticationManager authenticationManager;

    // 该对象用来将令牌信息存储到内存中
    /*@Autowired(required = false)
    TokenStore inMemoryTokenStore;*/

    //将令牌存储到redis中，不能和TokenStore同时注入，不然会出错
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    /* @Autowired
     public void setRedisConnectionFactory(RedisConnectionFactory redisConnectionFactory) {
         this.redisConnectionFactory = redisConnectionFactory;
     }*/
    // 该对象将为刷新token提供支持
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

     /*RedisConnectionFactory getRedisConnectionFactory() {
        if (redisConnectionFactory == null) {
            redisConnectionFactory = SpringContextUtil.getBean(RedisConnectionFactory.class);
        }
        return redisConnectionFactory;
    }*/

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置两个客户端，模拟第三方应用,一个用于password认证一个用于client认证
        clients.inMemory()
                .withClient("ztech")
                .authorizedGrantTypes("password", "refresh_token") //授权模式为password和refresh_token两种
                .accessTokenValiditySeconds(1800) // 配置access_token的过期时间
                .resourceIds("order") //配置资源id
                .scopes("select")
                .secret(passwordEncoder.encode("ztech"));
    }

    @Bean
    public TokenStore tokenStore() {
        TokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        return tokenStore;
    }


    @Bean
    public AuthorizationServerTokenServices tokenServices(RedisConnectionFactory redisConnectionFactory) {
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setTokenStore(tokenStore());
        tokenService.setAccessTokenValiditySeconds(60 * 24 * 365);
        return tokenService;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                //.tokenStore(inMemoryTokenStore) //配置令牌的存储（这里存放在内存中）
                .tokenStore(tokenStore())//令牌存redis
                .authenticationManager(authenticationManager)//支持 password 模式
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)//没有这项只能post方式获取令牌
        ;
    }

  /*  @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }*/

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        //允许表单认证，表示支持 client_id 和 client_secret 做登录认证
        oauthServer.allowFormAuthenticationForClients();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        // 使用BCrypt强哈希函数加密方案（密钥迭代次数默认为10）
        // return new BCryptPasswordEncoder();
        return new Md5PasswordEncoderImpl();
    }


}
