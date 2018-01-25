package com.wq.halfbeanapp.util;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.wq.halfbeanapp.bean.ChoosePhotoBean;
import com.wq.halfbeanapp.bean.photo.IBaseModel;
import com.wq.halfbeanapp.bean.photo.IBaseView;
import com.wq.halfbeanapp.bean.photo.LocalFileBean;
import com.wq.halfbeanapp.bean.photo.ThumbImgFileBean;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义相册，选取多张
 * Created by yangqin on 2017/3/7.
 */

public class PhotoAlbumChoicePresenter {




    /**
     * 获取相册图片列表
     *
     * @param context
     * @param iBaseView
     */
    public void getColumnData(final Context context, final IBaseView<ArrayList<ChoosePhotoBean>> iBaseView) {
        new AsyncTask<String, Integer, ArrayList<ChoosePhotoBean>>() {

            @Override
            protected ArrayList<ChoosePhotoBean> doInBackground(String... params) {

                //先获取所有的缩略图
                List<ThumbImgFileBean> thumbnailList = getThumbnailList(context);
                //在去取所有大图
                ArrayList<ChoosePhotoBean> List = new ArrayList<>();
                HashMap<Integer, String> localPicMap = new HashMap<Integer, String>();
                String[] projection = {MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media._ID};
                Cursor cur = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  // 大图URI
                        projection,   // 字段
                        null,         // No where clause
                        null,         // No where clause
                        MediaStore.Images.Media.DATE_ADDED + " DESC"); //根据时间升序
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    int _id;
                    String image_path;

                    int _idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails._ID);
                    int dataColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
                    _id = cur.getInt(_idColumn);//大图ID
                    image_path = cur.getString(dataColumn);//大图路径
                    File file = new File(image_path);
                    //判断大图是否存在,去掉特殊情况图片路径是文件夹不是文件,并且去掉gif图片
                    if (file.exists() && !file.isDirectory() && !image_path.contains(".gif")) {
                        localPicMap.put(cur.getPosition(), image_path);
                        //缩略图URI
                        String thumbUri = "";
                        if (thumbnailList != null) {
                            for (ThumbImgFileBean thumbBean : thumbnailList) {
                                if (thumbBean.getId() == _id) {//找到了缩略图
                                    file = new File(thumbBean.getThumbPath());
                                    if (file.exists()) {//缩略图文件确实存在
                                        thumbUri = thumbBean.getThumbPath();
                                    } else {
                                        //不存在那么就用大图地址做缩略图地址
                                        thumbUri = image_path;
                                    }
                                    break;
                                }
                            }
                            //如果没有找到那么就用大图地址做缩略图地址
                            if (AppStrUtil.isEmpty(thumbUri))
                                thumbUri = image_path;
                        } else {
                            thumbUri = image_path;
                        }
                        ChoosePhotoBean bean = new ChoosePhotoBean();
                        bean.setId(_id);
                        bean.setImgUrl(image_path);
                        bean.setThumbPath(thumbUri);
                        List.add(bean);
                    }
                }
                cur.close();
                return List;
            }

            @Override
            protected void onPostExecute(ArrayList<ChoosePhotoBean> list) {
                super.onPostExecute(list);
                iBaseView.succeed(list);
            }
        }.execute();
    }


    /**
     * 获取相册图片列表
     * 注;去掉了gif图，去掉特殊文件夹形式保存图，去掉了找不到原图的图
     *
     * @param context
     * @param iBaseModel
     */
    public void getColumnData(final Context context, final IBaseModel<ArrayList<ChoosePhotoBean>> iBaseModel) {

    }

    //大图遍历字段
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.ORIENTATION
    };
    //小图遍历字段
    private static final String[] THUMBNAIL_STORE_IMAGE = {
            MediaStore.Images.Thumbnails._ID,
            MediaStore.Images.Thumbnails.DATA,
            MediaStore.Images.Thumbnails.IMAGE_ID
    };

    /**
     * 获取图片分组列表
     *
     * @param context
     * @param iBaseModel
     */
    public void getPhotosList(final Context context, final IBaseModel<Map<String, List<LocalFileBean>>> iBaseModel) {
        new AsyncTask<String, Integer, Map<String, List<LocalFileBean>>>() {
            @Override
            protected Map<String, List<LocalFileBean>> doInBackground(String... params) {
                List<LocalFileBean> paths = new ArrayList<>();
                Map<String, List<LocalFileBean>> folders = new HashMap<>();
                //获取大图的游标
                Cursor cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  // 大图URI
                        STORE_IMAGES,   // 字段
                        null,         // No where clause
                        null,         // No where clause
                        MediaStore.Images.Media.DATE_TAKEN + " DESC"); //根据时间升序
                if (cursor == null)
                    return null;
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);//大图ID
                    String path = cursor.getString(1);//大图路径
                    File file = new File(path);
                    //判断大图是否存在
                    if (file.exists()) {
                        //小图URI
                        String thumbUri = getThumbnail(id, context);
                        //获取大图URI
                        String uri = path;
                        if (AppStrUtil.isEmpty(uri))
                            continue;
                        if (AppStrUtil.isEmpty(thumbUri))
                            thumbUri = uri;
                        //获取目录名
                        String folder = file.getParentFile().getName();

                        LocalFileBean localFile = new LocalFileBean();
                        localFile.setOriginalUri(uri);
                        localFile.setThumbnailUri(thumbUri);
                        int degree = cursor.getInt(2);
                        if (degree != 0) {
                            degree = degree + 180;
                        }
                        localFile.setOrientation(360 - degree);

                        paths.add(localFile);
                        //判断文件夹是否已经存在
                        if (folders.containsKey(folder)) {
                            folders.get(folder).add(localFile);
                        } else {
                            List<LocalFileBean> files = new ArrayList<>();
                            files.add(localFile);
                            folders.put(folder, files);
                        }
                    }
                }
                folders.put("所有图片", paths);
                cursor.close();
                return folders;
            }

            @Override
            protected void onPostExecute(Map<String, List<LocalFileBean>> stringListMap) {
                super.onPostExecute(stringListMap);
                if (stringListMap != null) {
                    iBaseModel.succeed(stringListMap);
                }
            }
        }.execute();
    }

    /**
     * 获取缩略图
     *
     * @param id
     * @param context
     * @return
     */
    private String getThumbnail(int id, Context context) {
        //获取大图的缩略图
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                THUMBNAIL_STORE_IMAGE,
                MediaStore.Images.Thumbnails.IMAGE_ID + " = ?",
                new String[]{id + ""},
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int thumId = cursor.getInt(0);
            String uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.buildUpon().
                    appendPath(Integer.toString(thumId)).build().toString();
            cursor.close();
            return uri;
        }
        cursor.close();
        return null;
    }

    /**
     * 获取所有的缩略图
     *
     * @param context
     * @return
     */
    private List<ThumbImgFileBean> getThumbnailList(Context context) {
        //获取大图的缩略图
        Cursor cur = context.getContentResolver().query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, THUMBNAIL_STORE_IMAGE, null, null, null);
        List<ThumbImgFileBean> thumbList = new ArrayList<>();
        for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
            ThumbImgFileBean thumbBean = new ThumbImgFileBean();
            int _id;
            String thumb_path;
            int _idColumn = cur.getColumnIndex(MediaStore.Images.Thumbnails.IMAGE_ID);
            _id = cur.getInt(_idColumn);
            int thumId = cur.getInt(0);
            thumb_path = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI.buildUpon().
                    appendPath(Integer.toString(thumId)).build().toString();
            thumbBean.setId(_id);
            thumbBean.setThumbPath(thumb_path);
            thumbList.add(thumbBean);
        }
        cur.close();
        return thumbList;
    }

    /**
     * 将list的数据倒过来，因数据需要时间的倒序显示
     *
     * @param arraylist
     */
    private ArrayList<HashMap<String, String>> inverted_list(
            ArrayList<HashMap<String, String>> arraylist) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        for (int i = arraylist.size() - 1; i >= 0; i--) {
            list.add(arraylist.get(i));
        }
        return list;
    }



}
