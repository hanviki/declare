<template>
  <div style="position:relative;">
    <a-input
      v-model="issue_content"
      v-on:input="inputFunc"
      type="text"
      placeholder="请输入药品名称/编码"
    >
    </a-input>
    <div
      style="position:absolute;top:30;left:0;z-index:9999;background:#ECECEC;"
      class="serch_result"
      v-show="serch_result_issue"
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
        :scroll="{ x: 450 ,y:400 }"
      >
      </a-table>
    </div>
  </div>
</template>
<script>
export default {
  name: 'upfc',
  data () {
    return {
      issue_content: '', // 输入框中的内容
      isSelect: 0,
      matnr: '',
      materId: '',
      serch_result_issue: false, // 控制搜索的问题显示隐藏
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
    }
  },
  props: {
    murl: {
      default: 'scmDMater'
    }
  },
  methods: {
    // 监听输入事件，当input中有内容时，下面的搜索列表显示出来
    inputFunc () {
      if (this.isSelect === 0) {
        if (this.issue_content.length > 0) {
          this.serch_result_issue = true
          this.queryParams['keyWord'] = this.issue_content
          this.search()
        } else {
          this.serch_result_issue = false
        }
      } else {
        this.isSelect = 0
      }
    },
    onSelectChange (selectedRowKeys) {
      const dataSource = [...this.dataSource]
      let row = dataSource.find(item => item.id === selectedRowKeys[0])
      this.issue_content = `${row.matnr}_${row.txz01}`
      this.materId = row.id
      this.matnr = row.matnr
      this.isSelect = 1
      this.serch_result_issue = false
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
      this.issue_content = ''
      this.serch_result_issue = false
      // this.fetch()
    },
    setFormValue (txz01, matnr, materId) {
      this.isSelect = 1
      this.issue_content = txz01
      console.info(matnr)
      this.matnr = matnr
      this.materId = materId
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
      let url = this.murl === undefined ? 'scmDMater' : this.murl
      this.$get(url, {
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
  // 计算属性，当输入内容的时候下面的方法就会根据你输入的内容来过滤serch_result数组中的数据
  computed: {
    columns () {
      return [
        {
          title: '物料描述',
          dataIndex: 'txz01',
          width: 200
        },
        {
          title: '物料编码',
          dataIndex: 'matnr',
          width: 100
        },
        {
          title: '规格',
          dataIndex: 'spec',
          width: 100
        },
        {
          title: '生产厂家',
          dataIndex: 'produceArea',
          width: 80
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../static/less/Common";
</style>
