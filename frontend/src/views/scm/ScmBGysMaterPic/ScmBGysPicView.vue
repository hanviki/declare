<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :width="750"
    @cancel="handleCancleClick"
    title="资质文件"
  >
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="药品"
      >
        <up-fc ref="upfc">
        </up-fc>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="批次号"
      >
        <a-input
          placeholder="请输入批次号"
          v-decorator="['charge', {rules: [{ required: true, message: '批次号不能为空' }]}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="备注"
      >
        <a-input
          placeholder="请输入备注"
          v-decorator="['comments', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="文件上传"
      >
        <a-upload :fileList="fileList">
          <a-button>
            <a-icon type="upload" /> 选择文件 </a-button>
        </a-upload>
        <a-button
          type="primary"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button>
      </a-form-item>
    </a-form>
    <template slot="footer">
      <a-button
        key="back"
        @click="handleCancel"
      >关闭</a-button>
      <a-button
        key="submit"
        type="primary"
        :loading="loading"
        @click="handleOk"
        v-show="showAudit"
      >
        审核
      </a-button>
    </template>
    <audit-mater
      @success="handleAuditSuccess"
      @close="handleAuditClick"
      :auditVisiable="auditVisiable"
      :id="scmBGysMaterPic.id"
      url="scmBGysMaterPic/audit"
    >
    </audit-mater>
  </a-modal>
</template>
<script>
import auditMater from './AuditMater'
import upFc from '../../common/UpFileCustomer'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ScmBGysMaterPicView',
  components: { upFc, auditMater },
  props: {
    editVisiable: {
      require: true,
      default: false
    },
    isShowAudit: {
      require: true,
      default: true
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
      scmBGysMaterPic: {
        fileId: '',
        materId: '',
        state: 0
      },
      auditVisiable: false
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.$refs.upfc.reset()
      this.$refs.upfc.matnr = ''
      this.fileList = []
      this.scmBGysMaterPic.materId = ''
      this.scmBGysMaterPic.fileId = ''
      this.form.resetFields()
    },
    handleOk (e) {
      this.loading = true
      this.auditVisiable = true
    },
    handleAuditSuccess (e) {
      this.reset()
      this.auditVisiable = false
      this.$emit('success')
    },
    handleAuditClick (e) {
      this.loading = false
      this.auditVisiable = false
    },
    handleCancleClick (e) {
      this.$emit('close')
    },
    handleCancel (e) {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({ ...scmBGysMaterPic }) {
      let fields = ['charge', 'comments']
      let fieldDates = ['createTime', 'modifyTime']
      Object.keys(scmBGysMaterPic).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmBGysMaterPic[key] !== '') {
              obj[key] = moment(scmBGysMaterPic[key])
            }
          } else {
            obj[key] = scmBGysMaterPic[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBGysMaterPic.id = scmBGysMaterPic.id
      this.scmBGysMaterPic.materId = scmBGysMaterPic.materId
      //console.info(this.$refs.upfc)
      if (scmBGysMaterPic.fileId) {
        if (scmBGysMaterPic.fileId != '') {
          this.scmBGysMaterPic.fileId = scmBGysMaterPic.fileId
          this.isShow = 0
          this.fileList = []
          this.$get('comFile/' + scmBGysMaterPic.fileId).then((r) => {
            this.$refs.upfc.setFormValue(scmBGysMaterPic.matnr + '_' + scmBGysMaterPic.txz01, scmBGysMaterPic.materId)
            let data = r.data
            this.fileList.push({
              uid: data.id,
              name: data.clientName,
              status: 'done',
              url: this.$baseUrl + 'comFile/checkFile/' + data.serverName
            })
          })
        }
      }
    }
  },
  computed: {
    show: {
      get: function () {
        return this.editVisiable
      },
      set: function () {
      }
    },
    showAudit: {
      get: function () {
        return this.isShowAudit
      },
      set: function () {
      }
    }
  }
}
</script>
