package com.example.kmucs.dugeun;

//countrymemo의 메인 자바 파일.
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;


public class countrymemo extends Activity {
    public static final String TAG = "MultiMemoActivity";

    ListView mMemoListView; // 메모 리스트뷰

    com.example.kmucs.dugeun.MemoListAdapter mMemoListAdapter; // 메모 리스트 어댑터

    int mMemoCount = 0; // 메모갯수

    public static com.example.kmucs.dugeun.db.MemoDatabase mDatabase = null; // 데이터베이스 인스턴스


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrymemo); //layout은 activity_countrymemo.

        // SD Card checking
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {             //SD카드가 없다면
            Toast.makeText(this, "SD 카드가 없습니다. SD 카드를 넣은 후 다시 실행하십시오.", Toast.LENGTH_LONG).show();
            return;
        } else {                                                                                    //SD카드가 존재 한다면
            String externalPath = Environment.getExternalStorageDirectory().getAbsolutePath();      //externalPath : 외장메모리path
            if (!com.example.kmucs.dugeun.BasicInfo.ExternalChecked && externalPath != null) {
                com.example.kmucs.dugeun.BasicInfo.ExternalPath = externalPath + File.separator;
                Log.d(TAG, "ExternalPath : " + com.example.kmucs.dugeun.BasicInfo.ExternalPath);

                com.example.kmucs.dugeun.BasicInfo.FOLDER_PHOTO = com.example.kmucs.dugeun.BasicInfo.ExternalPath + com.example.kmucs.dugeun.BasicInfo.FOLDER_PHOTO;
                com.example.kmucs.dugeun.BasicInfo.DATABASE_NAME = com.example.kmucs.dugeun.BasicInfo.ExternalPath + com.example.kmucs.dugeun.BasicInfo.DATABASE_NAME;

                com.example.kmucs.dugeun.BasicInfo.ExternalChecked = true;
            }
        }


        // 메모 리스트
        mMemoListView = (ListView)findViewById(R.id.memoList);
        mMemoListAdapter = new com.example.kmucs.dugeun.MemoListAdapter(this);
        mMemoListView.setAdapter(mMemoListAdapter);
        mMemoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                viewMemo(position);
            }
        });


        // 새 메모 버튼 설정
        Button newMemoBtn = (Button)findViewById(R.id.newMemoBtn);
        newMemoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "newMemoBtn clicked.");

                Intent intent = new Intent(getApplicationContext(), com.example.kmucs.dugeun.MemoInsertActivity.class);
                intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_MEMO_MODE, com.example.kmucs.dugeun.BasicInfo.MODE_INSERT);
                startActivityForResult(intent, com.example.kmucs.dugeun.BasicInfo.REQ_INSERT_ACTIVITY);
            }
        });

        // 닫기 버튼 설정
        Button closeBtn = (Button)findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        checkDangerousPermissions();
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    protected void onStart() {

        // 데이터베이스 열기
        openDatabase();

        // 메모 데이터 로딩
        loadMemoListData();

        super.onStart();
    }

    /**
     * 데이터베이스 열기 (데이터베이스가 없을 때는 만들기)
     */
    public void openDatabase() {
        // open database
        if (mDatabase != null) {
            mDatabase.close();
            mDatabase = null;
        }

        mDatabase = com.example.kmucs.dugeun.db.MemoDatabase.getInstance(this);
        boolean isOpen = mDatabase.open();
        if (isOpen) {
            Log.d(TAG, "Memo database is open.");
        } else {
            Log.d(TAG, "Memo database is not open.");
        }
    }

    /**
     * 메모 리스트 데이터 로딩(리스트 뷰에 기록된 메모정보를 뿌려줌)
     */
    public int loadMemoListData() {
        String SQL = "select _id, INPUT_DATE, CONTENT_TEXT, ID_PHOTO from MEMO order by INPUT_DATE desc";

        int recordCount = -1;
        if (countrymemo.mDatabase != null) {

            Cursor outCursor = countrymemo.mDatabase.rawQuery(SQL);

            recordCount = outCursor.getCount();
            Log.d(TAG, "cursor count : " + recordCount + "\n");

            mMemoListAdapter.clear();
            Resources res = getResources();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();

                String memoId = outCursor.getString(0);

                String dateStr = outCursor.getString(1);
                if (dateStr.length() > 10) {
                    dateStr = dateStr.substring(0, 10);
                }

                String memoStr = outCursor.getString(2);
                String photoId = outCursor.getString(3);
                String photoUriStr = getPhotoUriStr(photoId);

                mMemoListAdapter.addItem(new com.example.kmucs.dugeun.MemoListItem(memoId, dateStr, memoStr, photoId, photoUriStr));
            }

            outCursor.close();

            mMemoListAdapter.notifyDataSetChanged();
        }

        return recordCount;
    }

    /**
     * 사진 데이터 URI 가져오기
     */

    public String getPhotoUriStr(String id_photo) {
        String photoUriStr = null;
        if (id_photo != null && !id_photo.equals("-1")) {
            String SQL = "select URI from " + com.example.kmucs.dugeun.db.MemoDatabase.TABLE_PHOTO + " where _ID = " + id_photo + "";
            Cursor photoCursor = countrymemo.mDatabase.rawQuery(SQL);
            if (photoCursor.moveToNext()) {
                photoUriStr = photoCursor.getString(0);
            }
            photoCursor.close();
        } else if(id_photo == null || id_photo.equals("-1")) {
            photoUriStr = "";
        }

        return photoUriStr;
    }


    private void viewMemo(int position) {
        com.example.kmucs.dugeun.MemoListItem item = (com.example.kmucs.dugeun.MemoListItem)mMemoListAdapter.getItem(position);

        // 메모 보기 액티비티 띄우기
        Intent intent = new Intent(getApplicationContext(), com.example.kmucs.dugeun.MemoInsertActivity.class);
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_MEMO_MODE, com.example.kmucs.dugeun.BasicInfo.MODE_VIEW);
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_MEMO_ID, item.getId());
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_MEMO_DATE, item.getData(0));
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_MEMO_TEXT, item.getData(1));
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_ID_PHOTO, item.getData(2));
        intent.putExtra(com.example.kmucs.dugeun.BasicInfo.KEY_URI_PHOTO, item.getData(3));

        startActivityForResult(intent, com.example.kmucs.dugeun.BasicInfo.REQ_VIEW_ACTIVITY);
    }



    /**
     * 다른 액티비티의 응답 처리
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case com.example.kmucs.dugeun.BasicInfo.REQ_INSERT_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    loadMemoListData();
                    Log.d(TAG,"받는다");
                }

                break;

            case com.example.kmucs.dugeun.BasicInfo.REQ_VIEW_ACTIVITY:
                loadMemoListData();
                Log.d(TAG,"받는다233");
                break;

        }
    }


}
