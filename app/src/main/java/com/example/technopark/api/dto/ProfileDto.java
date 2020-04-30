package com.example.technopark.api.dto;

import com.example.technopark.profile.model.UserAccount;
import com.example.technopark.profile.model.UserContacts;
import com.example.technopark.profile.model.UserGroup;

import java.util.List;

public class ProfileDto {
    private long id;
    private String userName;

    private long projectId;
    private String projectName;
    private String fullName;
    private String gender;
    private String avatarUrl;

    //   Основная роль пользователя в образовательном проекте в данный момент
//	 (студент, преподаватель, администратор или другое)
    private String mainGroup;
    private String birthDate;
    private String about;
    //    Дата регистрации в образовательном проекте
    private String joinDate;
    //    Дата последнего посещения
    private String lastSeen;

    private List<UserContacts> contacts;
    private List<UserGroup> groups;
    private List<UserAccount> accounts;

    public ProfileDto(long id, String userName, long projectId, String projectName, String fullName,
                      String gender, String avatarUrl, String mainGroup, String birthDate, String about,
                      String joinDate, String lastSeen, List<UserContacts> contacts, List<UserGroup> groups,
                      List<UserAccount> accounts) {
        this.id = id;
        this.userName = userName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.fullName = fullName;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.mainGroup = mainGroup;
        this.birthDate = birthDate;
        this.about = about;
        this.joinDate = joinDate;
        this.lastSeen = lastSeen;
        this.contacts = contacts;
        this.groups = groups;
        this.accounts = accounts;
    }

    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getMainGroup() {
        return mainGroup;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getAbout() {
        return about;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public List<UserContacts> getContacts() {
        return contacts;
    }

    public List<UserGroup> getGroups() {
        return groups;
    }

    public List<UserAccount> getAccounts() {
        return accounts;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setMainGroup(String mainGroup) {
        this.mainGroup = mainGroup;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setContacts(List<UserContacts> contacts) {
        this.contacts = contacts;
    }

    public void setGroups(List<UserGroup> groups) {
        this.groups = groups;
    }

    public void setAccounts(List<UserAccount> accounts) {
        this.accounts = accounts;
    }
}
