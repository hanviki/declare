<template>
  <a-drawer
    title="职称申报"
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
      label="申报年度"
    >
      <a-select
        style="width: 200px"
        @change="handleChange"
         v-decorator="[
          'hk3',
          { rules: [{ required: true, message: '请输入申报年度' }] }
        ]"
      >
        <a-select-option
          v-for="d in yearArr"
          :key="d.value"
        >
          {{ d.text }}
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="岗位等级"
    >
      <a-select
        show-search
        style="width: 200px"
        @change="handleChangezw"
        v-decorator="[
          'hk2',
          { rules: [{ required: true, message: '请输入岗位等级' }] }
        ]"
      >
        <a-select-option
         key="正高"
        >
          正高
        </a-select-option>
         <a-select-option
         key="副高"
        >
          副高
        </a-select-option>
      </a-select>
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="申报职称"
    >
      <a-select
        show-search
        style="width: 200px"
        @change="handleChangezc"
         v-decorator="[
          'hk4',
          { rules: [{ required: true, message: '请输入申报职称' }] }
        ]"
      >
        <a-select-option
          v-for="d in zc"
          :key="d.value"
        >
          {{ d.text }}
        </a-select-option>
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
  wrapperCol: { span: 15 }
}
export default {
  name: 'DcaBUserapplyAdd',
  props: {
    addVisiable: {
      default: false
    }
  },
  data () {
    return {
      current: {
        default: 0
      },
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBUserapply: {},
      dcaYear: '',
      npPositionName: '',
      gwdj: '',
      zc: [],
      zg: [{
        value: '教授主任医师',
        text: '教授主任医师'
      },{
        value: '教授',
        text: '教授'
      },{
        value: '主任医师',
        text: '主任医师'
      },{
        value: '研究员',
        text: '研究员'
      },{
        value: '主任护师',
        text: '主任护师'
      },{
        value: '主任技师',
        text: '主任技师'
      },{
        value: '主任药师',
        text: '主任药师'
      },{
        value: '教授级高级工程师',
        text: '教授级高级工程师'
      },{
        value: '编审',
        text: '编审'
      }],
      fg: [{
        value: '副教授副主任医师',
        text: '副教授副主任医师'
      },{
        value: '副教授',
        text: '副教授'
      },{
        value: '副主任医师',
        text: '副主任医师'
      },{
        value: '副研究员',
        text: '副研究员'
      },{
        value: '副主任护师',
        text: '副主任护师'
      },{
        value: '副主任技师',
        text: '副主任技师'
      },{
        value: '副主任药师',
        text: '副主任药师'
      },{
        value: '高级工程师',
        text: '高级工程师'
      },{
        value: '副编审',
        text: '副编审'
      }]
    }
  },
  computed: {
    yearArr () {
      let arr = []
      var myDate = new Date()
      var startYear = myDate.getFullYear() - 2//起始年份
      var endYear = myDate.getFullYear() + 1//结束年份
      for (var i = startYear; i <= endYear; i++) {
        arr.push({ value: i, text: i })
      }
      return arr
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.dcaBUserapply = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleChange (value) {
      this.dcaYear = value
    },
    handleChangezc (value) {
      this.npPositionName = value
    },
    handleChangezw (value) {
       if(value =='正高'){
         this.zc =this.zg
       }
        if(value =='副高'){
         this.zc =this.fg
       }
       this.gwdj= value
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.$post('dcaBUserapply', {
            dcaYear: this.dcaYear,
            gwdj: this.gwdj,
            npPositionName: this.npPositionName
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
      let values = this.form.getFieldsValue(['ks', 'xl', 'sexName', 'birthday', 'telephone', 'zyjsgw', 'appointedDate', 'npPositionName', 'gwdj', 'deptName', 'positionName', 'schoolDate', 'xcszyjzc', 'dcaYear'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.dcaBUserapply[_key] = values[_key] })
      }
    }
  }
}
</script>
