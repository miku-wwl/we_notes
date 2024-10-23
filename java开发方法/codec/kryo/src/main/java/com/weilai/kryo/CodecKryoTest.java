package com.weilai.kryo;

import com.alipay.remoting.exception.CodecException;
import com.weilai.kryo.codec.BodyCodecKryoImpl;
import com.weilai.kryo.codec.CommonMsg;

import java.io.UnsupportedEncodingException;

public class CodecKryoTest {

    public static void main(String[] args) throws UnsupportedEncodingException, CodecException {
        long startTime = System.currentTimeMillis(); // 记录开始时间

        CommonMsg commonMsg = new CommonMsg();
        commonMsg.setMsgDst((short) 1);
        commonMsg.setErrCode((short) 12);
        BodyCodecKryoImpl bodyCodec = new BodyCodecKryoImpl();
        byte[] serialize = bodyCodec.serialize(commonMsg);
        System.out.println(bodyCodec.deserialize(serialize, CommonMsg.class));

        long endTime = System.currentTimeMillis(); // 记录结束时间
        long duration = endTime - startTime; // 计算消耗时间（毫秒）
        System.out.println("Total time taken: " + duration + " ms");
    }
}
