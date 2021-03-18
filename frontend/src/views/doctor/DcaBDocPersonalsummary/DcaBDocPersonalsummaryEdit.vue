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
        label="申报年度"
      >
        <a-input-number
          placeholder="请输入申报年度"
          v-decorator="['dcaYear', {rules: [{ required: true, message: '申报年度不能为空' }] }]"
          >
          </a-input-number>
          </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="总结内容"
      >
        <a-textarea
          placeholder="请输入总结内容"
          :rows="12"
          v-decorator="['psContent', {rules: [{ required: true, message: '总结内容不能为空' }] }]"
        ></a-textarea>
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
  labelCol: { span: 5 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'DcaBDocPersonalsummaryEdit',
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
      dcaBDocPersonalsummary: {}
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
    setFormValues ({ ...dcaBDocPersonalsummary }) {
      let fields = ['psContent', 'dcaYear']
      let fieldDates = []
      Object.keys(dcaBDocPersonalsummary).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocPersonalsummary[key] !== '') {
              obj[key] = moment(dcaBDocPersonalsummary[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBDocPersonalsummary[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBDocPersonalsummary.id = dcaBDocPersonalsummary.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocPersonalsummary = this.form.getFieldsValue()
          dcaBDocPersonalsummary.id = this.dcaBDocPersonalsummary.id
          this.$put('dcaBDocPersonalsummary', {
            ...dcaBDocPersonalsummary
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
