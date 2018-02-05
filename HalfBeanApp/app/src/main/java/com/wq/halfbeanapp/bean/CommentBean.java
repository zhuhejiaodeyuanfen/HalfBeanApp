package com.wq.halfbeanapp.bean;

import java.sql.Timestamp;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class CommentBean {
    private int commentId;
    private String userComment;
    private String userIcon;
    private String userName;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getSysCurrentTime() {
        return sysCurrentTime;
    }

    public void setSysCurrentTime(Timestamp sysCurrentTime) {
        this.sysCurrentTime = sysCurrentTime;
    }

    private Timestamp sysCurrentTime;

    public Timestamp getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Timestamp commentTime) {
        this.commentTime = commentTime;
    }

    private Timestamp commentTime;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
