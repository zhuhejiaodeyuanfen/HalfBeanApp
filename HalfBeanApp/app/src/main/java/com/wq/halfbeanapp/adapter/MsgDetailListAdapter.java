package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.MsgDetailModel;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;

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
public class MsgDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsgDetailModel> dataList = new ArrayList<>();
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

    public MsgDetailListAdapter(@NonNull Context context) {
        this.mContext = context;
    }

    public void addData(List<MsgDetailModel> typeBeans) {
        dataList.addAll(typeBeans);
        notifyDataSetChanged();
    }

    public void addData(MsgDetailModel typeBean) {
        dataList.add(typeBean);
        notifyDataSetChanged();
    }

    public List<MsgDetailModel> getDataList() {
        return dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_mine_msg, parent, false);
            return new MsgDetailListAdapter.MyTextMsg(view);

        } else {
            // first item
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_edit_text, parent, false);
            return new MsgDetailListAdapter.OtherTextMsg(view);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        final MsgDetailModel info = dataList.get(adapterPosition);
        if (viewHolder instanceof MsgDetailListAdapter.MyTextMsg) {
            final MsgDetailListAdapter.MyTextMsg myTextMsg = (MsgDetailListAdapter.MyTextMsg) viewHolder;
            GlideImageLoader.display(mContext, myTextMsg.ivIcon, info.getMsgFromIcon());
            myTextMsg.tvContent.setText(info.getMsgContent());
        } else if (viewHolder instanceof MsgDetailListAdapter.OtherTextMsg) {
            final MsgDetailListAdapter.OtherTextMsg myTextMsg = (MsgDetailListAdapter.OtherTextMsg) viewHolder;
            GlideImageLoader.display(mContext, myTextMsg.ivIcon, info.getMsgFromIcon());
            myTextMsg.tvContent.setText(info.getMsgContent());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    private static class MyTextMsg extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private ImageView ivIcon;
        private ImageView ivSendFail;
        private TextView tvContent;

        MyTextMsg(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            ivSendFail = (ImageView) itemView.findViewById(R.id.ivSendFail);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }

    private static class OtherTextMsg extends RecyclerView.ViewHolder {
        private TextView tvTime;
        private ImageView ivIcon;
        private TextView tvContent;

        OtherTextMsg(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position).getMsgType() == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    public void updateData(@NonNull List<MsgDetailModel> update) {
        this.dataList = update;
        notifyDataSetChanged();
    }
}

