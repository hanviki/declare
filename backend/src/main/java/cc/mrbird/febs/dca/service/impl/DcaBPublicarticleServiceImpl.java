package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.dao.DcaBSciencepublishMapper;
import cc.mrbird.febs.dca.entity.DcaBPublicarticle;
import cc.mrbird.febs.dca.dao.DcaBPublicarticleMapper;
import cc.mrbird.febs.dca.entity.userXuhao;
import cc.mrbird.febs.dca.service.IDcaBPublicarticleService;
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
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-06
 */
@Slf4j
@Service("IDcaBPublicarticleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBPublicarticleServiceImpl extends ServiceImpl<DcaBPublicarticleMapper, DcaBPublicarticle> implements IDcaBPublicarticleService {

        @Autowired
       private DcaBSciencepublishMapper dcaBSciencepublishMapper;

        @Autowired
        IDcaBUserapplyService iDcaBUserapplyService;


@Override
public IPage<DcaBPublicarticle> findDcaBPublicarticles(QueryRequest request, DcaBPublicarticle dcaBPublicarticle){
        try{
        LambdaQueryWrapper<DcaBPublicarticle> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBPublicarticle::getIsDeletemark, 1);//1是未删 0是已删

        if (StringUtils.isNotBlank(dcaBPublicarticle.getUserAccount())) {
        queryWrapper.and(wrap->  wrap.eq(DcaBPublicarticle::getUserAccount, dcaBPublicarticle.getUserAccount()).or()
        .like(DcaBPublicarticle::getUserAccountName, dcaBPublicarticle.getUserAccount()));

        }
                if(StringUtils.isNotBlank(dcaBPublicarticle.getAuditManName())){// 年度 和高级、中级、初级
                        List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBPublicarticle.getAuditMan(),dcaBPublicarticle.getAuditManName());
                        if(userAccountsList.size()==0){
                                userAccountsList.add("qiuc09");
                        }
                        queryWrapper.in(DcaBPublicarticle::getUserAccount,userAccountsList);
                }
        if (dcaBPublicarticle.getState()!=null) {
        queryWrapper.eq(DcaBPublicarticle::getState, dcaBPublicarticle.getState());
        }
        if (dcaBPublicarticle.getAuditState()!=null && (dcaBPublicarticle.getAuditState()>=0)) {
        queryWrapper.eq(DcaBPublicarticle::getAuditState, dcaBPublicarticle.getAuditState());
        }

                if (dcaBPublicarticle.getAuditXuhaoS() != null && (dcaBPublicarticle.getAuditXuhaoS() > 0)) {
                        if(dcaBPublicarticle.getAuditXuhaoE() == null||dcaBPublicarticle.getAuditXuhaoE().equals(0)) {
                                dcaBPublicarticle.setAuditXuhaoE(dcaBPublicarticle.getAuditXuhaoS());
                        }
                        queryWrapper.apply(" dca_b_publicarticle.user_account in (select user_account from dca_b_user where patent_ranknum between "+dcaBPublicarticle.getAuditXuhaoS()+" and "+dcaBPublicarticle.getAuditXuhaoE()+")");
                }
                                if (StringUtils.isNotBlank(dcaBPublicarticle.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBPublicarticle.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBPublicarticle::getCreateTime, dcaBPublicarticle.getCreateTimeFrom())
                                .le(DcaBPublicarticle::getCreateTime, dcaBPublicarticle.getCreateTimeTo());
                                }

        Page<DcaBPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
                List<userXuhao> xuhaoList = this.dcaBSciencepublishMapper.getXuhao();
                IPage<DcaBPublicarticle> result = this.page(page, queryWrapper);
                for (DcaBPublicarticle item : result.getRecords()
                ) {
                        List<userXuhao> list2 = xuhaoList.stream().filter(p -> p.getUserAccount().equals(item.getUserAccount())).collect(Collectors.toList());
                        if (list2.size() > 0) {
                                item.setAuditXuhao(list2.get(0).getPatentRanknum());
                        }
                }
                return  result;
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBPublicarticle> findDcaBPublicarticleList (QueryRequest request, DcaBPublicarticle dcaBPublicarticle){
        try{
        Page<DcaBPublicarticle> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBPublicarticle(page,dcaBPublicarticle);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBPublicarticle(DcaBPublicarticle dcaBPublicarticle){
                dcaBPublicarticle.setId(UUID.randomUUID().toString());
        dcaBPublicarticle.setCreateTime(new Date());
        dcaBPublicarticle.setIsDeletemark(1);
        this.save(dcaBPublicarticle);
        }

@Override
@Transactional
public void updateDcaBPublicarticle(DcaBPublicarticle dcaBPublicarticle){
        dcaBPublicarticle.setModifyTime(new Date());
        this.baseMapper.updateDcaBPublicarticle(dcaBPublicarticle);
        }

@Override
@Transactional
public void deleteDcaBPublicarticles(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }
@Override
@Transactional
public  int getMaxDisplayIndexByuseraccount(String userAccount){
        return this.baseMapper.getMaxDisplayIndexByuseraccount(userAccount);
        }
        }