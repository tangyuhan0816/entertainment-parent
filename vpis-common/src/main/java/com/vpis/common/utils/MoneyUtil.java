package com.vpis.common.utils;

import java.math.BigDecimal;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: MoneyUtil
 *  @package: com.vpis.common.utils
 *  @Date: Created in 2018/11/18 下午2:28
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

public class MoneyUtil {

    public static Integer Yuan2Fen(Double yuan) {
        return (new BigDecimal(String.valueOf(yuan))).movePointRight(2).intValue();
    }

    public static Double Fen2Yuan(Integer fen) {
        return (new BigDecimal(fen)).movePointLeft(2).doubleValue();
    }
}
