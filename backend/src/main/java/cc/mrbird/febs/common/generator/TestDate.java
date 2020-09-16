package cc.mrbird.febs.common.generator;

import cc.mrbird.febs.common.utils.SnowIdUtils;
import cn.hutool.core.lang.Snowflake;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    public static void main(String[] args) {
        //176771175472304128
        //176771254765621248
        //176771425276661760
        //176771766965637120
        System.out.println(SnowIdUtils.uniqueLong());
    }
}
