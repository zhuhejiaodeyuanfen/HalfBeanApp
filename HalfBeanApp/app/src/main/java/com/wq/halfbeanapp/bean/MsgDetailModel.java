package com.wq.halfbeanapp.bean;

import java.sql.Timestamp;

public class MsgDetailModel {
    private int msgId;
    private String msgFromIcon;
    private int msgType;
    private String msgContent;
    private Timestamp msgTime;
    private String msgFromName;
    private Timestamp sysTime;

    public String getMsgFromName() {
        return msgFromName;
    }

    public void setMsgFromName(String msgFromName) {
        this.msgFromName = msgFromName;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public String getMsgFromIcon() {
        return msgFromIcon;
    }

    public void setMsgFromIcon(String msgFromIcon) {
        this.msgFromIcon = msgFromIcon;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Timestamp getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Timestamp msgTime) {
        this.msgTime = msgTime;
    }
}
