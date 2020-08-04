<template>
  <a-drawer
    title="查看"
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
        label="发票号码"
      >
        <a-input
          placeholder="请输入发票号码"
          v-decorator="['fpHm', {rules: [{ required: true, message: '发票号码不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
            v-bind="formItemLayout"
            label="发票日期"
          >
            <a-date-picker v-decorator="[ 'fprq', { rules: [{ required: true, message: '发票日期不能为空' }] }]" />
     </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="发票金额"
      >
        <a-input-number
          :precision="2"
          placeholder="请输入发票金额"
          v-decorator="['fpAmount', {rules: [{ required: true, message: '发票金额不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="药厂发票"
      >
        <a-upload
          accept=".pdf"
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
        label="供应商发票"
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
          {{uploading2 ? '上传中' : '开始上传' }}
        </a-button>
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      <a-button
        v-show="state==0"
        v-hasPermission="['scmBGysfp:audit']"
        @click="audit(1)"
        style="margin-left: .8rem"
        type="primary"
      >审核通过</a-button>

      <a-button
        v-show="state==0"
        v-hasPermission="['scmBGysfp:audit']"
        @click="auditUndo"
        style="margin-left: .8rem"
        type="danger"
      >审核不通过</a-button>
      <a-button
        @click="onClose"
        style="margin-right: .8rem"
      >关闭</a-button>
    </div>
    <a-modal
      title="审核不成功"
      :visible="unAuditvisible"
      :confirm-loading="loading"
      @ok="handleAuditOk"
      @cancel="handleAuditCancel"
    >
      <a-textarea
        placeholder="请输入审核不成功原因"
        v-model="auditCause"
      />
    </a-modal>
  </a-drawer>
</template>
<script>
import upFc from '../../common/UpFileCustomer'
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 16 }
}
export default {
  name: 'scmBGysfp',
  components: { upFc },
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
      fileList: [],
      isShow2: 1,
      fileList2: [],
      uploading: false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBGysfp: {
        fileId: '',
        materId: ''
      },
      state: 0,
      unAuditvisible: false,
      auditCause: ''
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.fileList = []
      this.fileList2 = []
      this.scmBGysfp.materId = ''
      this.scmBGysfp.fileId = ''
      this.form.resetFields()
    },
    handleAuditCancel () {
      this.auditCause = ''
      this.unAuditvisible = false
    },
    auditUndo () {
      this.auditCause = ''
      this.unAuditvisible = true
    },
    handleAuditOk () {
      this.$post('scmBGysfp/auditAdmin', {
        id: this.scmBGysfp.id,
        state: 2,
        AuditCause: this.auditCause
      }).then(() => {
        this.$message.success('操作成功')
        this.auditCause = ''
        this.unAuditvisible = false
        this.onSucc()
      })
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    onSucc () {
      this.reset()
      this.$emit('succ')
    },
    audit (flag) {
      let that = this
      this.$confirm({
        title: '确定审核所选中的记录?',
        content: '当您点击确定按钮后，操作会被执行',
        centered: true,
        onOk () {
          that.$post('scmBGysfp/auditAdmin', {
            id: that.scmBGysfp.id,
            state: flag,
            AuditCause: ' '
          }).then(() => {
            that.$message.success('操作成功')
            that.onSucc()
          })
        },
        onCancel () {

        }
      })
    },
    setFormValues ({ ...scmBGysfp }) {
      let fields = ['fpHm', 'fpBm', 'fpAmount', 'fprq']
      let fieldDates = ['fprq']
      Object.keys(scmBGysfp).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBGysfp[key] !== '') {
              obj[key] = moment(scmBGysfp[key])
            }
          } else {
            obj[key] = scmBGysfp[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBGysfp.id = scmBGysfp.id
      this.scmBGysfp.materId = scmBGysfp.materId
      this.state = scmBGysfp.state
      // console.info(this.$refs.upfc)
      if (scmBGysfp.fileId) {
        if (scmBGysfp.fileId !== '') {
          this.scmBGysfp.fileId = scmBGysfp.fileId
          this.isShow = 0
          this.fileList = []
          this.$get('comFile/' + scmBGysfp.fileId).then((r) => {
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
      if (scmBGysfp.materId) {
        if (scmBGysfp.materId !== '') {
          this.scmBGysfp.materId = scmBGysfp.materId
          this.isShow2 = 0
          this.fileList2 = []
          this.$get('comFile/' + scmBGysfp.materId).then((r) => {
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
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.isShow = 1
    },
    onChange (date, dateString) {
      console.log(date, dateString)
    },
    beforeUpload (file) {
      const isJPG = file.type === 'pdf'
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
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.scmBGysfp.fileId = r.data.data
        // this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    },
    handleSubmit () {
      if (this.scmBGysfp.materId === '') {
        this.$message.warning('请在下拉列表里选择药品.')
        return false
      }
      if (this.scmBGysfp.fileId === '') {
        this.$message.warning('请上传资质附件.')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBGysfp = this.form.getFieldsValue()
          scmBGysfp.id = this.scmBGysfp.id
          scmBGysfp.materId = this.$refs.upfc.materId
          scmBGysfp.matnr = this.$refs.upfc.matnr
          scmBGysfp.fileId = this.scmBGysfp.fileId
          this.$put('scmBGysfp', {
            ...scmBGysfp
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
