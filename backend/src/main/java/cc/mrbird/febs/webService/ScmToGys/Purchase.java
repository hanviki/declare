package cc.mrbird.febs.webService.ScmToGys;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@ToString(callSuper=true)

public class Purchase {
    private String id;
    private String bedat;
    private String ebeln;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBedat() {
        return bedat;
    }

    public void setBedat(String bedat) {
        this.bedat = bedat;
    }

    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public String getEbelp() {
        return ebelp;
    }

    public void setEbelp(String ebelp) {
        this.ebelp = ebelp;
    }

    public String getEindt() {
        return eindt;
    }

    public void setEindt(String eindt) {
        this.eindt = eindt;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    public String getLifnr() {
        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public BigDecimal getMenge() {
        return menge;
    }

    public void setMenge(BigDecimal menge) {
        this.menge = menge;
    }

    public String getMseht() {
        return mseht;
    }

    public void setMseht(String mseht) {
        this.mseht = mseht;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public BigDecimal getNetpr() {
        return netpr;
    }

    public void setNetpr(BigDecimal netpr) {
        this.netpr = netpr;
    }

    public String getTxz01() {
        return txz01;
    }

    public void setTxz01(String txz01) {
        this.txz01 = txz01;
    }

    public String getWerkst() {
        return werkst;
    }

    public void setWerkst(String werkst) {
        this.werkst = werkst;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    private String ebelp;
    private String eindt;
    private String lgort;
    private String lifnr;
    private String matnr;
    private String meins;
    private BigDecimal menge;
    private String mseht;
    private String name1;
    private BigDecimal netpr;
    private String txz01;
    private String werkst;
    private String werks;

}
