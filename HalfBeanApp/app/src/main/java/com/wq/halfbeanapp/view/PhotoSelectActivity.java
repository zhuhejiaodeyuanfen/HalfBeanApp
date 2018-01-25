package com.wq.halfbeanapp.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.adapter.PhotoAlbumChoiceAdapter1;
import com.wq.halfbeanapp.bean.ChoosePhotoBean;
import com.wq.halfbeanapp.bean.photo.ChoosePhotoHelpBean;
import com.wq.halfbeanapp.bean.photo.IBaseView;
import com.wq.halfbeanapp.constants.PermissionConstants;
import com.wq.halfbeanapp.util.FullyGridLayoutManager;
import com.wq.halfbeanapp.util.PhotoAlbumChoicePresenter;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.system.AppPermissionUtil1;
import com.wq.halfbeanapp.widget.titlebar.IButtonClickListener;
import com.wq.halfbeanapp.widget.titlebar.TitleBar;

import java.util.ArrayList;

public class PhotoSelectActivity  extends BaseActivity {
    private RecyclerView recyclerPhoto;
    private ArrayList<ChoosePhotoBean> arrayList;
    private PhotoAlbumChoicePresenter photoAlbumChoicePresenter;
    private PhotoAlbumChoiceAdapter1 adapter;
    //选中的图片集合
    private ArrayList<ChoosePhotoBean> imgList = new ArrayList<>();
    private int CheckedSum = 0;
    private int limit = 9;
    private TitleBar titleBar;
    private AppPermissionUtil1 appPermissionUtil1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
    }


    @Override
    public void initView() {
        titleBar = initTitle("相机胶卷", "完成");
        recyclerPhoto = (RecyclerView) findViewById(R.id.recyclerPhoto);


    }

    @Override
    public void initEventData() {
        appPermissionUtil1 = new AppPermissionUtil1();
        limit = getIntent().getIntExtra("limit", 12);
        initAdapter();
        photoAlbumChoicePresenter = new PhotoAlbumChoicePresenter();

    }


    @Override
    public void bindEvent() {
        titleBar.setButtonClickListener(new IButtonClickListener() {
            @Override
            public void right(View view) {
                super.right(view);
                appPermissionUtil1.requestPermissions(PhotoSelectActivity.this, PermissionConstants.REQUEST_FILE_WRITE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil1.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        sendPhoto();
                    }

                    @Override
                    public void onPermissionDenied() {
                        showToast(/*R.mipmap.ic_prompt,*/ "没有存储卡权限\n不可查看相册");
                    }
                });
            }
        });


    }

    @Override
    public void loadData() {
        appPermissionUtil1.requestPermissions(PhotoSelectActivity.this, PermissionConstants.REQUEST_FILE_WRITE, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new AppPermissionUtil1.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                getColumnData();
            }

            @Override
            public void onPermissionDenied() {
                showToast(/*R.mipmap.ic_prompt,*/ "没有存储卡权限\n不可查看相册");
                finishActivity();

            }
        });

    }

    /**
     * 设置适配器，显示图片
     */
    private void initAdapter() {
        arrayList = new ArrayList<>();
        adapter = new PhotoAlbumChoiceAdapter1(this);
        // 解决调用 notifyIChanged 闪烁问题,取消默认动画
        adapter.setHasStableIds(true);
        adapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (view.getId() == R.id.indexView) {
                    selectAndUnSelect(position);
                }
//                if (view.getId() == R.id.photoalbumchoiceitem_photo) {
//
//                    Bundle args = new Bundle();
//                    args.putInt("limit",limit);
//                    args.putInt("position", position);
//                    ChoosePhotoHelpBean choosePhotoHelpBean = new ChoosePhotoHelpBean();
//                    choosePhotoHelpBean.setArrayList(arrayList);
//                    choosePhotoHelpBean.setSelectList(imgList);
//                    args.putSerializable("helpBean", choosePhotoHelpBean);
//                    launcherResult(10010, PhotoAlbumRvListActivity.this, PhotoDetailChoiceActivity.class, args);
//                }
            }
        });

        FullyGridLayoutManager manager = new FullyGridLayoutManager(PhotoSelectActivity.this, 4, GridLayoutManager.VERTICAL, false);
        recyclerPhoto.setLayoutManager(manager);

        recyclerPhoto.setAdapter(adapter);

    }


    /**
     * 选中与取消选中
     *
     * @param position
     */
//    @Subscriber(tag = EventBusParams.UPDATE_PHOTO_LIST)
    public void selectAndUnSelect(int position) {
        if (position > adapter.getItemCount()) {
            return;
        }

        ChoosePhotoBean hashMap = adapter.getItem(position);
        if (hashMap.isCheck()) {//取消选中
            cancelCheck(hashMap);//取消选中，序号的改变
            hashMap.setCheck(false);
            hashMap.setIndex(0);
            CheckedSum--;
        } else {//选中
            if (CheckedSum >= limit) {
                showToast(/*R.mipmap.ic_prompt,*/ "一次最多上传" + limit + "张图片");
                return;
            }
            hashMap.setCheck(true);
            CheckedSum++;
            hashMap.setIndex(CheckedSum);//选中图片，序号为图片总数就行
            imgList.add(hashMap);
        }
        arrayList.set(position, hashMap);
        adapter.notifyDataSetChanged();
    }


    /**
     * 取消选中，排序的数字改变
     * 这里的逻辑：取消选中后，我们只需要操作取消的那一项后面的数据（如：删除list里下标为1的数据，那么我们循环就从2开始）
     */
    private void cancelCheck(ChoosePhotoBean hashMap) {
        //拿到的map里的index是展示的序号，我们这里需要减一才是，list集合里的下标
        int index = hashMap.getIndex() - 1;
        //所以这里我们从删除的那条数据的后面一个数据位置开始循环
        for (int i = (++index); i < imgList.size(); i++) {
            //后面的数据的序号都减一
            imgList.get(i).setIndex(imgList.get(i).getIndex() - 1);
        }
        //删除取消选中的
        imgList.remove(hashMap);
        adapter.notifyDataSetChanged();
    }

    /**
     * 获取图片集合
     */
    private void getColumnData() {
        showProgressDialog("加载中...");
        photoAlbumChoicePresenter.getColumnData(this, new IBaseView<ArrayList<ChoosePhotoBean>>() {
            @Override
            public void succeed(ArrayList<ChoosePhotoBean> list) {
                cancelProgressDialog();
                arrayList = list;
                adapter.addData(arrayList);
            }

            @Override
            public void error(String error) {
                cancelProgressDialog();
                showToast(/*R.mipmap.ic_prompt,*/ error);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        appPermissionUtil1.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        GlideImageLoader.clearImageMemoryCache(PhotoSelectActivity.this);
    }


    public void sendPhoto() {
        if (imgList != null && imgList.size() > 0) {
            ChoosePhotoHelpBean choosePhotoHelpBean = new ChoosePhotoHelpBean();
            choosePhotoHelpBean.setArrayList(imgList);
            Intent intent = new Intent();
            intent.putExtra("imgList", choosePhotoHelpBean);
            setResult(2010, intent);
            finishActivity();


        } else {
            cancelProgressDialog();
            showToast(/*R.mipmap.ic_prompt, */"乐谱数量为空");
        }


    }
}

