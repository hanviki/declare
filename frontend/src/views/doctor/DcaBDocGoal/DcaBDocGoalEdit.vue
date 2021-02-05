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
        <a-input
          placeholder="请输入申报年度"
          v-decorator="['dcaYear', {rules: [{ required: true, message: '申报年度不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="拟聘岗位工作思路及预期目标"
      >
        <a-textarea
          placeholder="请输入拟聘岗位工作思路及预期目标"
          :rows="10"
          v-decorator="['preGoal', {rules: [{ required: true, message: '拟聘岗位工作思路及预期目标不能为空' }] }]"
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
  labelCol: { span: 8 },
  wrapperCol: { span: 15 }
}
export default {
  name: 'DcaBDocGoalEdit',
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
      dcaBDocGoal: {}
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
    setFormValues ({ ...dcaBDocGoal }) {
      let fields = ['preGoal', 'dcaYear']
      let fieldDates = []
      Object.keys(dcaBDocGoal).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocGoal[key] !== '') {
              obj[key] = moment(dcaBDocGoal[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBDocGoal[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBDocGoal.id = dcaBDocGoal.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocGoal = this.form.getFieldsValue()
          dcaBDocGoal.id = this.dcaBDocGoal.id
          this.$put('dcaBDocGoal', {
            ...dcaBDocGoal
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
