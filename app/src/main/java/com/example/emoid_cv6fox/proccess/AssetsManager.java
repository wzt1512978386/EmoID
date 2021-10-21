package com.example.emoid_cv6fox.proccess;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: wzt
 * @date: 2020/12/4
 */
public class AssetsManager  {
    private  Activity activity;
    private AssetManager asManager;
    private BufferedReader bufReader;
    private int []happyFileID={7,8};
    private int []neutFileID={0,1,2,3,4,5};
    private int []angerFileID={6};

    public AssetsManager(Activity activity){
        this.activity=activity;
        asManager=activity.getResources().getAssets();
    }
    public void initReader(int type){

        String fileName="data/";
        int len=0;
        if(type==0){
            len=happyFileID.length;
            int tempIndex=((int)(Math.random()*100*len)%len);
            fileName+=(""+happyFileID[tempIndex]);
        }
        else if(type==1){
            len=neutFileID.length;
            int tempIndxe=((int)(Math.random()*100*len)%len);
            fileName+=(""+neutFileID[tempIndxe]);
        }
        else if(type==2){
            len=neutFileID.length;
            int tempIndxe=((int)(Math.random()*100*len)%len);
            fileName+=(""+neutFileID[tempIndxe]);
        }
        else if(type==3){
            len=angerFileID.length;
            int tempIndex=((int)(Math.random()*100*len)%len);
            fileName+=(""+angerFileID[tempIndex]);
        }
        else if(type==4){
            fileName+="nothing";
        }
        fileName+=".txt";
        try {
            InputStreamReader inputReader = new InputStreamReader( activity.getResources().getAssets().open(fileName) );
            bufReader = new BufferedReader(inputReader);
        }catch (Exception e){
            Log.i("IN101","创建assets文件读入流失败！");
        }
    }
    public float readFloatData(){
        try {
            String str=bufReader.readLine();
           // Log.i("IN101",str);
            return Float.parseFloat(str);
        } catch (IOException e) {
            Log.i("IN101","读入assets数据失败一次");
            return 0f;
        }
    }
}
