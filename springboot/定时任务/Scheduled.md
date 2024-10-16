在 Spring 环境下，我们通常不会直接在类内部创建 Timer 实例来调度任务，而是会利用 Spring 提供的调度机制，如 @Scheduled 注解或者 TaskScheduler 接口。

既然我们已经有了 FetchTask 类，并且已经将其标记为一个 Spring 组件，我们可以使用 @Scheduled 注解来定义定时任务。这要求 run 方法必须是无参的，并且需要在类级别或方法级别添加 @Scheduled 注解来指定调度策略。

以下是修改后的 FetchTask 类：

java
深色版本
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FetchTask {

    private final SeqConfig seqConfig;

    public FetchTask(SeqConfig seqConfig) {
        this.seqConfig = seqConfig;
    }

    @Scheduled(fixedRate = 1000)
    public void run() {
        // 从这里开始的代码与原始实现相同
        if (!seqConfig.getNode().isLeader()) {
            return;
        }
        Map<String, FetchService> fetchServiceMap = seqConfig.getFetchServiceMap();
        if (MapUtils.isEmpty(fetchServiceMap)) {
            return;
        }
        List<OrderCmd> orderCmds = collectAllOrders(fetchServiceMap);
        if (CollectionUtils.isEmpty(orderCmds)) {
            return;
        }

        orderCmds.sort(((o1, o2) -> {
            int res = compareTime(o1, o2);
            if (res != 0) {
                return res;
            }
            res = comparePrice(o1, o2);
            if (res != 0) {
                return res;
            }
            res = compareVolume(o1, o2);
            return res;
        }));

        log.info(orderCmds);

        try {
            long packNo = getPackNoFromStore();
            CmdPack cmdPack = new CmdPack(packNo, orderCmds);
            byte[] serialize = seqConfig.getBodyCodec().serialize(cmdPack);
            insertToKVStore(packNo, serialize);
            updatePacketNoInStore(packNo + 1);
            seqConfig.getMulticastSender().send(
                    Buffer.buffer(serialize),
                    seqConfig.getMulticastPort(),
                    seqConfig.getMulticastIp(),
                    null
            );
        } catch (Exception e) {
            log.error("encode cmd error ", e);
        }
    }

    // 其他方法保持不变

}

如果你需要更细粒度地控制任务的调度，或者需要在非 Spring 环境下执行调度，你可以考虑使用 Spring 提供的 TaskScheduler 接口。这需要你在配置类中声明一个 TaskScheduler Bean，并在需要的地方注入使用。但是，对于大多数简单的定时任务来说，使用 @Scheduled 注解就足够了。
