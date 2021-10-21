package com.example.emoid_cv6fox.Config;

import android.os.Environment;

/**
 * @author: wzt
 * @date: 2020/11/18
 */
public class ConfigFile {
    //录音存储文件地址
    //public static final String dirPath= Environment.getStorageDirectory().getAbsolutePath()+"/wztAudioTest";//这个会失败

    private static final String ABSOLUTE_PATH=Environment.getExternalStorageDirectory().getPath();//绝对路径
    private static final String RELATIVE_PATH_DEFAULT="1/EmoID";//相对地址默认值
    public static String RELATIVE_PATH=RELATIVE_PATH_DEFAULT;//相对地址

    public static final String TEST_PATH="1/新的组合数据";
    //public static final String TEST_PATH="1/组合数据T";
    //public static final String TEST_PATH="1/额外数据";
    //public static final String RESULT_PATH="1/结果";
    //public static final String RESULT_PATH="1/结果24";
    //public static final String RESULT_PATH="1/结果T2";
    //public static final String RESULT_PATH="1/额外结果";
    public static final String RESULT_PATH="1/新的结果";


    private static final String STORAGE_PATH="storage";//存储文件夹名
    private static final String CACHE_PATH="cache";//缓存文件夹名

    public static String FILE_NAME="WZT";
    public final static String CACHE_NAME="AudioCache";

    public static String getStoragePath(){
        return ABSOLUTE_PATH+"/"+RELATIVE_PATH+"/"+STORAGE_PATH;
    }
    public static String getCachePath(){
        return ABSOLUTE_PATH+"/"+RELATIVE_PATH+"/"+CACHE_PATH;
    }
    public static String getRelativePath(){return ABSOLUTE_PATH+"/"+RELATIVE_PATH;}
    public static String getTestPath(){
        return ABSOLUTE_PATH+"/"+TEST_PATH;
    }
    public static String getResultPath(){
        return ABSOLUTE_PATH+"/"+RESULT_PATH;
    }

}
