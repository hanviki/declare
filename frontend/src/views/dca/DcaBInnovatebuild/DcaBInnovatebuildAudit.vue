<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="改革及建设项目">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="发薪号"
                    v-bind="formItemLayout"
                  >
                    <a-input v-model="queryParams.userAccount" />
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
              </span>
            </a-row>
          </a-form>
        </div>
        <a-tabs
          type="card"
          @change="callback"
        >
          <a-tab-pane
            key="1"
            tab="待审核"
          >
            <a-table
              ref="TableInfo"
              :columns="columns"
              :data-source="dataSource"
              :rowKey="record => record.id"
              :pagination="pagination"
              @change="handleTableChange"
              :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
              :bordered="true"
              :scroll="scroll"
            >
              <template
                slot="projectName"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectName')"
                    :value="record.projectName"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="projectType"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectType')"
                    :value="record.projectType"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="projectSource"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'projectSource')"
                    :value="record.projectSource"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="contractFund"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'contractFund')"
                    :value="record.contractFund"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="realFund"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'realFund')"
                    :value="record.realFund"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="auditDate2"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'auditDate2')"
                  />
                </div>
              </template>
              <template
                slot="startDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'startDate')"
                  />
                </div>
              </template>
              <template
                slot="endDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'endDate')"
                  />
                </div>
              </template>
              <template
                slot="rankNum"
                slot-scope="textw, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'rankNum')"
                    :value="record.rankNum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="isUse"
                slot-scope="text, record"
              >
                <a-checkbox
                  @change="e => onIsUseChange(e,record,'isUse')"
                  :checked="text"
                ></a-checkbox>
              </template>
              <template
                slot="auditSuggestion"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'auditSuggestion')"
                    :value="record.auditSuggestion"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="isBest"
                slot-scope="text, record"
              >
                <div key="jzContent">

                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="(e1,f) => inputCheckChange(e1,f,record,'isBest')"
                    :checked="record.isBest=='是'"
                  >
                  </a-switch>
                </div>
              </template>
              <template
                slot="action"
                slot-scope="text, record"
              >
                <a-button
                  type="dashed"
                  block
                  @click="handleAudit(record)"
                >
                  通过审核
                </a-button>
                <a-button
                  type="danger"
                  block
                  @click="handleAuditNo(record)"
                >
                  审核不通过
                </a-button>
              </template>
            </a-table>
          </a-tab-pane>
          <a-tab-pane
            key="2"
            tab="已审核"
          >
            <dcaBInnovatebuild-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBInnovatebuild-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
          >
            <dcaBInnovatebuild-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBInnovatebuild-done>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import DcaBInnovatebuildDone from './DcaBInnovatebuildDone'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      advanced: false,
      dataSource: [],
      formItemLayout,
      selectedRowKeys: [],
      loading: false,
      dcaBParttimeVisiable: false,
      idNums: 10000,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      queryParams: {
        userAccount: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
    }
  },
  components: { DcaBInnovatebuildDone },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    callback () {

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
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
      this.freshTabs()
    },
    freshTabs () {
      this.$refs.TableInfo2.fetch(this.queryParams.userAccount)
      this.$refs.TableInfo3.fetch(this.queryParams.userAccount)
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
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows[0].state != 3) {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputCheckChange (blFlag, f, record, filedName) {
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAudit (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBInnovatebuild/updateNew', {
            jsonStr: jsonStr,
            state: 3
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
            that.fetch()
            that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAuditNo (record) {
      let that = this
      this.$confirm({
        title: '确定审核不通过此记录?',
        content: '当您点击确定按钮后，此记录将审核不通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBInnovatebuild/updateNew', {
            jsonStr: jsonStr,
            state: 2
          }).then(() => {
            //this.reset()
            that.$message.success('操作成功')
            that.fetch()
            that.freshTabs()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    fetch (params = {}) {
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
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      this.loading = true
      this.$get('dcaBInnovatebuild/audit', {
        ...params,
        state: 1
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '项目名称',
          dataIndex: 'projectName',
          width: 130,
          scopedSlots: { customRender: 'projectName' }
        },
        {
          title: '项目性质',
          dataIndex: 'projectType',
          width: 130,
          scopedSlots: { customRender: 'projectType' }
        },
        {
          title: '项目来源',
          dataIndex: 'projectSource',
          width: 130,
          scopedSlots: { customRender: 'projectSource' }
        },
        {
          title: '合同经费',
          dataIndex: 'contractFund',
          width: 130,
          scopedSlots: { customRender: 'contractFund' }
        },
        {
          title: '实到经费',
          dataIndex: 'realFund',
          width: 130,
          scopedSlots: { customRender: 'realFund' }
        },
        {
          title: '批准年月',
          dataIndex: 'auditDate2',
          width: 130,
          scopedSlots: { customRender: 'auditDate2' }
        },
        {
          title: '起始日期',
          dataIndex: 'startDate',
          width: 130,
          scopedSlots: { customRender: 'startDate' }
        },
        {
          title: '终止日期',
          dataIndex: 'endDate',
          width: 130,
          scopedSlots: { customRender: 'endDate' }
        },
        {
          title: '本人排名',
          dataIndex: 'rankNum',
          width: 130,
          scopedSlots: { customRender: 'rankNum' }
        },
        {
          title: '状态',
          dataIndex: 'state',
          width: 80,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>
              case 1:
                return <a-tag color="green">已提交</a-tag>
              case 2:
                return <a-tag color="red">审核未通过</a-tag>
              case 3:
                return <a-tag color="#f50">已审核</a-tag>
              default:
                return text
            }
          }
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          scopedSlots: { customRender: 'auditSuggestion' },
          width: 120
        },
        {
          title: '是否用于本次评审',
          dataIndex: 'isUse',
          scopedSlots: { customRender: 'isUse' },
          width: 80
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }, {
          title: '审核',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 100
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
