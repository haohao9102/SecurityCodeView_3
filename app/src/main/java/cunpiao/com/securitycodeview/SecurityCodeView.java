package cunpiao.com.securitycodeview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/28.
 */

public class SecurityCodeView extends RelativeLayout {

    private EditText editText;
    private TextView[] textViews;
    private StringBuilder sb = new StringBuilder();
    private int count = 4;
    private String inputCount;
    private InputCompleteListener inputCompleteListener;

    public SecurityCodeView(Context context) {
        this(context,null);
    }

    public SecurityCodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SecurityCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        textViews = new TextView[4];
        View.inflate(context,R.layout.securitycodeciew_layout,this);

        editText = (EditText) findViewById(R.id.item_edittext);
        textViews[0] = (TextView) findViewById(R.id.text1);
        textViews[1] = (TextView) findViewById(R.id.text2);
        textViews[2] = (TextView) findViewById(R.id.text3);
        textViews[3] = (TextView) findViewById(R.id.text4);

        editText.setCursorVisible(false);//隐藏光标

        setListener();

    }

    private void setListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //如果字符不能为""时才能操作
                if (!s.toString().equals("")){
                    if (sb.length()>3){
                        editText.setText("");//当editText长度大于3时置为空
                        return;
                    }else{
                        sb.append(s);//将文字添加到Stringbuff中
                        editText.setText("");//添加后将EditText置空  造成没有文字输入的错局
                        count = sb.length();//记录stringbuffer的长度
                        inputCount = sb.toString();
                        if (sb.length() == 4){
                            //文字长度位4  则调用完成输入的监听
                            if (inputCompleteListener != null){
                                inputCompleteListener.inComplete();
                            }
                        }
                    }

                    for (int i = 0; i < sb.length(); i++) {
                        textViews[i].setText(String.valueOf(inputCount.charAt(i)));
                        textViews[i].setBackgroundResource(R.mipmap.bg_verify_press);
                    }
                }
            }
        });

        editText.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN){
                    if (onKeyDelete()) return true;
                    return true;
                }
                return false;
            }
        });
    }

    private boolean onKeyDelete() {
        if (count == 0){
            count = 4;
            return true;
        }
        if (sb.length()>0){
            //删除相应位置的字符
            sb.delete((count-1),count);
            count--;
            inputCount = sb.toString();
            textViews[sb.length()].setText("");
            textViews[sb.length()].setBackgroundResource(R.mipmap.bg_verify);
            if (inputCompleteListener != null){
                inputCompleteListener.deleteContent(true);//有删除就通知manger
            }
        }
        return false;
    }

    /**
     * 清空输入内容
     */
    public void clearEditText(){
        sb.delete(0,sb.length());
        inputCount = sb.toString();
        for (int i = 0; i < textViews.length; i++) {
            textViews[i].setText("");
            textViews[i].setBackgroundResource(R.mipmap.bg_verify);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public void setInputCompleteListener(InputCompleteListener inputCompleteListener){
        this.inputCompleteListener = inputCompleteListener;

    }

    public interface InputCompleteListener {
        void inComplete();

        void deleteContent(boolean isDelete);
    }

    /**
     * 获取输入文本
     * @return
     */
    public String getEditContent(){
        return inputCount;
    }
}
