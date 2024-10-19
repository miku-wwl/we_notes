``` java
import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.mqtt.MqttClient;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class MQTTClientStart {

    private static volatile MqttClient sender;

    private static String ip = "127.0.0.1";

    private static int port = 4396;

    public static void main(String[] args) {
        mqttConnect();
        publish(666);
    }

    public static Buffer encodeToBuffer(Integer msgId) {
        return Buffer.buffer()
                .appendInt(msgId);
    }


    public static void publish(Integer msgId) {
        sender.publish(msgId.toString(),   //发往的柜台id
                encodeToBuffer(msgId),
                MqttQoS.AT_LEAST_ONCE,      //总线保证至少到达一次
                false,      //不判断是否重复
                false);     //不判断是否保存
    }

    private static void mqttConnect() {
        Vertx vertx = Vertx.vertx();
        MqttClient mqttClient = MqttClient.create(vertx);
        mqttClient.connect(4396, "127.0.0.1", res -> {
            if (res.succeeded()) {
                log.info("connect to mqtt bus[ip:{}, port:{}] succeed", ip, port);
                sender = mqttClient;
            } else {
                log.info("connect to mqtt bus[ip:{}, port:{}] fail", ip, port);
                mqttConnect();
            }
        });
        mqttClient.closeHandler(h -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            mqttConnect();
        });
    }
}   
```


``` xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version> <!-- 请根据实际情况更新版本 -->
</dependency>
```