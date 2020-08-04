package cc.mrbird.febs.scm.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorRank {
    /**
     * 排名
     */
    private  int rankNum;
    /**
     * 供应商账号
     */
    private  String code;
    /**
     * 供应商名称
     */
    private  String name;
    /**
     * 供货率
     */
    private BigDecimal pe;
}
