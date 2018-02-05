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
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
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
public class MsgDetailListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MsgDetailModel> dataList = new ArrayList<>();
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

    public MsgDetailListAdapter(@NonNull Context context) {
        this.mContext = context;
        userBean = UserInfoUtil.getUserInfo(context);
    }

    /**
     * 将聊天消息的时间戳格式化==并进行储存
     *
     * @param chatMessageBean
     */
    public void handleTime(MsgDetailModel chatMessageBean) {
        if (compareTime == null) {
            compareTime = chatMessageBean.getMsgTime();
            chatMessageBean.setShowTime(AppDateUtil.getTime(chatMessageBean.getMsgTime().getTime()));
        }
        if (compareTime != null) {
            if (AppDateUtil.timeCompare5Minute(chatMessageBean.getMsgTime().getTime(), compareTime.getTime()) == false) {
                //超过五分钟,需要显示

                AppLogUtil.i("超过五分钟");
                chatMessageBean.setShowTime(AppDateUtil.getTime(chatMessageBean.getMsgTime().getTime()));
                compareTime = chatMessageBean.getMsgTime();
            } else {
                AppLogUtil.i("没到五分钟呢");
            }
        }
    }

    public void addData(List<MsgDetailModel> typeBeans, boolean isRefresh) {
        if (typeBeans != null && typeBeans.size() > 0) {
            if (isRefresh) {
                dataList.clear();

            }

            for (int i = 0; i < typeBeans.size(); i++) {
                handleTime(typeBeans.get(i));

            }
            dataList.addAll(typeBeans);
            notifyDataSetChanged();
        }
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
        if (viewType == OTHER_TEXT_MSG) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_other_msg, parent, false);
            return new MsgDetailListAdapter.OtherTextMsg(view);

        } else {
            // first item
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_mine_msg, parent, false);
            return new MsgDetailListAdapter.MyTextMsg(view);
        }
    }




    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        int adapterPosition = viewHolder.getAdapterPosition();
        final MsgDetailModel info = dataList.get(adapterPosition);
        if (viewHolder instanceof MsgDetailListAdapter.MyTextMsg) {
            final MsgDetailListAdapter.MyTextMsg myTextMsg = (MsgDetailListAdapter.MyTextMsg) viewHolder;
            GlideImageLoader.display(mContext, myTextMsg.ivIcon, UserInfoUtil.getUserInfo(mContext).getUserIcon());
            myTextMsg.tvContent.setText(info.getMsgContent());
            if (!info.getShowTime().equals("noData")) {
                myTextMsg.tvTime.setVisibility(View.VISIBLE);
                myTextMsg.tvTime.setText(info.getShowTime());
            } else {
                myTextMsg.tvTime.setVisibility(View.GONE);
            }
        } else if (viewHolder instanceof MsgDetailListAdapter.OtherTextMsg) {
            final MsgDetailListAdapter.OtherTextMsg myTextMsg = (MsgDetailListAdapter.OtherTextMsg) viewHolder;
            GlideImageLoader.display(mContext, myTextMsg.ivIcon, info.getMsgFromIcon());
            myTextMsg.tvContent.setText(info.getMsgContent());
            if (!info.getShowTime().equals("noData")) {
                myTextMsg.tvTime.setVisibility(View.VISIBLE);

                myTextMsg.tvTime.setText(info.getShowTime());
            } else {
                myTextMsg.tvTime.setVisibility(View.GONE);
            }
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
        //0 代表文字
        if (dataList.get(position).getMsgType() == 0) {
            if (dataList.get(position).getMsgUserId() == userBean.getUserId()) {
                return MY_TEXT_MSG;
            } else {
                //不相等
                return OTHER_TEXT_MSG;
            }
        } else {
            return 1;
        }
    }

    public void updateData(@NonNull List<MsgDetailModel> update) {
        this.dataList = update;
        notifyDataSetChanged();
    }
}

