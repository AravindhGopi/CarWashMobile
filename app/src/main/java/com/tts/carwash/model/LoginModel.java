package com.tts.carwash.model;

public class LoginModel {

    private String id;
    private String name;
    private String token;
    private Object user;
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }



    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
