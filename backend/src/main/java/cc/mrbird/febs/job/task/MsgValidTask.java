package cc.mrbird.febs.job.task;

import cc.mrbird.febs.common.utils.WxMessage;
import cc.mrbird.febs.webService.OwnToOwn.*;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class MsgValidTask {
    /**  这是消息提醒  在后台发布时候必须注释掉
    cc.mrbird.febs.webService.OwnToOwn.IScmJobService iScmJobService;
    ScmJobImplService  scmJobImplService=new ScmJobImplService();


    public void runFileValid(String openids){
        iScmJobService=scmJobImplService.getSapPort();
        List<VMsgFilevalidinfo> listMsg=iScmJobService.getFileValidMsg();
        WxMessage wm=new WxMessage();
        String[] open_ids = openids.split(StringPool.COMMA);
        for (VMsgFilevalidinfo msg:listMsg
             ) {
            if(StringUtils.isNotBlank(msg.getVxcode())) {
             //   String mg =  msg.getName() + msg.getFilename() + "将于" + msg.getDays() + "天后过期";
                String mg = msg.getFilename().replace("中华人民共和国","") +"将于" + msg.getDays() + "天后过期";
                wm.send(msg.getVxcode(), "资质效期", mg, "action=zzxq&id=" + msg.getCode());//供应商部分
                for (String openid ://医院部分的信息
                        open_ids) {
                    log.info("医院端 openid："+openid+",资质效期");
                    wm.send(openid, "资质效期", mg, "action=zzxq&id=" + msg.getCode());
                }
            }
        }
    }

    public  void runOrderInfo(){
        iScmJobService=scmJobImplService.getSapPort();
        List<VMsgOrderinfo> listMsg=iScmJobService.getOrderMsg();
       // List<VMsgOrderinfo> listMsg=this.iVMsgOrderinfoService.GetMsgFileValid();
        WxMessage wm=new WxMessage();
        for (VMsgOrderinfo msg:listMsg
        ) {
            if(StringUtils.isNotBlank(msg.getVxcode())) {
                String mg = "您收到来自武汉协和医院的" + msg.getNumCount() + "个药品采购项目";
                log.info("openid："+msg.getVxcode()+",订单信息");
                wm.send(msg.getVxcode(), "订单信息", mg, "action=dingdan&id=" + msg.getCode());//供应商部分
            }
        }
    }

    public  void runOrderInfoModify(){
        iScmJobService=scmJobImplService.getSapPort();
        List<VMsgOrderinfomodify> listMsg=iScmJobService.getOrderModifyMsg();
      //  List<VMsgOrderinfomodify> listMsg=this.iVMsgOrderinfomodifyService.GetMsgFileValid();
        WxMessage wm=new WxMessage();
        for (VMsgOrderinfomodify msg:listMsg
        ) {
            if(StringUtils.isNotBlank(msg.getVxcode())) {
                String mg = "您收到来自武汉协和医院" + msg.getNumCount() + "个订单变更信息";
                log.info("openid："+msg.getVxcode()+",订单信息更改");
                wm.send(msg.getVxcode(), "采购订单", mg, "action=dingdan&id=" + msg.getCode());//供应商部分
            }
        }
    }

        public  void runMaterInfoValid(String openids){
        iScmJobService=scmJobImplService.getSapPort();
        List<VMsgMaterinfovalid> listMsg=iScmJobService.getMaterValidMsg();
      //  List<VMsgMaterinfovalid> listMsg=this.iVMsgMaterinfovalidService.GetMsgFileValid();
        WxMessage wm=new WxMessage();
        String[] open_ids = openids.split(StringPool.COMMA);
        for (VMsgMaterinfovalid msg:listMsg
        ) {
            String mg="由供应商"+msg.getLifnr()+msg.getGysName()+"提供的批次为"+msg.getCharge()+"的"+msg.getMatnr()+msg.getTxz01()+"将于"+msg.getDays()+"天后过期，请知悉！";
                for (String openid ://医院部分的信息
                        open_ids) {
                    log.info("openid："+openid+",药品效期");
                    wm.send(openid, "药品效期", mg, "action=zzxq&id=" + msg.getLifnr());
                }

        }
    }

    public  void runQueryPriceInfo(){
        iScmJobService=scmJobImplService.getSapPort();
        List<VMsgQuerypriceinfo> listMsg=iScmJobService.getQueryPriceMsg();
      //  List<VMsgQuerypriceinfo> listMsg= .GetMsgFileValid();
        WxMessage wm=new WxMessage();
        for (VMsgQuerypriceinfo msg:listMsg
        ) {
            if(StringUtils.isNotBlank(msg.getVxCode())) {
                String mg = "您有一条来自于武汉协和医院的询价信息";
                log.info("openid："+msg.getVxCode()+",询价请求");
                wm.send(msg.getVxCode(), "询价请求", mg, "action=xunjia&id=" + msg.getGysaccount());//供应商部分
            }
        }
    }

    public  void runReportInfo() {
        iScmJobService = scmJobImplService.getSapPort();
        List<VMsgReportinfo> listMsg = iScmJobService.getReportMsg();
        //  List<VMsgReportinfo> listMsg=this.iVMsgReportinfoService.GetMsgFileValid();
        WxMessage wm = new WxMessage();
        for (VMsgReportinfo msg : listMsg
        ) {
            if (StringUtils.isNotBlank(msg.getVxCode())) {
                String mg = "您有一条来自于武汉协和医院平台公告";
                log.info("openid：" + msg.getVxCode() + ",平台公告");
                wm.send(msg.getVxCode(), "平台公告", mg, "action=gonggao&id=" + msg.getCode());//供应商部分
            }

        }
       // wm.send("opSQh5VNuPHqJ_vVmF_u52VYA0F8", "消息通知", "您有一条来自于武汉协和医院通知信息", "action=zzsh&id=供应商ID=10000466");
    }

    public void runGetUsers(){
        iScmJobService=scmJobImplService.getSapPort();
        List<User> listMsg=iScmJobService.getUserWithoutOpenid();
        WxMessage wm=new WxMessage();
        for (User user:listMsg
             ) {
            log.error("微信前code"+user.getCode());
           String openid= wm.gentWxId(user.getCode().replace("2020",""));
            log.error("微信openid"+openid);
          user.setCode(openid);
        }
        iScmJobService.setUserOpenid(listMsg);
    }

    public  void runYanShouNot() {//验收报告 审核不通过
        iScmJobService = scmJobImplService.getSapPort();
        List<VMsgGysysauditnot> listMsg = iScmJobService.getGysysauditNot();
        //  List<VMsgReportinfo> listMsg=this.iVMsgReportinfoService.GetMsgFileValid();
        WxMessage wm = new WxMessage();
        for (VMsgGysysauditnot msg : listMsg
        ) {
            if (StringUtils.isNotBlank(msg.getVxCode())) {
                String mg = "您有一条检验报告审核不通过";
                log.info("openid：" + msg.getVxCode() + ",检验报告审核不通过");
                wm.send(msg.getVxCode(), "检验报告审核不通过", mg, "action=jybgshbtg&id="+msg.getId());
            }

        }
    }
    public  void runYanShouAudit() {//验收报告待审核
        iScmJobService = scmJobImplService.getSapPort();
        List<VMsgGysysaudit> listMsg = iScmJobService.getGysysaudit();
        //  List<VMsgReportinfo> listMsg=this.iVMsgReportinfoService.GetMsgFileValid();
        WxMessage wm = new WxMessage();
        for (VMsgGysysaudit msg : listMsg
        ) {
            if (StringUtils.isNotBlank(msg.getVxCode())) {
                String mg = "您有一条检验报告审核待审核";
                log.info("openid：" + msg.getVxCode() + ",检验报告审核");
                wm.send(msg.getVxCode(), "检验报告审核", mg, "action=jybgsh&id="+msg.getId());
            }

        }
    }
    public  void runPlanundo() {//供应计划数量不对等的
        iScmJobService = scmJobImplService.getSapPort();
        List<VMsgPlanundo> listMsg = iScmJobService.getPlanundo();
        //  List<VMsgReportinfo> listMsg=this.iVMsgReportinfoService.GetMsgFileValid();
        WxMessage wm = new WxMessage();
        for (VMsgPlanundo msg : listMsg
        ) {
            if (StringUtils.isNotBlank(msg.getVxCode())) {
                String mg = msg.getTxz01() +"供应数量小于订单数量";
                wm.send(msg.getVxCode(), "供应数量", mg, "action=plan&id="+msg.getId());
            }

        }
    }**/
}
