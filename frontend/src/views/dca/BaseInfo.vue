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
          <dcaBParttime-customer v-if="index==1">
            <!--社会兼职-->
          </dcaBParttime-customer>
          <dcaB-employ v-if="index==6">
            <!--任职培养-->
          </dcaB-employ>
          <dcaB-educationexperice v-if="index==4">
            <!--学习工作经历-->
          </dcaB-educationexperice>
          <dcaB-essaypublish v-if="index==7">
            <!--论文出版-->
          </dcaB-essaypublish>
          <dcaB-graduate v-if="index==20">
            <!--研究生情况-->
          </dcaB-graduate>
          <dcaB-otherwork v-if="index==23">
            <!--其他工作及成果-->
          </dcaB-otherwork>
          <dcaB-paperspublish v-if="index==21">
            <!--教学论文出版教材-->
          </dcaB-paperspublish>
          <dcaB-patent v-if="index==10">
            <!--申请专利-->
          </dcaB-patent>
          <dcaB-prizeorpunish v-if="index==2">
            <!--何时何地奖励或处分-->
          </dcaB-prizeorpunish>
          <dcaB-sciencepublish v-if="index==22">
            <!--科研论文-->
          </dcaB-sciencepublish>
          <dcaB-scientificprize v-if="index==9">
            <!--自任职以来科研获奖情况-->
          </dcaB-scientificprize>
          <dcaB-talent v-if="index==19">
            <!--任现职以来完成研究生教学人才培养情况-->
          </dcaB-talent>
          <dcaB-teacherqualify v-if="index==12">
            <!--教师资格-->
          </dcaB-teacherqualify>
          <dcaB-turtor v-if="index==13">
            <!--担任辅导员-->
          </dcaB-turtor>
          <dcaB-undergraduate v-if="index==16">
            <!--本科教学情况-->
          </dcaB-undergraduate>
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
           <dcaB-innovatebuild v-if="index==17">
            <!--改革及建设项目-->
          </dcaB-innovatebuild>
            <dcaB-sciencesearch v-if="index==8">
            <!--科研项目-->
          </dcaB-sciencesearch>
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
import DcaBTurtor from '../dca/DcaBTurtor/DcaBTurtor'
import DcaBUndergraduate from '../dca/DcaBUndergraduate/DcaBUndergraduate'
import DcaBApplyjob from '../dca/DcaBApplyjob/DcaBApplyjob'
import DcaBAuditfive from '../dca/DcaBAuditfive/DcaBAuditfive'
import DcaBLastemploy from '../dca/DcaBLastemploy/DcaBLastemploy'
import DcaBPersonalsummary from '../dca/DcaBPersonalsummary/DcaBPersonalsummary'
import DcaBPolitalshow from '../dca/DcaBPolitalshow/DcaBPolitalshow'
import DcaBSciencesearch from '../dca/DcaBSciencesearch/DcaBSciencesearch'
import DcaBFivecomment from '../dca/DcaBFivecomment/DcaBFivecomment'

export default {
  name: 'DcaBPatent2',
  components: {
    DcaBParttimeCustomer, DcaBEmploy, DcaBEducationexperice, DcaBEssaypublish, DcaBGraduate,
    DcaBInnovatebuild, DcaBOtherwork, DcaBPaperspublish, DcaBPatent, DcaBPrizeorpunish, DcaBSciencepublish,
    DcaBScientificprize, DcaBTalent, DcaBTeacherqualify, DcaBTurtor, DcaBUndergraduate, DcaBApplyjob ,
    DcaBAuditfive, DcaBLastemploy, DcaBPersonalsummary, DcaBPolitalshow, DcaBSciencesearch, DcaBFivecomment
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
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>