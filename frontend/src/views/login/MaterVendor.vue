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

    <upload-file
      v-for="item in attachList"
      :key="item.index"
      :ref="'file'+item.index"
      :title="item.title"
      :displayIndex="item.index" 
     
      :isRequire="item.isRequire"
    >
    </upload-file>
    <!--<a-button
      v-if="current < 10"
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
     <a class="login" @click="returnLogin">使用已有账户登录</a>
  </div>
</template>

<script>
import UploadFile from './UploadFile'

const formItemLayout = {
  labelCol: { span: 6 },
  wrapperCol: { span: 17 },
};
export default {
  components: { UploadFile },
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
        lb: 1//物资
      },
      scmDVendorD: [],
      form1: this.$form.createForm(this, { name: 'dynamic_rule2' }),
      attachList: [
        { title: "企业法人营业执照", isRequire: true, index: 1 },
        { title: "开发票资料及银行账户信息", isRequire: true, index: 6 },
        { title: "中华人民共和国组织结构代码证", isRequire: false, index: 2 },
        { title: "税务登记证", isRequire: false, index: 3 },
        { title: "中华人民共和国医疗器械经营企业许可证", isRequire: false, index: 4 },
        { title: "中华人民共和国危险化学品经营许可证", isRequire: false, index: 5 },
        
        { title: "企业税票模板", isRequire: false, index: 7 },
        { title: "企业样章备案", isRequire: false, index: 8 },
        { title: "廉洁协议", isRequire: false, index: 9 },
        { title: "采购合同", isRequire: false, index: 10 }
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
      this.form1.validateFields(err => {
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
      this.$post('scmDVendor/regist', {
        ...this.scmDVendor,
        scmDVendorD: JSON.stringify(this.scmDVendorD)
      }).then(() => {
        this.saveF = true
        this.loading = false
        this.$message.success('注册成功')
        this.returnLogin()
      }).catch(() => {
        this.loading = false
        this.$message.error('抱歉，注册账号失败')
      })
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