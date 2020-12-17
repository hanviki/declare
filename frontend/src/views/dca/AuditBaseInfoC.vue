<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <a-layout id="custom-trigger">
      <a-layout-sider
        width="300"
        style="background: #fff;"
        v-model="collapsed"
        :trigger="null"
        collapsible
      >
        <a-card
          :bordered="true"
          class="card-area"
        >
          <div :style="calcHeight">
              <a-tree
                :key="mouduleTreeKey"
                ref="menuTree"
                :treeData="mouduleTreeData"
                @select="handleTreeClick"
              >
              </a-tree>
            </div>
        </a-card>
      </a-layout-sider>
      <a-layout>
        <a-layout-content>
          <div style="position: relative">
            <div style="position: absolute;left:0;top:20px; z-index:99999">
              <a-icon
                class="trigger"
                :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                @click="() => (collapsed = !collapsed)"
              />
            </div>
            <dcaB-parttimejob
              v-if="index==1"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--社会兼职-->
            </dcaB-parttimejob>
            <dcaB-employAudit
              v-if="index==6"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--任现职以来完成教学、人才培养情况-->
            </dcaB-employAudit>
            <!--  <dcaB-teachtalent v-if="index==6">
              教师 任现职以来完成教学、人才培养情况  和emply重复了
            </dcaB-teachtalent>-->
                <dcaB-auditfiveAudit v-if="index==3"
                :dcaYear="dcaYear"
              :dcaType="dcaType"
                >
               <!--近五年考核情况-->
              </dcaB-auditfiveAudit>
            <dcaB-educationexpericeAudit
              v-if="index==4"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--学习工作经历-->
            </dcaB-educationexpericeAudit>
            <!--<dcaB-essaypublish v-if="index==7">
              //论文出版
            </dcaB-essaypublish>-->
            <dcaB-graduateAudit
              v-if="index==21"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--研究生情况-->
            </dcaB-graduateAudit>
            <dcaB-otherworkAudit v-if="index==24">
              <!--其他工作及成果-->
            </dcaB-otherworkAudit>
            <!-- <dcaB-paperspublish v-if="index==22">
             教学论文出版教材
            </dcaB-paperspublish>-->
            <dcaB-patentAudit
              v-if="index==10"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--申请专利-->
            </dcaB-patentAudit>
            <dcaB-prizeorpunishAudit
              v-if="index==2"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--何时何地奖励或处分-->
            </dcaB-prizeorpunishAudit>
            <dcaB-sciencepublishAudit
              v-if="index==7"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--科研论文-->
            </dcaB-sciencepublishAudit>
            <dcaB-scientificprizeAudit
              v-if="index==9"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--自任职以来科研获奖情况-->
            </dcaB-scientificprizeAudit>
            <dcaB-talentAudit
              v-if="index==20"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--任现职以来完成研究生教学人才培养情况-->
            </dcaB-talentAudit>
            <dcaB-teacherqualifyAudit
              v-if="index==13"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--教师资格-->
            </dcaB-teacherqualifyAudit>
            <dcaB-turtorAudit
              v-if="index==14"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--担任辅导员-->
            </dcaB-turtorAudit>
            <dcaB-undergraduateAudit
              v-if="index==17"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--本科教学情况-->
            </dcaB-undergraduateAudit>
            <dcaB-applyjobAudit v-if="index==16">
              <!--拟聘岗位-->
            </dcaB-applyjobAudit>
            <!--<dcaB-auditfive v-if="index==3">
               近五年考核情况
              </dcaB-auditfive>-->
            <dcaB-lastemployAudit v-if="index==15">
              <!--完成上一聘期-->
            </dcaB-lastemployAudit>
            <dcaB-personalsummaryAudit v-if="index==12">
              <!--个人总结-->
            </dcaB-personalsummaryAudit>
            <dcaB-politalshowAudit v-if="index==5">
              <!--个人思想政治表现-->
            </dcaB-politalshowAudit>
            <dcaB-innovatebuildAudit
              v-if="index==18"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--改革及建设项目-->
            </dcaB-innovatebuildAudit>
            <dcaB-sciencesearchAudit
              v-if="index==8"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--科研项目-->
            </dcaB-sciencesearchAudit>
            <!--<dcaB-fivecommentAudit v-if="index==17">
                近五年总体情况评价
              </dcaB-fivecommentAudit>-->
            <dcaB-goalAudit v-if="index==25">
              <!--拟聘岗位工作思路及预期目标-->
            </dcaB-goalAudit>

            <dcaB-undergraduateprizeAudit
              v-if="index==19"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--任现职以来本科教学工作获奖情况-->
            </dcaB-undergraduateprizeAudit>
            <dcaB-attachfileAudit
              v-if="index==26"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--其他材料附件-->
            </dcaB-attachfileAudit>
            <!--<dcaB-worknumAudit v-if="index==11">
                门诊工作量
              </dcaB-worknumAudit>-->
            <dcaB-exportcountryAudit
              v-if="index==27"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--出国情况-->
            </dcaB-exportcountryAudit>
            <dcaB-publicarticleAudit
              v-if="index==28"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--著作-->
            </dcaB-publicarticleAudit>

            <dcaB-courseclassAudit
              v-if="index==31"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--精品课程情况-->
            </dcaB-courseclassAudit>
            <dcaB-schoolprizeAudit
              v-if="index==30"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--校教学质量奖、校教学成果奖-->
            </dcaB-schoolprizeAudit>
            <dcaB-teacherprizeAudit
              v-if="index==29"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--省部级及以上教学获奖-->
            </dcaB-teacherprizeAudit>
            <dcaB-youngprizeAudit
              v-if="index==32"
              :dcaYear="dcaYear"
              :dcaType="dcaType"
            >
              <!--教师教学竞赛获奖-->
            </dcaB-youngprizeAudit>
              <dcaB-doctorturtorAudit v-if="index==33"
               :dcaYear="dcaYear"
              :dcaType="dcaType"
              >
                <!--博导时间-->
              </dcaB-doctorturtorAudit>
               <dcaB-medicalaccidentAudit v-if="index==34"
                :dcaYear="dcaYear"
              :dcaType="dcaType"
              >
                <!--医疗事故评分-->
              </dcaB-medicalaccidentAudit>
                 <dcaB-academicAudit v-if="index==35"
                  :dcaYear="dcaYear"
              :dcaType="dcaType"
                 >
                <!--学术-->
               </dcaB-academicAudit>
               <dcaB-achievementAudit v-if="index==36"
                :dcaYear="dcaYear"
              :dcaType="dcaType"
               >
                <!--医疗业绩-->
              </dcaB-achievementAudit>
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-card>

</template>
<script>
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18, offset: 1 }
}
import DcaBParttimejob from '../dca/DcaBParttimejob/DcaBParttimejob'
import DcaBEmployAudit from '../dca/DcaBEmploy/DcaBEmployAudit'
import DcaBEducationexpericeAudit from '../dca/DcaBEducationexperice/DcaBEducationexpericeAudit'
import DcaBEssaypublishAudit from '../dca/DcaBEssaypublish/DcaBEssaypublishAudit'
import DcaBGraduateAudit from '../dca/DcaBGraduate/DcaBGraduateAudit'
import DcaBInnovatebuildAudit from '../dca/DcaBInnovatebuild/DcaBInnovatebuildAudit'
import DcaBOtherworkAudit from '../dca/DcaBOtherwork/DcaBOtherworkAudit'
import DcaBPaperspublishAudit from '../dca/DcaBPaperspublish/DcaBPaperspublishAudit'
import DcaBPatentAudit from '../dca/DcaBPatent/DcaBPatentAudit'
import DcaBPrizeorpunishAudit from '../dca/DcaBPrizeorpunish/DcaBPrizeorpunishAudit'
import DcaBSciencepublishAudit from '../dca/DcaBSciencepublish/DcaBSciencepublishAudit'
import DcaBScientificprizeAudit from '../dca/DcaBScientificprize/DcaBScientificprizeAudit'
import DcaBTalentAudit from '../dca/DcaBTalent/DcaBTalentAudit'
import DcaBTeacherqualifyAudit from '../dca/DcaBTeacherqualify/DcaBTeacherqualifyAudit'
import DcaBTurtorAudit from '../dca/DcaBTurtor/DcaBTurtorAudit'
import DcaBUndergraduateAudit from '../dca/DcaBUndergraduate/DcaBUndergraduateAudit'
import DcaBApplyjobAudit from '../dca/DcaBApplyjob/DcaBApplyjobAudit'
import DcaBAuditfiveAudit from '../dca/DcaBAuditfive/DcaBAuditfiveAudit'
import DcaBLastemployAudit from '../dca/DcaBLastemploy/DcaBLastemployAudit'
import DcaBPersonalsummaryAudit from '../dca/DcaBPersonalsummary/DcaBPersonalsummaryAudit'
import DcaBPolitalshowAudit from '../dca/DcaBPolitalshow/DcaBPolitalshowAudit'
import DcaBSciencesearchAudit from '../dca/DcaBSciencesearch/DcaBSciencesearchAudit'
import DcaBFivecommentAudit from '../dca/DcaBFivecomment/DcaBFivecommentAudit'
import DcaBGoalAudit from '../dca/DcaBGoal/DcaBGoalAudit'
import DcaBAttachfileAudit from '../dca/DcaBAttachfile/DcaBAttachfileAudit'
import DcaBWorknumAudit from '../dca/DcaBWorknum/DcaBWorknumAudit'
import DcaBUndergraduateprizeAudit from '../dca/DcaBUndergraduateprize/DcaBUndergraduateprizeAudit'
import DcaBExportcountryAudit from '../dca/DcaBExportcountry/DcaBExportcountryAudit'
import DcaBPublicarticleAudit from '../dca/DcaBPublicarticle/DcaBPublicarticleAudit'
import DcaBCourseclassAudit from '../dca/DcaBCourseclass/DcaBCourseclassAudit'
import DcaBSchoolprizeAudit from '../dca/DcaBSchoolprize/DcaBSchoolprizeAudit'
import DcaBTeacherprizeAudit from '../dca/DcaBTeacherprize/DcaBTeacherprizeAudit'
import DcaBYoungprizeAudit from '../dca/DcaBYoungprize/DcaBYoungprizeAudit'
import DcaBDoctorturtorAudit from '../dca/DcaBDoctorturtor/DcaBDoctorturtorAudit'
import DcaBMedicalaccidentAudit from '../dca/DcaBMedicalaccident/DcaBMedicalaccidentAudit'
import DcaBAchievementAudit from '../dca/DcaBAchievement/DcaBAchievementAudit'
import DcaBAcademicAudit from '../dca/DcaBAcademic/DcaBAcademicAudit'

export default {
  name: 'AuditBaseInfo',
  components: {
    DcaBParttimejob, DcaBEmployAudit, DcaBEducationexpericeAudit, DcaBEssaypublishAudit, DcaBGraduateAudit, DcaBPublicarticleAudit, DcaBCourseclassAudit, DcaBSchoolprizeAudit, DcaBTeacherprizeAudit, DcaBYoungprizeAudit,
    DcaBInnovatebuildAudit, DcaBOtherworkAudit, DcaBPaperspublishAudit, DcaBPatentAudit, DcaBPrizeorpunishAudit, DcaBSciencepublishAudit,
    DcaBScientificprizeAudit, DcaBTalentAudit, DcaBTeacherqualifyAudit, DcaBTurtorAudit, DcaBUndergraduateAudit, DcaBApplyjobAudit,
    DcaBAuditfiveAudit, DcaBLastemployAudit, DcaBPersonalsummaryAudit, DcaBPolitalshowAudit, DcaBSciencesearchAudit, DcaBFivecommentAudit, DcaBGoalAudit, DcaBAttachfileAudit, DcaBWorknumAudit, DcaBUndergraduateprizeAudit, DcaBExportcountryAudit
    , DcaBMedicalaccidentAudit, DcaBDoctorturtorAudit, DcaBAcademicAudit, DcaBAchievementAudit
  },
  data () {
    return {
      mouduleTreeKey: +new Date(),
      loading: false,
      bordered: true,
      mouduleTreeData: [],
      allTreeKeys: [],
      formItemLayout,
      index: 0,
      collapsed: false,
      dcaYear: 2020,
      dcaType: "初级",
      calcHeight: {
        height: '',
        overflow: 'auto'
      }
    }
  },
  mounted () {
    this.fetch()
  },
  created () {
    window.addEventListener('resize', this.getHeight);
    this.getHeight()
  },
  methods: {
    getHeight () {
      this.calcHeight.height = window.innerHeight - 59 - 39 - 100 + 'px';
    },
    fetch () {
      this.$get('dcaDMudules/treeByUserId').then((r) => {
        //console.info(222)
        this.mouduleTreeData = r.data.rows.children
        this.index = r.data.rows.children[0].id
        this.allTreeKeys = r.data.ids
      })
    },
    handleTreeClick (keys, event) {
      if(keys.length>0){
        this.index = keys[0]
      }
    }
  }

}
</script>
<style>
.trigger {
  font-size: 18px;
  line-height: 20px;
  padding: 0 5px;
  cursor: pointer;
  transition: color 0.3s;
}
#custom-trigger .trigger:hover {
  color: #1890ff;
}
.ant-card-body {
  padding: 8px;
}
.ant-tabs-bar {
  margin-bottom: 8px;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>