<template>

  <a-card
    class="card-area"
    title="基本资料"
  >
    <a-spin :spinning="loading">
      <div>
        <a-form layout="horizontal">
          <a-row>
            <div>
              <a-col
                :md="8"
                :sm="24"
              >
                <a-form-item
                  label="发薪号/姓名"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.userAccount" />
                </a-form-item>
              </a-col>
              <a-col
                :md="8"
                :sm="24"
              >
                <a-form-item
                  label="申报年度"
                  v-bind="formItemLayout"
                  v-show="!dcaType==''"
                >
                  <a-input v-model="queryParams.auditMan" />
                </a-form-item>
              </a-col>
            </div>
            <span style="float: right; margin-top: 3px;">
              <a-button
                type="primary"
                @click="exportCustomExcel"
              >导出</a-button>
              <a-button
                type="primary"
                @click="search"
              >查询</a-button>
              <a-button
                style="margin-left: 8px"
                @click="reset"
              >重置</a-button>
            </span>
          </a-row>
        </a-form>
      </div>

      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :bordered="true"
        :scroll="scroll"
      >
        <template
          slot="userAccount"
          slot-scope="text, record"
        >
          <a
            href="#"
            @click="showUserInfo(record)"
          >{{text}}</a>
        </template>

      </a-table>
      <user-info
        :infoVisiable="infoVisiable"
        :userAccount="userAccount"
        :picUrl="picUrl"
        @close="onCloseUserInfo"
      >
      </user-info>
    </a-spin>
  </a-card>

</template>

<script>
import moment from 'moment';
import UserInfo from './UserInfo'


const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
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
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      userAccount: '',
      infoVisiable: false,
      picUrl: '',
      user: []
    }
  },
  components: { UserInfo },
  mounted () {
    this.search()
  },
  props: {
    dcaYear: {
      default: '' //年度
    },
    dcaType: {
      default: '' //中高级
    }
  },
  methods: {
    moment,
    callback () {

    },
    showUserInfo (record) {
      //debugger
      this.infoVisiable = true
      this.userAccount = record.userAccount
      console.info(record)
      this.picUrl= record.pictureUrl
     // this.user=[]
     // this.user.push(record)

    },
    onCloseUserInfo () {
      this.infoVisiable = false
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
      // this.freshTabs()
    },

    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列排序规则
      this.sortedInfo = null
      this.paginationInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.search()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {

    },
    handleSelectChange (value, option, record, filedName) {
      record[filedName] = value
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    checkedValue (record, fieldName) {
      // console.info(fieldName)
      return record[fieldName] == '是'
    },
    inputCheckChange (blFlag, f, record, filedName) {
      //console.info(blFlag)
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      // json.splice(this.columns.length-1,1) //移出第一个
      // console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams = this.queryParams


      this.$export('dcaBUser/excel2', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...queryParams
      })
    },
    fetch (params = {}) {
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
      this.$get('dcaBUser/audit', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        this.dataSource = data.rows
        console.info(data.rows)
        this.pagination = pagination
      }
      )
    }
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
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 60
        },
        {
          title: '身份证号',
          dataIndex: 'idCard',
          width: 150
        },
        {
          title: '政治面貌',
          dataIndex: 'politicalStatus',
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
          dataIndex: 'positionName',
          width: 130
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'zygwDate',
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
