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
        x: 2800,
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
      this.$get('dcaBDocSciencepublish/audit', {
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
     
      this.$get('dcaBDocSciencepublish/audit', {
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
          scopedSlots: { customRender: 'userAccount' },
          fixed: 'left'
        },
         {
          title: '序号',
          dataIndex: 'auditXuhao',
          width: 60,
          fixed: 'left'
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80,
          fixed: 'left'
        },
        {
          title: '论文名',
          dataIndex: 'paperName',
          width: 200,
          fixed: 'left'
        },
        {
          title: '期刊名',
          dataIndex: 'journalName',
          width: 200,
          fixed: 'left'
        },
        {
          title: '期刊号',
          dataIndex: 'journalCode',
          width: 120,
          fixed: 'left'
        },
        {
          title: '发表年月',
          dataIndex: 'paperPublishdate',
          width: 130,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
          fixed: 'left'
        },
        {
          title: '收录情况',
          dataIndex: 'paperShoulu',
          width: 120,
          scopedSlots: { customRender: 'paperShoulu' }
        },
        {
          title: '影响因子',
          dataIndex: 'paperCause',
          width: 120,
          scopedSlots: { customRender: 'paperCause' }
        },
        {
          title: '是否一流期刊',
          dataIndex: 'isBest',
          width: 120,
          scopedSlots: { customRender: 'isBest' }
        },
        {
          title: '他引次数',
          dataIndex: 'otherTimes',
          width: 120,
          scopedSlots: { customRender: 'otherTimes' }
        },
        {
          title: '期刊级别',
          dataIndex: 'qkjb',
          width: 100,
          scopedSlots: { customRender: 'qkjb' }
        },
        {
          title: '第一或通讯作者',
          dataIndex: 'authorRank',
          width: 120,
          scopedSlots: { customRender: 'authorRank' }
        },
        {
          title: '第几作者',
          dataIndex: 'djzz',
          width: 80,
          scopedSlots: { customRender: 'djzz' }
        },
         {
          title: '第一作者第一单位',
          dataIndex: 'firstUnitAuthor',
          width: 80,
          scopedSlots: { customRender: 'firstUnitAuthor' }
        },
        {
          title: '期刊类型',
          dataIndex: 'wzlx',
          width: 80,
          scopedSlots: { customRender: 'wzlx' }
        },
        //  {
        //   title: '是否能用于教学职称申报',
        //   dataIndex: 'isJxzcsb',
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   width: 120,
        // }, {
        //   title: '是否能用于临床职称申报',
        //   dataIndex: 'isLczcsb',
        //   width: 120,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        // }, {
        //   title: '期刊级别',
        //   dataIndex: 'auditQkjb',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   }
        // }, {
        //   title: '教学职称数量',
        //   dataIndex: 'jxzcsl',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   }
        // }, {
        //   title: '临床职称数量',
        //   dataIndex: 'lczcsl',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   }
        // }, {
        //   title: '第一作者或通讯作者共几人',
        //   dataIndex: 'auditTotalnum',
        //   width: 100,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        // },
        // {
        //   title: '非第一作者或通讯作者',
        //   dataIndex: 'auditIsfirst',
        //   width: 100,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   customRender: (text, row, index) => {
        //     if (text) return "是"
        //     return "否"
        //   }
        // },
        {
          title: '状态',
          dataIndex: 'state',
          width: 100,
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
          dataIndex: 'auditSuggestion'
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