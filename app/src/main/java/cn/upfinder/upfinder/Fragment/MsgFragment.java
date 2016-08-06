package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.upfinder.upfinder.Activity.ChatActivity;
import cn.upfinder.upfinder.Activity.SearchUserActivity;
import cn.upfinder.upfinder.Adapter.ConversationAdapter;
import cn.upfinder.upfinder.Adapter.OnRecyclerViewListener;
import cn.upfinder.upfinder.Contract.MsgContract;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.PrivateConversation;
import cn.upfinder.upfinder.R;


public class MsgFragment extends Fragment implements MsgContract.View {
    private final String TAG = MsgFragment.class.getSimpleName();

    @BindView(R.id.rvConversation)
    RecyclerView rvConversation;
    @BindView(R.id.ivAlert)
    ImageView ivAlert;
    @BindView(R.id.tvAlert)
    TextView tvAlert;
    @BindView(R.id.rlAlert)
    RelativeLayout rlAlert;
    @BindView(R.id.pbProgress)
    ContentLoadingProgressBar pbProgress;
    @BindView(R.id.swRefresh)
    SwipeRefreshLayout swRefresh;

    private MsgContract.Precenter precenter;

    private ConversationAdapter conversationAdapter;
    private List<Conversation> conversations;

    public MsgFragment() {
        // Required empty public constructor
    }


    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        initView();
        precenter.start();
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        conversations = new ArrayList<>();
        conversationAdapter = new ConversationAdapter(getActivity(), conversations);
        conversationAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + conversationAdapter.getItem(position));
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ChatActivity.INTENT_KEY_CONVERSATION, conversationAdapter.getItem(position));
                intent.putExtra(ChatActivity.INTENT_KEY_CONVERSATION, bundle);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(int position) {
                //删除服务器
                PrivateConversation conversation = (PrivateConversation) conversationAdapter.getItem(position);
                BmobIMConversation bmobIMConversation = conversation.getConversation();
                BmobIM.getInstance().deleteConversation(bmobIMConversation);
                //删除界面
                conversationAdapter.removeItem(position);
                return true;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvConversation.setLayoutManager(linearLayoutManager);
        //如果可以确定每个Item的高度，这个设置能提高性能
//        rvConversation.setHasFixedSize(true);
        rvConversation.setAdapter(conversationAdapter);

        //初始化下拉刷新
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "onRefresh: 正在刷新");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        precenter.start();
    }

    @Override
    public void setPresenter(MsgContract.Precenter presenter) {

        this.precenter = presenter;
    }

    @Override
    public void showProgress() {
        pbProgress.show();
    }

    @Override
    public void hideProgress() {
        pbProgress.hide();
    }

    @Override
    public void showAddConversation() {

        rvConversation.setVisibility(View.GONE);
        rlAlert.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConversations(List<Conversation> conversations) {
        rlAlert.setVisibility(View.GONE);
        rvConversation.setVisibility(View.VISIBLE);
        conversationAdapter.refreshData(conversations);
    }

    @OnClick({R.id.ivAlert, R.id.tvAlert})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAlert:
                Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                startActivity(intent);
                break;
            case R.id.tvAlert:
                break;
        }
    }
}
