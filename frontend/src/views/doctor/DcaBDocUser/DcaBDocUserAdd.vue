<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="部门描述"
      >
        <a-input
          placeholder="请输入部门描述"
          v-decorator="['deptDesc', {rules: [{ required: true, message: '部门描述不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="员工工号"
      >
        <a-input
          placeholder="请输入员工工号"
          v-decorator="['yggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="手机号"
      >
        <a-input
          placeholder="请输入手机号"
          v-decorator="['telephone', {rules: [{ required: true, message: '手机号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="华科人事编号"
      >
        <a-input
          placeholder="请输入华科人事编号"
          v-decorator="['hkrsbh', {rules: [{ required: true, message: '华科人事编号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="招收类型"
      >
        <a-input
          placeholder="请输入招收类型"
          v-decorator="['zslx', {rules: [{ required: true, message: '招收类型不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="流动站"
      >
        <a-input
          placeholder="请输入流动站"
          v-decorator="['ldz', {rules: [{ required: true, message: '流动站不能为空' }] }]"
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
        label="出生年月"
      >
        <a-date-picker v-decorator="[ 'birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="来院工作时间"
      >
        <a-date-picker v-decorator="[ 'schoolDate', {rules: [{ required: true, message: '来院工作时间不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="身份"
      >
        <a-input
          placeholder="请输入身份"
          v-decorator="['shenfen', {rules: [{ required: true, message: '身份不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="学历"
      >
        <a-input
          placeholder="请输入学历"
          v-decorator="['xueli', {rules: [{ required: true, message: '学历不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="博士导师"
      >
        <a-input
          placeholder="请输入博士导师"
          v-decorator="['boshidaoshi', {rules: [{ required: true, message: '博士导师不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="毕业时间"
      >
        <a-date-picker v-decorator="[ 'biyeDate', {rules: [{ required: true, message: '毕业时间不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="第几个聘期"
      >
        <a-input
          placeholder="请输入第几个聘期"
          v-decorator="['pinqiRanknum', {rules: [{ required: true, message: '第几个聘期不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否授权"
      >
        <a-input
          placeholder="请输入是否授权"
          v-decorator="['isAuthority', {rules: [{ required: true, message: '是否授权不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否转让"
      >
        <a-input
          placeholder="请输入是否转让"
          v-decorator="['isZhuanrang', {rules: [{ required: true, message: '是否转让不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="合作导师"
      >
        <a-input
          placeholder="请输入合作导师"
          v-decorator="['hezuodaoshi', {rules: [{ required: true, message: '合作导师不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="毕业院校"
      >
        <a-input
          placeholder="请输入毕业院校"
          v-decorator="['biyexuexiao', {rules: [{ required: true, message: '毕业院校不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="专业"
      >
        <a-input
          placeholder="请输入专业"
          v-decorator="['zhuanye', {rules: [{ required: true, message: '专业不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="进站日期"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'inDate', {rules: [{ required: true, message: '进站日期不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="站内工作目标"
      >
        <a-input
          placeholder="请输入站内工作目标"
          v-decorator="['goal', {rules: [{ required: true, message: '站内工作目标不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="延期时间"
      >
        <a-date-picker v-decorator="[ 'yqDate', {rules: [{ required: true, message: '延期时间不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="附件（临床）"
      >
        <a-input
          placeholder="请输入附件（临床）"
          v-decorator="['fileIdLc', {rules: [{ required: true, message: '附件（临床）不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="附件地址（临床）"
      >
        <a-input
          placeholder="请输入附件地址（临床）"
          v-decorator="['fileUrlLc', {rules: [{ required: true, message: '附件地址（临床）不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="员工组"
      >
        <a-input
          placeholder="请输入员工组"
          v-decorator="['yuangongzu', {rules: [{ required: true, message: '员工组不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="民族"
      >
        <a-input
          placeholder="请输入民族"
          v-decorator="['nation', {rules: [{ required: true, message: '民族不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现任岗位级别聘任时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'xrgwjbprsj', {rules: [{ required: true, message: '现任岗位级别聘任时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="籍贯"
      >
        <a-input
          placeholder="请输入籍贯"
          v-decorator="['jiguan', {rules: [{ required: true, message: '籍贯不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label=""
      >
        <a-input
          placeholder="请输入"
          v-decorator="['dcaYear', {rules: [{ required: true, message: '不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="政治面貌"
      >
        <a-input
          placeholder="请输入政治面貌"
          v-decorator="['politicalStatus', {rules: [{ required: true, message: '政治面貌不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="国籍"
      >
        <a-input
          placeholder="请输入国籍"
          v-decorator="['country', {rules: [{ required: true, message: '国籍不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="身份证号"
      >
        <a-input
          placeholder="请输入身份证号"
          v-decorator="['idCard', {rules: [{ required: true, message: '身份证号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="职位时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'staffDate', {rules: [{ required: true, message: '职位时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="照片id"
      >
        <a-input
          placeholder="请输入照片id"
          v-decorator="['pictureId', {rules: [{ required: true, message: '照片id不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="照片url"
      >
        <a-input
          placeholder="请输入照片url"
          v-decorator="['pictureUrl', {rules: [{ required: true, message: '照片url不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="聘期"
      >
        <a-input
          placeholder="请输入聘期"
          v-decorator="['pinqi', {rules: [{ required: true, message: '聘期不能为空' }] }]"
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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'DcaBDocUserAdd',
  props: {
    addVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBDocUser: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.dcaBDocUser = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('dcaBDocDocUser', {
            ...this.dcaBDocUser
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    setFields () {
      let values = this.form.getFieldsValue(['deptDesc', 'yggh', 'telephone', 'hkrsbh', 'zslx', 'ldz', 'sexName', 'birthday', 'schoolDate', 'shenfen', 'xueli', 'boshidaoshi', 'biyeDate', 'pinqiRanknum', 'isAuthority', 'isZhuanrang', 'hezuodaoshi', 'biyexuexiao', 'zhuanye', 'inDate', 'goal', 'yqDate', 'fileIdLc', 'fileUrlLc', 'yuangongzu', 'nation', 'xrgwjbprsj', 'jiguan', 'dcaYear', 'politicalStatus', 'country', 'idCard', 'staffDate', 'pictureId', 'pictureUrl', 'pinqi'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.dcaBDocUser[_key] = values[_key] })
      }
    }
  }
}
</script>
