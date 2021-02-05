package cc.mrbird.febs.dca.entity;

import com.wuwenze.poi.config.Options;

public class WzlxOptions implements Options {
    @Override
    public String[] get() {
        return new String[]{"科研", "教学"};
    }
}
