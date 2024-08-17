package com.imooc.log.stack.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * <h1>自定义启动异常分析器: NullPointerException</h1>
 * */
@Slf4j
public class NullPointerFailureAnalyzer extends
        AbstractFailureAnalyzer<NullPointerException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, NullPointerException cause) {
        log.error("null error is application", cause);
        return new FailureAnalysis(
                cause.getMessage(),
                "请检查程序中的空指针问题",
                cause
        );
    }
}
