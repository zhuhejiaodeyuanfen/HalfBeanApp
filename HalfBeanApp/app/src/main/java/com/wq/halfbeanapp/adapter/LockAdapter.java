package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.TypeBean;
import com.wq.halfbeanapp.util.AppLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class LockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TypeBean> dataList = new ArrayList<>();
    private final Context mContext;
    private int lockCount;
    private int unLockCount;
    private int mUnLockCountPos;
    private int mLockCountPos;


    public int getUnLockCountPos() {
        return mUnLockCountPos;
    }

    public int getLockCount() {
        return lockCount;
    }

    public int getUnLockCount() {
        return unLockCount;
    }

    public LockAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    public void addData(List<TypeBean> typeBeans) {
        dataList.addAll(typeBeans);
        notifyDataSetChanged();
    }

    public void addData(TypeBean typeBean) {
        dataList.add(typeBean);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_pic_show, parent, false);
            return new NormalVH(view);

        } else {
            // first item
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_edit_text, parent, false);
            return new FirstVH(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        final TypeBean info = dataList.get(adapterPosition);
        if (viewHolder instanceof NormalVH) {
            final NormalVH normalVH = (NormalVH) viewHolder;
            normalVH.ivPicShow.setImageResource(R.mipmap.ic_launcher);
            normalVH.etContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!s.toString().trim().equals("")) {
                        AppLogUtil.i("新增提示文本" + s);
                        normalVH.etContent.setText("");
                        TypeBean typeBean = new TypeBean();
                        typeBean.setContent(s.toString());
                        typeBean.setType(0);
                        addData(typeBean);
                    }

                }
            });

        } else if (viewHolder instanceof FirstVH) {
            final FirstVH holder = (FirstVH) viewHolder;
            holder.etAddContent.setText(info.getContent());
            holder.etAddContent.requestFocus();
            holder.etAddContent.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (info.getContent() != null) {
                        info.setContent(s.toString());
                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    private static class NormalVH extends RecyclerView.ViewHolder {

        private final ImageView ivPicShow;
        private final EditText etContent;


        NormalVH(View itemView) {
            super(itemView);
            ivPicShow = (ImageView) itemView.findViewById(R.id.ivPicShow);
            etContent = (EditText) itemView.findViewById(R.id.etContent);
        }
    }

    private static class FirstVH extends RecyclerView.ViewHolder {

        private final EditText etAddContent;

        FirstVH(View itemView) {
            super(itemView);
            etAddContent = (EditText) itemView.findViewById(R.id.etAddContent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getType() == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    public void updateData(@NonNull List<TypeBean> update) {
        this.dataList = update;
        notifyDataSetChanged();
    }
}
