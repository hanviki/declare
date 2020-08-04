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
              :precision="2"
              @blur="mengeBlur"
              style="width: 100%"
              placeholder="请输入供应数量"
              v-decorator="['gMenge',  { rules: [{ required: true, message: '供应数量不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="批号"
          >
            <a-select
              mode="single"
              :allowClear="true"
              style="width: 100%"
              v-decorator="[
          'charge',
          { rules: [{ required: true, message: '请选择批号' }] }
        ]"
            >
              <a-select-option
                v-for="r in chargeData"
                :key="r"
              >{{
          r
        }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="有效期"
          >
            <a-date-picker v-decorator="[ 'vfdat', { rules: [{ required: true, message: '有效期不能为空' }] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="生产日期"
          >
            <a-date-picker v-decorator="[ 'hsdat', { rules: [{ required: true, message: '生产日期不能为空' }] }]" />
          </a-form-item>
        </a-col>
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
              placeholder="请输入发票金额"
              v-decorator="['fpjr', { rules: [{ required: true, message: '发票金额不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="发票日期"
          >
            <a-date-picker v-decorator="[ 'fprq', { rules: [{ required: true, message: '发票日期不能为空' }] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="发票编码"
          >
            <a-input
              @blur="settingNumber"
              placeholder="请输入发票编码"
              v-decorator="['fpbm', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="包装规格"
          >
            <a-input-number
              :precision="2"
              style="width: 100%"
              @blur="pkgNumberBlur"
              placeholder="请输入包装规格"
              v-decorator="['pkgAmount', { rules: [{ required: true, message: '包装规格不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="包装数量"
          >
            <a-input-number
              :precision="2"
              placeholder="请输入包装数量"
              style="width: 100%"
              v-decorator="['pkgNumber',{ rules: [{ required: true, message: '包装数量不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="缺货原因"
          >
            <a-select
              mode="single"
              :allowClear="true"
              style="width: 100%"
              v-decorator="[
          'outCause',
          { rules: [{ message: '请输入缺货原因' },{
            validator: this.handleValidator
          }] }
        ]"
            >
              <a-select-option value="库存不足">库存不足</a-select-option>
              <a-select-option value="厂家停产">厂家停产</a-select-option>
              <a-select-option value="补足上批交货">补足上批交货</a-select-option>
              <a-select-option value="批次拆分">批次拆分</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="补货日期"
          >
            <a-date-picker v-decorator="[ 'outDate',  { rules: [{ required: false, message: '请输入补货日期' },{
            validator: this.handleValidator2
          }]}]" />
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
  name: 'ScmBSupplyplanAdd',
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
    },
    pRecord: {

    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBSupplyplan: {
      },
      chargeData: []
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
    settingNumber () {
      var Fpbm = this.form.getFieldValue('fpbm')
      if (Fpbm.trim().length > 0) {
        var arr = Fpbm.trim().split(',')
        for (var i = 0; i < arr.length; i++) {
          if (i === 3) {
            this.form.setFields({ fphm: { value: arr[i] } })
          }
          if (i === 4) {
            //  var jr=parseFloat(arr[i]);
            //  var fpjr=jr*1.17;
            //  $("#FPJR").numberbox("setValue",fpjr.toFixed(2));

          }
          if (i === 5) {
            
            var year = arr[i].substr(0, 4)
            var month = arr[i].substr(4, 2)
            var day = arr[i].substr(6, 2)
            //console.info(year + "-" + month + "-" + day);
            this.form.setFields({ fprq: {value: moment(year + "-" + month + "-" + day) }})
          }
        }
      }
    },
    mengeBlur (e) {
      if (e.target.value) {
        let money = (this.price * e.target.value).toFixed(2)
        this.form.setFields({ fpjr: { value: money } })
        let pkg = this.form.getFieldValue('pkgAmount')
        if (pkg) {
          let num = Math.ceil(e.target.value / pkg)
          this.form.setFields({ pkgNumber: { value: num } })
        }
      }

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
    pkgNumberBlur (e) {
      if (e.target.value) {
        let gMenge = this.form.getFieldValue('gMenge')
        if (gMenge) {
          let num = Math.ceil(gMenge / e.target.value)
          this.form.setFields({ pkgNumber: { value: num } })
        }
      }
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          this.setFields()
          this.scmBSupplyplan.baseId = this.baseId
          this.scmBSupplyplan.status = 0
          this.scmBSupplyplan.bsartD = 0 // 订单类型
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
    handleValidator (rule, val, callback) {
      let validateResult = false;  // 自定义规则
      let gMenge = this.form.getFieldValue('gMenge')
      let menge = this.form.getFieldValue('menge')
      let outCause = this.form.getFieldValue('outCause')
      if (gMenge < menge && outCause == null) {
        callback('请输入填写缺货原因！');
      }
      callback();
    },
    handleValidator2 (rule, val, callback) {
      let validateResult = false;  // 自定义规则
      let gMenge = this.form.getFieldValue('gMenge')
      let menge = this.form.getFieldValue('menge')
      let outDate = this.form.getFieldValue('outDate')

      if (!typeof (outDate) === 'undefined') {
        this.form.setFields({ outDate: { value: moment(outDate) } })
      }

      if (gMenge < menge && typeof (outDate) === 'undefined') {
        callback('请输入补货日期！');
      }
      callback();
    },
    setFields () {
      let values = this.form.getFieldsValue(['gMenge', 'charge', 'vfdat', 'hsdat', 'fphm', 'fpjr', 'fprq', 'fpbm', 'pkgAmount', 'pkgNumber', 'outCause', 'outDate'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBSupplyplan[_key] = values[_key] })
      }
    }
  },
  watch: {
    addVisiable: {
      handler: function () {
        if (this.addVisiable) {
          this.$get("scmBGysMaterPic/charge/" + this.baseId).then(r => {
            this.chargeData = r.data
          })
        }
      }
    }
  }
}
</script>
