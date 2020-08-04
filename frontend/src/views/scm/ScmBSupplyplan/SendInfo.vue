<template>
  <a-modal
    title="送货单"
    v-model="sendVisiable"
    @ok="handleOk"
    @cancel="handleCancel"
    :width="900"
  >
    <a-input-search placeholder="请输入供应计划号" v-model="queryParams.id" @search="search" enterButton />
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
      :scroll="{ x: 1500 ,y: 200 }"
    >
    </a-table>
  </a-modal>
</template>

<script>
export default {
  props: {
    sendVisiable: {
      default: false
    }
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
    handleOk (e) {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择送货单')
        return
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning('请只选择一个送货单')
        return
      }
      const dataSource = [...this.dataSource]
      let row = dataSource.find(item => item.id === this.selectedRowKeys[0]);
      this.reset()
      this.$emit("sucess", row);//赋值 并关闭当前
    },
    handleCancel (e) {
      console.log(e);
      this.$emit("close");
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    search () {
      this.selectedRowKeys=[] //搜索时候清空选择项
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
      this.$get('scmBSendinfo', {
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
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '送货单号',
        dataIndex: 'id',
        width:100
      }, {
        title: '物料编码',
        dataIndex: 'matnr',
        width:100
      }, {
        title: '物料描述',
        dataIndex: 'txz01',
        width:150
      }, {
        title: '送货数量',
        dataIndex: 'amount',
        width:100
      }, {
        title: '单价',
        dataIndex: 'price',
        width:80
      }, {
        title: '送货金额',
        dataIndex: 'money',
        width:100
      }, {
        title: '计量单位',
        dataIndex: 'mseht',
        width:100
      }, {
        title: '联系人',
        dataIndex: 'linkPerson',
        width:100
      }, {
        title: '院区',
        dataIndex: 'werkst',
        width:150
      }, {
        title: '送达科室',
        dataIndex: 'sendDepart',
        width:150
      }, {
        title: '联系方式',
        dataIndex: 'linkTelephone',
        width:150
      }, {
        title: '商品条码',
        dataIndex: 'materCode'
      }]
    }
  },
   watch: {
     sendVisiable () {
        if(this.sendVisiable)
        {
          this.fetch()
        }
     }
   }
}
</script>

<style>
</style>