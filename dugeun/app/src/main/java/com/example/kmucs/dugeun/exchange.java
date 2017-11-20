package com.example.kmucs.dugeun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class exchange extends AppCompatActivity {
    TextView mTextValue; //입력 받은 숫자
    TextView resultValue;//환전 후 숫자
    int before_country_num = 0;
    int after_country_num = 0;

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

        //before
        //스피너 객체 생성?
        Spinner before_spinner = (Spinner)findViewById(R.id.before);
        //스피너 어댑터 설정
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Country,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        before_spinner.setAdapter(adapter);

        //스피너 이벤트 발생
        before_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                before_country_num = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //after
        //스피터 객체 생성?
        Spinner after_spinner = (Spinner)findViewById(R.id.after);

        //스피너 어댑터 설정
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,R.array.Country,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        after_spinner.setAdapter(adapter2);

        //스피너 이벤트 발생(after)
        after_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                after_country_num = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    public void sever(View v) {
        Toast.makeText(this, "환율 계산기 사이트로 넘어갑니다.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse("https://m.kr.investing.com/currency-converter/"));
        startActivity(intent);
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
            case R.id.buttonDel : {
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
                //                resultValue.setText(mTextValue.getText().toString());

                float before, after;
                String s = null;
                before = Integer.parseInt(mTextValue.getText().toString());
                switch(before_country_num) {
                    case 0:
                        switch(after_country_num) {
                            case 0:
                                s = String.valueOf(before);
                                break;

                            case 1:
                                after = before * 0.0009f;
                                s = String.valueOf(after);
                                break;

                            case 2:
                                after = before * 0.0007f;
                                s = String.valueOf(after);
                                break;

                            case 3:
                                after = before * 0.101f;
                                s = String.valueOf(after);
                                break;

                            case 4:
                                after = before * 0.006f;
                                s = String.valueOf(after);
                                break;
                        }
                        break;

                    case 1:
                        switch(after_country_num) {
                            case 0:
                                after = before * 1110f;
                                s = String.valueOf(after);
                                break;

                            case 1:
                                s = String.valueOf(before);
                                break;

                            case 2:
                                after = before * 0.84f;
                                s = String.valueOf(after);
                                break;

                            case 3:
                                after = before * 110f;
                                s = String.valueOf(after);
                                break;

                            case 4:
                                after = before * 6.6f;
                                s = String.valueOf(after);
                                break;
                        }
                        break;

                    case 2:
                        switch(after_country_num) {
                            case 0:
                                after = before * 1310f;
                                s = String.valueOf(after);
                                break;

                            case 1:
                                after = before * 1.17f;
                                s = String.valueOf(after);
                                break;

                            case 2:
                                s = String.valueOf(before);
                                break;

                            case 3:
                                after = before * 133f;
                                s = String.valueOf(after);
                                break;

                            case 4:
                                after = before * 7.82f;
                                s = String.valueOf(after);
                                break;
                        }
                        break;

                    case 3:
                        switch(after_country_num) {
                            case 0:
                                after = before * 9.83f;
                                s = String.valueOf(after);
                                break;

                            case 1:
                                after = before * 0.0088f;
                                s = String.valueOf(after);
                                break;

                            case 2:
                                after = before * 0.0075f;
                                s = String.valueOf(after);
                                break;

                            case 3:
                                s = String.valueOf(before);
                                break;

                            case 4:
                                after = before * 0.0586f;
                                s = String.valueOf(after);
                                break;
                        }
                        break;

                    case 4:
                        switch(after_country_num) {
                            case 0:
                                after = before * 167f;
                                s = String.valueOf(after);
                                break;

                            case 1:
                                after = before * 0.0015f;
                                s = String.valueOf(after);
                                break;

                            case 2:
                                after = before * 0.127f;
                                s = String.valueOf(after);
                                break;

                            case 3:
                                after = before * 17f;
                                s = String.valueOf(after);
                                break;

                            case 4:
                                s = String.valueOf(before);
                                break;
                        }
                        break;

                }


                resultValue.setText(s);

            }
            break;

//            //sever 버튼을 눌렀을 시
//            case R.id.buttonSever : {
//                //tet exchange rate from sever버튼을 누르면 "환율 정보가 갱신 되었습니다." 문구가 뜨게하기 - toast
//                Toast.makeText(this, "환율 정보가 갱신 되었습니다.", Toast.LENGTH_LONG).show();
//
//            }
//            break;
        }

        //13자리 입력 할 경우, "12자리까지 입력 가능합니다" 문구가 뜨게 하기 - toast
        //길이 제한
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(12);
        mTextValue.setFilters(FilterArray);
    }





}
