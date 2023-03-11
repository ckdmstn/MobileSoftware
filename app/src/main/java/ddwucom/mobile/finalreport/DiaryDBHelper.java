package ddwucom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DiaryDBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "diaries.db";
    public final static String TABLE_NAME = "diary_table";

    public final static String COL_ID = "_id";
    public final static String COL_DATE = "date";
    public final static String COL_WEATHER = "weather";
    public final static String COL_TITLE = "title";
    public final static String COL_CONTENT = "content";
    public final static String COL_IMAGE = "image";

    public DiaryDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COL_ID + " integer primary key autoincrement, "
                + COL_DATE + " TEXT, " + COL_WEATHER + " TEXT, "
                + COL_TITLE + " TEXT, " + COL_CONTENT + " TEXT, "
                + COL_IMAGE + " INTEGER)";
        db.execSQL(sql);

        insertSample(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertSample(SQLiteDatabase db) {
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20220618', '적당함', '드림콘서트', '가서 좋아하는 아이돌 많이 보고 왔다!!', " + R.mipmap.cb + ");");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20220619', '맑음', '오늘은', '종강!', " + R.mipmap.dog + ");");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20220620', '흐림', '학교', '근로 중', " + R.mipmap.flower + ");");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20220621', '더움', '심심하다', '아무 것도 안 했다..', " + R.mipmap.heart + ");");
        db.execSQL("insert into " + TABLE_NAME + " values (null, '20220622', '시원함', '사촌언니랑', '돈까스 먹었다', " + R.mipmap.star+");");
        
    }
}
