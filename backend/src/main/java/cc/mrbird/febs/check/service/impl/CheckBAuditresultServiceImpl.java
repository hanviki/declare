package cc.mrbird.febs.check.service.impl;

import cc.mrbird.febs.check.entity.TotalResultNum;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.check.entity.CheckBAuditresult;
import cc.mrbird.febs.check.dao.CheckBAuditresultMapper;
import cc.mrbird.febs.check.service.ICheckBAuditresultService;
import com.baomidou.dynamic.datasource.annotation.DS;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 * 指标结果表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2021-01-22
 */
@Slf4j
@Service("ICheckBAuditresultService")
@DS("slave")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CheckBAuditresultServiceImpl extends ServiceImpl<CheckBAuditresultMapper, CheckBAuditresult> implements ICheckBAuditresultService {


@Override
@DS("slave")
public IPage<CheckBAuditresult> findCheckBAuditresults(QueryRequest request, CheckBAuditresult checkBAuditresult){
        try{
        LambdaQueryWrapper<CheckBAuditresult> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckBAuditresult::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(checkBAuditresult.getUserAccountName())) {
                                queryWrapper.like(CheckBAuditresult::getUserAccountName, checkBAuditresult.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(checkBAuditresult.getUserAccount())) {
                                queryWrapper.like(CheckBAuditresult::getUserAccount, checkBAuditresult.getUserAccount());
                                }
                                if (StringUtils.isNotBlank(checkBAuditresult.getDcaYear())) {
                                queryWrapper.like(CheckBAuditresult::getDcaYear, checkBAuditresult.getDcaYear());
                                }

        Page<CheckBAuditresult> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
@DS("slave")
public IPage<CheckBAuditresult> findCheckBAuditresultList (QueryRequest request, CheckBAuditresult checkBAuditresult){
        try{
        Page<CheckBAuditresult> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findCheckBAuditresult(page,checkBAuditresult);
        }catch(Exception e){
        log.error("获取指标结果表失败" ,e);
        return null;
        }
        }
@Override
@Transactional
@DS("slave")
public void createCheckBAuditresult(CheckBAuditresult checkBAuditresult){
                checkBAuditresult.setId(UUID.randomUUID().toString());
        checkBAuditresult.setCreateTime(new Date());
        checkBAuditresult.setIsDeletemark(1);
        this.save(checkBAuditresult);
        }

@Override
@Transactional
@DS("slave")
public void updateCheckBAuditresult(CheckBAuditresult checkBAuditresult){
        checkBAuditresult.setModifyTime(new Date());
        this.baseMapper.updateCheckBAuditresult(checkBAuditresult);
        }

@Override
@Transactional
@DS("slave")
public void deleteCheckBAuditresults(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
@DS("slave")
public List<CheckBAuditresult> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<CheckBAuditresult> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.apply("check_b_auditresult.check_user_id ='"+userAccount+"'");
        }

        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(CheckBAuditresult::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }
    @Override
    @Transactional
    @DS("slave")
    public void deleteBy(List<String> accounts,List<String> dataIndexList,String userName,String dcaYear){
        LambdaQueryWrapper<CheckBAuditresult> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CheckBAuditresult::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.in(CheckBAuditresult::getUserAccount,accounts);
        queryWrapper.eq(CheckBAuditresult::getCheckUserId,userName);
        queryWrapper.eq(CheckBAuditresult::getDcaYear,dcaYear);
        queryWrapper.in(CheckBAuditresult::getAuditTitletype,dataIndexList);
        this.baseMapper.delete(queryWrapper);
    }

    @Override
    @Transactional
    @DS("slave")
    public List<TotalResultNum> findTotalResult(CheckBAuditresult checkBAuditresult){
       return this.baseMapper.findTotalResult(checkBAuditresult);
    }
    @Override
    @Transactional
    @DS("slave")
    public List<TotalResultNum> findStrResult(CheckBAuditresult checkBAuditresult){
        return this.baseMapper.findStrResult(checkBAuditresult);
    }
        }