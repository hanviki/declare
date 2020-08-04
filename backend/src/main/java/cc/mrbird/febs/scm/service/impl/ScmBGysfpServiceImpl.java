package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBGysfp;
import cc.mrbird.febs.scm.dao.ScmBGysfpMapper;
import cc.mrbird.febs.scm.service.IScmBGysfpService;
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
 * @since 2020-07-10
 */
@Slf4j
@Service("IScmBGysfpService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBGysfpServiceImpl extends ServiceImpl<ScmBGysfpMapper, ScmBGysfp> implements IScmBGysfpService {


@Override
public IPage<ScmBGysfp> findScmBGysfps(QueryRequest request, ScmBGysfp scmBGysfp){
        try{
        LambdaQueryWrapper<ScmBGysfp> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ScmBGysfp::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(scmBGysfp.getFpHm())) {
                        queryWrapper.like(ScmBGysfp::getFpHm, scmBGysfp.getFpHm());
                }
            if (StringUtils.isNotBlank(scmBGysfp.getGysaccount())) {
                queryWrapper.like(ScmBGysfp::getGysaccount, scmBGysfp.getGysaccount());
            }
                if (StringUtils.isNotBlank(scmBGysfp.getKeyword_gys())) {
                        queryWrapper.and(qw -> qw.eq(ScmBGysfp::getGysaccount, scmBGysfp.getKeyword_gys()).or().like(ScmBGysfp::getGysName, scmBGysfp.getKeyword_gys()));
                }
                if(scmBGysfp.getState()!=null){
                        queryWrapper.eq(ScmBGysfp::getState,scmBGysfp.getState());
                }

        Page<ScmBGysfp> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
    @Override
   public IPage<ScmBGysfp> findScmBGysfpsAudit(QueryRequest request, ScmBGysfp scmBGysfp){
        Page<ScmBGysfp> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);
        return  this.baseMapper.findScmBGysfp(page,scmBGysfp);
    }
@Override
@Transactional
public void createScmBGysfp(ScmBGysfp scmBGysfp){
        scmBGysfp.setId(UUID.randomUUID().toString());
        scmBGysfp.setCreateTime(new Date());
        scmBGysfp.setIsDeletemark(1);
        this.save(scmBGysfp);
        }

@Override
@Transactional
public void updateScmBGysfp(ScmBGysfp scmBGysfp){
        scmBGysfp.setModifyTime(new Date());
        this.baseMapper.updateScmBGysfp(scmBGysfp);
        }

@Override
@Transactional
public void deleteScmBGysfps(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
        @Override
        @Transactional
       public boolean IsExist(String fphm,String gys,String id){
          Integer reInt= 0;
          if(StringUtils.isNotBlank(id)){
                  reInt=this.baseMapper.IsExistById(fphm.trim(),gys.trim(),id);
          }
          else{
                  reInt=this.baseMapper.IsExist(fphm.trim(),gys.trim());
          }
          if(reInt>0){
                  return  true;
          }
          return  false;
        }
        }