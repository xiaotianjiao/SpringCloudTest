package com.atht.springcloud.controller;

import com.atht.springcloud.entities.CommonResult;
import com.atht.springcloud.entities.Payment;
import com.atht.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping(value = "/payment")
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;
    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("*******插入结果："+result);
        if(result>0){
            return new CommonResult(200,"插入成功,serverPort"+serverPort,result);
        }else {
            return new CommonResult(444,"插入失败",null);
        }
    }
    @GetMapping("/get/{id}")
    public CommonResult getPayMentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null){
            return new CommonResult(200,"查询成功，serverPort"+serverPort,payment);
        }else {
            return new CommonResult(444,"没有记录，serverPort"+serverPort,null);
        }
    }
    @GetMapping(value = "/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
