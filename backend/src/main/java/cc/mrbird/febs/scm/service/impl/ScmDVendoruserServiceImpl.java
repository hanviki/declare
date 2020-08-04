package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmDVendoruser;
import cc.mrbird.febs.scm.dao.ScmDVendoruserMapper;
import cc.mrbird.febs.scm.service.IScmDVendoruserService;
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
 * @since 2020-06-03
 */
@Slf4j
@Service("IScmDVendoruserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDVendoruserServiceImpl extends ServiceImpl<ScmDVendoruserMapper, ScmDVendoruser> implements IScmDVendoruserService {


@Override
public IPage<ScmDVendoruser> findScmDVendorusers(QueryRequest request, ScmDVendoruser scmDVendoruser){
        try{
        LambdaQueryWrapper<ScmDVendoruser> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(ScmDVendoruser::getIsDeletemark, 1);//1是未删 0是已删
        Page<ScmDVendoruser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmDVendoruser(ScmDVendoruser scmDVendoruser){
        scmDVendoruser.setId(UUID.randomUUID().toString());
        scmDVendoruser.setCreateTime(new Date());
        scmDVendoruser.setIsDeletemark(1);
        this.save(scmDVendoruser);
        }

@Override
@Transactional
public void updateScmDVendoruser(ScmDVendoruser scmDVendoruser){
        scmDVendoruser.setModifyTime(new Date());
        this.baseMapper.updateScmDVendoruser(scmDVendoruser);
        }

@Override
@Transactional
public void deleteScmDVendorusers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
        public ScmDVendoruser getUserInfo(String gys){
                return  this.baseMapper.getVendorusers(gys);
        }

        }