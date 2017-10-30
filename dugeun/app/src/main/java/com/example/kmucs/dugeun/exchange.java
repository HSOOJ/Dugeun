package com.example.kmucs.dugeun;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class exchange extends AppCompatActivity {
    TextView mTextValue; //입력 받은 숫자
    TextView resultValue;//환전 후 숫자

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        //숫자 입력 하는 부분 처음에 0으로 표기
        mTextValue = (TextView) findViewById(R.id.textValue);
        mTextValue.setText("0");

        //환전 후 보여지는 부분 처음에 아무것도 표기하지 않기
        resultValue = (TextView)findViewById(R.id.result);
        resultValue.setText(null);
    }

    // 입력하는대로 출력
    public float getTextValueFloat() {
        float fValue = 0.f;
        String strText = mTextValue.getText().toString();
        fValue = Float.parseFloat(strText);
        return
                fValue;
    }

    //숫자로 바꾸기인가..?
    public void onNumberButton(String strNumber) {
        String strText = mTextValue.getText().toString();
        float fValue = getTextValueFloat();

        if (fValue == 0.f) strText = "";

        strText = strText + strNumber;
        mTextValue.setText(strText);
    }

    //버튼을 눌렀을 시
    //숫자 버튼을 눌렀을 때 해당하는 숫자 출력
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button0:
                onNumberButton("0");
                break;

            case R.id.button1:
                onNumberButton("1");
                break;

            case R.id.button2:
                onNumberButton("2");
                break;

            case R.id.button3:
                onNumberButton("3");
                break;

            case R.id.button4:
                onNumberButton("4");
                break;

            case R.id.button5:
                onNumberButton("5");
                break;

            case R.id.button6:
                onNumberButton("6");
                break;

            case R.id.button7:
                onNumberButton("7");
                break;

            case R.id.button8:
                onNumberButton("8");
                break;

            case R.id.button9:
                onNumberButton("9");
                break;

            //clear 버튼을 누르면 입력받은 숫자를 초기화하고
            // 숫자 입력하는 부분은 0으로, 환전 후 표기하는 부분은 null값으로
            case R.id.buttonClear:
                mTextValue.setText("0");
                resultValue.setText("");
                break;

            //back 버트을 누르면 입력한 숫자 하나 지워짐
            case R.id.buttonBack : {
                String strText = mTextValue.getText().toString();
                int nLength = strText.length();
                if (nLength > 0) {
                    strText = strText.substring(0, nLength - 1);
                    if (strText.length() == 0)
                        strText = "0";
                    mTextValue.setText(strText);
                }
            }
            break;

            //change 버튼을 누르면 입력받은 숫자를 해당 국가에 맞게 환전 후 출력
            // 현재는 입력받은 숫자 그대로 출력
            case R.id.buttonchange : {
                resultValue.setText(mTextValue.getText().toString());
            }
            break;

            //sever 버튼을 눌렀을 시
            case R.id.buttonSever : {
                //tet exchange rate from sever버튼을 누르면 "환율 정보가 갱신 되었습니다." 문구가 뜨게하기 - toast

            }
            break;

        }

        //13자리 입력 할 경우, "12자리까지 입력 가능합니다" 문구가 뜨게 하기 - toast
        //길이 제한
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(12);
        mTextValue.setFilters(FilterArray);
    }

    public void choose_country(View v){
        Intent intent = new Intent(getApplicationContext(), choose_country.class);
        startActivity(intent);
    }

}
