package com.xuj.lib.db.beans;

public class config {
    private String name;
    private String time;
    private String Band;
    private String Mode;
    private String APN;
    private String Destination_IP;
    private String Destination_Port;
    private String IP_Package_Size;
    private String Send_Times;
    private String Send_Interval;

    public config() {
    }

    public config(String name, String time, String band, String mode, String APN, String destination_IP, String destination_Port, String IP_Package_Size, String send_Times, String send_Interval) {
        this.name = name;
        this.time = time;
        Band = band;
        Mode = mode;
        this.APN = APN;
        Destination_IP = destination_IP;
        Destination_Port = destination_Port;
        this.IP_Package_Size = IP_Package_Size;
        Send_Times = send_Times;
        Send_Interval = send_Interval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBand() {
        return Band;
    }

    public void setBand(String band) {
        Band = band;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getAPN() {
        return APN;
    }

    public void setAPN(String APN) {
        this.APN = APN;
    }

    public String getDestination_IP() {
        return Destination_IP;
    }

    public void setDestination_IP(String destination_IP) {
        Destination_IP = destination_IP;
    }

    public String getDestination_Port() {
        return Destination_Port;
    }

    public void setDestination_Port(String destination_Port) {
        Destination_Port = destination_Port;
    }

    public String getIP_Package_Size() {
        return IP_Package_Size;
    }

    public void setIP_Package_Size(String IP_Package_Size) {
        this.IP_Package_Size = IP_Package_Size;
    }

    public String getSend_Times() {
        return Send_Times;
    }

    public void setSend_Times(String send_Times) {
        Send_Times = send_Times;
    }

    public String getSend_Interval() {
        return Send_Interval;
    }

    public void setSend_Interval(String send_Interval) {
        Send_Interval = send_Interval;
    }

    @Override
    public String toString() {
        return time + '\n' +
                "Band='" + Band + '\'' +
                ", Mode='" + Mode + '\'' +
                ", APN='" + APN + '\'' +
                ", Destination_IP='" + Destination_IP + '\'' +
                ", Destination_Port=" + Destination_Port +
                ", IP_Package_Size=" + IP_Package_Size +
                ", Send_Times=" + Send_Times +
                ", Send_Interval=" + Send_Interval;



    }




}