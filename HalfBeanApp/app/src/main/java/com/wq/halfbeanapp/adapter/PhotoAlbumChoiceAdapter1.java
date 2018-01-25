package com.wq.halfbeanapp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wq.halfbeanapp.R;
import com.wq.halfbeanapp.bean.ChoosePhotoBean;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.sdk.glide.GlideImageLoader;
import com.wq.halfbeanapp.util.sdk.glide.GlideRoundTransform;

/**
 * 自定义相册，adapter
 */
public class PhotoAlbumChoiceAdapter1 extends BaseRecyclerViewAdapter<ChoosePhotoBean> {
    private Context context;


    public PhotoAlbumChoiceAdapter1(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public int getLayoutId() {
        return R.layout.list_item_photoalbumchoice1;
    }

    @Override
    public long getItemId(int position) {
        return position + 10000;
    }

    @Override
    protected void bindData(BaseViewHolder holder, int position) {
        ChoosePhotoBean choosePhotoBean = getItem(position);
        ImageView viewImg = (ImageView) holder.getView(R.id.photoalbumchoiceitem_photo, true);
        TextView indexTv = (TextView) holder.getView(R.id.indexTv, false);
        holder.getView(R.id.indexView, true);
        //显示缩略图减少内存消耗


        if (choosePhotoBean.getThumbPath() != null && choosePhotoBean.getThumbPath().equals("ic_camera")) {
            GlideImageLoader.display(context, viewImg, R.mipmap.ic_pictures);
            indexTv.setVisibility(View.GONE);
        } else {
            Glide.with(context.getApplicationContext()).load(choosePhotoBean.getThumbPath())
                    .transform(new GlideRoundTransform(context.getApplicationContext(), 5))
                    .override(150, 150)
                    .diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).into(viewImg);
            AppLogUtil.i("自定义相册图片地址" + choosePhotoBean.getThumbPath());
            indexTv.setVisibility(View.VISIBLE);
        }


        //是否选中,显示序号
        if (choosePhotoBean.isCheck() == true) {
            indexTv.setText(choosePhotoBean.getIndex() + "");
            indexTv.setBackgroundResource(R.drawable.bg_round_red);
        } else {
            indexTv.setText("");
            indexTv.setBackgroundResource(R.mipmap.ic_unselect);
        }
    }


}
