示例代码
Java


``` java 
import java.util.function.Consumer;

public class CounterExample {

    public static void main(String[] args) {
        // 创建一个 Consumer<Runnable> 的 Lambda 表达式
        Consumer<Runnable> executeMultipleTimes = runnable -> {
            for (int i = 0; i < 5; i++) {
                runnable.run();
            }
        };

        // 创建一个 Runnable 的 Lambda 表达式，在内部定义变量
        Runnable incrementAndPrint = () -> {
            int count = 0; // 在 Lambda 内部定义的变量

            // 模拟每次调用时变量的递增
            System.out.println("Lambda 被调用，当前 count 值：" + count++);
        };

        // 执行多次 Lambda
        executeMultipleTimes.accept(incrementAndPrint);
    }
}
```
代码解释
定义 executeMultipleTimes 方法：
这个方法接受一个 Runnable 参数，并在循环中多次调用该 Runnable。
这里的目的是多次执行 incrementAndPrint Lambda。
定义 incrementAndPrint 方法：
这个方法是一个 Runnable，在每次调用时都会定义一个局部变量 count 并打印它的值。
count 在每次调用时会被递增，但由于它是局部变量，每次调用时都会重新初始化为 0。
观察结果
运行这个示例，你会看到以下输出：

深色版本
Lambda 被调用，当前 count 值：0
Lambda 被调用，当前 count 值：0
Lambda 被调用，当前 count 值：0
Lambda 被调用，当前 count 值：0
Lambda 被调用，当前 count 值：0
尽管 count 在每次调用时都会递增，但由于它是局部变量，每次调用 incrementAndPrint 时都会重新初始化为 0。这说明在 Lambda 内部定义的变量在每次调用时都会被重新创建。

修改以保留状态
如果我们希望在多次调用时保留 count 的状态，我们需要将 count 定义为外部类的成员变量，或者使用其他方法来保持状态。

使用闭包保留状态
我们可以使用一个闭包来保留状态。在 Java 中，可以使用一个匿名类来实现闭包。

Java

``` java
import java.util.function.Consumer;

public class CounterExample {

    public static void main(String[] args) {
        // 使用闭包来保留状态
        Runnable incrementAndPrint = new Runnable() {
            private int count = 0; // 在闭包内部定义的变量

            @Override
            public void run() {
                // 模拟每次调用时变量的递增
                System.out.println("Lambda 被调用，当前 count 值：" + count++);
            }
        };

        // 创建一个 Consumer<Runnable> 的 Lambda 表达式
        Consumer<Runnable> executeMultipleTimes = runnable -> {
            for (int i = 0; i < 5; i++) {
                runnable.run();
            }
        };

        // 执行多次 Lambda
        executeMultipleTimes.accept(incrementAndPrint);
    }
}

```
观察结果
运行这个修改后的示例，你会看到以下输出：


Lambda 被调用，当前 count 值：0
Lambda 被调用，当前 count 值：1
Lambda 被调用，当前 count 值：2
Lambda 被调用，当前 count 值：3
Lambda 被调用，当前 count 值：4
这次 count 的值在多次调用时得到了保留，并且每次调用时都会递增。

这个例子展示了如何在 Lambda 表达式内部定义变量，并且如何通过闭包保留状态，使得变量在多次调用时能够保持其值。


``` java
package com.star.gateway.handler;

import com.star.gateway.config.GatewayConfig;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import io.vertx.core.parsetools.RecordParser;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import thirdpart.bean.CommonMsg;

@Log4j2
@Data
@RequiredArgsConstructor
public class ConnHandler implements Handler<NetSocket> {

    @NonNull
    private GatewayConfig gatewayConfig;

    //包头[ 包体长度 int + 校验和 byte + src short+ dst short + 消息类型 short + 消息状态 byte + 包编号 long ]
    private static final int PACKET_HEADER_LENGTH = 4 + 1 + 2 + 2 + 2 + 1 + 8;
    @Override
    public void handle(NetSocket netSocket) {
        MsgHandler msgHandler = new MsgHandler(gatewayConfig.getBodyCodec());
        msgHandler.onConnect(netSocket);
        final RecordParser parser = RecordParser.newFixed(PACKET_HEADER_LENGTH);
        //设置报文接收处理器
        parser.setOutput(new Handler<Buffer>() {
            int bodyLength = -1;
            byte checksum = -1;
            short msgSrc = -1;
            short msgDst = -1;
            short msgType = -1;
            byte status = -1;
            long msgNo = -1;
            @Override
            public void handle(Buffer buffer) {
                if (bodyLength == -1) {
                    //读取报头
                    bodyLength = buffer.getInt(0);  //4字节
                    checksum = buffer.getByte(4);   //1字节
                    msgSrc = buffer.getShort(5);    //2字节
                    msgDst = buffer.getShort(7);
                    msgType = buffer.getShort(9);
                    status = buffer.getByte(11);
                    msgNo = buffer.getLong(12);     //8字节
                    parser.fixedSizeMode(bodyLength);
                } else {
                    //读取数据
                    byte[] bufferBytes = buffer.getBytes();
                    CommonMsg commonMsg;
                    //检验报体的校验和是否匹配
                    if (checksum != gatewayConfig.getCheckSumImpl().getCheckSum(bufferBytes)) {
                        log.error("illegal byte body exist from client:{}", netSocket.remoteAddress());
                        return;
                    } else {
                        if (msgDst != gatewayConfig.getId()) {
                            //报文的地址和配置的地址不一致（发错地方）
                            log.error("recv error msgDst dst:{} from client:{}", msgDst, netSocket.remoteAddress());
                            return;
                        }
                        //将数据封装为CommonMsg
                        commonMsg = new CommonMsg();
                        commonMsg.setBodyLength(bodyLength);
                        commonMsg.setChecksum(checksum);
                        commonMsg.setMsgSrc(msgSrc);
                        commonMsg.setMsgDst(msgDst);
                        commonMsg.setMsgType(msgType);
                        commonMsg.setStatus(status);
                        commonMsg.setMsgNo(msgNo);
                        commonMsg.setBody(bufferBytes);
                        commonMsg.setTimestamp(System.currentTimeMillis());

                        msgHandler.onCounterData(commonMsg);

                        //复原，以读取下一个报文
                        bodyLength = -1;
                        checksum = -1;
                        msgSrc = -1;
                        msgDst = -1;
                        msgType = -1;
                        status = -1;
                        msgNo = -1;
                        parser.fixedSizeMode(PACKET_HEADER_LENGTH);
                    }
                }
            }
        });
        netSocket.handler(parser);

        //异常 退出处理器
        netSocket.closeHandler(close -> {
            msgHandler.onDisConnect(netSocket);
        });
        netSocket.exceptionHandler(e -> {
            msgHandler.onException(netSocket, e);
            netSocket.close();
        });
    }
}
```