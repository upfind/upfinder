package cn.upfinder.upfinder.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.upfinder.upfinder.R;

public class EditActivity extends AppCompatActivity {
    private final String TAG = EditActivity.class.getSimpleName();

    public static final String INTENT_KEY_EDIT_CONTENT = "editContent";
    public static final String RESULT_KEY_EDIT_CONTENT = "resultKey";

    private Context context;

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.commit)
    Button commit;
    @BindView(R.id.etEditContent)
    EditText etEditContent;
    @BindView(R.id.tvEditPrompt)
    TextView tvEditPrompt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    /*初始化界面*/
    private void initView() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        String editContent = intent.getStringExtra(INTENT_KEY_EDIT_CONTENT);
        etEditContent.setText(editContent);
        etEditContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                commit.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        commit.setText(getResources().getString(R.string.commit));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick({R.id.btn_back, R.id.commit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.commit:
                Intent data = new Intent();
                data.putExtra(RESULT_KEY_EDIT_CONTENT, etEditContent.getText().toString());
                setResult(RESULT_OK, data);
                finish();
                break;
        }
    }
}
