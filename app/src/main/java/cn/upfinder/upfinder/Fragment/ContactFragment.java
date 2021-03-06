package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.upfinder.upfinder.Activity.ChatActivity;
import cn.upfinder.upfinder.Adapter.ContactAdapter;
import cn.upfinder.upfinder.Adapter.OnRecyclerViewListener;
import cn.upfinder.upfinder.Contract.ContactContract;
import cn.upfinder.upfinder.Model.Bean.Contacts;
import cn.upfinder.upfinder.Model.Bean.Conversation;
import cn.upfinder.upfinder.Model.Bean.Friend;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ToastUtil;

/*联系人界面，好友列表展示*/
public class ContactFragment extends Fragment implements ContactContract.View {
    private final String TAG = ContactFragment.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rcContact)
    RecyclerView rcContact;
    @BindView(R.id.swRefresh)
    SwipeRefreshLayout swRefresh;

    private String mParam1;
    private String mParam2;

    private ContactContract.Presenter presenter;

    private ContactAdapter adapter;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView() {
        adapter = new ContactAdapter(getContext());
        adapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + adapter.getItem(position));
                //根据选中的好友常见会话 Conversation
                Contacts contacts = adapter.getItem(position);
                presenter.toChatWithFriend(contacts);
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcContact.setLayoutManager(linearLayoutManager);

        adapter.setFriendList(new ArrayList<Contacts>());
        rcContact.setAdapter(adapter);
        Log.d(TAG, "initView:初始化界面 " + adapter.getItemCount());
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.obtainContactData();
                swRefresh.setRefreshing(false);
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        presenter.start();
    }

    @Override
    public void setPresenter(ContactContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showContacts(List<Contacts> contactsList) {
        adapter.setFriendList(contactsList);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void jumpToChatActivity(Bundle bundle) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        if (bundle != null) {
            intent.putExtra(ChatActivity.INTENT_KEY_CONVERSATION, bundle);
        }
        startActivity(intent);
    }


}
