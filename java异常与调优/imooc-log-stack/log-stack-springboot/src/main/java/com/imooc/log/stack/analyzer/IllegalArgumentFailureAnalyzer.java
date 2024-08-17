package com.imooc.log.stack.analyzer;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

/**
 * <h1>自定义启动异常分析器: IllegalArgumentException</h1>
 * */
public class IllegalArgumentFailureAnalyzer extends
        AbstractFailureAnalyzer<IllegalArgumentException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, IllegalArgumentException cause) {

        return new FailureAnalysis(
                cause.getMessage(),
                "你需要检查 application.yml 中定义的参数",
                cause
        );
    }
}
