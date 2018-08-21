package com.it.dnc.keoh.util;

import com.it.dnc.keoh.Constants;

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


}
