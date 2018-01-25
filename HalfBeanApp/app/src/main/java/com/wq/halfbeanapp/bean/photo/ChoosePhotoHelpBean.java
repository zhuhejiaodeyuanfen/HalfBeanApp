package com.wq.halfbeanapp.bean.photo;

import com.wq.halfbeanapp.bean.ChoosePhotoBean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vivianWQ on 2017/6/4
 * Mail: wangqi_vivian@sina.com
 * desc:图片旋转帮助类
 * Version: 1.0
 */
public class ChoosePhotoHelpBean implements Serializable {
    private ArrayList<ChoosePhotoBean> arrayList;

    private ArrayList<ChoosePhotoBean> selectList;

    public ArrayList<ChoosePhotoBean> getSelectList() {
        return selectList;
    }

    public void setSelectList(ArrayList<ChoosePhotoBean> selectList) {
        this.selectList = selectList;
    }

    public ArrayList<ChoosePhotoBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<ChoosePhotoBean> arrayList) {
        this.arrayList = arrayList;
    }
}