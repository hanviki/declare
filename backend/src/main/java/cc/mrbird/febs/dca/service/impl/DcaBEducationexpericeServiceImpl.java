package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBEducationexperice;
import cc.mrbird.febs.dca.dao.DcaBEducationexpericeMapper;
import cc.mrbird.febs.dca.service.IDcaBEducationexpericeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
/**
 * <p>
 * 学习工作经历 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-09-15
 */
@Slf4j
@Service("IDcaBEducationexpericeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBEducationexpericeServiceImpl extends ServiceImpl<DcaBEducationexpericeMapper, DcaBEducationexperice> implements IDcaBEducationexpericeService {


    @Autowired
    IDcaBUserapplyService iDcaBUserapplyService;
@Override
public IPage<DcaBEducationexperice> findDcaBEducationexperices(QueryRequest request, DcaBEducationexperice dcaBEducationexperice){
        try{
        LambdaQueryWrapper<DcaBEducationexperice> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBEducationexperice::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBEducationexperice.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBEducationexperice::getUserAccount, dcaBEducationexperice.getUserAccount()).or().like(DcaBEducationexperice::getUserAccountName, dcaBEducationexperice.getUserAccount()));
            }

            if(StringUtils.isNotBlank(dcaBEducationexperice.getAuditManName())){// 年度 和高级、中级、初级
                List<String> userAccountsList=this.iDcaBUserapplyService.getApplyAccount(dcaBEducationexperice.getAuditMan(),dcaBEducationexperice.getAuditManName());
                if(userAccountsList.size()==0){
                    userAccountsList.add("qiuc09");
                }
                queryWrapper.in(DcaBEducationexperice::getUserAccount,userAccountsList);
            }
                                if (dcaBEducationexperice.getState()!=null) {
                                queryWrapper.eq(DcaBEducationexperice::getState, dcaBEducationexperice.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBEducationexperice.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBEducationexperice.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBEducationexperice::getCreateTime, dcaBEducationexperice.getCreateTimeFrom())
                                .le(DcaBEducationexperice::getCreateTime, dcaBEducationexperice.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBEducationexperice.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBEducationexperice.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBEducationexperice::getModifyTime, dcaBEducationexperice.getModifyTimeFrom())
                                .le(DcaBEducationexperice::getModifyTime, dcaBEducationexperice.getModifyTimeTo());
                                }

        Page<DcaBEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBEducationexperice> findDcaBEducationexpericeList (QueryRequest request, DcaBEducationexperice dcaBEducationexperice){
        try{
        Page<DcaBEducationexperice> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBEducationexperice(page,dcaBEducationexperice);
        }catch(Exception e){
        log.error("获取学习工作经历失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBEducationexperice(DcaBEducationexperice dcaBEducationexperice){
                dcaBEducationexperice.setId(UUID.randomUUID().toString());
        dcaBEducationexperice.setCreateTime(new Date());
        dcaBEducationexperice.setIsDeletemark(1);
        this.save(dcaBEducationexperice);
        }

@Override
@Transactional
public void updateDcaBEducationexperice(DcaBEducationexperice dcaBEducationexperice){
        dcaBEducationexperice.setModifyTime(new Date());
        this.baseMapper.updateDcaBEducationexperice(dcaBEducationexperice);
        }

@Override
@Transactional
public void deleteDcaBEducationexperices(String[]Ids){
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