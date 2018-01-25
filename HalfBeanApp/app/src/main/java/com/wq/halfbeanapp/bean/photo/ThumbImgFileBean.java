package com.wq.halfbeanapp.bean.photo;

/**
 * ================================================
 * 作    者：轻风
 * 版    本：1.0
 * 创建日期：2017/5/10 14:46
 * 描    述：
 * Q    Q：2319118797
 * ================================================
 */
public class ThumbImgFileBean {
    private int id;//缩略图对应的大图的id
    private String thumbPath;//缩略图地址

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
