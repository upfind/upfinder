package cn.upfinder.upfinder.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Adapter.HomeVPAdapter;
import cn.upfinder.upfinder.Fragment.CountFragment;
import cn.upfinder.upfinder.Fragment.ContactFragment;
import cn.upfinder.upfinder.Fragment.MsgFragment;
import cn.upfinder.upfinder.Fragment.NearbyFragment;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Presenter.CountPresenter;
import cn.upfinder.upfinder.Presenter.ContactPresenter;
import cn.upfinder.upfinder.Presenter.MsgPresenter;
import cn.upfinder.upfinder.Presenter.NearbyPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.IMMLeaks;
import cn.upfinder.upfinder.Utils.ToastUtil;
import cn.upfinder.upfinder.Widget.NoScrollViewPager;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.vpHome)
    NoScrollViewPager vpHome;
    @BindView(R.id.tvTopName)
    TextView tvTopName;
    @BindView(R.id.ivTopMore)
    ImageView ivTopMore;
    @BindView(R.id.ivTopSearch)
    ImageView ivTopSearch;
    @BindView(R.id.rgTabs)
    RadioGroup rgTabs;
    @BindView(R.id.rbTabNearby)
    RadioButton rbTabNearby;
    @BindView(R.id.rbTabContact)
    RadioButton rbTabContact;
    @BindView(R.id.rbTabMsg)
    RadioButton rbTabMsg;
    @BindView(R.id.rbTabMy)
    RadioButton rbTabMy;

    private HomeVPAdapter adapter;
    private Context context;

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        ButterKnife.bind(this);
        initIMConnect();
        initView();

        //监听连接状态,也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                ToastUtil.showShort(context, "" + status.getMsg());
            }
        });

        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
    }

    private void initIMConnect() {
        User user = BmobUser.getCurrentUser(this, User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.d(TAG, "done: connect success!");
                } else {
                    Log.e(TAG, "done: connect Error" + e.getErrorCode() + ":/" + e.getMessage());
                }
            }
        });

    }

    //初始化控件
    private void initView() {

        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        NearbyFragment nearbyFragment = NearbyFragment.newInstance("附近", "人");
        MsgFragment msgFragment = MsgFragment.newInstance();
        ContactFragment contactFragment = ContactFragment.newInstance("搜索", "人");
        CountFragment countFragment = CountFragment.newInstance();

        NearbyPresenter nearbyPresenter = new NearbyPresenter(nearbyFragment);
        MsgPresenter msgPresenter = new MsgPresenter(msgFragment);
        ContactPresenter contactPresenter = new ContactPresenter(this, contactFragment);
        CountPresenter countPresenter = new CountPresenter(countFragment);

        fragmentArrayList.add(nearbyFragment);
        fragmentArrayList.add(msgFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(countFragment);
        adapter = new HomeVPAdapter(getSupportFragmentManager(), fragmentArrayList);
        vpHome.setAdapter(adapter);
        vpHome.setCurrentItem(0);
        //当TabLayout中的Item选项中含有图标，或者只含有图标时
        //用这个方法和ViewPager关联式TabLayout会变得什么都没有
        //TabLayout使用时的一个坑
        //tabLayout.setupWithViewPager(viewPager);

        //解决办法
//        vpHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlHome));
//        tlHome.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpHome));
        //关闭ViewPager的左右滑动
        vpHome.setScrollble(false);

        rbTabNearby.setChecked(true);

        //监听底部radioGroup的点击
        rgTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbTabNearby:
                        vpHome.setCurrentItem(0, false);
                        break;
                    case R.id.rbTabMsg:
                        vpHome.setCurrentItem(1, false);
                        break;
                    case R.id.rbTabContact:
                        vpHome.setCurrentItem(2, false);
                        break;
                    case R.id.rbTabMy:
                        vpHome.setCurrentItem(3, false);
                        break;
                }
            }
        });

    }

    @OnClick({R.id.ivTopMore, R.id.ivTopSearch})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopMore:  //点击加号 弹出popWindow
                getPopWindow();
                //显示
                popupWindow.showAsDropDown(view, 0, -20);
                break;
            case R.id.ivTopSearch: //点击搜索 前往搜索页面搜索
                Intent intent = new Intent(context, SearchUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    /*创建popWindow
    * */
    private void initPopWindow() {
        //获取自定义布局文件
        View popView = getLayoutInflater().inflate(R.layout.pop_top_more_layout, null, false);
        TextView tvGroupChat = (TextView) popView.findViewById(R.id.tvGroupChat);
        TextView tvScan = (TextView) popView.findViewById(R.id.tvScan);
        TextView tvAddPerson = (TextView) popView.findViewById(R.id.tvAddPerson);
        TextView tvHelp = (TextView) popView.findViewById(R.id.tvHelp);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tvGroupChat:
                    case R.id.tvScan:
                        Intent intent = new Intent(context, QRCodeActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.tvAddPerson:
                    case R.id.tvHelp:
                        ToastUtil.showShort(context, "稍后完善！");
                        break;
                }
            }
        };

        tvGroupChat.setOnClickListener(listener);
        tvScan.setOnClickListener(listener);
        tvAddPerson.setOnClickListener(listener);
        tvHelp.setOnClickListener(listener);

        popupWindow = new PopupWindow(popView, 400, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setAnimationStyle(R);
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    /*
    * 获取popWindow实例
    * */
    private void getPopWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            return;
        } else {
            initPopWindow();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
    }


}
