package ddwucom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DiaryDBManager {

    DiaryDBHelper diaryDBHelper = null;
    Cursor cursor = null;

    public DiaryDBManager(Context context) {
        diaryDBHelper = new DiaryDBHelper(context);
    }

    public ArrayList<Diary> getAllDiary() {
        ArrayList diaryList = new ArrayList();
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DiaryDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String date = cursor.getString(1);
            String weather = cursor.getString(2);
            String title = cursor.getString(3);
            String content = cursor.getString(4);
            int res = cursor.getInt(5);
            diaryList.add( new Diary(id, date, weather, title, content, res) );
        }

        cursor.close();
        diaryDBHelper.close();
        return diaryList;
    }

    public boolean addNewDiary(Diary newDiary) {
        SQLiteDatabase db = diaryDBHelper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put(DiaryDBHelper.COL_DATE, newDiary.getDate());
        value.put(DiaryDBHelper.COL_WEATHER, newDiary.getWeather());
        value.put(DiaryDBHelper.COL_TITLE, newDiary.getTitle());
        value.put(DiaryDBHelper.COL_CONTENT, newDiary.getContent());
        value.put(DiaryDBHelper.COL_IMAGE, newDiary.getImaRes());

        long result = db.insert(DiaryDBHelper.TABLE_NAME, null, value);

        diaryDBHelper.close();

        if (result > 0) return true;
        return false;
    }

    public boolean modifyDiary(Diary diary) {
        SQLiteDatabase db = diaryDBHelper.getWritableDatabase();

        ContentValues row = new ContentValues();
        row.put(DiaryDBHelper.COL_DATE, diary.getDate());
        row.put(DiaryDBHelper.COL_WEATHER, diary.getWeather());
        row.put(DiaryDBHelper.COL_TITLE, diary.getTitle());
        row.put(DiaryDBHelper.COL_CONTENT, diary.getContent());

        String whereClause = DiaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(diary.get_id()) };

        int result = db.update(DiaryDBHelper.TABLE_NAME, row, whereClause, whereArgs);

        diaryDBHelper.close();

        if (result > 0) return true;
        return false;
    }

    public boolean removeDiary(long id) {
        SQLiteDatabase db = diaryDBHelper.getWritableDatabase();

        String whereClause = DiaryDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[] { String.valueOf(id) };

        int result = db.delete(DiaryDBHelper.TABLE_NAME, whereClause, whereArgs);

        diaryDBHelper.close();

        if (result > 0) return true;
        return false;
    }

    public int getDairyByDate(String strDate) {
        SQLiteDatabase db = diaryDBHelper.getReadableDatabase();

        String[] columns = {"_id"};
        String selection = "date=?";
        String[] selectArgs = new String[1];
        selectArgs[0] = strDate;
        Cursor cursor = db.query(DiaryDBHelper.TABLE_NAME, columns, selection, selectArgs, null, null, null, null);

        int id = 0;
        while (cursor.moveToNext()) {
            id = cursor.getInt(0);
        }

        cursor.close();
        diaryDBHelper.close();

        return id-1;    // 인덱스는 0부터 시작이므로!
    }

    public void close() {
        if (diaryDBHelper != null) diaryDBHelper.close();
        if (cursor != null) cursor.close();
    }
}
