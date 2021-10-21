package com.example.emoid_cv6fox.page.Setting;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigFile;
import com.example.emoid_cv6fox.Model.Classifier;
import com.example.emoid_cv6fox.Util.UtilSys;
import com.example.emoid_v2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class PageSetting extends Fragment {
    private Activity activity;
    private Context context;
    private View root;

    private EditText collectTime;
    private EditText scanTime;
    private EditText lineDataNum;
    private EditText heartNum;
    private EditText identifyId;
    private EditText deviceID;

    private EditText happy;
    private EditText neut;
    private EditText sadn;
    private EditText anger;

    private Button enter;
    public TextView test;

    private TextView terminal;
    public ImageButton bt_trash;


    private Button bt_randData;

    public Button bt_pythonTest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.page_more, container, false);//设置对应布局
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.activity=getActivity();
        this.context=getContext();
        MyApp.pageSetting =this;
        configInit();
        otherInit();

        terminal=(TextView) root.findViewById(R.id.textview_setting_terminal);

        bt_trash=(ImageButton)root.findViewById(R.id.imagebutton_setting_terminal_trash);
        bt_trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminal.setText("~EmoID:");
            }
        });

        MyApp.terminal=terminal;

        /*
        scanTime.setText(String.valueOf(ConfigBT.SCAN_TIME));
        lineDataNum.setText(String.valueOf(ConfigSys.NUMDRAW));
        heartNum.setText(String.valueOf(ConfigSys.CHARTDATANUM));
        identifyId.setText(String.valueOf(ConfigSys.IDENTIFYID));
        deviceID.setText(String.valueOf(ConfigSys.DEVICEID));

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfigSys.COLLECTTIME=Integer.parseInt(collectTime.getText().toString().trim());
                ConfigSys.BTSCANTIME=Integer.parseInt(scanTime.getText().toString().trim());
                ConfigSys.NUMDRAW=Integer.parseInt(lineDataNum.getText().toString().trim());
                ConfigSys.CHARTDATANUM=Integer.parseInt(heartNum.getText().toString().trim());
                ConfigSys.IDENTIFYID=Integer.parseInt(identifyId.getText().toString().trim());
                ConfigSys.DEVICEID=Integer.parseInt(deviceID.getText().toString().trim());

                float happy_f=Float.parseFloat(happy.getText().toString().trim());
                float neut_f=Float.parseFloat(neut.getText().toString().trim());
                float sadn_f=Float.parseFloat(sadn.getText().toString().trim());
                float anger_f=Float.parseFloat(anger.getText().toString().trim());
                com.example.emoid_v1.App.MyApp.frameRecommend.framePie.setResult(happy_f,neut_f,sadn_f,anger_f);

                /*
                TextView textView=root.findViewById(R.id.textview_recommend_result);
                float[] temp={happy_f,neut_f,sadn_f,anger_f};
                String []aa={"Happy","Neut","Sadn","Anger"};
                int []coloraa={R.color.face1,R.color.face2,R.color.face3,R.color.face4};
                for(int i=0;i<4;i++) {
                    if(temp[i]==Math.max(Math.max(happy_f, neut_f), Math.max(sadn_f, anger_f))){
                        textView.setText("happy");
                        textView.setBackgroundColor(getResources().getColor(coloraa[i]));
                        break;
                    }
                }





                Toast.makeText(context,"设置成功",Toast.LENGTH_SHORT).show();
            }
        }
        );
         */




    }
    private void otherInit(){

        //模型//--------暂时放这
        MyApp.classifier=new Classifier();

        //python测试
        bt_pythonTest=(Button)root.findViewById(R.id.button_test_python);
        bt_pythonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyApp.pythonManager.getFloat0("test","funcTest");
                //Float A[]=new Float[10];

                //List<Float> B=new ArrayList<>();
                //MyApp.pythonManager.getFloat0("onlysignals","Preprocessing",new Kwarg("signals",B.toArray()));


                //Toast.makeText(MyApp.mainActivity,"kais",Toast.LENGTH_SHORT).show();

                /////////////////////////////////////
                //开始数据处理
                Log.i("IN106","开始数据处理");

                long time1=System.currentTimeMillis();
                ////////////////////////////////////////
                //写txt文本

                File cache=new File(ConfigFile.getCachePath());
                if(!cache.exists())
                    cache.mkdirs();
                File txt=new File(ConfigFile.getCachePath()+"/cache.txt");
                //Log.i("IN106",txt.getAbsolutePath());
                if(!txt.exists()){
                    Log.i("IN106","没有txt文件");
                    try {
                        if(txt.createNewFile()){
                            Log.i("IN106","txt文件创建成功");
                        }else{
                            Log.i("IN106","txt文件创建失败");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    FileOutputStream fout=new FileOutputStream(txt);
                    for(int i=0;i<3;i++){
                        for(int j=0;j<15000;j++){
                            String ss= MyApp.data.dataCollect[i].get(j)+"";
                            if(j!=0){
                                ss=" "+ss;
                            }
                            fout.write(ss.getBytes());
                        }
                        fout.write("\n".getBytes());
                    }

                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(false)
                    return;
                Log.i("IN106","点击1");
                //UtilSys.LOG_I("开始数据处理前期工作");
                /*
                Float A[][][]=new Float[1][3][15000];
                for(int i=0;i<3;i++){
                    for(int j=0;j<15000;j++){
                        A[0][i][j]=MyApp.data.dataCollect[i].get(j);
                    }
                }
                Log.i("IN106","点击2");
                UtilSys.LOG_I("开始数据处理");

                 */
                //float B[][][][]=MyApp.pythonManager.doProccess("onlysignals","Preprocessing",A);
                float B[]=MyApp.pythonManager.doProccess("onlysignals","Preprocessing",txt.getAbsolutePath());
                //Log.i("IN106","点击3");
                ///////////////////////////////////////////////
                //数据处理结束

                long time2=System.currentTimeMillis();

                /////////////////////////////////////////////
                //开始模型运算
                Log.i("IN106","开始模型运算");
                //A=null;
                //UtilSys.LOG_I("开始模型运算前期工作");
                float C[]=new float[257*126*5];
                System.arraycopy(B,0,C,0,257*126*3);
                System.arraycopy(B,257*126,C,257*126*3,257*126*2);
                /*
                float C[]=new float[257*126*5];
                for(int i=0;i<5;i++){
                    for(int j=0;j<257;j++){
                        for(int k=0;k<126;k++){
                            if(i>=3)
                                C[i*257*126+j*126+k]=B[0][i-2][j][k];
                            else
                                C[i*257*126+j*126+k]=B[0][i][j][k];
                        }
                    }
                }

                 */

                //UtilSys.LOG_I("开始模型运算");
                float []ans=MyApp.classifier.getPredit(C);
                UtilSys.LOG_I("模型结果得出");

                for(int i=0;i<ans.length;i++){
                    Log.i("IN105",i+" : "+ans[i]);
                }

                ///////////////////////////////////////////////
                //模型运算结束
                Log.i("IN106","得出模型结果");

                long time3=System.currentTimeMillis();

                Log.i("IN107","数据处理时间："+(time2-time1)+"ms");
                Log.i("IN107","模型运算时间："+(time3-time2)+"ms");
                 
                //MyApp.pythonManager.getFloat0("onlysignals","haha");
            }
        });
    }


    private void configInit(){
        collectTime=root.findViewById(R.id.edittext_more_collecttime);
        scanTime=root.findViewById(R.id.edittext_more_Scantime);
        lineDataNum =root.findViewById(R.id.edittext_more_brokennum);
        heartNum=root.findViewById(R.id.edittext_more_headnum);
        identifyId=root.findViewById(R.id.edittext_more_identifyId);
        deviceID=root.findViewById(R.id.edittext_more_deviceId);
        enter=root.findViewById(R.id.button_more_enter);
        test=root.findViewById(R.id.textview_more_test);

        happy=root.findViewById(R.id.edittext_more_happy);
        neut=root.findViewById(R.id.edittext_more_neut);
        sadn=root.findViewById(R.id.edittext_more_sadn);
        anger=root.findViewById(R.id.edittext_more_anger);

        happy.setText("0.12");
        neut.setText("0.68");
        sadn.setText("0.11");
        anger.setText("0.09");
    }


}
