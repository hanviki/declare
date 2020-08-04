<template>
  <div>
    <a-form
      :form="form1"
      v-show="current == 0"
    >
      <a-form-item
        v-bind="formItemLayout"
        label="供应商名称"
      >
        <a-input
          v-decorator="[
          'name',
          { rules: [{ required: true, message: '请输入供应商名称' }] },
        ]"
          placeholder="请输入供应商名称"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系人"
      >
        <a-input
          v-decorator="[
          'linkPerson',
          { rules: [{ required: true, message: '请输入联系人' }] },
        ]"
          placeholder="请输入联系人"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系电话"
      >
        <a-input
          v-decorator="[
          'phone',
          { rules: [{ required: true, message: '请输入联系电话' }] },
        ]"
          placeholder="请输入联系电话"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="电子邮件"
      >
        <a-input
          v-decorator="[
          'email',
          { rules: [{ type: 'email', required: true, message: '请输入电子邮件' }] },
        ]"
          placeholder="请输入电子邮件"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="办公地址"
      >
        <a-input
          v-decorator="[
          'address',
          { rules: [{ required: true, message: '请输入办公地址' }] },
        ]"
          placeholder="请输入办公地址"
        />
      </a-form-item>
    </a-form>
    <vendor-user
      title="业务员信息"
      isRequire="true"
      ref="vendorUser"
    >
    </vendor-user>
    <upload-file
      v-for="item in attachList"
      :key="item.index"
      :ref="'file'+item.index"
      :title="item.title"
      :displayIndex="item.index"
      :isRequire="item.isRequire"
    >
    </upload-file>
    <!-- <a-button
      v-if="current < 18"
      type="primary"
      @click="next"
    >
      下一步
    </a-button>
    <a-button
      v-if="current>0"
      style="margin-left: 8px"
      @click="prev"
    >
      上一步
    </a-button>-->
    <a-button
      type="primary"
      :loading="loading"
      @click.stop.prevent="handleSubmit"
      :disabled="saveF"
    >
      立即注册
    </a-button>
    <a
      class="login"
      @click="returnLogin"
    >使用已有账户登录</a>
    <div ref="divID"></div>
  </div>
</template>

<script>
import UploadFile from './UploadFile'
import VendorUser from '../scm/ScmDVendor/VendorUser'

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 },
};
export default {
  components: { UploadFile, VendorUser },
  data () {
    return {
      saveF: false,
      loading: false,
      current: 0,
      checkNick: false,
      formItemLayout,
      scmDVendor: {
        name: '',
        linkPerson: '',
        phone: '',
        email: '',
        address: '',
        lb: 0//药品
      },
      scmDVendorD: [],
      scmDVendoruser: {},
      form1: this.$form.createForm(this, { name: 'dynamic_rule' }),
      attachList: [
        { title: "企业法人营业执照", isRequire: true, index: 1 },
        { title: "中华人民共和国组织机构代码证", isRequire: true, index: 2 },
        { title: "税务登记证", isRequire: true, index: 3 },
        { title: "中华人民共和国药品经营许可证", isRequire: true, index: 4 },
        { title: "中华人民共和国药品经营质量管理规范认证证书(GSP)", isRequire: true, index: 5 },

        { title: "开发票资料及银行账户信息", isRequire: true, index: 6 },
        { title: "企业税票模板", isRequire: true, index: 7 },
        { title: "企业出库单模板", isRequire: true, index: 8 },
        { title: "企业样章备案", isRequire: true, index: 9 },
        { title: "企业基本情况和质量保证体系情况表", isRequire: true, index: 10 },
        { title: "下游客户法人授权委托书模板", isRequire: true, index: 11 },
        { title: "药品供需双方质量保证协议(正本)", isRequire: true, index: 12 },
        { title: "药品供需双方质量保证协议(副本)", isRequire: true, index: 13 },
        { title: "中华人民共和国药品经营许可证副本及变更记录", isRequire: false, index: 14 },
        { title: "中华人民共和国医疗器械经营企业许可证", isRequire: false, index: 15 },
        { title: "中华人民共和国危险化学品经营许可证", isRequire: false, index: 16 },
        { title: "食品流通许可证", isRequire: false, index: 17 },

        { title: "药品销售单位首次开户应收集资料", isRequire: false, index: 18 },

      ]
    };
  },
  methods: {
    check () {

    },
    handleChange (e) {
      this.checkNick = e.target.checked;
      this.$nextTick(() => {
        this.form1.validateFields(['nickname'], { force: true })
      })
    },
    returnLogin () {
      this.$emit('regist')
    },
    handleSubmit () {
      let flag = true
      this.scmDVendorD = []
      this.form1.validateFields(err => {
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
        //if (this.$refs["file" + i][0].scmDVendorD.fileName != "") {
        this.scmDVendorD.push(this.$refs["file" + i][0].scmDVendorD)
        //}
      }
      // 新增业务员用户信息
      this.$refs.vendorUser.setVendorUserFields()
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.headImage == "") {
        this.$message.error('请上传免冠照片附件');
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.idcardBack == "") {
        this.$message.error('请上传身份证反面附件');
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.idcardFront == "") {
        this.$message.error('请上传身份证正面附件');
        flag = false
      }
      if (this.$refs.vendorUser.isRequire && this.$refs.vendorUser.vendorUser.agentImage == "") {
        this.$message.error('请上传委托人附件');
        flag = false
      }
      if (flag) {
        
        this.scmDVendoruser = this.$refs.vendorUser.vendorUser
        console.log(this.scmDVendoruser)

        this.loading = true
        this.$post('scmDVendor/regist', {
          ...this.scmDVendor,
          scmDVendorD: JSON.stringify(this.scmDVendorD),
          scmDVendoruser: JSON.stringify(this.scmDVendoruser)
        }).then((data) => {
          this.saveF = true
          this.loading = false
          if (data.data.message.length === 36) {
            // this.$message.success('注册成功')
            this.$message.info('注册成功,请记住此注册码:' + data.data.message + ' ,以便查询审核进度。', 300)
            this.$refs.divID.innerHTML = "请记住此注册码:" + data.data.message + " ,以便查询审核进度。"
          }
          else{
            this.$message.success('注册失败')
            this.$message.info(data.data.message, 300)
          }
          console.log(data)      
          // this.returnLogin()
        }).catch(() => {
          this.saveF = false
          this.loading = false
          this.$message.error('抱歉，注册账号失败')
        })
      }
    },
    setscmDVendorFields () {
      let values = this.form1.getFieldsValue(['name', 'linkPerson', 'phone', 'email', 'address'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmDVendor[_key] = values[_key] })
      }
    },

    next () {
      this.form1.validateFields(err => {
        if (!err) {
          if (this.current > 0) {
            if (this.$refs["file" + this.current][0].isRequire && this.$refs["file" + this.current][0].scmDVendorD.fileId == "") {
              this.$message.error('请上传附件')
              return
            }
            this.$refs["file" + this.current][0].form.validateFields(error => {
              if (!error) {
                this.current++
              }
            })
          }
          else {
            this.current++
          }
        }
      })
    },
    prev () {
      this.current--
    }
  }
};
</script>