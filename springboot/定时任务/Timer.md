Timer.schedule 不支持λ表达式


在 Java 中，`java.util.Timer` 类提供了一种安排任务在未来某个时刻执行的方式。`Timer` 类通常与 `TimerTask` 类一起使用，后者是一个抽象类，用户需要继承它并实现其中的 `run()` 方法来定义任务的具体行为。

下面是一个简单的例子，演示如何使用 `Timer` 和 `TimerTask` 安排一个任务，在指定的延迟后执行：

```java
import java.util.Timer;
import java.util.TimerTask;

public class TimerExample {

    public static void main(String[] args) {
        // 创建一个定时器实例
        Timer timer = new Timer();

        // 定义一个任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello, Timer!");
            }
        };

        // 安排任务在未来5秒后执行
        timer.schedule(task, 5000); // 5000毫秒 = 5秒
    }
}
```

在这个例子中，我们创建了一个 `Timer` 实例，并定义了一个 `TimerTask`，该任务在 `run()` 方法中简单地打印一条消息。然后，我们使用 `timer.schedule(task, delay)` 方法安排了这个任务在五秒后执行。

如果你想要重复执行某个任务，可以使用 `TimerTask` 的周期性调度功能。下面的例子展示了如何设置一个每隔两秒钟执行一次的任务：

```java
import java.util.Timer;
import java.util.TimerTask;

public class PeriodicTimerExample {

    public static void main(String[] args) {
        // 创建定时器实例
        Timer timer = new Timer();

        // 定义任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello, Timer! Current time is: " + System.currentTimeMillis());
            }
        };

        // 安排任务首次延迟5秒后执行，之后每隔2秒执行一次
        timer.scheduleAtFixedRate(task, 5000, 2000); // 第一个参数为首次延迟时间，第二个参数为周期时间
    }
}
```

在这个例子中，我们使用了 `timer.scheduleAtFixedRate()` 方法来安排任务。这个方法接受两个额外的参数：一个是首次执行前的延迟时间（以毫秒为单位），另一个是后续执行之间的固定周期（也以毫秒为单位）。这样任务就会在首次延迟后开始执行，并且之后会按照给定的周期重复执行。
