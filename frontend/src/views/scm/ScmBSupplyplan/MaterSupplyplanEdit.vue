<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width=700
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="采购订单"
          >
            <a-input
              :disabled="true"
              v-decorator="['ebeln', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="项目号"
          >
            <a-input
              :disabled="true"
              v-decorator="['ebelp', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="药品编码"
          >
            <a-input
              :disabled="true"
              v-decorator="['matnr', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="药品名称"
          >
            <a-input
              :disabled="true"
              v-decorator="['txz01', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="采购数量"
          >
            <a-input
              :disabled="true"
              v-decorator="['menge', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="采购单价"
          >
            <a-input
              :disabled="true"
              v-decorator="['netpr', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="供应数量"
          >
            <a-input-number
              :min="0"
              :max="amount"
              @blur="mengeBlur"
              placeholder="请输入供应数量"
              v-decorator="['gMenge',  { rules: [{ required: true, message: '供应数量不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="联系人"
          >
            <a-input
              :disabled="true"
              placeholder="请输入联系人"
              v-decorator="['linkPerson', { rules: [{  message: '联系人不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="送达科室"
          >
            <a-input
              :disabled="true"
              placeholder="请输入送达科室"
              v-decorator="['sendDepart', { rules: [{  message: '送达科室不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="联系方式"
          >
            <a-input
              placeholder="请输入联系方式"
              v-decorator="['linkTelephone', { rules: [{ message: '联系方式不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="发票号码"
          >
            <a-input
              placeholder="请输入发票号码"
              v-decorator="['fphm', { rules: [{ required: true, message: '发票号码不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="发票金额"
          >
            <a-input
              :disabled="true"
              :precision="4"
              placeholder="请输入发票金额"
              v-decorator="['fpjr', { rules: [{ required: true, message: '发票金额不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="开票日期"
          >
            <a-date-picker v-decorator="[ 'fprq', { rules: [{ required: true, message: '开票日期不能为空' }] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="商品条码"
          >
            <a-input
              placeholder="请输入商品条码"
              v-decorator="['fpbm', {}]"
            />
          </a-form-item>
        </a-col>
      </a-row>
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
  name: 'ScmBSupplyplanEdit',
  props: {
    editVisiable: {
      default: false
    },
    price: {
      type: Number,
      default: 0
    },
    amount: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBSupplyplan: {
        baseId: '',
        id: ''
      },
      chargeData: []
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
    setFormValues ({ ...scmBSupplyplan }) {
      let fields = ['gMenge', 'linkPerson', 'fphm', 'fpjr', 'fprq', 'sendDepart', 'linkTelephone', 'materCode']
      let fieldDates = ['fprq', 'createTime', 'modifyTime']
      Object.keys(scmBSupplyplan).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBSupplyplan[key] !== null && scmBSupplyplan[key] !== '') {
              obj[key] = moment(scmBSupplyplan[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = scmBSupplyplan[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBSupplyplan.id = scmBSupplyplan.id
      this.scmBSupplyplan.baseId = scmBSupplyplan.baseId
    },
    setOrderFormValues ({ ...order }) {
      let fields = ['menge', 'ebeln', 'ebelp', 'netpr', 'matnr', 'txz01']
      Object.keys(order).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}

          obj[key] = order[key]
          this.form.setFieldsValue(obj)
        }
      })
    },
    mengeBlur (e) {
      if (e.target.value) {
        let money = this.price * e.target.value
        this.form.setFields({ fpjr: { value: money } })
      }

    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBSupplyplan = this.form.getFieldsValue()
          scmBSupplyplan.id = this.scmBSupplyplan.id
          scmBSupplyplan.baseId = this.scmBSupplyplan.baseId
          this.$put('scmBSupplyplan', {
            ...scmBSupplyplan
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  },
  watch: {
    editVisiable: {
      handler: function () {
        // if (this.editVisiable) {
        //   this.$get("scmBGysMaterPic/charge/" + this.scmBSupplyplan.baseId).then(r => {
        //     this.chargeData = r.data
        //   })
        // }
      }
    }
  },
  mounted () {

  }
}
</script>
