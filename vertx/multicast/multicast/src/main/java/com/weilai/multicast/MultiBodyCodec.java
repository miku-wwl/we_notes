package com.weilai.multicast;

import com.alipay.remoting.exception.CodecException;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayOutputStream;

public class MultiBodyCodec {
    private final Kryo kryo = createKryo();

    // 创建 Kryo 实例并注册所有需要序列化的类
    private Kryo createKryo() {
        Kryo kryo = new Kryo();
        // 注册所有需要序列化的类
        kryo.setRegistrationRequired(false);
        // kryo.register(MyClass.class);
        // 可以在这里注册其他类
        return kryo;
    }

    public <T> byte[] serialize(T obj) throws CodecException {
        try (Output output = new Output(new ByteArrayOutputStream())) {
            kryo.writeClassAndObject(output, obj);
            return output.toBytes();
        }
    }

    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws CodecException {
        try (Input input = new Input(bytes)) {
            Object obj = kryo.readClassAndObject(input);
            if (!clazz.isInstance(obj)) {
                throw new CodecException("Deserialized object is not of expected type: " + clazz.getName());
            }
            return clazz.cast(obj);
        }
    }
}