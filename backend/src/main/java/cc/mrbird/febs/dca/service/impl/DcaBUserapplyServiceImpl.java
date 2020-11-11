package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.DcaBUserapply;
import cc.mrbird.febs.dca.dao.DcaBUserapplyMapper;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
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

import java.util.Date;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.time.LocalDate;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-05
 */
@Slf4j
@Service("IDcaBUserapplyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUserapplyServiceImpl extends ServiceImpl<DcaBUserapplyMapper, DcaBUserapply> implements IDcaBUserapplyService {

    @Autowired
    private IDcaBUserService dcaBUserService;

@Override
public IPage<DcaBUserapply> findDcaBUserapplys(QueryRequest request, DcaBUserapply dcaBUserapply){
        try{
        LambdaQueryWrapper<DcaBUserapply> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUserapply.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount()).or()
                        .like(DcaBUserapply::getUserAccountName, dcaBUserapply.getUserAccount()));

            }
                                if (StringUtils.isNotBlank(dcaBUserapply.getDcaYear())) {
                                queryWrapper.eq(DcaBUserapply::getDcaYear, dcaBUserapply.getDcaYear());
                                }
            if (dcaBUserapply.getState()!=null &&dcaBUserapply.getState()>0) {
                queryWrapper.eq(DcaBUserapply::getState, dcaBUserapply.getState());
            }
            if (StringUtils.isNotBlank(dcaBUserapply.getNpPositionName())) {
                queryWrapper.like(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
            }

        Page<DcaBUserapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBUserapply> findDcaBUserapplyList (QueryRequest request, DcaBUserapply dcaBUserapply){
        try{
        Page<DcaBUserapply> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBUserapply(page,dcaBUserapply);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBUserapply(DcaBUserapply dcaBUserapply){
                dcaBUserapply.setId(UUID.randomUUID().toString());
        dcaBUserapply.setCreateTime(new Date());
        dcaBUserapply.setIsDeletemark(1);
    LambdaQueryWrapper<DcaBUser> queryWrapper2=new LambdaQueryWrapper<>();
    queryWrapper2.eq(DcaBUser::getUserAccount,dcaBUserapply.getUserAccount());
    DcaBUser user= this.dcaBUserService.getOne(queryWrapper2);
    user.setNpPositionName(dcaBUserapply.getNpPositionName());
    user.setDcaYear(dcaBUserapply.getDcaYear());
    dcaBUserapply.setUserAccountName(user.getUserAccountName());
    dcaBUserapply.setAppointedDate(user.getZygwDate());
    dcaBUserapply.setPositionName(user.getPositionName()==null?"":user.getPositionName());
    dcaBUserapply.setBirthday(user.getBirthday());
    dcaBUserapply.setKs(user.getKs());
    dcaBUserapply.setSchoolDate(user.getSchoolDate());
    dcaBUserapply.setSexName(user.getSexName());
    dcaBUserapply.setTelephone(user.getTelephone());
    dcaBUserapply.setXl(user.getXl());
    dcaBUserapply.setXcszyjzc(user.getXcszyjzc());
    dcaBUserapply.setZyjsgw(user.getPositionName());
    dcaBUserapply.setState(1);
        this.save(dcaBUserapply);
        this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
        }

@Override
@Transactional
public void updateDcaBUserapply(DcaBUserapply dcaBUserapply){
        dcaBUserapply.setModifyTime(new Date());
        dcaBUserapply.setState(1);
        this.baseMapper.updateDcaBUserapply(dcaBUserapply);

    LambdaQueryWrapper<DcaBUser> queryWrapper2=new LambdaQueryWrapper<>();
    queryWrapper2.eq(DcaBUser::getUserAccount,dcaBUserapply.getUserAccount());
    DcaBUser user= this.dcaBUserService.getOne(queryWrapper2);
    user.setNpPositionName(dcaBUserapply.getNpPositionName());
    user.setDcaYear(dcaBUserapply.getDcaYear());
    this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
        }
    @Override
    @Transactional
    public void updateDcaBUserapplyState(DcaBUserapply dcaBUserapply){

        LambdaQueryWrapper<DcaBUser> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(DcaBUser::getUserAccount,dcaBUserapply.getUserAccount());
        DcaBUser user= this.dcaBUserService.getOne(queryWrapper2);
        user.setNpPositionName("");

        this.baseMapper.updateDcaBUserapply(dcaBUserapply);;// 更改 申报职位
        this.dcaBUserService.updateDcaBUser(user);// 更改 申报职位
    }
@Override
@Transactional
public void deleteDcaBUserapplys(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

    @Override
    @Transactional
    public  boolean IsExistApply(DcaBUserapply dcaBUserapply){
        LambdaQueryWrapper<DcaBUserapply> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUserapply::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBUserapply::getDcaYear, dcaBUserapply.getDcaYear());
        queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
        if(dcaBUserapply.getId()!=null){
            queryWrapper.ne(DcaBUserapply::getId,dcaBUserapply.getId());
        }
        int count= this.baseMapper.selectCount(queryWrapper);
        if(count>0){
            return  false;
        }
        return  true;
    }
        }