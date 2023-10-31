package com.hhk.yebserver.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Alipay {
    @ApiModelProperty(value = "appId")
    @Value("${alipay.app-id}")
    private String appId;
    @ApiModelProperty(value = "支付宝密钥")
    @Value("${alipay.private-key}")
    private String privateKey;
    @ApiModelProperty(value = "支付宝公钥")
    @Value("${alipay.alipay-public-key}")
    private String alipayPublicKey;
    @ApiModelProperty(value = "服务链接")
    @Value("${alipay.server-url}")
    private String serverUrl;
    @ApiModelProperty(value = "编码")
    @Value("${alipay.charset}")
    private String charset;
    @ApiModelProperty(value = "签名类型")
    @Value("${alipay.sign-type}")
    private String signType;
    @ApiModelProperty(value = "通知链接")
    @Value("${alipay.notify-url}")
    private String notifyUrl;

}
