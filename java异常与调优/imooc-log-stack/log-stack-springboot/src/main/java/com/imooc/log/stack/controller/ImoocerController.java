package com.imooc.log.stack.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.imooc.log.stack.service.ImoocerServiceImpl;
import com.imooc.log.stack.vo.Imoocer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
@RestController
@RequestMapping("/imoocer")
public class ImoocerController {

    private final ImoocerServiceImpl imoocerService;

    public ImoocerController(ImoocerServiceImpl imoocerService) {
        this.imoocerService = imoocerService;
    }

    /**
     * <h2>创建 Imoocer</h2>
     * */
    @PostMapping("/create")
    public Long createImoocer(@RequestBody Imoocer imoocer) throws JsonProcessingException {
        return imoocerService.createImoocer(imoocer);
    }

    /**
     * <h2>查看 Imoocer 的信息</h2>
     * */
    @GetMapping("/info")
    public List<Imoocer> imoocerInfo() throws IOException {
        return imoocerService.imoocerInfo();
    }

    /**
     * <h2>审核 Imoocer 的头像是否符合要求</h2>
     * 通过线程堆栈去分析
     * */
    @GetMapping("/audit")
    public Callable<Integer> audit() throws IOException {
        return imoocerService.callableAudit();
    }
}
