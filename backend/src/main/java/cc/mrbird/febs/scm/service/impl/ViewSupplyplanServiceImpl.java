package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ScmBSupplyplanMapper;
import cc.mrbird.febs.scm.entity.ViewSupplyplan;
import cc.mrbird.febs.scm.dao.ViewSupplyplanMapper;
import cc.mrbird.febs.scm.service.IViewSupplyplanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-05
 */
@Slf4j
@Service("IViewSupplyplanService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ViewSupplyplanServiceImpl extends ServiceImpl<ViewSupplyplanMapper, ViewSupplyplan> implements IViewSupplyplanService {

    @Autowired
    ScmBSupplyplanMapper scmBSupplyplanMapper;
    @Override
    public IPage<ViewSupplyplan> findViewSupplyplans(QueryRequest request, ViewSupplyplan viewSupplyplan) {
        try {
            LambdaQueryWrapper<ViewSupplyplan> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(ViewSupplyplan::getIsDeletemark, 1);//1是未删 0是已删
            if (viewSupplyplan.getId() != null) {
                queryWrapper.eq(ViewSupplyplan::getId, viewSupplyplan.getId());
            }
            if (viewSupplyplan.getStatus() != null) {
                queryWrapper.eq(ViewSupplyplan::getStatus, viewSupplyplan.getStatus());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                queryWrapper.eq(ViewSupplyplan::getEbeln, viewSupplyplan.getEbeln());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getBaseId())) {
                queryWrapper.eq(ViewSupplyplan::getBaseId, viewSupplyplan.getBaseId());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerks()) && !viewSupplyplan.getWerks().equals("0")) {
                queryWrapper.eq(ViewSupplyplan::getWerks, viewSupplyplan.getWerks());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerkst())) {
                queryWrapper.like(ViewSupplyplan::getWerkst, viewSupplyplan.getWerkst());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                queryWrapper.like(ViewSupplyplan::getLgortName, viewSupplyplan.getLgortName());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgort()) && !viewSupplyplan.getLgort().equals("0")) {
                queryWrapper.eq(ViewSupplyplan::getLgort, viewSupplyplan.getLgort());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getGysname())) {
                queryWrapper.like(ViewSupplyplan::getGysname, viewSupplyplan.getGysname());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getGysaccount())) {
                queryWrapper.eq(ViewSupplyplan::getGysaccount, viewSupplyplan.getGysaccount());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                queryWrapper.like(ViewSupplyplan::getTxz01, viewSupplyplan.getTxz01());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                queryWrapper.eq(ViewSupplyplan::getMatnr, viewSupplyplan.getMatnr());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getNoOrder()) && viewSupplyplan.getNoOrder() == "1") {
                queryWrapper.and(p -> p.isNull(ViewSupplyplan::getSendOrderCode).or().eq(ViewSupplyplan::getSendOrderCode, ""));
            }
            Page<ViewSupplyplan> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<ViewSupplyplan> findViewSupplyplans2(QueryRequest request, ViewSupplyplan viewSupplyplan) {
        try {
            LambdaQueryWrapper<ViewSupplyplan> queryWrapper = new LambdaQueryWrapper<>();

            if (viewSupplyplan.getId() != null) {
                queryWrapper.eq(ViewSupplyplan::getId, viewSupplyplan.getId());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getBaseId())) {
                queryWrapper.eq(ViewSupplyplan::getBaseId, viewSupplyplan.getBaseId());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getGysaccount())) {
                queryWrapper.eq(ViewSupplyplan::getGysaccount, viewSupplyplan.getGysaccount());
            }
            if (viewSupplyplan.getStatus() != null) {
                queryWrapper.eq(ViewSupplyplan::getStatus, viewSupplyplan.getStatus());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                queryWrapper.eq(ViewSupplyplan::getWerks, viewSupplyplan.getWerks());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgort())) {
                queryWrapper.eq(ViewSupplyplan::getLgort, viewSupplyplan.getLgort());
            }
            if (viewSupplyplan.getBsartD() == "1") {
                if (StringUtils.isNotBlank(viewSupplyplan.getFphm())) {
                    queryWrapper.and(wrapper -> wrapper.eq(ViewSupplyplan::getFphm, viewSupplyplan.getFphm()).or().eq(ViewSupplyplan::getFphm, null
                    ).or().eq(ViewSupplyplan::getFphm, ""
                    ));
                } else {
                    queryWrapper.and(wrapper -> wrapper.isNull(ViewSupplyplan::getFphm)
                            .or().eq(ViewSupplyplan::getFphm, ""
                            ));
                }

            } else {
                queryWrapper.and(wrapper -> wrapper.isNull(ViewSupplyplan::getSendOrderCode).or().eq(ViewSupplyplan::getSendOrderCode, ""
                ).or().eq(ViewSupplyplan::getSendOrderCode, viewSupplyplan.getSendOrderCode()));
            }
            queryWrapper.eq(ViewSupplyplan::getBsartD, viewSupplyplan.getBsartD());//订单类型
            if (viewSupplyplan.getIsDeletemark() != null) {
                queryWrapper.eq(ViewSupplyplan::getIsDeletemark, viewSupplyplan.getIsDeletemark());
            }
            Page<ViewSupplyplan> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createViewSupplyplan(ViewSupplyplan viewSupplyplan) {

        viewSupplyplan.setCreateTime(new Date());
        viewSupplyplan.setIsDeletemark(1);
        this.save(viewSupplyplan);
    }

    @Override
    @Transactional
    public void updateViewSupplyplan(ViewSupplyplan viewSupplyplan) {
        viewSupplyplan.setModifyTime(new Date());
        this.baseMapper.updateViewSupplyplan(viewSupplyplan);
    }

    @Override
    @Transactional
    public void deleteViewSupplyplans(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }
    @Override
    @Transactional
    public Long findAreaCount(String username,String werks){
        return  this.baseMapper.findAreaCount(username,werks);
    }
    @Override
    @Transactional
    public List<ViewSupplyplan> getViewSupplyPlanByIds(String ids) {
        return this.baseMapper.GetViewSupplyPlanByIds(ids);
    }

    @Override
    @Transactional
    public List<ViewSupplyplan> getViewSupplyPlanByOrderId(String sendOrderId) {
        ViewSupplyplan viewSupplyplan=new ViewSupplyplan();
        viewSupplyplan.setSendOrderCode(sendOrderId);
        viewSupplyplan.setBedat(new Date());
        Calendar c = Calendar.getInstance();
        //过去一个月
        c.setTime(new Date());
        c.add(Calendar.MONTH, - 1);
        Date preMonth = c.getTime();
        viewSupplyplan.setEindt(preMonth);
        return this.baseMapper.findVPlanByOrderCode(sendOrderId);
    }

    @Override
    public IPage<ViewSupplyplan> findDoneViewSupplyplans(QueryRequest request, ViewSupplyplan viewSupplyplan, String statusType) {
        try {
            /**
            LambdaQueryWrapper<ViewSupplyplan> queryWrapper = new LambdaQueryWrapper<>();

            queryWrapper.eq(ViewSupplyplan::getIsDeletemark, 1);//1是未删 0是已删

            queryWrapper.eq(ViewSupplyplan::getBsartD, "0");
            if (StringUtils.equals(statusType, "0"))//预收
            {
                queryWrapper.eq(ViewSupplyplan::getStatus, 0);
            }
            if (StringUtils.equals(statusType, "1"))//已收
            {
                queryWrapper.eq(ViewSupplyplan::getStatus, 0);
                queryWrapper.gt(ViewSupplyplan::getDoneMenge, 0);
            }

            if (StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                queryWrapper.eq(ViewSupplyplan::getEbeln, viewSupplyplan.getEbeln());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getBaseId())) {
                queryWrapper.eq(ViewSupplyplan::getBaseId, viewSupplyplan.getBaseId());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                queryWrapper.eq(ViewSupplyplan::getWerks, viewSupplyplan.getWerks());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgort())) {
                queryWrapper.eq(ViewSupplyplan::getLgort, viewSupplyplan.getLgort());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerkst())) {
                queryWrapper.like(ViewSupplyplan::getWerkst, viewSupplyplan.getWerkst());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getGysname())) {
                queryWrapper.like(ViewSupplyplan::getGysname, viewSupplyplan.getGysname());
            }
            if (viewSupplyplan.getId()!=null) {
                queryWrapper.eq(ViewSupplyplan::getId, viewSupplyplan.getId());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getGysaccount())) {
                queryWrapper.eq(ViewSupplyplan::getGysaccount, viewSupplyplan.getGysaccount());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                queryWrapper.like(ViewSupplyplan::getTxz01, viewSupplyplan.getTxz01());
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                queryWrapper.eq(ViewSupplyplan::getMatnr, viewSupplyplan.getMatnr());
            }


            Page<ViewSupplyplan> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
             **/
            if (StringUtils.equals(statusType, "0"))//预收
            {
                viewSupplyplan.setStatus(0);
                viewSupplyplan.setDoneMenge(new BigDecimal(2));
            }
            if (StringUtils.equals(statusType, "1"))//已收
            {
                viewSupplyplan.setStatus(0);
                viewSupplyplan.setDoneMenge(new BigDecimal(0));
               // queryWrapper.eq(ViewSupplyplan::getStatus, 0);
               // queryWrapper.gt(ViewSupplyplan::getDoneMenge, 0);
            }
            Page<ViewSupplyplan> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            // return this.page(page, queryWrapper);
            IPage<ViewSupplyplan> listPage= this.baseMapper.findVPurcharseorder(page, viewSupplyplan);
            Boolean flag=false;
            Long totaNum=0L;
            if (StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getNoOrder())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgort())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerkst())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getKeyword_mater())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getEbelp())) {
                flag=true;
            }
            if (viewSupplyplan.getBedat()!=null) {
                flag=true;
            }
            if (viewSupplyplan.getEindt()!=null) {
                flag=true;
            }
            if(flag) {
                totaNum = this.baseMapper.findVPurcharseorder_COUNT(viewSupplyplan);
            }
            else{//在不搜索订单表的情况下
                totaNum = this.baseMapper.findVPurcharseorder_noOrder(viewSupplyplan);
            }
            listPage.setTotal(totaNum);
            return  listPage;
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

        @Override
        public IPage<ViewSupplyplan> findVPurcharseorder (QueryRequest request, ViewSupplyplan viewSupplyplan){
            try {
//            LambdaQueryWrapper<ScmBPurcharseorder> queryWrapper = new LambdaQueryWrapper<>();
//            if (StringUtils.isNotBlank(scmBPurcharseorder.getCode())) {
//                queryWrapper.eq(ScmBPurcharseorder::getCode, scmBPurcharseorder.getCode());
//            }
                Page<ViewSupplyplan> page = new Page<>();
                SortUtil.handlePageSort(request, page, false);
                page.setSearchCount(false);
                // return this.page(page, queryWrapper);
                IPage<ViewSupplyplan> listPage= this.baseMapper.findVPurcharseorder(page, viewSupplyplan);
                Boolean flag=false;
                Long totaNum=0L;
                if (StringUtils.isNotBlank(viewSupplyplan.getEbeln())) {
                     flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getNoOrder())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getLgort())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getWerkst())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                    flag=true;
                }
                if (StringUtils.isNotBlank(viewSupplyplan.getEbelp())) {
                    flag=true;
                }
                if (viewSupplyplan.getBedat()!=null) {
                    flag=true;
                }
                if (viewSupplyplan.getEindt()!=null) {
                    flag=true;
                }
                if(viewSupplyplan.getDoneMenge()!=null && viewSupplyplan.getDoneMenge().intValue()>0)
                {
                    flag=true;
                }
                if(flag) {
                     totaNum = this.baseMapper.findVPurcharseorder_COUNT(viewSupplyplan);
                }
                else{//在不搜索订单表的情况下
                     totaNum = this.baseMapper.findVPurcharseorder_noOrder(viewSupplyplan);
                }
                listPage.setTotal(totaNum);
                return  listPage;

            } catch (Exception e) {
                log.error("findVPurcharseorder", e);
                return null;
            }
        }

        @Override
        public  List<ViewSupplyplan> findPurcharseSendOrder(ViewSupplyplan viewSupplyplan) {
           return  this.baseMapper.findVPurcharseorder(viewSupplyplan);
        }
    @Override
    @Transactional
   public List<ViewSupplyplan> findVPlanByOrderCode(String orderCode){
        return  this.baseMapper.findVPlanByOrderCode(orderCode);
   }
    @Override
    @Transactional
    public IPage<ViewSupplyplan> findMatnrValid (QueryRequest request, ViewSupplyplan viewSupplyplan){
        try {

            Page<ViewSupplyplan> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
           // page.setSearchCount(false);
            // return this.page(page, queryWrapper);
            Boolean flag=false;
            Long totaNum=0L;
            if (StringUtils.isNotBlank(viewSupplyplan.getLgort())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getWerks())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getLgortName())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getMatnr())) {
                flag=true;
            }
            if (StringUtils.isNotBlank(viewSupplyplan.getTxz01())) {
                flag=true;
            }

                page.setSearchCount(false);

            IPage<ViewSupplyplan> listPage= this.baseMapper.findMatnrValid(page, viewSupplyplan);
            if(!flag) {
                totaNum = this.baseMapper.findMatnrValid_Count(viewSupplyplan);

            }
            else {
                totaNum = this.baseMapper.findMatnrValid_Count2(viewSupplyplan);
            }
            listPage.setTotal(totaNum);
            return  listPage;

        } catch (Exception e) {
            log.error("获取药品有效期", e);
            return null;
        }
    }
    @Override
    public List<ViewSupplyplan> getViewSupplyPlanByPdaId(String id){
        return  this.baseMapper.findVPurcharseorderForPda(id);
    }

}