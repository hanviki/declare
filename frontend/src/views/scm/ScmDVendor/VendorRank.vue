<template>
  <a-card
    :bordered="true"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row>
             <a-col
              :md="12"
              :sm="24"
            >
              <a-form-item
                label="供应商"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.lifnr" placeholder="请输入供应商账号、供应商名称" />
              </a-form-item>
            </a-col>
            <a-col
              :md="12"
              :sm="24"
            >
              <a-form-item
                label="时间范围"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <range-date
                  @change="handleDateChange"
                  ref="createTime"
                  :startDate="startDate"
                  :endDate="endDate"

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
        </span>
      </a-form>
    </div>
    <div>
      <!-- 表格区域 -->
      <a-table
        :bordered="true"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :scroll="{ x: 900 }"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
      >
      </a-table>
    </div>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import moment from 'moment'
export default {
  name: 'rank',
  components: { RangeDate },
  data () {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      queryParams: {
        eindt: moment().subtract(1, "months").format("YYYY-MM-DD"),
        bedat: moment().format("YYYY-MM-DD")
      },
      sortedInfo: null,
      paginationInfo: null,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 1000,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      loading: false,
      startDate: moment().subtract(1, "months"),
      endDate: moment()
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '供应商账号',
        dataIndex: 'code'
      }, {
        title: '供应商名称',
        dataIndex: 'name'
      }, {
        title: '排名次序',
        dataIndex: 'rankNum'
      }, {
        title: '供货率',
        dataIndex: 'pe',
         customRender: (text, row, index) => {
           return text+'%'
         }
      }]
    }
  },
  mounted () {
    this.search()
  },
  methods: {
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    handleDateChange (value) {
      if (value) {
        this.queryParams.eindt = value[0]
        this.queryParams.bedat = value[1]
      }
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('scmDArea/excel', {
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
      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
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
        params.sortField = "code"
        params.sortOrder = "descend"
      }
      this.$get('scmDVendor/rank', {
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
