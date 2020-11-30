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
    },
    dcaYear: {
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
          customRender: (text, row, index) => {
            if (row.auditTitletype != "sfbsds" || row.auditTitletype != "sfssds"){
              if (text == "是") {
                if (row.state == 1) {
                  return <a-tag color="green">是</a-tag>
                }
                else {
                  return <a-tag color="red">是</a-tag>
                }
              }
              else if (text == "否") {
                if (row.state == 2) {
                  return <a-tag color="green">否</a-tag>
                }
                else {
                  return <a-tag color="red">否</a-tag>
                }
              }
              else {
                return text
              }
          }
          else {
            return text
          }
        }
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
        this.getUserInfo(this.userAccount,this.dcaYear)
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
    getUserInfo (userAccount,dcaYear) {
      if (userAccount != '') {
        let url= 'dcaBAuditdynamic/userAccount';
        if(dcaYear!='') {
           url= 'dcaBCopyAuditdynamic/account';
        }
        this.$get(url, {
          userAccount: userAccount,
          dcaYear: dcaYear
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