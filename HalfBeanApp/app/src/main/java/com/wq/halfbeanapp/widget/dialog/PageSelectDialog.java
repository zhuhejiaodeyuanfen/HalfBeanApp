package com.wq.halfbeanapp.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.adapter.MyItemClickListener;
import com.wq.halfbeanapp.adapter.PageGridAdapter;
import com.wq.halfbeanapp.adapter.PageSlideAdapter;
import com.wq.halfbeanapp.util.AppLogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2017/11/21
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PageSelectDialog extends Dialog {
    private View mView;
    private Context context;
    private TextView tvSelectPhoto;
    private int commentCount;
    private RecyclerView rvPageSlide, rvPageSelect;
    private List<String> pageSlideNum;
    private PageSlideAdapter pageSlideAdapter;
    private PageGridAdapter pageGridAdapter;
    private List<Integer> list = new ArrayList<>();
    private List<List<Integer>> listInteger = new ArrayList<>();
    private IDialog iDialog;


    private PageSelectDialog(Context context) {
        super(context, R.style.ios_bottom_dialog);
        this.context = context;

    }

    public IDialog getiDialog() {
        return iDialog;
    }

    public void setiDialog(IDialog iDialog) {
        this.iDialog = iDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        //设置dialog的宽高为屏幕的宽高
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        View viewDialog = LayoutInflater.from(context).inflate(R.layout.layout_page_select, null);
        tvSelectPhoto = (TextView) viewDialog.findViewById(R.id.tvSelectPhoto);
        setContentView(viewDialog, layoutParams);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        rvPageSlide = (RecyclerView) viewDialog.findViewById(R.id.rvPageSlide);
        rvPageSelect = (RecyclerView) viewDialog.findViewById(R.id.rvPageSelect);
        pageSlideNum = new ArrayList<>();
        pageSlideAdapter = new PageSlideAdapter(context);
        pageGridAdapter = new PageGridAdapter(context);
//        tvSelectPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                iDialog.selectPhotoList();
//            }
//        });

        setCanceledOnTouchOutside(true);

        if (commentCount > 50 * 20) {
            //超过50页
            rvPageSlide.setVisibility(View.VISIBLE);



        } else {
            rvPageSlide.setVisibility(View.GONE);
        }

        int page = commentCount / 20;

        int pageCount = page / 50;
        for (int i = 0; i < pageCount; i++) {
            pageSlideNum.add((i * 50 + 1) + "-" + 50 * (i + 1));
            list = new ArrayList<>();
            for (int j = i * 50 + 1; j <= i * 50 + 50; j++) {
                list.add(j);
            }
            listInteger.add(list);
        }
        int pageMod = commentCount % (50 * 20);
        if (pageMod == 0) {

        } else {
            if (pageMod == 1) {
                pageSlideNum.add(pageCount * 50 + 1 + "");
            } else {
                int i;
                if (commentCount % 20 > 0) {
                    i = commentCount / 20 + 1;
                } else {
                    i = commentCount / 20;
                }
                list = new ArrayList<>();
                for (int j = (pageCount) * 50 + 1; j <= i; j++) {
                    list.add(j);
                }
                listInteger.add(list);
                pageSlideNum.add((pageCount) * 50 + 1 + "-" + i);
            }

        }


        pageSlideAdapter.addData(pageSlideNum);
        rvPageSlide.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        rvPageSlide.setAdapter(pageSlideAdapter);
        rvPageSelect.setLayoutManager(new GridLayoutManager(context, 5, GridLayoutManager.VERTICAL, false));
        pageGridAdapter.addData(listInteger.get(0));
        rvPageSelect.setAdapter(pageGridAdapter);

        pageSlideAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //页码器上方的点击事件
                pageGridAdapter.setDatas(listInteger.get(position));
            }
        });

        pageGridAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Integer item = pageGridAdapter.getItem(position);
                AppLogUtil.i("点击了第几个item" + item);
                iDialog.selectPage(item);
            }
        });
    }

    /**
     * 调用完Builder类的create()方法后显示该对话框的方法
     */
    @Override
    public void show() {
        super.show();
        show(this);
    }

    private void show(final PageSelectDialog mDialog) {




    }


    public interface IDialog {
        void selectPage(int page);
    }

    public static class Builder {

        private PageSelectDialog mDialog;


        public Builder(Context context) {
            mDialog = new PageSelectDialog(context);
        }


        public PageSelectDialog.Builder initCommentCount(int commentCount) {
            mDialog.commentCount = commentCount;
            return this;
        }


        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public PageSelectDialog create() {
            return mDialog;
        }
    }

}
