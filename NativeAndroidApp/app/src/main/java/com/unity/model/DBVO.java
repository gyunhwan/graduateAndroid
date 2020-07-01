package com.unity.model;

public class DBVO {
    int count;
    TimeVO timeVO;
    UsersVO usersVO;
    int archive;
    int calendar;

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public int getCalendar() {
        return calendar;
    }

    public void setCalendar(int calendar) {
        this.calendar = calendar;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public TimeVO getTimeVO() {
        return timeVO;
    }

    public void setTimeVO(TimeVO timeVO) {
        this.timeVO = timeVO;
    }

    public UsersVO getUsersVO() {
        return usersVO;
    }

    public void setUsersVO(UsersVO usersVO) {
        this.usersVO = usersVO;
    }
}
