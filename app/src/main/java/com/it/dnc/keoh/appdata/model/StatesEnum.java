package com.it.dnc.keoh.appdata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dnc on 29/08/18.
 */

public enum StatesEnum {

    AC(0,"AC"),
    AL(1,"AL"),
    AP(2,"AP"),
    AM(3,"AM"),
    BA(4,"BA"),
    CE(5,"CE"),
    DF(6,"DF"),
    ES(7,"ES"),
    GO(8,"GO"),
    MA(9,"MA"),
    MT(10,"MT"),
    MS(11,"MS"),
    MG(12,"MG"),
    PA(13,"PA"),
    PB(14,"PB"),
    PR(15,"PR"),
    PE(16,"PE"),
    PI(17,"PI"),
    RJ(18,"RJ"),
    RN(19,"RN"),
    RS(20,"RS"),
    RO(21,"RO"),
    RR(22,"RR"),
    SC(23,"SC"),
    SP(34,"SP"),
    SE(25,"SE"),
    TO(26,"TO");

    private int value;
    private String abbreviation;

    public static List<StatesEnum> states;


    StatesEnum(int value, String abbreviation){
        this.value = value;
        this.abbreviation = abbreviation;
    }

    static{
        states = new ArrayList<>();
        for(StatesEnum item : values()){
            states.add(item);
        }
    }

    public static StatesEnum valueOf(int id){
        for(StatesEnum state : states){
            if(state.value == id){
                return  state;
            }
        }

        return null;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
