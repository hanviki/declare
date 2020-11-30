package cc.mrbird.febs.dcacopy.service.impl;

import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.dca.entity.CustomApplyFirst;
import cc.mrbird.febs.dcacopy.entity.*;
import cc.mrbird.febs.dcacopy.dao.DcaBCopyUserMapper;
import cc.mrbird.febs.dcacopy.service.*;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
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
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author viki
 * @since 2020-11-26
 */
@Slf4j
@Service("IDcaBCopyUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DcaBCopyUserServiceImpl extends ServiceImpl<DcaBCopyUserMapper, DcaBCopyUser> implements IDcaBCopyUserService {

    @Autowired
    private IDcaBCopyYoungprizeService iDcaBCopyYoungprize;
    @Autowired
    private IDcaBCopyApplyjobService iDcaBCopyApplyjobService;
    @Autowired
    private IDcaBCopyAttachfileService iDcaBCopyAttachfileService;
    @Autowired
    private IDcaBCopyAuditfiveService iDcaBCopyAuditfiveService;
    @Autowired
    private IDcaBCopyCourseclassService iDcaBCopyCourseclassService;
    @Autowired
    private IDcaBCopyEducationexpericeService iDcaBCopyEducationexpericeService;
    @Autowired
    private IDcaBCopyEmployService iDcaBCopyEmployService;
    @Autowired
    private IDcaBCopyExportcountryService iDcaBCopyExportcountryService;
    @Autowired
    private IDcaBCopyFivecommentService iDcaBCopyFivecommentService;
    @Autowired
    private IDcaBCopyGoalService iDcaBCopyGoalService;
    @Autowired
    private IDcaBCopyGraduateService iDcaBCopyGraduateService;
    @Autowired
    private IDcaBCopyInnovatebuildService iDcaBCopyInnovatebuildService;
    @Autowired
    private IDcaBCopyLastemployService iDcaBCopyLastemployService;
    @Autowired
    private IDcaBCopyOtherworkService iDcaBCopyOtherworkService;
    @Autowired
    private IDcaBCopyUndergraduateService iDcaBCopyUndergraduateService;
    @Autowired
    private IDcaBCopyUndergraduateprizeService iDcaBCopyUndergraduateprizeService;
    @Autowired
    private IDcaBCopyTurtorService iDcaBCopyTurtorService;
    @Autowired
    private IDcaBCopyTeachtalentService iDcaBCopyTeachtalentService;
    @Autowired
    private IDcaBCopyTeacherqualifyService iDcaBCopyTeacherqualifyService;
    @Autowired
    private IDcaBCopyTeacherprizeService iDcaBCopyTeacherprizeService;
    @Autowired
    private IDcaBCopyTalentService iDcaBCopyTalentService;
    @Autowired
    private IDcaBCopyScientificprizeService iDcaBCopyScientificprizeService;
    @Autowired
    private IDcaBCopySciencesearchService iDcaBCopySciencesearchService;
    @Autowired
    private IDcaBCopySciencepublishService iDcaBCopySciencepublishService;
    @Autowired
    private IDcaBCopySchoolprizeService iDcaBCopySchoolprizeService;
    @Autowired
    private IDcaBCopyPublicarticleService iDcaBCopyPublicarticleService;
    @Autowired
    private IDcaBCopyPrizeorpunishService iDcaBCopyPrizeorpunishService;
    @Autowired
    private IDcaBCopyPolitalshowService iDcaBCopyPolitalshowService;
    @Autowired
    private IDcaBCopyPersonalsummaryService iDcaBCopyPersonalsummaryService;
    @Autowired
    private IDcaBCopyPatentService iDcaBCopyPatentService;

    @Autowired
    private IDcaBCopyParttimejobService iDcaBCopyParttimejobService;

    @Autowired
    private IDcaBCopyUserService iDcaBCopyUserService;


   @Autowired
   private  IDcaBCopyAuditdynamicService iDcaBCopyAuditdynamicService;




@Override
public IPage<DcaBCopyUser> findDcaBCopyUsers(QueryRequest request, DcaBCopyUser dcaBCopyUser){
        try{
        LambdaQueryWrapper<DcaBCopyUser> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBCopyUser::getIsDeletemark, 1);//1是未删 0是已删

                                if (StringUtils.isNotBlank(dcaBCopyUser.getUserAccountName())) {
                                queryWrapper.like(DcaBCopyUser::getUserAccountName, dcaBCopyUser.getUserAccountName());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUser.getUserAccount())) {
                                queryWrapper.like(DcaBCopyUser::getUserAccount, dcaBCopyUser.getUserAccount());
                                }
                                if (StringUtils.isNotBlank(dcaBCopyUser.getDcaYear())) {
                                queryWrapper.like(DcaBCopyUser::getDcaYear, dcaBCopyUser.getDcaYear());
                                }

        Page<DcaBCopyUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return this.page(page,queryWrapper);
        }catch(Exception e){
        log.error("获取字典信息失败" ,e);
        return null;
        }
        }
@Override
public IPage<DcaBCopyUser> findDcaBCopyUserList (QueryRequest request, DcaBCopyUser dcaBCopyUser){
        try{
        Page<DcaBCopyUser> page=new Page<>();
        SortUtil.handlePageSort(request,page,false);//true 是属性  false是数据库字段可两个
        return  this.baseMapper.findDcaBCopyUser(page,dcaBCopyUser);
        }catch(Exception e){
        log.error("获取失败" ,e);
        return null;
        }
        }
@Override
@Transactional
public void createDcaBCopyUser(DcaBCopyUser dcaBCopyUser){
                dcaBCopyUser.setId(UUID.randomUUID().toString());
        dcaBCopyUser.setCreateTime(new Date());
        dcaBCopyUser.setIsDeletemark(1);
        this.save(dcaBCopyUser);
        }

@Override
@Transactional
public void updateDcaBCopyUser(DcaBCopyUser dcaBCopyUser){
        dcaBCopyUser.setModifyTime(new Date());
        this.baseMapper.updateDcaBCopyUser(dcaBCopyUser);
        }

@Override
@Transactional
public void deleteDcaBCopyUsers(String[]Ids){
        List<String> list=Arrays.asList(Ids);
        this.baseMapper.deleteBatchIds(list);
        }
@Override
@Transactional
public List<DcaBCopyUser> getAll(String userAccount,String dcaYear){
        LambdaQueryWrapper<DcaBCopyUser> queryWrapper=new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(userAccount)) {
        queryWrapper.eq(DcaBCopyUser::getUserAccount, userAccount);
        }
        if (StringUtils.isNotBlank(dcaYear)) {
        queryWrapper.eq(DcaBCopyUser::getDcaYear, dcaYear);
        }
      return  this.baseMapper.selectList(queryWrapper);
        }

        private String DateStr(Date date,String stringFormat){
              if(date ==null) return  "";
              return  DateUtil.format(date,stringFormat);
        }
    @Override
    @Transactional
        public CustomApplyFirst getPrintPdf(String userAccount,String dcaYear,String zc){

            CustomApplyFirst customApplyFirst =new CustomApplyFirst();
           // List<DcaBCopyYoungprize> listDcaBCopyYoungprize =this.iDcaBCopyYoungprize.getAll(userAccount,dcaYear);

            List<DcaBCopyPatent> listDcaBCopyPatent =this.iDcaBCopyPatentService.getAll(userAccount,dcaYear);
            customApplyFirst.setDcaBPatentList(listDcaBCopyPatent);
            List<DcaBCopyApplyjob> listDcaBApplyjob =this.iDcaBCopyApplyjobService.getAll(userAccount,dcaYear);
            List<DcaBCopyAttachfile> listDcaBCopyAttachfile =this.iDcaBCopyAttachfileService.getAll(userAccount,dcaYear);
            List<DcaBCopyAuditfive> listDcaBCopyAuditfive =this.iDcaBCopyAuditfiveService.getAll(userAccount,dcaYear);
            List<DcaBCopyCourseclass> listDcaBCopyCourseclass =this.iDcaBCopyCourseclassService.getAll(userAccount,dcaYear);
            List<DcaBCopyGoal> listDcaBCopyGoal =this.iDcaBCopyGoalService.getAll(userAccount,dcaYear);
            List<DcaBCopyEducationexperice> listDcaBCopyEducationexperice =this.iDcaBCopyEducationexpericeService.getAll(userAccount,dcaYear);
            List<DcaBCopyEmploy> listDcaBCopyEmploy =this.iDcaBCopyEmployService.getAll(userAccount,dcaYear);
            List<DcaBCopyExportcountry> listDcaBCopyExportcountry =this.iDcaBCopyExportcountryService.getAll(userAccount,dcaYear);
            String fivecomment =this.iDcaBCopyAuditdynamicService.GetZtkhqk(userAccount,dcaYear);
            List<DcaBCopyGraduate> listDcaBCopyGraduate =this.iDcaBCopyGraduateService.getAll(userAccount,dcaYear);
            List<DcaBCopyOtherwork> listDcaBCopyOtherwork =this.iDcaBCopyOtherworkService.getAll(userAccount,dcaYear);
            List<DcaBCopyParttimejob> listDcaBCopyParttimejob=this.iDcaBCopyParttimejobService.getAll(userAccount,dcaYear);
            List<DcaBCopyInnovatebuild> listDcaBCopyInnovatebuild=this.iDcaBCopyInnovatebuildService.getAll(userAccount,dcaYear);
            List<DcaBCopyLastemploy> listDcaBCopyLastemploy=this.iDcaBCopyLastemployService.getAll(userAccount,dcaYear);
            List<DcaBCopyPersonalsummary> listDcaBCopyPersonalsummary=this.iDcaBCopyPersonalsummaryService.getAll(userAccount,dcaYear);
            List<DcaBCopyPolitalshow> listDcaBCopyPolitalshow=this.iDcaBCopyPolitalshowService.getAll(userAccount,dcaYear);
            List<DcaBCopyPrizeorpunish> listDcaBCopyPrizeorpunish=this.iDcaBCopyPrizeorpunishService.getAll(userAccount,dcaYear);
            List<DcaBCopyPublicarticle> listDcaBCopyPublicarticle=this.iDcaBCopyPublicarticleService.getAll(userAccount,dcaYear);
            List<DcaBCopySchoolprize> listDcaBCopySchoolprize=this.iDcaBCopySchoolprizeService.getAll(userAccount,dcaYear);
            List<DcaBCopySciencepublish> listDcaBCopySciencepublish=this.iDcaBCopySciencepublishService.getAll(userAccount,dcaYear);
            List<DcaBCopySciencesearch> listDcaBCopySciencesearch=this.iDcaBCopySciencesearchService.getAll(userAccount,dcaYear);
            List<DcaBCopyScientificprize> listDcaBCopyScientificprize=this.iDcaBCopyScientificprizeService.getAll(userAccount,dcaYear);
            List<DcaBCopyTalent> listDcaBCopyTalent=this.iDcaBCopyTalentService.getAll(userAccount,dcaYear);
            List<DcaBCopyTeachtalent> listDcaBCopyTeachtalent=this.iDcaBCopyTeachtalentService.getAll(userAccount,dcaYear);
            List<DcaBCopyTurtor> listDcaBCopyTurtor=this.iDcaBCopyTurtorService.getAll(userAccount,dcaYear);
            List<DcaBCopyUndergraduate> listDcaBCopyUndergraduate=this.iDcaBCopyUndergraduateService.getAll(userAccount,dcaYear);
            List<DcaBCopyUndergraduateprize> listDcaBCopyUndergraduateprize=this.iDcaBCopyUndergraduateprizeService.getAll(userAccount,dcaYear);
            List<DcaBCopyUser> listDcaBCopyUser=this.iDcaBCopyUserService.getAll(userAccount,dcaYear);
            List<DcaBCopyTeacherqualify> listDcaBCopyTeacherqualify=this.iDcaBCopyTeacherqualifyService.getAll(userAccount,dcaYear);

            DcaBCopyUser user =listDcaBCopyUser.get(0);
            customApplyFirst.setName(user.getUserAccountName());
            customApplyFirst.setBirthday(DateStr(user.getBirthday(),"yyyy-MM-dd"));
            customApplyFirst.setDcaBEducationexpericeList(listDcaBCopyEducationexperice);
            customApplyFirst.setXzyjsgw(user.getPositionName());//专业技术岗位
            customApplyFirst.setXgwzw(user.getPositionName());//
            customApplyFirst.setXcszyjzc(user.getXcszyjzc());
            customApplyFirst.setWcsypqgzrwqk(listDcaBCopyLastemploy.size()>0?listDcaBCopyLastemploy.get(0).getLastContent():"");
            customApplyFirst.setSzyx(user.getDeptName());

         String shjz=   listDcaBCopyParttimejob.stream().map(p ->DateStr( p.getJzStartTime(),"yyyy-MM-dd")+"至"+DateStr( p.getJzEndTime(),"yyyy-MM-dd")+" "+p.getJzContent()).collect(Collectors.joining("\n", "", ""));
            customApplyFirst.setShjz(shjz);//社会兼职
            customApplyFirst.setSex(user.getSexName());
            customApplyFirst.setSbnpgwly(listDcaBApplyjob.size()>0?listDcaBApplyjob.get(0).getApplyContent():"");
            customApplyFirst.setRsbh(user.getAuditSuggestion()); //华科人事编号
            customApplyFirst.setQtgzjcg(listDcaBCopyOtherwork.size()>0?listDcaBCopyOtherwork.get(0).getOtherWork():"");
            customApplyFirst.setPrsj(user.getZygwDate());
            customApplyFirst.setNpgwzw(zc);//申请职称
            customApplyFirst.setNpgwgzsljyqmb(listDcaBCopyGoal.size()>0?listDcaBCopyGoal.get(0).getPreGoal():"");
            customApplyFirst.setLxgzsj(DateStr(user.getSchoolDate(),"yyyy-MM-dd"));

            String teacherQualify=   listDcaBCopyTeacherqualify.stream().map(p->DateStr(p.getTqReceiveDate(),"yyyy-MM-dd")+" "+p.getTqCode()).collect(Collectors.joining("\n", "", ""));
            customApplyFirst.setJszgzbhjhdsj(teacherQualify);

            customApplyFirst.setJ5nkhqk(fivecomment);
            String prizeOrPunish=   listDcaBCopyPrizeorpunish.stream().map(p->DateStr(p.getPpStartTime(),"yyyy-MM-dd")+" "+p.getPpContent()).collect(Collectors.joining("\n", "", ""));
            customApplyFirst.setHshdshjljcf(prizeOrPunish);

            customApplyFirst.setGwlb(user.getGwdj());
            customApplyFirst.setGrzj(listDcaBCopyPersonalsummary.size()>0?listDcaBCopyPersonalsummary.get(0).getPsContent():"");
            customApplyFirst.setGrsxzzjsdsf(listDcaBCopyPolitalshow.size()>0?listDcaBCopyPolitalshow.get(0).getPsContent():"");
            String drfdyjsbzrjkhqk=   listDcaBCopyTurtor.stream().map(p->p.getTurtorMain()+" "+p.getTutorContent()).collect(Collectors.joining("\n", "", ""));
            customApplyFirst.setDrfdyjsbzrjkhqk(drfdyjsbzrjkhqk);

            customApplyFirst.setDcaBUndergraduateList(listDcaBCopyUndergraduate);
            customApplyFirst.setDcaBUndergraduateprizeList(listDcaBCopyUndergraduateprize);
            customApplyFirst.setDcaBCopyEmployList(listDcaBCopyEmploy);
            customApplyFirst.setDcaBTalentList(listDcaBCopyTalent);
            customApplyFirst.setDcaBScientificprizeList(listDcaBCopyScientificprize);
            customApplyFirst.setDcaBSciencesearchList(listDcaBCopySciencesearch);
            customApplyFirst.setDcaBSciencepublishList(listDcaBCopySciencepublish);
            customApplyFirst.setDcaBPublicarticleList(listDcaBCopyPublicarticle);
            customApplyFirst.setDcaBPatentList(listDcaBCopyPatent);
            customApplyFirst.setDcaBInnovatebuildList(listDcaBCopyInnovatebuild);
            customApplyFirst.setDcaBGraduate(listDcaBCopyGraduate.size()>0?listDcaBCopyGraduate.get(0):null);


            return  customApplyFirst;
}

        }