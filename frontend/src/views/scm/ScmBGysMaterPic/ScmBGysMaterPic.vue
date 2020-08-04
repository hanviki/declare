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
                label="药品名称"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-input v-model="queryParams.txz01" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
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
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="状态"
                :labelCol="{span: 8}"
                :wrapperCol="{span: 15, offset: 1}"
              >
                <a-select
                  defaultValue="全部"
                  v-model="queryParams.state"
                  style="width: 100%"
                >
                  <a-select-option value="-1">全部</a-select-option>
                  <a-select-option value="0">未审核</a-select-option>
                  <a-select-option value="1">已审核</a-select-option>
                  <a-select-option value="2">审核未成功</a-select-option>
                </a-select>
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
          v-hasPermission="['scmBGysMaterPic:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          v-hasPermission="['scmBGysMaterPic:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-dropdown v-hasPermission="['scmBGysMaterPic:export']">
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
        :scroll="{ x: 900 }"
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
            v-hasPermission="['scmBGysMaterPic:update']"
            type="setting"
            theme="twoTone"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['scmBGysMaterPic:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
      </a-table>
    </div>
    <!-- 新增资质文件 -->
    <scmBGysMaterPic-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </scmBGysMaterPic-add>
    <!-- 修改资质文件 -->
    <scmBGysMaterPic-edit
      ref="scmBGysMaterPicEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
      :isShowsub="isShowsub"
    >
    </scmBGysMaterPic-edit>
  </a-card>
</template>

<script>
import ScmBGysMaterPicAdd from './ScmBGysMaterPicAdd'
import ScmBGysMaterPicEdit from './ScmBGysMaterPicEdit'

export default {
  name: 'ScmBGysMaterPic',
  components: { ScmBGysMaterPicAdd, ScmBGysMaterPicEdit },
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
        title: '药品编码',
        dataIndex: 'matnr',
        width: 100
      }, {
        title: '药品名称',
        dataIndex: 'txz01',
        width: 200
      }, {
        title: '批次号',
        dataIndex: 'charge',
        width: 100
      }, {
        title: '审核原因',
        dataIndex: 'auditCause'

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
              return <a-tag color="red">审核未通过</a-tag>
            default:
              return text
          }
        },
        width: 150
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
      this.$message.success('新增检验报告成功')
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
      this.$message.success('修改检验报告成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.scmBGysMaterPicEdit.setFormValues(record)
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
            let scmBGysMaterPicIds = that.selectedRowKeys.join(',')
            that.$delete('scmBGysMaterPic/' + scmBGysMaterPicIds).then((data) => {
              if(data===undefined)
              {
                that.$message.success('删除成功')
              }
              else{
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
      this.$export('scmBGysMaterPic/excel', {
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
      this.$get('scmBGysMaterPic', {
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
