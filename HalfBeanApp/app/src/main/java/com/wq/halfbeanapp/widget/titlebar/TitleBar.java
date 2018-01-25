package com.wq.halfbeanapp.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.view.BaseActivity;


/**
 * TitleBar自定义控件
 */
public class TitleBar extends FrameLayout {
    private TextView tvLeft, tvTitle, tvRight;
    private ImageView ivLeft, ivRight;
    private IButtonClickListener buttonClickListener;
    private View rightClickView, leftClickView;
    private View titleBarView;


    public TitleBar(Context context) {
        super(context);
        initView();
    }

    public TitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public void setBackGroundColor(int color) {
        titleBarView.setBackgroundColor(color);
    }


    private void initView() {
        titleBarView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.widget_base_title_bar_layout, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(titleBarView, layoutParams);
        leftClickView = findViewById(R.id.leftClickView);
        rightClickView = findViewById(R.id.rightClickView);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvRight = (TextView) findViewById(R.id.tvRight);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        ivLeft = (ImageView) findViewById(R.id.ivLeft);
        ivRight = (ImageView) findViewById(R.id.ivRight);
        addLeftClick(leftClickView);
        addRightClick(rightClickView);
    }


    private void addLeftClick(View view) {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null == buttonClickListener || !buttonClickListener.left(v)) {
                    BaseActivity.finishActivity(getContext());
                }
            }
        });
    }

    private void addRightClick(View view) {
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (null != buttonClickListener) {
                    buttonClickListener.right(v);
                }
            }
        });
    }


    /**
     * @param @param margin
     * @return void
     * @description 设置左边按钮距离左边的距离
     * @author jiaBF
     */
    public void setLeftMargin(int margin) {
        RelativeLayout.LayoutParams relativeLayout = (RelativeLayout.LayoutParams) tvLeft.getLayoutParams();
        relativeLayout.leftMargin = margin;
        relativeLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvLeft.setLayoutParams(relativeLayout);
    }


    /**
     * @param @param margin
     * @return void
     * @description 设置右边按钮距离右边的距离
     * @author jiaBF
     */
    public void setRightMargin(int margin) {
        RelativeLayout.LayoutParams relativeLayout = (RelativeLayout.LayoutParams) tvRight.getLayoutParams();
        relativeLayout.rightMargin = margin;
        tvRight.setLayoutParams(relativeLayout);
    }


    /**
     * 描述：标题栏的背景图.
     *
     * @param color 背景颜色值
     */
    public void setTitleBackgroundByColor(int color) {
        this.setBackgroundColor(color);
    }

    /**
     * 描述：设置左边按钮背景图.
     *
     * @param res 背景图资源ID
     */
    public void setLeftBtnBackground(int res) {
        tvLeft.setBackgroundResource(res);
        tvLeft.setVisibility(View.VISIBLE);
    }


    /**
     * 描述：标题左边按钮背景色.
     *
     * @param color 背景颜色值
     */
    public void setLeftBtnBackgroundColor(int color) {
        tvLeft.setBackgroundColor(color);
        tvLeft.setVisibility(View.VISIBLE);
    }


    public void hidenLeftButton() {
        leftClickView.setVisibility(View.GONE);
    }

    public void hidenRightButton() {
        tvRight.setVisibility(View.INVISIBLE);
    }

    public void showLeftButton() {
        tvLeft.setVisibility(View.VISIBLE);
    }

    public void showRightButton() {
        tvRight.setVisibility(View.VISIBLE);
    }

    /**
     * 描述：设置左边按钮文本.
     *
     * @param text 文本
     */
    public void setLeftText(String text) {
        ivLeft.setVisibility(View.GONE);
        tvLeft.setText(" " + text);
        tvLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 描述：设置左边按钮文本.
     *
     * @param resId 文本的资源ID
     */
    public void setLeftText(int resId) {
        setLeftText(getResources().getString(resId));
    }


    /**
     * 描述：设置左边标题字体颜色
     */
    public void setLeftTextColor(String color) {
        tvLeft.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置左按钮字体大小
     *
     * @param resSize 字体大小
     */
    public void setLeftTextSize(int resSize) {
        tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_SP, resSize);
    }


    /**
     * 描述：设置右边按钮背景图.
     *
     * @param res 背景图资源ID,背景颜色id
     */
    public void setRightBackground(int res) {
        rightClickView.setVisibility(VISIBLE);
        ivRight.setImageResource(res);
        ivRight.setVisibility(View.VISIBLE);
    }

    public void setRightTvBackground(int res)
    {
        rightClickView.setVisibility(VISIBLE);
        tvRight.setBackgroundResource(res);
        tvRight.setVisibility(View.VISIBLE);
    }


    /**
     * 描述：标题右边按钮背景色.
     *
     * @param color 背景颜色值
     */
    public void setRightBackgroundColor(int color) {
        tvRight.setBackgroundColor(color);
    }


    /**
     * 描述：设置右边按钮文本.
     *
     * @param text 文本
     */
    public void setRightText(String text) {
        rightClickView.setVisibility(VISIBLE);
        tvRight.setText(text);
        tvRight.setVisibility(View.VISIBLE);
    }


    /**
     * 描述：设置右边按钮文本.
     *
     * @param resId 文本的资源ID
     */
    public void setRightText(int resId) {
        setRightText(getResources().getString(resId));
    }


    /**
     * 描述：设置右边标题字体颜色
     */
    public void setRightTextColor(String color) {
        tvRight.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置右按钮字体大小
     *
     * @param resSize 字体大小
     */
    public void setRightTextSize(int resSize) {
        tvRight.setTextSize(TypedValue.COMPLEX_UNIT_SP, resSize);
    }


    /**
     * 描述：设置标题文本.
     *
     * @param text 文本
     */
    public void setTitleText(String text) {
        if (null == tvTitle) {
            return;
        }
        tvTitle.setText(text);
    }


    /**
     * 描述：设置标题字体颜色
     */
    public void setTitleTextColor(String color) {
        if (null == tvTitle) {
            return;
        }
        tvTitle.setTextColor(Color.parseColor(color));
    }

    /**
     * 设置标题字体大小
     *
     * @param resSize 字体大小
     */
    public void setTitleTextSize(int resSize) {
        if (null == tvTitle) {
            return;
        }
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, resSize);
    }


    public void setButtonClickListener(IButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }


}