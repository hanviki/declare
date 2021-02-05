<template>
  <a-card title="基本情况及博士后跟踪">
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="姓名"
      >
        <a-input
          placeholder="请输入姓名"
          :disabled="true"
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
        label="部门描述"
      >
        <a-input
          placeholder="请输入部门描述"
          :disabled="true"
          v-decorator="['deptDesc', {rules: [{ required: true, message: '部门描述不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="员工工号"
      >
        <a-input
          placeholder="请输入员工工号"
          :disabled="true"
          v-decorator="['yggh', {rules: [{ required: true, message: '员工工号不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="华科人事编号"
      >
        <a-input
          placeholder="请输入华科人事编号"
          :disabled="true"
          v-decorator="['hkrsbh', {rules: [{ required: true, message: '华科人事编号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="性别"
      >
        <a-input
          placeholder="请输入性别"
          :disabled="true"
          v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="出生年月"
      >
        <a-date-picker 
        :disabled="true"
        v-decorator="[ 'birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="员工子组"
      >
        <a-input
          placeholder="请输入员工子组"
          :disabled="true"
          v-decorator="['yuangongzu', {rules: [{ required: true, message: '员工子组不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="招收类型"
      >
        <a-input
          placeholder="请输入招收类型"
          :disabled="true"
          v-decorator="['zslx', {rules: [{ required: true, message: '招收类型不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="流动站"
      >
        <a-input
          placeholder="请输入流动站"
          :disabled="true"
          v-decorator="['ldz', {rules: [{ required: true, message: '流动站不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="身份"
      >
        <a-input
          placeholder="请输入身份"
          :disabled="true"
          v-decorator="['shenfen', {rules: [{ required: true, message: '身份不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="身份证号"
      >
        <a-input
          placeholder="请输入身份证号"
          :disabled="true"
          v-decorator="['idCard', {rules: [{ required: true, message: '身份证号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="学历"
      >
        <a-input
          placeholder="请输入学历"
          :disabled="true"
          v-decorator="['xueli', {rules: [{ required: true, message: '学历不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="博士导师"
      >
        <a-input
          placeholder="请输入博士导师"
          :disabled="true"
          v-decorator="['boshidaoshi', {rules: [{ required: true, message: '博士导师不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="合作导师"
      >
        <a-input
          placeholder="请输入合作导师"
          :disabled="true"
          v-decorator="['hezuodaoshi', {rules: [{ required: true, message: '合作导师不能为空' }] }]"
        />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="毕业时间"
      >
        <a-date-picker 
        :disabled="true"
        v-decorator="[ 'biyeDate', {rules: [{ required: true, message: '毕业时间不能为空' }] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="毕业院校"
      >
        <a-input
          placeholder="请输入毕业院校"
          :disabled="true"
          v-decorator="['biyexuexiao', {rules: [{ required: true, message: '毕业院校不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="专业"
      >
        <a-input
          placeholder="请输入专业"
          :disabled="true"
          v-decorator="['zhuanye', {rules: [{ required: true, message: '专业不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="来院工作时间"
      >
        <a-date-picker 
        :disabled="true"
        v-decorator="[ 'schoolDate', {rules: [{ required: true, message: '来院工作时间不能为空' }] }]" />
      </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="进站日期"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD'
          :disabled="true"
          v-decorator="[ 'inDate', {rules: [{ required: true, message: '进站日期不能为空' }] }]"
        />
      </a-form-item>
          <a-form-item
        v-bind="formItemLayout"
        label="延期时间"
      >
        <a-date-picker
        :disabled="true"
         v-decorator="[ 'yqDate', {rules: [] }]" />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="聘期"
      >
        <a-input
          placeholder="请输入聘期"
          :disabled="true"
          v-decorator="['pinqi', {rules: [{ required: true, message: '聘期不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="第几个聘期"
      >
        <a-input
          placeholder="请输入第几个聘期"
          :disabled="true"
          v-decorator="['pinqiRanknum', {rules: [{ required: true, message: '第几个聘期不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="站内工作目标"
      >
        <a-textarea
          placeholder="请输入站内工作目标"
          :disabled="true"
          v-decorator="['goal', {rules: [{ required: true, message: '站内工作目标不能为空' }] }]"
        >
        </a-textarea>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="手机号"
      >
        <a-input
          placeholder="请输入手机号"

          v-decorator="['telephone', {rules: [{ required: true, message: '手机号不能为空' }] }]"
        />
      </a-form-item>
  
      <a-form-item
        v-bind="formItemLayout"
        label="国籍"
      >
        <a-input
          placeholder="请输入国籍"
          :disabled="true"
          v-decorator="['country', {rules: [{ required: true, message: '国籍不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="籍贯"
      >
        <a-input
          placeholder="请输入籍贯"
          :disabled="true"
          v-decorator="['jiguan', {rules: [{ required: true, message: '籍贯不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="民族"
      >
        <a-input
          placeholder="请输入民族"
          :disabled="true"
          v-decorator="['nation', {rules: [{ required: true, message: '民族不能为空' }] }]"
        />
      </a-form-item>
     
      
      <a-form-item
        v-bind="formItemLayout"
        label="政治面貌"
      >
        <a-input
          placeholder="请输入政治面貌"
          :disabled="true"
          v-decorator="['politicalStatus', {rules: [{ required: true, message: '政治面貌不能为空' }] }]"
        />
      </a-form-item>
     
       <a-form-item
        v-bind="formItemLayout"
        label="进站答辩PPT"
      >
        <a-upload
          accept=".pdf,.ppt"
          :fileList="fileList"
          :remove="handleRemove"
          :beforeUpload="beforeUpload"
          @change="handleChange"
        >
          <a-button v-if="fileList.length === 0">
            <a-icon type="upload"  /> 选择文件
          </a-button>
        </a-upload>
       </a-form-item>
       <a-form-item
        v-bind="formItemLayout"
        label="出站答辩PPT"
      >
        <a-upload
          accept=".pdf,.ppt"
          :fileList="fileList2"
          :remove="handleRemove2"
          :beforeUpload="beforeUpload2"
          @change="handleChange2"
        >
          <a-button v-if="fileList2.length === 0">
            <a-icon type="upload"  /> 选择文件
          </a-button>
        </a-upload>
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="其他进出站材料PDF"
      >
        <a-upload
          accept=".pdf"
          :fileList="fileListp"
          :remove="handleRemovep"
          :beforeUpload="beforeUploadp"
          @change="handleChangepic"
        >
          <a-button v-if="fileListp.length === 0">
            <a-icon type="upload"  /> 选择文件
          </a-button>
        </a-upload>
       
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
      isShow2: 1,
      fileList: [],
      fileList2: [],
      fileListp: [],
      isShowp: 1,
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
      let values = this.form.getFieldsValue(['userAccountName', 'userAccount', 'telephone'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.dcaBUser[_key] = values[_key] })
      }
    },
    setFormValues ({ ...dcaBUser }) {
      let fields = ['userAccountName', 'userAccount','deptDesc', 'yggh', 'telephone', 'hkrsbh', 'zslx', 'ldz', 'sexName', 'birthday', 'schoolDate', 'shenfen', 'xueli', 'boshidaoshi', 'biyeDate', 'pinqiRanknum', 'isAuthority', 'isZhuanrang', 'hezuodaoshi', 'biyexuexiao', 'zhuanye', 'inDate', 'goal', 'yqDate', 'fileId','fileUrl','fileIdLc', 'fileUrlLc', 'yuangongzu', 'nation', 'xrgwjbprsj', 'jiguan', 'dcaYear', 'politicalStatus', 'country', 'idCard', 'staffDate', 'pictureId', 'pictureUrl', 'pinqi']
      let fieldDates = ['birthday', 'schoolDate', 'inDate', 'yqDate', 'staffDate', 'biyeDate', 'xrgwjbprsj']
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
      if (dcaBUser.pictureId) {
        if (dcaBUser.pictureId !== '') {
          this.dcaBUser.pictureId = dcaBUser.pictureId
          this.isShowp = 0
          this.fileListp = []
          this.$get('comFile/' + dcaBUser.pictureId).then((r) => {
            let data = r.data
            //console.log(data)
            this.fileListp.push(data)
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
      const isJPG = (file.type === 'application/pdf'||file.type === 'application/x-ppt')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf,ppt文件!')
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
        this.dcaBUser.fileUrl = r.data.data.url
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
      const isJPG = (file.type === 'application/pdf'||file.type === 'application/x-ppt')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf,ppt文件!')
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
         this.dcaBUser.fileUrlLc = r.data.data.url
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
     handleChangepic (info) {
      if (info.file.status === 'uploading') {
        this.handleUploadp()
      }
    },
    handleRemovep (file) {
      this.dcaBUser.pictureId = ''
      this.fileListp = []
      this.isShow2 = 1
    },
    beforeUploadp (file) {
      const isJPG = (file.type === 'application/pdf')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传pdf文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 5
      if (!isLt2M) {
        this.$message.error('附件必须小于 5MB!')
      }
      if (isJPG && isLt2M) {
        this.fileListp = [...this.fileListp, file]
      }
      return isJPG && isLt2M
    },
    handleUploadp () {
      const { fileListp } = this
      const formData = new FormData()
      formData.append('file', fileListp[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        this.dcaBUser.pictureId = r.data.data.uid
        this.dcaBUser.pictureUrl = r.data.data.url
        this.fileListp = []
        this.fileListp.push(r.data.data)
        this.isShowp = 0
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
          let dcaBUser = {}

          this.setFields()
          dcaBUser.telephone= this.dcaBUser.telephone
          dcaBUser.id = this.dcaBUser.id
          dcaBUser.fileId = this.dcaBUser.fileId
          dcaBUser.fileUrl= this.dcaBUser.fileUrl
          dcaBUser.fileIdLc = this.dcaBUser.fileIdLc
          dcaBUser.fileUrlLc= this.dcaBUser.fileUrlLc
          dcaBUser.pictureId= this.dcaBUser.pictureId
          dcaBUser.pictureUrl= this.dcaBUser.pictureUrl
         
         // dcaBUser.appointedDate = (this.dcaBUser.appointedDate==''?null:this.dcaBUser.appointedDate)
         // dcaBUser.appointedDateLC = (this.dcaBUser.appointedDateLc==''?null:this.dcaBUser.appointedDateLc)
         if(dcaBUser.appointedDate ==''){
           dcaBUser.appointedDate= null
         }
          if(dcaBUser.appointedDateLc ==''){
           dcaBUser.appointedDateLc= null
         }
          if(dcaBUser.staffDate ==''){
           dcaBUser.staffDate= null
         }

          this.$put('dcaBDocUser', {
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
      this.$get('dcaBDocUser/person', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data[0]
      
          this.setFormValues(data[0])
        
      }
      )
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
<style scoped>
/* tile uploaded pictures */
.upload-list-inline >>> .ant-upload-list-item {
  float: left;
  width: 200px;
  margin-right: 8px;
}
.upload-list-inline >>> .ant-upload-animate-enter {
  animation-name: uploadAnimateInlineIn;
}
.upload-list-inline >>> .ant-upload-animate-leave {
  animation-name: uploadAnimateInlineOut;
}
</style>
