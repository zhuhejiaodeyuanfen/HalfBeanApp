package com.wq.halfbeanapp.util.file;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.wq.halfbeanapp.util.AppLogUtil;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

/**
 * 描述：文件操作类.
 */
public class AppFileUtil {


    public static boolean isFileExists(File file) {
        if (!file.exists()) {
            return false;
        }
        return true;
    }


    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        } else {
            File file = new File(filePath);
            return file.exists() && file.isFile();
        }
    }


    /**
     * 在SD卡上创建文件夹
     *
     * @throws IOException
     */
    public static File createSDDirectory(String fileName) throws IOException {
        File file = new File(fileName);
        if (!isFileExists(file))
            file.mkdirs();
        return file;
    }

    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!isFileExists(file))
            if (file.isDirectory()) {
                file.mkdirs();
            } else {
                file.createNewFile();
            }
        return file;
    }


    /**
     * 删除文件夹和文件夹里面的文件
     */
    public static void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 创建文件 注意是mkdirs==需要创建多层文件夹
     *
     * @param filePath
     * @param cacheDir
     * @return
     */
    public static File createFile(String filePath, String cacheDir) {
        File file = new File(cacheDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        String sdFilePath = cacheDir + filePath;
        file = new File(sdFilePath);
        return file;
    }


    /**
     * 将字符串写入到文本文件中
     *
     * @param strContent 写入的内容
     * @param filePath   文件路径
     * @param fileName   文件名
     */
    public static boolean writeTxtToFile(String strContent, String filePath, String fileName) {
        try {
            //生成文件夹之后，再生成文件，不然会出错
            createFile(filePath, fileName);
            String strFilePath = filePath + fileName;
            // 每次写入时，都换行写
            strContent = strContent + "\n";
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean removeFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * 描述：SD卡是否能用.
     *
     * @return true 可用,false不可用
     */
    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);

        if (!file.exists()) {
            if (file.mkdir()) {
                return true;
            } else
                return false;
        }
        return true;
    }


    /**
     * 描述：从sd卡中的文件读取到byte[].
     *
     * @param path sd卡中文件路径
     * @return byte[]
     */
    public static byte[] getByteArrayFromSD(String path) {
        byte[] bytes = null;
        ByteArrayOutputStream out = null;
        FileInputStream in = null;
        try {
            File file = new File(path);
            // SD卡是否存在
            if (!isCanUseSD()) {
                return null;
            }
            // 文件是否存在
            if (!file.exists()) {
                return null;
            }

            long fileSize = file.length();
            if (fileSize > Integer.MAX_VALUE) {
                return null;
            }
            in = new FileInputStream(path);
            out = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int size = 0;
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
            in.close();
            bytes = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }


    public static boolean isSDCardReady() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取assets目录下的txt文本
     *
     * @return
     */
    public static String readFromAssets(Context context, String txtName) {
        String text = "";
        try {
            InputStream is = context.getAssets().open(txtName);
            text = readTextFromSDcard(is);
            return text;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private static String readTextFromSDcard(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }


    /**
     * 计算文件下图片文件数量
     */
    public static int getFilesNum(String stringFile) {
        // TODO Auto-generated method stub
        int i = 0;
        File file = new File(stringFile);
        File[] files = file.listFiles();
        for (int j = 0; j < files.length; j++) {
            String name = files[j].getName();
            if (files[j].isDirectory()) {
                String dirPath = files[j].toString().toLowerCase();
                System.out.println(dirPath);
                getFilesNum(dirPath + "/");
            } else if (files[j].isFile() & name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp") || name.endsWith(".gif") || name.endsWith(".jpeg")) {
                AppLogUtil.i("FileName===" + files[j].getName());
                i++;
            }
        }

        return i;
    }


}
