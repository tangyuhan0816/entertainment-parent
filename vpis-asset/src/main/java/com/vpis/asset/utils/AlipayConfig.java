package com.vpis.asset.utils;

public class AlipayConfig {
    /**
     * 支付宝请求地址
     */
    public static final String ALIPAY_URL = "https://openapi.alipay.com/gateway.do";

    /**
     * 支付宝对应APP_ID
     */
    public static final String ALIPAY_APP_ID = "2018111162083855";

    /**
     * 开发者应用私钥,自己生成
     */
    public static final String ALIPAY_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDC7uF2NRZ67MtI" +
            "JZxXuKrgTo2L7m8GFZH+rCltHTwpAMU75gWYnraNre0R6au3S/PzVmmHXRJRI4Xc" +
            "3yEmQQ7QpRF4CYi17H3VjzwXPgOJIBOmvC3mv4qzksS905bSVEltcyvXUmjCVLmm" +
            "GjGP84NTFVWnKv5yG5kdS94D2wDIOdiMqP16PN/FePW5Im87t8oM3tEg5nUVx11P" +
            "rX/wiYJTwVO4b+3yjKzqz2MB1UYBtuO5BoZyea29Z8pqThpI2IG6VNR3OPuDDUeL" +
            "LV5EA9Umy9H77jMdhnnBJhO/LXaN5qy1Nk6SKqFZi3/drjO1lFdYqI4PZQMFf7YW" +
            "T7q9gLPtAgMBAAECggEBAIolWvk35EeXevUVWng8cjTm1p9diacJMF8IyY7/kfwJ" +
            "BL5PZJXlnMBJi2IPdNdA55EDI2SADBQelaaTJt4CaypoYTntn8o14HOxv8dE9qJ+" +
            "bg7S+8C06+oZUH6q7g7/Fs6POXmueHmIK81QcOFwlRgTXZFMAekCL/+4HY4j7aj3" +
            "QB5kzvR/ZEuvX7qn/0tOBtZ/JynF4G86K+5VDTAXJbTOxjcGxi3XYq13G57NWu3A" +
            "I3hczVd0Tp6KDg6Cqg6xvwPWJIjf1eqYB/udSWhjObIEk1Sy9j7t0NTiDI9Px/5B" +
            "wmJiYLd+WI/8RWcnfXvfmoFOfQVrkCu/6Qj6aPXUJ4ECgYEA8hQjWMr9AOVdn3He" +
            "80d4k/hXtrBF+A7RkXQ48loNYjQX+KlgQP56A3kN73MByYAo8SaWLBzXDjHkEZIu" +
            "nLqoJZX1UKsUEKAfF2nVYbR0EDdmAQja8PnNAYO2MjPLdhHnoVUrsaAVo5JmCkEx" +
            "AazNnEwUdyuqIbaQGVrXMx0oOSECgYEAziSrg3wXeDR1B6JtJh2cp1SJlePp2yil" +
            "QAdYu07n0Wn/ciTM2o+avgKTjxLkKrV+Ifi4vJljsCAn10nwjx1F9nBajVU6gNXP" +
            "+WCFAfKcxXlWQROVMMnoZje+cIxx2jh3RDDk3fb0RFKl1yGDg5pSq4tvToWKHPTT" +
            "uo7LYSKt5U0CgYEAzz0wOW6lg6PYL4zDag+wiTScmrhzXfYJsPUHX2p6dKzWLLmL" +
            "vnhcqiZzKJ+TUxaVdkUrYJ5b+Tn63pP/tYsSMgMb3TBoYAdENr4Sv69FgTB78bzL" +
            "+mpP99DzJC01LGINow8P9eNvQvYsxvCibKWr6/cxuge2gWASIrKi8o4uhiECgYBP" +
            "/FCEeyDMJmaGaNK7KGBBWP7tpvp1bAb6mh8mxo4SZnOUgYzu/SuIsI06xZS8saFY" +
            "Yx6jJ01T6JYs5eauUSh1X9Dy5/AET3IcDARv5ghIYzVBmOyZeq5vAxAksYn3p1M3" +
            "pVUYfxr2a68nkzxpUvbi0v6EVDJr299Pktp9PSyGPQKBgFB9s0mliGPivq+guCb0" +
            "4OkZAQFaxWDtVkzYDZmsvy8z4EJmGyJkB6MGL1iWNqTgZC2nBlyQF//jF5zk1vBA" +
            "zVuaM31Rilm0aNMOQ//X+3IlaOinZKla4wTR0U7vFqekPEYnIF3OEORhEg1zRg5D" +
            "yVrtruHUQ6VoFN/tmknQg4wG";

    /**
     * 参数返回格式，只支持json
     */
    public static final String ALIPAY_FORMAT = "JSON";

    /**
     * 请求和签名使用的字符编码格式，支持GBK和UTF-8
     */
    public static final String ALIPAY_CHARSET = "UTF-8";

    /**
     * 支付宝公钥，由支付宝生成
     */
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiLhLFdO+2URcvxqLzo/blSZ9aSEWfl4ZTk6drki0Nbn7nF9xCi5biD7QmRmRAfFOuvvFpwkpe3/+EE1X/qimPmw8evPtBBn9y2KeCC15hptI46eSAVCs7WUd6HYoOhXzTGCNoeosq2eMIc7CndEGcsXGYAkqv6gy0WCa5+cgAgWNjdcIb1ydtdSCx6EJyi7WOUE4WEC6zpxp9kPoEjkmV4o0c6dexTD6dUAm0Mfzj44D7KjYIJoQC7YTKgavoRYQ76VTJrKpNCZ7jznasWnFJRH3ydDfPmLLAA91vevlJJ1kLiroKC9FPb7/hdLmOVtLh8T3nNMAhXbx0hzk3jAZ+QIDAQAB";

    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    public static final String ALIPAY_SIGN_TYPE = "RSA2";
}
