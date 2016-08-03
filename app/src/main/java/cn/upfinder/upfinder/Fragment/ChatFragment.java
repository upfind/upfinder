package cn.upfinder.upfinder.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.upfinder.upfinder.Activity.PhotoPickerActivity;
import cn.upfinder.upfinder.Adapter.ChatAdapter;
import cn.upfinder.upfinder.Contract.ChatContract;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.OtherUtil;
import cn.upfinder.upfinder.Utils.ToastUtil;


public class ChatFragment extends Fragment implements ChatContract.View {
    private final String TAG = ChatFragment.class.getSimpleName();

    private static final int PICK_PHOTO = 11;

    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    @BindView(R.id.rvChat)
    RecyclerView rvChat;
    @BindView(R.id.swRefresh)
    SwipeRefreshLayout swRefresh;
    @BindView(R.id.btnChatAdd)
    Button btnChatAdd;
    @BindView(R.id.btn_chat_emo)
    Button btnChatEmo;
    @BindView(R.id.etChatContent)
    EditText etChatContent;
    @BindView(R.id.btn_speak)
    Button btnSpeak;
    @BindView(R.id.btnChatVoice)
    Button btnChatVoice;
    @BindView(R.id.btnChatKeyboard)
    Button btnChatKeyboard;
    @BindView(R.id.btnChatSend)
    Button btnChatSend;
    @BindView(R.id.llChat)
    LinearLayout llChat;
    @BindView(R.id.vpEmo)
    ViewPager vpEmo;
    @BindView(R.id.tvAddPicture)
    TextView tvAddPicture;
    @BindView(R.id.tvAddCamera)
    TextView tvAddCamera;
    @BindView(R.id.tvAddLocation)
    TextView tvAddLocation;
    @BindView(R.id.llMore)
    LinearLayout llMore;
    @BindView(R.id.layoutAdd)
    LinearLayout llAdd;

    private ChatContract.Presenter presenter;

    private ChatAdapter adapter;
    private LinearLayoutManager layoutManager;

    public ChatFragment() {

    }


    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.start();
    }


    @Override
    public void setPresenter(ChatContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void onResume() {
        //添加页面新消息监听
        presenter.addMsgListener();
        super.onResume();
    }


    @Override
    public void onPause() {
        //移除页面消息监听器
        presenter.removeMsgListener();
        super.onPause();
    }


    @Override
    public void onDestroy() {

        presenter.readAllMsg();
        super.onDestroy();

    }

    @OnClick({R.id.ivBack, R.id.tvTitle, R.id.tvCommit, R.id.btnChatAdd, R.id.btn_chat_emo, R.id.btn_speak, R.id.btnChatVoice, R.id.btnChatKeyboard, R.id.btnChatSend, R.id.tvAddPicture, R.id.tvAddCamera, R.id.tvAddLocation})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                onBack();
                break;
            case R.id.tvTitle:
                break;
            case R.id.tvCommit:
                toUserInfoActivity();
                break;
            case R.id.btnChatAdd: //发送其他类型消息
                onAddClick();
                break;
            case R.id.btn_chat_emo:
                break;
            case R.id.btn_speak:
                break;
            case R.id.btnChatVoice:
                break;
            case R.id.btnChatKeyboard:
                break;
            case R.id.btnChatSend:
                presenter.sendTextMsg(etChatContent.getText().toString());
                etChatContent.setText("");
                break;
            case R.id.tvAddPicture: //选择图片
                Log.d(TAG, "onClick: 选择照片");
                onChoiceSendImg();
                break;
            case R.id.tvAddCamera: //拍照
                Log.d(TAG, "onClick: 拍照");
                break;
            case R.id.tvAddLocation: //发送位置
                Log.d(TAG, "onClick: 发送位置");
                break;
        }
    }

    /*
    * 跳转到图片选择页选取图片*/
    private void onChoiceSendImg() {

        //定义选择照片模式为多选
        int selectedMode = PhotoPickerActivity.MODE_MULTI;
        //设置显示相机
        boolean showCamera = false;
        //选择最大照片张数默认，也可不设置
        int maxNum = PhotoPickerActivity.DEFAULT_NUM;
        Intent intent = new Intent(getActivity(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, showCamera);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, selectedMode);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
        startActivityForResult(intent, PICK_PHOTO);
    }

    //当点击添加发送其他类型消息按钮时，对界面的一些操作
    private void onAddClick() {
        if (llMore.getVisibility() == View.GONE) {
            llMore.setVisibility(View.VISIBLE);
            llAdd.setVisibility(View.VISIBLE);
            vpEmo.setVisibility(View.GONE);
            //隐藏键盘
            OtherUtil.hideSoftInputView(getActivity());
        } else {
            if (vpEmo.getVisibility() == View.VISIBLE) {
                vpEmo.setVisibility(View.GONE);
                llAdd.setVisibility(View.VISIBLE);
            } else {
                llMore.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void initView(BmobIMConversation conversation) {
        Log.d(TAG, "initView: ");
        tvTitle.setText(conversation.getConversationTitle());
        swRefresh.setEnabled(true);
        layoutManager = new LinearLayoutManager(getContext());
        rvChat.setLayoutManager(layoutManager);
        adapter = new ChatAdapter(getContext(), conversation);
        rvChat.setAdapter(adapter);
        llChat.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llChat.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                swRefresh.setRefreshing(true);
                //自动刷新
                presenter.queryMessages(null);
            }
        });

        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BmobIMMessage msg = adapter.getFirstMessage();
                presenter.queryMessages(msg);
            }
        });

        //初始化底部编辑消息布局
        etChatContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                    scrollToBottom();
                }
                return false;
            }
        });

        etChatContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                scrollToBottom();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(s)) {
                    btnChatSend.setVisibility(View.VISIBLE);
                    btnChatKeyboard.setVisibility(View.GONE);
                    btnChatVoice.setVisibility(View.GONE);
                } else {
                    if (btnChatVoice.getVisibility() != View.VISIBLE) {
                        btnChatVoice.setVisibility(View.VISIBLE);
                        btnChatSend.setVisibility(View.GONE);
                        btnChatKeyboard.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBack() {

        getActivity().finish();
    }

    @Override
    public void showMessages(List<BmobIMMessage> messages) {
        Log.d(TAG, "showMessages: " + messages.size());
        adapter.addMessages(messages);
    }

    @Override
    public void toUserInfoActivity() {

    }

    @Override
    public void hideRefreshing() {
        swRefresh.setRefreshing(false);
    }

    @Override
    public void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(adapter.getItemCount() - 1, 0);
    }

    @Override
    public void showToast(String msg) {
        ToastUtil.showShort(getContext(), msg);
    }

    @Override
    public void addMsg(BmobIMMessage message) {
        if (adapter.findPosition(message) < 0) { //未添加到聊天界面
            adapter.addMessage(message);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == getActivity().RESULT_OK) { // 成功选择多张图片
            ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT); //获得图片路径
            for (String filePath : result) {
                //执行发送
                presenter.sendLocalImgMsg(filePath);
                Log.d(TAG, "onActivityResult: " + filePath);
            }

        }
    }
}
