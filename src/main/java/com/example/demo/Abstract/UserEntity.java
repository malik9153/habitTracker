package com.example.demo.Abstract;

public abstract class UserEntity {

    protected String userName;
    protected final String salt = "gFAZhExYQOyyrL+ZU5LAKg==";
    protected String uniqueId;
    protected String password;

    public abstract void register();
    public abstract void login();
}
