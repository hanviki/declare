<template>
  <div style="width:80%">
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="名字"
      >
        <a-input
          placeholder="请输入名字"
          v-decorator="['name', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="地址"
      >
        <a-input
          placeholder="请输入地址"
          v-decorator="['address', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系人"
      >
        <a-input
          placeholder="请输入联系人"
          v-decorator="['linkPerson', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系电话"
      >
        <a-input
          placeholder="请输入联系电话"
          v-decorator="['phone', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="邮件"
      >
        <a-input
          placeholder="请输入邮件"
          v-decorator="['email', {}]"
        />
      </a-form-item>
    </a-form>
    <attach-file
      v-for="item in attachList"
      :key="item.index"
      :ref="'file'+item.index"
      :title="item.title"
      :displayIndex="item.index"
      :isRequire="item.isRequire"
      :validdatestart="item.validdatestart"
      :validdate="item.validdate"
      :fileId="item.fileId"
      :fileName="item.fileName"
    >
    </attach-file>
    <a-button
      type="primary"
      @click.stop.prevent="handleSubmit"
      :disabled="saveF"
    >
      保存
    </a-button>
  </div>
</template>
<script>
import moment from 'moment'
import AttachFile from './AttachFile'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: { AttachFile },
  name: 'ScmDVendorUpdate',
  data () {
    return {
      saveF:false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmDVendor: {},
      scmDVendorD:[],
      attachList:  [
        { title: "企业法人营业执照", isRequire: true, index: 1, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国组织结构代码证", isRequire: false, index: 2, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "税务登记证", isRequire: false, index: 3, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国医疗器械经营企业许可证", isRequire: false, index: 4, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国危险化学品经营许可证", isRequire: false, index: 5, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "开发票资料及银行账户信息", isRequire: true, index: 6, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业税票模板", isRequire: false, index: 7, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业样章备案", isRequire: false, index: 8, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "廉洁协议", isRequire: false, index: 9, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "采购合同", isRequire: false, index: 10, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 }
      ]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    setFormValues ({ ...scmDVendor }) {
      let fields = ['name', 'address', 'linkPerson', 'phone', 'email']
      let fieldDates = ['createTime', 'modifyTime']
      Object.keys(scmDVendor).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (scmDVendor[key] !== '') {
              obj[key] = moment(scmDVendor[key])
            }
          } else {
            obj[key] = scmDVendor[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmDVendor.id = scmDVendor.id
      this.scmDVendor.code = scmDVendor.code
    },
    setscmDVendorFields () {
      let values = this.form.getFieldsValue(['name', 'linkPerson', 'phone', 'email', 'address'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmDVendor[_key] = values[_key] })
      }
    },
    handleSubmit () {
        this.scmDVendorD=[]
      this.form.validateFields(err => {
        if (!err) {
          for (let i = 1; i < 11; i++) {
            this.$refs["file" + i][0].form.validateFields(error => {
              if (error) {
                return false
              }
            })
          }
        }
        else {
          return false
        }
      })
      this.setscmDVendorFields()
      // console.info(this.scmDVendor.name)
      //循环把子组件的值获取到
      for (let i = 1; i < 11; i++) {
        this.$refs["file" + i][0].setScmDAreaFields()
        if (this.$refs["file" + i][0].isRequire && this.$refs["file" + i][0].scmDVendorD.fileId == "") {
          this.$message.error('请上传' + this.attachList[i - 1].title + '附件');
          return false
        }
        //if (this.$refs["file" + i][0].scmDVendorD.fileName != "") {
        this.scmDVendorD.push(this.$refs["file" + i][0].scmDVendorD)
        //}
      }
      this.loading = true
      this.$post('scmDVendor/Edit', {
        ...this.scmDVendor,
        scmDVendorD: JSON.stringify(this.scmDVendorD)
      }).then(() => {
        this.saveF = true
        this.loading = false
        this.$message.success('修改成功')
      }).catch(() => {
        this.saveF = false
        this.loading = false
        this.$message.error('抱歉，修改失败')
      })
    },
    fetch (params = {}) {
      this.loading = true
      this.$get('scmDVendor/GetByVendorCode').then((r) => {
        let data2 = r.data.data
        let scmDVendor2 = data2.scmDVendor
        this.setFormValues(scmDVendor2)
        let data = data2.scmDVendorDS
        if (data) {
          if (data.length > 0) {
            for (var index = 0; index < data.length; index++) {
              let entity = {
                fileName: '',
                validdatestart: '',
                validdate: ''
              }
              if (data[index].validDatestart != null) {
                entity.validdatestart = data[index].validDatestart
                this.attachList[index].validdatestart = data[index].validDatestart
              }
              if (data[index].validDate != null) {
                entity.validdate = data[index].validDate
                this.attachList[index].validdate = data[index].validDate
              }
              if (data[index].attachfile.id != null) {
                this.attachList[index].fileId = data[index].attachfile.id
              }
              if (data[index].filename != null) {
                entity.fileName = data[index].filename
              }
              this.$refs['file' + (index + 1)][0].getScmDAreaFields(entity)
              this.attachList[index].fileName = data[index].filename
              this.$refs['file' + (index + 1)][0].fileList = []
              if (data[index].attachfile.id !== '' && data[index].attachfile.id !== null) {
                this.$refs['file' + (index + 1)][0].isShow = 0
                this.$refs['file' + (index + 1)][0].fileList.push({
                  uid: data[index].attachfile.id,
                  name: data[index].attachfile.clientName,
                  status: 'done',
                  url: this.$baseUrl+'uploadFile/'+data[index].attachfile.serverName
                })
              }
            }
          }
        }
      })
    }
  }
}
</script>
