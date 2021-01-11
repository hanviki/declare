package cc.mrbird.febs.dca.app;

import cc.mrbird.febs.dca.entity.DcaBWorknum;
import cc.mrbird.febs.dca.service.IDcaBWorknumService;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Common {
    @Autowired
    public IDcaBWorknumService iDcaBWorknumService;
    public void  InsertAuditResult(DcaBWorknum dcaBWorknum){
        LambdaQueryWrapper<DcaBWorknum> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBWorknum::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBWorknum::getUserAccount,dcaBWorknum.getUserAccount());
        queryWrapper.eq(DcaBWorknum::getYear,dcaBWorknum.getYear());
        List<DcaBWorknum> list=this.iDcaBWorknumService.list(queryWrapper);
        if(list.size()>0){
            dcaBWorknum.setId(list.get(0).getId());
            this.iDcaBWorknumService.updateDcaBWorknum(dcaBWorknum);
        }
        else{
            dcaBWorknum.setState(3);
            dcaBWorknum.setIsDeletemark(1);
            this.iDcaBWorknumService.createDcaBWorknum(dcaBWorknum);
        }
    }
    public void ConvertAuditResult(String titleType,String auditResult,DcaBWorknum dcaBWorknum2019,DcaBWorknum dcaBWorknum2020,DcaBWorknum dcaBWorknum2021){
        BigDecimal value= new BigDecimal(0);
        if(!StrUtil.isBlank(auditResult)){
            value= Convert.toBigDecimal(auditResult);
        }


        //region 病人工作量
        if(titleType.equals("ddqnmzgzl"))
        {
            dcaBWorknum2019.setMzbrl(value);
            //  return  "ddqnmzgzl";
        }
        if(titleType.equals("dqnmzgzl"))
        {
            dcaBWorknum2020.setMzbrl(value);
            //  return  "ddqnmzgzl";
        }
        if(titleType.equals("qnmzgzl"))
        {
            dcaBWorknum2021.setMzbrl(value);
        }
        //endregion
        //region 管理住院病人量
        if(titleType.equals("ddqnglzybrl"))
        {
            dcaBWorknum2019.setGlzybrl(value);
        }
        if(titleType.equals("dqnglzybrl"))
        {
            dcaBWorknum2020.setGlzybrl(value);
        }
        if(titleType.equals("qnglzybrl"))
        {
            dcaBWorknum2021.setGlzybrl(value);
        }
        //endregion
        //region 手术病人量(总)
        if(titleType.equals("ddqsybrl"))
        {
            dcaBWorknum2019.setSsbrl(value);
        }
        if(titleType.equals("dqnsybrl"))
        {
            dcaBWorknum2020.setSsbrl(value);
        }
        if(titleType.equals("qnsybrl"))
        {
            dcaBWorknum2021.setSsbrl(value);
        }
        //endregion

        //region手术量（1）
        if(titleType.equals("dqnssbrl1"))
        {
            dcaBWorknum2019.setSsbrl1(value);
        }
        if(titleType.equals("qnbrlss1"))
        {
            dcaBWorknum2020.setSsbrl1(value);
        }
        if(titleType.equals("qnbrssl1"))
        {
            dcaBWorknum2021.setSsbrl1(value);
        }
        //endregion
        //region手术量（2）
        if(titleType.equals("dqnssbrl2"))
        {
            dcaBWorknum2019.setSsbrl2(value);
        }
        if(titleType.equals("qnbrlss2"))
        {
            dcaBWorknum2020.setSsbrl2(value);
        }
        if(titleType.equals("qnbrssl2"))
        {
            dcaBWorknum2021.setSsbrl2(value);
        }
        //endregion
        //region手术量（3）
        if(titleType.equals("dqnssbrl3"))
        {
            dcaBWorknum2019.setSsbrl3(value);
        }
        if(titleType.equals("qnbrlss3"))
        {
            dcaBWorknum2020.setSsbrl3(value);
        }
        if(titleType.equals("qnbrssl3"))
        {
            dcaBWorknum2021.setSsbrl3(value);
        }
        //endregion
        //region手术量（4）
        if(titleType.equals("dqnssbrl4"))
        {
            dcaBWorknum2019.setSsbrl4(value);
        }
        if(titleType.equals("qnbrlss4"))
        {
            dcaBWorknum2020.setSsbrl4(value);
        }
        if(titleType.equals("qnbrssl4"))
        {
            dcaBWorknum2021.setSsbrl4(value);
        }
        //endregion
    }
}
