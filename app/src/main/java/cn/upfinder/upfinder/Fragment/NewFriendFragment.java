package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.Activity.SearchUserActivity;
import cn.upfinder.upfinder.Adapter.NewFriendAdapter;
import cn.upfinder.upfinder.Contract.NewFriendContract;
import cn.upfinder.upfinder.Model.Bean.NewFriend;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewFriendFragment extends Fragment implements NewFriendContract.View {
    private final String TAG = NewFriendFragment.class.getSimpleName();

    @BindView(R.id.rvNewFriend)
    RecyclerView rvNewFriend;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private NewFriendContract.Presenter presenter;
    private NewFriendAdapter adapter;

    public NewFriendFragment() {
        // Required empty public constructor
    }

    public static NewFriendFragment newInstance() {
        return new NewFriendFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_friend, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        presenter.start();
    }

    private void initView() {
        tvTitle.setText(getResources().getString(R.string.new_friend));
        tvCommit.setText(getResources().getString(R.string.add_friends));
        adapter = new NewFriendAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvNewFriend.setLayoutManager(layoutManager);
        adapter.setFriendList(new ArrayList<NewFriend>());
        rvNewFriend.setAdapter(adapter);
        Log.d(TAG, "initView:初始化新好友界面 " + adapter.getItemCount());

    }

    @Override
    public void setPresenter(NewFriendContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @OnClick({R.id.ivBack, R.id.tvCommit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                getActivity().finish();
                break;
            case R.id.tvCommit:
                Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void showAllNewFriend(List<NewFriend> newFriendList) {
        adapter.setFriendList(newFriendList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }
}
