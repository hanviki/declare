package cc.mrbird.febs.scm.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.scm.dao.ScmDVendorDMapper;
import cc.mrbird.febs.scm.dao.ScmDVendoruserMapper;
import cc.mrbird.febs.scm.entity.*;
import cc.mrbird.febs.scm.dao.ScmDVendorMapper;
import cc.mrbird.febs.scm.service.IScmDVendorService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * <p>
 * 供应商表 服务实现类
 * </p>
 *
 * @author viki
 * @since 2019-11-14
 */
@Slf4j
@Service("IScmDVendorService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ScmDVendorServiceImpl extends ServiceImpl<ScmDVendorMapper, ScmDVendor> implements IScmDVendorService {

    @Autowired
    private ScmDVendorDMapper scmDVendorDMapper;
    @Autowired
    private ScmDVendoruserMapper scmDVendoruserMapper;

    @Override
    public IPage<ScmDVendor> findScmDVendors(QueryRequest request, ScmDVendor scmDVendor, String keyword) {
        try {
            LambdaQueryWrapper<ScmDVendor> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(scmDVendor.getName())) {
                queryWrapper.like(ScmDVendor::getName, scmDVendor.getName());
            }

            if (StringUtils.isNotBlank(scmDVendor.getCode())) {
                queryWrapper.eq(ScmDVendor::getCode, scmDVendor.getCode());
            }
            if (scmDVendor.getLb() != null) {
                queryWrapper.eq(ScmDVendor::getLb, scmDVendor.getLb());
            }
            if (scmDVendor.getState() != null && scmDVendor.getState() != -1) {
                queryWrapper.eq(ScmDVendor::getState, scmDVendor.getState());
            }
            if (StringUtils.isNotBlank(keyword)) {
                if (keyword.equals("-1")) {
                    queryWrapper.isNotNull(ScmDVendor::getCode);
                } else {
                    queryWrapper.and(wrapper -> wrapper.like(ScmDVendor::getName, keyword).or().eq(ScmDVendor::getCode, keyword));
                }
            }
            Page<ScmDVendor> page = new Page<>();
            SortUtil.handlePageSort(request, page, false);
            return this.page(page, queryWrapper);
        } catch (Exception e) {
            log.error("获取字典信息失败", e);
            return null;
        }
    }

    @Override
    public IPage<VendorRank> findScmDVendorsRank(QueryRequest request, ScmBPurcharseorder order) {
        Page<VendorRank> page = new Page<>();

        List<VendorRank> records = this.baseMapper.getRank(order);
        List<VendorRank> backRerords = records;//.stream().skip((request.getPageNum() - 1) * request.getPageSize()).limit(request.getPageSize()).collect(Collectors.toList());
        page.setRecords(backRerords);
        page.setTotal(records.size());

        //  page.setCurrent(request.getPageNum());
        return page;
    }
    @Override
    public IPage<MaterPercentage> findScmDVendorsMater(QueryRequest request, ScmBPurcharseorder order) {

        Page<MaterPercentage> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);

       this.baseMapper.getMatnrPercentage(page,order);
        //  page.setCurrent(request.getPageNum());
        return page;
    }
    @Override
    public IPage<TotalStatistic> findVendorM(QueryRequest request, ScmBPurcharseorder order){
        Page<TotalStatistic> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);

        this.baseMapper.getGysAmount(page,order);
        //  page.setCurrent(request.getPageNum());
        return page;
    }
    @Override
    public IPage<TotalStatistic> findMaterVendor(QueryRequest request, ScmBPurcharseorder order){
        Page<TotalStatistic> page = new Page<>();
        SortUtil.handlePageSort(request, page, false);
        log.info(order.getLgortName());
        this.baseMapper.getMatnrAmount(page,order);
        //  page.setCurrent(request.getPageNum());
        return page;
    }

    @Override
    @Transactional
    public void createScmDVendor(ScmDVendor scmDVendor) {
        scmDVendor.setId(UUID.randomUUID().toString());
        scmDVendor.setCreateTime(new Date());
        this.save(scmDVendor);
    }

    @Override
    @Transactional
    public void updateScmDVendor(ScmDVendor scmDVendor) {
        scmDVendor.setModifyTime(new Date());
        this.baseMapper.updateScmDVendor(scmDVendor);
        if(scmDVendor.getState()!=null && scmDVendor.getState().equals(1)){ //在审核完成时，去除更改消息记录
            this.baseMapper.removeNoteVendor(scmDVendor.getCode());
            this.baseMapper.removeNoteVendord(scmDVendor.getCode());
            this.baseMapper.removeNoteVendoruser(scmDVendor.getCode());
        }
    }

    @Override
    @Transactional
    public void deleteScmDVendors(String[] Ids) {
        List<String> list = Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
    }

    @Override
    @Transactional
    public void createScmVendor(ScmDVendor scmDVendor, List<ScmDVendorD> scmDVendorDS,ScmDVendoruser enscmDVendoruser) {
        if(!StringUtils.isNotBlank(scmDVendor.getId()))
        {
            scmDVendor.setId(UUID.randomUUID().toString());
        }
        scmDVendor.setCreateTime(new Date());
        scmDVendor.setState(0);//不可用
        scmDVendor.setIsDeletemark(1);
        scmDVendor.setFileState(0);
        scmDVendor.setJieKouState(0);
        this.save(scmDVendor);
        for (ScmDVendorD scmDVendorD :
                scmDVendorDS) {
            scmDVendorD.setId(UUID.randomUUID().toString());
            scmDVendorD.setBaseId(scmDVendor.getId());
            scmDVendorDMapper.insert(scmDVendorD);
        }
        if(StringUtils.isNotBlank( enscmDVendoruser.getIdcard())) {
            enscmDVendoruser.setId(UUID.randomUUID().toString());
            enscmDVendoruser.setBaseId(scmDVendor.getId());
            this.scmDVendoruserMapper.insert(enscmDVendoruser);
        }
    }
    public  String  getModifyInfo(ScmDVendor scmDVendor)
    {
        ScmDVendor oldvendor=this.baseMapper.selectById(scmDVendor.getId());
        String msg="";
        if(!oldvendor.getAddress().equals(scmDVendor.getAddress())) {
            msg+="地址变更；";
        }

        if(!oldvendor.getLinkPerson().equals(scmDVendor.getLinkPerson())) {
            msg+="联系人变更；";
        }
        if(!oldvendor.getEmail().equals(scmDVendor.getEmail())) {
            msg+="邮箱变更；";
        }
        if(!oldvendor.getName().equals(scmDVendor.getName())) {
            msg+="姓名变更；";
        }
        if(!oldvendor.getPhone().equals(scmDVendor.getPhone())) {
            msg+="电话变更；";
        }
        return  msg;
    }

    @Override
    @Transactional
    public void updateScmDVendor(ScmDVendor scmDVendor, List<ScmDVendorD> scmDVendorDS, ScmDVendoruser enscmDVendoruser) {
        scmDVendor.setModifyTime(new Date());
        String vendorMsg= getModifyInfo(scmDVendor);
        scmDVendor.setNote(vendorMsg);
        scmDVendor.setState(0);
        this.updateScmDVendor(scmDVendor);
        QueryWrapper<ScmDVendorD> queryWrapper = new QueryWrapper<>();
        String F_id = scmDVendor.getId();
        queryWrapper.lambda().eq(ScmDVendorD::getBaseId, F_id);
        List<ScmDVendorD> oldList=scmDVendorDMapper.selectList(queryWrapper);
        scmDVendorDMapper.delete(queryWrapper);
        for (ScmDVendorD scmDVendorD :
                scmDVendorDS) {
            //变更的时候增加变更记录 begin
            List<ScmDVendorD> vdold= oldList.stream().filter(t->t.getTitle().equals(scmDVendorD.getTitle())).collect(Collectors.toList());
           if(vdold.size()>0)
           {
               ScmDVendorD dvoldv=vdold.get(0);
               String msgNote="";
               if(!dvoldv.getFileId().equals(scmDVendorD.getFileId()))
               {
                   msgNote+="附件更改；";
               }
               if(dvoldv.getValidDate()!=null&&!dvoldv.getValidDate().equals(scmDVendorD.getValidDate())) {
                   msgNote+="有效期更改；";
               }
               if(dvoldv.getValidDatestart()!=null&&!dvoldv.getValidDatestart().equals(scmDVendorD.getValidDatestart())) {
                   msgNote+="起始有效期更改；";
               }
               if(msgNote!="")
               {
                   scmDVendorD.setNoted(msgNote);
               }
           }
            //变更的时候增加变更记录  end
            scmDVendorD.setId(UUID.randomUUID().toString());
            scmDVendorD.setBaseId(scmDVendor.getId());
            scmDVendorDMapper.insert(scmDVendorD);
        }

        QueryWrapper<ScmDVendoruser> queryWrapper2 = new QueryWrapper<>();

        queryWrapper2.lambda().eq(ScmDVendoruser::getBaseId, F_id);

        List<ScmDVendoruser> oldListUser=scmDVendoruserMapper.selectList(queryWrapper2);
        if(oldListUser.size()>0){
            String msgUser="";
            ScmDVendoruser ouser=oldListUser.get(0);
            if(!ouser.getIdcard().equals(enscmDVendoruser.getIdcard()))
            {
                msgUser+="身份证号更改；";
            }
            if(!ouser.getAgentImage().equals(enscmDVendoruser.getAgentImage()))
            {
                msgUser+="委托图片更改；";
            }
            if(!ouser.getIdcardBack().equals(enscmDVendoruser.getIdcardBack()))
            {
                msgUser+="身份证背面更改；";
            }
            if(!ouser.getIdcardFront().equals(enscmDVendoruser.getIdcardFront()))
            {
                msgUser+="身份证前面更改；";
            }
            if(!ouser.getHeadImage().equals(enscmDVendoruser.getHeadImage()))
            {
                msgUser+="业务员照片更改；";
            }
            if(!ouser.getName().equals(enscmDVendoruser.getName()))
            {
                msgUser+="业务员姓名更改；";
            }
            if(!ouser.getTelphone().equals(enscmDVendoruser.getTelphone()))
            {
                msgUser+="业务员电话更改；";
            }
            if(msgUser!=""){
                enscmDVendoruser.setNoteu(msgUser);
            }
        }
        this.scmDVendoruserMapper.delete(queryWrapper2);
        if(StringUtils.isNotBlank( enscmDVendoruser.getIdcard())) {
            enscmDVendoruser.setId(UUID.randomUUID().toString());
            enscmDVendoruser.setBaseId(scmDVendor.getId());
            this.scmDVendoruserMapper.insert(enscmDVendoruser);
        }

    }
}