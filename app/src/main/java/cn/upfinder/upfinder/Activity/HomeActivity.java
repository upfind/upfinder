package cn.upfinder.upfinder.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.upfinder.upfinder.Adapter.HomeVPAdapter;
import cn.upfinder.upfinder.Fragment.CountFragment;
import cn.upfinder.upfinder.Model.Bean.User;
import cn.upfinder.upfinder.Fragment.MsgFragment;
import cn.upfinder.upfinder.Fragment.NearbyFragment;
import cn.upfinder.upfinder.Presenter.CountPresenter;
import cn.upfinder.upfinder.Presenter.MsgPresenter;
import cn.upfinder.upfinder.Presenter.NearbyPresenter;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Widget.NoScrollViewPager;

public class HomeActivity extends AppCompatActivity {
    private final String TAG = HomeActivity.class.getSimpleName();

    @BindView(R.id.vpHome)
    NoScrollViewPager vpHome;
    @BindView(R.id.tlHome)
    TabLayout tlHome;

    private HomeVPAdapter adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        ButterKnife.bind(this);
        initIMConnect();
        initView();
    }

    private void initIMConnect() {
        User user = BmobUser.getCurrentUser(User.class);
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
        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_nearby).setText("附近"));
        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_find).setText("发现"));
        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_msg).setText("消息"));
        tlHome.addTab(tlHome.newTab().setIcon(R.drawable.bg_tabitem_count).setText("我"));
        ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        NearbyFragment nearbyFragment = NearbyFragment.newInstance("附近", "人");
        NearbyFragment nearbyFragment1 = NearbyFragment.newInstance("附近", "人");
        MsgFragment msgFragment = MsgFragment.newInstance();
        CountFragment countFragment = CountFragment.newInstance();

        NearbyPresenter nearbyPresenter = new NearbyPresenter(nearbyFragment);
        NearbyPresenter nearbyPresenter1 = new NearbyPresenter(nearbyFragment1);
        MsgPresenter msgPresenter = new MsgPresenter(msgFragment);
        CountPresenter countPresenter = new CountPresenter(countFragment);

        fragmentArrayList.add(nearbyFragment);
        fragmentArrayList.add(nearbyFragment1);
        fragmentArrayList.add(msgFragment);
        fragmentArrayList.add(countFragment);
        adapter = new HomeVPAdapter(getSupportFragmentManager(), fragmentArrayList);
        vpHome.setAdapter(adapter);
        //当TabLayout中的Item选项中含有图标，或者只含有图标时
        //用这个方法和ViewPager关联式TabLayout会变得什么都没有
        //TabLayout使用时的一个坑
        //tabLayout.setupWithViewPager(viewPager);

        //解决办法
        vpHome.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tlHome));
        tlHome.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vpHome));
        //关闭ViewPager的左右滑动
        vpHome.setScrollble(false);
    }
}
