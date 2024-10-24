package com.weilai.pubsub.pubsub;

public interface Publish {
    void pub(String channel, String message);
}
