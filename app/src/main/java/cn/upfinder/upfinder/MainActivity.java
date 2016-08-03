package cn.upfinder.upfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.upfinder.upfinder.Activity.LoginActivity;
import cn.upfinder.upfinder.Model.Bean.Person;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.tvShowPerson)
    TextView tvShowPerson;
    @BindView(R.id.btnGetPerson)
    Button btnGetPerson;
    @BindView(R.id.btnDelPerson)
    Button btnDelPerson;
    @BindView(R.id.btnAddPerson)
    Button btnAddPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initBmobSdk();
    }

//    @OnClick({R.id.btnDelPerson, R.id.btnAddPerson})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btnDelPerson:
//                Person person = new Person();
//                person.setObjectId("f14b986505");
//                person.delete(new UpdateListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        if (e == null) {
//                            Log.d(TAG, "done: 删除成功");
//                        } else {
//                            Log.e(TAG, "done: 删除失败");
//                        }
//                    }
//                });
//
//                break;
//            case R.id.btnAddPerson:
//                Person person2 = new Person();
//                person2.setObjectId("f14b986505");
//                person2.setName("xiaosi");
//                person2.setAddress("zhengzhou");
//                person2.save(new SaveListener<String>() {
//                    @Override
//                    public void done(String s, BmobException e) {
//                        if (e == null) {
//                            Log.d(TAG, "done: " + s);
//                        } else {
//                            Log.d(TAG, "done: " + e.getMessage());
//                        }
//                    }
//                });
//                break;
//        }
//    }


//    @OnClick(R.id.btnGetPerson)
//    void getPerson(View view) {
//        BmobQuery<Person> bmobQuery = new BmobQuery<>();
//        bmobQuery.getObject("f14b986505", new QueryListener<Person>() {
//            @Override
//            public void done(Person person, BmobException e) {
//
//                if (e == null) {
//                    Log.d(TAG, "done: " + person.getName() + "地址：" + person.getAddress());
//                    tvShowPerson.setText(person.getName() + "地址：" + person.getAddress() + person.getObjectId());
//                } else {
//
//                    Log.e(TAG, "done: 错误码：" + e.getErrorCode() + "错误信息:" + e.getMessage());
//                }
//            }
//        });
//    }

//    @OnClick(R.id.btnSetPerson)
//    void setPerson(View view) {
//        Person person = new Person();
//        person.setAddress("河南郑州");
//        person.update("f14b986505", new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    Log.d(TAG, "done: 修改成功");
//                } else {
//                    Log.e(TAG, "done: 修改失败");
//                }
//            }
//        });
//    }


    //初始化BmobSdk
    private void initBmobSdk() {
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化 第二个参数为 Application Id
        Bmob.initialize(this, "1c423bf9e2f10e66afbf40fb98f077f3");


        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }


}
