<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <div :class="advanced ? 'search' : null">
      <a-form layout="horizontal">
        <a-row>
          <div :class="advanced ? null: 'fold'">
            <a-row>
              <a-col
                :md="8"
                :sm="24"
              >
                <a-form-item
                  label="申报年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.dcayear" />
                </a-form-item>
              </a-col>
              <a-col
                :md="6"
                :sm="24"
              >
                <a-form-item
                  label="投票组别"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.tpzb" />
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
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-row>
          <a-col :span="3">
            <a-upload
              accept=".xls,.xlsx"
              :fileList="fileList"
              :beforeUpload="beforeUpload"
              @change="handleChange"
            >
              <a-button>
                <a-icon type="upload" /> 上传信息
              </a-button>
            </a-upload>
          </a-col>
          <a-col :span="3">
            <a-button
              type="primary"
              ghost
              @click="exportExcel"
            >导出Excel</a-button>
          </a-col>
        </a-row>

      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record. id                                            "
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
      </a-table>
    </div>
  </a-card>
</template>

<script>


const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'OutBInfo',
  components: {},
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
      fileList: [],
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
      return [ {
        title: '申报年度',
        dataIndex: 'dcayear',
        width: 100
      },{
        title: '投票组别',
        dataIndex: 'tpzb',
        width: 100
      },
      {
        title: '对应职称',
        dataIndex: 'dyzc',
        width: 100
      },
      {
        title: '投票标题',
        dataIndex: 'tpbt',
        width: 100
      },
      {
        title: '序号',
        dataIndex: 'xuhao',
        width: 100
      },
      {
        title: '职工编码',
        dataIndex: 'zgbm',
        width: 100
      },
     
      {
        title: '科室',
        dataIndex: 'ks',
        width: 100
      },
      {
        title: '姓名',
        dataIndex: 'name',
        width: 100
      },
      {
        title: '申报职称',
        dataIndex: 'sbzc',
        width: 100
      },
      {
        title: '指标备注',
        dataIndex: 'zbbz',
        width: 100
      },
      {
        title: '总人数',
        dataIndex: 'totalnum',
        width: 100
      },
      {
        title: '学科组选出人数',
        dataIndex: 'xkznum',
        width: 100
      },
      {
        title: '医院评审委员会选出人数',
        dataIndex: 'hospitalnum',
        width: 100
      },
      {
        title: '申请类别',
        dataIndex: 'sqlb',
        width: 100
      },
      {
        title: '是否占指标',
        dataIndex: 'sfzzb',
        width: 100
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleChange (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    handleRemove (file) {
      this.fileList = []
    },
    beforeUpload (file) {
      console.info(file.type)
      const isJPG = (file.type === 'application/vnd.ms-excel' || file.type === 'application/x-excel' || file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet')
      //console.info(file.type)
      if (!isJPG) {
        this.$message.error('请只上传excel文件!')
      }
      const isLt2M = file.size / 1024 / 1024 < 4
      if (!isLt2M) {
        this.$message.error('附件必须小于 4MB!')
      }
      if (isJPG && isLt2M) {
        this.fileList = [...this.fileList, file]
      }
      return isJPG && isLt2M
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      // You can use any AJAX library you like
      this.$upload('outBInfo/importTp', formData).then((r) => {
        this.$message.success('上传成功.')
      }).catch(() => {
        this.$message.error('上传失败.')
      })
      this.fileList = []
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
      this.$refs.outBInfoEdit.setFormValues(record)
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
          let outBInfoIds = that.selectedRowKeys.join(',')
          that.$delete('outBInfo/' + outBInfoIds).then(() => {
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
      let json = this.columns
      let dataJson = JSON.stringify(json)
      this.$export('outBInfo/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson: dataJson,
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
      this.$get('outBInfo', {
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
