package com.keehoo.kree.budgetguru.data_models;

/**
 * Created by krzysztof on 03.11.2017.
 */

class Chronology {

    String calendarType = "iso8601";
    String id = "ISO";

    public String getCalendarType() {
        return calendarType;
    }

    public void setCalendarType(String calendarType) {
        this.calendarType = calendarType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
