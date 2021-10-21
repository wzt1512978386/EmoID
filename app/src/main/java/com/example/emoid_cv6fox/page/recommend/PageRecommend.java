package com.example.emoid_cv6fox.page.recommend;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.emoid_cv6fox.UI.Recommend.FramePie;
import com.example.emoid_v2.R;

public class PageRecommend extends Fragment {
    private Activity activity;
    private View root;
    private ImageView musicView;
    private FramePie framePie;

    //狐狸按钮
    protected Button bt_cheater1;//neut
    protected Button bt_cheater2;//happy
    protected Button bt_cheater3;//anger
    protected Button bt_cheater4;//nothing

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.page_recommend, container, false);//设置对应布局
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity=getActivity();

        //狐狸按钮初始化
        bt_cheater1=(Button)root.findViewById(R.id.button_recommend_cheater1);
        bt_cheater2=(Button)root.findViewById(R.id.button_recommend_cheater2);
        bt_cheater3=(Button)root.findViewById(R.id.button_recommend_cheater3);
        bt_cheater4=(Button)root.findViewById(R.id.button_recommend_cheater4);

        //musicView=(ImageView)root.findViewById(R.id.imageview_recommend_music);
        new EventsRecommend(this).setEvents();
    }
}
