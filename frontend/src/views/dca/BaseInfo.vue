<template>
  <a-card
    :bordered="false"
    class="card-area mycard-area"
  >
    <template v-if="!showAdd">
      <div style="text-align:center;font-size:20px;">
        <p style="font-size:20px!important;font-weight:bold;">个人承诺</p>
        <p style="font-size:20px!important;font-weight:bold;">本人慎重承诺所从事的学术研究符合学术道德规范，所填写内容真实准确，如有不实之处，本人愿意承担相关责任。</p>
        <a-button
          @click="handlePromise"
          type="primary"
        >本人确认</a-button>
      </div>
    </template>
    <template v-if="showAdd">
      <a-layout id="custom-trigger">
        <a-layout-sider
          width="250"
          style="background: #fff;"
          v-model="collapsed"
          :trigger="null"
          collapsible
        >
          <a-card
            :bordered="true"
            class="card-area mycard-area"
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
              <dcaB-user v-if="index==0">
                <!--基本资料-->
              </dcaB-user>
              <dcaBParttime-customer v-if="index==1">
                <!--社会兼职-->
              </dcaBParttime-customer>
              <dcaB-employ v-if="index==6">
                <!--任现职以来完成教学、人才培养情况-->
              </dcaB-employ>
              <!--  <dcaB-teachtalent v-if="index==6">
              教师 任现职以来完成教学、人才培养情况  和emply重复了
            </dcaB-teachtalent>-->
              <dcaB-educationexperice v-if="index==4">
                <!--学习工作经历-->
              </dcaB-educationexperice>
              <!--<dcaB-essaypublish v-if="index==7">
              //论文出版
            </dcaB-essaypublish>-->
              <dcaB-graduate v-if="index==21">
                <!--研究生情况-->
              </dcaB-graduate>
              <dcaB-otherwork v-if="index==24">
                <!--其他工作及成果-->
              </dcaB-otherwork>
              <!-- <dcaB-paperspublish v-if="index==22">
             教学论文出版教材
            </dcaB-paperspublish>-->
              <dcaB-patent v-if="index==10">
                <!--申请专利-->
              </dcaB-patent>
              <dcaB-prizeorpunish v-if="index==2">
                <!--何时何地奖励或处分-->
              </dcaB-prizeorpunish>
              <dcaB-sciencepublish v-if="index==7">
                <!--科研论文-->
              </dcaB-sciencepublish>
              <dcaB-scientificprize v-if="index==9">
                <!--自任职以来科研获奖情况-->
              </dcaB-scientificprize>
              <dcaB-talent v-if="index==20">
                <!--任现职以来完成研究生教学人才培养情况-->
              </dcaB-talent>
              <dcaB-teacherqualify v-if="index==13">
                <!--教师资格-->
              </dcaB-teacherqualify>
              <dcaB-turtor v-if="index==14">
                <!--担任辅导员-->
              </dcaB-turtor>
              <dcaB-undergraduate v-if="index==17">
                <!--本科教学情况-->
              </dcaB-undergraduate>
              <dcaB-applyjob v-if="index==16">
                <!--拟聘岗位-->
              </dcaB-applyjob>
               <dcaB-auditfive v-if="index==3">
               <!--近五年考核情况-->
              </dcaB-auditfive>
              <dcaB-lastemploy v-if="index==15">
                <!--完成上一聘期-->
              </dcaB-lastemploy>
              <dcaB-personalsummary v-if="index==12">
                <!--个人总结-->
              </dcaB-personalsummary>
              <dcaB-politalshow v-if="index==5">
                <!--个人思想政治表现-->
              </dcaB-politalshow>
              <dcaB-innovatebuild v-if="index==18">
                <!--改革及建设项目-->
              </dcaB-innovatebuild>
              <dcaB-sciencesearch v-if="index==8">
                <!--科研项目-->
              </dcaB-sciencesearch>
              <!--<dcaB-fivecomment v-if="index==17">
                近五年总体情况评价
              </dcaB-fivecomment>-->
              <dcaB-goal v-if="index==25">
                <!--拟聘岗位工作思路及预期目标-->
              </dcaB-goal>

              <dcaB-undergraduateprize v-if="index==19">
                <!--任现职以来本科教学工作获奖情况-->
              </dcaB-undergraduateprize>
              <dcaB-attachfile v-if="index==26">
                <!--其他材料附件-->
              </dcaB-attachfile>
             <!-- <dcaB-worknum v-if="index==11">
                门诊工作量
              </dcaB-worknum>-->
              <dcaB-exportcountry v-if="index==27">
                <!--出国情况-->
              </dcaB-exportcountry>
               <dcaB-publicarticle v-if="index==28">
                <!--著作-->
              </dcaB-publicarticle>
             
               <dcaB-courseclass v-if="index==31">
                <!--精品课程情况-->
              </dcaB-courseclass>
                <dcaB-schoolprize v-if="index==30">
                <!--校教学质量奖、校教学成果奖-->
              </dcaB-schoolprize>
                <dcaB-teacherprize v-if="index==29">
                <!--省部级及以上教学获奖-->
              </dcaB-teacherprize>
              <dcaB-youngprize v-if="index==32">
                <!--教师教学竞赛获奖-->
              </dcaB-youngprize>
                <dcaB-doctorturtor v-if="index==33">
                <!--博导时间-->
              </dcaB-doctorturtor>
               <dcaB-medicalaccident v-if="index==34">
                <!--医疗事故评分-->
              </dcaB-medicalaccident>
               <dcaB-academic v-if="index==35">
                <!--学术-->
               </dcaB-academic>
               <dcaB-achievement v-if="index==36">
                <!--医疗业绩-->
              </dcaB-achievement>
            </div>
          </a-layout-content>
        </a-layout>
      </a-layout>
    </template>
  </a-card>

</template>
<script>
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18, offset: 1 }
}
import DcaBParttimeCustomer from '../dca/DcaBParttimejob/DcaBParttimeCustomer'
import DcaBEmploy from '../dca/DcaBEmploy/DcaBEmploy'
import DcaBEducationexperice from '../dca/DcaBEducationexperice/DcaBEducationexperice'
import DcaBEssaypublish from '../dca/DcaBEssaypublish/DcaBEssaypublish'
import DcaBGraduate from '../dca/DcaBGraduate/DcaBGraduate'
import DcaBInnovatebuild from '../dca/DcaBInnovatebuild/DcaBInnovatebuild'
import DcaBOtherwork from '../dca/DcaBOtherwork/DcaBOtherwork'
import DcaBPaperspublish from '../dca/DcaBPaperspublish/DcaBPaperspublish'
import DcaBPatent from '../dca/DcaBPatent/DcaBPatent'
import DcaBPrizeorpunish from '../dca/DcaBPrizeorpunish/DcaBPrizeorpunish'
import DcaBSciencepublish from '../dca/DcaBSciencepublish/DcaBSciencepublish'
import DcaBScientificprize from '../dca/DcaBScientificprize/DcaBScientificprize'
import DcaBTalent from '../dca/DcaBTalent/DcaBTalent'
import DcaBTeacherqualify from '../dca/DcaBTeacherqualify/DcaBTeacherqualify'
import DcaBTeachtalent from '../dca/DcaBTeachtalent/DcaBTeachtalent'
import DcaBTurtor from '../dca/DcaBTurtor/DcaBTurtor'
import DcaBUndergraduate from '../dca/DcaBUndergraduate/DcaBUndergraduate'
import DcaBApplyjob from '../dca/DcaBApplyjob/DcaBApplyjob'
import DcaBAuditfive from '../dca/DcaBAuditfive/DcaBAuditfive'
import DcaBLastemploy from '../dca/DcaBLastemploy/DcaBLastemploy'
import DcaBPersonalsummary from '../dca/DcaBPersonalsummary/DcaBPersonalsummary'
import DcaBPolitalshow from '../dca/DcaBPolitalshow/DcaBPolitalshow'
import DcaBSciencesearch from '../dca/DcaBSciencesearch/DcaBSciencesearch'
import DcaBFivecomment from '../dca/DcaBFivecomment/DcaBFivecomment'
import DcaBGoal from '../dca/DcaBGoal/DcaBGoal'
import DcaBUndergraduateprize from '../dca/DcaBUndergraduateprize/DcaBUndergraduateprize'
import DcaBUser from '../dca/DcaBUser/DcaBUser'
import DcaBAttachfile from '../dca/DcaBAttachfile/DcaBAttachfile'
import DcaBWorknum from '../dca/DcaBWorknum/DcaBWorknum'
import DcaBExportcountry from '../dca/DcaBExportcountry/DcaBExportcountry'
import DcaBPublicarticle from '../dca/DcaBPublicarticle/DcaBPublicarticle'
import DcaBCourseclass from '../dca/DcaBCourseclass/DcaBCourseclass'
import DcaBSchoolprize from '../dca/DcaBSchoolprize/DcaBSchoolprize'
import DcaBTeacherprize from '../dca/DcaBTeacherprize/DcaBTeacherprize'
import DcaBYoungprize from '../dca/DcaBYoungprize/DcaBYoungprize'
import DcaBDoctorturtor from '../dca/DcaBDoctorturtor/DcaBDoctorturtor'
import DcaBMedicalaccident from '../dca/DcaBMedicalaccident/DcaBMedicalaccident'
import DcaBAcademic from '../dca/DcaBAcademic/DcaBAcademic'
import DcaBAchievement from '../dca/DcaBAchievement/DcaBAchievement'

export default {
  name: 'DcaBPatent2',
  components: {
    DcaBParttimeCustomer, DcaBEmploy, DcaBEducationexperice, DcaBEssaypublish, DcaBGraduate, DcaBPublicarticle, DcaBCourseclass, DcaBSchoolprize, DcaBTeacherprize, DcaBYoungprize, DcaBMedicalaccident, DcaBDoctorturtor, 
    DcaBInnovatebuild, DcaBOtherwork, DcaBPaperspublish, DcaBPatent, DcaBPrizeorpunish, DcaBSciencepublish,
    DcaBScientificprize, DcaBTalent, DcaBTeacherqualify, DcaBTurtor, DcaBUndergraduate, DcaBApplyjob,
    DcaBAuditfive, DcaBLastemploy, DcaBPersonalsummary, DcaBPolitalshow, DcaBSciencesearch, DcaBFivecomment, DcaBGoal, DcaBTeachtalent, DcaBUndergraduateprize, DcaBUser, DcaBAttachfile, DcaBWorknum, DcaBExportcountry
    , DcaBAcademic, DcaBAchievement
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
      showAdd: false,
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
    handlePromise () {
      this.showAdd = true
    },
    fetch () {
      this.$get('dcaDMudules/tree').then((r) => {
        console.info(222)
        this.mouduleTreeData = r.data.rows.children
        this.allTreeKeys = r.data.ids
      })
    },
    handleTreeClick (keys, event) {
      this.index = keys[0]
      console.info(this.index)
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
.mycard-area {
  padding: 8px !important;
}
.ant-card-body {
  padding: 8px !important;
}
.ant-tree li span.ant-tree-switcher {
  width: 1px !important;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>