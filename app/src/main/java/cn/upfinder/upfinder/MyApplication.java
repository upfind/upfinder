package cn.upfinder.upfinder;

import android.app.Application;
import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobConfig;
import cn.upfinder.upfinder.Handler.MessageHandler;

/**
 * Created by Upfinder on 2016/7/18 0018.
 */
public class MyApplication extends Application {

    private static MyApplication INSTANCE;

    private static Context context;

    public static MyApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(MyApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(MyApplication a) {
        MyApplication.INSTANCE = a;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        setInstance(this);
        //初始化
        //只有主线程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new MessageHandler(this));
        }

    }

    public static Context getContext() {
        return context;
    }

    /**
     * 获取当前运行的进程名
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /*
    * 上传文件相关配置
    * 设置文件分片上传 请求超时时间 分片大小之类的
    * */
    private void initBmomConfig() {
        BmobConfig bmobConfig = new BmobConfig.Builder(getContext())
                .setConnectTimeout(30)
                .setUploadBlockSize(500*1024)
                .build();
//        Bmob.getInstance().initConfig(bmobConfig);
    }
}
