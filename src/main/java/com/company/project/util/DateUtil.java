package com.company.project.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    private final static String DAY_FORMAT = "yyyy-MM-dd";
    private final static String FULL_FORMAT = "yyyy-MM-dd hh:mm:ss";

    public static LocalDate getNow(){
        return LocalDate.now();
    }

    public static String getCurrentTime(){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(DAY_FORMAT);
        LocalTime localTime = LocalTime.now();
        return df.format(localTime);
    }

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

    public static void main(String[] args) {
        System.out.println(getCurrentTime(LocalDateTime.now(),"hh"));
    }
}
