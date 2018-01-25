package com.wq.halfbeanapp.util;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by vivianWQ on 2017/4/12
 * Mail: wangqi_vivian@sina.com
 * desc: Intent 拍照util 兼容7.0+注意fragment 不能使用getActivity,需要传值fragment
 * Version: 1.0
 */
public class AppIntentUtil {

    public static final int REQUEST_CAPTURE = 2008;
    public static final int REQUEST_PICTURE_CUT = 2009;
    public static final int REQUEST_PICK_IMAGE = 2010;


    /**
     * 使用fragment打开照相机的intent,使用fragment打开activity
     *
     * @param fragment
     * @param file
     * @return
     */
    public static Uri openCamera(Fragment fragment, File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        fragment.startActivityForResult(intent, REQUEST_CAPTURE);
        return imageUri;
    }

    /**
     * activity打开照相机的intent
     *
     * @param activity
     * @param file
     * @return
     */
    public static Uri openCamera(Activity activity, File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        activity.startActivityForResult(intent, REQUEST_CAPTURE);
        return imageUri;
    }


    /**
     * fragment调用系统的裁剪程序,fragment.startActivityForResult
     *
     * @param fragment
     * @param file
     * @param imageUri
     */
    public static void cropPhoto(Fragment fragment, File file, Uri imageUri) {
        Uri outputUri = Uri.fromFile(file);//缩略图保存地址
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        fragment.startActivityForResult(intent, REQUEST_PICTURE_CUT);
    }


    /**
     * 7.0以下获取图片地址的方法
     *
     * @param uri
     * @param selection
     * @param context
     * @return
     */
    private static String getImagePath(Uri uri, String selection, Context context) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    /**
     * 适用于7.0以下及以上的裁剪intent,获取data的图片地址
     *
     * @param data
     * @param fragment
     * @return
     */
    public static File handleImage(Intent data, Fragment fragment) {
        String imagePath = null;
        File file;
        Uri imageUri = data.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(fragment.getActivity(), imageUri)) {
                //如果是document类型的uri,则通过document id处理
                String docId = DocumentsContract.getDocumentId(imageUri);
                if ("com.android.providers.media.documents".equals(imageUri.getAuthority())) {
                    String id = docId.split(":")[1];//解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, fragment.getActivity());
                } else if ("com.android.downloads.documents".equals(imageUri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                    imagePath = getImagePath(contentUri, null, fragment.getActivity());
                }
            } else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
                //如果是content类型的Uri，则使用普通方式处理
                imagePath = getImagePath(imageUri, null, fragment.getActivity());
            } else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
                //如果是file类型的Uri,直接获取图片路径即可
                imagePath = imageUri.getPath();
            }

        } else {
            imagePath = getImagePath(imageUri, null, fragment.getActivity());
        }
        file = new File(imagePath);
        cropPhoto(fragment, file, imageUri);
        return file;
    }


    /**
     * 调用系统相册命令 从相册选择图片
     *
     * @param fragment
     */
    public static void selectFromAlbum(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        fragment.startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }
}
