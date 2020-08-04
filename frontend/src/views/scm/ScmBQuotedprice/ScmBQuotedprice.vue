<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="药品"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input
                  v-model="queryParams.keyword"
                  placeholder="请输入药品名称、编码"
                />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="询价状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-select
                  v-model="queryParams.queryState"
                  style="width: 100%"
                  placeholder="请输入询价状态"
                >
                  <a-select-option value="-1">全部</a-select-option>
                  <a-select-option value="0">未询价</a-select-option>
                  <a-select-option value="1">询价中</a-select-option>
                  <a-select-option value="2">已结束</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="报价状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-select
                  v-model="queryParams.quoteState"
                  style="width: 100%"
                  placeholder="请输入报价状态"
                >
                  <a-select-option value="-1">全部</a-select-option>
                  <a-select-option value="0">未报价</a-select-option>
                  <a-select-option value="1">已报价</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </a-row>
          <a-row v-if="advanced">
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="询价时间"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <range-date
                  @change="handleQueryDateChange"
                  ref="queryDate"
                ></range-date>
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="截止时间"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <range-date
                  @change="handleEndDateChange"
                  ref="endDate"
                ></range-date>
              </a-form-item>
            </a-col>
          </a-row>
        </div>
        <span style="float: right; margin-top: 3px;">
          <a-button
            type="primary"
            @click="search"
          >查询</a-button>
          <a-button
            style="margin-left: 8px"
            @click="reset"
          >重置</a-button>
          <a
            @click="toggleAdvanced"
            style="margin-left: 8px"
          >
            {{advanced ? '收起' : '展开'}}
            <a-icon :type="advanced ? 'up' : 'down'" />
          </a>
        </span>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button
          v-hasPermission="['scmBQuotedprice:add']"
          type="primary"
          ghost
          @click="add"
        >我要报价</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 1200 }"
      >
        <template
          slot="remark"
          slot-scope="text, record"
        >
          <a-popover placement="topLeft">
            <template slot="content">
              <div style="max-width: 200px">{{text}}</div>
            </template>
            <p style="width: 200px;margin-bottom: 0">{{text}}</p>
          </a-popover>
        </template>
        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            v-show="record.queryState<2"
            type="eye"
            theme="twoTone"
            twoToneColor="#42b983"
            @click="edit(record)"
            title="查看"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <scmBQuotedprice-add
      ref="scmBQuotedpriceAdd"
      :scmBQueryprice="scmBQueryprice"
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
      :showType="showType"
    >
    </scmBQuotedprice-add>

  </a-card>
</template>

<script>
import ScmBQuotedpriceAdd from './ScmBQuotedpriceAdd'
import RangeDate from '@/components/datetime/RangeDate'
import moment from 'moment'

export default {
  name: 'ScmBQuotedprice',
  components: { ScmBQuotedpriceAdd, RangeDate },
  data () {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {},
      addVisiable: false,
      editVisiable: false,
      loading: false,
      bordered: true,
      scmBQueryprice: {},
      showType: 0
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '询价单号',
        dataIndex: 'id',
        width: 100,
        fixed: 'left'
      }, {
        title: '药品名称',
        dataIndex: 'txz01',
        width: 200,
        fixed: 'left'
      }, {
        title: '药品编码',
        dataIndex: 'matnr',
        width: 80
      }, {
        title: '药品规格',
        dataIndex: 'spec',
        width: 100
      }, {
        title: '计量单位',
        dataIndex: 'mseht',
        width: 80
      }, {
        title: '采购数量',
        dataIndex: 'amount',
        width: 80
      }, {
        title: '询价日期',
        dataIndex: 'queryDate',
        customRender: (text, row, index) => {
          if (text != null) {
            return moment(text).format('YYYY-MM-DD')
          }
          else {
            return '';
          }
        },
        width: 100
      }, {
        title: '截止日期',
        dataIndex: 'endDate',
        customRender: (text, row, index) => {
          if (text != null) {
            return moment(text).format('YYYY-MM-DD')
          }
          else {
            return '';
          }
        }
      }, {
        title: '报价状态',
        dataIndex: 'quoteState',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未报价</a-tag>
            default:
              return <a-tag color="green">已报价</a-tag>
          }
        },
        width: 80,
        fixed: 'right'
      }, {
        title: '询价状态',
        dataIndex: 'queryState',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未询价</a-tag>
            case 1:
              return <a-tag color="green">询价中</a-tag>
            case 2:
              return <a-tag color="#f50">已结束</a-tag>
          }
        },
        width: 80,
        fixed: 'right'
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 100
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.comments = ''
      }
    },
    handleAddSuccess () {
      this.addVisiable = false
      this.$message.success('新增成功')
      this.search()
    },
    handleAddClose () {
      this.addVisiable = false
    },
    add () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择询价列表')
        return
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning('请只选择一个询价列表')
        return
      }
      const dataSource = [...this.dataSource]
      this.baseId = this.selectedRowKeys[0]
      let row = dataSource.find(item => item.id === this.selectedRowKeys[0]);
      if (row.queryState == 2) {
        this.$message.warning('询价已经结束，不能操作')
        return
      }
      this.scmBQueryprice = row
      this.showType = 0
      this.addVisiable = true
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.$message.success('修改成功')
      this.search()
    },
    handleQueryDateChange (value) {
      if (value) {
        this.queryParams.queryDateFrom = value[0]
        this.queryParams.queryDateTo = value[1]
      }
    },
    handleEndDateChange (value) {
      if (value) {
        this.queryParams.endDateFrom = value[0]
        this.queryParams.endDateTo = value[1]
      }
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {//查看报价
      this.scmBQueryprice = record
      this.showType = 1
      this.addVisiable = true
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let scmBQuotedpriceIds = that.selectedRowKeys.join(',')
          that.$delete('scmBQuotedprice/' + scmBQuotedpriceIds).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('scmBQuotedprice/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
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
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
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
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams
      })
    },
    fetch (params = {}) {
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
      if (params.sortField == null) {
        params.sortField = "id"
        params.sortOrder = "descend"
      }
      if(params.quoteState==null||params.quoteState==undefined)
      {
        params.quoteState=-1
      }
      this.$get('scmBQueryprice/gysList', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
        this.dataSource = data.rows
        this.pagination = pagination
      })
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
