package cz.odhlasujto.odhlasujto.Adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cz.odhlasujto.odhlasujto.Models.Poll;
import cz.odhlasujto.odhlasujto.R;

public class Adapter extends BaseAdapter {

    FragmentActivity activity;
    Context context = activity.getApplicationContext();
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

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = null;
        view = inflater.inflate(R.layout.rimmer_results, null);

        TextView t0 = (TextView) view.findViewById(R.id.name_option);
        t0.setText(String.valueOf(data.get(i).getPollId()));

        CheckBox cbResults = (CheckBox) view.findViewById(R.id.checkBoxResult);
        cbResults.setChecked(true);
//          if (data.get(i).getSUM() == 1)
//              cbVote.setChecked(true);
//              else
//              cbVote.setChecked(false);

        return view;
    }

}
