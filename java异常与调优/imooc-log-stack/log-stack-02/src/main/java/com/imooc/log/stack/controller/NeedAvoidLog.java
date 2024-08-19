package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

/**
 * <h1>需要规避的日志</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/avoid")
public class NeedAvoidLog {

    @PostMapping("/log")
    public Imoocer avoidLog(List<Imoocer> imoocers) {

        // 1. 避免大数据量日志的打印
        log.info("args imoocer: [{}]", imoocers); // 这种方式并不好
        if (imoocers.size() > 10) {
            log.info("args imoocer count: [{}]", imoocers.size());
        }

        // 2. 避免在循环中打日志, 特别是大循环
        imoocers.forEach(i -> log.info("imoocer name is: [{}]", i.getName()));

        // 3. 不要打没有意义的日志
        File file = new File(imoocers.get(0).getName());
        if (!file.exists()) {
            log.warn("file does not exist!");   // 没有意义
            log.warn("file does not exist: [{}]", imoocers.get(0).getName());
        }

        // 4. 如果日志什么都说明不了, 修改或删除
        double sumSalary = 0.0;
        for (Imoocer imoocer : imoocers) {
            sumSalary += imoocer.getSalary();
        }

        log.info("all imoocers sum salary is: [{}], [{}]", imoocers.size(), sumSalary);

        return null;
    }
}


这段代码示例展示了在编写日志时应避免的一些常见问题，并提出了一些调优思想。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **避免大数据量日志的打印**:
   - `log.info("args imoocer: [{}]", imoocers);` 这一行日志记录了整个 `List<Imoocer>` 的内容。
   - 如果列表很大，这可能导致日志文件变得非常庞大，影响性能。
   - 为了规避这个问题，如果列表大小超过 10，仅记录列表的大小。

2. **避免在循环中打印日志，特别是大循环**:
   - 使用 `forEach` 方法遍历 `Imoocer` 列表，并在循环中打印每个 `Imoocer` 的名字。
   - 如果列表很大，这会导致大量的日志输出，影响性能。
   - 通常情况下，应该尽量避免在循环中打印日志，特别是在循环次数较多时。

3. **不要打印没有意义的日志**:
   - 创建一个 `File` 对象，并检查该文件是否存在。
   - 如果文件不存在，则打印一条警告日志。
   - 但是，这条日志并没有提供足够的上下文信息，不清楚这个文件是什么意思或者为什么重要。
   - 改进后的日志记录了文件名，这样更有意义。

4. **如果日志什么都说明不了，修改或删除**:
   - 计算 `Imoocer` 列表中所有人的薪资总和。
   - 打印薪资总和和列表的大小。
   - 这条日志记录可能不够明确，因为它没有提供足够的信息来解释薪资总和的意义。
   - 例如，可能需要添加更多的上下文信息，比如平均薪资或薪资范围。

### 调优思想

1. **日志的合理性**:
   - 只记录对问题诊断有帮助的日志。
   - 避免记录大数据量的日志，以减少日志文件的大小和提高性能。

2. **日志的正确性**:
   - 确保日志提供足够的信息来帮助解决问题。
   - 避免打印没有意义的日志，确保每条日志都有其价值。

3. **日志的必要性**:
   - 只记录必要的信息。
   - 避免在循环中打印日志，特别是大循环。

4. **性能监控**:
   - 通过日志可以监控应用程序的性能。
   - 例如，记录 HTTP 请求的响应时间可以帮助评估网络延迟。

5. **异常定位**:
   - 通过日志可以辅助定位异常发生的位置。
   - 例如，记录异常的原因可以帮助快速找到问题所在。

6. **调试和故障排除**:
   - 日志信息对于调试多线程应用程序中的问题非常有用。
   - 通过观察日志，可以快速定位问题，并理解程序的执行流程。

7. **代码简洁性**:
   - 使用简洁的方法来记录日志，这有助于提高代码的可读性。
   - 例如，使用 `log.info` 和 `log.warn` 方法来记录不同类型的信息。

8. **代码可读性**:
   - 通过为每种情况创建明确的日志记录语句，可以提高代码的组织性和可读性。
   - 这使得其他开发者更容易理解每个日志语句的目的和作用。

9. **避免资源耗尽**:
   - 通过合理控制日志记录的内容和频率，可以避免资源耗尽的问题。
   - 如果日志记录过于频繁或记录的信息量过大，可能会导致磁盘空间迅速耗尽。

10. **避免性能瓶颈**:
    - 通过减少不必要的日志记录，可以避免成为性能瓶颈。
    - 例如，避免在循环中频繁记录日志，这可能会导致性能下降。

### 实际应用场景

在实际应用中，编写合理的日志记录的方法适用于以下场景：
- 当需要监控应用程序的性能和状态时。
- 当需要调试多线程应用程序中的问题时。
- 当需要优化应用程序的性能时。

总结来说，这段代码示例通过展示如何规避一些常见的日志问题，体现了日志的合理性、正确性、必要性、性能监控、异常定位、调试和故障排除、代码简洁性、代码可读性、避免资源耗尽以及避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。
