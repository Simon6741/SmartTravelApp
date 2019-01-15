package com.simonsmarttravel.www.smarttravelapp.Model;

public class UserProfile {
    private int Id;
    private String UserName;
    private String UserLoginId;
    private String UserPassword;

    public UserProfile() {
    }

    public UserProfile(String userName, String userLoginId, String userPassword) {
        UserName = userName;
        UserLoginId = userLoginId;
        UserPassword = userPassword;
    }

    public UserProfile(int id, String userName, String userLoginId, String userPassword) {
        Id = id;
        UserName = userName;
        UserLoginId = userLoginId;
        UserPassword = userPassword;
    }

    public int getId() {
        return Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserLoginId() {
        return UserLoginId;
    }

    public void setUserLoginId(String userLoginId) {
        UserLoginId = userLoginId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
