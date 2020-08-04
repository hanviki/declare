package cc.mrbird.febs.scm.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * 药品物料库
 * </p>
 *
 * @author viki
 * @since 2019-11-11
 */

@Excel("scm_d_mater")
public class ScmDMater implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
                    @TableId("ID")
                    @ExcelField(value ="主键")
private String id;
    /**
     * 编码
     */
    @TableField("CODE")
            @ExcelField(value ="编码")
private String code;
    /**
     * 名字
     */
    @TableField("NAME")
            @ExcelField(value ="名字")
private String name;
    /**
     * 物料编码
     */
    @TableField("MATNR")
            @ExcelField(value ="物料编码")
private String matnr;
    /**
     * 物料类型
     */
    @TableField("MTART")
            @ExcelField(value ="物料类型")
private String mtart;
    /**
     * 单位
     */
    @TableField("MEINS")
            @ExcelField(value ="单位")
private String meins;
    /**
     * 单位描述
     */
    @TableField("MSEHT")
            @ExcelField(value ="单位描述")
private String mseht;
    /**
     * 物料描述
     */
    @TableField("TXZ01")
            @ExcelField(value ="物料描述")
private String txz01;
    /**
     * 生产厂家
     */
    @TableField("PRODUCE_AREA")
            @ExcelField(value ="生产厂家")
private String produceArea;
    /**
     * 规格
     */
    @TableField("SPEC")
            @ExcelField(value ="规格")
private String spec;
    /**
     * 状态
     */
    @TableField("STATE")
            @ExcelField(value ="状态")
private Integer state;
    /**
     * 药品类别
     */
    @TableField("BKLAS")
            @ExcelField(value ="药品类别")
private String bklas;
    /**
     * 拼音码
     */
    @TableField("SPELL_CODE")
            @ExcelField(value ="拼音码")
private String spellCode;
    /**
     * 供应商账号
     */
    @TableField("GYSACCOUNT")
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 是否删除
     */
    @TableField("IS_DELETEMARK")
            @ExcelField(value ="是否删除")
private Integer isDeletemark;
    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
            @ExcelField(value ="创建时间")
private Date createTime;
    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
            @ExcelField(value ="修改时间")
private Date modifyTime;
    /**
     * 创建人
     */
    @TableField("CREATE_USER_ID")
            @ExcelField(value ="创建人")
private Long createUserId;
    /**
     * 修改人
     */
    @TableField("MODIFY_USER_ID")
            @ExcelField(value ="修改人")
private Long modifyUserId;

    @TableField(exist = false)
    public  String keyword;

    public String getId(){
            return id;
            }

        public void setId(String id) {
            this.id = id;
            }

    public String getCode(){
            return code;
            }

        public void setCode(String code) {
            this.code = code;
            }

    public String getName(){
            return name;
            }

        public void setName(String name) {
            this.name = name;
            }

    public String getMatnr(){
            return matnr;
            }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
            }

    public String getMtart(){
            return mtart;
            }

        public void setMtart(String mtart) {
            this.mtart = mtart;
            }

    public String getMeins(){
            return meins;
            }

        public void setMeins(String meins) {
            this.meins = meins;
            }

    public String getMseht(){
            return mseht;
            }

        public void setMseht(String mseht) {
            this.mseht = mseht;
            }

    public String getTxz01(){
            return txz01;
            }

        public void setTxz01(String txz01) {
            this.txz01 = txz01;
            }

    public String getProduceArea(){
            return produceArea;
            }

        public void setProduceArea(String produceArea) {
            this.produceArea = produceArea;
            }

    public String getSpec(){
            return spec;
            }

        public void setSpec(String spec) {
            this.spec = spec;
            }

    public Integer getState(){
            return state;
            }

        public void setState(Integer state) {
            this.state = state;
            }

    public String getBklas(){
            return bklas;
            }

        public void setBklas(String bklas) {
            this.bklas = bklas;
            }

    public String getSpellCode(){
            return spellCode;
            }

        public void setSpellCode(String spellCode) {
            this.spellCode = spellCode;
            }

    public String getGysaccount(){
            return gysaccount;
            }

        public void setGysaccount(String gysaccount) {
            this.gysaccount = gysaccount;
            }

    public Integer getIsDeletemark(){
            return isDeletemark;
            }

        public void setIsDeletemark(Integer isDeletemark) {
            this.isDeletemark = isDeletemark;
            }

    public Date getCreateTime(){
            return createTime;
            }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
            }

    public Date getModifyTime(){
            return modifyTime;
            }

        public void setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
            }

    public Long getCreateUserId(){
            return createUserId;
            }

        public void setCreateUserId(Long createUserId) {
            this.createUserId = createUserId;
            }

    public Long getModifyUserId(){
            return modifyUserId;
            }

        public void setModifyUserId(Long modifyUserId) {
            this.modifyUserId = modifyUserId;
            }



    public static final String ID ="ID" ;

    public static final String CODE ="CODE" ;

    public static final String NAME ="NAME" ;

    public static final String MATNR ="MATNR" ;

    public static final String MTART ="MTART" ;

    public static final String MEINS ="MEINS" ;

    public static final String MSEHT ="MSEHT" ;

    public static final String TXZ01 ="TXZ01" ;

    public static final String PRODUCE_AREA ="PRODUCE_AREA" ;

    public static final String SPEC ="SPEC" ;

    public static final String STATE ="STATE" ;

    public static final String BKLAS ="BKLAS" ;

    public static final String SPELL_CODE ="SPELL_CODE" ;

    public static final String GYSACCOUNT ="GYSACCOUNT" ;

    public static final String IS_DELETEMARK ="IS_DELETEMARK" ;

    public static final String CREATE_TIME ="CREATE_TIME" ;

    public static final String MODIFY_TIME ="MODIFY_TIME" ;

    public static final String CREATE_USER_ID ="CREATE_USER_ID" ;

    public static final String MODIFY_USER_ID ="MODIFY_USER_ID" ;

@Override
public String toString() {
        return "ScmDMater{" +
                ", id=" + id +
                ", code=" + code +
                ", name=" + name +
                ", matnr=" + matnr +
                ", mtart=" + mtart +
                ", meins=" + meins +
                ", mseht=" + mseht +
                ", txz01=" + txz01 +
                ", produceArea=" + produceArea +
                ", spec=" + spec +
                ", state=" + state +
                ", bklas=" + bklas +
                ", spellCode=" + spellCode +
                ", gysaccount=" + gysaccount +
                ", isDeletemark=" + isDeletemark +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", createUserId=" + createUserId +
                ", modifyUserId=" + modifyUserId +
        "}";
        }
        }