package com.xinrui.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_test) TextView tv_test;

    @BindView(R.id.pic_test) ImageView pic_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.btn_test,R.id.btn_test2})
    public void OnClick(View view){
        switch(view.getId()){
            case R.id.btn_test:
                Toast.makeText(this,"和哦哦",Toast.LENGTH_LONG).show();
                createQrCode();
                break;

            case R.id.btn_test2:
                scanQrCode();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult=IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult!=null){
           if(intentResult.getContents()==null){
               Toast.makeText(MainActivity.this,"内容为空",Toast.LENGTH_LONG).show();
           }else{
               Toast.makeText(MainActivity.this,"扫描成功",Toast.LENGTH_LONG).show();
               String content=intentResult.getContents();
               tv_test.setText(content);
           }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    /**生成二维码*/
    private void createQrCode(){
        Bitmap bitmap = ZXingUtils.createQRImage("你好", pic_test.getWidth(), pic_test.getHeight());
        pic_test.setImageBitmap(bitmap);
    }
    /**扫描二维码*/
    public void scanQrCode(){
        new IntentIntegrator(this)
                .setOrientationLocked(true)
                .setCaptureActivity(ScanActivity.class)
                .initiateScan();
    }
}
