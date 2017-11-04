package com.keehoo.kree.budgetguru.data_models;

import java.util.Objects;

/**
 * Created by krzysztof on 03.11.2017.
 */

public class DateOfCost {

    private long year;
    String  month;
    private String era;
    int dayOfYear;
    String dayOfWeek;
    boolean leapYear;
    int dayOfMonth;
    int monthValue;
    Chronology chronology;

    public DateOfCost(long year, int dayOfMonth, int monthValue) {
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.monthValue = monthValue;

    }

    public DateOfCost(long year, String month, String era, int dayOfYear, String dayOfWeek, boolean leapYear, int dayOfMonth, int monthValue, Chronology chronology) {
        this.year = year;
        this.month = month;
        this.era = era;
        this.dayOfYear = dayOfYear;
        this.dayOfWeek = dayOfWeek;
        this.leapYear = leapYear;
        this.dayOfMonth = dayOfMonth;
        this.monthValue = monthValue;
        this.chronology = chronology;
    }

    public DateOfCost() {
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }

    public int getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(int dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public boolean isLeapYear() {
        return leapYear;
    }

    public void setLeapYear(boolean leapYear) {
        this.leapYear = leapYear;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getMonthValue() {
        return monthValue;
    }

    public void setMonthValue(int monthValue) {
        this.monthValue = monthValue;
    }

    public Chronology getChronology() {
        return chronology;
    }

    public void setChronology(Chronology chronology) {
        this.chronology = chronology;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateOfCost that = (DateOfCost) o;
        return year == that.year &&
                month == that.month &&
                dayOfYear == that.dayOfYear &&
                leapYear == that.leapYear &&
                dayOfMonth == that.dayOfMonth &&
                monthValue == that.monthValue &&
                Objects.equals(era, that.era) &&
                Objects.equals(dayOfWeek, that.dayOfWeek) &&
                Objects.equals(chronology, that.chronology);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, month, era, dayOfYear, dayOfWeek, leapYear, dayOfMonth, monthValue, chronology);
    }

    @Override
    public String toString() {
        return "DateOfCost{" +
                "year=" + year +
                ", month=" + month +
                ", era='" + era + '\'' +
                ", dayOfYear=" + dayOfYear +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", leapYear=" + leapYear +
                ", dayOfMonth=" + dayOfMonth +
                ", monthValue=" + monthValue +
                ", chronology=" + chronology +
                '}';
    }

    /*    "dateOfCost": {
        "year": 2017,
                "month": "NOVEMBER",
                "era": "CE",
                "dayOfYear": 307,
                "dayOfWeek": "FRIDAY",
                "leapYear": false,
                "dayOfMonth": 3,
                "monthValue": 11,
                "chronology": {
            "calendarType": "iso8601",
                    "id": "ISO"
        }
    }*/
}
