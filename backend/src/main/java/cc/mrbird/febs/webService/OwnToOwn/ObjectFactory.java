
package cc.mrbird.febs.webService.OwnToOwn;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cc.mrbird.febs.webService.OwnToOwn package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetUserWithoutOpenid_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getUserWithoutOpenid");
    private final static QName _GetFileValidMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getFileValidMsg");
    private final static QName _GetGysysauditNot_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getGysysauditNot");
    private final static QName _GetOrderModifyMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getOrderModifyMsgResponse");
    private final static QName _GetReportMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getReportMsgResponse");
    private final static QName _GetOrderModifyMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getOrderModifyMsg");
    private final static QName _GetPlanundoResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getPlanundoResponse");
    private final static QName _GetOrderMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getOrderMsgResponse");
    private final static QName _GetVendorauditResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getVendorauditResponse");
    private final static QName _GetGysysauditNotResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getGysysauditNotResponse");
    private final static QName _GetGysysaudit_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getGysysaudit");
    private final static QName _GetVendoraudit_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getVendoraudit");
    private final static QName _GetGysysauditResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getGysysauditResponse");
    private final static QName _GetMaterValidMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getMaterValidMsgResponse");
    private final static QName _SetUserOpenidResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "setUserOpenidResponse");
    private final static QName _GetOrderMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getOrderMsg");
    private final static QName _GetUserWithoutOpenidResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getUserWithoutOpenidResponse");
    private final static QName _SetUserOpenid_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "setUserOpenid");
    private final static QName _GetFileValidMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getFileValidMsgResponse");
    private final static QName _GetPlanundo_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getPlanundo");
    private final static QName _GetQueryPriceMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getQueryPriceMsg");
    private final static QName _GetReportMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getReportMsg");
    private final static QName _GetQueryPriceMsgResponse_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getQueryPriceMsgResponse");
    private final static QName _GetMaterValidMsg_QNAME = new QName("http://ScmJob.webService.febs.mrbird.cc/", "getMaterValidMsg");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cc.mrbird.febs.webService.OwnToOwn
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOrderMsg }
     * 
     */
    public GetOrderMsg createGetOrderMsg() {
        return new GetOrderMsg();
    }

    /**
     * Create an instance of {@link GetGysysauditResponse }
     * 
     */
    public GetGysysauditResponse createGetGysysauditResponse() {
        return new GetGysysauditResponse();
    }

    /**
     * Create an instance of {@link GetMaterValidMsgResponse }
     * 
     */
    public GetMaterValidMsgResponse createGetMaterValidMsgResponse() {
        return new GetMaterValidMsgResponse();
    }

    /**
     * Create an instance of {@link SetUserOpenidResponse }
     * 
     */
    public SetUserOpenidResponse createSetUserOpenidResponse() {
        return new SetUserOpenidResponse();
    }

    /**
     * Create an instance of {@link GetUserWithoutOpenidResponse }
     * 
     */
    public GetUserWithoutOpenidResponse createGetUserWithoutOpenidResponse() {
        return new GetUserWithoutOpenidResponse();
    }

    /**
     * Create an instance of {@link GetFileValidMsgResponse }
     * 
     */
    public GetFileValidMsgResponse createGetFileValidMsgResponse() {
        return new GetFileValidMsgResponse();
    }

    /**
     * Create an instance of {@link GetPlanundo }
     * 
     */
    public GetPlanundo createGetPlanundo() {
        return new GetPlanundo();
    }

    /**
     * Create an instance of {@link GetQueryPriceMsg }
     * 
     */
    public GetQueryPriceMsg createGetQueryPriceMsg() {
        return new GetQueryPriceMsg();
    }

    /**
     * Create an instance of {@link GetReportMsg }
     * 
     */
    public GetReportMsg createGetReportMsg() {
        return new GetReportMsg();
    }

    /**
     * Create an instance of {@link SetUserOpenid }
     * 
     */
    public SetUserOpenid createSetUserOpenid() {
        return new SetUserOpenid();
    }

    /**
     * Create an instance of {@link GetMaterValidMsg }
     * 
     */
    public GetMaterValidMsg createGetMaterValidMsg() {
        return new GetMaterValidMsg();
    }

    /**
     * Create an instance of {@link GetQueryPriceMsgResponse }
     * 
     */
    public GetQueryPriceMsgResponse createGetQueryPriceMsgResponse() {
        return new GetQueryPriceMsgResponse();
    }

    /**
     * Create an instance of {@link GetOrderModifyMsgResponse }
     * 
     */
    public GetOrderModifyMsgResponse createGetOrderModifyMsgResponse() {
        return new GetOrderModifyMsgResponse();
    }

    /**
     * Create an instance of {@link GetReportMsgResponse }
     * 
     */
    public GetReportMsgResponse createGetReportMsgResponse() {
        return new GetReportMsgResponse();
    }

    /**
     * Create an instance of {@link GetFileValidMsg }
     * 
     */
    public GetFileValidMsg createGetFileValidMsg() {
        return new GetFileValidMsg();
    }

    /**
     * Create an instance of {@link GetGysysauditNot }
     * 
     */
    public GetGysysauditNot createGetGysysauditNot() {
        return new GetGysysauditNot();
    }

    /**
     * Create an instance of {@link GetUserWithoutOpenid }
     * 
     */
    public GetUserWithoutOpenid createGetUserWithoutOpenid() {
        return new GetUserWithoutOpenid();
    }

    /**
     * Create an instance of {@link GetOrderMsgResponse }
     * 
     */
    public GetOrderMsgResponse createGetOrderMsgResponse() {
        return new GetOrderMsgResponse();
    }

    /**
     * Create an instance of {@link GetOrderModifyMsg }
     * 
     */
    public GetOrderModifyMsg createGetOrderModifyMsg() {
        return new GetOrderModifyMsg();
    }

    /**
     * Create an instance of {@link GetPlanundoResponse }
     * 
     */
    public GetPlanundoResponse createGetPlanundoResponse() {
        return new GetPlanundoResponse();
    }

    /**
     * Create an instance of {@link GetGysysauditNotResponse }
     * 
     */
    public GetGysysauditNotResponse createGetGysysauditNotResponse() {
        return new GetGysysauditNotResponse();
    }

    /**
     * Create an instance of {@link GetVendorauditResponse }
     * 
     */
    public GetVendorauditResponse createGetVendorauditResponse() {
        return new GetVendorauditResponse();
    }

    /**
     * Create an instance of {@link GetVendoraudit }
     * 
     */
    public GetVendoraudit createGetVendoraudit() {
        return new GetVendoraudit();
    }

    /**
     * Create an instance of {@link GetGysysaudit }
     * 
     */
    public GetGysysaudit createGetGysysaudit() {
        return new GetGysysaudit();
    }

    /**
     * Create an instance of {@link VMsgReportinfo }
     * 
     */
    public VMsgReportinfo createVMsgReportinfo() {
        return new VMsgReportinfo();
    }

    /**
     * Create an instance of {@link VMsgOrderinfo }
     * 
     */
    public VMsgOrderinfo createVMsgOrderinfo() {
        return new VMsgOrderinfo();
    }

    /**
     * Create an instance of {@link VMsgOrderinfomodify }
     * 
     */
    public VMsgOrderinfomodify createVMsgOrderinfomodify() {
        return new VMsgOrderinfomodify();
    }

    /**
     * Create an instance of {@link VMsgQuerypriceinfo }
     * 
     */
    public VMsgQuerypriceinfo createVMsgQuerypriceinfo() {
        return new VMsgQuerypriceinfo();
    }

    /**
     * Create an instance of {@link VMsgGysysaudit }
     * 
     */
    public VMsgGysysaudit createVMsgGysysaudit() {
        return new VMsgGysysaudit();
    }

    /**
     * Create an instance of {@link LocalDate }
     * 
     */
    public LocalDate createLocalDate() {
        return new LocalDate();
    }

    /**
     * Create an instance of {@link VMsgPlanundo }
     * 
     */
    public VMsgPlanundo createVMsgPlanundo() {
        return new VMsgPlanundo();
    }

    /**
     * Create an instance of {@link VMsgVendoraudit }
     * 
     */
    public VMsgVendoraudit createVMsgVendoraudit() {
        return new VMsgVendoraudit();
    }

    /**
     * Create an instance of {@link VMsgGysysauditnot }
     * 
     */
    public VMsgGysysauditnot createVMsgGysysauditnot() {
        return new VMsgGysysauditnot();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link VMsgMaterinfovalid }
     * 
     */
    public VMsgMaterinfovalid createVMsgMaterinfovalid() {
        return new VMsgMaterinfovalid();
    }

    /**
     * Create an instance of {@link VMsgFilevalidinfo }
     * 
     */
    public VMsgFilevalidinfo createVMsgFilevalidinfo() {
        return new VMsgFilevalidinfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserWithoutOpenid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getUserWithoutOpenid")
    public JAXBElement<GetUserWithoutOpenid> createGetUserWithoutOpenid(GetUserWithoutOpenid value) {
        return new JAXBElement<GetUserWithoutOpenid>(_GetUserWithoutOpenid_QNAME, GetUserWithoutOpenid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileValidMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getFileValidMsg")
    public JAXBElement<GetFileValidMsg> createGetFileValidMsg(GetFileValidMsg value) {
        return new JAXBElement<GetFileValidMsg>(_GetFileValidMsg_QNAME, GetFileValidMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGysysauditNot }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getGysysauditNot")
    public JAXBElement<GetGysysauditNot> createGetGysysauditNot(GetGysysauditNot value) {
        return new JAXBElement<GetGysysauditNot>(_GetGysysauditNot_QNAME, GetGysysauditNot.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderModifyMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getOrderModifyMsgResponse")
    public JAXBElement<GetOrderModifyMsgResponse> createGetOrderModifyMsgResponse(GetOrderModifyMsgResponse value) {
        return new JAXBElement<GetOrderModifyMsgResponse>(_GetOrderModifyMsgResponse_QNAME, GetOrderModifyMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getReportMsgResponse")
    public JAXBElement<GetReportMsgResponse> createGetReportMsgResponse(GetReportMsgResponse value) {
        return new JAXBElement<GetReportMsgResponse>(_GetReportMsgResponse_QNAME, GetReportMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderModifyMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getOrderModifyMsg")
    public JAXBElement<GetOrderModifyMsg> createGetOrderModifyMsg(GetOrderModifyMsg value) {
        return new JAXBElement<GetOrderModifyMsg>(_GetOrderModifyMsg_QNAME, GetOrderModifyMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanundoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getPlanundoResponse")
    public JAXBElement<GetPlanundoResponse> createGetPlanundoResponse(GetPlanundoResponse value) {
        return new JAXBElement<GetPlanundoResponse>(_GetPlanundoResponse_QNAME, GetPlanundoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getOrderMsgResponse")
    public JAXBElement<GetOrderMsgResponse> createGetOrderMsgResponse(GetOrderMsgResponse value) {
        return new JAXBElement<GetOrderMsgResponse>(_GetOrderMsgResponse_QNAME, GetOrderMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendorauditResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getVendorauditResponse")
    public JAXBElement<GetVendorauditResponse> createGetVendorauditResponse(GetVendorauditResponse value) {
        return new JAXBElement<GetVendorauditResponse>(_GetVendorauditResponse_QNAME, GetVendorauditResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGysysauditNotResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getGysysauditNotResponse")
    public JAXBElement<GetGysysauditNotResponse> createGetGysysauditNotResponse(GetGysysauditNotResponse value) {
        return new JAXBElement<GetGysysauditNotResponse>(_GetGysysauditNotResponse_QNAME, GetGysysauditNotResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGysysaudit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getGysysaudit")
    public JAXBElement<GetGysysaudit> createGetGysysaudit(GetGysysaudit value) {
        return new JAXBElement<GetGysysaudit>(_GetGysysaudit_QNAME, GetGysysaudit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVendoraudit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getVendoraudit")
    public JAXBElement<GetVendoraudit> createGetVendoraudit(GetVendoraudit value) {
        return new JAXBElement<GetVendoraudit>(_GetVendoraudit_QNAME, GetVendoraudit.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetGysysauditResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getGysysauditResponse")
    public JAXBElement<GetGysysauditResponse> createGetGysysauditResponse(GetGysysauditResponse value) {
        return new JAXBElement<GetGysysauditResponse>(_GetGysysauditResponse_QNAME, GetGysysauditResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaterValidMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getMaterValidMsgResponse")
    public JAXBElement<GetMaterValidMsgResponse> createGetMaterValidMsgResponse(GetMaterValidMsgResponse value) {
        return new JAXBElement<GetMaterValidMsgResponse>(_GetMaterValidMsgResponse_QNAME, GetMaterValidMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserOpenidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "setUserOpenidResponse")
    public JAXBElement<SetUserOpenidResponse> createSetUserOpenidResponse(SetUserOpenidResponse value) {
        return new JAXBElement<SetUserOpenidResponse>(_SetUserOpenidResponse_QNAME, SetUserOpenidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrderMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getOrderMsg")
    public JAXBElement<GetOrderMsg> createGetOrderMsg(GetOrderMsg value) {
        return new JAXBElement<GetOrderMsg>(_GetOrderMsg_QNAME, GetOrderMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserWithoutOpenidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getUserWithoutOpenidResponse")
    public JAXBElement<GetUserWithoutOpenidResponse> createGetUserWithoutOpenidResponse(GetUserWithoutOpenidResponse value) {
        return new JAXBElement<GetUserWithoutOpenidResponse>(_GetUserWithoutOpenidResponse_QNAME, GetUserWithoutOpenidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserOpenid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "setUserOpenid")
    public JAXBElement<SetUserOpenid> createSetUserOpenid(SetUserOpenid value) {
        return new JAXBElement<SetUserOpenid>(_SetUserOpenid_QNAME, SetUserOpenid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileValidMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getFileValidMsgResponse")
    public JAXBElement<GetFileValidMsgResponse> createGetFileValidMsgResponse(GetFileValidMsgResponse value) {
        return new JAXBElement<GetFileValidMsgResponse>(_GetFileValidMsgResponse_QNAME, GetFileValidMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPlanundo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getPlanundo")
    public JAXBElement<GetPlanundo> createGetPlanundo(GetPlanundo value) {
        return new JAXBElement<GetPlanundo>(_GetPlanundo_QNAME, GetPlanundo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQueryPriceMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getQueryPriceMsg")
    public JAXBElement<GetQueryPriceMsg> createGetQueryPriceMsg(GetQueryPriceMsg value) {
        return new JAXBElement<GetQueryPriceMsg>(_GetQueryPriceMsg_QNAME, GetQueryPriceMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getReportMsg")
    public JAXBElement<GetReportMsg> createGetReportMsg(GetReportMsg value) {
        return new JAXBElement<GetReportMsg>(_GetReportMsg_QNAME, GetReportMsg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQueryPriceMsgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getQueryPriceMsgResponse")
    public JAXBElement<GetQueryPriceMsgResponse> createGetQueryPriceMsgResponse(GetQueryPriceMsgResponse value) {
        return new JAXBElement<GetQueryPriceMsgResponse>(_GetQueryPriceMsgResponse_QNAME, GetQueryPriceMsgResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMaterValidMsg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ScmJob.webService.febs.mrbird.cc/", name = "getMaterValidMsg")
    public JAXBElement<GetMaterValidMsg> createGetMaterValidMsg(GetMaterValidMsg value) {
        return new JAXBElement<GetMaterValidMsg>(_GetMaterValidMsg_QNAME, GetMaterValidMsg.class, null, value);
    }

}
