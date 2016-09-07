package cn.upfinder.upfinder.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.upfinder.upfinder.R;

public class PictureActivity extends AppCompatActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_TITLE = "image_title";
    public static final String TRANSIT_PIC = "picture";

    @BindView(R.id.ivPicture)
    ImageView ivPicture;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

    private String imageUrl;
    private String imageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        parseIntent();
        //动画相关
        ViewCompat.setTransitionName(ivPicture,TRANSIT_PIC);
        Glide.with(this)
                .load(imageUrl)
                .error(R.drawable.ic_photo_loading)
                .into(ivPicture);
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL, url);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_TITLE, desc);
        return intent;
    }

    private void parseIntent() {
        imageTitle = getIntent().getStringExtra(EXTRA_IMAGE_TITLE);
        imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
    }


}
