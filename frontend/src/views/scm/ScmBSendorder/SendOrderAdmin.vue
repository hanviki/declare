<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div ref="lodopDiv"></div>
    <div>
      <a-form layout="horizontal">
        <div :class="advanced ? null: 'fold'">
          <a-row>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="送货单号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.id" />
              </a-form-item>
            </a-col>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="供应商"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.fphm" />
              </a-form-item>
            </a-col>
            <a-col
              :md="12"
              :sm="24"
            >
              <werks-lgort
                ref="werklgort"
                @werks="setWerks"
                @lgort="setLgort"
              >
              </werks-lgort>
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
          v-hasPermission="['sendorder:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          v-hasPermission="['sendorder:add']"
          type="primary"
          ghost
          @click="print"
        >打印送货清单</a-button>
        <a-button
          v-hasPermission="['sendorder:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-dropdown v-hasPermission="['sendorder:export']">
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
            v-hasPermission="['sendorder:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['sendorder:update']"
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
      :orderId="orderId"
    >
    </scmBSendorder-edit>
    <!-- 打印送货清单 -->
    <sendOrder-print
      ref="sendinfoPrint"
      @close="handlePrintClose"
      :printVisiable="printVisiable"
      :ids="printIds"
      bsart="0"
      :lodop="lodop"
    >
    </sendOrder-print>
  </a-card>
</template>

<script>
import ScmBSendorderAdd from './SendOrderAdd'
import ScmBSendorderEdit from './SendOrderEdit'
import SendOrderPrint from './SendOrderPrint'
import WerksLgort from '../../common/WerksLgort'
import moment from 'moment'
import { getLodop, getLodopDiv } from '../../../tools/lodop'

export default {
  name: 'Sendorder',
  components: { ScmBSendorderAdd, ScmBSendorderEdit, SendOrderPrint, WerksLgort },
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
      orderId: '',
      lodop: {}
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
        title: '供应商账号',
        dataIndex: 'gysaccount'
      }, {
        title: '供应商名称',
        dataIndex: 'gysname'
      }, {
        title: '院区',
        dataIndex: 'werkst'
      }, {
        title: '库房',
        dataIndex: 'lgortname'
      }, {
        title: '送货日期',
        dataIndex: 'sendDate',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'sendDate' && sortedInfo.order
      }]
    },
    innerColumns () {
      return [{
        title: '供应计划号',
        dataIndex: 'id'
      },
       {
        title: '供应数量',
        dataIndex: 'gMenge'
      }, {
        title: '批号',
        dataIndex: 'charge'
      }, {
        title: '有效期',
        dataIndex: 'vfdat',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '生产日期',
        dataIndex: 'hsdat',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '发票号码',
        dataIndex: 'fphm'
      }, {
        title: '发票金额',
        dataIndex: 'fpjr'
      }, {
        title: '发票日期',
        dataIndex: 'fprq',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未入库</a-tag>
            case 1:
              return <a-tag color="green">已入库</a-tag>
            default:
              return text
          }
        }
      }, {
        title: '预收数量',
        dataIndex: 'doneMenge'
      }, {
        title: '包装规格',
        dataIndex: 'pkgAmount'
      }, {
        title: '包装数量',
        dataIndex: 'pkgNumber'
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
    setWerks (werks) {
      this.queryParams.werks = werks
    },
    setLgort (lgort) {
      this.queryParams.lgort = lgort
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

      this.printIds = this.selectedRowKeys.join(',')
      getLodopDiv(this.$refs.lodopDiv)
      if (getLodop() == undefined || getLodop() == null) {

      }
      else {
        this.lodop = getLodop();
        this.printVisiable = true
      }
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
      this.orderId = record.id
      this.editVisiable = true
      setTimeout(function () {
        that.$refs.scmBSendorderEdit.setFormValues(record.sendDate, record.id)
      }, 100);
    },
    subDelete (record, pRecord) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，此记录将会被彻底删除',
        centered: true,
        onOk () {
          that.$delete('scmBSupplyplan/deleteSendOrder2/' + record.id).then((r) => {
            console.log(r)
            if (r.data == null) {
              that.$message.success('删除成功')
            }
            else {
              that.$message.success(r.data.data)
            }
            that.search()
            that.expandedRowKeys = []
          })
        },
        onCancel () {

        }
      })
    },
    expandSubGrid (expanded, record) { // 获取供应计划的数量
      if (expanded) {
        this.expandedRowKeys.push(record.id)
        this.handleSubData(record) // 获取子表数据
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
      params.bsart = "0"
      if (params.sortField == null) {
        params.sortField = "ID"
        params.sortOrder = "descend"
      }
      this.$get('scmBSendorder/admin', {
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
