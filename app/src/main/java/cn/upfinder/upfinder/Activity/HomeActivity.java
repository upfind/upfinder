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
    //    @BindView(R.id.tlHome)
//    TabLayout tlHome;
    @BindView(R.id.tvTopName)
    TextView tvTopName;
    @BindView(R.id.ivTopMore)
    ImageView ivTopMore;
    @BindView(R.id.ivTopSearch)
    ImageView ivTopSearch;
    @BindView(R.id.tvBadgeCountNearby)
    TextView tvBadgeCountNearby;
    @BindView(R.id.ivBadgeDotNearby)
    ImageView ivBadgeDotNearby;
    @BindView(R.id.rlTabNearby)
    RelativeLayout rlTabNearby;
    @BindView(R.id.tvBadgeCountFind)
    TextView tvBadgeCountFind;
    @BindView(R.id.ivBadgeDotFind)
    ImageView ivBadgeDotFind;
    @BindView(R.id.rlTabFind)
    RelativeLayout rlTabFind;
    @BindView(R.id.tvBadgeCountMsg)
    TextView tvBadgeCountMsg;
    @BindView(R.id.ivBadgeDotMsg)
    ImageView ivBadgeDotMsg;
    @BindView(R.id.rlTabMsg)
    RelativeLayout rlTabMsg;
    @BindView(R.id.tvBadgeCountMy)
    TextView tvBadgeCountMy;
    @BindView(R.id.ivBadgeDotMy)
    ImageView ivBadgeDotMy;
    @BindView(R.id.rlTabMy)
    RelativeLayout rlTabMy;
    @BindView(R.id.rgTabs)
    RadioGroup rgTabs;
    @BindView(R.id.rbTabNearby)
    RadioButton rbTabNearby;
    @BindView(R.id.rbTabFind)
    RadioButton rbTabFind;
    @BindView(R.id.rbTabMsg)
    RadioButton rbTabMsg;
    @BindView(R.id.rbTabMy)
    RadioButton rbTabMy;

    private HomeVPAdapter adapter;
    private Context context;

    private PopupWindow popupWindow;
    private ViewPager.OnPageChangeListener mainViewPagerOnPageChangeListener;

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
//        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_nearby).setText("附近"));
//        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_find).setText("发现"));
//        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_msg).setText("消息"));
//        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_count).setText("我"));
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        NearbyFragment nearbyFragment = NearbyFragment.newInstance("附近", "人");
        ContactFragment contactFragment = ContactFragment.newInstance("搜索", "人");
        MsgFragment msgFragment = MsgFragment.newInstance();
        CountFragment countFragment = CountFragment.newInstance();

        NearbyPresenter nearbyPresenter = new NearbyPresenter(nearbyFragment);
        ContactPresenter contactPresenter = new ContactPresenter(this, contactFragment);
        MsgPresenter msgPresenter = new MsgPresenter(msgFragment);
        CountPresenter countPresenter = new CountPresenter(countFragment);

        fragmentArrayList.add(nearbyFragment);
        fragmentArrayList.add(contactFragment);
        fragmentArrayList.add(msgFragment);
        fragmentArrayList.add(countFragment);
        adapter = new HomeVPAdapter(getSupportFragmentManager(), fragmentArrayList);
        vpHome.setAdapter(adapter);
        //当TabLayout中的Item选项中含有图标，或者只含有图标时
        //用这个方法和ViewPager关联式TabLayout会变得什么都没有
        //TabLayout使用时的一个坑
        //tabLayout.setupWithViewPager(viewPager);

        //解决办法
//        vpHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlHome));
//        tlHome.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpHome));
        //关闭ViewPager的左右滑动
        vpHome.setScrollble(false);

        mainViewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //view pager切换也自动切换Tab状态
                int currentCheckedId = rgTabs.getCheckedRadioButtonId();
                int targetCheckId = getTabsCheckedButtonIdFromViewPagerIndex(position, currentCheckedId);
                if (targetCheckId != currentCheckedId) {

                }

                if (position == adapter.FRAGMENT_INDEX_SECOND) {
                    //第二个Tab, 刷第二页面里的badge number

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
    }

    @OnClick({R.id.ivTopMore, R.id.ivTopSearch, R.id.rlTabNearby, R.id.rlTabFind, R.id.rlTabMsg, R.id.rlTabMy, R.id.rbTabNearby, R.id.rbTabFind, R.id.rbTabMsg, R.id.rbTabMy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivTopMore:  //点击加号 弹出popWindow
                getPopWindow();
                //显示
                popupWindow.showAsDropDown(view, -10, 20);
                break;
            case R.id.ivTopSearch: //点击搜索 前往搜索页面搜索
                Intent intent = new Intent(context, SearchUserActivity.class);
                startActivity(intent);
                break;

            case R.id.rlTabNearby:
                break;
            case R.id.rlTabFind:
                break;
            case R.id.rlTabMsg:
                break;
            case R.id.rlTabMy:
                break;

            case R.id.rbTabNearby:
                onClickTab(view);
                break;
            case R.id.rbTabFind:
                onClickTab(view);
                break;
            case R.id.rbTabMsg:
                onClickTab(view);
                break;
            case R.id.rbTabMy:
                onClickTab(view);
                break;
        }
    }


    //tab点击处理
    private void onClickTab(View view) {

        int currentIndex = vpHome.getCurrentItem();
        int targetIndex = getViewPagerIndexFromTabsCheckedButtonId(view.getId(), currentIndex);
        if (targetIndex != currentIndex) {
            rgTabs.check(view.getId());
            vpHome.setCurrentItem(targetIndex, false);
        }
    }

    private int getViewPagerIndexFromTabsCheckedButtonId(int checkedId, int defaultIndex) {
        int targetIndex;
        switch (checkedId) {
            case R.id.rbTabNearby:
                targetIndex = adapter.FRAGMENT_INDEX_FIRST;
                break;
            case R.id.rbTabFind:
                targetIndex = adapter.FRAGMENT_INDEX_SECOND;
                break;
            case R.id.rbTabMsg:
                targetIndex = adapter.FRAGMENT_INDEX_THIRD;
                break;
            case R.id.rbTabMy:
                targetIndex = adapter.FRAGMENT_INDEX_FORE;
                break;
            default:
                targetIndex = defaultIndex;
                break;
        }
        return targetIndex;
    }

    private int getTabsCheckedButtonIdFromViewPagerIndex(int index, int defaultCheckedId) {
        int targetCheckedId;
        switch (index) {
            case HomeVPAdapter.FRAGMENT_INDEX_FIRST:
                targetCheckedId = R.id.rbTabNearby;
                break;
            case HomeVPAdapter.FRAGMENT_INDEX_SECOND:
                targetCheckedId = R.id.rbTabFind;
                break;
            case HomeVPAdapter.FRAGMENT_INDEX_THIRD:
                targetCheckedId = R.id.rbTabMsg;
                break;
            case HomeVPAdapter.FRAGMENT_INDEX_FORE:
                targetCheckedId = R.id.rbTabMy;
                break;
            default:
                targetCheckedId = defaultCheckedId;
                break;
        }
        return targetCheckedId;
    }

    /*创建popWindow
    * */
    private void initPopWindow() {
        //获取自定义布局文件
        View popView = getLayoutInflater().inflate(R.layout.pop_top_more_layout, null, false);
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
