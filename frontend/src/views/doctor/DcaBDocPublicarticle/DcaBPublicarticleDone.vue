<template>
  <div>
    <a-spin :spinning="loading">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        bordered
        :scroll="scroll"
      >
        <template
          slot="userAccount"
          slot-scope="text, record"
        >
          <a
            href="#"
            @click="showUserInfo(text)"
          >{{text}}</a>
        </template>
      </a-table>
      <audit-userInfo
        ref="userinfo"
        @close="onCloseUserInfo"
        :visibleUserInfo="visibleUserInfo"
        :userAccount="userAccount"
      ></audit-userInfo>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import AuditUserInfo from '../../common/AuditUserDocInfo'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        userAccount: '',
        auditXuhaoE: 0,
        auditXuhaoS: 0,
        auditMan: '',
        auditManName: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 2600,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: ''
    }
  },
  components: { AuditUserInfo },
  props: {
    state: {
      default: 3
    }
  },
  mounted () {
    //this.fetch2()
  },
  methods: {
    moment,
    fetch2 (params = {}) {
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      this.$get('dcaBDocPublicarticle/audit', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    fetch (obj) {
      this.loading = true
      this.queryParams.userAccount = obj.userAccount
      if(obj.auditXuhaoE!=''&& obj.auditXuhaoE !=null ){
         this.queryParams.auditXuhaoE = obj.auditXuhaoE
      }
      else{
        this.queryParams.auditXuhaoE =0
      }
       if(obj.auditXuhaoS!=''&& obj.auditXuhaoS !=null ){
      this.queryParams.auditXuhaoS = obj.auditXuhaoS
       }
       else{
         this.queryParams.auditXuhaoS = 0
       }
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = 1
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = 1
      }
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      params.userAccount = obj.userAccount
      if(obj.auditXuhaoS!=''&& obj.auditXuhaoS !=null ){
        params.auditXuhaoS =  obj.auditXuhaoS
      }
      else{
        params.auditXuhaoS =0
      }
      if(obj.auditXuhaoE!=''&& obj.auditXuhaoE !=null ){
        params.auditXuhaoE =  obj.auditXuhaoE
      }
      else{
        params.auditXuhaoE =0
      }
      this.$get('dcaBDocPublicarticle/audit', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
          {
          title: '序号',
          dataIndex: 'auditXuhao',
          width: 60
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '著作类型',
          dataIndex: 'zzlx',
          width: 130
        },
        {
          title: '著作名称',
          dataIndex: 'zzmc',
          width: 300
        },
        {
          title: '著者身份',
          dataIndex: 'zzsf',
          width: 130
        },
        {
          title: '出版时间',
          dataIndex: 'cbDate',
          width: 130
        },
        {
          title: '第几版',
          dataIndex: 'ranknum',
          width: 130
        },
        {
          title: ' 第几次印刷',
          dataIndex: 'printnum',
          width: 130
        },
        {
          title: ' 书号（ISNB号）',
          dataIndex: 'bookNo',
          width: 130
        },
        {
          title: '出版社名称',
          dataIndex: 'cbsmc',
          width: 130
        },
        {
          title: '作为第一编写人编写章节名称',
          dataIndex: 'bxzjmc',
          width: 130
        },
        {
          title: '作为第一编写人编写章节起止页',
          dataIndex: 'bxwzqzy',
          width: 130
        },
        {
          title: '作为第一编写人编写字数合计',
          dataIndex: 'bxwzzshj',
          width: 130
        },
        {
          title: '作为第一编写人编写字数合计（单位：万字）',
          dataIndex: 'cdzs',
          width: 130
        },
        {
          title: '状态',
          dataIndex: 'state',
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>
              case 1:
                return <a-tag color="green">已提交</a-tag>
              case 2:
                return <a-tag color="red">审核未通过</a-tag>
              case 3:
                return <a-tag color="#f50">已审核</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          width: 120
        },
        
        {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>