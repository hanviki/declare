<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width="75%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="6">
          <a-form-item
            v-bind="formItemLayout"
            label="送货时间"
          >
            <a-date-picker v-decorator="[ 'sendDate', { rules: [{ required: true, message: '送货时间不能为空' }] }]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <werks-lgort
            ref="werklgort"
            @werks="setWerks"
            @lgort="setLgort"
          >
          </werks-lgort>
        </a-col>
        <a-col
          :span="5"
          :offset="1"
        >
          <a-input-search
            placeholder="输入供应计划号"
            v-model="queryParams.id"
            @search="search"
            enterButton="查询"
          />
        </a-col>
      </a-row>

    </a-form>
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
      :scroll="{ x: 1700 ,y:300 }"
    >
    </a-table>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'
import WerksLgort from '../../common/WerksLgort'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 }
}
export default {
  name: 'SendorderAdd',
  components: {WerksLgort},
  props: {
    addVisiable: {
      default: false
    }
  },
  watch: {
    addVisiable () {
      if (this.addVisiable) {
        this.fetch()
      }
    }
  },
  computed: {
    columns () {
      return [{
        title: '供应计划号',
        dataIndex: 'id',
        width: 130
      }, {
        title: '药品编码',
        dataIndex: 'matnr',
        width: 100
      }, {
        title: '药品名称',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '供应数量',
        dataIndex: 'gMenge',
        width: 80
      }, {
        title: '批号',
        dataIndex: 'charge',
        width: 80
      }, {
        title: '有效期',
        dataIndex: 'vfdat',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 100
      }, {
        title: '生产日期',
        dataIndex: 'hsdat',
        customRender: (text, row, index) => {
          if(text==null) return ''
          return moment(text).format('YYYY-MM-DD')
        },
        width: 100
      }, {
        title: '发票号码',
        dataIndex: 'fphm',
        width: 100
      }, {
        title: '发票金额',
        dataIndex: 'fpjr',
        width: 100
      }, {
        title: '院区',
        dataIndex: 'werkst',
        width: 120
      }, {
        title: '库房',
        dataIndex: 'lgortName',
        width: 80
      }, {
        title: '发票日期',
        dataIndex: 'fprq',
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
              return <a-tag color="purple">未收货</a-tag>
            case 1:
              return <a-tag color="green">已收货</a-tag>
            default:
              return text
          }
        },
        width: 80
      }, {
        title: '包装规格',
        dataIndex: 'pkgAmount',
        width: 80
      }, {
        title: '包装数量',
        dataIndex: 'pkgNumber'
      }]
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBSendorder: {},
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
      werks: '',
      lgort: '',
      rows: []
    }
  },
  methods: {
    onClose () {
      this.reset()
      this.$emit('close')
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
      const dataSource = [...this.dataSource];
      for (let item of dataSource) {
        const rows2 = [...this.rows]
        if (this.selectedRowKeys.indexOf(item.id) >= 0) {
          this.rows = rows2.filter(item2 => item2.id !== item.id);
          this.rows.push(item)
        }
        else {
          this.rows = rows2.filter(item2 => item2.id !== item.id);
        }
      }
    },
    setWerks (werks) {
      this.queryParams.werks = werks
    },
    setLgort (lgort) {
      this.queryParams.lgort = lgort
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
      this.loading = false
      this.scmBSendorder = {}
      this.rows = []
      this.form.resetFields()
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
      this.$refs.werklgort.reset()
      // this.fetch()
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
      params.bsartD = "0" // 物资单类型
      params.status = 0 // 为收货的数据
      this.$get('viewSupplyplan/sendOrder', {
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
    handleSubmit () {
      let supplyPlanIds = this.selectedRowKeys.join(",")
      this.scmBSendorder["supplyPlanIds"] = supplyPlanIds
      this.scmBSendorder["bsart"] = "0"
      var werks = ''
      var lgort = ''
      var msg = ''
      for (let item of this.rows) {
        if (werks == '') {
          werks = item.werks;
        }
        else {
          if (werks != item.werks) {
            msg += item.id + ":" + item.werks + "院区不一致"
          }
        }
        if (lgort == '') {
          lgort = item.lgort;
        }
        else {
          if (lgort != item.lgort) {
            msg += item.id + ":" + item.lgortName + "库房不一致"
          }
        }
      }
      if (msg != "") {
        this.$message.warning(msg);
        return
      }


      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('scmBSendorder/OrderAdd', {
            ...this.scmBSendorder
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    setFields () {
      let values = this.form.getFieldsValue(['sendDate'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBSendorder[_key] = values[_key] })
      }
    }
  }
}
</script>

