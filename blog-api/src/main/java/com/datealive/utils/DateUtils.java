package com.datealive.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: DateUtils
 * @Description: TODO
 * @author: datealive
 * @date: 2021/2/3  9:52
 */
@Component
public class DateUtils {
    public static Date getCurrentTime() throws ParseException {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String currentdate = sdf.format(date);
        return sdf.parse(currentdate);
    }

    public static String getToday() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    public static String getCurrentHHMMTime() {
        Date date = new Date();
        String strDateFormat = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    /**
     * Unix时间戳转换成北京时间
     *
     * @param unixTime
     * @return
     */
    public static String unixTime2BeiJingDate(int unixTime){
        String unixTimeString = String.valueOf(unixTime);
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.parseLong(unixTimeString)*1000));
    }

    /**
     * time1 - time2
     *
     * @param time1 HH:mm
     * @param time2 HH:mm
     * @return
     */
    public static int compareTime(String time1, String time2) {
        int compareResult;
        String[] time1PartArray = time1.split(":");
        int time1Hour = Integer.parseInt(time1PartArray[0]);
        int time1Minute = Integer.parseInt(time1PartArray[1]);
        String[] time2PartArray = time2.split(":");
        int time2Hour = Integer.parseInt(time2PartArray[0]);
        int time2Minute = Integer.parseInt(time2PartArray[1]);
        if (time1Hour > time2Hour) {
            compareResult = 1;
        } else if (time1Hour < time2Hour) {
            compareResult = -1;
        } else {
            if(time1Minute > time2Minute){
                compareResult = 1;
            } else if(time1Minute < time2Minute){
                compareResult = -1;
            } else {
                compareResult = 0;
            }
        }

        return compareResult;
    }
}
