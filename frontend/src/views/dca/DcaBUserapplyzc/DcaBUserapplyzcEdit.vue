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
        label="申报年度"
      >
        <a-select
          style="width: 200px"
          @change="handleChange"
          v-decorator="[
          'dcaYear',
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
        label="申请测试等级"
      >
        <a-select
          show-search
          style="width: 200px"
          @change="handleChangezw"
          v-decorator="[
          'gwdj',
          { rules: [{ required: true, message: '请输入申请测试等级' }] }
        ]"
        >

          <a-select-option key="中级">
            中级
          </a-select-option>
          <a-select-option key="初级">
            初级
          </a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="论文"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileList"
          :remove="handleRemove"
          :beforeUpload="beforeUpload"
          @change="handleChangefile"
        >
          <a-button v-if="fileList.length === 0||isShow===0">
            <a-icon type="upload" /> 选择文件
          </a-button>
        </a-upload>

      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="住院医师规范化培训证书（或文件）"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileList2"
          :remove="handleRemove2"
          :beforeUpload="beforeUpload2"
          @change="handleChangefile2"
        >
          <a-button v-if="fileList2.length === 0||isShow2===0">
            <a-icon type="upload" /> 选择文件
          </a-button>
        </a-upload>
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="是否补考"
      >
        <a-checkbox
          placeholder="请输入是否补考"
          v-decorator="['isBukao', {valuePropName:'checked'}]"
        ></a-checkbox>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="补考科目"
      >
        <a-input
          placeholder="请输入补考科目"
          v-decorator="['bukaokemu', {}]"
        />
      </a-form-item>
     
      <a-form-item
        v-bind="formItemLayout"
        label="手机号码"
      >
        <a-input
          placeholder="请输入手机号码"
          v-decorator="['telephone', {rules: [{ required: true, message: '手机号码不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否通过初级水平能力测试"
      >
        {{isChujikh}}
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="通过初级水平能力测试时间"
      >
        {{chujikhDate}}
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否通过中级水平能力测试"
      >
        {{isZhongjikh}}
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="通过中级水平能力测试时间"
      >
        {{zhongjikhDate}}
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
  labelCol: { span: 9 },
  wrapperCol: { span: 14 }
}
export default {
  name: 'DcaBUserapplyzcEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      isShow: 1,
      isShow2: 1,
      fileList: [],
      fileList2: [],
      uploading: false,
      uploading2: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBUserapplyzc: {
        fileId: '',
        zcFileId: '',
        gwdj: '',
        dcaYear: ''
      },
      isChujikh: '',
      chujikhDate: '',
      isZhongjikh: '',
      zhongjikhDate: ''
    }
  },
  computed: {
    yearArr () {
      let arr = []
      var myDate = new Date()
      var startYear = myDate.getFullYear() - 1//起始年份
      /*var endYear = myDate.getFullYear() + 1//结束年份
      for (var i = startYear; i <= endYear; i++) {
        arr.push({ value: i, text: i })
      }*/
      arr.push({ value: startYear, text: startYear })
      return arr
    }
  },
  methods: {
    moment,
    reset () {
      this.loading = false
      this.isShow = 1
      this.isShow2 = 1
      this.fileList = []
      this.fileList2 = []
      this.dcaBUserapplyzc.zcFileId = ''
      this.dcaBUserapplyzc.fileId = ''
      this.dcaBUserapplyzc = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleChange (value) {
      this.dcaBUserapplyzc.dcaYear = value
    },
    handleChangezw (value) {
      this.dcaBUserapplyzc.gwdj = value
    },
    handleChangefile (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    handleChangefile2 (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload2()
      }
    },
    setFormValues ({ ...dcaBUserapplyzc }) {
      let fields = ['gwdj', 'dcaYear', 'isBukao', 'bukaokemu', 'telephone']
      let fieldDates = []
      Object.keys(dcaBUserapplyzc).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBUserapplyzc[key] !== '') {
              obj[key] = moment(dcaBUserapplyzc[key])
            }
            else {
              obj[key] = ''
            }
          } else {
              obj[key] = dcaBUserapplyzc[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBUserapplyzc.fileUrl = dcaBUserapplyzc.fileUrl
      this.dcaBUserapplyzc.fileId = dcaBUserapplyzc.fileId
      this.dcaBUserapplyzc.zcFileId = dcaBUserapplyzc.zcFileId
      this.dcaBUserapplyzc.zcFileUrl = dcaBUserapplyzc.zcFileUrl
      this.dcaBUserapplyzc.gwdj = dcaBUserapplyzc.gwdj
      this.dcaBUserapplyzc.dcaYear = dcaBUserapplyzc.dcaYear
      this.dcaBUserapplyzc.id = dcaBUserapplyzc.id
      this.isChujikh = dcaBUserapplyzc.isChujikh == null ? "" : (dcaBUserapplyzc.isChujikh ? '是' : '否')
      this.chujikhDate = dcaBUserapplyzc.chujikhDate == null ? "" : moment(dcaBUserapplyzc.chujikhDate).format('YYYY-MM-DD')
      this.isZhongjikh = dcaBUserapplyzc.isZhongjikh == null ? "" : (dcaBUserapplyzc.isZhongjikh ? '是' : '否')
      this.zhongjikhDate = dcaBUserapplyzc.zhongjikhDate == null ? "" : moment(dcaBUserapplyzc.zhongjikhDate).format('YYYY-MM-DD')
    },
    fetch (fileId) {
      this.fileList = []
      this.isShow = 0
      this.$get('comFile/' + fileId).then((r) => {
        let data = r.data
        //this.fileUrl = data.url
        this.fileList.push(data)

      })
    },
    fetch2 (fileId) {
      this.fileList2 = []
      this.isShow2 = 0
      this.$get('comFile/' + fileId).then((r) => {
        let data = r.data
        this.fileList2.push(data)

      })
    },
    handleRemove (file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.dcaBUserapplyzc.fileId = ''
      this.isShow = 1
    },
    handleRemove2 (file) {
      const index = this.fileList2.indexOf(file)
      const newFileList = this.fileList2.slice()
      newFileList.splice(index, 1)
      this.fileList2 = newFileList
      this.dcaBUserapplyzc.zcFileId = ''
      this.isShow2 = 1
    },
    beforeUpload (file) {
      const isJPG = (file.type === 'application/pdf')
      console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!isLt2M) {
        this.$message.error('附件必须小于 5MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList = [...this.fileList, file]
      }
      return isJPG && isLt2M;
    },
    beforeUpload2 (file) {
      const isJPG = (file.type === 'application/pdf')
      console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!isLt2M) {
        this.$message.error('附件必须小于 5MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList2 = [...this.fileList2, file]
      }
      return isJPG && isLt2M;
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        let comfile = r.data.data
        this.dcaBUserapplyzc.fileId = comfile.uid
        this.dcaBUserapplyzc.fileUrl = comfile.url
        this.fileList = []
        this.fileList.push(comfile)
        // this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    },
    handleUpload2 () {
      const { fileList2 } = this
      const formData = new FormData()
      formData.append('file', fileList2[0])
      this.uploading2 = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        //console.info('上传IF:' + r.data.data)
        let comfile = r.data.data
        this.dcaBUserapplyzc.zcFileId = comfile.uid
        this.dcaBUserapplyzc.zcFileUrl = comfile.url
        this.fileList2 = []
        this.fileList2.push(comfile)
        // this.fileList = []
        this.isShow2 = 0
        this.uploading2 = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading2 = false
        this.$message.error('上传失败.')
      })
    },
         setFields () {
      let values = this.form.getFieldsValue(['isBukao', 'bukaokemu', 'lilunbukao', 'telephone'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.dcaBUserapplyzc[_key] = values[_key] })
      }
    },
    handleSubmit () {
      /*if (this.dcaBUserapplyzc.fileId === '') {
        this.$message.warning('请上传论文附件.')
        return false
      }
      if (this.dcaBUserapplyzc.zcFileId === '') {
        this.$message.warning('请上传住院医师规范化培训证书附件.')
        return false
      }*/
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$put('dcaBUserapplyzc', {
            ...this.dcaBUserapplyzc
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
