package com.vpis.common.utils;

import java.util.Calendar;
import java.util.Random;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderUtil
 *  @package: com.vpis.common.utils
 *  @Date: Created in 2018/11/8 下午4:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 订单生成工具类
 */
public class OrderUtil {

    /**
     * 为 change history , order产生id
     *
     * @return
     */
    public static String generateOrderId(String prefix) {
        Calendar calendar = Calendar.getInstance();
        return prefix + randomWords() + calendar.get(Calendar.YEAR) + (calendar.get(Calendar.MONTH) + 1) + calendar.get(Calendar.SECOND) + calendar.get(Calendar.MILLISECOND);
    }


    /**
     * randomWords
     *
     * @return
     */
    private static String randomWords() {
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] c = s.toCharArray();
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 3; i++) {
            stringBuffer.append(c[random.nextInt(c.length)]);
        }
        return stringBuffer.toString();
    }

    private static final Random RANDOM = new Random();

    public static String getRandomStr() {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < 16; ++i) {
            sb.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(RANDOM.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length())));
        }

        return sb.toString();
    }
}
