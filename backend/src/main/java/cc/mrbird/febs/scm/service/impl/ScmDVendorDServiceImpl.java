package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ComFileMapper;
import cc.mrbird.febs.scm.entity.ComFile;
import cc.mrbird.febs.scm.entity.ScmDVendorD;
import cc.mrbird.febs.scm.dao.ScmDVendorDMapper;
import cc.mrbird.febs.scm.service.IScmDVendorDService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
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


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Date;
/**
 * <p>
 * 供应商对应的资质文件表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
@Slf4j
@Service("IScmDVendorDService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDVendorDServiceImpl extends ServiceImpl<ScmDVendorDMapper, ScmDVendorD> implements IScmDVendorDService {
        @Autowired
        private ComFileMapper comFileMapper;

@Override
public IPage<ScmDVendorD> findScmDVendorDs(QueryRequest request, ScmDVendorD scmDVendorD){
        try{
        LambdaQueryWrapper<ScmDVendorD> queryWrapper=new LambdaQueryWrapper<>();

        Page<ScmDVendorD> page=new Page<>();
        SortUtil.handlePageSort(request,page,true);
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }

@Override
@Transactional
public void createScmDVendorD(ScmDVendorD scmDVendorD){
        scmDVendorD.setId(UUID.randomUUID().toString());
        scmDVendorD.setCreateTime(new Date());
        this.save(scmDVendorD);
        }

@Override
@Transactional
public void updateScmDVendorD(ScmDVendorD scmDVendorD){
        scmDVendorD.setModifyTime(new Date());
        this.baseMapper.updateScmDVendorD(scmDVendorD);
        }

@Override
@Transactional
public void deleteScmDVendorDs(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }

        @Override
        public List<ScmDVendorD> findScmDVendorDByBaseId(String base_id){
                try{
                       List<ScmDVendorD> scmDVendorDs=this.baseMapper.selectList(new LambdaQueryWrapper<ScmDVendorD>().eq(ScmDVendorD::getBaseId, base_id));

                        for (ScmDVendorD item:
                             scmDVendorDs) {
                                String file_id=item.getFileId();
                                if(StringUtils.isNotBlank(file_id)){
                                        ComFile comFile = this.comFileMapper.selectById(file_id);
                                        if (comFile != null) {
                                                item.attachfile=comFile;
                                        }
                                }
                        }
                        return  scmDVendorDs;
                }catch(Exception e){
                        log.error("获取资质文件信息失败" ,e);
                        return null;
                }
        }
        }