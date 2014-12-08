package cz.odhlasujto.odhlasujto;

import android.os.Bundle;
import android.app.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.util.Log;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.view.LayoutInflater;

public class FragmentResults extends Fragment {

    private static final String LOG = MainActivity.class.getSimpleName(); //for printing out LOGs

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.results, container, false);

        final Button btnBack = (Button) view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrameLayout activityMainLayout = (FrameLayout) view.findViewById(R.id.container);
//                activityMainLayout.removeAllViews();

                LayoutInflater inflater = getActivity().getLayoutInflater();
                activityMainLayout.addView(inflater.inflate(R.layout.activity_main, null));
            }
        });
        return view;
    }
}