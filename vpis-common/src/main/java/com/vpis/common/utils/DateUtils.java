package com.vpis.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: DateUtils
 *  @package: com.vpis.common.utils
 *  @Date: Created in 2018/11/23 上午2:45
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Slf4j
public class DateUtils {

    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static Date defaultFormat(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMddHHmmss);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
