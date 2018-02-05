package com.wq.halfbeanapp.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 首页
 */
public class HomeBoardDetailModel implements Serializable {

    private int homePostId;//帖子id
    private String postTitle;//帖子标题
    private String postParentId;//帖子所属父版块id
    private String postAdmin;//发帖者姓名
    private Timestamp postCreateTime;//发帖时间
    private String postContent;//帖子内容
    private int postGoodCount;//帖子的好评数
    private int postBadCount;//帖子的坏评数
    private int postCommentCount;//帖子的评论数
    private int rePostCount;//帖子的转发数
    private Timestamp postUpdateTime;//更新时间

    public Timestamp getPostUpdateTime() {
        return postUpdateTime;
    }

    public void setPostUpdateTime(Timestamp postUpdateTime) {
        this.postUpdateTime = postUpdateTime;
    }


    public String getPostAdminIcon() {
        return postAdminIcon;
    }

    public void setPostAdminIcon(String postAdminIcon) {
        this.postAdminIcon = postAdminIcon;
    }

    private String postAdminIcon;

    public Timestamp getSysCurrentTime() {
        return sysCurrentTime;
    }

    public void setSysCurrentTime(Timestamp sysCurrentTime) {
        this.sysCurrentTime = sysCurrentTime;
    }

    private Timestamp sysCurrentTime;//服务器当前时间

    public int getHomePostId() {
        return homePostId;
    }

    public void setHomePostId(int homePostId) {
        this.homePostId = homePostId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }


    public String getPostParentId() {
        return postParentId;
    }

    public void setPostParentId(String postParentId) {
        this.postParentId = postParentId;
    }


    public String getPostAdmin() {
        return postAdmin;
    }

    public void setPostAdmin(String postAdmin) {
        this.postAdmin = postAdmin;
    }

    public Timestamp getPostCreateTime() {
        return postCreateTime;
    }

    public void setPostCreateTime(Timestamp postCreateTime) {
        this.postCreateTime = postCreateTime;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getPostGoodCount() {
        return postGoodCount;
    }

    public void setPostGoodCount(int postGoodCount) {
        this.postGoodCount = postGoodCount;
    }

    public int getPostBadCount() {
        return postBadCount;
    }

    public void setPostBadCount(int postBadCount) {
        this.postBadCount = postBadCount;
    }

    public int getPostCommentCount() {
        return postCommentCount;
    }

    public void setPostCommentCount(int postCommentCount) {
        this.postCommentCount = postCommentCount;
    }

    public int getRePostCount() {
        return rePostCount;
    }

    public void setRePostCount(int rePostCount) {
        this.rePostCount = rePostCount;
    }
}
