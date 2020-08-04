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
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="药品名称"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.txz01" />
              </a-form-item>
            </a-col>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="药品名称"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.matnr" />
              </a-form-item>
            </a-col>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="批次号"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.charg" />
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
          v-hasPermission="['scmBSalereturn:fpSubmit']"
          @click="batchFpSubmit"
        >发票已开</a-button>
         <a-button
          v-hasPermission="['scmBSalereturn:audit']"
          @click="batchAudit"
        >发票已开</a-button>
      </div>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="{ x: 1500 }"
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
      </a-table>
    </div>

  </a-card>
</template>

<script>

export default {
  name: 'ScmBSalereturn',
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
      bordered: true
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '物料描述',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '物料编码',
        dataIndex: 'matnr',
        width: 80
      }, {
        title: '供应商账号',
        dataIndex: 'gysaccount',
        width: 100
      }, {
        title: '供应商名',
        dataIndex: 'gysname',
        width: 150
      }, {
        title: '批次号',
        dataIndex: 'charg',
        width: 80
      }, {
        title: '退货数量',
        dataIndex: 'amount',
        width: 80
      }, {
        title: '规格',
        dataIndex: 'spec',
        width: 120
      }, {
        title: '拼音码',
        dataIndex: 'spellCode',
        width: 80
      }, {
        title: '生产厂家',
        dataIndex: 'produceArea',
        width: 100
      }, {
        title: '退货原因',
        dataIndex: 'saleCause',
        width: 100
      }, {
        title: '部门名称',
        dataIndex: 'deptIdName'
      }, {
        title: '状态',
        dataIndex: 'state',
        width: 80,
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="red">未提交</a-tag>
            case 1:
              return <a-tag color="purple">已提交</a-tag>
            case 2:
              return <a-tag color="green">已审核</a-tag>
            case 3:
              return <a-tag color="blue">供应商确认</a-tag>
            case 4:
              return <a-tag color="orange">退回确认</a-tag>
            default:
              return text
          }
        }
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleChange (value) {
      this.queryParams.state = value
    },
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
      this.$message.success('新增退货成功')
      this.search()
    },
    handleAddClose () {
      this.addVisiable = false
    },
    add () {
      this.addVisiable = true
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.$message.success('修改退货成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.scmBSalereturnEdit.setFormValues(record)
      this.editVisiable = true
    },
    batchFpSubmit () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要开发票的记录')
        return
      }
      const dataSource = [...this.dataSource]
      let flag = 0
      //   this.selectedRowKeys.forEach(el => {
      //     let row = dataSource.find(item => item.id === el)
      //     if (row.state != 4) {
      //       this.$message.warning('只有未提交数据可以提交！！！')
      //       flag = 1
      //       return
      //     }
      //   });
      if (flag == 0) {
        let that = this
        this.$confirm({
          title: '确定提交所选中的记录?',
          content: '当您点击确定按钮后，这些记录将会不能编辑',
          centered: true,
          onOk () {
            let scmBSalereturnIds = that.selectedRowKeys.join(',')
            that.$delete('scmBSalereturn/fpsubmit/' + scmBSalereturnIds).then(() => {
              that.$message.success('提交成功')
              that.selectedRowKeys = []
              that.search()
            })
          },
          onCancel () {
            that.selectedRowKeys = []
          }
        })
      }
    },
    batchAudit () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要退回确认的记录')
        return
      }
      const dataSource = [...this.dataSource]
      let flag = 0
      this.selectedRowKeys.forEach(el => {
        let row = dataSource.find(item => item.id === el)
        if (row.state != 2) {
          this.$message.warning('尚不能进行此操作！！！')
          flag = 1
          return
        }
      });
      if (flag == 0) {
        let that = this
        this.$confirm({
          title: '确定提交所选中的记录?',
          content: '当您点击确定按钮后，这些记录将会处理',
          centered: true,
          onOk () {
            let scmBSalereturnIds = that.selectedRowKeys.join(',')
            that.$delete('scmBSalereturn/audit/' + scmBSalereturnIds).then(() => {
              that.$message.success('审核成功')
              that.selectedRowKeys = []
              that.search()
            })
          },
          onCancel () {
            that.selectedRowKeys = []
          }
        })
      }
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
          let scmBSalereturnIds = that.selectedRowKeys.join(',')
          that.$delete('scmBSalereturn/' + scmBSalereturnIds).then(() => {
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
      this.$export('scmBSalereturn/excel', {
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
      this.$get('scmBSalereturn/setting/4', {
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
