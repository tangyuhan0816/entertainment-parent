
package com.vpis.asset.utils;

/*
 */

import java.text.MessageFormat;
import java.util.Random;
import java.util.UUID;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: StringUtil
 *  @package: com.entertainment.asset.utils
 *  @Date: Created in 2018/10/8 下午5:14
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public class StringUtil {
    // 去除 数字0.字母O,o防止阅读歧义
    public static char[] CHAR_ARRAY = new char[]{'2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    // 去除 数字0.字母O,o防止阅读歧义
    public static char[] UPERCASE_CHAR_ARRAY = new char[]{'2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成length位长度的随机数字 generateRandomStr
     *
     * @param length
     */
    public static String generateRandomNumStr(int length) {
        char[] charArr = new char[length];
        Random rand = new Random();// 创建Random类的对象rand
        for (int i = 0; i < length; i++) {
            charArr[i] = CHAR_ARRAY[rand.nextInt(7)];
        }
        return String.valueOf(charArr);
    }

    /**
     * 生成length位长度的随机数 generateRandomStr
     *
     * @param length
     */
    public static String generateRandomStrUpperCase(int length) {
        char[] charArr = new char[length];
        Random rand = new Random();// 创建Random类的对象rand
        for (int i = 0; i < length; i++) {
            charArr[i] = UPERCASE_CHAR_ARRAY[rand.nextInt(UPERCASE_CHAR_ARRAY.length - 1)];
        }
        return String.valueOf(charArr);
    }

    /**
     * 生成length位长度的随机数 generateRandomStr
     *
     * @param length
     */
    public static String generateRandomStr(int length) {
        char[] charArr = new char[length];
        Random rand = new Random();// 创建Random类的对象rand
        for (int i = 0; i < length; i++) {
            charArr[i] = CHAR_ARRAY[rand.nextInt(CHAR_ARRAY.length - 1)];
        }
        return String.valueOf(charArr);
    }

    /**
     * 将包含{0}，{1}等占位符的String 替换对应的参数 返回替换后的字符串
     * formatParamterString
     *
     * @param paramterString
     * @param paramters
     * @return
     */
    public static String formatParamterString(String paramterString, Object[] paramters) {
        return MessageFormat.format(paramterString, paramters);
    }

    /**
     * 生成uuid
     *
     * @return
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

//	public static void main(String[] args) {
//		for(int i=0;i<100;i++){
//			System.out.println(generateRandomStrUpperCase(6));
//		}
//
//
//	}


}
