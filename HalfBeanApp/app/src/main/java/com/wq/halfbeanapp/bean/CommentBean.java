package com.wq.halfbeanapp.bean;

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
