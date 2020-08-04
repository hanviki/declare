<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div ref="lodopDiv"></div>
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null: 'fold'">
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
                label="药品编码"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.matnr" />
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
            <template v-if="advanced">
              <a-col
                :md="6"
                :sm="24"
              >
                <a-form-item
                  label="状态"
                  :labelCol="{span: 8}"
                  :wrapperCol="{span: 15, offset: 1}"
                >
                  <a-select @change="handleChange">
                    <a-select-option
                      key="0"
                      value="0"
                    >未入库</a-select-option>
                    <a-select-option
                      key="1"
                      value="1"
                    >已入库</a-select-option>
                     <a-select-option
                      key="-1"
                      value="-1"
                    >全部</a-select-option>
                  </a-select>
                </a-form-item>
              </a-col>
            <a-col
              :md="6"
              :sm="24"
            >
              <a-form-item
                label="供应商"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.keyword_gys" />
              </a-form-item>
            </a-col>
            </template>

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
        </a-row>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
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
      </a-table>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'
import { mapState } from 'vuex'
import WerksLgort from '../../common/WerksLgort'

export default {
  name: 'ScmBSupplyplan',
  components: { WerksLgort },
  data () {
    return {
      scroll: {
        x: 3000,
        y: window.innerHeight - 200 - 100 - 20 - 5
      },
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
      printIds: '',
      printVisiable: false,
      lodop: {}
    }
  },
  computed: {
    ...mapState({
      user: state => state.account.user
    }),
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '供应计划号',
        dataIndex: 'id',
        width: 120,
        fixed: 'left'
      }, {
        title: '订单号',
        dataIndex: 'ebeln',
        width: 100,
        fixed: 'left'
      }, {
        title: '项目号',
        dataIndex: 'ebelp',
        width: 100
      }, {
        title: '物料编码',
        dataIndex: 'matnr',
        width: 120
      }, {
        title: '物料描述',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '供应商账号',
        dataIndex: 'gysaccount',
        width: 100
      }, {
        title: '供应商名称',
        dataIndex: 'gysname',
        width: 200
      }, {
        title: '院区名称',
        dataIndex: 'werkst',
        width: 150
      }, {
        title: '库房',
        dataIndex: 'lgortName',
        width: 100
      }, {
        title: '订单数量',
        dataIndex: 'menge',
        width: 100
      }, {
        title: '单位',
        dataIndex: 'mseht',
        width: 80
      }, {
        title: '单价',
        dataIndex: 'netpr',
        width: 80
      }, {
        title: '供应数量',
        dataIndex: 'gMenge',
        width: 100
      }, {
        title: '批号',
        dataIndex: 'charge',
        width: 120
      }, {
        title: '有效期',
        dataIndex: 'vfdat',
        width: 120,
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '生产日期',
        dataIndex: 'hsdat',
        width: 120,
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        }
      }, {
        title: '发票号码',
        dataIndex: 'fphm',
        width: 100
      }, {
        title: '发票金额',
        dataIndex: 'fpjr',
        width: 100
      }, {
        title: '发票日期',
        dataIndex: 'fprq',
        width: 120,
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
        },
        width: 100
      }, {
        title: '包装规格',
        dataIndex: 'pkgAmount',
        width: 100
      }, {
        title: '包装数量',
        dataIndex: 'pkgNumber',
        width: 100
      }, {
        title: '缺货原因',
        dataIndex: 'outCause'
      }, {
        title: '补货日期',
        dataIndex: 'outDate',
        width: 120,
        customRender: (text, row, index) => {
          if (text) {
            return moment(text).format('YYYY-MM-DD')
          }
          else {
            return ''
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
      if (value !== '-1') {
        this.queryParams.status = value
      } else {
        this.queryParams.status = ''
      }
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
    setWerks (werks) {
      this.queryParams.werks = werks
    },
    setLgort (lgort) {
      this.queryParams.lgort = lgort
    },
    print () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要打印的记录')
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
      this.lodop = null
      this.printVisiable = false
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('scmBSupplyplan/excel', {
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
      params.bsartD = "0"
      // params.gysaccount = this.user.username//供应商账号
      this.$get('viewSupplyplan', {
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
