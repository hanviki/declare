package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.entity.ScmBQuotedprice;
import cc.mrbird.febs.scm.dao.ScmBQuotedpriceMapper;
import cc.mrbird.febs.scm.service.IScmBQuotedpriceService;
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
 * 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-12-27
 */
@Slf4j
@Service("IScmBQuotedpriceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmBQuotedpriceServiceImpl extends ServiceImpl<ScmBQuotedpriceMapper, ScmBQuotedprice> implements IScmBQuotedpriceService {


    @Override
    public IPage<ScmBQuotedprice> findScmBQuotedprices(QueryRequest request, ScmBQuotedprice scmBQuotedprice) {
        try {
            LambdaQueryWrapper<ScmBQuotedprice> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmBQuotedprice.getCode())) {
                queryWrapper.eq(ScmBQuotedprice::getCode, scmBQuotedprice.getCode());
            }
            if (StringUtils.isNotBlank(scmBQuotedprice.getGysaccount())) {
                queryWrapper.eq(ScmBQuotedprice::getGysaccount, scmBQuotedprice.getGysaccount());
            }
            queryWrapper.eq(ScmBQuotedprice::getIsDeletemark, 1);//1是未删 0是已删
            Page<ScmBQuotedprice> page = new Page<>();
            SortUtil.handlePageSort(request, page, true);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取信息失败", e);
            return null;
        }
    }

    @Override
    @Transactional
    public void createScmBQuotedprice(ScmBQuotedprice scmBQuotedprice) {

        scmBQuotedprice.setId(UUID.randomUUID().toString());
        scmBQuotedprice.setCreateTime(new Date());
        scmBQuotedprice.setIsDeletemark(1);
        this.save(scmBQuotedprice);
    }


    @Override
    @Transactional
    public void updateScmBQuotedprice(ScmBQuotedprice scmBQuotedprice) {
        scmBQuotedprice.setModifyTime(new Date());
        this.baseMapper.updateScmBQuotedprice(scmBQuotedprice);
    }

    @Override
    @Transactional
    public void deleteScmBQuotedprices(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);

    }
    @Override
    @Transactional
    public void deleteScmBQuotedprices(String Id) {
        this.baseMapper.deleteScmBQuotedprice(Id);

    }

}