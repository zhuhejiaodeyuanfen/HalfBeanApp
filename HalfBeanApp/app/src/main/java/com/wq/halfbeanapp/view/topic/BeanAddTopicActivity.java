package com.wq.halfbeanapp.view.topic;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.LiveBoardModel;
import com.wq.halfbeanapp.constants.PermissionConstants;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataResponseCallback;
import com.wq.halfbeanapp.net.response.ResponseBean;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.AppIntentUtil;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.system.AppPermissionUtil1;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.widget.dialog.AppShareDialog;

import java.io.File;
import java.sql.Timestamp;

public class BeanAddTopicActivity extends BaseActivity {

    private AppShareDialog appShareDialog;
    private ImageView ivIcon;
    private AppPermissionUtil1 appPermissionUtil1;
    private String url;
    private Button btnCommit;
    private LiveBoardModel liveBoardModel;
    private EditText etTitle, etIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_add_topic);
    }

    @Override
    public void initView() {
        initTitle("新增live");
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        btnCommit = (Button) findViewById(R.id.btnCommit);
        etTitle = (EditText) findViewById(R.id.etTitle);
        etIntro = (EditText) findViewById(R.id.etIntro);
    }

    @Override
    public void initEventData() {

        liveBoardModel = new LiveBoardModel();

        appPermissionUtil1 = new AppPermissionUtil1();
        appShareDialog = new AppShareDialog.Builder(BeanAddTopicActivity.this).create();
        appShareDialog.setiDialog(new AppShareDialog.IDialog() {
            @Override
            public void selectPhotoList() {
                appPermissionUtil1.requestPermissions(BeanAddTopicActivity.this, PermissionConstants.REQUEST_FILE_WRITE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil1.OnPermissionListener() {
                    //权限申请成功时调用
                    @Override
                    public void onPermissionGranted() {
                        AppLogUtil.i("权限成功");
                        AppIntentUtil.selectFromAlbum(BeanAddTopicActivity.this);
                    }

                    //权限被用户禁止时调用
                    @Override
                    public void onPermissionDenied() {
                        AppLogUtil.i("权限失败");
                        showToast("必须开启存储空间功能才可以开启上传功能");

                    }
                });


            }
        });


    }

    @Override
    public void bindEvent() {
        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appShareDialog.show();

            }
        });
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveBoardModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
                liveBoardModel.setLiveBoardContent(etIntro.getText().toString());
                liveBoardModel.setLiveBoardTitle(etTitle.getText().toString());
                liveBoardModel.setLiveIcon(url);
                RoNetWorkUtil
                        .getInstance()
                        .get(UrlConstants.USER_ADD_TOPIC_LIVE)
                        .params(liveBoardModel)
                        .execute(new DataResponseCallback<ResponseBean>() {
                            @Override
                            public void onResponseSuccess(ResponseBean response) {
                                showToast("发表成功");

                            }

                            @Override
                            public void onResponseFail(String errorString) {
                                showToast(errorString);

                            }
                        });
            }
        });

    }

    @Override
    public void loadData() {

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
                    url = cropFile.getAbsolutePath();

                    GlideImageLoader.display(BeanAddTopicActivity.this, ivIcon, cropFile.getAbsolutePath());


                    break;
                case AppIntentUtil.REQUEST_PICK_IMAGE:
                    AppLogUtil.i("收到图片选取返回码");
                    timeStamp = "picture_pick";
                    cropFile = AppIntentUtil.handleImage(data, BeanAddTopicActivity.this);
                    break;
            }
        }
    }
}
