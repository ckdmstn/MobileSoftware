package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AddActivity extends AppCompatActivity {
    ImageView img;
    int res;    // 이미지 리소스
    EditText etDate;
    EditText etWeather;
    EditText etTitle;
    EditText etContent;

    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        img = findViewById(R.id.addImg);
        etDate = findViewById(R.id.etDate);
        etWeather = findViewById(R.id.etWeather);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);

        diaryDBManager = new DiaryDBManager(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                if (etDate.getText().toString().equals("")
                        || etWeather.getText().toString().equals("")
                        || etTitle.getText().toString().equals("")
                        || etContent.getText().toString().equals("")) {
                    Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String strDate = etDate.getText().toString();
                    String strWeather = etWeather.getText().toString();
                    String strTitle = etTitle.getText().toString();
                    String strContent = etContent.getText().toString();

                    boolean isNum = false;  // 8자리 숫자인지 확인을 위한 변수
                    if (strDate.length() == 8) {    // 8자리일 경우,
                        isNum = true;
                        for (int i = 0; i < strDate.length(); i++) {
                            char tmp = strDate.charAt(i);

                            if (!('0' <= tmp && tmp <= '9')) {  // 숫자가 아닐 경우 false
                                isNum = false;
                                break;
                            }
                        }
                    }

                    if (isNum) {
                        Diary diary = new Diary(strDate, strWeather, strTitle, strContent, res);

                        if (diaryDBManager.addNewDiary(diary)) {
                            setResult(RESULT_OK);
                            Log.d("20201021", "추가 성공");
                            finish();
                        } else {
                            Log.d("20201021", "추가 실패");
                        }
                    } else {
                        Toast.makeText(this,
                                "YYYYMMDD 형식에 맞춰 8자리 숫자로 입력하세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_cancel:
                setResult(RESULT_CANCELED);
                Log.d("20201021", "추가 취소");
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 이미지 랜덤으로 생성
        Random random = new Random();
        res = setImage(random.nextInt(5));
        img.setImageResource(res);
    }

    private int setImage(int n) {     // n에 따라
        //  cb, dog, flower, heart, star
        int res = 0;
        if (n == 0) {
            res = R.mipmap.cb;
        } else if (n == 1) {
            res = R.mipmap.dog;
        } else if (n == 2) {
            res = R.mipmap.flower;
        } else if (n == 3) {
            res = R.mipmap.heart;
        } else {
            res = R.mipmap.star;
        }
        return res;
    }
}
