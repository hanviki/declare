package cc.mrbird.febs.common.converter;

import cn.hutool.core.convert.Convert;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserState implements WriteConverter {
    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            if (value == null)
                return "";
            else {
                int state = Convert.toInt(value);
                if (state == 1) {
                    return "已提交";
                }
                if (state == 2) {
                    return "已退回";
                }


                return "";
            }
        } catch (Exception e) {
            log.error("转换异常", e);
            return "";
        }
    }
}
