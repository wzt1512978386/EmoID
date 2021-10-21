package com.example.emoid_cv6fox.proccess;

import android.content.Context;

import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

/**
 * @author: wzt
 * @date: 2020/12/1
 */
public class PythonManager {
    private Python py;
    private PyObject obj;
    public PythonManager(Context context){
        initPython(context);
        py=Python.getInstance();
    }
    private void initPython(Context context){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(context));
        }
    }
    private void doPython(String module,String key, Object... args){
        obj=py.getModule(module).callAttr(key,args);
    }
    //返回三维浮点数据
    public float [][][]getFloat3(String module,String key, Object... args){
        doPython(module,key, args);
        float [][][]result=obj.toJava(float[][][].class);
        return result;
    }
    //返回一维浮点数据
    public float []getFloat1(String module,String key, Object... args){
        doPython(module,key, args);
        return obj.toJava(float[].class);
    }
    //不返回浮点数据
    public float[] doProccess(String module,String key, String ssPath){
    //public float[][][][] doProccess(String module,String key, Float[][][] A){

        //PyObject list = py.getBuiltins().get("list");
        //A[0][0][0]=4.1231f;

        //PyObject par=PyObject.fromJava(A);


        //Log.i("IN106","点击3.1");
        obj=py.getModule(module).callAttr(key,new Kwarg("ssPath",ssPath));
        //Log.i("IN106","点击3.2");
        //return obj.toJava(float[][][][].class);
        return obj.toJava(float[].class);
    }

}
