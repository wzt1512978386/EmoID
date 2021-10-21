package com.example.emoid_cv6fox.Model;

import android.util.Log;


import com.example.emoid_cv6fox.App.MyApp;
import com.example.emoid_cv6fox.Config.ConfigModel;
import com.example.emoid_cv6fox.Util.UtilFile;

import org.pytorch.IValue;
import org.pytorch.Module;
import org.pytorch.Tensor;

import java.nio.FloatBuffer;

//分类器
public class Classifier {
    Module model;
    private float []predit;
    private String curModel;
    //读取模型 模型名称
    public Classifier(){
        curModel= ConfigModel.model[0];
        String path=UtilFile.assetFilePath(MyApp.mainActivity,curModel);
        model = Module.load(path);
    }
    public void UpdateClassifier(String newModel){
        if(newModel.equals(curModel))
            return;
        curModel=newModel;
        model = Module.load(UtilFile.assetFilePath(MyApp.mainActivity,curModel));
    }
    //预测 包括输入和输出
    private   void predit(float []data)
    {
        //float []data=new float[2091];


        int ah=257,aw=126;


        int length= 5 * ah * aw;
        Log.i("IN101","ss"+String.valueOf(data.length));
        final FloatBuffer floatBuffer = Tensor.allocateFloatBuffer(length);
        InputToFloatBuffer(data, length, floatBuffer, 0);

        long sharp[]={1,5,ah,aw};
        //long sharp[]={1,ah,aw,5};

        //long sharp[]={3,17,41};
        Tensor tensor= Tensor.fromBlob(data,sharp);
        IValue inputdata= IValue.from(tensor);
        Tensor output=model.forward(inputdata).toTensor();
        predit=output.getDataAsFloatArray();
    }
    private static void checkOutBufferCapacity(
            FloatBuffer outBuffer, int outBufferOffset, int length) {
        if (outBufferOffset + 1 * 1 * length > outBuffer.capacity()) {
            throw new IllegalStateException("Buffer underflow");
        }
    }
    private static void InputToFloatBuffer(
            final float[] array,
            final int length,
            final FloatBuffer outBuffer,
            final int outBufferOffset) {
        checkOutBufferCapacity(outBuffer, outBufferOffset, length);

        final int Count = 1 * 1 * length;
        for (int i = 0; i < Count; i++) {
            float u=array[i];
            outBuffer.put(outBufferOffset + i, u);
        }
    }
    public float[]getPredit(float []data){
        predit(data);
        return predit;
    }

}
