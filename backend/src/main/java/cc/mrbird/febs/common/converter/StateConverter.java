package cc.mrbird.febs.common.converter;

import cc.mrbird.febs.common.utils.DateUtil;
import cn.hutool.core.convert.Convert;
import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitWriteConverterException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StateConverter implements WriteConverter {
    @Override
    public String convert(Object value) throws ExcelKitWriteConverterException {
        try {
            if (value == null)
                return "";
            else {
                int state = Convert.toInt(value);
                if (state == 1) {
                    return "未审核";
                }
                if (state == 0) {
                    return "未提交";
                }

                if (state == 2) {
                    return "审核未通过";
                }

                if (state == 3) {
                    return "已审核";
                }
                return "";
            }
        } catch (Exception e) {
            log.error("时间转换异常", e);
            return "";
        }
    }
}
