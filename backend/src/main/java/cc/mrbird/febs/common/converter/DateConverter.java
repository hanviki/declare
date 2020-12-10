package cc.mrbird.febs.common.converter;

import cc.mrbird.febs.common.utils.DateUtil;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateConverter implements WriteConverter {
    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            if (value == null)
                return "";
            else {
                return DateUtil.formatCSTTime(value.toString(), "yyyy-MM-dd");
            }
        } catch (Exception e) {
            log.error("时间转换异常", e);
            return "";
        }
    }
}
