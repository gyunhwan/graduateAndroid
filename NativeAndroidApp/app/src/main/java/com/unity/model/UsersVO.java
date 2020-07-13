package com.unity.model;

public class UsersVO {
    String id;
    String addictionRate;
    int count;
    int item;
    int countFriend;

    public int getCountFriend() {
        return countFriend;
    }

    public void setCountFriend(int countFriend) {
        this.countFriend = countFriend;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddictionRate() {
        return addictionRate;
    }

    public void setAddictionRate(String addictionRate) {
        this.addictionRate = addictionRate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }
}
