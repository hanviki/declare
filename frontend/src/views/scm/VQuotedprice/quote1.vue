<template>
  <a-card
    :bordered="true"
    class="card-area"
  >
    <div :class="null">
      <a-form :form="form">
        <div :class="'fold'">
          <a-row>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="供应商"
                :labelCol="{span: 6}"
                :wrapperCol="{span: 17, offset: 1}"
              >
                <a-input v-model="queryParams.keyword" placeholder="请输入供应商名称、编码" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="生产厂家"
                :labelCol="{span: 6}"
                :wrapperCol="{span: 17, offset: 1}"
              >
                <a-input v-model="queryParams.productName" placeholder="请输入生产厂家" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="医院名称"
                :labelCol="{span: 6}"
                :wrapperCol="{span: 17, offset: 1}"
              >
                <a-input v-model="queryParams.hospitalName" placeholder="请输入医院名称" />
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
        :scroll="{ x: 1200,y: 300 }"
      >

        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a @click="open(record.serverName)">{{text}}</a>
        </template>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'
//href="{{this.$baseUrl}}uploadFile/{{record.serverName}}"


export default {
  name: 'VQuotedprice1',
  data () {
    return {
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: {
        field:'create_Time desc,gysaccount ',
        order:'descend'
      },
      form: this.$form.createForm(this),
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
      temp: {}
    }
  },
  
  props: {
    oneVisiable: {
      default: false
    },
    baseId: 0
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '供应商账号',
        dataIndex: 'gysaccount',
        width: 100,
        customRender: (text, row, index) => {
          const obj = {
            children: text,
            attrs: {},
          };
          obj.attrs.rowSpan = this.mergeCells(text, this.dataSource, 'gysaccount');
          return obj;
        }
      }, {
        title: '供应商名称',
        dataIndex: 'gysname',
        width: 120,
        customRender: (text, row, index) => {
          const obj = {
            children: text,
            attrs: {},
          };
          obj.attrs.rowSpan = this.mergeCells(row.gysname, this.dataSource, 'gysname');
          return obj;
        }
      }, {
        title: '报价时间',
        dataIndex: 'createTime',
        customRender: (text, row, index) => {
          if (text != null) {
            return moment(text).format('YYYY-MM-DD')
          }
          else {
            return ''
          }
        },
        width: 100
      }, {
        title: '供货厂家',
        dataIndex: 'productName',
        width: 100
      }, {
        title: '报价价格',
        dataIndex: 'quotedPrice',
        width: 80
      }, {
        title: '可供应数量',
        dataIndex: 'supplyAmount',
        width: 100
      }, {
        title: '供货医院',
        dataIndex: 'hospitalName',
        width: 100
      }, {
        title: '报价医院价格',
        dataIndex: 'price',
        width: 120
      }, {
        title: '发票附件',
        scopedSlots: { customRender: 'operation' },
        dataIndex: 'clientName'
      }]
    }
  },
  mounted () {
    this.search()
  },
  watch: {
    oneVisiable () {
      if (this.oneVisiable) {
        this.search()
      }
    }
  },
  methods: {
    reset (){
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
      this.dataSource=[]
      this.temp = {}
      this.queryParams={}
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    exportExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.$export('vQuotedprice/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams
      })
    },
    mergeCells (text, array, columns) {
      let i = 0;
      if (text !== this.temp[columns]) {
        this.temp[columns] = text;
        array.forEach((item) => {
          if (item[columns] === this.temp[columns]) {
            i += 1;
          }
        });
      }
      return i;
    },
    open (serverName) {
      window.open(this.$baseUrl + 'uploadFile/' + serverName)
    },
    search () {
      console.info(2222)
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
      params.baseId = this.baseId
      this.$get('vQuotedprice', {
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
