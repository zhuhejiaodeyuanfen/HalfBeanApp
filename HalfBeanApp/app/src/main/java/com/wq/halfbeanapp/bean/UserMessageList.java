package com.wq.halfbeanapp.bean;

import java.io.Serializable;

public class UserMessageList implements Serializable {
    private int msgListId;
    private String msgUserName;
    private String msgUserIcon;
    private int msgCount;
    private int msgUserId;

    public int getMsgUserId() {
        return msgUserId;
    }

    public void setMsgUserId(int msgUserId) {
        this.msgUserId = msgUserId;
    }

    public int getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }

    public int getMsgListId() {
        return msgListId;
    }

    public void setMsgListId(int msgListId) {
        this.msgListId = msgListId;
    }

    public String getMsgUserName() {
        return msgUserName;
    }

    public void setMsgUserName(String msgUserName) {
        this.msgUserName = msgUserName;
    }

    public String getMsgUserIcon() {
        return msgUserIcon;
    }

    public void setMsgUserIcon(String msgUserIcon) {
        this.msgUserIcon = msgUserIcon;
    }
}
