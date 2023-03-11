package ddwucom.mobile.finalreport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    EditText etDate;
    ArrayList<Diary> diaryList = null;

    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        diaryList = (ArrayList<Diary>) getIntent().getSerializableExtra("diaryList");
        etDate = findViewById(R.id.searchDate);

        diaryDBManager = new DiaryDBManager(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String strDate = etDate.getText().toString();

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
                    // 해당 날짜인 다이어리 순서 (position) 반환
                    int position = diaryDBManager.getDairyByDate(strDate);
                    if (position == -1) {
                        setResult(RESULT_CANCELED);
                    } else {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("position", position);
                        setResult(RESULT_OK, resultIntent);
                    }
                    finish();
                } else {
                    Toast.makeText(this,
                            "YYYYMMDD 형식에 맞춰 8자리 숫자로 다시 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_searchCancel:
                setResult(RESULT_CANCELED);
                Log.d("20201021", "검색 취소");
                finish();
                break;
        }
    }
}
