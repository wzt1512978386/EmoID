package com.example.emoid_cv6fox.Util;

public class MyList {
    private float []list;
    private int maxLen;
    private int start;
    private int end;
    private int len;
    private int FLen;
    public MyList(int maxLen){
        this.maxLen=maxLen;
        list=new float[maxLen];
        start=0;
        end=0;
        len=0;
        FLen=0;
    }
    //获得允许最长长度
    public int getMaxLen(){return maxLen;}
    //当前长度
    public int getLen(){return len;}
    //获取虚拟长度，即收集的全部长度
    public int getFLen(){return FLen;}
    //当前start位置
    public int getEnd(){return end;}
    //弹出头元素
    public boolean pop(){
        if(len>=1) {
            len--;
            list[start]=0;
            start = (start + 1) % maxLen;
            return true;
        }
        else
            return false;
    }
    //向后推入元素
    public boolean push(float x){
        if(len<maxLen) {
            list[(start + len) % maxLen] = x;
            len++;
            end=(end+1)%maxLen;
            return true;
        }
        else
            return false;
    }
    //暴力向后推入，会覆盖前面的
    public boolean forcePush(float x){
        FLen++;
        if(len<maxLen){
            list[(start+len)%maxLen]=x;
            len++;
            end=(end+1)%maxLen;
            return true;
        }
        else {
            list[start] = x;
            start = (start + 1) % maxLen;
            end = (end + 1) % maxLen;
            return false;
        }
    }
    //返回索引元素
    public float get(int i){
        if(i>=0&&i<maxLen){
            return list[(start+i+maxLen*10)%maxLen];
        }
        else
            return -1;
    }

    //返回绝对索引元素
    public float getAbsolute(int i){
        if(i>=0&&i<maxLen)
            return list[i];
        return -1;
    }
    //设置索引元素
    public boolean set(int i,float x){
        if(i>=0&&i<maxLen){
            list[(start+i)%maxLen]=x;
            return true;

        }
        else
            return false;
    }
    //设置绝对索引元素
    public boolean setAbsolute(int i,float x){
        if(i>=0&&i<maxLen) {
            list[i]=x;
            return true;
        }
        return false;
    }
    //清空列表使其全为0
    public void clear(){
        for(int i=0;i<list.length;i++)
            list[i]=0;
        start=end=len=0;


    }
}


