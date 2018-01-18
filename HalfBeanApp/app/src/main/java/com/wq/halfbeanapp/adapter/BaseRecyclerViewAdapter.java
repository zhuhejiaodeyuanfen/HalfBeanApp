package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vivianWQ on 2017/5/18
 * Mail: wangqi_vivian@sina.com
 * desc: RecyclerView的baseAdapter
 * Version: 1.0
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.BaseViewHolder> {


    protected List<T> datas = new ArrayList<>();

    private MyItemClickListener mItemClickListener;

    public BaseRecyclerViewAdapter(Context context) {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return new BaseViewHolder(view, mItemClickListener);
    }

    public List<T> getDatas() {
        return datas;
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(BaseRecyclerViewAdapter.BaseViewHolder holder, final int position) {

        bindData(holder, position);

    }

    public void onBindViewHolder(BaseViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            bindData(holder,position);

        }
    }

    public void set(int position, T data) {
        if (datas.size() > 0 && datas.size() > position) {
            datas.set(position, data);
            notifyDataSetChanged();
        }
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    public void refresh(List<T> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (datas != null) {
            return datas.get(position);
        } else
            return null;
    }

    /**
     * 删除某一位置的元素
     *
     * @param position
     */
    public void removeItem(int position) {
        if (datas != null) {
            datas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, datas.size());
            notifyDataSetChanged();
        }
    }


    /**
     * 添加数据
     *
     * @param datas
     */
    public void addData(List<T> datas) {
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clear() {
        this.datas.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加单一数据
     *
     * @param data
     */
    public void addData(T data) {
        this.datas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 添加单一数据
     *
     * @param data
     */
    public void addData(T data, boolean isRefresh) {
        this.datas.add(data);
        if (isRefresh)
            notifyDataSetChanged();
    }

    public void refreshData() {
        notifyDataSetChanged();
    }


    public void setDatas(List<T> datas) {
        if (this.datas != null)
            this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 绑定数据
     *
     * @param holder   具体的viewHolder
     * @param position 对应的索引
     */
    protected abstract void bindData(BaseViewHolder holder, int position);


    @Override
    public int getItemCount() {

        return datas == null ? 0 : datas.size();
    }


    /**
     * 封装ViewHolder ,子类可以直接使用
     */
    public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyItemClickListener mListener;
        private Map<Integer, View> mViewMap;

        public BaseViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            mViewMap = new HashMap<>();
            this.mListener = listener;
        }

        /**
         * 获取设置的view
         *
         * @param id
         * @return
         */
        public View getView(int id, boolean isClick) {
            View view = mViewMap.get(id);
            if (view == null) {
                view = itemView.findViewById(id);
                if (isClick)
                    view.setOnClickListener(this);
                mViewMap.put(id, view);
            }
            return view;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
            }

        }
    }

    /**
     * 获取子item
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 设置文本属性
     *
     * @param view
     * @param text
     */
    public void setItemText(View view, String text) {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }
}