<template>
  <a-card title="基本资料">
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="姓名"
      >
        <a-input
          placeholder="请输入姓名"
          v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="人事编号"
      >
        <a-input
          placeholder="请输入人事编号"
          :disabled="true"
          v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="所在院系"
      >
        <a-input
          placeholder="请输入所在院系"
          v-decorator="['deptName', {rules: [{ required: true, message: '所在院系不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="科室"
      >
        <a-input
          placeholder="请输入科室"
          v-decorator="['ks', {rules: [{ required: true, message: '科室不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系电话"
      >
        <a-input
          placeholder="请输入联系电话"
          v-decorator="['telephone', {rules: [{ required: true, message: '联系电话不能为空' }] }]"
        />
      </a-form-item>
     
     
      <a-form-item
        v-bind="formItemLayout"
        label="性别"
      >
        <a-input
          placeholder="请输入性别"
          v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="出生年月"
      >
        <a-date-picker v-decorator="[ 'birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="来校工作时间"
      >
        <a-date-picker v-decorator="[ 'schoolDate', {rules: [{ required: true, message: '来校工作时间不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现专业技术岗位（教学）"
      >
        <a-input
          :disabled="true"
          placeholder="请输入现专业技术岗位（教学）"
          v-decorator="['zyjsgw', {rules: [] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现专业技术岗位附件（教学）"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileList"
          :remove="handleRemove"
          :beforeUpload="beforeUpload"
          @change="handleChange"
        >
          <a-button v-if="fileList.length === 0">
            <a-icon type="upload"  /> 选择文件
          </a-button>
        </a-upload>
        <!-- <a-button
          type="primary"
          @click="handleUpload"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button> -->
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="聘任时间（教学）"
      >
        <a-date-picker :disabled="true" v-decorator="['appointedDate', {}]"  @change="handleChangeP"/>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现专业技术岗位（临床）"
      >
        <a-input
          :disabled="true"
          placeholder="请输入现专业技术岗位（临床）"
          v-decorator="['zyjsgwLc', {rules: [] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现专业技术岗位附件（临床）"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileList2"
          :remove="handleRemove2"
          :beforeUpload="beforeUpload2"
          @change="handleChange2"
        >
          <a-button v-if="fileList2.length === 0">
            <a-icon type="upload"  /> 选择文件
          </a-button>
        </a-upload>
        <!-- <a-button
          type="primary"
          @click="handleUpload"
          :disabled="fileList.length === 0 ||isShow===0"
          :loading="uploading"
          style="margin-top: 16px"
        >
          {{uploading ? '上传中' : '开始上传' }}
        </a-button> -->
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="聘任时间（临床）"
      >
        <a-date-picker :disabled="true" v-decorator="['appointedDateLc', {}]" @change="handleChangeLc" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="现从事专业及专长"
      >
        <a-input
          placeholder="请输入现从事专业及专长"
          v-decorator="['xcszyjzc', {rules: [{ required: true, message: '现从事专业及专长不能为空' }] }]"
        />
      </a-form-item>
     
    </a-form>

    <a-button
      @click="handleSubmit"
      type="primary"
      :loading="loading"
    >保存</a-button>
  </a-card>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 }
}
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      dcaBUser: {
        fileId: '',
        appointedDate: '',
        appointedDateLc: ''
      },
      isShow: 1,
      fileList: [],
      fileList2: [],
      uploading: false,
      uploading2: false,
    }
  },
  components: { TableUploadFile },
  mounted () {
    this.fetch()
    this.form.setFields({ deptName: { value: '华中科技大学同济医学院附属协和医院' } })
  },
  methods: {
    moment,
    setFields () {
      let values = this.form.getFieldsValue(['userAccountName', 'userAccount', 'deptName',  'sexName', 'birthday', 'schoolDate', 'zyjsgw', 'xcszyjzc', 'appointedDate', 'patentRanknum', 'appointedDateLc', 'zyjsgwLc','ks','telephone'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.dcaBUser[_key] = values[_key] })
      }
    },
    setFormValues ({ ...dcaBUser }) {
      let fields = ['userAccountName', 'userAccount', 'deptName',  'sexName', 'birthday', 'schoolDate', 'zyjsgw', 'xcszyjzc', 'appointedDate', 'appointedDateLc', 'zyjsgwLc','ks','telephone']
      let fieldDates = ['birthday', 'schoolDate', 'appointedDate', 'auditDate', 'appointedDateLc']
      Object.keys(dcaBUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBUser[key] !== '' && dcaBUser[key] !== null) {
              obj[key] = moment(dcaBUser[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBUser[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBUser.id = dcaBUser.id
      if (dcaBUser.fileId) {
        if (dcaBUser.fileId !== '') {
          this.dcaBUser.fileId = dcaBUser.fileId
          this.isShow = 0
          this.fileList = []
          this.$get('comFile/' + dcaBUser.fileId).then((r) => {
            let data = r.data
            //console.log(data)
            this.fileList.push(data)
          })
        }
      }
      if (dcaBUser.fileIdLc) {
        if (dcaBUser.fileIdLc !== '') {
          this.dcaBUser.fileIdLc = dcaBUser.fileIdLc
          this.isShow2 = 0
          this.fileList2 = []
          this.$get('comFile/' + dcaBUser.fileIdLc).then((r) => {
            let data = r.data
            //console.log(data)
            this.fileList2.push(data)
          })
        }
      }
    },
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    handleChangeLc (value,dateStr) {
      //this.dcaBUser.appointedDateLc = dateStr
      this.form.getFieldDecorator('appointedDateLc')
      this.form.setFieldsValue({
         appointedDateLc: ''
      })
    },
    handleChangeP (value,dateStr) {
     
      this.form.getFieldDecorator('appointedDate')
      this.form.setFieldsValue({
         appointedDate: ''
      })
    },
    handleRemove (file) {
      this.dcaBUser.fileId = ''
      this.fileList = []
      this.isShow = 1
    },
    beforeUpload (file) {
      const isJPG = (file.type === 'application/pdf')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isLt2M) {
        this.$message.error('附件必须小于 10MB!')
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
        this.dcaBUser.fileId = r.data.data.uid
        this.fileList = []
        this.fileList.push(r.data.data)
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
      // this.fileList[0].status = 'done'
    },
     handleChange2 (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload2()
      }
    },
    handleRemove2 (file) {
      this.dcaBUser.fileIdLc = ''
      this.fileList2 = []
      this.isShow2 = 1
    },
    beforeUpload2 (file) {
      const isJPG = (file.type === 'application/pdf')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isLt2M) {
        this.$message.error('附件必须小于 10MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList2 = [...this.fileList2, file]
      }
      return isJPG && isLt2M
    },
    handleUpload2 () {
      const { fileList2 } = this
      const formData = new FormData()
      formData.append('file', fileList2[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.dcaBUser.fileIdLc = r.data.data.uid
        this.fileList2 = []
        this.fileList2.push(r.data.data)
        this.isShow2 = 0
        this.uploading = false
        this.$message.success('上传成功.')
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
      // this.fileList[0].status = 'done'
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBUser = this.form.getFieldsValue()
          dcaBUser.id = this.dcaBUser.id
          dcaBUser.fileId = this.dcaBUser.fileId
          dcaBUser.fileIdLc = this.dcaBUser.fileIdLc
         
         // dcaBUser.appointedDate = (this.dcaBUser.appointedDate==''?null:this.dcaBUser.appointedDate)
         // dcaBUser.appointedDateLC = (this.dcaBUser.appointedDateLc==''?null:this.dcaBUser.appointedDateLc)
         if(dcaBUser.appointedDate ==''){
           dcaBUser.appointedDate= null
         }
          if(dcaBUser.appointedDateLc ==''){
           dcaBUser.appointedDateLc= null
         }

          this.$put('dcaBUser', {
            ...dcaBUser
          }).then(() => {
            this.$message.success('保存成功')
            this.fetch()
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    fetch () {
      this.$get('dcaBUser/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows
        if (data.rows.length > 0
        ) {
          this.setFormValues(data.rows[0])
        }
      }
      )
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
