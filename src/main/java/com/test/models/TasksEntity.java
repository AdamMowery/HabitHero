package com.test.models;

import javax.persistence.*;

/**
 * Created by adamm on 3/15/2017.
 */
@Entity
@Table(name = "tasks", schema = "Habitz", catalog = "")
@IdClass(TasksEntityPK.class)
public class friendsEntity {
    private String userId;
    private String taskId;

    @Id
    @Column(name = "userID", nullable = false, length = 20)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "taskID", nullable = false, length = 45)
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        friendsEntity that = (friendsEntity) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        return result;
    }
}
