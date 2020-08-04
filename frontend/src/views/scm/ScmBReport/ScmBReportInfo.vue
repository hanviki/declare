<template>
  <a-modal
    title="公告详情"
    :width="650"
    @cancel="onClose"
    :footer="null"
    v-model="infoVisiable"
  >
    <a-form :form="form">

      <a-form-item
        v-bind="formItemLayout"
        label="公告标题"
      >
        <a-input
          placeholder="请输入公告标题"
          v-decorator="['name', {rules: [{ required: true, message: '公告标题不能为空' }]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="公告内容"
      >
        <a-textarea
          :rows="4"
          placeholder="请输入公告内容"
          v-decorator="['comments', {rules: [{ required: true, message: '公告内容不能为空' }]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="附件"
      >
       <a :href="serverName">
        {{clientName}}
      </a>
      </a-form-item>
    </a-form>
  </a-modal>
</template>
<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ScmBReportInfo',
  props: {
    infoVisiable: {
      default: false
    }
  },
  data () {
    return {
      isShow: 1,
      fileList: [],
      uploading: false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBReport: {
        fileId: '',
       
      },
      serverName: '',
      clientName: ''
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.fileList=[]
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({ ...scmBReport }) {
      let fields = ['name', 'comments']
      let fieldDates = ['createTime', 'modifyTime']
      Object.keys(scmBReport).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBReport[key] !== '') {
              obj[key] = moment(scmBReport[key])
            }
          } else {
            obj[key] = scmBReport[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBReport.id = scmBReport.id
      this.fileList=[]
      if (scmBReport.fileId) {
        if (scmBReport.fileId != '') {
          this.scmBReport.fileId = scmBReport.fileId
          this.isShow = 0
          this.$get('comFile/' + scmBReport.fileId).then((r) => {
            let data = r.data
            console.info(data)
            this.clientName=data.clientName;
            this.serverName=this.$baseUrl + 'uploadFile/' + data.serverName
          })
        }
      }
    },
   
    onChange (date, dateString) {
      console.log(date, dateString);
    }
   
   
  }
}
</script>
