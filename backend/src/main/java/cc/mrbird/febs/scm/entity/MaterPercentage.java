package cc.mrbird.febs.scm.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MaterPercentage {
    private String code;
    private String name;
    private BigDecimal menge;
    private BigDecimal doneMenge;
    private BigDecimal percent;
    private String matnr;
    private String txz01;
    private String werkst;
}
