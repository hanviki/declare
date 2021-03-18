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
      <a-modal
        title=""
        :visible="modelVisiable"
        :footer="null"
        @cancel="cancelAudit"
      >
        <a-textarea
          :value="dcaBUser.userAccountName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.userAccount"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.deptName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.positionName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.npPositionName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.sexName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.birthday"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.schoolDate"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.zyjsgw"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.xcszyjzc"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.appointedDate"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.patentRanknum"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.isAuthority"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.isZhuanrang"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.patentGood"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.auditMan"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.auditManName"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.auditDate"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.auditSuggestion"
          :rows="12"
          placeholder=""
        ></a-textarea>
        <a-textarea
          :value="dcaBUser.IsUse"
          :rows="12"
          placeholder=""
        ></a-textarea>
      </a-modal>
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
        fieldName: 'xxy',
        fieldTitle: '显示',
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
     // params.userAccount = this.queryParams.userAccount 
      this.loading = true
      this.$get('dcaUserAudit/user', {
        ...params,
        state: 3
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
         // console.info(auditList)
          if (auditList == null) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
            //  console.info(element2)
              element[element2.fieldName] = element2.showType==1?'否':''
              element["auditNote"] = element2.auditNote
            });
          }
          else {
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult
              element["auditNote"] = element2.auditNote
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
      this.$get('dcaBUser/audit', {
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
    getGwdj (text) {
      let name = ''
      switch (text) {
        case '教授主任医师':
          name = '正高'
          break
        case '教授':
          name = '正高'
          break
        case '主任医师':
          name = '正高'
          break
        case '研究员':
          name = '正高'
          break
        case '主任护师':
          name = '正高'
          break
        case '主任技师':
          name = '正高'
          break
        case '主任药师':
          name = '正高'
          break
        case '教授级高级工程师':
          name = '正高'
          break
        case '编审':
          name = '正高'
          break
        case '副教授副主任医师':
          name = '副高'
          break
        case '副教授':
          name = '副高'
          break
        case '副主任医师':
          name = '副高'
          break
        case '副研究员':
          name = '副高'
          break
        case '副主任护师':
          name = '副高'
          break
        case '副主任技师':
          name = '副高'
          break
        case '副主任药师':
          name = '副高'
          break
        case '高级工程师':
          name = '副高'
          break
        case '副编审':
          name = '副高'
          break
      }
      return name
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
      this.$get('dcaDAuditinfo/userAudit', {
      }).then((r) => {
        console.info(r.data)
        this.listAuditInfo = r.data

        r.data.forEach(element => {
          this.columns.push({
            title: element.fieldTitle,
            dataIndex: element.fieldName,
            width: 130,
            scopedSlots: { customRender: element.fieldName }
          });

        });
        this.columns.push({
          title: '备注',
          dataIndex: 'auditNote',
          width: 130,
          scopedSlots: { customRender: 'auditNote' }
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
          title: '系列',
          dataIndex: 'xl',
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
          title: '申报年度',
          dataIndex: 'dcaYear',
          width: 80
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 60
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
          customRender: (text, row, index) => {
            return (row.zyjsgw == null || row.zyjsgw == '' ? "" : row.zyjsgw) + (row.zyjsgwLc == null || row.zyjsgwLc == '' ? "" : row.zyjsgwLc)
          },
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'appointedDate',
          width: 130,
          customRender: (text, row, index) => {
            return (text == null ? '' : moment(text).format('YYYY-MM-DD')) + (text == null ? '' : '/') + (row.appointedDateLc == null ? '' : moment(row.appointedDateLc).format('YYYY-MM-DD'))
          },
        },
        {
          title: '申请岗位等级',
          dataIndex: 'gwdj',
          width: 130,
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>