package cc.mrbird.febs.common.utils;

import com.wuwenze.poi.convert.WriteConverter;
import com.wuwenze.poi.exception.ExcelKitRuntimeException;
import com.wuwenze.poi.pojo.ExcelProperty;
import com.wuwenze.poi.util.DateUtil;
import com.wuwenze.poi.util.POIUtil;
import com.wuwenze.poi.util.ValidatorUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.xssf.streaming.SXSSFCell;

import java.text.ParseException;
import java.util.Date;

public class ExportExcelMapping {
    public static Object buildCellValueByExcelProperty(Object cellValue,
                                                        ExcelProperty property) {
        if (null != cellValue) {
            String dateFormat = property.getDateFormat();
            if (!ValidatorUtil.isEmpty(dateFormat)) {
                if (cellValue instanceof Date) {
                    return com.wuwenze.poi.util.DateUtil.format(dateFormat, (Date) cellValue);
                } else if (cellValue instanceof String) {
                    try {
                        Date parse = com.wuwenze.poi.util.DateUtil.ENGLISH_LOCAL_DF.parse((String) cellValue);
                        return DateUtil.format(dateFormat, parse);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return "";
                }
            }
            // writeConverterExp && writeConverter
            String writeConverterExp = property.getWriteConverterExp();
            WriteConverter writeConverter = property.getWriteConverter();
            if (!ValidatorUtil.isEmpty(writeConverterExp)) {
                try {
                    cellValue = POIUtil.convertByExp(cellValue, writeConverterExp);
                } catch (Throwable e) {
                    throw new ExcelKitRuntimeException(e);
                }
            } else if (null != writeConverter) {
                return writeConverter.convert(cellValue);
            }
            return String.valueOf(cellValue);
        }
        return  "";
    }
}
