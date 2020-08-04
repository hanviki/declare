package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.VScmbgysfpuser;
import cc.mrbird.febs.scm.dao.VScmbgysfpuserMapper;
import cc.mrbird.febs.scm.service.IVScmbgysfpuserService;
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
 * VIEW 服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-07-13
 */
@Slf4j
@Service("IVScmbgysfpuserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class VScmbgysfpuserServiceImpl extends ServiceImpl<VScmbgysfpuserMapper, VScmbgysfpuser> implements IVScmbgysfpuserService {


@Override
public IPage<VScmbgysfpuser> findVScmbgysfpusers(QueryRequest request, VScmbgysfpuser vScmbgysfpuser,String userId){
        try{
        LambdaQueryWrapper<VScmbgysfpuser> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.eq(VScmbgysfpuser::getUserid,userId);
                queryWrapper.eq(VScmbgysfpuser::getIsDeletemark, 1);//1是未删 0是已删

                if (StringUtils.isNotBlank(vScmbgysfpuser.getFpHm())) {
                        queryWrapper.like(VScmbgysfpuser::getFpHm, vScmbgysfpuser.getFpHm());
                }
                if (StringUtils.isNotBlank(vScmbgysfpuser.getKeyword_gys())) {
                        queryWrapper.and(qw -> qw.eq(VScmbgysfpuser::getGysaccount, vScmbgysfpuser.getKeyword_gys()).or().like(VScmbgysfpuser::getGysName, vScmbgysfpuser.getKeyword_gys()));
                }
                if(vScmbgysfpuser.getState()!=null){
                        queryWrapper.eq(VScmbgysfpuser::getState,vScmbgysfpuser.getState());
                }

        Page<VScmbgysfpuser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createVScmbgysfpuser(VScmbgysfpuser vScmbgysfpuser){
        vScmbgysfpuser.setId(UUID.randomUUID().toString());
        vScmbgysfpuser.setCreateTime(new Date());
        vScmbgysfpuser.setIsDeletemark(1);
        this.save(vScmbgysfpuser);
        }

@Override
@Transactional
public void updateVScmbgysfpuser(VScmbgysfpuser vScmbgysfpuser){
        vScmbgysfpuser.setModifyTime(new Date());
        this.baseMapper.updateVScmbgysfpuser(vScmbgysfpuser);
        }

@Override
@Transactional
public void deleteVScmbgysfpusers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }


        }