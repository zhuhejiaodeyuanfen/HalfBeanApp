package com.wq.halfbeanapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.LockAdapter;
import com.wq.halfbeanapp.bean.ChoosePhotoBean;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.bean.TypeBean;
import com.wq.halfbeanapp.bean.photo.ChoosePhotoHelpBean;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.BaseResponseCallback;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HomeAddTopicActivity extends BaseActivity {
    private TextView tvAddPic;
    private RecyclerView rvAddTopic;
    private LockAdapter lockAdapter;
    private Button btnAddTopic;
    private EasyPopup mCirclePop;
    private TextView tvCommit;
    private HomeBoardDetailModel homeBoardDetailModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_topic);
    }

    @Override
    public void initView() {
        initTitle("发表话题");
        tvAddPic = (TextView) findViewById(R.id.tvAddPic);
        tvCommit= (TextView) findViewById(R.id.tvCommit);
        btnAddTopic = (Button) findViewById(R.id.btnAddTopic);
        rvAddTopic = (RecyclerView) findViewById(R.id.rvAddTopic);
        rvAddTopic.setLayoutManager(new LinearLayoutManager(HomeAddTopicActivity.this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    public void initEventData() {
        lockAdapter = new LockAdapter(HomeAddTopicActivity.this);
        rvAddTopic.setAdapter(lockAdapter);


    }


    private List<TypeBean> list = new ArrayList<>();

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2010) {
            //从相册选择返回页面
            if (data != null && data.getExtras() != null) {
                ChoosePhotoHelpBean choosePhoto = (ChoosePhotoHelpBean) data.getExtras().getSerializable("imgList");
                if (choosePhoto != null) {
                    if (choosePhoto.getArrayList() != null && choosePhoto.getArrayList().size() > 0) {
                        for (int i = 0; i < choosePhoto.getArrayList().size(); i++) {

                            ChoosePhotoBean choosePhotoBean = choosePhoto.getArrayList().get(i);
                            TypeBean typeBean = new TypeBean();
                            typeBean.setType(1);
                            typeBean.setContent(choosePhotoBean.getImgUrl());
                            list.add(typeBean);
                        }
                    }

                    lockAdapter.addData(list);

                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void bindEvent() {
        //从相册选择
        tvAddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcherResult(2010, HomeAddTopicActivity.this, PhotoSelectActivity.class);
//                TypeBean typeBean=new TypeBean();
//                typeBean.setType(1);
//                typeBean.setContent("");
//                lockAdapter.addData(typeBean);


            }
        });
        tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交评论
                homeBoardDetailModel=new HomeBoardDetailModel();
                homeBoardDetailModel.setPostContent(JsonTools.getJsonString(lockAdapter.getDataList()).trim());

                homeBoardDetailModel.setPostTitle("测试标题");
                homeBoardDetailModel.setPostParentId("1");
                homeBoardDetailModel.setPostAdmin("速速速小沫");
                homeBoardDetailModel.setPostCreateTime(new Timestamp(System.currentTimeMillis()));

                RoNetWorkUtil
                        .getInstance()
                        .get(UrlConstants.ADD_HOME_TOPIC)
                        .params(homeBoardDetailModel)
                        .execute(new BaseResponseCallback() {

                            @Override
                            public void onResponseFail(String errorString) {

                            }

                            @Override
                            public void onSuccess(String response) {

                            }
                        });
            }
        });
        btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mCirclePop.showAtAnchorView(v,VerticalGravity.BELOW, HorizontalGravity.RIGHT, 0, 0);
            }
        });

    }


    @Override
    public void loadData() {

        mCirclePop = new EasyPopup(HomeAddTopicActivity.this)
                .setContentView(R.layout.layout_list_item)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .createPopup();

    }
}
