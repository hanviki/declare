<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <a-layout id="custom-trigger">
      <a-layout-sider
        width="300"
        style="background: #fff;"
        v-model="collapsed" :trigger="null" collapsible
      >
        <a-card
          :bordered="true"
          class="card-area"
        >
          <a-tree
            :key="mouduleTreeKey"
            ref="menuTree"
            :treeData="mouduleTreeData"
            @select="handleTreeClick"
          >
          </a-tree>
        </a-card>
      </a-layout-sider>
      <a-layout>
        <a-layout-content><div style="position: relative">
          <div style="position: absolute;left:0;top:20px; z-index:99999" >
             <a-icon
          class="trigger"
          :type="collapsed ? 'menu-unfold' : 'menu-fold'"
          @click="() => (collapsed = !collapsed)"
        />
          </div>
          <dcaB-parttimejob v-if="index==1">
            <!--社会兼职-->
          </dcaB-parttimejob>
          <dcaB-employAudit v-if="index==6">
            <!--任职培养-->
          </dcaB-employAudit>
          <dcaB-educationexpericeAudit v-if="index==4">
            <!--学习工作经历-->
          </dcaB-educationexpericeAudit>
          <dcaB-essaypublishAudit v-if="index==7">
            <!--论文出版-->
          </dcaB-essaypublishAudit>
          <dcaB-graduateAudit v-if="index==20">
            <!--研究生情况-->
          </dcaB-graduateAudit>
          <dcaB-otherwork v-if="index==23">
            <!--其他工作及成果-->
          </dcaB-otherwork>
          <dcaB-paperspublishAudit v-if="index==21">
            <!--教学论文出版教材-->
          </dcaB-paperspublishAudit>
          <dcaB-patentAudit v-if="index==10">
            <!--申请专利-->
          </dcaB-patentAudit>
          <dcaB-prizeorpunishAudit v-if="index==2">
            <!--何时何地奖励或处分-->
          </dcaB-prizeorpunishAudit>
          <dcaB-sciencepublishAudit v-if="index==22">
            <!--科研论文-->
          </dcaB-sciencepublishAudit>
          <dcaB-scientificprizeAudit v-if="index==9">
            <!--自任职以来科研获奖情况-->
          </dcaB-scientificprizeAudit>
          <dcaB-talentAudit v-if="index==19">
            <!--任现职以来完成研究生教学人才培养情况-->
          </dcaB-talentAudit>
          <dcaB-teacherqualifyAudit v-if="index==12">
            <!--教师资格-->
          </dcaB-teacherqualifyAudit>
          <dcaB-turtorAudit v-if="index==13">
            <!--担任辅导员-->
          </dcaB-turtorAudit>
          <dcaB-undergraduateAudit v-if="index==16">
            <!--本科教学情况-->
          </dcaB-undergraduateAudit>
            <dcaB-applyjob v-if="index==15">
            <!--拟聘岗位-->
          </dcaB-applyjob>
            <dcaB-auditfive v-if="index==3">
            <!--近五年考核情况-->
          </dcaB-auditfive>
          <dcaB-lastemploy v-if="index==14">
            <!--完成上一聘期-->
          </dcaB-lastemploy>
          <dcaB-personalsummary v-if="index==11">
            <!--个人总结-->
          </dcaB-personalsummary>
           <dcaB-politalshow v-if="index==5">
            <!--个人思想政治表现-->
          </dcaB-politalshow>
           <dcaB-innovatebuildAudit v-if="index==17">
            <!--改革及建设项目-->
          </dcaB-innovatebuildAudit>
            <dcaB-sciencesearchAudit v-if="index==8">
            <!--科研项目-->
          </dcaB-sciencesearchAudit>
          <dcaB-fivecomment v-if="index==18">
            <!--近五年总体情况评价-->
          </dcaB-fivecomment>
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
import DcaBOtherwork from '../dca/DcaBOtherwork/DcaBOtherwork'
import DcaBPaperspublishAudit from '../dca/DcaBPaperspublish/DcaBPaperspublishAudit'
import DcaBPatentAudit from '../dca/DcaBPatent/DcaBPatentAudit'
import DcaBPrizeorpunishAudit from '../dca/DcaBPrizeorpunish/DcaBPrizeorpunishAudit'
import DcaBSciencepublishAudit from '../dca/DcaBSciencepublish/DcaBSciencepublishAudit'
import DcaBScientificprizeAudit from '../dca/DcaBScientificprize/DcaBScientificprizeAudit'
import DcaBTalentAudit from '../dca/DcaBTalent/DcaBTalentAudit'
import DcaBTeacherqualifyAudit from '../dca/DcaBTeacherqualify/DcaBTeacherqualifyAudit'
import DcaBTurtorAudit from '../dca/DcaBTurtor/DcaBTurtorAudit'
import DcaBUndergraduateAudit from '../dca/DcaBUndergraduate/DcaBUndergraduateAudit'
import DcaBApplyjob from '../dca/DcaBApplyjob/DcaBApplyjob'
import DcaBAuditfive from '../dca/DcaBAuditfive/DcaBAuditfive'
import DcaBLastemploy from '../dca/DcaBLastemploy/DcaBLastemploy'
import DcaBPersonalsummary from '../dca/DcaBPersonalsummary/DcaBPersonalsummary'
import DcaBPolitalshow from '../dca/DcaBPolitalshow/DcaBPolitalshow'
import DcaBSciencesearchAudit from '../dca/DcaBSciencesearch/DcaBSciencesearchAudit'
import DcaBFivecomment from '../dca/DcaBFivecomment/DcaBFivecomment'

export default {
  name: 'AuditBaseInfo',
  components: {
    DcaBParttimejob, DcaBEmployAudit, DcaBEducationexpericeAudit, DcaBEssaypublishAudit, DcaBGraduateAudit,
    DcaBInnovatebuildAudit, DcaBOtherwork, DcaBPaperspublishAudit, DcaBPatentAudit, DcaBPrizeorpunishAudit, DcaBSciencepublishAudit,
    DcaBScientificprizeAudit, DcaBTalentAudit, DcaBTeacherqualifyAudit, DcaBTurtorAudit, DcaBUndergraduateAudit, DcaBApplyjob ,
    DcaBAuditfive, DcaBLastemploy, DcaBPersonalsummary, DcaBPolitalshow, DcaBSciencesearchAudit, DcaBFivecomment
  },
  data () {
    return {
      mouduleTreeKey: +new Date(),
      loading: false,
      bordered: true,
      mouduleTreeData: [],
      allTreeKeys: [],
      formItemLayout,
      index: 1,
      collapsed: false
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
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
.ant-card-body{
  padding: 8px;
}
.ant-tabs-bar{
  margin-bottom: 8px;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>