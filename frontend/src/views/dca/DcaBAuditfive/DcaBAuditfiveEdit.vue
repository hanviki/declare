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
        label="人事编号"
      >
        <a-input
          placeholder="请输入人事编号"
          v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="工作内容"
      >
        <a-input
          placeholder="请输入工作内容"
          v-decorator="['adContent', {rules: [{ required: true, message: '工作内容不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="状态"
      >
        <a-input
          placeholder="请输入状态"
          v-decorator="['state', {rules: [{ required: true, message: '状态不能为空' }] }]"
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
  name: 'DcaBAuditfiveEdit',
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
      dcaBAuditfive: {}
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
    setFormValues ({ ...dcaBAuditfive }) {
      let fields = ['userAccount', 'adContent', 'state']
      let fieldDates = []
      Object.keys(dcaBAuditfive).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBAuditfive[key] !== '') {
              obj[key] = moment(dcaBAuditfive[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBAuditfive[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBAuditfive.id = dcaBAuditfive.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBAuditfive = this.form.getFieldsValue()
          dcaBAuditfive.id = this.dcaBAuditfive.id                            
          this.$put('dcaBAuditfive', {
            ...dcaBAuditfive
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
