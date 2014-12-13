package cz.odhlasujto.odhlasujto;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Adapter extends BaseAdapter {

    FragmentActivity activity;
    List<Poll> data = new ArrayList<Poll>();

    public Adapter(FragmentActivity activity, List<Poll> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return fillView(i);
    }


    public View fillView(int i) {
        Context context = activity.getApplicationContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = null;

        view = inflater.inflate(R.layout.rimmer, null);
        TextView t0 = (TextView) view.findViewById(R.id.pollIdMUSTR);
        t0.setText(String.valueOf(data.get(i).getPollId()));

        TextView t1 = (TextView) view.findViewById(R.id.pollNameMUSTR);
        t1.setText(data.get(i).getPollName());

        TextView t2 = (TextView) view.findViewById(R.id.pollDescMUSTR);
        t2.setText(data.get(i).getPollDesc());

        TextView t3 = (TextView) view.findViewById(R.id.optionNameMUSTR);
        t3.setText(data.get(i).getOptionName());

        TextView t4 = (TextView) view.findViewById(R.id.SUMMUSTR);
        t4.setText(data.get(i).getSUM());

        return view;
    }

}
