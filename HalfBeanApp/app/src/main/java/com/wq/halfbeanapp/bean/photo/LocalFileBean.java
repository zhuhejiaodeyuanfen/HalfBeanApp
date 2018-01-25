package com.wq.halfbeanapp.bean.photo;

/**
 * ================================================
 * 作    者：轻风
 * 版    本：1.0
 * 创建日期：2017/4/28 18:18
 * 描    述：
 * Q    Q：2319118797
 * ================================================
 */
public class LocalFileBean {
    private String originalUri;//原图URI
    private String thumbnailUri;//缩略图URI
    private int orientation;//图片旋转角度

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public String getOriginalUri() {
        return originalUri;
    }

    public void setOriginalUri(String originalUri) {
        this.originalUri = originalUri;
    }


    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int exifOrientation) {
        orientation = exifOrientation;
    }


}
