package ddwucom.mobile.finalreport;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int REQ_CODE = 100;
    final int UPDATE_CODE = 200;
    final int SEARCH_CODE = 300;

    ListView listView;
    MyAdapter adapter;
    ArrayList<Diary> diaryList = null;
    DiaryDBManager diaryDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 과제명: 다이어리 앱
        // 분반: 01 분반
        // 학번: 20201021 성명: 차은수
        // 제출일: 2022년 6월 ?일
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        diaryDBManager = new DiaryDBManager(this);
        listView = findViewById(R.id.customListView);
        diaryList = new ArrayList<Diary>();
        adapter = new MyAdapter(this, R.layout.custom_adapter_view, diaryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Diary diary = diaryList.get(position);

                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra("diary", diary);
                startActivityForResult(intent, UPDATE_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                final int pos = position;
                String strDate = diaryList.get(pos).getDate();
                String strTitle = diaryList.get(pos).getTitle();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("다이어리 삭제")
                        .setMessage(strDate + " " + strTitle + " 글을 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (diaryDBManager.removeDiary(diaryList.get(pos).get_id())) {
                                    diaryList.clear();
                                    diaryList.addAll(diaryDBManager.getAllDiary());
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onMenuClick(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_diary:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, REQ_CODE);
                break;
            case R.id.developer:
                Intent intentDeveloper = new Intent(this, IntroducingDeveloper.class);
                startActivity(intentDeveloper);
                break;
            case R.id.search:
                Log.d("20201021", "검색 시작");
                Intent intentSearch = new Intent(this, Search.class);
                intentSearch.putExtra("diaryList", diaryList);
                startActivityForResult(intentSearch, SEARCH_CODE);
                break;
            case R.id.exit:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        diaryList.clear();
        diaryList.addAll(diaryDBManager.getAllDiary());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Log.d("20201021", "수정 완료");
                    break;
                case RESULT_CANCELED:
                    Log.d("20201021", "수정 실패");
                    break;
            }
        } else if (requestCode == REQ_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Log.d("20201021", "추가 완료");
                    break;
                case RESULT_CANCELED:
                    Log.d("20201021", "추가 실패");
                    break;
            }
        } else if (requestCode == SEARCH_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    int pos = data.getIntExtra("position", -1);
                    String strTitle = diaryList.get(pos).getTitle().toString();
                    Toast.makeText(MainActivity.this, "제목 '"+strTitle+"'입니다. ",
                            Toast.LENGTH_SHORT).show();
                    Log.d("20201021", "검색 완료");
                    break;
                case RESULT_CANCELED:
                    Toast.makeText(MainActivity.this, "검색에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show();
                    Log.d("20201021", "검색 실패");
                    break;
            }
        }
    }
}