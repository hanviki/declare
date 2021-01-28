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
      </a-table>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
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
        userAccount: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      listAuditInfo: [{
        filedName: 'xxy',
        filedTitle: '显示',
        showType: 4,
      }], // 当前用户包含的审核数据
      modelVisiable: false,
      dcaBUser: {

      }
    }
  },
  props: {
    state: {
      default: 3
    }
  },
  mounted () {
    this.fetchUseraudit()
    this.fetch2()
  },
  methods: {
    moment,
    OpenAudit (record) {
      this.dcaBUser = record
      this.modelVisiable = true
    },
     fetch2 (params = {}) {
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
      this.loading = true
      this.$get('checkBUser/user', {
        ...params,
        state: 3
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        data.rows.forEach(element => {
          let auditList = element.checkBAuditresultList
          console.info(auditList)
          if (auditList == null) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
            //  console.info(element2)
              element[element2.filedName] = ''
             // element["auditNote"] = element2.auditNote
            });
          }
          else {
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult
             // element["auditNote"] = element2.auditNote
            });
          }

        });
        this.dataSource = data.rows
       // console.info(data.rows)
        this.pagination = pagination
      }
      )
    },
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
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
      params.sortField = "user_account"
      params.sortOrder = "descend"
      params.userAccount = userAccount
      this.$get('checkBUser/audit', {
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
    cancelAudit () {
      this.modelVisiable = false
    },
 
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "user_account",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
    fetchUseraudit () {
      this.$get('checkBSetting/userCheck', {
      }).then((r) => {
       // console.info(r.data)
        this.listAuditInfo = r.data

        r.data.forEach(element => {
          this.columns.push({
            title: element.filedTitle,
            dataIndex: element.filedName,
            width: 130,
            scopedSlots: { customRender: element.filedName }
          });

        });
      })
    },
  },
  computed: {
    columns () {
       return [
        {
          title: '科室',
          dataIndex: 'ks',
          width: 80
        },
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80
        },
         
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '考核年度',
          dataIndex: 'dcaYear',
          width: 80
        },
        {
          title: '出生日期',
          dataIndex: 'birthday',
          width: 100,
          customRender: (text, row, index) => {
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '专业技术职务',
          dataIndex: 'zyjsgw',
          width: 130,
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'appointedDate',
          width: 130,
          customRender: (text, row, index) => {
            return text == null ? '' : moment(text).format('YYYY-MM-DD')
          },
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>