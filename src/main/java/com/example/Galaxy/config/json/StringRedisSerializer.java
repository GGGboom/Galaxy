package com.example.Galaxy.config.json;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

public class StringRedisSerializer implements RedisSerializer<Object> {
    private final Charset charset;

    private final String target = "\"";

    private final String replacement = "";

    public StringRedisSerializer() {
        this(Charset.forName("UTF8"));
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    @Override
    public String deserialize(byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }

    @Override
    public byte[] serialize(Object object) {
        String str = JSON.toJSONString(object);
        if (str == null) {
            return null;
        }
        str = str.replace(target, replacement);
        return str.getBytes(charset);
    }

    @Override
    public boolean canSerialize(Class<?> type) {
        return false;
    }

    @Override
    public Class<?> getTargetType() {
        return null;
    }
}
