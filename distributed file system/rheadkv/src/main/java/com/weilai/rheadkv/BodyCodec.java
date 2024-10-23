package com.weilai.rheadkv;

import com.alipay.remoting.exception.CodecException;
import com.alipay.remoting.serialization.SerializerManager;

public class BodyCodec {
    public <T> byte[] serialize(T obj) throws CodecException {
        byte[] bytes = SerializerManager.getSerializer(SerializerManager.Hessian2).serialize(obj);
        return bytes;
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws CodecException {
        return SerializerManager.getSerializer(SerializerManager.Hessian2).deserialize(bytes, clazz.getName());
    }

}