package com.ztech.common.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RedisFactory {


    private String mutexDefaultExt = "_mutex";
    private long mutextDefaultExpireTime = 60l;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(
            RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    public void saveByStr(final String key, final String value) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                connection.set(
                        redisSerializer.serialize(
                                key),
                        redisSerializer.serialize(
                                value));
                return null;
            }
        });
    }


    public String getByStr(final String key) {
        return (String) (redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                byte[] keys = redisSerializer.serialize(key);
                if (connection.exists(keys)) {
                    byte[] values = connection.get(keys);
                    String retval = redisSerializer.deserialize(values);
                    return retval;
                }
                return null;
            }
        }));
    }


    public String getSetByStr(final String key, final String value) {
        return (String) (redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                byte[] keys = redisSerializer.serialize(key);
                if (connection.exists(keys)) {
                    byte[] values = redisSerializer.serialize(value);
                    byte[] results = connection.getSet(keys, values);
                    String retval = redisSerializer.deserialize(results);
                    return retval;
                }
                return null;
            }
        }));
    }


    public void saveByStr(final String key, final long timer, final String value) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                connection.set(
                        redisSerializer.serialize(
                                key),
                        redisSerializer.serialize(
                                value));
                if (timer != -1) {
                    byte[] keys = redisSerializer.serialize(key);
                    connection.expire(keys, timer);
                }
                return null;
            }
        });
    }


    public <T> void saveByObj(final String key, final T value) {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<T> redisSerializer = (RedisSerializer<T>) (redisTemplate.getValueSerializer());
                connection.set(
                        redisTemplate.getStringSerializer().serialize(key),
                        redisSerializer.serialize(value));
                return null;
            }
        });
    }


    public <T> T getByObj(final String key) {
        return (T) (redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keys)) {
                    byte[] values = connection.get(keys);
                    RedisSerializer<T> redisSerializer = (RedisSerializer<T>) (redisTemplate.getValueSerializer());
                    T retval = redisSerializer.deserialize(values);
                    return retval;
                }
                return null;
            }
        }));
    }


    public <T> T getSetByObj(final String key, final T value) {
        return (T) (redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                if (connection.exists(keys)) {
                    RedisSerializer<T> redisSerializer = (RedisSerializer<T>) (redisTemplate.getValueSerializer());
                    byte[] values = redisSerializer.serialize(value);
                    byte[] results = connection.getSet(keys, values);
                    T retval = redisSerializer.deserialize(results);
                    return retval;
                }
                return null;
            }
        }));
    }


    public <T> void saveByObj(final String key, final long timer, final T value) {
        redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<T> redisSerializer = (RedisSerializer<T>) (redisTemplate.getValueSerializer());
                connection.set(
                        redisTemplate.getStringSerializer().serialize(key),
                        redisSerializer.serialize(value));
                if (timer != -1) {
                    byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                    connection.expire(keys, timer);
                }
                return null;
            }
        });
    }


    public Long delete(final String key) {
        return (Long) (redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                return connection.del(
                        redisTemplate.getStringSerializer().serialize(
                                key));
            }
        }));
    }

    public void deleteByPrex(final String name) {
        List<String> allKeys = getAllKeyByName(name);
        allKeys.forEach((key) -> {
            delete(key);
        });
    }


    public List<String> getAllKeyByName(final String name) {
        Set<byte[]> keyBytes = (Set<byte[]>) (redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(name + "*");
                return connection.keys(keys);
            }
        }));
        if (keyBytes != null && keyBytes.size() > 0) {
            List<String> resultList = new ArrayList<String>();
            for (Iterator<byte[]> it = keyBytes.iterator(); it.hasNext(); ) {
                String keyName = new String(it.next());
                resultList.add(keyName);
            }
            return resultList;
        } else {
            return null;
        }
    }


    public boolean setNX(final String key) {
        return this.setNX(key, "1");
    }


    public boolean setNX(final String key, final String value) {
        return this.setNX(key, -1, value);
    }


    public boolean setNX(final String key, final long timer, final String value) {
        return (boolean) (redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                boolean nxFlag = connection.setNX(
                        redisTemplate.getStringSerializer().serialize(
                                key),
                        redisTemplate.getStringSerializer().serialize(
                                value));
                if (timer != -1) {
                    byte[] keys = redisTemplate.getStringSerializer().serialize(key);
                    connection.expire(keys, timer);
                }
                return nxFlag;
            }
        }));
    }


    public boolean mutexKey(String key, long validTimer, long mutexTimeout) {
        boolean mutexFlag = false;
        try {
            String newKey = key + this.mutexDefaultExt;
            if (mutexTimeout == -1) {
                //无限时等待
                while (true) {
                    long value = System.currentTimeMillis() + validTimer + 1;
                    boolean acquired = this.setNX(newKey, validTimer, String.valueOf(value));
                    if (acquired) {
                        mutexFlag = true;
                        break;
                    } else {
                        long oldValue = Long.valueOf(this.getByStr(newKey));
                        //超时
                        if (oldValue < System.currentTimeMillis()) {
                            long lastValue = Long.valueOf(getSetByStr(newKey, String.valueOf(value)));
                            if (lastValue == oldValue) {
                                // 本线程获取锁成功
                                mutexFlag = true;
                                break;
                            } else {
                                //已被其他线程抢占
                                mutexFlag = false;
                            }
                        } else {
                            mutexFlag = false;
                        }
                    }
                    Thread.sleep(300);
                }
            } else {
                //限时等待
                long curnano = System.nanoTime();
                long nanoMutexTimeout = mutexTimeout * 1000l * 1000l * 1000l;
                do {
                    long value = System.currentTimeMillis() + validTimer + 1;
                    boolean acquired = this.setNX(newKey, validTimer, String.valueOf(value));
                    if (acquired) {
                        mutexFlag = true;
                        break;
                    } else {
                        long oldValue = Long.valueOf(this.getByStr(newKey));
                        //超时
                        if (oldValue < System.currentTimeMillis()) {
                            long lastValue = Long.valueOf(getSetByStr(newKey, String.valueOf(value)));
                            if (lastValue == oldValue) {
                                // 本线程获取锁成功
                                mutexFlag = true;
                                break;
                            } else {
                                //已被其他线程抢占
                                mutexFlag = false;
                            }
                        } else {
                            mutexFlag = false;
                        }
                    }
                    Thread.sleep(300);
                } while ((System.nanoTime() - curnano) < nanoMutexTimeout);
            }
        } catch (Exception ex) {
            mutexFlag = false;
        }
        return mutexFlag;
    }


    public Long mutexRemove(String key) {
        if (key.contains(mutexDefaultExt)) {
            return this.delete(key);
        } else {
            return this.delete(key + mutexDefaultExt);
        }
    }


    public boolean updateLockAndTransactionByStr(final String[] keys, final String[] values) {
        try {
            return (boolean) (redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                    for (String key : keys) {
                        if (mutexKey(key, mutextDefaultExpireTime, mutextDefaultExpireTime)) {
                            return false;
                        }
                    }
                    for (String key : keys) {
                        connection.watch(redisSerializer.serialize(key + mutexDefaultExt));
                    }
                    connection.multi();
                    for (int i = 0; i < keys.length; i++) {
                        connection.set(redisSerializer.serialize(keys[i] + mutexDefaultExt), redisSerializer.serialize(values[i]));
                    }
                    List<Object> results = connection.exec();
                    if (results == null || results.isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }));
        } catch (Exception ex) {
            return false;
        }
    }


    public boolean updateTransactionByStr(final String[] keys, final String[] values) {
        try {
            return (boolean) (redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    RedisSerializer<String> redisSerializer = redisTemplate.getStringSerializer();
                    for (String key : keys) {
                        connection.watch(redisSerializer.serialize(key + mutexDefaultExt));
                    }
                    connection.multi();
                    for (int i = 0; i < keys.length; i++) {
                        connection.set(redisSerializer.serialize(keys[i] + mutexDefaultExt), redisSerializer.serialize(values[i]));
                    }
                    List<Object> results = connection.exec();
                    if (results == null || results.isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }));
        } catch (Exception ex) {
            return false;
        }
    }


    public boolean updateLockAndTransactionByObj(final String[] keys, final Object[] values) {
        try {
            return (boolean) (redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    for (String key : keys) {
                        if (mutexKey(key, mutextDefaultExpireTime, mutextDefaultExpireTime)) {
                            return false;
                        }
                    }
                    for (String key : keys) {
                        connection.watch(redisTemplate.getStringSerializer().serialize(key + mutexDefaultExt));
                    }
                    connection.multi();
                    for (int i = 0; i < keys.length; i++) {
                        RedisSerializer redisSerializer = (RedisSerializer) (redisTemplate.getValueSerializer());
                        connection.set(redisTemplate.getStringSerializer().serialize(keys[i] + mutexDefaultExt), redisSerializer.serialize(values[i]));
                    }
                    List<Object> results = connection.exec();
                    if (results == null || results.isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }));
        } catch (Exception ex) {
            return false;
        }
    }


    public boolean updateTransactionByObj(final String[] keys, final Object[] values) {
        try {
            return (boolean) (redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Object doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    for (String key : keys) {
                        connection.watch(redisTemplate.getStringSerializer().serialize(key + mutexDefaultExt));
                    }
                    connection.multi();
                    for (int i = 0; i < keys.length; i++) {
                        RedisSerializer redisSerializer = (RedisSerializer) (redisTemplate.getValueSerializer());
                        connection.set(redisTemplate.getStringSerializer().serialize(keys[i] + mutexDefaultExt), redisSerializer.serialize(values[i]));
                    }
                    List<Object> results = connection.exec();
                    if (results == null || results.isEmpty()) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }));
        } catch (Exception ex) {
            return false;
        }
    }


    public boolean extPushQueue(final String queueName, final String queueTitle, final long timeout) {
        boolean result = false;
        result = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(queueName);
                byte[] values = redisTemplate.getStringSerializer().serialize(queueTitle);
                if (connection.lPush(keys, values) > 0) {
                    if (timeout != -1l) {
                        connection.expire(keys, timeout);
                    }
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            }
        });
        return result;
    }


    public boolean extExpireQueue(final String queueName, final long timeout) {
        boolean result = false;
        result = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(queueName);
                if (timeout != -1l) {
                    return connection.expire(keys, timeout);
                } else {
                    return true;
                }
            }
        });
        return result;
    }


    public boolean extRemoveQueueMsg(final String queueName, final String msg) {
        boolean result = false;
        Long back = (Long) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(queueName);
                byte[] msgs = redisTemplate.getStringSerializer().serialize(msg);
                return connection.lRem(keys, 0l, msgs);
            }
        });
        if (back.longValue() == 0l) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }


    public List<String> extTraversalQueue(final String queueName) {
        List<String> result = null;
        List<byte[]> valueList = (List<byte[]>) redisTemplate.execute(new RedisCallback<List<byte[]>>() {
            @Override
            public List<byte[]> doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(queueName);
                List<byte[]> valueList = connection.lRange(keys, 0l, -1l);
                return valueList;
            }
        });
        if (valueList != null && valueList.size() > 0) {
            result = new ArrayList<String>();
            for (int i = 0; i < valueList.size(); i++) {
                byte[] valBytes = valueList.get(i);
                result.add(new String(valBytes));
            }
        }
        return result;
    }


    public boolean extTraversalQueueMsg(final String queueName, final String msg) {
        boolean result = false;
        Boolean back = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = redisTemplate.getStringSerializer().serialize(queueName);
                List<byte[]> valueList = connection.lRange(keys, 0l, -1l);
                Boolean find = Boolean.FALSE;
                if (valueList != null && valueList.size() > 0) {
                    for (int i = 0; i < valueList.size(); i++) {
                        byte[] valBytes = valueList.get(i);
                        String matchKey = new String(valBytes);
                        if (matchKey.equals(msg)) {
                            find = Boolean.TRUE;
                            break;
                        }
                    }
                }
                return find;
            }
        });
        if (!back.booleanValue()) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }


    public boolean extHMapSave(final String key, final String field, final String value) {
        boolean result = false;
        Boolean back = Boolean.FALSE;
        try {
            back = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection connection)
                        throws DataAccessException {
                    connection.hSet(
                            redisTemplate.getStringSerializer().serialize(
                                    key),
                            redisTemplate.getStringSerializer().serialize(
                                    field),
                            redisTemplate.getStringSerializer().serialize(
                                    value));
                    return Boolean.TRUE;
                }
            });
        } catch (Exception ex) {
            back = Boolean.FALSE;
        }
        if (!back.booleanValue()) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }


    public String extHMapGet(final String key, final String field) {
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] valBytes = connection.hGet(
                        redisTemplate.getStringSerializer().serialize(
                                key),
                        redisTemplate.getStringSerializer().serialize(
                                field));
                if (valBytes == null || valBytes.length == 0) {
                    return null;
                } else {
                    return new String(valBytes);
                }
            }
        });
    }
}
