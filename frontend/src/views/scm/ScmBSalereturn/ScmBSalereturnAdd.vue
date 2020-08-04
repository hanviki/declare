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
        label="物料编码"
      >
        <scm-materInfo
          ref="upfc"
          v-decorator="['matnr2', {rules: [{ required: true, message: '物料编码不能为空' }]}]"
        >
        </scm-materInfo>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="批次号"
      >
        <a-input
          placeholder="请输入批次号"
          v-decorator="['charg',  { rules: [{ required: true, message: '批次号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="退货数量"
      >
        <a-input-number
          style="width: 100%"
          placeholder="请输入退货数量"
          v-decorator="['amount',  { rules: [{ required: true, message: '退货数量不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="退货原因"
      >
        <a-select
          mode="single"
          :allowClear="true"
          style="width: 100%"
          v-decorator="[
          'saleCause',
          { rules: [{ required: true, message: '请输入退货原因' }] }
        ]"
        >
          <a-select-option value="药品呆滞">药品呆滞</a-select-option>
          <a-select-option value="药品破损">药品破损</a-select-option>
          <a-select-option value="药品近效期">药品近效期</a-select-option>
          <a-select-option value="药品召回">药品召回</a-select-option>
          <a-select-option value="其他">其他</a-select-option>
        </a-select>
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
  labelCol: { span: 8 },
  wrapperCol: { span: 13 }
}
import scmMaterInfo from './ScmMaterInfo'
export default {
  name: 'ScmBSalereturnAdd',
  components: { scmMaterInfo },
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
      scmBSalereturn: {
          matnrId:''
      }
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmBSalereturn = {}
       this.$refs.upfc.reset()
      this.$refs.upfc.matnr = ''
      this.scmBSalereturn.matnrId = ''
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.scmBSalereturn.matnrId = this.$refs.upfc.matnr
      if (this.scmBSalereturn.matnrId==null||this.scmBSalereturn.matnrId == '') {
        this.$message.warning('请在下列列表里选择物料.')
        return false
      }
      this.form.setFieldsValue({ matnr2: '2' })//防止显示提示错误
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('scmBSalereturn', {
            ...this.scmBSalereturn
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
      let values = this.form.getFieldsValue(['charg', 'amount', 'saleCause'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBSalereturn[_key] = values[_key] })
      }
    }
  }
}
</script>
