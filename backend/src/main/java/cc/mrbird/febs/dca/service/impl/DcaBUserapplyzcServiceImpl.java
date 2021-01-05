package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.entity.DcaBUserapplyzc;
import cc.mrbird.febs.dca.dao.DcaBUserapplyzcMapper;
import cc.mrbird.febs.dca.service.IDcaBUserService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyzcService;
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
 * @since 2020-12-22
 */
@Slf4j
@Service("IDcaBUserapplyzcService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUserapplyzcServiceImpl extends ServiceImpl<DcaBUserapplyzcMapper, DcaBUserapplyzc> implements IDcaBUserapplyzcService {

    @Autowired
    private IDcaBUserService dcaBUserService;
@Override
public IPage<DcaBUserapplyzc> findDcaBUserapplyzcs(QueryRequest request, DcaBUserapplyzc dcaBUserapplyzc){
        try{
        LambdaQueryWrapper<DcaBUserapplyzc> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUserapplyzc::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBUserapplyzc.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBUserapplyzc::getUserAccount, dcaBUserapplyzc.getUserAccount()).or()
                        .like(DcaBUserapplyzc::getUserAccountName, dcaBUserapplyzc.getUserAccount()));

            }
                                if (StringUtils.isNotBlank(dcaBUserapplyzc.getDcaYear())) {
                                queryWrapper.like(DcaBUserapplyzc::getDcaYear, dcaBUserapplyzc.getDcaYear());
                                }
            if (dcaBUserapplyzc.getState()!=null &&dcaBUserapplyzc.getState()>0) {
                queryWrapper.eq(DcaBUserapplyzc::getState, dcaBUserapplyzc.getState());
            }
            if (StringUtils.isNotBlank(dcaBUserapplyzc.getGwdj())) {
                //queryWrapper.like(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
                String[] gwdjList=dcaBUserapplyzc.getGwdj().split(",");
                queryWrapper.in(DcaBUserapplyzc::getGwdj,gwdjList);
            }

        Page<DcaBUserapplyzc> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBUserapplyzc> findDcaBUserapplyzcList (QueryRequest request, DcaBUserapplyzc dcaBUserapplyzc){
        try{
        Page<DcaBUserapplyzc> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBUserapplyzc(page,dcaBUserapplyzc);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBUserapplyzc(DcaBUserapplyzc dcaBUserapplyzc){
                dcaBUserapplyzc.setId(UUID.randomUUID().toString());
        dcaBUserapplyzc.setCreateTime(new Date());
        dcaBUserapplyzc.setIsDeletemark(1);
    LambdaQueryWrapper<DcaBUser> queryWrapper2=new LambdaQueryWrapper<>();
    queryWrapper2.eq(DcaBUser::getUserAccount,dcaBUserapplyzc.getUserAccount());
    DcaBUser user= this.dcaBUserService.getOne(queryWrapper2);
    user.setNpPositionName(dcaBUserapplyzc.getNpPositionName());
    user.setDcaYear(dcaBUserapplyzc.getDcaYear());
    dcaBUserapplyzc.setUserAccountName(user.getUserAccountName());
    dcaBUserapplyzc.setAppointedDate(user.getZygwDate());
    dcaBUserapplyzc.setPositionName(user.getPositionName()==null?"":user.getPositionName());
    dcaBUserapplyzc.setBirthday(user.getBirthday());
    dcaBUserapplyzc.setKs(user.getKs());
    dcaBUserapplyzc.setSchoolDate(user.getSchoolDate());
    dcaBUserapplyzc.setSexName(user.getSexName());
    //dcaBUserapplyzc.setTelephone(user.getTelephone());
    dcaBUserapplyzc.setXl(user.getXl());
    dcaBUserapplyzc.setXcszyjzc(user.getXcszyjzc());
    dcaBUserapplyzc.setZyjsgw(user.getPositionName());
    dcaBUserapplyzc.setChujikhDate(user.getChujikhDate());
    dcaBUserapplyzc.setIsChujikh(user.getIsChujikh());
    dcaBUserapplyzc.setIsZhongjikh(user.getIsZhongjikh());
    dcaBUserapplyzc.setZhongjikhDate(user.getZhongjikhDate());
    dcaBUserapplyzc.setState(1);
        this.save(dcaBUserapplyzc);
        }

@Override
@Transactional
public void updateDcaBUserapplyzc(DcaBUserapplyzc dcaBUserapplyzc){
        dcaBUserapplyzc.setModifyTime(new Date());
        if(dcaBUserapplyzc.getState()!=null&&dcaBUserapplyzc.getState().equals(2)){
            dcaBUserapplyzc.setState(2);
        }
        else{
            dcaBUserapplyzc.setState(1);
        }

        this.baseMapper.updateDcaBUserapplyzc(dcaBUserapplyzc);
        }
    @Override
    @Transactional
    public  boolean IsExistApply(DcaBUserapplyzc dcaBUserapply){
        LambdaQueryWrapper<DcaBUserapplyzc> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUserapplyzc::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBUserapplyzc::getDcaYear, dcaBUserapply.getDcaYear());
        queryWrapper.eq(DcaBUserapplyzc::getUserAccount, dcaBUserapply.getUserAccount());
        if(dcaBUserapply.getId()!=null){
            queryWrapper.ne(DcaBUserapplyzc::getId,dcaBUserapply.getId());
        }
        int count= this.baseMapper.selectCount(queryWrapper);
        if(count>0){
            return  false;
        }
        return  true;
    }
@Override
@Transactional
public void deleteDcaBUserapplyzcs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBUserapplyzc> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBUserapplyzc> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBUserapplyzc::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBUserapplyzc::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        }