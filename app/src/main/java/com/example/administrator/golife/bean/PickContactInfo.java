package com.example.administrator.golife.bean;

/**
 * @author yhy
 * @time 2017/1/3 9:57
 */
public class PickContactInfo {
    private UserInfo userInfo;
    private boolean isCheaked;

    public PickContactInfo() {
    }

    public PickContactInfo(UserInfo userInfo, boolean isCheaked) {
        this.userInfo = userInfo;
        this.isCheaked = isCheaked;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isCheaked() {
        return isCheaked;
    }

    public void setCheaked(boolean cheaked) {
        isCheaked = cheaked;
    }
}
