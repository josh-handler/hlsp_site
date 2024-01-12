package com.hlsp.hlsp_site.model;

public class SleepEventDTO{
    private String sleepEventDate;

    private String toBedTime;

    private String fellAsleepTime;

    private String wokeUpTime;

    private String gotUpTime;

    public String getSleepEventDate() {
        return sleepEventDate;
    }

    public void setSleepEventDate(String sleepEventDate) {
        this.sleepEventDate = sleepEventDate;
    }

    public String getToBedTime() {
        return toBedTime;
    }

    public void setToBedTime(String toBedTime) {
        this.toBedTime = toBedTime;
    }

    public String getFellAsleepTime() {
        return fellAsleepTime;
    }

    public void setFellAsleepTime(String fellAsleepTime) {
        this.fellAsleepTime = fellAsleepTime;
    }

    public String getWokeUpTime() {
        return wokeUpTime;
    }

    public void setWokeUpTime(String wokeUpTime) {
        this.wokeUpTime = wokeUpTime;
    }

    public String getGotUpTime() {
        return gotUpTime;
    }

    public void setGotUpTime(String gotUpTime) {
        this.gotUpTime = gotUpTime;
    }

    public SleepEventDTO() {
    }

    public SleepEventDTO(String sleepEventDate, String toBedTime, String fellAsleepTime, String wokeUpTime,
            String gotUpTime) {
        this.sleepEventDate = sleepEventDate;
        this.toBedTime = toBedTime;
        this.fellAsleepTime = fellAsleepTime;
        this.wokeUpTime = wokeUpTime;
        this.gotUpTime = gotUpTime;
    }

    // public SleepEventDTO(Date sleepEventDate, Date toBedTime, Date fellAsleepTime, Date wokeUpTime,
    // Date gotUpTime) {
    // this.sleepEventDate = sleepEventDate.toString();
    // this.toBedTime = toBedTime.toString();
    // this.fellAsleepTime = fellAsleepTime.toString();
    // this.wokeUpTime = wokeUpTime.toString();
    // this.gotUpTime = gotUpTime.toString();
    // }
}
