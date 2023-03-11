package ddwucom.mobile.finalreport;

import java.io.Serializable;

public class Diary implements Serializable {
    private long _id;
    private String date;
    private String weather;
    private String title;
    private String content;
    private int imaRes;

    public Diary(String date, String weather, String title, String content, int imaRes) {
        this.date = date;
        this.weather = weather;
        this.title = title;
        this.content = content;
        this.imaRes = imaRes;
    }

    public Diary(long _id, String date, String weather, String title, String content, int imaRes) {
        this._id = _id;
        this.date = date;
        this.weather = weather;
        this.title = title;
        this.content = content;
        this.imaRes = imaRes;
    }

    public long get_id() {
        return _id;
    }
    public void set_id(long _id) {
        this._id = _id;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getImaRes() {
        return imaRes;
    }
    public void setImaRes(int imaRes) {
        this.imaRes = imaRes;
    }
}
