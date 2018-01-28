package com.wq.halfbeanapp.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.CommentListAdapter;
import com.wq.halfbeanapp.adapter.HomeDetailAdapter;
import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.CommentModelOri;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.bean.TypeBean;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataListResponseCallback;
import com.wq.halfbeanapp.net.response.DataResponseCallback;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.ResponseBean;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.system.SoftKeyBoardListener;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.widget.dialog.CommentDialog;
import com.wq.halfbeanapp.widget.dialog.PageSelectDialog;

import java.sql.Timestamp;
import java.util.List;

/**
 * 页码器展示规则 commentNum>20展示页码器
 * <20不需要展示
 */

public class HomeDetailActivity extends BaseActivity {

    private TextView tvContent, tvSubTitle, tvWriter, tvTimeDate;
    private RecyclerView rvComment;
    private HomeBoardDetailModel homeBoardDetailModel;
    private ImageView ivIcon;
    private RecyclerView rvContent;
    private TextView btnSend;
    private EditText etContent;

    private CommentModelOri commentModelOri;
    private CommentListAdapter commentListAdapter;
    private HomeDetailAdapter homeDetailAdapter;
    private CommentBean commentBean;
    private TextView btnSelectPage;
    private PageSelectDialog pageSelectDialog;
    private TwinklingRefreshLayout swipeRefresh;
    private CommentDialog commentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);
    }

    @Override
    public void initView() {
        initTitle("话题详情");
        tvContent = (TextView) findViewById(R.id.tvContent);
        etContent = (EditText) findViewById(R.id.etContent);
        rvComment = (RecyclerView) findViewById(R.id.rvComment);
        tvSubTitle = (TextView) findViewById(R.id.tvSubTitle);
        tvWriter = (TextView) findViewById(R.id.tvWriter);
        tvTimeDate = (TextView) findViewById(R.id.tvTimeDate);
        rvContent = (RecyclerView) findViewById(R.id.rvContent);
        btnSend = (TextView) findViewById(R.id.btnSend);
        btnSelectPage = (TextView) findViewById(R.id.btnSelectPage);
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        swipeRefresh = (TwinklingRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefresh.setAutoLoadMore(true);

        swipeRefresh.setEnableOverScroll(false);
        swipeRefresh.setEnableRefresh(false);
        LoadingView loadingView = new LoadingView(this);
        swipeRefresh.setBottomView(loadingView);
//        etContent.setInputType(InputType.TYPE_NULL);

    }

    @Override
    public void initEventData() {
        commentModelOri = new CommentModelOri();
        homeBoardDetailModel = (HomeBoardDetailModel) getIntent().getSerializableExtra("item");
        if (homeBoardDetailModel != null) {
            GlideImageLoader.display(HomeDetailActivity.this, ivIcon, homeBoardDetailModel.getPostAdminIcon());
            tvTimeDate.setText(AppDateUtil.getCompareShowString(homeBoardDetailModel.getSysCurrentTime().getTime(), homeBoardDetailModel.getPostCreateTime().getTime()));
        }
        commentListAdapter = new CommentListAdapter(HomeDetailActivity.this);
        homeDetailAdapter = new HomeDetailAdapter(HomeDetailActivity.this);
        rvContent.setLayoutManager(new LinearLayoutManager(HomeDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvContent.setAdapter(homeDetailAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(HomeDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvComment.setAdapter(commentListAdapter);
        if (homeBoardDetailModel.getPostCommentCount() > 20) {
            //超过20条,需要展示
            btnSelectPage.setVisibility(View.VISIBLE);
            int page = homeBoardDetailModel.getPostCommentCount() / 20;


            int pageMod = homeBoardDetailModel.getPostCommentCount() % 20;
            if (pageMod > 0) {
                page = page + 1;

            }
            btnSelectPage.setText("1/" + page);
        } else {
            btnSelectPage.setVisibility(View.GONE);
        }

        pageSelectDialog = new PageSelectDialog
                .Builder(HomeDetailActivity.this)
                .initCommentCount(homeBoardDetailModel.getPostCommentCount())
                .create();
        pageSelectDialog.setiDialog(new PageSelectDialog.IDialog() {
            @Override
            public void selectPage(int page) {
                showToast("选择了第几页" + page);
                getPageData(page);
            }
        });

        commentDialog=new CommentDialog
                .Builder(HomeDetailActivity.this)
                .create();
    }

    @Override
    public void bindEvent() {
//        swipeRefresh.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                AppUtil.hideInputMethod(HomeDetailActivity.this,etContent);
//                return false;
//            }
//        });
        swipeRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                currPage = currPage + 1;
                getPageData(currPage);
            }
        });
        etContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                commentDialog.show();

            }
        });

        SoftKeyBoardListener.setListener(HomeDetailActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Toast.makeText(HomeDetailActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
                Toast.makeText(HomeDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
//                etContent.setText(commentDialog.getText());
//                etContent.setSelection(commentDialog.getSelection());
//                etContent.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
////改变默认的单行模式
//                etContent.setSingleLine(false);
//                etContent.setMaxLines(3);
////水平滚动设置为False
//                etContent.setHorizontallyScrolling(false);
//                commentDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                commentModelOri.setCommentContent(etContent.getText().toString());
                commentModelOri.setCommentPostId(homeBoardDetailModel.getHomePostId());
                commentModelOri.setCommentUserId(UserInfoUtil.getUserInfo(HomeDetailActivity.this).getUserId());
                commentModelOri.setCommentTime(new Timestamp(System.currentTimeMillis()));
                RoNetWorkUtil
                        .getInstance()
                        .get(UrlConstants.USER_ADD_COMMENT)
                        .params(commentModelOri)
                        .execute(new DataResponseCallback<ResponseBean>() {
                            @Override
                            public void onResponseSuccess(ResponseBean response) {
                                showToast("发表成功!");
                                commentBean = new CommentBean();
                                commentBean.setUserComment(etContent.getText().toString());
                                etContent.setText("");
                                commentBean.setUserIcon(UserInfoUtil.getUserInfo(HomeDetailActivity.this).getUserIcon());
                                commentBean.setUserName(UserInfoUtil.getUserInfo(HomeDetailActivity.this).getUserName());
                                commentBean.setCommentTime(commentModelOri.getCommentTime());
                                commentListAdapter.addData(commentBean);

                            }

                            @Override
                            public void onResponseFail(String errorString) {
                                showToast(errorString);

                            }
                        });

            }
        });
        btnSelectPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageSelectDialog.show();
            }
        });

    }

    @Override
    public void loadData() {
        if (homeBoardDetailModel != null) {
//            tvContent.setText(homeBoardDetailModel.getPostContent());
            tvSubTitle.setText(homeBoardDetailModel.getPostTitle());
            tvWriter.setText(homeBoardDetailModel.getPostAdmin());
            List<TypeBean> beanList = JsonTools.getBeanList(homeBoardDetailModel.getPostContent(), TypeBean.class);
            if (beanList != null && beanList.size() > 0) {
                homeDetailAdapter.addData(beanList);
            }

        }

        getPageData(currPage);

    }

    private int currPage = 1;

    public void getPageData(int page) {
        currPage = page;


        RoNetWorkUtil
                .getInstance()
                .get(UrlConstants.GET_COMMENT_LIST)
                .conParams("postId=" + homeBoardDetailModel.getHomePostId() + "&&page=" + page)
                .execute1(new DataListResponseCallback<CommentBean>() {
                    @Override
                    public void onResponseSuccess(List<CommentBean> response) {
                        swipeRefresh.finishLoadmore();

                        if (response != null && response.size() > 0) {
                            commentListAdapter.setDatas(response);
                        }

                    }

                    @Override
                    public void onResponseFail(String errorString) {

                    }
                });
    }
}