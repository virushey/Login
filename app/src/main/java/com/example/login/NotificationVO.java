package com.example.login;



public class NotificationVO {

    public String to;
    public Notification notification=new Notification();

    public static class Notification{
        public String title;
        public String text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "NotificationVO{" +
                "to='" + to + '\'' +
                ", notification=" + notification +
                '}';
    }
}
