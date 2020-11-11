package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaBAttachfile;
import cc.mrbird.febs.dca.dao.DcaBAttachfileMapper;
import cc.mrbird.febs.dca.service.IDcaBAttachfileService;
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
 * 其他附件 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-10-20
 */
@Slf4j
@Service("IDcaBAttachfileService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBAttachfileServiceImpl extends ServiceImpl<DcaBAttachfileMapper, DcaBAttachfile> implements IDcaBAttachfileService {


@Override
public IPage<DcaBAttachfile> findDcaBAttachfiles(QueryRequest request, DcaBAttachfile dcaBAttachfile){
        try{
        LambdaQueryWrapper<DcaBAttachfile> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBAttachfile::getIsDeletemark, 1);//1是未删 0是已删

            if (StringUtils.isNotBlank(dcaBAttachfile.getUserAccount())) {
                queryWrapper.and(wrap->  wrap.eq(DcaBAttachfile::getUserAccount, dcaBAttachfile.getUserAccount()).or().like(DcaBAttachfile::getUserAccountName, dcaBAttachfile.getUserAccount()));
            }
                                if (dcaBAttachfile.getState()!=null) {
                                queryWrapper.eq(DcaBAttachfile::getState, dcaBAttachfile.getState());
                                }
                                if (StringUtils.isNotBlank(dcaBAttachfile.getCreateTimeFrom()) && StringUtils.isNotBlank(dcaBAttachfile.getCreateTimeTo())) {
                                queryWrapper
                                .ge(DcaBAttachfile::getCreateTime, dcaBAttachfile.getCreateTimeFrom())
                                .le(DcaBAttachfile::getCreateTime, dcaBAttachfile.getCreateTimeTo());
                                }
                                if (StringUtils.isNotBlank(dcaBAttachfile.getModifyTimeFrom()) && StringUtils.isNotBlank(dcaBAttachfile.getModifyTimeTo())) {
                                queryWrapper
                                .ge(DcaBAttachfile::getModifyTime, dcaBAttachfile.getModifyTimeFrom())
                                .le(DcaBAttachfile::getModifyTime, dcaBAttachfile.getModifyTimeTo());
                                }

        Page<DcaBAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBAttachfile> findDcaBAttachfileList (QueryRequest request, DcaBAttachfile dcaBAttachfile){
        try{
        Page<DcaBAttachfile> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBAttachfile(page,dcaBAttachfile);
        }catch(Exception e){
        log.error("获取其他附件失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBAttachfile(DcaBAttachfile dcaBAttachfile){
                dcaBAttachfile.setId(UUID.randomUUID().toString());
        dcaBAttachfile.setCreateTime(new Date());
        dcaBAttachfile.setIsDeletemark(1);
        this.save(dcaBAttachfile);
        }

@Override
@Transactional
public void updateDcaBAttachfile(DcaBAttachfile dcaBAttachfile){
        dcaBAttachfile.setModifyTime(new Date());
        this.baseMapper.updateDcaBAttachfile(dcaBAttachfile);
        }

@Override
@Transactional
public void deleteDcaBAttachfiles(String[]Ids){
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