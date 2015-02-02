package cz.odhlasujto.odhlasujto;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.List;

public class AdapterOptions extends BaseAdapter {

    FragmentActivity activity;
    Context context = activity.getApplicationContext();
    List<Options> data = new ArrayList<Options>();

    public AdapterOptions(FragmentActivity activity, List<Options> data) {
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
        view = inflater.inflate(R.layout.rimmer, null);

        TextView t1 = (TextView) view.findViewById(R.id.name_option);
        t1.setText(data.get(i).getOptionName());

        CheckBox cbVote = (CheckBox) view.findViewById(R.id.checkBoxVote);
        cbVote.setChecked(true);
//          if (data.get(i).getSUM() == 1)
//              cbVote.setChecked(true);
//              else
//              cbVote.setChecked(false);
        return view;
    }
}
