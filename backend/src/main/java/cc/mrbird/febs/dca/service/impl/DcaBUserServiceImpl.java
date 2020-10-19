package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBUser;
import cc.mrbird.febs.dca.dao.DcaBUserMapper;
import cc.mrbird.febs.dca.service.IDcaBUserService;
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
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-15
 */
@Slf4j
@Service("IDcaBUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBUserServiceImpl extends ServiceImpl<DcaBUserMapper, DcaBUser> implements IDcaBUserService {


@Override
public IPage<DcaBUser> findDcaBUsers(QueryRequest request, DcaBUser dcaBUser){
        try{
        LambdaQueryWrapper<DcaBUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBUser::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBUser.getUserAccount())) {
                                queryWrapper.like(DcaBUser::getUserAccount, dcaBUser.getUserAccount());
                                }
                                if (dcaBUser.getState()!=null) {
                                queryWrapper.eq(DcaBUser::getState, dcaBUser.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBUser.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBUser::getCreateTime, dcaBUser.getCreateTimeFrom())
                                .le(DcaBUser::getCreateTime, dcaBUser.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBUser.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBUser.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBUser::getModifyTime, dcaBUser.getModifyTimeFrom())
                                .le(DcaBUser::getModifyTime, dcaBUser.getModifyTimeTo());
                                }

        Page<DcaBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBUser> findDcaBUserList (QueryRequest request, DcaBUser dcaBUser){
        try{
        Page<DcaBUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBUser(page,dcaBUser);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBUser(DcaBUser dcaBUser){
                dcaBUser.setId(UUID.randomUUID().toString());
        dcaBUser.setCreateTime(new Date());
        dcaBUser.setIsDeletemark(1);
        this.save(dcaBUser);
        }

@Override
@Transactional
public void updateDcaBUser(DcaBUser dcaBUser){
        dcaBUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBUser(dcaBUser);
        }

@Override
@Transactional
public void deleteDcaBUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public  void deleteByuseraccount(String userAccount){
        this.baseMapper.deleteByAccount(userAccount);
        }

        }