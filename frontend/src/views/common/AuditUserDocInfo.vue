<template>
  <a-drawer
    title="用户基本资料"
    placement="left"
    :closable="true"
    :mask="false"
    :visible="visibleUserInfo"
    @close="onClose"
  >
    <a-form-item
      v-bind="formItemLayout"
      label="姓名"
    >
      {{dcaBUser.userAccountName}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="人事编号"
    >
      {{dcaBUser.userAccount}}
    </a-form-item>

    <a-form-item
      v-bind="formItemLayout"
      label="部门描述"
    > {{dcaBUser.deptDesc}}

    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="员工工号"
    >
      {{dcaBUser.yggh}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="华科人事编号"
    >
      {{dcaBUser.hkrsbh}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="性别"
    >
      {{dcaBUser.sexName}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="出生年月"
    >
      {{dcaBUser.birthday==null?"":moment(dcaBUser.birthday).format('YYYY-MM-DD')}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="员工子组"
    >
      {{dcaBUser.yuangongzu}}
    </a-form-item>

    <a-form-item
      v-bind="formItemLayout"
      label="身份证号"
    >
      {{dcaBUser.idCard}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="学历"
    >
      {{dcaBUser.xueli}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="博士导师"
    >
      {{dcaBUser.boshidaoshi}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="合作导师"
    >
      {{dcaBUser.hezuodaoshi}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="毕业时间"
    >
      {{dcaBUser.biyeDate==null?"":moment(dcaBUser.biyeDate).format('YYYY-MM-DD')}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="毕业院校"
    >
      {{dcaBUser.biyexuexiao}}

    </a-form-item>

    <a-form-item
      v-bind="formItemLayout"
      label="来院工作时间"
    >
      {{dcaBUser.schoolDate==null?"":moment(dcaBUser.schoolDate).format('YYYY-MM-DD')}}
    </a-form-item>

    <a-form-item
      v-bind="formItemLayout"
      label="手机号"
    >
     {{dcaBUser.telephone}}
    </a-form-item>

    <a-textarea
      placeholder="请输入发送信息"
      @blur="e => inputChange(e.target.value)"
      :value="mess"
    >
    </a-textarea>
    <a-button
      style="margin-top: 5px"
      type="primary"
      @click="sendMess"
    >发送消息</a-button>
  </a-drawer>

</template>

<script>
const formItemLayout = {
  labelCol: { span: 12 },
  wrapperCol: { span: 12 }
}
import moment from 'moment';
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      formItemLayout,
      loading: false,
      dcaBUser: {
        userAccountName: '',
        userAccount: '',
        deptName: '',
        ks: '',
        telephone: '',
        doctorDesc: '',
      },
      mess: ''
    }
  },
  props: {
    visibleUserInfo: {
      default: false
    },
    userAccount: {
      default: ''
    }
  },
  mounted () {

  },
  watch: {
    userAccount () {
      if (this.visibleUserInfo) {
        this.getUserInfo(this.userAccount)
      }
    }
  },
  methods: {
    moment,
    onClose () {
      //this.dcaBUser = {}
      //this.userAccount = ''
      this.$emit('close')
    },
    inputChange (value) {
      console.info(value)
      this.mess = value
    },
    sendMess () {
      if (this.mess != '') {
        this.$post('user/mess', {
          tel: this.dcaBUser.telephone,
          message: this.mess
        }).then((r) => {
          this.$message.success('发送成功')
        }
        )
      }
    },
    getUserInfo (userAccount) {
      if (userAccount != '') {
        this.$get('dcaBDocUser', {
          userAccount: userAccount
        }).then((r) => {
          let data = r.data
          if (data.rows.length > 0
          ) {
            this.dcaBUser = data.rows[0]
            this.mess = ''
          }
        }
        )
      }
    }
  }
}
</script>

<style>
</style>