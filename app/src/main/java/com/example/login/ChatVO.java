package com.example.login;

public class ChatVO {
    private String content;
    private String email;
    private String wdate;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWdate() {
        return wdate;
    }

    public void setWdate(String wdate) {
        this.wdate = wdate;
    }

    @Override
    public String toString() {
        return "ChatVO{" +
                "content='" + content + '\'' +
                ", email='" + email + '\'' +
                ", wdate='" + wdate + '\'' +
                '}';
    }
}
