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
                label="姓名/人事编号"
                v-bind="formItemLayout"
              >
                <a-input v-model="queryParams.userAccount" />
              </a-form-item>
            </a-col>
            <a-col
              :md="8"
              :sm="24"
            >
              <a-form-item
                label="状态"
                v-bind="formItemLayout"
              >
               <a-select @change="handleChangeState">
                       <a-select-option
                      key="-1"
                      value="-1"
                    >全部</a-select-option>
                    <a-select-option
                      key="0"
                      value="0"
                    >未确认</a-select-option>
                    <a-select-option
                      key="1"
                      value="1"
                    >已确认</a-select-option>
                     <a-select-option
                      key="2"
                      value="2"
                    >已退回</a-select-option>
                  </a-select>
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
        :scroll="scroll"
      >
       <template
              slot="action"
              slot-scope="text, record"
            >
              <a-button
                type="dashed"
                block
                v-if="record.state==1"
                @click="handleAudit(record)"
              >
                退回
              </a-button>
            </template>
      </a-table>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBWorker',
  components: {  },
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
      bordered: true,
      scroll: {
        x: 3000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },

    }
  },
  computed: {
    columns () {
      let { sortedInfo } = this
      sortedInfo = sortedInfo || {}
      return [
         {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },{
        title: '博士学校',
        dataIndex: 'doctorSchool',
        width: 120
      },
      {
        title: '博士毕业时间',
        dataIndex: 'doctorDate',
        width: 100,
       
      },
      {
        title: '博士专业',
        dataIndex: 'doctorZy',
        width: 100
      },
      {
        title: '硕士学校',
        dataIndex: 'graduateSchool',
        width: 100
      },
      {
        title: '硕士毕业时间',
        dataIndex: 'graduateDate',
        width: 100,
        
      },
      {
        title: '硕士专业',
        dataIndex: 'graduateZy',
        width: 100
      },
      {
        title: '本科学校',
        dataIndex: 'bkSchool',
        width: 100
      },
      {
        title: '本科毕业时间',
        dataIndex: 'bkDate',
        width: 100,
         
      },
      {
        title: '本科专业',
        dataIndex: 'bkZy',
        width: 100
      },
      {
        title: '大专学校',
        dataIndex: 'dzSchool',
        width: 100
      },
      {
        title: '大专毕业时间',
        dataIndex: 'dzDate',
        width: 100,
        
      },
      {
        title: '大专专业',
        dataIndex: 'dzZy',
        width: 100
      },
      {
        title: '中专学校',
        dataIndex: 'zzSchool',
        width: 100
      },
      {
        title: '中专毕业时间',
        dataIndex: 'zzDate',
        width: 100,
        
      },
      {
        title: '中专专业',
        dataIndex: 'zzZy',
        width: 100
      },
      {
        title: '现任专业技术职务',
        dataIndex: 'xrzyzw'
      },
      {
        title: '聘任时间',
        dataIndex: 'prDate',
        width: 130,
        
      },
      {
        title: '职员类别',
        dataIndex: 'zylb',
        width: 100
      },
      {
        title: '现任职员职级',
        dataIndex: 'zrzyjb',
        width: 100
      },
      {
        title: '现任职员聘任时间',
        dataIndex: 'zrprDate',
        width: 130,
         
      },
      {
        title: '签订劳动合同时间',
        dataIndex: 'comployDate',
        width: 130,
        
      },
      {
        title: '确认时间',
        dataIndex: 'confirmDate',
        width: 100,
         customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
      },
      {
          title: '状态',
          dataIndex: 'state',
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
               case 0:
                return <a-tag color="purple">未确认</a-tag>
              case 1:
                return <a-tag color="green">已确认</a-tag>
              case 2:
                return <a-tag color="red">已退回</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '操作',
          dataIndex: 'action',
          width: 130,
          scopedSlots: { customRender: 'action' }
        }
    ]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
      if (!this.advanced) {
        this.queryParams.comments = ''
      }
    },
    handleChangeState (value) {
      if(value=="-1"){
        delete this.queryParams.state
      }
      else{
      this.queryParams.state= value
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
      this.$refs.dcaBWorkerEdit.setFormValues(record)
      this.editVisiable = true
    },
     handleAudit (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将退回',
        centered: true,
        onOk () {
          that.loading = true
          that.$delete('dcaBWorker/'+ record.id, {
            state: 2
          }).then(() => {
            //this.reset()
            that.$message.success('退回成功')
            that.search()
            // that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要提交的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定提交所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被提交',
        centered: true,
        onOk () {
          let dcaBWorkerIds = that.selectedRowKeys.join(',')
          that.$delete('dcaBWorker/' + dcaBWorkerIds,{state: 1}).then(() => {
            that.$message.success('提交成功')
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
      this.$export('dcaBWorker/excel', {
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
      this.$get('dcaBWorker', {
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
