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
        label="药品名称"
      >
        <a-input
          placeholder="请输入药品名称"
          v-decorator="['txz01', {rules: [{ required: true, message: '药品名称不能为空' }, {max: 30, message: '药品名称不能超30个字符'}]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="药品规格"
      >
        <a-input
          placeholder="请输入药品规格"
          v-decorator="['spec', {rules: [{ required: true, message: '药品规格不能为空' }, {max: 20, message: '药品规格不能超20个字符'}]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="生产厂家"
      >
        <a-input
          placeholder="请输入生产厂家"
          v-decorator="['produceArea', {rules: [{ required: true, message: '生产厂家不能为空' }, {max: 20, message: '生产厂家不能超30个字符'}]}]"
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
        <a-upload :fileList="fileList">
          <!--  <a-button>
       <a-icon type="upload" /> 选择文件 </a-button> -->
        </a-upload>
        <!--  <a-button
          type="primary"
          @click="handleUpload"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button>-->
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="两票制承诺书"
      >
        <a-upload :fileList="fileList2">
          <!--  <a-button>
       <a-icon type="upload" /> 选择文件 </a-button> -->
        </a-upload>
        <!--  <a-button
          type="primary"
          @click="handleUpload"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button>-->
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      <a-button
        v-show="state==0 || state == 4"
        v-hasPermission="['scmBGysmatersend:audit']"
        @click="audit(1)"
        style="margin-left: .8rem"
        type="primary"
      >审核通过</a-button>
      <a-button
        v-show="state==1"
        v-hasPermission="['scmBGysmatersend:audit']"
        @click="audit(2)"
        style="margin-left: .8rem"
        type="danger"
      >停止配送</a-button>
      <a-button
        v-show="state==2"
        v-hasPermission="['scmBGysmatersend:audit']"
        @click="audit(1)"
        style="margin-left: .8rem"
        type="primary"
      >恢复配送</a-button>
      <a-button
        v-show="state==0"
        v-hasPermission="['scmBGysmatersend:audit']"
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
  name: 'ScmBGysMatersendEdit',
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
      scmBGysmatersend: {
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
      this.scmBGysmatersend.materId = ''
      this.scmBGysmatersend.fileId = ''
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
      this.$post('scmBGysmatersend/auditAdmin', {
        id: this.scmBGysmatersend.id,
        state: 4,
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
          that.$post('scmBGysmatersend/auditAdmin', {
            id: that.scmBGysmatersend.id,
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
        this.scmBGysmatersend.fileId = r.data.data
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
      if (this.scmBGysmatersend.materId === '') {
        this.$message.warning('请在下拉列表里选择药品.')
        return false
      }
      if (this.scmBGysmatersend.fileId === '') {
        this.$message.warning('请上传资质附件.')
        return false
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBGysmatersend = this.form.getFieldsValue()
          scmBGysmatersend.id = this.scmBGysmatersend.id
          scmBGysmatersend.materId = this.$refs.upfc.materId
          scmBGysmatersend.matnr = this.$refs.upfc.matnr
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
