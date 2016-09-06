package cn.upfinder.upfinder.Activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import cn.upfinder.upfinder.R;
import cn.upfinder.upfinder.Utils.ToastUtil;

public class QRCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    private final String TAG = QRCodeActivity.class.getSimpleName();

    @BindView(R.id.zxingView)
    QRCodeView qrCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        qrCodeView.setDelegate(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        qrCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
    }

    @Override
    protected void onStop() {
        qrCodeView.stopCamera();
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        qrCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.d(TAG, "onScanQRCodeSuccess: " + result);
        ToastUtil.showShort(this, result);
        vibrate();
        qrCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

        Log.e(TAG, "onScanQRCodeOpenCameraError: 打开相机出错");
    }
}
