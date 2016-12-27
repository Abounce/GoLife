package com.example.administrator.golife.bean;

/**
 * Created by yhy on 2016/12/27.
 */
// 群信息的bean类
public class GroupInfo {
    private  String groupName;
    private  String groupId;
    private  String InvatePerson;


    public GroupInfo() {

    }

    public GroupInfo(String groupName, String groupId, String invatePerson) {
        this.groupName = groupName;
        this.groupId = groupId;
        InvatePerson = invatePerson;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getInvatePerson() {
        return InvatePerson;
    }

    public void setInvatePerson(String invatePerson) {
        InvatePerson = invatePerson;
    }
}
