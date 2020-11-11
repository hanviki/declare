package cc.mrbird.febs.dca.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.DcaUserXl;
import cc.mrbird.febs.dca.dao.DcaUserXlMapper;
import cc.mrbird.febs.dca.service.IDcaUserXlService;
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
 * @since 2020-10-29
 */
@Slf4j
@Service("IDcaUserXlService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaUserXlServiceImpl extends ServiceImpl<DcaUserXlMapper, DcaUserXl> implements IDcaUserXlService {


@Override
public IPage<DcaUserXl> findDcaUserXls(QueryRequest request, DcaUserXl dcaUserXl){
        try{
        LambdaQueryWrapper<DcaUserXl> queryWrapper=new LambdaQueryWrapper<>();



        Page<DcaUserXl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaUserXl> findDcaUserXlList (QueryRequest request, DcaUserXl dcaUserXl){
        try{
        Page<DcaUserXl> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaUserXl(page,dcaUserXl);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaUserXl(DcaUserXl dcaUserXl){

        this.save(dcaUserXl);
        }

@Override
@Transactional
public void updateDcaUserXl(DcaUserXl dcaUserXl){

        this.baseMapper.updateDcaUserXl(dcaUserXl);
        }

@Override
@Transactional
public void deleteDcaUserXls(String[]Ids){
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
        @Override
        @Transactional
        public List<DcaUserXl> getMudulesByUserId(Integer userId){
                LambdaQueryWrapper<DcaUserXl> queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(DcaUserXl::getUserId,userId);
                return this.baseMapper.selectList(queryWrapper);
        }
        }