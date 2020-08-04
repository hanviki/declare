<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width=700
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="24">
          <a-button
            @click="addSendInfo"
            icon="plus"
          >引入送货单</a-button>
        </a-col>
      </a-row>
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
    <send-info
      :sendVisiable="sendVisiable"
      @sucess="handleSendInfoSuccess"
      @close="handleSendInfoClose"
    >
    </send-info>
  </a-drawer>
</template>
<script>
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 }
}
import SendInfo from './SendInfo'
export default {
  name: 'ScmBSupplyplanAdd',
  components: { SendInfo },
  props: {
    addVisiable: {
      default: false
    },
    baseId: '',
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
      },
      chargeData: [],
      sendVisiable: false
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmBSupplyplan = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    addSendInfo () {
      this.sendVisiable = true
    },
    handleSendInfoSuccess (sendInfo) {
      this.sendVisiable = false
      this.form.setFieldsValue({ gMenge: sendInfo.amount })
      this.form.setFieldsValue({ linkTelephone: sendInfo.linkTelephone })
      this.form.setFieldsValue({ fpjr: sendInfo.amount * this.price })
      this.form.setFieldsValue({ materCode: sendInfo.materCode })
    },
    handleSendInfoClose () {
      this.sendVisiable = false
    },
    mengeBlur (e) {
      if (e.target.value) {
        let money = this.price * e.target.value
        this.form.setFields({ fpjr: { value: money } })
      }
    },
    setFormValues ({ ...scmBSupplyplan }) {
      // let fields = ['linkPerson', 'sendDepart', 'linkTelephone']
      //  let fields = ['sendDeaprtContact', 'sendDeaprtName', 'sendDeaprtPhone']
      this.form.setFieldsValue({ linkPerson: scmBSupplyplan.sendDeaprtContact })
      this.form.setFieldsValue({ sendDepart: scmBSupplyplan.sendDeaprtName })
      this.form.setFieldsValue({ linkTelephone: scmBSupplyplan.sendDeaprtPhone })
      let fields = ['menge', 'ebeln', 'ebelp', 'netpr', 'matnr', 'txz01']
      Object.keys(scmBSupplyplan).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}

          obj[key] = scmBSupplyplan[key]
          this.form.setFieldsValue(obj)
        }
      })
    },

    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.scmBSupplyplan.baseId = this.baseId
          this.scmBSupplyplan.status = 0
          this.scmBSupplyplan.bsartD = 1//订单类型 物资
          this.$post('scmBSupplyplan', {
            ...this.scmBSupplyplan
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
      let values = this.form.getFieldsValue(['gMenge', 'linkPerson', 'fphm', 'fpjr', 'fprq', 'sendDepart', 'linkTelephone', 'materCode'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBSupplyplan[_key] = values[_key] })
      }
    }
  },
  watch: {
    addVisiable: {
      handler: function () {
        // if (this.addVisiable) {
        //   this.$get("scmBGysMaterPic/charge/" + this.baseId).then(r => {
        //     this.chargeData = r.data
        //   })
        // }
      }
    }
  }
}
</script>
