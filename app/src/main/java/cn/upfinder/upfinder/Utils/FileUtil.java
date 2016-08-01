package cn.upfinder.upfinder.Utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Upfinder on 2015/12/24.
 */
public class FileUtil {
    private String SDPATH = null;

    public String getSDPATH() {
        return SDPATH;
    }

    /**
     * 判断外部存储卡是否可用
     * @return
     */
    public static boolean isExternalStorageAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        }
        return false;
    }

    public FileUtil() {
        //获得当前外部存储设备SD卡的目录
        SDPATH = Environment.getExternalStorageDirectory() + "/";
    }

    //创建文件
    public File createFile(String fileName) throws IOException {
        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    //创建目录
    public File createDir(String fileName) throws IOException {
        File dir = new File(SDPATH + fileName);
        dir.mkdir();
        return dir;
    }

    //判断文件是否存在
    public boolean isExist(String fileName) {
        File file = new File(SDPATH + fileName);
        return file.exists();
    }

    public File writeToSDPATHFromInput(String path, String fileName, InputStream inputstream) {
        File file = null;
        OutputStream outputstream = null;
        //已经读取的长度
        int hasRead = 0;
        //读取到的数据长度
        int len;
        try {
            createDir(path);
            file = createFile(path + fileName);
            outputstream = new FileOutputStream(file);
            //1K的数据缓冲
            byte buffer[] = new byte[128];
            //将输入流中的内容先输入到buffer中缓存，然后用输出流写到文件中
            while ((len = inputstream.read(buffer)) != -1) {
                outputstream.write(buffer);
                //记录完成了多少
                hasRead +=len;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭所有连接
                outputstream.close();
                inputstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


    /**
     * 获取磁盘缓存文件
     * @param context
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
