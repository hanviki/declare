<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="18">
          <a-form-item
            v-bind="formItemLayout"
            label="发票号码"
          >
            <a-input
              placeholder="请输入发票号码"
              v-decorator="['fphm', { rules: [{ required: true, message: '发票号码不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col
          :span="4"
          :offset="1"
        >
          <a-button
            @click="search"
            type="primary"
          >查询</a-button>
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
      :scroll="{ x: 1200 ,y:300 }"
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
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ScmBSendorderAdd',
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
        width:120
      }, {
        title: '送货数量',
        dataIndex: 'gMenge',
        width:100
      }, {
        title: '联系人',
        dataIndex: 'linkPerson',
        width:100
      }, {
        title: '送达科室',
        dataIndex: 'sendDepart',
        width:100
      }, {
        title: '联系方式',
        dataIndex: 'linkTelephone',
        width:120
      }, {
        title: '发票号码',
        dataIndex: 'fphm',
        width:120
      }, {
        title: '发票金额',
        dataIndex: 'fpjr',
        width:100
      }, {
        title: '开票日期',
        dataIndex: 'fprq',
        width:100
      }, {
        title: '商品条码',
        dataIndex: 'materCode',
        width:100
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
        }
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
      bordered: true
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmBSendorder = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
      //   const dataSource = [...this.dataSource]
      //   let row = dataSource.find(item => item.id === selectedRowKeys[0])
      //   this.issue_content = `${row.matnr}`
      //   this.matnr = row.matnr
      //   this.isSelect = 1
      //   this.serch_result_issue = false
      //   this.$emit("sucess", { maktx: row.maktx, meins: row.meins, mseht: row.mseht })
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder

      let fphm = this.form.getFieldValue('fphm');
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.queryParams.fphm=fphm
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

      //this.fetch()
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
      params.bsartD = "1"//物资单类型
      this.$get('scmBSupplyplan/sendOrder', {
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
      let supplyPlanIds=this.selectedRowKeys.join(",")
      this.scmBSendorder["supplyPlanIds"]=supplyPlanIds
      this.scmBSendorder["bsart"]="1"
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('scmBSendorder', {
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
      let values = this.form.getFieldsValue(['fphm'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBSendorder[_key] = values[_key] })
      }
    }
  }
}
</script>
