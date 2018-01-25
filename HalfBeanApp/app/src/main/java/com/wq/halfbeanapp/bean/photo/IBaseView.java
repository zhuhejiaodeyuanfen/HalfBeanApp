package com.wq.halfbeanapp.bean.photo;

/**
 * 通用IView回调接口
 * Created by yangqin on 2017/3/2.
 */

public interface IBaseView<T> {
    //成功
    void succeed(T t);
    //失败
    void error(String error);
}
