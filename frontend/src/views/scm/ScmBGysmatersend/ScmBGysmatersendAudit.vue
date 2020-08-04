<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null: 'fold'">
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="药品"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.keyword_mater" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="规格"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.spec" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}"
              >
                <a-select @change="handleChange">
                  <a-select-option
                    key="0"
                    value="0"
                  >未审核</a-select-option>
                  <a-select-option
                    key="1"
                    value="1"
                  >已审核</a-select-option>
                  <a-select-option
                    key="2"
                    value="2"
                  >已停用</a-select-option>
                  <a-select-option
                    key="4"
                    value="4"
                  >审核未通过</a-select-option>
                  <a-select-option
                    key="-1"
                    value="-1"
                  >全部</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <template v-if="advanced">
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="生产厂家"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.produceArea" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
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
        :scroll="{ x: 1200 }"
        :rowClassName="rowColor"
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
            type="eye"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="查看"
          ></a-icon>
        </template>
      </a-table>
    </div>
    <scmBGysMater-view
      ref="scmBGysmatersendEdit"
      @close="handleEditClose"
      @succ="handleEditSuccess"
      :editVisiable="editVisiable"
      :isShowsub="isShowsub"
    >
    </scmBGysMater-view>

  </a-card>
</template>

<script>

import moment from 'moment'
import ScmBGysMaterView from '../ScmBGysmatersend/ScmBGysMaterView'

export default {
  name: 'ScmBGysMaterPic',
  components: { ScmBGysMaterView },
  data () {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: {
        field: 'Create_TIME',
        order: 'descend'
      },
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
      isShowsub: true,
      loading: false,
      bordered: true
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '药品名称',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '规格',
        dataIndex: 'spec',
        width: 100
      }, {
        title: '生产厂家',
        dataIndex: 'produceArea',
        width: 100
      }, {
        title: '供应商名称',
        dataIndex: 'name'
      }, {
        title: '供应商账号',
        dataIndex: 'gysaccount',
        width: 100
      }, {
        title: '配送开始日期',
        dataIndex: 'sendStartTime',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 120
      }, {
        title: '配送结束日期',
        dataIndex: 'sendEndTime',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD')
        },
        width: 120
      }, {
        title: '未审核原因',
        dataIndex: 'auditCause',
        width: 100
      }, {
        title: '状态',
        dataIndex: 'state',
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未审核</a-tag>
            case 1:
              return <a-tag color="green">已审核</a-tag>
            case 2:
              return <a-tag color="red">已停用</a-tag>
            case 4:
              return <a-tag color="red">审核未通过</a-tag>
            default:
              return text
          }
        },
        fixed: 'right',
        width: 80
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' },
        fixed: 'right',
        width: 60
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    rowColor (row) {
      if (row.state == 1) {
        return 'green2'
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
    handleChange (value) {
      if (value !== '-1') {
        this.queryParams.state = value
      } else {
        this.queryParams.state = ''
      }
    },
    handleAddSuccess () {
      this.addVisiable = false
      this.$message.success('新增配送信息成功')
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
      // this.$message.success('修改配送成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.scmBGysmatersendEdit.setFormValues(record)
      if (record.state == 1) {
        this.isShowsub = false
      }
      else {
        this.isShowsub = true
      }
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
          const dataSource = [...that.dataSource]
          let IsValid = 0
          for (let key in that.selectedRowKeys) {
            let row = dataSource.find(item => item.id === that.selectedRowKeys[key])

            if (row.state == 1) {
              IsValid = 1
              that.$message.warning(`该${row.materId}_${row.charge}已经审核不能删除,请确认操作`)
            }

          }
          if (IsValid == 0) {
            let scmBGysmatersendIds = that.selectedRowKeys.join(',')
            that.$delete('scmBGysmatersend/' + scmBGysmatersendIds).then((data) => {
              if (data === undefined) {
                that.$message.success('删除成功')
              }
              else {
                that.$message.success(data)
              }

              that.selectedRowKeys = []
              that.search()
            })
          }
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
      this.$export('scmBGysmatersend/excel', {
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
      if (this.sortedInfo) {
        if (!params.sortField) {
          params.sortField = this.sortedInfo.field
          params.sortOrder = this.sortedInfo.order
        }
      }
      if (params.sortField == null) {
        params.sortField = "Create_TIME"
        params.sortOrder = "descend"
      }
      this.$get('scmBGysmatersend/audit', {
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

