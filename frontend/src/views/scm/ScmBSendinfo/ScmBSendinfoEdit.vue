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
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="物料编码"
          >
            <up-fc
              ref="upfc"
              @sucess="setMaterName"
              v-decorator="['matnr2', {rules: [{ required: true, message: '物料编码不能为空' }]}]"
            >
            </up-fc>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="物料名称"
          >
            <a-input
              :disabled="true"
              placeholder="请输入物料名称"
              v-decorator="['txz01', { rules: [{ required: true, message: '物料名称不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="联系人"
          >
            <a-input
              placeholder="请输入联系人"
              v-decorator="['linkPerson', { rules: [{ required: true, message: '联系人不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="送达科室"
          >
            <send-depart
              ref="sendDepart"
              @sucess="departSuccess"
              v-decorator="['sendDepart2', {rules: [{ required: true, message: '送达科室不能为空' }]}]"
            >
            </send-depart>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="联系方式"
          >
            <a-input
              placeholder="请输入联系方式"
              v-decorator="['linkTelephone', { rules: [{ required: true, message: '联系方式不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="商品条码"
          >
            <a-input
              placeholder="请输入商品条码"
              v-decorator="['materCode', { rules: [{ required: true, message: '商品条码不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="送货数量"
          >
            <a-input-number
              :precision="2"
              @blur="amountBlur"
              placeholder="请输入送货数量"
              v-decorator="['amount', { rules: [{ required: true, message: '送货数量不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="单价"
          >
            <a-input-number
              :precision="2"
              @blur="priceBlur"
              placeholder="请输入单价"
              v-decorator="['price', { rules: [{ required: true, message: '单价不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="送货金额"
          >
            <a-input-number
              :precision="2"
              :disabled="true"
              placeholder="请输入送货金额"
              v-decorator="['money', { rules: [{ required: true, message: '送货金额不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="送货院区"
          >
            <a-select
              @change="handleWerksChange"
              placeholder="请输入送货院区"
              v-decorator="['werks', 
              { rules: [{ required: true, message: '送货院区不能为空' }] }]"
            >
              <a-select-option value="2000">武汉协和医院-本部</a-select-option>
              <a-select-option value="2001">武汉协和医院-肿瘤中心</a-select-option>
              <a-select-option value="2002">武汉协和医院-西院</a-select-option>
            </a-select>
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
import upFc from './MaterInfo'
import sendDepart from './DepartInfo'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 13 }
}
export default {
  components: { upFc ,sendDepart },
  name: 'ScmBSendinfoEdit',
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
      scmBSendinfo: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.$refs.upfc.reset()
      this.$refs.upfc.matnr = ''
      this.scmBSendinfo.matnr = ''
      this.scmBSendinfo.sendDepart = ''
      this.$refs.sendDepart.reset()
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setMaterName (mObj) {
      this.form.setFieldsValue({ txz01: mObj.maktx })//防止显示提示错误
      this.scmBSendinfo.meins = mObj.meins
      this.scmBSendinfo.mseht = mObj.mseht
    },
    departSuccess (departName) {
      this.scmBSendinfo.sendDepart = departName
    },
    amountBlur (e) {
      if (e.target.value) {
        let price = this.form.getFieldValue('price')
        if (price) {
          let num = e.target.value * price
          this.form.setFields({ money: { value: num } })
        }
      }

    },
    priceBlur (e) {
      if (e.target.value) {
        let amount = this.form.getFieldValue('amount')
        if (amount) {
          let num = e.target.value * amount
          this.form.setFields({ money: { value: num } })
        }
      }
    },
    handleWerksChange (value, option) {
      this.scmBSendinfo.werks = value
      this.scmBSendinfo.werkst = option.componentOptions.children[0].text
    },
    setFormValues ({ ...scmBSendinfo }) {
      let fields = ['txz01', 'linkPerson', 'linkTelephone', 'materCode', 'sendOrderCode', 'amount', 'money', 'price','werks']
      let fieldDates = ['createTime', 'modifyTime']
      Object.keys(scmBSendinfo).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBSendinfo[key] !== '') {
              obj[key] = moment(scmBSendinfo[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = scmBSendinfo[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBSendinfo.id = scmBSendinfo.id
      this.scmBSendinfo.matnr = scmBSendinfo.matnr
      this.scmBSendinfo.sendDepart = scmBSendinfo.sendDepart
      let that=this
      setTimeout(function () {
        that.$refs.sendDepart.setFormValue(that.scmBSendinfo.sendDepart);
        that.$refs.upfc.setFormValue(that.scmBSendinfo.matnr)
      }, 200)
    },
    handleSubmit () {
      this.scmBSendinfo.matnr = this.$refs.upfc.matnr
      if (this.scmBSendinfo.matnr == null || this.scmBSendinfo.matnr == '') {
        this.$message.warning('请在下列列表里选择物料.')
        return false
      }
      if (this.scmBSendinfo.sendDepart == null || this.scmBSendinfo.sendDepart == '') {
        this.$message.warning('请在下列列表里选择送达科室.')
        return false
      }
      this.form.setFieldsValue({ matnr2: '2' })//防止显示提示错误
      this.form.setFieldsValue({ sendDepart2: '2' })//防止显示提示错误
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBSendinfo = this.form.getFieldsValue()
          scmBSendinfo.id = this.scmBSendinfo.id
          scmBSendinfo.matnr = this.scmBSendinfo.matnr
          scmBSendinfo.sendDepart = this.scmBSendinfo.sendDepart
          this.$put('scmBSendinfo', {
            ...scmBSendinfo
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
