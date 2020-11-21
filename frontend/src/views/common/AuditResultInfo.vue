<template>
  <a-drawer
    title="部门审核结果"
    placement="right"
    :closable="true"
    :mask="false"
    :visible="visibleUserInfo"
    :width="600"
    @close="onClose"
  >
    
    <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
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
      dataSource: [],
      dcaBUser: {
        
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
   computed: {
    columns () {
      return [
        {
          title: '审核部门',
          dataIndex: 'auditDept',
          width: 100,
          scopedSlots: { customRender: 'confirmIndex' },
        },

        {
          title: '审核内容',
          dataIndex: 'auditTitle',
          width: 200,
        },
        {
          title: '审核结论',
          dataIndex: 'auditResult',
          width: 100,
        },
        {
          title: '备注',
          dataIndex: 'auditNote',
          width: 100,
        }
        ]
    }
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
        this.$get('dcaBAuditdynamic/userAccount', {
          userAccount: userAccount
        }).then((r) => {
          let data = r.data
          this.dataSource = data
        }
        )
      }
    }
  }
}
</script>

<style>
</style>