package ddwucom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Diary> diaryList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<Diary> diaryList) {
        this.context = context;
        this.layout = layout;
        this.diaryList = diaryList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return diaryList.size();
    }

    @Override
    public Object getItem(int i) {
        return diaryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return diaryList.get(i).get_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int position = i;
        ViewHolder holder;

        if (view == null) {
            view = layoutInflater.inflate(layout, viewGroup, false);

            holder = new ViewHolder();
            holder.textNo = (ImageView)view.findViewById(R.id.tvNo);
            holder.textDate = (TextView)view.findViewById(R.id.tvDate);
            holder.textTitle = (TextView)view.findViewById(R.id.tvTitle);
            holder.textWeather = (TextView)view.findViewById(R.id.tvWeather);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        holder.textNo.setImageResource(diaryList.get(position).getImaRes());
        holder.textDate.setText(diaryList.get(position).getDate());
        holder.textTitle.setText(diaryList.get(position).getTitle());
        holder.textWeather.setText(diaryList.get(position).getWeather());


        return view;
    }

    static class ViewHolder {
        ImageView textNo;
        TextView textTitle;
        TextView textDate;
        TextView textWeather;
    }
}
