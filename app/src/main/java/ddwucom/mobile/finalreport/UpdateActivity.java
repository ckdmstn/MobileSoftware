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

public class UpdateActivity extends AppCompatActivity {
    Diary diary;

    ImageView img;
    EditText etDate;
    EditText etWeather;
    EditText etTitle;
    EditText etContent;

    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        diary = (Diary) getIntent().getSerializableExtra("diary");

        img = findViewById(R.id.upImg);
        etDate = findViewById(R.id.etUpDate);
        etWeather = findViewById(R.id.etUpWeather);
        etTitle = findViewById(R.id.etUpTitle);
        etContent = findViewById(R.id.etUpContent);

        img.setImageResource(diary.getImaRes());
        etDate.setText(diary.getDate());
        etWeather.setText(diary.getWeather());
        etTitle.setText(diary.getTitle());
        etContent.setText(diary.getContent());

        diaryDBManager = new DiaryDBManager(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if (etDate.getText().toString().equals("")
                 || etWeather.getText().toString().equals("")
                 || etTitle.getText().toString().equals("")
                 || etContent.getText().toString().equals("")) {
                    Toast.makeText(this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    String strDate = etDate.getText().toString();
                    diary.setDate(strDate);
                    diary.setWeather(etWeather.getText().toString());
                    diary.setTitle(etTitle.getText().toString());
                    diary.setContent(etContent.getText().toString());

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
                        if (diaryDBManager.modifyDiary(diary)) {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("diary", diary);
                            setResult(RESULT_OK, resultIntent);
                            Log.d("20201021", "수정 성공");
                            finish();
                        } else {
                            setResult(RESULT_CANCELED);
                            Log.d("20201021", "수정 실패");
                        }
                    } else {
                        Toast.makeText(this,
                                "YYYYMMDD 형식에 맞춰 8자리 숫자로 입력하세요.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_Upcancel:
                setResult(RESULT_CANCELED);
                Log.d("20201021", "수정 취소");
                finish();
                break;
        }
    }
}
