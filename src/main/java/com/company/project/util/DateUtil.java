package com.company.project.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private final static String DAY_FORMAT = "yyyy-MM-dd";
    private final static String FULL_FORMAT = "yyyy-MM-dd hh:mm:ss";

    /**
     * 当前时间
     * @param date
     * @param format
     * @return
     */
    public static String getCurrentTime(LocalDateTime date, String format){
        try{
            if(StringUtils.isNotEmpty(format)){
                DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
                return df.format(date);
            }
        }catch (Exception e){
            logger.info("时间格式错误：{}",e);
        }
        return null;
    }

    /**
     * 获取日期差
     * @param date1
     * @param date2
     * @return 秒
     */
    public static long diff(LocalDateTime date1,LocalDateTime date2){
        Duration duration = Duration.between(date1,date2);
        return duration.getSeconds();
    }

    public static void main(String[] args) {
        LocalTime time = LocalTime.MAX;
        LocalDate date = LocalDate.MAX;
        LocalDate date1 = LocalDate.of(LocalDate.now().getYear(),1,1);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
}
