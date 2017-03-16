package com.test.models;

import javax.persistence.*;

/**
 * Created by adamm on 3/15/2017.
 */
@Entity
@Table(name = "usernames", schema = "Habitz", catalog = "")
public class UsernamesEntity implements Comparable {
    private String fullname;
    private String email;
    private Integer points;
    private String userId;

    @Basic
    @Column(name = "fullname", nullable = true, length = 40)
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Basic
    @Column(name = "email", nullable = true, length = 30)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "points", nullable = true)
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Id
    @Column(name = "userID", nullable = false, length = 20)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsernamesEntity that = (UsernamesEntity) o;

        if (fullname != null ? !fullname.equals(that.fullname) : that.fullname != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (points != null ? !points.equals(that.points) : that.points != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fullname != null ? fullname.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (points != null ? points.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
//compare to method is ran when the sort method called
    @Override
    public int compareTo(Object o) {
        UsernamesEntity name = (UsernamesEntity) o;

        return name.getPoints() - this.points;
    }
}
