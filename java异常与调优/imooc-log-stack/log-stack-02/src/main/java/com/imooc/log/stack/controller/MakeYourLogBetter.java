package com.imooc.log.stack.controller;

import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <h1>对日志合理性、正确性、必要性的分析</h1>
 * */
@Slf4j
@RestController
@RequestMapping("/make")
public class MakeYourLogBetter {

    /** 在 SomeConf 中定义的 */
    final RestTemplate restTemplate;

    public MakeYourLogBetter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/better/log")
    public Imoocer betterLog(List<Imoocer> imoocers) {

        // 1. HTTP 请求的入参和结果
        ResponseEntity<Imoocer> entity =
                restTemplate.getForEntity("www.imooc.com",
                        Imoocer.class, imoocers.get(0));
        // 也需要酌情考虑这里的可行性，如果入参和结果都很大，那么，只记录一些 “核心” 的信息就可以了
        log.info("call imooc website with args and response: [{}], [{}]",
                imoocers.get(0), entity.getBody());

        // 2. 程序异常的原因
        int num = 0;
        try {
            num = Integer.parseInt(imoocers.get(0).getName());
        } catch (IllegalArgumentException ex) {
            log.error("parse int value error for imooc(index 0) name: [{}]",
                    imoocers.get(0).getName());
            // ...
        }

        // 3. 远程接口调用（HTTP 或 RPC）情况 -- 与第一种类似, 不再重复举例子

        // 4. 特殊的条件分支
        num = statistics(imoocers);

        return null;
    }

    private int statistics(List<Imoocer> imoocers) {

        int greaterThan20k = 0;

        for (Imoocer imoocer : imoocers) {
            if (null != imoocer.getSalary() && imoocer.getSalary() >= 20000.0) {
                ++greaterThan20k;
                if (imoocer.getSalary() > 50000.0) {
                    log.info("imoocer salary greater than 50k: [{}]", imoocer);
                }
            }
        }

        return greaterThan20k;
    }
}


这段代码示例展示了如何在 Spring Boot 应用程序中编写合理的日志记录，以提高日志的合理性、正确性和必要性。下面是对代码的详细解析以及它所体现的一些调优思想。

### 代码解析

1. **HTTP 请求的入参和结果**:
   - 使用 `RestTemplate` 发送 GET 请求到一个外部网站，并接收 `Imoocer` 类型的结果。
   - 在日志中记录了请求参数和响应结果。
   - 注意到了对日志记录的可行性考虑，即如果数据量过大，只记录“核心”的信息。

2. **程序异常的原因**:
   - 尝试将 `Imoocer` 对象的 `name` 属性转换为整数。
   - 如果转换失败，捕获异常并在日志中记录异常原因。

3. **远程接口调用**:
   - 虽然没有具体实现，但提到与第一种情况类似，即记录远程接口调用的相关信息。

4. **特殊的条件分支**:
   - 统计传入的 `Imoocer` 列表中薪资大于等于 20,000 的人数。
   - 如果薪资大于 50,000，则在日志中记录相关信息。

### 调优思想

1. **日志的合理性**:
   - 记录重要的信息，而不是所有信息。
   - 例如，在发送 HTTP 请求时，记录请求参数和响应结果，但考虑到性能影响，只记录必要的信息。

2. **日志的正确性**:
   - 记录异常的原因，帮助快速定位问题。
   - 例如，当尝试将字符串转换为整数失败时，记录具体的错误信息。

3. **日志的必要性**:
   - 只记录对问题诊断有帮助的日志。
   - 例如，在统计薪资时，只记录薪资特别高的情况，因为这可能需要特别关注。

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
   - 例如，使用 `log.info` 和 `log.error` 方法来记录不同类型的信息。

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

总结来说，这段代码示例通过展示如何编写合理的日志记录，体现了日志的合理性、正确性、必要性、性能监控、异常定位、调试和故障排除、代码简洁性、代码可读性、避免资源耗尽以及避免性能瓶颈等调优思想。这对于提高应用程序的性能和稳定性非常重要。