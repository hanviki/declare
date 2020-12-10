package cc.mrbird.febs.common.converter;

import cc.mrbird.febs.common.utils.DateUtil;
import cn.hutool.core.convert.Convert;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BooleanConverter implements WriteConverter {
    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            if (value == null)
                return "";
            else {
                Boolean isT= Convert.toBool(value);
                if(isT){ return  "是";}
                if(!isT){ return  "否";}
            }
            return  "";
        } catch (Exception e) {
            log.error("时间转换异常", e);
            return "";
        }
    }
}
