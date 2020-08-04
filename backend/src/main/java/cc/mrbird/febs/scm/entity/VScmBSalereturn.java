package cc.mrbird.febs.scm.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author viki
 * @since 2019-12-09
 */

@Excel("v_scm_b_salereturn")
public class VScmBSalereturn implements Serializable{

private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
            @ExcelField(value ="主键ID")
private String id;
    /**
     * 物料描述
     */
            @ExcelField(value ="物料描述")
private String txz01;
    /**
     * 供应商账号
     */
            @ExcelField(value ="供应商账号")
private String gysaccount;
    /**
     * 物料编码
     */
            @ExcelField(value ="物料编码")
private String matnr;
    /**
     * 规格
     */
            @ExcelField(value ="规格")
private String spec;
    /**
     * 拼音码
     */
            @ExcelField(value ="拼音码")
private String spellCode;
    /**
     * 生产厂家
     */
            @ExcelField(value ="生产厂家")
private String produceArea;
    /**
     * 名字
     */
    @TableField("GYSNAME")
            @ExcelField(value ="名字")
private String gysname;

    /**
     * 退货原因
     */
    @TableField("SALE_CAUSE")
    @ExcelField(value ="退货原因")
    private String saleCause;
    /**
     * 批次号
     */
            @ExcelField(value ="批次号")
private String charg;
    /**
     * 退货数量
     */
            @ExcelField(value ="退货数量")
private BigDecimal amount;
    /**
     * 备注
     */
            @ExcelField(value ="备注")
private String comments;
    /**
     * 退货部门
     */
            @ExcelField(value ="退货部门")
private Long deptId;
    /**
     * 部门名称
     */
    @TableField("dept_id_Name")
            @ExcelField(value ="部门名称")
private String deptIdName;
    /**
     * 0是未提交1是提交2是审核完成3是供应商确认4退货完成
     */
            @ExcelField(value ="0是未提交1是提交2是审核完成3是供应商确认4退货完成")
private Integer state;


    public String getId(){
            return id;
            }

        public void setId(String id) {
            this.id = id;
            }

    public String getTxz01(){
            return txz01;
            }

        public void setTxz01(String txz01) {
            this.txz01 = txz01;
            }

    public String getGysaccount(){
            return gysaccount;
            }

        public void setGysaccount(String gysaccount) {
            this.gysaccount = gysaccount;
            }

    public String getMatnr(){
            return matnr;
            }

        public void setMatnr(String matnr) {
            this.matnr = matnr;
            }

    public String getSpec(){
            return spec;
            }

        public void setSpec(String spec) {
            this.spec = spec;
            }

    public String getSpellCode(){
            return spellCode;
            }

        public void setSpellCode(String spellCode) {
            this.spellCode = spellCode;
            }

    public String getProduceArea(){
            return produceArea;
            }

        public void setProduceArea(String produceArea) {
            this.produceArea = produceArea;
            }

    public String getGysname(){
            return gysname;
            }

        public void setGysname(String gysname) {
            this.gysname = gysname;
            }

    public String getCharg(){
            return charg;
            }

        public void setCharg(String charg) {
            this.charg = charg;
            }

    public BigDecimal getAmount(){
            return amount;
            }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
            }

    public String getComments(){
            return comments;
            }

        public void setComments(String comments) {
            this.comments = comments;
            }

    public Long getDeptId(){
            return deptId;
            }

        public void setDeptId(Long deptId) {
            this.deptId = deptId;
            }

    public String getDeptIdName(){
            return deptIdName;
            }

        public void setDeptIdName(String deptIdName) {
            this.deptIdName = deptIdName;
            }

    public Integer getState(){
            return state;
            }

        public void setState(Integer state) {
            this.state = state;
            }

    public String getSaleCause(){
        return saleCause;
    }

    public void setSaleCause(String saleCause) {
        this.saleCause = saleCause;
    }

    public static final String ID ="id" ;

    public static final String TXZ01 ="txz01" ;

    public static final String GYSACCOUNT ="gysaccount" ;

    public static final String MATNR ="matnr" ;

    public static final String SPEC ="spec" ;

    public static final String SPELL_CODE ="spell_code" ;

    public static final String PRODUCE_AREA ="produce_area" ;

    public static final String GYSNAME ="GYSNAME" ;

    public static final String CHARG ="charg" ;

    public static final String AMOUNT ="amount" ;

    public static final String COMMENTS ="comments" ;

    public static final String DEPT_ID ="dept_id" ;

    public static final String DEPT_ID_NAME ="dept_id_Name" ;

    public static final String STATE ="state" ;

@Override
public String toString() {
        return "VScmBSalereturn{" +
                ", id=" + id +
                ", txz01=" + txz01 +
                ", gysaccount=" + gysaccount +
                ", matnr=" + matnr +
                ", spec=" + spec +
                ", spellCode=" + spellCode +
                ", produceArea=" + produceArea +
                ", gysname=" + gysname +
                ", charg=" + charg +
                ", amount=" + amount +
                ", comments=" + comments +
                ", deptId=" + deptId +
                ", deptIdName=" + deptIdName +
                ", state=" + state +
        "}";
        }
        }