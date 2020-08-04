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
        label="文件上传"
      >
        <a-upload
          :fileList="fileList"
          :remove="handleRemove"
          :beforeUpload="beforeUpload"
        >
          <a-button>
            <a-icon type="upload" /> 选择文件 </a-button>
        </a-upload>
        <a-button
          type="primary"
          @click="handleUpload"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button>
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
  name: 'ScmBReportEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  data () {
    return {
      isShow: 1,
      fileList: [],
      uploading:false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBReport: {
        fileId: ''
      }
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

      if (scmBReport.fileId) {
        if (scmBReport.fileId != '') {
          this.scmBReport.fileId = scmBReport.fileId
          this.isShow = 0
          this.$get('comFile/' + scmBReport.fileId).then((r) => {
            let data = r.data
            console.info(data)
            this.fileList.push({
              uid: data.id,
              name: data.clientName,
              status: 'done',
              url: this.$baseUrl + 'uploadFile/' + data.serverName
            })
          })
        }
      }
    },
    handleRemove (file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.isShow = 1
    },
    onChange (date, dateString) {
      console.log(date, dateString);
    },
    beforeUpload (file) {
      this.fileList = [...this.fileList, file]
      return false
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.scmBReport.fileId = r.data.data
        //this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBReport = this.form.getFieldsValue()
          scmBReport.id = this.scmBReport.id
          scmBReport.fileId = this.scmBReport.fileId
          this.$put('scmBReport', {
            ...scmBReport
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
