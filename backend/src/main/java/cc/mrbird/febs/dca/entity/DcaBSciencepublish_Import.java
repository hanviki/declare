package cc.mrbird.febs.dca.entity;

import cc.mrbird.febs.common.converter.DateConverter;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import com.wuwenze.poi.config.Options;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Excel("论文")
public class DcaBSciencepublish_Import {
    @ExcelField(value = "论文名")
    private String paperName;

    @ExcelField(value = "期刊名")
    private String journalName;

    @ExcelField(value = "期刊号")
    private String journalCode;

    @ExcelField(value = "发表年月", dateFormat = "yyyy-MM-dd",comment = "这里请填日期格式如2020-09-08")
    private Date paperPublishdate;

    @ExcelField(value = "收录情况")
    private String paperShoulu;

    @ExcelField(value = "影响因子")
    private String paperCause;

    @ExcelField(value = "是否一流期刊",comment = "这里请填是、否")
    private String isBest;

    @ExcelField(value = "他引次数")
    private String otherTimes;

    @ExcelField(value = "期刊级别",options = QkjbOptions.class,comment = "这里请下拉选择A、B、C、D、E、F")
    private String qkjb;

    @ExcelField(value = "第一或通讯作者")
    private String authorRank;

    @ExcelField(value = "第几作者")
    private String djzz;

    @ExcelField(value = "期刊类型",options = WzlxOptions.class,comment = "这里请下拉选择科研、教学")
    private String wzlx;
}

