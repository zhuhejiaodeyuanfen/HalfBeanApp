package com.wq.halfbeanapp.bean;

import java.io.Serializable;

/**
 * Created by vivianWQ on 2017/5/19
 * Mail: wangqi_vivian@sina.com
 * desc: 选择相册实体类
 * Version: 1.0
 */
public class ChoosePhotoBean implements Serializable {
    private int index;//元素选中后的下标位置
    private String imgUrl;//图片地址
    private boolean isCheck;//图片是否选中
    private String thumbPath;//图片小图地址
    private int id;//图片--通过cursor查询的图片id
    private int rotation;//图片旋转角度

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getIndex() {
        return index;
    }


    public void setIndex(int index) {
        this.index = index;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
