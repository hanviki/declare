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
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ScmBReportAdd',
  props: {
    addVisiable: {
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
      this.scmBReport = {}
      this.fileList=[]
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('scmBReport', {
            ...this.scmBReport
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
      let values = this.form.getFieldsValue(['name', 'comments'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBReport[_key] = values[_key] })
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
    }
  }
}
</script>
