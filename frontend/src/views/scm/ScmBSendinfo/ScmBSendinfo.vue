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
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="名称"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.name" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="编码"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.code" />
              </a-form-item>
            </a-col>
          </a-row>
          <a-row v-if="advanced">
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="备注"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-input v-model="queryParams.comments" />
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
          v-hasPermission="['scmBSendinfo:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          v-hasPermission="['scmBSendinfo:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-button
          type="primary"
          ghost
          @click="print"
        >打印</a-button>
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
        <template
          slot="operation"
          slot-scope="text, record"
        >
          <a-icon
            v-hasPermission="['scmBSendinfo:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            v-show="record.gyjh==null?true:false"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['scmBSendinfo:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <scmBSendinfo-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </scmBSendinfo-add>
    <!-- 修改字典 -->
    <scmBSendinfo-edit
      ref="scmBSendinfoEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </scmBSendinfo-edit>
    <!-- 打印送货单 -->
    <sendInfo-print
      ref="sendinfoPrint"
      @close="handlePrintClose"
      :printVisiable="printVisiable"
      :ids="printIds"
    >
    </sendInfo-print>
  </a-card>
</template>

<script>
import ScmBSendinfoAdd from './ScmBSendinfoAdd'
import ScmBSendinfoEdit from './ScmBSendinfoEdit'
import SendInfoPrint from './SendInfoPrint'

export default {
  name: 'ScmBSendinfo',
  components: { ScmBSendinfoAdd, ScmBSendinfoEdit, SendInfoPrint },
  data () {
    return {
      scroll: {
        x: 1600,
        y: window.innerHeight - 200 - 100 - 20 - 15
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
      printVisiable: false,
      loading: false,
      bordered: true,
      printIds: ''
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
        width:200
      }, {
        title: '送货数量',
        dataIndex: 'amount',
        width:80
      }, {
        title: '单价',
        dataIndex: 'price',
        width:80
      }, {
        title: '送货金额',
        dataIndex: 'money',
        width:80
      }, {
        title: '计量单位',
        dataIndex: 'mseht',
        width:80
      }, {
        title: '联系人',
        dataIndex: 'linkPerson',
        width:120
      }, {
        title: '院区',
        dataIndex: 'werkst',
        width:150
      }, {
        title: '送达科室',
        dataIndex: 'sendDepart',
        width:120
      }, {
        title: '联系方式',
        dataIndex: 'linkTelephone',
        width:120
      }, {
        title: '商品条码',
        dataIndex: 'materCode'
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
    handleAddSuccess () {
      this.addVisiable = false
      this.$message.success('新增字典成功')
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
      const dataSource = [...this.dataSource]
      let depart = "";
      let flag = 0
      this.selectedRowKeys.forEach(el => {
        let row = dataSource.find(item => item.id === el)
        if (depart === "") {
          depart = row.sendDepart
        }
        else {
          if (depart !== row.sendDepart) {
            this.$message.warning('所选打印送货单的送货科室必须一致！！！')
            flag = 1
            return
          }
        }

      });
      if (flag == 0) {
        this.printIds = this.selectedRowKeys.join(',')
        this.printVisiable = true
      }
    },
    handleEditSuccess () {
      this.editVisiable = false
      this.$message.success('修改字典成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    handlePrintClose () {
      this.printVisiable = false
    },
    edit (record) {
      this.$refs.scmBSendinfoEdit.setFormValues(record)
      this.editVisiable = true
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
          let scmBSendinfoIds = that.selectedRowKeys.join(',')
          that.$delete('scmBSendinfo/' + scmBSendinfoIds).then(() => {
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
      this.$export('scmBSendinfo/excel', {
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
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
