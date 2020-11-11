package cc.mrbird.febs.dca.entity;

import lombok.Data;

/**
 * @author lijiang
 * @createDate 2020/11/6
 */
@Data
public class ExportAfferentCustomExcel {

    /**
     * 标题名称
     */
    private String title;

    /**
     * 导出字段名
     */
    private String dataIndex;

    private int isDynamic;
}

