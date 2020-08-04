<template>
  <a-modal
    title="药品详情"
    v-model="show"
    @cancel="handleCancel"
    :width="900"
    :footer="null"
  >
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
      :scroll="{ x: 900 ,y: 200 }"
    >
    </a-table>
  </a-modal>
</template>

<script>
import moment from 'moment'

export default {
  props: {
    sendVisiable: {
      default: false
    },
    matnr: ''
  },
  data () {
    return {
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
      loading: false,
      bordered: true
    };
  },
  methods: {
    handleCancel (e) {
      this.$emit("close");
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    search () {
      this.selectedRowKeys = [] //搜索时候清空选择项
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
      params.matnr = this.matnr
      params.bsart = 0//药品
      this.$get('scmBPurcharseorder', {
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
  },
  computed: {
    show: {
      get: function () {
        return this.sendVisiable
      },
      set: function () {
      }
    },
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '物料号',
        dataIndex: 'matnr',
        width: 80
      }, {
        title: '供应商账号',
        dataIndex: 'lifnr',
        width: 100
      }, {
        title: '供应商名称',
        dataIndex: 'gysname',
        width: 150
      }, {
        title: '采购日期',
        dataIndex: 'bedat',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 100
      }, {
        title: '订单号',
        dataIndex: 'ebeln',
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'ebeln' && sortedInfo.order,
        width: 100
      }, {
        title: '订单数量',
        dataIndex: 'menge',
        width: 80
      }, {
        title: '供应数量',
        dataIndex: 'allmenge',
        width: 80
      }, {
        title: '收货数量',
        dataIndex: 'suremenge'
      }]
    }
  },
  watch: {
    sendVisiable () {
      if (this.sendVisiable) {
        this.fetch()
      }
    }
  }
}
</script>

<style>
</style>