package cc.mrbird.febs.dca.entity;

import com.wuwenze.poi.config.Options;

public class YesNoOptions implements Options {
    @Override
    public String[] get() {
        return new String[]{"是", "否"};
    }
}
