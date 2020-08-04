<template>
  <div style="width:80%">
    <p style="font-size:26px;color:red;text-align:center;">{{scmDVendor.state=="1"?"您已经审核成功,您的登录账号为"+scmDVendor.code:(scmDVendor.state=="0"?"尚未审核，请等待":"你提交的信息审核未通过,原因:"+scmDVendor.auditCause)}}</p>
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
    <vendor-user
      title="业务员信息"
      isRequire="true"
      ref="vendorUser"
    >
    </vendor-user>
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
      v-show="scmDVendor.state==1?false:true"
    >
      保存
    </a-button>
    <a
      class="login"
      @click="returnLogin"
    >使用已有账户登录</a>
  </div>
</template>
<script>
import moment from 'moment'
import AttachFile from '../scm/ScmDVendor/AttachFile'
import VendorUser from '../scm/ScmDVendor/VendorUser'


const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: { AttachFile, VendorUser },
  name: 'ScmDVendorUpdate',
  props: {
    vendorId: ''
  },
  data () {
    return {
      saveF: false,
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmDVendor: {},
      scmDVendorD: [],
      scmDVendoruser: {},
      attachList: [
        { title: "企业法人营业执照", isRequire: true, index: 1, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国组织机构代码证", isRequire: true, index: 2, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "税务登记证", isRequire: true, index: 3, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营许可证", isRequire: true, index: 4, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营质量管理规范认证证书(GSP)", isRequire: true, index: 5, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },

        { title: "开发票资料及银行账户信息", isRequire: true, index: 6, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业税票模板", isRequire: true, index: 7, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业出库单模板", isRequire: true, index: 8, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业样章备案", isRequire: true, index: 9, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "企业基本情况和质量保证体系情况表", isRequire: true, index: 10, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "下游客户法人授权委托书模板", isRequire: true, index: 11, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品供需双方质量保证协议(正本)", isRequire: true, index: 12, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品供需双方质量保证协议(副本)", isRequire: true, index: 13, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国药品经营许可证副本及变更记录", isRequire: false, index: 14, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国医疗器械经营企业许可证", isRequire: false, index: 15, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "中华人民共和国危险化学品经营许可证", isRequire: false, index: 16, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "食品流通许可证", isRequire: false, index: 17, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
        { title: "药品销售单位首次开户应收集资料", isRequire: false, index: 18, validdatestart: '', validdate: '', fileId: '', fileName: '', fileList: [], showV: 1 },
      ]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    reset () {
      this.scmDVendorD = []
      this.scmDVendor = {}
      this.scmDVendoruser = {}
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
      this.scmDVendor.state = scmDVendor.state
      this.scmDVendor.auditCause = scmDVendor.auditCause
    },
    setscmDVendorFields () {
      let values = this.form.getFieldsValue(['name', 'linkPerson', 'phone', 'email', 'address'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmDVendor[_key] = values[_key] })
      }
    },
    returnLogin () {
      this.$emit('regist', "Login")
    },
    handleSubmit () {
      this.scmDVendorD = []
      this.scmDVendoruser = {}
      var flag = true
      this.form.validateFields(err => {
        if (!err) {
          for (let i = 1; i < 19; i++) {
            this.$refs["file" + i][0].form.validateFields(error => {
              if (error) {
                flag = false
              }
            })
          }
          //新增业务员信息
          this.$refs.vendorUser.form.validateFields(error => {
            if (error) {
              flag = false
            }
          })
        }
        else {
          flag = false
        }
      })

      this.setscmDVendorFields()
      // console.info(this.scmDVendor.name)
      //循环把子组件的值获取到
      for (let i = 1; i < 19; i++) {
        this.$refs["file" + i][0].setScmDAreaFields()
        if (this.$refs["file" + i][0].isRequire && this.$refs["file" + i][0].scmDVendorD.fileId == "") {
          this.$message.error('请上传' + this.attachList[i - 1].title + '附件');
          flag = false
        }
        else {
          //if (this.$refs["file" + i][0].scmDVendorD.fileName != "") {
          this.scmDVendorD.push(this.$refs["file" + i][0].scmDVendorD)
          //}
        }
      }

      // 新增业务员用户信息
      this.$refs.vendorUser.setVendorUserFields()
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.headImage == "") {
        this.$message.error('请上传免冠照片附件')
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.idcardBack == "") {
        this.$message.error('请上传身份证反面附件')
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.idcardFront == "") {
        this.$message.error('请上传身份证正面附件')
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.agentImage == "") {
        this.$message.error('请上传委托人附件')
        flag = false
      }
      this.scmDVendoruser = this.$refs.vendorUser.vendorUser
      //console.log(this.scmDVendoruser)
      if (flag) {
        this.loading = true
        this.$post('scmDVendor/Edit', {
          ...this.scmDVendor,
          scmDVendorD: JSON.stringify(this.scmDVendorD),
          scmDVendoruser: JSON.stringify(this.scmDVendoruser)
        }).then(() => {
          this.saveF = true
          this.loading = false
          this.$message.success('修改成功')
        }).catch(() => {
          this.saveF = false
          this.loading = false
          this.$message.error('抱歉，修改失败')
        })
      }
    },
    fetch (params = {}) {
      this.loading = true
      this.$get('scmDVendor/GetByVendorId/' + this.vendorId).then((r) => {
        this.loading = false
        let data2 = r.data.data
        let scmDVendor2 = data2.scmDVendor
        this.setFormValues(scmDVendor2)
        let data = data2.scmDVendorDS
        if (data) {
          if (data.length > 0) {
            console.info('数据长度' + data.length);
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
                  url: this.$baseUrl + 'uploadFile/' + data[index].attachfile.serverName
                })
              }
            }
          }
        }
      })
      this.setVendorUser(this.vendorId);//新增 设置业务员信息
    },
    setVendorUser (vendorId) {
      this.$get('scmDVendoruser/user2/' + vendorId).then((r) => {
        console.log(r)
        if (r.data != null) {
          let data2 = r.data.data
          //let vendorUser = data2.scmDVendoruser
          let that = this

          that.$refs.vendorUser.getVendorUserFields(data2)
        }
      })
    }
  }
}
</script>

