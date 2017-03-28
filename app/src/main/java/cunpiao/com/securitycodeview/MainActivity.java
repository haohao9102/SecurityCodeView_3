package cunpiao.com.securitycodeview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SecurityCodeView.InputCompleteListener {

    private SecurityCodeView editText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        editText = (SecurityCodeView) findViewById(R.id.sec_edittext);
        textView = (TextView) findViewById(R.id.tv_text);

        setListener();
    }

    private void setListener() {
        editText.setInputCompleteListener(this);
    }

    @Override
    public void inComplete() {
        Toast.makeText(this, "验证码是："+editText.getEditContent(), Toast.LENGTH_SHORT).show();
        if (!editText.getEditContent().equals("1234")){
            textView.setText("验证码输入错误");
            textView.setTextColor(Color.RED);
        }
    }

    @Override
    public void deleteContent(boolean isDelete) {
        if (isDelete){
            textView.setText("输入验证码表示同意《用户协议》");
            textView.setTextColor(Color.BLACK);
        }
    }
}
