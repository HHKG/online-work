package com.hhk.yebserver.controller;

import com.alipay.api.AlipayApiException;
import com.hhk.yebserver.service.AlipayService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/alipay")
public class AlipayController {
    @Autowired
    private AlipayService alipayService;

    @ApiOperation(value = "创建支付宝订单")
    @PostMapping("/createAlipayOrder")
    public String createAlipayOrder(@RequestParam String orderId, @RequestParam BigDecimal amount){
       try{
           String qrCodeUrl = alipayService.createAlipayOrder(orderId,amount);
           return qrCodeUrl;
       }catch (AlipayApiException e){
//           throw new RuntimeException(e);
           return "创建支付宝订单失败";
       }
    }
    @ApiOperation(value = "支付宝支付结果通知处理")
    @PostMapping("/notify")
    public String handleAlipayNotify(HttpServletRequest request){
        // 获取支付宝支付结果通知参数
        Map<String,String> requestParams = new HashMap<>();
        Map<String,String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()){
            requestParams.put(key, StringUtils.join(parameterMap.get(key),","));
        }
        // 处理支付宝支付结果通知
        boolean success = alipayService.handleAlipayNotify(requestParams);
        return success ? "success" : "failure";
    }

}
