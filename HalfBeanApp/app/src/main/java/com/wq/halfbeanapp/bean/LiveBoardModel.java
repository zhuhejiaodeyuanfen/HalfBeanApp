package com.wq.halfbeanapp.bean;

/**
 * 照片墙model
 */
public class LiveBoardModel {
    private int liveBoardModelId;//子版块的id
    private String liveBoardTitle;//子版块的名称
    private String liveBoardContent;//子版块的内容
    private long createTime;//发布时间

    public int getLiveBoardModelId() {
        return liveBoardModelId;
    }

    public void setLiveBoardModelId(int liveBoardModelId) {
        this.liveBoardModelId = liveBoardModelId;
    }

    public String getLiveBoardTitle() {
        return liveBoardTitle;
    }

    public void setLiveBoardTitle(String liveBoardTitle) {
        this.liveBoardTitle = liveBoardTitle;
    }

    public String getLiveBoardContent() {
        return liveBoardContent;
    }

    public void setLiveBoardContent(String liveBoardContent) {
        this.liveBoardContent = liveBoardContent;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
