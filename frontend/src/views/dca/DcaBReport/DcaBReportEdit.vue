<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="起止年度"
      >
        <a-input
          placeholder="请输入起止年度"
          v-decorator="['year', {rules: [{ required: true, message: '起止年度不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="确认顺序号"
      >
        <a-input
          placeholder="请输入确认顺序号"
          v-decorator="['confirmIndex', {rules: [{ required: true, message: '确认顺序号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="档案袋顺序号"
      >
        <a-input
          placeholder="请输入档案袋顺序号"
          v-decorator="['danganIndex', {rules: [{ required: true, message: '档案袋顺序号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="报名顺序号"
      >
        <a-input
          placeholder="请输入报名顺序号"
          v-decorator="['baomingIndex', {rules: [{ required: true, message: '报名顺序号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="系列"
      >
        <a-input
          placeholder="请输入系列"
          v-decorator="['xl', {rules: [{ required: true, message: '系列不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="评审分组"
      >
        <a-input
          placeholder="请输入评审分组"
          v-decorator="['pingshenfenzu', {rules: [{ required: true, message: '评审分组不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="双报标志"
      >
        <a-input
          placeholder="请输入双报标志"
          v-decorator="['ifshuangbao', {rules: [{ required: true, message: '双报标志不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="申报等级"
      >
        <a-input
          placeholder="请输入申报等级"
          v-decorator="['gwdj', {rules: [{ required: true, message: '申报等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="科室"
      >
        <a-input
          placeholder="请输入科室"
          v-decorator="['ks', {rules: [{ required: true, message: '科室不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="科室分类"
      >
        <a-input
          placeholder="请输入科室分类"
          v-decorator="['kslb', {rules: [{ required: true, message: '科室分类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="出生年月"
      >
        <a-input
          placeholder="请输入出生年月"
          v-decorator="['birthdaystr', {rules: [{ required: true, message: '出生年月不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="年龄至20201031"
      >
        <a-input
          placeholder="请输入年龄至20201031"
          v-decorator="['age', {rules: [{ required: true, message: '年龄至20201031不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="性别"
      >
        <a-input
          placeholder="请输入性别"
          v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="学历(位)"
      >
        <a-input
          placeholder="请输入学历(位)"
          v-decorator="['edu', {rules: [{ required: true, message: '学历(位)不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="毕业时间"
      >
        <a-input
          placeholder="请输入毕业时间"
          v-decorator="['eduDate', {rules: [{ required: true, message: '毕业时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现职务名称"
      >
        <a-input
          placeholder="请输入现职务名称"
          v-decorator="['positionName', {rules: [{ required: true, message: '现职务名称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="聘任时间"
      >
        <a-input
          placeholder="请输入聘任时间"
          v-decorator="['zygwDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="申报职称"
      >
        <a-input
          placeholder="请输入申报职称"
          v-decorator="['npPositionName', {rules: [{ required: true, message: '申报职称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="来院时间"
      >
        <a-input
          placeholder="请输入来院时间"
          v-decorator="['schoolDate', {rules: [{ required: true, message: '来院时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否起带头或骨干作用"
      >
        <a-input
          placeholder="请输入是否起带头或骨干作用"
          v-decorator="['ifdaitou', {rules: [{ required: true, message: '是否起带头或骨干作用不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="医疗评分"
      >
        <a-input
          placeholder="请输入医疗评分"
          v-decorator="['ylpfbfz', {rules: [{ required: true, message: '医疗评分不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学评分"
      >
        <a-input
          placeholder="请输入教学评分"
          v-decorator="['jxpf', {rules: [{ required: true, message: '教学评分不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学科研项目或获奖情况是否符合"
      >
        <a-input
          placeholder="请输入教学科研项目或获奖情况是否符合"
          v-decorator="['iffuhekeyan', {rules: [{ required: true, message: '教学科研项目或获奖情况是否符合不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="第一作者论文情况是否符合"
      >
        <a-input
          placeholder="请输入第一作者论文情况是否符合"
          v-decorator="['iffuhediyi', {rules: [{ required: true, message: '第一作者论文情况是否符合不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否符合必备条件"
      >
        <a-input
          placeholder="请输入是否符合必备条件"
          v-decorator="['iffuhebibei', {rules: [{ required: true, message: '是否符合必备条件不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="国家、省部级科研奖名称"
      >
        <a-input
          placeholder="请输入国家、省部级科研奖名称"
          v-decorator="['sciName', {rules: [{ required: true, message: '国家、省部级科研奖名称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="国家、省部级科研奖等级"
      >
        <a-input
          placeholder="请输入国家、省部级科研奖等级"
          v-decorator="['sciDengji', {rules: [{ required: true, message: '国家、省部级科研奖等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="国家、省部级科研奖排名"
      >
        <a-input
          placeholder="请输入国家、省部级科研奖排名"
          v-decorator="['sciRanknum', {rules: [{ required: true, message: '国家、省部级科研奖排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学名称"
      >
        <a-input
          placeholder="请输入教学名称"
          v-decorator="['teachName', {rules: [{ required: true, message: '教学名称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学等级"
      >
        <a-input
          placeholder="请输入教学等级"
          v-decorator="['teachDengji', {rules: [{ required: true, message: '教学等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学排名"
      >
        <a-input
          placeholder="请输入教学排名"
          v-decorator="['teachRanknum', {rules: [{ required: true, message: '教学排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="发明专利项数"
      >
        <a-input
          placeholder="请输入发明专利项数"
          v-decorator="['patentNum', {rules: [{ required: true, message: '发明专利项数不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="实施转让费"
      >
        <a-input
          placeholder="请输入实施转让费"
          v-decorator="['patentFund', {rules: [{ required: true, message: '实施转让费不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="A 类"
      >
        <a-input
          placeholder="请输入A 类"
          v-decorator="['publishA', {rules: [{ required: true, message: 'A 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="B 类"
      >
        <a-input
          placeholder="请输入B 类"
          v-decorator="['publishB', {rules: [{ required: true, message: 'B 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="C 类"
      >
        <a-input
          placeholder="请输入C 类"
          v-decorator="['publishC', {rules: [{ required: true, message: 'C 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="D 类"
      >
        <a-input
          placeholder="请输入D 类"
          v-decorator="['publishD', {rules: [{ required: true, message: 'D 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="E 类"
      >
        <a-input
          placeholder="请输入E 类"
          v-decorator="['publishE', {rules: [{ required: true, message: 'E 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="F 类"
      >
        <a-input
          placeholder="请输入F 类"
          v-decorator="['publishF', {rules: [{ required: true, message: 'F 类不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="D类以上"
      >
        <a-input
          placeholder="请输入D类以上"
          v-decorator="['publishDup', {rules: [{ required: true, message: 'D类以上不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="E类以上"
      >
        <a-input
          placeholder="请输入E类以上"
          v-decorator="['publishEup', {rules: [{ required: true, message: 'E类以上不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="F类以上"
      >
        <a-input
          placeholder="请输入F类以上"
          v-decorator="['publishFup', {rules: [{ required: true, message: 'F类以上不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="出版书类别"
      >
        <a-input
          placeholder="请输入出版书类别"
          v-decorator="['publicarticle1', {rules: [{ required: true, message: '出版书类别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="承担字数(万)"
      >
        <a-input
          placeholder="请输入承担字数(万)"
          v-decorator="['publicarticle2', {rules: [{ required: true, message: '承担字数(万)不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学质量奖与成果奖名称"
      >
        <a-input
          placeholder="请输入教学质量奖与成果奖名称"
          v-decorator="['schoolprizeName', {rules: [{ required: true, message: '教学质量奖与成果奖名称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="等级"
      >
        <a-input
          placeholder="请输入等级"
          v-decorator="['schoolprizeDengji', {rules: [{ required: true, message: '等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="排名"
      >
        <a-input
          placeholder="请输入排名"
          v-decorator="['schoolprizeRanknum', {rules: [{ required: true, message: '排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="时间"
      >
        <a-input
          placeholder="请输入时间"
          v-decorator="['schoolprizeDate', {rules: [{ required: true, message: '时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="精品课程名称"
      >
        <a-input
          placeholder="请输入精品课程名称"
          v-decorator="['courseName', {rules: [{ required: true, message: '精品课程名称不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="排名"
      >
        <a-input
          placeholder="请输入排名"
          v-decorator="['courseRanknum', {rules: [{ required: true, message: '排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="时间"
      >
        <a-input
          placeholder="请输入时间"
          v-decorator="['courseDate', {rules: [{ required: true, message: '时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学竞赛获奖奖项级别"
      >
        <a-input
          placeholder="请输入教学竞赛获奖奖项级别"
          v-decorator="['youngName', {rules: [{ required: true, message: '教学竞赛获奖奖项级别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="等级"
      >
        <a-input
          placeholder="请输入等级"
          v-decorator="['youngDengji', {rules: [{ required: true, message: '等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="排名"
      >
        <a-input
          placeholder="请输入排名"
          v-decorator="['youngRanknum', {rules: [{ required: true, message: '排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="时间"
      >
        <a-input
          placeholder="请输入时间"
          v-decorator="['youngDate', {rules: [{ required: true, message: '时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="科研项目教改项目类别"
      >
        <a-input
          placeholder="请输入科研项目教改项目类别"
          v-decorator="['sciDjlb', {rules: [{ required: true, message: '科研项目教改项目类别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="经费"
      >
        <a-input
          placeholder="请输入经费"
          v-decorator="['sciDjfund', {rules: [{ required: true, message: '经费不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="排名"
      >
        <a-input
          placeholder="请输入排名"
          v-decorator="['sciDjranknum', {rules: [{ required: true, message: '排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="实到校单项科研经费类别"
      >
        <a-input
          placeholder="请输入实到校单项科研经费类别"
          v-decorator="['sciJflb', {rules: [{ required: true, message: '实到校单项科研经费类别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="实到校单项科研经费类别"
      >
        <a-input
          placeholder="请输入实到校单项科研经费类别"
          v-decorator="['sciJffund', {rules: [{ required: true, message: '实到校单项科研经费类别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="排名"
      >
        <a-input
          placeholder="请输入排名"
          v-decorator="['sciJfranknum', {rules: [{ required: true, message: '排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="医疗评分等级"
      >
        <a-input
          placeholder="请输入医疗评分等级"
          v-decorator="['ylpfdj', {rules: [{ required: true, message: '医疗评分等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教学评分等级"
      >
        <a-input
          placeholder="请输入教学评分等级"
          v-decorator="['jxpfdj', {rules: [{ required: true, message: '教学评分等级不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="评分合计"
      >
        <a-input
          placeholder="请输入评分合计"
          v-decorator="['pfHeji', {rules: [{ required: true, message: '评分合计不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否担任一年辅导员或班主任"
      >
        <a-input
          placeholder="请输入是否担任一年辅导员或班主任"
          v-decorator="['tutor', {rules: [{ required: true, message: '是否担任一年辅导员或班主任不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="申报类型"
      >
        <a-input
          placeholder="请输入申报类型"
          v-decorator="['sblx', {rules: [{ required: true, message: '申报类型不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="达到选择条件一之第几条"
      >
        <a-input
          placeholder="请输入达到选择条件一之第几条"
          v-decorator="['choosepos', {rules: [{ required: true, message: '达到选择条件一之第几条不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="材料审核结果"
      >
        <a-input
          placeholder="请输入材料审核结果"
          v-decorator="['clshjg', {rules: [{ required: true, message: '材料审核结果不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="拟退原因"
      >
        <a-input
          placeholder="请输入拟退原因"
          v-decorator="['ntyy', {rules: [{ required: true, message: '拟退原因不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="科室排名"
      >
        <a-input
          placeholder="请输入科室排名"
          v-decorator="['ksrank', {rules: [{ required: true, message: '科室排名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="教师资格证"
      >
        <a-input
          placeholder="请输入教师资格证"
          v-decorator="['teacherQualify', {rules: [{ required: true, message: '教师资格证不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="内聘情况"
      >
        <a-input
          placeholder="请输入内聘情况"
          v-decorator="['npqk', {rules: [{ required: true, message: '内聘情况不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="出国情况"
      >
        <a-input
          placeholder="请输入出国情况"
          v-decorator="['borad', {rules: [{ required: true, message: '出国情况不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="备注"
      >
        <a-input
          placeholder="请输入备注"
          v-decorator="['note', {rules: [{ required: true, message: '备注不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系方式"
      >
        <a-input
          placeholder="请输入联系方式"
          v-decorator="['telephone', {rules: [{ required: true, message: '联系方式不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="等级"
      >
        <a-input
          placeholder="请输入等级"
          v-decorator="['courseDengji', {rules: [{ required: true, message: '等级不能为空' }] }]"
        />
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'DcaBReportEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBReport: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({ ...dcaBReport }) {
      let fields = ['year', 'confirmIndex', 'danganIndex', 'baomingIndex', 'xl', 'pingshenfenzu', 'ifshuangbao', 'gwdj', 'ks', 'kslb', 'birthdaystr', 'age', 'sexName', 'edu', 'eduDate', 'positionName', 'zygwDate', 'npPositionName', 'schoolDate', 'ifdaitou', 'ylpfbfz', 'jxpf', 'iffuhekeyan', 'iffuhediyi', 'iffuhebibei', 'sciName', 'sciDengji', 'sciRanknum', 'teachName', 'teachDengji', 'teachRanknum', 'patentNum', 'patentFund', 'publishA', 'publishB', 'publishC', 'publishD', 'publishE', 'publishF', 'publishDup', 'publishEup', 'publishFup', 'publicarticle1', 'publicarticle2', 'schoolprizeName', 'schoolprizeDengji', 'schoolprizeRanknum', 'schoolprizeDate', 'courseName', 'courseRanknum', 'courseDate', 'youngName', 'youngDengji', 'youngRanknum', 'youngDate', 'sciDjlb', 'sciDjfund', 'sciDjranknum', 'sciJflb', 'sciJffund', 'sciJfranknum', 'ylpfdj', 'jxpfdj', 'pfHeji', 'tutor', 'sblx', 'choosepos', 'clshjg', 'ntyy', 'ksrank', 'teacherQualify', 'npqk', 'borad', 'note', 'telephone', 'courseDengji']
      let fieldDates = []
      Object.keys(dcaBReport).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBReport[key] !== '') {
              obj[key] = moment(dcaBReport[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBReport[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBReport.id = dcaBReport.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBReport = this.form.getFieldsValue()
          dcaBReport.id = this.dcaBReport.id
          this.$put('dcaBReport', {
            ...dcaBReport
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>
