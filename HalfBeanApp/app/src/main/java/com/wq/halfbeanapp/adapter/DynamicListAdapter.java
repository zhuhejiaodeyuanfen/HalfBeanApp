package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.DynamicListBean;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.util.user.UserInfoUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2018/1/24
 * Mail: wangqi_vivian@sina.com
 * desc: 评论列表adapter
 * <p>
 *    服务器记录所有消息的时间，但是并不全部显示出来，需要满足触发条件；
 * 	聊天界面中时间显示有5分钟的触发冷却时间，即第一次发送消息，显示该消息的时间；每次触发时间显示后五分钟内的其他消息不触发时间显示；五分钟后的下一条消息可触发时间显示；
 * 	重新载入消息时，依然保持同样的呈现结果；
 * 	时间格式：
 * 	T日的所有时间显示【xx：xx】
 * 	T-1日的所有时间显示【昨天 xx：xx】
 * 	T-6到T-2日的所有时间显示【星期 xx:xx】
 * 	T-7及之前的时间显示【xxxx-xx-xx xx:xx】
 * 	其中T日指日期等于今天日期，T-1指日期等于今天日期-1
 * 	已发送消息时间按读服务器时间，未发送和发送失败消息，读本地时间；
 */
public class DynamicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DynamicListBean> dataList = new ArrayList<>();
    private final Context mContext;
    private int lockCount;
    private int unLockCount;
    private int mUnLockCountPos;
    private int mLockCountPos;
    private Timestamp compareTime;
    public static final int MY_TEXT_MSG = 0;
    public static final int OTHER_TEXT_MSG = 1;
    public static final int MY_IMAGE_MSG = 3;
    public static final int OTHER_IMAGE_MSG = 4;
    private UserBean userBean;


    public int getUnLockCountPos() {
        return mUnLockCountPos;
    }

    public int getLockCount() {
        return lockCount;
    }

    public int getUnLockCount() {
        return unLockCount;
    }

    public DynamicListAdapter(@NonNull Context context) {
        this.mContext = context;
        userBean = UserInfoUtil.getUserInfo(context);
    }


    public void addData(List<DynamicListBean> typeBeans, boolean isRefresh) {
        if (typeBeans != null && typeBeans.size() > 0) {
            if (isRefresh) {
                dataList.clear();

            }


            dataList.addAll(typeBeans);
            notifyDataSetChanged();
        }
    }

    public void addData(DynamicListBean typeBean) {
        dataList.add(typeBean);
        notifyDataSetChanged();
    }

    public List<DynamicListBean> getDataList() {
        return dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_user_broad, parent, false);
            return new DynamicListAdapter.OtherTextMsg(view);

//        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        final DynamicListBean info = dataList.get(adapterPosition);
        if (viewHolder instanceof DynamicListAdapter.MyTextMsg) {

        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private static class MyTextMsg extends RecyclerView.ViewHolder {

        MyTextMsg(View itemView) {
            super(itemView);

        }
    }

    private static class OtherTextMsg extends RecyclerView.ViewHolder {

        OtherTextMsg(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //0 代表文字
        if (dataList.get(position).getType() == 0) {

            return 0;
        }
        return 0;
    }

    public void updateData(@NonNull List<DynamicListBean> update) {
        this.dataList = update;
        notifyDataSetChanged();
    }
}

