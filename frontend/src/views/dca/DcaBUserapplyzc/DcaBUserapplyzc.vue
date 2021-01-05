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
                label="申报年度"
                v-bind="formItemLayout"
              >
                <a-input v-model="queryParams.dcaYear" />
              </a-form-item>
            </a-col>
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
      <div class="operator">
        <a-button
          v-hasPermission="['dcaBUserapplyzc:add']"
          type="primary"
          ghost
          @click="add"
        >新增</a-button>
        <a-button
          v-hasPermission="['dcaBUserapplyzc:delete']"
          @click="batchDelete"
        >删除</a-button>
        <a-dropdown v-hasPermission="['dcaBUserapplyzc:export']">
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
        :rowKey="record => record. id                                                              "
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
            v-hasPermission="['dcaBUserapplyzc:update']"
            type="setting"
            theme="twoTone"
            v-show="record.state==2"
            twoToneColor="#4a9ff5"
            @click="edit(record)"
            title="修改"
          ></a-icon>
          <a-badge
            v-hasNoPermission="['dcaBUserapplyzc:update']"
            status="warning"
            text="无权限"
          ></a-badge>
        </template>
      </a-table>
    </div>
    <!-- 新增字典 -->
    <dcaBUserapplyzc-add
      @close="handleAddClose"
      @success="handleAddSuccess"
      :addVisiable="addVisiable"
    >
    </dcaBUserapplyzc-add>
    <!-- 修改字典 -->
    <dcaBUserapplyzc-edit
      ref="dcaBUserapplyzcEdit"
      @close="handleEditClose"
      @success="handleEditSuccess"
      :editVisiable="editVisiable"
    >
    </dcaBUserapplyzc-edit>
  </a-card>
</template>

<script>
import DcaBUserapplyzcAdd from './DcaBUserapplyzcAdd'
import DcaBUserapplyzcEdit from './DcaBUserapplyzcEdit'
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBUserapplyzc',
  components: { DcaBUserapplyzcAdd, DcaBUserapplyzcEdit },
  data () {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      sortedInfo: null,
      paginationInfo: null,
      formItemLayout,
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
      editVisiable: false,
      loading: false,
      bordered: true
    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [{
        title: '科室',
        dataIndex: 'ks',
        width: 100
      },
      {
        title: '申报年度',
        dataIndex: 'dcaYear',
        width: 100
      },

      {
        title: '性别',
        dataIndex: 'sexName',
        width: 100
      },
      {
        title: '出生年月',
        dataIndex: 'birthday',
        width: 100,
        customRender: (text, row, index) => {
          if (text == null) return ''
          return moment(text).format('YYYY-MM-DD')
        },
      },
     
      {
        title: '专业技术职务',
        dataIndex: 'zyjsgw',
        width: 100
      },
      {
        title: '专业技术职务聘任时间',
        dataIndex: 'appointedDate',
        width: 100
      },
      {
        title: '是否通过初级水平能力测试',
        dataIndex: 'isChujikh',
        width: 100,
        customRender: (text, row, index) => {
          if (text) return "是"
          return "否"
        }
      },
      {
        title: '通过初级水平能力测试时间',
        dataIndex: 'chujikhDate',
        width: 100,
        customRender: (text, row, index) => {
          if (text == null) return ''
          return moment(text).format('YYYY-MM-DD')
        },
      },
      {
        title: '是否通过中级水平能力测试',
        dataIndex: 'isZhongjikh',
        width: 100,
        customRender: (text, row, index) => {
          if (text) return "是"
          return "否"
        }
      },
      {
        title: '通过中级水平能力测试时间',
        dataIndex: 'zhongjikhDate',
        width: 100,
        customRender: (text, row, index) => {
          if (text == null) return ''
          return moment(text).format('YYYY-MM-DD')
        },
      },
      {
        title: '申请测试等级',
        dataIndex: 'gwdj',
        width: 80
      },
      {
        title: '是否补考',
        dataIndex: 'isBukao',
        width: 80,
        customRender: (text, row, index) => {
          if (text) return "是"
          return "否"
        }
      },
      {
        title: '补考科目',
        dataIndex: 'bukaokemu',
        width: 80
      },
     
      {
        title: '手机号',
        dataIndex: 'telephone',
        width: 100
      },
      {
        title: '论文',
        dataIndex: 'fileId',
        customRender: (text, row, index) => {
          if (text != null && text != '') {
            return <a href={row.fileUrl} target="_blank" ><a-icon type="search" /></a>
          }
          return ''
        },
        width: 80
      },
      {
        title: '住院医师规范化培训证书（或文件）',
        dataIndex: 'zcFileId',
        customRender: (text, row, index) => {
          if (text != null && text != '') {
            return <a href={row.zcFileUrl} target="_blank" ><a-icon type="search" /></a>
          }
          return ''
        },
        width: 120
      },
      {
        title: '状态',
        dataIndex: 'state',
        width: 80,
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag color="green">已提交</a-tag>
            case 2:
              return <a-tag color="red">已退回</a-tag>
            default:
              return text
          }
        }
      },
      {
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
      this.$message.success('新增成功')
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
      this.$message.success('修改成功')
      this.search()
    },
    handleEditClose () {
      this.editVisiable = false
    },
    edit (record) {
      this.$refs.dcaBUserapplyzcEdit.setFormValues(record)
      if (record.fileId != null && record.fileId != '') {
        this.$refs.dcaBUserapplyzcEdit.fetch(record.fileId)
      }
      if (record.zcFileId != null && record.zcFileId != '') {
        this.$refs.dcaBUserapplyzcEdit.fetch2(record.zcFileId)
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
          let dcaBUserapplyzcIds = that.selectedRowKeys.join(',')
          that.$delete('dcaBUserapplyzc/' + dcaBUserapplyzcIds).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          }
          )
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
      this.$export('dcaBUserapplyzc/excel', {
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
      this.$get('dcaBUserapplyzc/person', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
