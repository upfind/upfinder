package cn.upfinder.upfinder.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Model.UserModel;
import cn.upfinder.upfinder.R;

/**
 * 启动界面
 *
 * @author :upfinder
 * @project:SplashActivity
 * @date :2016-01-15-18:23
 */
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getSimpleName();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = this;
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = UserModel.getInstance().getLocalUser();
                if (user == null) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 1000);

    }
}
