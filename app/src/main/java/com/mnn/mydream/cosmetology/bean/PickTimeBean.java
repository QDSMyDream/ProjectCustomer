package com.mnn.mydream.cosmetology.bean;

/**
 * 创建人：MyDream
 * 创建日期：2020/5/16 11:54
 * 类描述 : 时间选择bean
 */
public class PickTimeBean {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;
    private int second;

    private String timeString;

    @Override
    public String toString() {
        return "PickTimeBean{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", min=" + min +
                ", second=" + second +
                ", timeString='" + timeString + '\'' +
                '}';
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
