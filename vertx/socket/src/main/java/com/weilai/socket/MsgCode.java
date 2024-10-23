package com.weilai.socket;

import io.vertx.core.buffer.Buffer;

public class MsgCode {
    public static Buffer encodeToBuffer(CommonMsg msg) {
        return Buffer.buffer().appendInt(msg.getId());
    }

    public static CommonMsg decodeFromBuffer(Buffer buffer) {
        int id = buffer.getInt(0);

        CommonMsg commonMsg = new CommonMsg();
        commonMsg.setId(id);
        return commonMsg;
    }
}