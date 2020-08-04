package cc.mrbird.febs.webService.SapToScm;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


public class Sap_PurchasePlan implements Serializable {
    private static final long serialVersionUID = 1L;

    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    private String ebeln ;

    public String getEbelp() {
        return ebelp;
    }

    public void setEbelp(String ebelp) {
        this.ebelp = ebelp;
    }

    private String ebelp ;

    public String getLifnr() {
        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }

    private String lifnr ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name ;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    private String matnr ;

    public String getTxz01() {
        return txz01;
    }

    public void setTxz01(String txz01) {
        this.txz01 = txz01;
    }

    private String txz01 ;

    private String werks ;

    private String lgort ;


    private BigDecimal menge;


    private String meins ;

    private String netpr ;


    private String eindt ;

    private String bedat ;


    private String mseht ;


    private String werkst ;


    private String bsart ;


    private String kostl ;


    private String loekz ;


    private String remark ;

    private String contact ;

    private String phone ;



    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    public BigDecimal getMenge() {
        return menge;
    }

    public void setMenge(BigDecimal menge) {
        this.menge = menge;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public String getNetpr() {
        return netpr;
    }

    public void setNetpr(String netpr) {
        this.netpr = netpr;
    }

    public String getEindt() {
        return eindt;
    }

    public void setEindt(String eindt) {
        this.eindt = eindt;
    }

    public String getBedat() {
        return bedat;
    }

    public void setBedat(String bedat) {
        this.bedat = bedat;
    }

    public String getMseht() {
        return mseht;
    }

    public void setMseht(String mseht) {
        this.mseht = mseht;
    }

    public String getWerkst() {
        return werkst;
    }

    public void setWerkst(String werkst) {
        this.werkst = werkst;
    }

    public String getBsart() {
        return bsart;
    }

    public void setBsart(String bsart) {
        this.bsart = bsart;
    }

    public String getKostl() {
        return kostl;
    }

    public void setKostl(String kostl) {
        this.kostl = kostl;
    }

    public String getLoekz() {
        return loekz;
    }

    public void setLoekz(String loekz) {
        this.loekz = loekz;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    private String comments ;
}
