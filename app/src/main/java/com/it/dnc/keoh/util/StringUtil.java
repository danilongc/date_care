package com.it.dnc.keoh.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dnc on 22/08/18.
 */

public class StringUtil {

    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    public static String formatDate(Date date){
        return  new SimpleDateFormat(DATE_FORMAT_PATTERN).format(date);
    }

    public static boolean isEmpty(String value){
        if(value != null){
            return value.isEmpty();
        }
        return true;

    }
}
