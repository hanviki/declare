<template>
  <a-modal
    title="供应商选择"
    :width="800"
    @cancel="onClose"
    v-model="addVendorVisiable"
    @ok="handleOk"
  >
    <div>
      <a-form layout="horizontal">
        <div :class="'fold'">
          <a-row>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="供应商名称"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.name" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="供应商账号"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.code" />
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
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange, onSelect: onSelect}"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 600 }"
      >
      </a-table>
    </div>
  </a-modal>
</template>

<script>

export default {
  name: 'scmDVendor',
  props: {
    addVendorVisiable: {
      default: false
    }
  },
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
      loading: false,
      bordered: true,
      rows: []
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '供应商名称',
        dataIndex: 'name'
      }, {
        title: '供应商账号',
        dataIndex: 'code'
      }, {
        title: '联系人',
        dataIndex: 'linkPerson'
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      const dataSource = [...this.dataSource];
      for (let item of dataSource) {
        const rows2=[...this.rows]
        if (this.selectedRowKeys.indexOf(item.id)>=0) {
          this.rows.push(item)
        }
        else {
          this.rows = rows2.filter(item2 => item2.code !== item.code);
        }
      }
    },
    onSelect (record, selected) {
      // if (selected) {
      //   this.rows.push(record)
      // }
      // else {
      //   this.rows = this.rows.filter(item => item.code !== record.code);
      // }
    },
    onClose () {
      this.$emit('close')
      this.reset()
    },
    handleOk () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择供应商')
        return
      }
      const _rows=[...this.rows]
      console.info(_rows)
      this.$emit('ok', _rows)
      this.reset()
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
      this.rows=[]
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
      this.$get('scmDVendor/list', {
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

