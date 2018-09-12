package com.it.dnc.keoh.util;

import com.it.dnc.keoh.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 21/08/18.
 */

public class CollectionUtil {

    public static String getStrList(Object... itens){
        StringBuilder strBuilder = new StringBuilder();
        for(Object item: itens){
            strBuilder.append(item).append(Constants.STR_LIST_SEPARATOR);
        }
        String s = strBuilder.toString();
        int lastSeparatorIndex = s.lastIndexOf(Constants.STR_LIST_SEPARATOR);
        return new StringBuilder(s).replace(lastSeparatorIndex, lastSeparatorIndex+1, "").toString();
    }

    public static List<Integer> getList(String strList){
        List<Integer> values = new ArrayList<>();
        String[] itens = strList.split(Constants.STR_LIST_SEPARATOR);
        for(String item : itens){
            if(!item.equals(Constants.STR_LIST_SEPARATOR) && !item.isEmpty()){
                values.add(new Integer(item));
            }
        }
        return values;
    }

    public static int getCountOnStrList(String strList){

        if(strList == null || strList.isEmpty() ){
            return 0;
        }
        int count = 0;

        strList = strList.trim();
        String[] itens = strList.split(Constants.STR_LIST_SEPARATOR);

        for(String item : itens){
            if(!item.equals(Constants.STR_LIST_SEPARATOR)){
                count++;
            }
        }
        return count;
    }


}
