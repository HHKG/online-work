package com.hhk.yebserver.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.hhk.yebserver.pojo.Alipay;
import com.hhk.yebserver.service.AlipayService;

import java.math.BigDecimal;
import java.util.Map;

public class AlipayServiceImpl implements AlipayService {
    /**
     * 创建支付宝订单
     * @param orderId
     * @param amount
     * @return
     * @throws AlipayApiException
     */
    @Override
    public String createAlipayOrder(String orderId, BigDecimal amount) throws AlipayApiException {
        Alipay alipay = new Alipay();
        // 实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(alipay.getServerUrl(),alipay.getAppId(),alipay.getPrivateKey(),"json",alipay.getCharset(),alipay.getAlipayPublicKey(),alipay.getSignType());
        // 创建API对应的request
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        // 设置支付宝回调地址
        request.setNotifyUrl(alipay.getNotifyUrl());
        // 设置请求参数
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(orderId);
        model.setTotalAmount(amount.toString());
        model.setSubject("订单支付");

        request.setBizModel(model);
        // 发起请求，获取支付宝支付二维码链接
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            return response.getQrCode();
        }else {
            throw new AlipayApiException(response.getSubMsg());
        }
    }

    /**
     * 处理支付宝支付结果通知
     * @param requestParams
     * @return
     */

    @Override
    public Boolean handleAlipayNotify(Map<String, String> requestParams) {
        Alipay alipay = new Alipay();
        try{
            // 验签
            boolean signVerified = AlipaySignature.rsaCheckV1(requestParams,alipay.getAlipayPublicKey(),alipay.getCharset(),alipay.getSignType());
            if(signVerified){
                // 获取支付宝支付结果通知数据
                String outTradeNo = requestParams.get("out_trade_no");
                String tradeNo = requestParams.get("trade_no");
                String tradeStatus = requestParams.get("trade_status");

                // 根据支付结果更新订单状态
                if("TRADE_SUCCESS".equals(tradeStatus)||"TRADE_FINISHED".equals(tradeStatus)){
                    // 更新订单状态为已支付
                    // updateOrderStatus(outTradeNo,tradeNo,"已支付");
                    return true;
                }
            }
        }catch (AlipayApiException e){
            throw new RuntimeException(e);
        }

        return false;
    }
}
