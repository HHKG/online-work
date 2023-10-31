package com.hhk.yebserver.service;

import com.alipay.api.AlipayApiException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
@Service
public interface AlipayService {
    /**
     * 创建支付订单
     * @param orderId
     * @param amount
     * @return
     */
    String createAlipayOrder(String orderId, BigDecimal amount) throws AlipayApiException;

    /**
     * 处理支付宝支付结果通知
     * @param requestParams
     * @return
     */
    Boolean handleAlipayNotify(Map<String,String> requestParams);
}
