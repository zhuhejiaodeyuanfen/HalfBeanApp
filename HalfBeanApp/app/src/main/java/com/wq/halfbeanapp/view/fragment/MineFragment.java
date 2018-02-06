package com.wq.halfbeanapp.view.fragment;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.UserBean;
import com.wq.halfbeanapp.constants.PermissionConstants;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.util.AppIntentUtil;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.network.HttpCallBack;
import com.wq.halfbeanapp.util.network.HttpUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.system.AppPermissionUtil1;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.user.UserSetActivity;
import com.wq.halfbeanapp.view.user.UserUnWatchActivity;
import com.wq.halfbeanapp.view.user.UserWatchActivity;
import com.wq.halfbeanapp.widget.dialog.AppShareDialog;

import java.io.File;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vivianWQ on 2017/12/7
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class MineFragment extends BaseFragment {
    private ImageView ivUser;
    private TextView tvName;
    private AppShareDialog appShareDialog;
    private AppPermissionUtil1 appPermissionUtil1;
    private View tvWatchUp, tvWatchDown, tvUnWatchUp, tvUnWatchDown;
    private View ivSet;

    @Override
    public void initEventData() {

        appPermissionUtil1 = new AppPermissionUtil1();
        appShareDialog = new AppShareDialog.Builder(mContext).create();
        appShareDialog.setiDialog(new AppShareDialog.IDialog() {
            @Override
            public void selectPhotoList() {
                appPermissionUtil1.requestPermissions(MineFragment.this, PermissionConstants.REQUEST_FILE_WRITE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil1.OnPermissionListener() {
                    //权限申请成功时调用
                    @Override
                    public void onPermissionGranted() {
                        AppLogUtil.i("权限成功");
                        AppIntentUtil.selectFromAlbum(MineFragment.this);
                    }

                    //权限被用户禁止时调用
                    @Override
                    public void onPermissionDenied() {
                        AppLogUtil.i("权限失败");
                        getBaseActivity().showToast("必须开启存储空间功能才可以开启上传功能");

                    }
                });


            }
        });

    }

    @Override
    public int onSetLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        ivUser = (ImageView) mContentView.findViewById(R.id.ivUser);
        tvName = (TextView) mContentView.findViewById(R.id.tvName);
        tvWatchUp = mContentView.findViewById(R.id.tvWatchUp);
        tvWatchDown = mContentView.findViewById(R.id.tvWatchDown);
        tvUnWatchDown = mContentView.findViewById(R.id.tvUnWatchDown);
        tvUnWatchUp = mContentView.findViewById(R.id.tvUnWatchUp);
        ivSet=mContentView.findViewById(R.id.ivSet);
    }

    @Override
    public void bindEvent() {
        ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击更换头像
                appShareDialog.show();

            }
        });
        ivSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, UserSetActivity.class);

            }
        });
        //用户关注列表
        tvWatchDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, UserWatchActivity.class);

            }
        });
        tvWatchUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBaseActivity().launcher(mContext, UserWatchActivity.class);

            }
        });

        tvUnWatchUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, UserUnWatchActivity.class);
            }
        });

        tvUnWatchDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBaseActivity().launcher(mContext, UserUnWatchActivity.class);
            }
        });

    }

    @Override
    public void loadData() {
        if (UserInfoUtil.getUserInfo(mContext) != null) {
            UserBean userInfo = UserInfoUtil.getUserInfo(mContext);
            GlideImageLoader.display(mContext, ivUser, userInfo.getUserIcon(), R.mipmap.ic_launcher);
            tvName.setText(userInfo.getUserName());

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        appPermissionUtil1.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    private File cropFile;
    private String timeStamp;

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case AppIntentUtil.REQUEST_CAPTURE:
//                    AppLogUtil.i("收到图片裁剪命令返回码");
//                    cropFile = AppFileUtil.createFile(timeStamp + "crop.png", AppFileConstants.CAMERA_PATH);
//                    AppIntentUtil.cropPhoto(MineFragment.this, cropFile, imageUri);
                    break;
                case AppIntentUtil.REQUEST_PICTURE_CUT:
                    AppLogUtil.i("收到图片裁剪成功返回码");
                    cropFile.getAbsolutePath();


//                    RoNetWorkUtil
//                            .getInstance()
//                            .get(UrlConstants.UPDATE_USER_ICON)
//                            .conParams("userIcon="+cropFile.getAbsolutePath())
//                            .execute1(new DataResponseCallback<UserBean>() {
//                                @Override
//                                public void onResponseSuccess(UserBean response) {
//                                    if(response!=null)
//                                    {
//                                        GlideImageLoader.display(mContext,ivUser,response.getUserIcon());
//                                        UserInfoUtil.saveUserInfo(mContext,response);
//                                    }
//
//                                }
//
//                                @Override
//                                public void onResponseFail(String errorString) {
//
//                                }
//                            });
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("userIcon", cropFile.getAbsolutePath());
                    HttpUtil.getInstance().request(UrlConstants.UPDATE_USER_ICON, hashMap, new HttpCallBack<UserBean>() {
                        @Override
                        public void onSuccess(UserBean data) {
                            if (data != null) {
                                GlideImageLoader.display(mContext, ivUser, data.getUserIcon());
                                UserInfoUtil.saveUserInfo(mContext, data);
                            }

                        }

                        @Override
                        public void onFail(String msg) {

                        }
                    });


                    break;
                case AppIntentUtil.REQUEST_PICK_IMAGE:
                    AppLogUtil.i("收到图片选取返回码");
                    timeStamp = "picture_pick";
                    cropFile = AppIntentUtil.handleImage(data, MineFragment.this);
                    break;
            }
        }
    }
}
