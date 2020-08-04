package cc.mrbird.febs.scm.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TotalStatistic {
    private String txz01;
    private String matnr;
    private BigDecimal fpjr;
    private BigDecimal gMenge;
    private String gysaccount;
    private  String gysname;
}
