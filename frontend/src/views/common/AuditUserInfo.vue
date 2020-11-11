<template>
  <a-drawer
    title="用户申报基本资料"
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
      label="所在科室"
    >
      {{dcaBUser.ks}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="联系电话"
    >
      {{dcaBUser.telephone}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="教学岗位"
    >
      {{dcaBUser.zyjsgw}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="聘任时间(教学)"
    >
      {{dcaBUser.appointedDate==null?"":moment(dcaBUser.appointedDate).format('YYYY-MM-DD')}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="临床岗位"
    >
      {{dcaBUser.zyjsgwLc}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="聘任时间(临床)"
    >
      {{dcaBUser.appointedDateLc==null?"":moment(dcaBUser.appointedDateLc).format('YYYY-MM-DD')}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="拟聘岗位职务"
    >
      {{dcaBUser.npPositionName}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="博士毕业时间"
    >
      {{dcaBUser.doctorDesc}}
    </a-form-item>
    <a-form-item
      v-bind="formItemLayout"
      label="来院时间"
    >
      {{dcaBUser.schoolDate==null?"":moment(dcaBUser.schoolDate).format('YYYY-MM-DD')}}
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
        userAccountName: 'mrbird',
        userAccount: 'mrbird',
        deptName: '胡亚克',
        ks: '的事实',
        telephone: '顶顶顶'
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
        this.$get('dcaBUser', {
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