package com.ztech.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 资源服务器配置
 * date 2020-03-23
 */
@Configuration
@EnableResourceServer
public class OAuth2ServerConfig extends ResourceServerConfigurerAdapter {

    /*@Autowired
    private TokenEndpoint tokenEndpoint;*/

    private static final String DEMO_RESOURCE_ID = "order";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

       /* http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated();//配置访问控制，必须认证过后才可以访问*/
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers("/upload/**").permitAll()
                .antMatchers("/image/**").permitAll()
                .antMatchers("/websocket/**").permitAll()
                .anyRequest().authenticated();//配置order访问控制，必须认证过后才可以访问

    }

   /**
     * 给allowedRequestMethods加入get方法
     * 没有该方法只能通过post请求获取token
     */
   /* @PostConstruct
    public void reconfigure() {
        Set<HttpMethod> allowedMethods = new HashSet<>(Arrays.asList(HttpMethod.GET, HttpMethod.POST));
        tokenEndpoint.setAllowedRequestMethods(allowedMethods);
    }*/

}



  /*  private static final String RESOURCE_ID = "order";

    public static class AnnotatedWith implements ResolverUtil.Test {
        public AnnotatedWith() {
        }

        @Override
        public boolean matches(Class<?> type) {
            return type != null && (type.isAnnotationPresent(Controller.class) || type.isAnnotationPresent(RestController.class));
        }

        @Override
        public String toString() {
            return "annotated with Controller.class or RestController.class";
        }
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID).stateless(true);
        }

        public List<String> getPackageClazz(String packageName) {
            List<String> urls = new ArrayList<>();
            ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<Class<?>>();
            resolverUtil.find(new OAuth2ServerConfig.AnnotatedWith(), packageName);
            Set<Class<? extends Class<?>>> typeSet = resolverUtil.getClasses();
            for (Class<?> type : typeSet) {
                if (!type.isAnonymousClass() && !type.isInterface() && !type.isMemberClass()) {
                    String[] values = null;
                    String[] methodValues = null;
                    boolean clazzAuthorize = type.isAnnotationPresent(NoAuthorize.class);
                    if (type.isAnnotationPresent(RequestMapping.class) || type.isAnnotationPresent(GetMapping.class) || type.isAnnotationPresent(PostMapping.class)) {
                        values = ((RequestMapping) type.getAnnotation(RequestMapping.class)).value();
                    }


                    for (Method method : type.getMethods()) {
                        boolean methodAuthorize = method.isAnnotationPresent(NoAuthorize.class);
                        if (clazzAuthorize || methodAuthorize) {
                            if (method.isAnnotationPresent(RequestMapping.class) || method.isAnnotationPresent(GetMapping.class) || method.isAnnotationPresent(PostMapping.class)) {

                                if (method.isAnnotationPresent(RequestMapping.class)) {
                                    methodValues = ((RequestMapping) method.getAnnotation(RequestMapping.class)).value();
                                } else if (method.isAnnotationPresent(GetMapping.class)) {
                                    methodValues = ((GetMapping) method.getAnnotation(GetMapping.class)).value();
                                } else if (method.isAnnotationPresent(PostMapping.class)) {
                                    methodValues = ((PostMapping) method.getAnnotation(PostMapping.class)).value();
                                }


                                if (values != null && methodValues != null) {
                                    for (String v : values) {
                                        String url = "";
                                        if (!v.startsWith("/")) {
                                            url = "/" + v;
                                        } else {
                                            url = v;
                                        }
                                        if (!v.endsWith("/")) {
                                            url += "/";
                                        }
                                        for (String m : methodValues) {
                                            String str = "";
                                            if (m.startsWith("/")) {
                                                str = url + m.substring(1, m.length() - 1);
                                            } else {
                                                str = url + m;
                                            }

                                            Pattern pattern = Pattern.compile("(\\{\\S+\\})");
                                            Matcher matcher = pattern.matcher(str);
                                            str = matcher.replaceAll("*");


                                            urls.add(str);

                                        }


                                    }
                                }

                            }
                        }


                    }
                }
            }

            return urls;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {


            String basePackage = PropertiesUtils.getInstance().getProperty("ztech.oauth2.basePackage", "com.ztech");
            List<String> urls = getPackageClazz(basePackage);


            if (urls.size() > 0) {
                http
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .antMatchers("/upload/**").permitAll()
                        .antMatchers("/druid/**").permitAll()
                        .antMatchers("/websocket/**").permitAll()
                        .antMatchers(urls.toArray(new String[urls.size()])).permitAll()
                        .anyRequest().authenticated();//配置order访问控制，必须认证过后才可以访问
            } else {
                http
                        .authorizeRequests()
                        .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .antMatchers("/upload/**").permitAll()
                        .antMatchers("/druid/**").permitAll()
                        .antMatchers("/websocket/**").permitAll()
                        .anyRequest().authenticated();//配置order访问控制，必须认证过后才可以访问
            }
        }
    }


    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        AuthenticationManager authenticationManager;


        @Bean
        public ClientDetailsService clientDetailsService() {
            return new ClientDetailsServiceImpl();
        }


        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(clientDetailsService());
        }




        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
            //允许表单认证
            oauthServer.allowFormAuthenticationForClients();
        }

    }*/

