package com.example.emoid_cv6fox.Config;

import com.example.emoid_v2.R;

/**
 * @author: wzt
 * @date: 2020/11/21
 */
public class ConfigView {
    public static class Face{
        //人脸颜色
        public static final int []colorId={R.color.face1,R.color.face2,R.color.face3,R.color.face4};
        public static final int []color_dId={R.color.face1_d,R.color.face2_d,R.color.face3_d,R.color.face4_d};
        //public static final String[] name={"Happy","Neut","Sadn","anger"};
        public static final String[] name={"Happy","Neutral","Sadness","Mixed"};
    }
    public static class LChart{
        //折线图绘制天数
        public static int DRAW_DAY_NUM=12;
    }
    public static class Heart{
        //心率图一次输出的数据
        public static int DRAW_DATA_NUM=5000;
        //心率图要显示的设备id
        public static int DEVICE_ID=1;
        //设备图片id
        public static final int[]deviceId={R.drawable.p_image_earphone,R.drawable.p_image_wristwatch,R.drawable.p_image_hairhoop};

    }

}
