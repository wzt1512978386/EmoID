package com.example.emoid_cv6fox.Test;

import android.content.Context;
import android.util.Log;

import com.example.emoid_cv6fox.App.MyApp;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: wzt
 * @date: 2021/1/29
 */
public class test {
    public static   Module module;
    public static void start(){
        try //加载模型
        {
            String aa=assetFilePath(MyApp.mainActivity, "last.pt");//获取路径，打开模型
            module = Module.load(aa);

        } catch (IOException e) {
            e.printStackTrace();

        }
        long shape[]={1,3,300,100};//模型输入形状

        float []aaz=new float[3*300*100];//传入参数的个数
        Tensor tensor=Tensor.fromBlob(aaz,shape);//tensor初始化方法

        IValue input=IValue.from(tensor);   //传入参数部分，90000个数据放到此处
        Tensor output=module.forward(input).toTensor();    //向模型传入参数，并返回结果
        float predict[]=output.getDataAsFloatArray();   //结果转到数组
        //返回的四个向量，向量值最大的为所取的那个情绪
        Log.i("test",Float.toString(predict[0]));
        Log.i("test",Float.toString(predict[1]));
        Log.i("test",Float.toString(predict[2]));
        Log.i("test",Float.toString(predict[3]));
    }
    public static String assetFilePath(Context context, String assetName) throws IOException {
        File file = new File(context.getFilesDir(), assetName);
        if (file.exists() && file.length() > 0) {
            return file.getAbsolutePath();
        }

        try (InputStream is = context.getAssets().open(assetName)) {
            try (OutputStream os = new FileOutputStream(file)) {
                byte[] buffer = new byte[4 * 1024];
                int read;
                while ((read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, read);
                }
                os.flush();
            }
            return file.getAbsolutePath();
        }
    }
}
