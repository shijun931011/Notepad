package com.example.shijun.notepad.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shijun.notepad.R;
import com.example.shijun.notepad.activity.MainActivity;

/**
 * Created by shijun on 2017/1/2.
 */
public class Guide3 extends Fragment {

    private Button mIntoAppBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.guide_view3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIntoAppBtn = (Button) view.findViewById(R.id.into_app_btn);
        mIntoAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("is_first_in_data", 0x0000);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isFirstIn", false);
                editor.commit();

                getActivity().finish();
            }
        });
    }
}
