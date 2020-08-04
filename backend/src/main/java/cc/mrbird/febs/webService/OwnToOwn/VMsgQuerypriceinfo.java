
package cc.mrbird.febs.webService.OwnToOwn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>vMsgQuerypriceinfo complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="vMsgQuerypriceinfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="gysaccount" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gysname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="matnr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="txz01" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="vxCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "vMsgQuerypriceinfo", propOrder = {
    "gysaccount",
    "gysname",
    "matnr",
    "txz01",
    "vxCode"
})
public class VMsgQuerypriceinfo {

    protected String gysaccount;
    protected String gysname;
    protected String matnr;
    protected String txz01;
    protected String vxCode;

    /**
     * 获取gysaccount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGysaccount() {
        return gysaccount;
    }

    /**
     * 设置gysaccount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGysaccount(String value) {
        this.gysaccount = value;
    }

    /**
     * 获取gysname属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGysname() {
        return gysname;
    }

    /**
     * 设置gysname属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGysname(String value) {
        this.gysname = value;
    }

    /**
     * 获取matnr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatnr() {
        return matnr;
    }

    /**
     * 设置matnr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatnr(String value) {
        this.matnr = value;
    }

    /**
     * 获取txz01属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxz01() {
        return txz01;
    }

    /**
     * 设置txz01属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxz01(String value) {
        this.txz01 = value;
    }

    /**
     * 获取vxCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVxCode() {
        return vxCode;
    }

    /**
     * 设置vxCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVxCode(String value) {
        this.vxCode = value;
    }

}
