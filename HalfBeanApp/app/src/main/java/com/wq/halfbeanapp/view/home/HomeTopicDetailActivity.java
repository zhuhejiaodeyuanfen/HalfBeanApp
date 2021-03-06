package com.wq.halfbeanapp.view.home;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
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
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.bean.CommentBean;
import com.wq.halfbeanapp.bean.CommentModelOri;
import com.wq.halfbeanapp.bean.HomeBoardDetailModel;
import com.wq.halfbeanapp.bean.TypeBean;
import com.wq.halfbeanapp.constants.UrlConstants;
import com.wq.halfbeanapp.net.response.DataResponseCallback;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.net.response.ResponseBean;
import com.wq.halfbeanapp.net.response.RoNetWorkUtil;
import com.wq.halfbeanapp.presenter.HomeTopicPresenter;
import com.wq.halfbeanapp.util.AppDateUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.system.SoftKeyBoardListener;
import com.wq.halfbeanapp.util.user.UserInfoUtil;
import com.wq.halfbeanapp.view.BaseActivity;
import com.wq.halfbeanapp.view.UserDetailActivity;
import com.wq.halfbeanapp.view.iview.IHomeTopicView;
import com.wq.halfbeanapp.widget.dialog.CommentDialog;
import com.wq.halfbeanapp.widget.dialog.PageSelectDialog;

import java.sql.Timestamp;
import java.util.List;

/**
 * 页码器展示规则 commentNum>20展示页码器
 * <20不需要展示
 */

public class HomeTopicDetailActivity extends BaseActivity implements IHomeTopicView {

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
    private NestedScrollView scrollView;
    private TwinklingRefreshLayout swipeRefresh;
    private CommentDialog commentDialog;
    private TextView tvResponse;
    private HomeTopicPresenter homeTopicPresenter;
    private int totalPage;
    private int currPage = 1;

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
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        tvResponse = (TextView) findViewById(R.id.tvResponse);
        swipeRefresh.setEnableOverScroll(false);
        swipeRefresh.setEnableRefresh(false);
        LoadingView loadingView = new LoadingView(this);
        swipeRefresh.setBottomView(loadingView);
//        etContent.setInputType(InputType.TYPE_NULL);

    }

    @Override
    public void initEventData() {
        homeTopicPresenter = new HomeTopicPresenter(HomeTopicDetailActivity.this, this);
        commentModelOri = new CommentModelOri();
        homeBoardDetailModel = (HomeBoardDetailModel) getIntent().getSerializableExtra("item");
        if (homeBoardDetailModel != null) {
            GlideImageLoader.display(HomeTopicDetailActivity.this, ivIcon, homeBoardDetailModel.getPostAdminIcon());
            tvTimeDate.setText(AppDateUtil.getCompareShowString(homeBoardDetailModel.getSysCurrentTime().getTime(), homeBoardDetailModel.getPostCreateTime().getTime()));
        }
        commentListAdapter = new CommentListAdapter(HomeTopicDetailActivity.this);
        homeDetailAdapter = new HomeDetailAdapter(HomeTopicDetailActivity.this);
        rvContent.setLayoutManager(new LinearLayoutManager(HomeTopicDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvContent.setAdapter(homeDetailAdapter);
        rvComment.setLayoutManager(new LinearLayoutManager(HomeTopicDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
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
            currPage = 1;
            totalPage = page;
            showPage();
        } else {
            btnSelectPage.setVisibility(View.GONE);
        }

        pageSelectDialog = new PageSelectDialog
                .Builder(HomeTopicDetailActivity.this)
                .initCommentCount(homeBoardDetailModel.getPostCommentCount())
                .create();
        pageSelectDialog.setiDialog(new PageSelectDialog.IDialog() {
            @Override
            public void selectPage(int page) {
                showToast("选择了第几页" + page);
                getPageData(page);
            }
        });

        commentDialog = new CommentDialog
                .Builder(HomeTopicDetailActivity.this)
                .create();
    }

    public void showPage() {
        btnSelectPage.setText(currPage + "/" + totalPage);
    }

    private  int userId;
    @Override
    public void bindEvent() {
//        swipeRefresh.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                AppUtil.hideInputMethod(HomeTopicDetailActivity.this,etContent);
//                return false;
//            }
//        });
        commentListAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle args = new Bundle();
              userId  = commentListAdapter.getItem(position).getUserId();
                args.putInt("userId",userId );
                launcher(HomeTopicDetailActivity.this, UserDetailActivity.class, args);
            }
        });
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

        SoftKeyBoardListener.setListener(HomeTopicDetailActivity.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                Toast.makeText(HomeTopicDetailActivity.this, "键盘显示 高度" + height, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void keyBoardHide(int height) {
                Toast.makeText(HomeTopicDetailActivity.this, "键盘隐藏 高度" + height, Toast.LENGTH_SHORT).show();
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
                commentModelOri.setCommentUserId(UserInfoUtil.getUserInfo(HomeTopicDetailActivity.this).getUserId());
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
                                commentBean.setUserIcon(UserInfoUtil.getUserInfo(HomeTopicDetailActivity.this).getUserIcon());
                                commentBean.setUserName(UserInfoUtil.getUserInfo(HomeTopicDetailActivity.this).getUserName());
                                commentBean.setCommentTime(commentModelOri.getCommentTime());
                                commentBean.setSysCurrentTime(commentModelOri.getCommentTime());
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


    public void getPageData(int page) {
        currPage = page;
        homeTopicPresenter.getPostCommentList(homeBoardDetailModel.getHomePostId(), page);
    }

    @Override
    public void getTopicCommentList(List<CommentBean> response) {
        swipeRefresh.finishLoadmore();
        if (response != null && response.size() > 0) {
            commentListAdapter.setDatas(response);
            if (pageSelectDialog != null)
                pageSelectDialog.dismiss();
            if (currPage > 1) {
                //
                rvComment.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        rvComment.scrollToPosition(0);
                        scrollView.scrollTo(0, tvResponse.getTop() - 20);
                        if (btnSelectPage.getVisibility() == View.VISIBLE) {
                            showPage();
                        }

                    }
                }, 250);
            }
        }

    }
}
