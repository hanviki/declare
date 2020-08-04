<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div>
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="发票号码"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.fphm" />
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
      <div class="operator">
        <a-button
          v-hasPermission="['scmBSendorder:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          type="primary"
          ghost
          @click="print"
        >打印送货清单</a-button>
        <a-button
          v-hasPermission="['scmBSendorder:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-dropdown v-hasPermission="['scmBSendorder:export']">
          <a-menu slot="overlay">
            <a-menu-item
              key="export-data"
              @click="exportExcel"
            >导出Excel</a-menu-item>
          </a-menu>
          <a-button>
            更多操作
            <a-icon type="down" />
          </a-button>
        </a-dropdown>
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
        :expandedRowKeys="expandedRowKeys"
        @expand="expandSubGrid"
      >
        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            v-hasPermission="['scmBSendorder:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['scmBSendorder:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
        <a-table
          ref="subTable"
          slot="expandedRowRender"
          slot-scope="record"
          :columns="innerColumns"
          :dataSource="record.innerData"
          :pagination="false"
          :rowKey="record2 => record2.id"
        >
          <template
            slot="operation2"
            slot-scope="text, record2"
          >
            <a-icon
              type="delete"
              theme="twoTone"
              twoToneColor="#4a9ff5"
              @click="subDelete(record2)"
              title="删除"
            ></a-icon>
          </template>
        </a-table>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <scmBSendorder-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </scmBSendorder-add>
    <!-- 修改字典 -->
    <scmBSendorder-edit
      ref="scmBSendorderEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
      :fp="fph"
    >
    </scmBSendorder-edit>
    <!-- 打印送货清单 -->
    <sendOrder-print
      ref="sendinfoPrint"
      @close="handlePrintClose"
      :printVisiable="printVisiable"
      :ids="printIds"
      :bsart="bart"
    >
    </sendOrder-print>
  </a-card>
</template>

<script>
import ScmBSendorderAdd from './ScmBSendorderAdd'
import ScmBSendorderEdit from './ScmBSendorderEdit'
import SendOrderPrint from './SendOrderPrint'
import moment from 'moment'

export default {
  name: 'ScmBSendorder',
  components: { ScmBSendorderAdd, ScmBSendorderEdit, SendOrderPrint },
  data () {
    return {
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 30
      },
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      expandedRowKeys: [],
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
      fph: '',
      printIds: '',
      printVisiable: false,
      bart: ''
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '送货清单号',
        dataIndex: 'id'
      }, {
        title: '发票号码',
        dataIndex: 'fphm'
      }, {
        title: '发票金额',
        dataIndex: 'fpjr'
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }]
    },
    innerColumns () {
      return [{
        title: '供应计划号',
        dataIndex: 'id',
        width: 100
      }, {
        title: '送货数量',
        dataIndex: 'gMenge',
        width: 80
      }, {
        title: '联系人',
        dataIndex: 'linkPerson',
        width: 100
      }, {
        title: '送达科室',
        dataIndex: 'sendDepart',
        width: 100
      }, {
        title: '联系方式',
        dataIndex: 'linkTelephone',
        width: 120
      }, {
        title: '发票号码',
        dataIndex: 'fphm',
        width: 100
      }, {
        title: '发票金额',
        dataIndex: 'fpjr',
        width: 80
      }, {
        title: '开票日期',
        dataIndex: 'fprq',
        width: 100,
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '商品条码',
        dataIndex: 'materCode',
        width: 100
      }, {
        title: '状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未收货</a-tag>
            case 1:
              return <a-tag color="green">已收货</a-tag>
            default:
              return text
          }
        },
        width: 80
      }, {
        title: '操作',
        dataIndex: 'operation2',
        scopedSlots: { customRender: 'operation2' }
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
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
      this.addVisiable = true
    },
    print () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要打印的记录')
        return
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning('请选择一条需要打印的记录')
        return
      }
      this.bart = '1'
      this.printIds = this.selectedRowKeys.join(',')
      this.printVisiable = true
    },
    handlePrintClose () {
      this.printVisiable = false
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.$message.success('修改成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      let that = this
      this.editVisiable = true
      this.fph = record.fphm
      setTimeout(function () {
        that.$refs.scmBSendorderEdit.setFormValues(record.fphm, record.id)
      }, 100);
    },
    subDelete (record, pRecord) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，此记录将会被彻底删除',
        centered: true,
        onOk () {
          that.$delete('scmBSupplyplan/deleteSendOrder/' + record.id).then(() => {
            that.$message.success('删除成功')
            that.search()
            that.expandedRowKeys = []
          })
        },
        onCancel () {

        }
      })
    },
    expandSubGrid (expanded, record) {//获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(record.id)
        this.handleSubData(record) //获取子表数据
      } else {
        let expandedRowKeys = this.expandedRowKeys.filter(RowKey => RowKey !== record.id)
        this.expandedRowKeys = expandedRowKeys
      }
    },
    handleSubData (record) {
      this.loading = true
      this.$get('scmBSupplyplan', {
        sendOrderCode: record.id
      }).then((r) => {
        let data = r.data
        this.loading = false
        record.innerData = data.rows
      })
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
          let scmBSendorderIds = that.selectedRowKeys.join(',')
          that.$delete('scmBSendorder/' + scmBSendorderIds).then(() => {
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
      this.$export('scmBSendorder/excel', {
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
      this.expandedRowKeys = [] //合并所有展开子项
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
      params.bsart = "1"
      this.$get('scmBSendorder', {
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
