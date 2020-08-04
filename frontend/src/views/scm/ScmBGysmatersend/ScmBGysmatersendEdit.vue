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
        label="药品名称"
      >
        <a-input
          placeholder="请输入药品名称"
          v-decorator="['txz01', {rules: [{ required: true, message: '药品名称不能为空' }, {max: 100, message: '药品名称不能超100个字符'}]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="药品规格"
      >
        <a-input
          placeholder="请输入药品规格"
          v-decorator="['spec', {rules: [{ required: true, message: '药品规格不能为空' }]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="生产厂家"
      >
        <a-input
          placeholder="请输入生产厂家"
          v-decorator="['produceArea', {rules: [{ required: true, message: '生产厂家不能为空' }, {max: 80, message: '生产厂家不能超80个字符'}]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="配送开始日期"
      >
        <a-date-picker
          v-decorator="[
          'sendStartTime',
          { rules: [{ required:true , message: '请输入配送开始日期' }] },
        ]"
          placeholder="请输入配送开始日期"
          @change="onChange"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="配送结束日期"
      >
        <a-date-picker
          v-decorator="[
          'sendEndTime',
          { rules: [{ required:true , message: '请输入配送结束日期' }] },
        ]"
          placeholder="请输入配送结束日期"
          @change="onChange"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="配送委托函"
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
      <a-form-item
        v-bind="formItemLayout"
        label="两票制承诺书"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileList2"
          :remove="handleRemove2"
          :beforeUpload="beforeUpload2"
        >
          <a-button>
            <a-icon type="upload" /> 选择文件 </a-button>
        </a-upload>
        <a-button
          type="primary"
          @click="handleUpload2"
          :disabled="fileList2.length === 0 ||isShow2===0"
          :loading="uploading2"
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
        v-show="isShowsub && (state==0 || state==4)"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 16 }
}
export default {
  name: 'ScmBGysMatersendEdit',
  props: {
    editVisiable: {
      default: false
    },
    isShowsub: {
      default: true
    }
  },
  data () {
    return {
      isShow: 1,
      isShow2: 1,
      state: 0,
      fileList: [],
      fileList2: [],
      uploading: false,
      uploading2: false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBGysmatersend: {
        fileId: '',
        materId: ''
      }
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.fileList = []
      this.fileList2 = []
      this.scmBGysmatersend.materId = ''
      this.scmBGysmatersend.fileId = ''
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({ ...scmBGysmatersend }) {
      let fields = ['sendStartTime', 'sendEndTime', 'txz01', 'spec', 'produceArea']
      let fieldDates = ['sendStartTime', 'sendEndTime']
      Object.keys(scmBGysmatersend).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBGysmatersend[key] !== '') {
              obj[key] = moment(scmBGysmatersend[key])
            }
          } else {
            obj[key] = scmBGysmatersend[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBGysmatersend.id = scmBGysmatersend.id
      this.scmBGysmatersend.materId = scmBGysmatersend.materId
      this.state = scmBGysmatersend.state
      // console.info(this.$refs.upfc)
      if (scmBGysmatersend.fileId) {
        if (scmBGysmatersend.fileId !== '') {
          this.scmBGysmatersend.fileId = scmBGysmatersend.fileId
          this.isShow = 0
          this.fileList = []
          this.$get('comFile/' + scmBGysmatersend.fileId).then((r) => {
            let data = r.data
            this.fileList.push({
              uid: data.id,
              name: data.clientName,
              status: 'done',
              url: this.$baseUrl + 'uploadFile/' + data.serverName
            })
          })
        }
      }
      if (scmBGysmatersend.materId) {
        if (scmBGysmatersend.materId !== '') {
          this.scmBGysmatersend.materId = scmBGysmatersend.materId
          this.isShow2 = 0
          this.fileList2 = []
          this.$get('comFile/' + scmBGysmatersend.materId).then((r) => {
            let data = r.data
            this.fileList2.push({
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
      this.scmBGysmatersend.fileId = ''
      this.fileList = []
      this.isShow = 1
    },
    handleRemove2 (file) {
      this.scmBGysmatersend.materId = ''
      this.fileList2 = []
      this.isShow2 = 1
    },
    onChange (date, dateString) {
      console.log(date, dateString)
    },
    beforeUpload (file) {
      const isJPG = file.type === 'application/pdf'
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('附件必须小于 2MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList = [...this.fileList, file]
      }
      return isJPG && isLt2M
    },
    beforeUpload2 (file) {
      const isJPG = file.type === 'application/pdf'
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 2
      if (!isLt2M) {
        this.$message.error('附件必须小于 2MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList2 = [...this.fileList2, file]
      }
      return isJPG && isLt2M
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.scmBGysmatersend.fileId = r.data.data
        // this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
      this.fileList[0].status = 'done'
    },
    handleUpload2 () {
      const { fileList2 } = this
      const formData = new FormData()
      formData.append('file', fileList2[0])
      this.uploading2 = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.scmBGysmatersend.materId = r.data.data
        // this.fileList = []
        this.isShow2 = 0
        this.uploading2 = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading2 = false
        this.$message.error('上传失败.')
      })
      this.fileList2[0].status = 'done'
    },
    handleSubmit () {
      if (this.scmBGysmatersend.materId === '') {
        this.$message.warning('请上传两票制承诺书附件.')
        return false
      }
      if (this.scmBGysmatersend.fileId === '') {
        this.$message.warning('请上传委托书附件.')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBGysmatersend = this.form.getFieldsValue()
          scmBGysmatersend.id = this.scmBGysmatersend.id
          scmBGysmatersend.materId = this.scmBGysmatersend.materId
          scmBGysmatersend.fileId = this.scmBGysmatersend.fileId
          this.$put('scmBGysmatersend', {
            ...scmBGysmatersend
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
