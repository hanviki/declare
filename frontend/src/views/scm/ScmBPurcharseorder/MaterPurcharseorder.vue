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
                label="物料名称"
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
                label="物料编码"
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
                label="开始时间"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-date-picker
                  @change="onChange"
                  :defaultValue="start"
                />
              </a-form-item>
            </a-col>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="结束时间"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-date-picker
                  @change="onChange2"
                  :defaultValue="enddate"
                />
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
          v-hasPermission="['scmBSupplyPlan:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.id"
        :dataSource="dataSource"
        :pagination="pagination"
        :expandedRowKeys="expandedRowKeys"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
        @expand="expandSubGrid"
        :scroll="scroll"
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
            slot="operation"
            slot-scope="text, record2"
          >
            <a-icon
              type="setting"
              theme="twoTone"
              twoToneColor="#4a9ff5"
              v-show="record2.status==0 && record.status==1"
              @click="edit(record2,record)"
              title="修改"
            ></a-icon>
            <a-icon
              v-hasPermission="['scmBSupplyplan:delete']"
              type="delete"
              theme="twoTone"
              twoToneColor="#4a9ff5"
              v-show="record2.status==0 && record.status==1"
              @click="subDelete(record2)"
              title="删除"
            ></a-icon>
            <!-- <a-badge
              v-hasNoPermission="['scmBSupplyplan:update']"
              status="warning"
              text="无权限"
            ></a-badge>-->
          </template>
        </a-table>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <scmBSupplyplan-add
      ref="scmBPurcharseorderAdd"
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
      :price="price"
      :baseId="baseId"
      :amount="amount"
    >
    </scmBSupplyplan-add>
    <!-- 修改字典 -->
    <scmBSupplyplan-edit
      ref="scmBPurcharseorderEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
      :price="ePrice"
      :amount="eAmount"
    >
    </scmBSupplyplan-edit>
  </a-card>
</template>
<script>
import ScmBSupplyplanAdd from '../ScmBSupplyplan/MaterSupplyplanAdd'
import ScmBSupplyplanEdit from '../ScmBSupplyplan/MaterSupplyplanEdit'
import moment from 'moment'
import {mapState} from 'vuex'

export default {
  name: 'ScmBPurcharseorder',
  components: { ScmBSupplyplanAdd, ScmBSupplyplanEdit },
  data () {
    return {
      scroll: {
        x: 1500,
        y: window.innerHeight - 200 - 100 - 20 - (this.advanced ? 40 : 0)
      },
      dateFormat: 'YYYY-MM-DD',
      advanced: false,
      dataSource: [],
      innerData: [],
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
      queryParams: {
      },
      addVisiable: false,
      baseId: '',//采购订单的id
      editRecord: {},//编辑行
      addKey: '',//添加的订单id
      amount: 0,
      price: 0,
      eAmount: 0,
      ePrice: 0,
      editVisiable: false,
      loading: false,
      bordered: true
    }
  },
  computed: {
    ...mapState({
      user: state => state.account.user
    }),
    start () {
      return moment(this.defultDate_pre())
    },
    enddate () {
      return moment(this.defultDate())
    },
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '订单号',
        dataIndex: 'ebeln',
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'ebeln' && sortedInfo.order,
        width: 100
      }, {
        title: '项目号',
        dataIndex: 'ebelp',
        width: 60
      }, {
        title: '供应商账号',
        dataIndex: 'lifnr',
        width: 100
      }, {
        title: '物料号',
        dataIndex: 'matnr',
        width: 100
      }, {
        title: '物料描述',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '院区名称',
        dataIndex: 'werkst',
        width: 100
      }, {
        title: '库房名称',
        dataIndex: 'lgortName',
        width: 100
      }, {
        title: '订单数量',
        dataIndex: 'menge',
        width: 80
      }, {
        title: '计量单位',
        dataIndex: 'mseht',
        width: 80
      }, {
        title: '单价',
        dataIndex: 'netpr',
        width: 60
      }, {
        title: '交货日期',
        dataIndex: 'eindt',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 100
      }, {
        title: '订单日期',
        dataIndex: 'bedat',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 100
      }, {
        title: '状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">无效</a-tag>
            case 1:
              return <a-tag color="green">有效</a-tag>
            default:
              return text
          }
        },
        width: 80
      }, {
        title: '供应数量',
        dataIndex: 'allmenge',
        width: 80
      }, {
        title: '收货数量',
        dataIndex: 'suremenge'
      }]
    },
    innerColumns () {
      return [{
        title: '供应计划号',
        dataIndex: 'id'
      }, {
        title: '送货数量',
        dataIndex: 'gMenge'
      }, {
        title: '联系人',
        dataIndex: 'linkPerson'
      }, {
        title: '送达科室',
        dataIndex: 'sendDepart'
      }, {
        title: '联系方式',
        dataIndex: 'linkTelephone'
      }, {
        title: '发票号码',
        dataIndex: 'fphm'
      }, {
        title: '发票金额',
        dataIndex: 'fpjr'
      }, {
        title: '开票日期',
        dataIndex: 'fprq',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '商品条码',
        dataIndex: 'materCode'
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
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.comments = ''
      }
    },
    onChange (date, dateString) {
      this.queryParams.eindt = dateString
    },
    onChange2 (date, dateString) {
      this.queryParams.bedat = dateString
    },
    handleAddSuccess () {
      this.addVisiable = false
      this.$message.success('新增供应计划成功')
      // this.expandedRowKeys=[]
      //this.search()
      // const dataSource = [...this.dataSource]
      let row = this.dataSource.find(item => item.id === this.baseId);
      this.handleSubData(row)
    },
    handleAddClose () {
      this.addVisiable = false
    },
    add () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择采购订单')
        return
      }
      if (this.selectedRowKeys.length > 1) {
        this.$message.warning('请只选择一个采购订单')
        return
      }



      const dataSource = [...this.dataSource]
      this.baseId = this.selectedRowKeys[0]
      let row = dataSource.find(item => item.id === this.selectedRowKeys[0]);
      if (row.status == 0) {
        this.$message.warning('当前采购订单无效,请联系库房人员')
        return
      }
      this.price = row.netpr
      this.amount = row.menge - (row.allmenge == null ? 0 : row.allmenge)
      this.addVisiable = true
      let that = this
      setTimeout(function () {
        that.$refs.scmBPurcharseorderAdd.setFormValues(row)
      }, 200)

    },
    handleEditSuccess (baseId) {
      this.editVisiable = false
      this.$message.success('修改供应计划成功')
      //this.expandedRowKeys=[]
      //this.search()
      this.handleSubData(this.editRecord)
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record, pRecord) {
      this.$refs.scmBPurcharseorderEdit.setFormValues(record)
      this.$refs.scmBPurcharseorderEdit.setOrderFormValues(pRecord)
      this.editVisiable = true
      this.ePrice = pRecord.netpr
      this.eAmount = pRecord.menge - (pRecord.allmenge == null ? 0 : pRecord.allmenge) + record.gMenge
      this.editRecord = pRecord
    },
    subDelete (record, pRecord) {
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，此记录将会被彻底删除',
        centered: true,
        onOk () {
          that.$delete('scmBSupplyplan/' + record.id).then(() => {
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
        baseId: record.id
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
          let scmBPurcharseorderIds = that.selectedRowKeys.join(',')
          that.$delete('scmBPurcharseorder/' + scmBPurcharseorderIds).then(() => {
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
      this.$export('scmBPurcharseorder/excel', {
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
      if (params.eindt == null) {
        params.eindt = this.defultDate_pre()
      }
      if (params.bedat == null) {
        params.bedat = this.defultDate()
      }
      params.bsart = 1//物资
      params.lifnr=this.user.username//供应商账号
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
    },
    defultDate () {
      var date = new Date()
      var seperator1 = "-"
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var strDate = date.getDate()
      if (month >= 1 && month <= 9) {
        month = "0" + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate
      return currentdate
    },
    defultDate_pre () {
      var date = new Date()
      date.setDate(date.getDate() - 7)
      var seperator1 = "-"
      var year = date.getFullYear()
      var month = date.getMonth() + 1
      var strDate = date.getDate()
      if (month >= 1 && month <= 9) {
        month = "0" + month
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate
      }
      var currentdate = year + seperator1 + month + seperator1 + strDate
      return currentdate
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
