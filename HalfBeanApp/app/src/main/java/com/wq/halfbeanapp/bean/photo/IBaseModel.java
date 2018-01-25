package com.wq.halfbeanapp.bean.photo;

/**
 * 通用的Model接口回调
 * Created by yangqin on 2017/3/2.
 */

public interface IBaseModel<T> {
    //成功
    void succeed(T bean);
    //失败
    void error(String error);
}
