<template>
  <a-card
    :bordered="false"
    class="card-area mycard-area "
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
          width="280"
          style="background: #fff;"
          v-model="collapsed"
          :trigger="null"
          collapsible
        >
          <a-card
            :bordered="true"
            class="card-area mycard-area myBaseInfo"
            title="请点击左侧小三角展开或关闭"
          >
           <a-icon
           slot="extra"
                  class="trigger"
                  :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                  @click="() => (collapsed = !collapsed)"
                />
          
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
              <dcaB-user v-if="index==6">
                <!--基本资料-->
              </dcaB-user>
              <dcaBParttime-customer v-if="index==15">
                <!--社会兼职-->
              </dcaBParttime-customer>
              <dcaB-employ v-if="index==60">
                <!--任现职以来完成教学、人才培养情况-->
              </dcaB-employ>
              <!--  <dcaB-teachtalent v-if="index==6">
              教师 任现职以来完成教学、人才培养情况  和emply重复了
            </dcaB-teachtalent>-->
              <dcaB-educationexperice v-if="index==14">
                <!--学习工作经历-->
              </dcaB-educationexperice>
              <!--<dcaB-essaypublish v-if="index==7">
              //论文出版
            </dcaB-essaypublish>-->
              <dcaB-graduate v-if="index==210">
                <!--研究生情况-->
              </dcaB-graduate>
              <dcaB-otherwork v-if="index==240">
                <!--其他工作及成果-->
              </dcaB-otherwork>
              <!-- <dcaB-paperspublish v-if="index==22">
             教学论文出版教材
            </dcaB-paperspublish>-->
              <dcaB-patent v-if="index==100">
                <!--申请专利-->
              </dcaB-patent>
              <dcaB-prizeorpunish v-if="index==20">
                <!--何时何地奖励或处分-->
              </dcaB-prizeorpunish>
              <dcaB-sciencepublish v-if="index==70">
                <!--科研论文-->
              </dcaB-sciencepublish>
              <dcaB-scientificprize v-if="index==90">
                <!--自任职以来科研获奖情况-->
              </dcaB-scientificprize>
              <dcaB-talent v-if="index==200">
                <!--任现职以来完成研究生教学人才培养情况-->
              </dcaB-talent>
              <dcaB-teacherqualify v-if="index==130">
                <!--教师资格-->
              </dcaB-teacherqualify>
              <dcaB-turtor v-if="index==140">
                <!--担任辅导员-->
              </dcaB-turtor>
              <dcaB-undergraduate v-if="index==170">
                <!--本科教学情况-->
              </dcaB-undergraduate>
              <dcaB-applyjob v-if="index==160">
                <!--拟聘岗位-->
              </dcaB-applyjob>
               <dcaB-auditfive v-if="index==270">
               <!--近五年考核情况-->
              </dcaB-auditfive>
               <dcaB-auditfivemonth v-if="index==272">
               <!--月度考核-->
              </dcaB-auditfivemonth>
               <dcaB-auditfiveother v-if="index==273">
               <!--中期考核和出站考核-->
              </dcaB-auditfiveother>
              
              <dcaB-lastemploy v-if="index==150">
                <!--完成上一聘期-->
              </dcaB-lastemploy>
              <dcaB-personalsummary v-if="index==121">
                <!--个人总结-->
              </dcaB-personalsummary>
              <dcaB-politalshow v-if="index==50">
                <!--个人思想政治表现-->
              </dcaB-politalshow>
              <dcaB-innovatebuild v-if="index==180">
                <!--改革及建设项目-->
              </dcaB-innovatebuild>
              <dcaB-sciencesearch v-if="index==80">
                <!--科研项目-->
              </dcaB-sciencesearch>
              <!--<dcaB-fivecomment v-if="index==17">
                近五年总体情况评价
              </dcaB-fivecomment>-->
              <dcaB-goal v-if="index==251">
                <!--拟聘岗位工作思路及预期目标-->
              </dcaB-goal>

              <dcaB-undergraduateprize v-if="index==190">
                <!--任现职以来本科教学工作获奖情况-->
              </dcaB-undergraduateprize>
              <dcaB-attachfile v-if="index==400">
                <!--其他材料附件-->
              </dcaB-attachfile>
              <dcaB-worknum v-if="index==110">
                 <!--门诊工作量-->
              </dcaB-worknum>
              <dcaB-exportcountry v-if="index==40">
                <!--出国情况-->
              </dcaB-exportcountry>
               <dcaB-publicarticle v-if="index==280">
                <!--著作-->
              </dcaB-publicarticle>
             
               <dcaB-courseclass v-if="index==310">
                <!--精品课程情况-->
              </dcaB-courseclass>
                <dcaB-schoolprize v-if="index==300">
                <!--校教学质量奖、校教学成果奖-->
              </dcaB-schoolprize>
                <dcaB-teacherprize v-if="index==290">
                <!--省部级及以上教学获奖-->
              </dcaB-teacherprize>
              <dcaB-youngprize v-if="index==320">
                <!--教师教学竞赛获奖-->
              </dcaB-youngprize>
                <!-- <dcaB-doctorturtor v-if="index==212">
               博导时间
              </dcaB-doctorturtor>-->
               <dcaB-medicalaccident v-if="index==340">
                <!--医疗事故评分-->
              </dcaB-medicalaccident>
               <dcaB-academic v-if="index==380">
                <!--学术-->
               </dcaB-academic>
               <dcaB-achievement v-if="index==360">
                <!--医疗业绩-->
              </dcaB-achievement>
               <dcaB-qualification v-if="index==370">
                <!--医疗业绩-->
              </dcaB-qualification>
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
import DcaBParttimeCustomer from '../doctor/DcaBDocParttimejob/DcaBParttimeCustomer'
import DcaBEmploy from '../doctor/DcaBDocEmploy/DcaBEmploy'
import DcaBEducationexperice from '../doctor/DcaBDocEducationexperice/DcaBEducationexperice'
import DcaBEssaypublish from '../doctor/DcaBDocEssaypublish/DcaBEssaypublish'
import DcaBGraduate from '../doctor/DcaBDocGraduate/DcaBGraduate'
import DcaBInnovatebuild from '../doctor/DcaBDocInnovatebuild/DcaBInnovatebuild'
import DcaBOtherwork from '../doctor/DcaBDocOtherwork/DcaBOtherwork'
import DcaBPaperspublish from '../doctor/DcaBDocPaperspublish/DcaBPaperspublish'
import DcaBPatent from '../doctor/DcaBDocPatent/DcaBPatent'
import DcaBPrizeorpunish from '../doctor/DcaBDocPrizeorpunish/DcaBPrizeorpunish'
import DcaBSciencepublish from '../doctor/DcaBDocSciencepublish/DcaBSciencepublish'
import DcaBScientificprize from '../doctor/DcaBDocScientificprize/DcaBScientificprize'
import DcaBTalent from '../doctor/DcaBDocTalent/DcaBTalent'
import DcaBTeacherqualify from '../doctor/DcaBDocTeacherqualify/DcaBTeacherqualify'
import DcaBTeachtalent from '../doctor/DcaBDocTeachtalent/DcaBTeachtalent'
import DcaBTurtor from '../doctor/DcaBDocTurtor/DcaBTurtor'
import DcaBUndergraduate from '../doctor/DcaBDocUndergraduate/DcaBUndergraduate'
import DcaBApplyjob from '../doctor/DcaBDocApplyjob/DcaBApplyjob'
import DcaBAuditfive from '../doctor/DcaBDocAuditfive/DcaBAuditfive'
import DcaBLastemploy from '../doctor/DcaBDocLastemploy/DcaBLastemploy'
import DcaBPersonalsummary from '../doctor/DcaBDocPersonalsummary/DcaBDocPersonalsummaryList'
import DcaBPolitalshow from '../doctor/DcaBDocPolitalshow/DcaBPolitalshow'
import DcaBSciencesearch from '../doctor/DcaBDocSciencesearch/DcaBSciencesearch'
import DcaBFivecomment from '../doctor/DcaBDocFivecomment/DcaBFivecomment'
import DcaBGoal from '../doctor/DcaBDocGoal/DcaBDocGoalList'
import DcaBUndergraduateprize from '../doctor/DcaBDocUndergraduateprize/DcaBUndergraduateprize'
import DcaBUser from '../doctor/DcaBDocUser/DcaBDocUser'
import DcaBAttachfile from '../doctor/DcaBDocAttachfile/DcaBAttachfile'
import DcaBWorknum from '../doctor/DcaBDocWorknum/DcaBWorknum'
import DcaBExportcountry from '../doctor/DcaBDocExportcountry/DcaBExportcountry'
import DcaBPublicarticle from '../doctor/DcaBDocPublicarticle/DcaBPublicarticle'
import DcaBCourseclass from '../doctor/DcaBDocCourseclass/DcaBCourseclass'
import DcaBSchoolprize from '../doctor/DcaBDocSchoolprize/DcaBSchoolprize'
import DcaBTeacherprize from '../doctor/DcaBDocTeacherprize/DcaBTeacherprize'
import DcaBYoungprize from '../doctor/DcaBDocYoungprize/DcaBYoungprize'
//import DcaBDoctorturtor from '../doctor/DcaBDocDoctorturtor/DcaBDoctorturtor'
import DcaBMedicalaccident from '../doctor/DcaBDocMedicalaccident/DcaBMedicalaccident'
import DcaBAcademic from '../doctor/DcaBDocAcademic/DcaBAcademic'
import DcaBAchievement from '../doctor/DcaBDocAchievement/DcaBAchievement'
import DcaBQualification from '../doctor/DcaBDocQualification/DcaBQualification'
//import DcaBAuditDynamic from '../doctor/DcaBDocUserapply/DcaBAuditDynamic'

import DcaBAuditfivemonth from '../doctor/DcaBDocAuditfivemonth/DcaBDocAuditfivemonth'
import DcaBAuditfiveother from '../doctor/DcaBDocAuditfiveother/DcaBDocAuditfiveother'

export default {
  name: 'DcaBPatent2',
  components: {
    DcaBParttimeCustomer, DcaBEmploy, DcaBEducationexperice, DcaBEssaypublish, DcaBGraduate, DcaBPublicarticle, DcaBCourseclass, DcaBSchoolprize, DcaBTeacherprize, DcaBYoungprize, DcaBMedicalaccident,  
    DcaBInnovatebuild, DcaBOtherwork, DcaBPaperspublish, DcaBPatent, DcaBPrizeorpunish, DcaBSciencepublish,
    DcaBScientificprize, DcaBTalent, DcaBTeacherqualify, DcaBTurtor, DcaBUndergraduate, DcaBApplyjob,
    DcaBAuditfive, DcaBLastemploy, DcaBPersonalsummary, DcaBPolitalshow, DcaBSciencesearch, DcaBFivecomment, DcaBGoal, DcaBTeachtalent, DcaBUndergraduateprize, DcaBUser, DcaBAttachfile, DcaBWorknum, DcaBExportcountry
    , DcaBAcademic, DcaBAchievement, DcaBQualification, DcaBAuditfivemonth, DcaBAuditfiveother
   // DcaBDoctorturtor, DcaBAuditDynamicAudit, DcaBAuditDynamic
  },
  data () {
    return {
      mouduleTreeKey: +new Date(),
      loading: false,
      bordered: true,
      mouduleTreeData: [],
      allTreeKeys: [],
      formItemLayout,
      index: 6,
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
     let codes="3,60,130,140,170,180,190,200,210,212,290,300,310,320,50,150,160,240,110,340,7,401,120,250"
      this.$get('dcaDMudules/doctree/'+codes).then((r) => {
        var drows=r.data.rows.children
        drows[0].children=drows[0].children.filter(p=>p.id!=10) 
       // console.info(drows)
       // var drows=r.data.rows.children.filter(p=>p.id!=11) //医疗工作量
        this.mouduleTreeData = drows
        this.allTreeKeys = r.data.ids
      })
    },
    handleTreeClick (keys, event) {
     // console.info(event.node)
       if(event.node.getNodeChildren().length>0)
      {
        let previous = event.node.$el.firstElementChild;
        previous.click()
      }
      else{
        if(keys.length>0){
        this.index = keys[0]
        }
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
  padding: 8px !important;
}
.myBaseInfo .ant-card-head-title {
  color: red;
}

/* .ant-tree li span.ant-tree-switcher {
  width: 5px !important;
} */
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>

