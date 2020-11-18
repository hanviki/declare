<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="自任职以来科研获奖情况">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="发薪号/姓名"
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
                    label="初审状态"
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
                    >审核一待审核</a-select-option>
                    <a-select-option
                      key="1"
                      value="1"
                    >审核二待审核</a-select-option>
                     <a-select-option
                      key="2"
                      value="2"
                    >审核三待审核</a-select-option>
                    <a-select-option
                      key="3"
                      value="3"
                    >审核四待审核</a-select-option>
                    
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
                slot="spProjectName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'spProjectName')"
                    :value="record.spProjectName"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="srProjectGrade"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'srProjectGrade')"
                    :value="record.srProjectGrade"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="srProjectLevel"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'srProjectLevel')"
                    :value="record.srProjectLevel"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="srPrizeDept"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'srPrizeDept')"
                    :value="record.srPrizeDept"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="srPrizeDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'srPrizeDate')"
                  />
                </div>
              </template>
              <template
                slot="srPrizeRanknum"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'srPrizeRanknum')"
                    :value="record.srPrizeRanknum"
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
                slot="auditName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditName==null?'':record.auditName"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditName')"
                  >
                    <a-select-option value="国科进">
                      国科进
                    </a-select-option>
                    <a-select-option value="省科进">
                      省科进
                    </a-select-option>
                    <a-select-option value="省发明">
                      省发明
                    </a-select-option>
                    <a-select-option value="省推广">
                      省推广
                    </a-select-option>
                     <a-select-option value="省教学">
                      省教学
                    </a-select-option>
                    <a-select-option value="市科进">
                      市科进
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="auditGrade"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditGrade==null?'':record.auditGrade"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditGrade')"
                  >
                    <a-select-option value="一">
                      一
                    </a-select-option>
                    <a-select-option value="二">
                      二
                    </a-select-option>
                    <a-select-option value="三">
                      三
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="auditRank"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditRank==null?'':record.auditRank"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditRank')"
                  >
                    <a-select-option value="1">
                      1
                    </a-select-option>
                    <a-select-option value="2">
                      2
                    </a-select-option>
                    <a-select-option value="3">
                     3
                    </a-select-option>
                     <a-select-option value="4">
                     4
                    </a-select-option>
                    <a-select-option value="5">
                     5
                    </a-select-option>
                    <a-select-option value="6">
                     6
                    </a-select-option>
                  </a-select>
                </div>
              </template>
               <template
                slot="userAccount"
                slot-scope="text, record"
              >
                <a
                  href="#"
                  @click="showUserInfo(text)"
                >{{text}}</a>
              </template>
              <template
                slot="action"
                slot-scope="text, record"
              >
                <a-button
                  style="width:50%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleAuditNext(record)"
                >
                  科研处审核
                </a-button>
                <a-button
                  style="width:40%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleAudit(record)"
                >
                  通过
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
            :forceRender="true"
          >
            <dcaBScientificprize-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBScientificprize-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
            :forceRender="true"
          >
            <dcaBScientificprize-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBScientificprize-done>
          </a-tab-pane>
        </a-tabs>
      </a-card>
    </a-spin>
        <audit-userInfo
      ref="userinfo"
      @close="onCloseUserInfo"
      :visibleUserInfo="visibleUserInfo"
      :userAccount="userAccount"
    ></audit-userInfo>
  </div>
</template>

<script>
import moment from 'moment';
import DcaBScientificprizeDone from './DcaBScientificprizeDone'
import AuditUserInfo from '../../common/AuditUserInfo'

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
        x: 1800,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: ''
    }
  },
  components: { DcaBScientificprizeDone, AuditUserInfo },
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
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    
     onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
   
        this.selectedRowKeys = selectedRowKeys
    
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    handleChangeState(state) {
      this.queryParams.auditState =state
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
    handleAuditNext (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将进入下一个审核人',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBScientificprize/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: record.auditState 
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
            that.search()
            that.loading = false
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
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
          that.$post('dcaBScientificprize/updateNew', {
            jsonStr: jsonStr,
            state: 3,
            auditState: -1
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
            that.search()
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
          that.$post('dcaBScientificprize/updateNew', {
            jsonStr: jsonStr,
            state: 2,
            auditState: 0
          }).then(() => {
            //this.reset()
            that.$message.success('操作成功')
            that.search()
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
      this.$get('dcaBScientificprize/audit', {
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
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
          fixed: 'left'
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80,
          fixed: 'left'
        },
        {
          title: '获奖项目名称',
          dataIndex: 'spProjectName',
          width: 130,
          scopedSlots: { customRender: 'spProjectName' },
          fixed: 'left'
        },
        {
          title: '奖项级别',
          dataIndex: 'srProjectGrade',
          width: 130,
          scopedSlots: { customRender: 'srProjectGrade' },
          fixed: 'left'
        },
        {
          title: '奖项等级',
          dataIndex: 'srProjectLevel',
          width: 130,
          scopedSlots: { customRender: 'srProjectLevel' },
          fixed: 'left'
        },
        {
          title: '授奖部门',
          dataIndex: 'srPrizeDept',
          width: 130,
          scopedSlots: { customRender: 'srPrizeDept' },
          fixed: 'left'
        },
        {
          title: '批准年月',
          dataIndex: 'srPrizeDate',
          width: 130,
          scopedSlots: { customRender: 'srPrizeDate' },
          fixed: 'left'
        },
        {
          title: '本人排名',
          dataIndex: 'srPrizeRanknum',
          width: 130,
          scopedSlots: { customRender: 'srPrizeRanknum' }
        },
         {
          title: '名称',
          dataIndex: 'auditName',
          width: 130,
          scopedSlots: { customRender: 'auditName' },
          customHeaderCell: function () {
            return { style: { color: 'red' } }
          },
        },
        {
          title: '等级',
          dataIndex: 'auditGrade',
          width: 130,
          scopedSlots: { customRender: 'auditGrade' },
          customHeaderCell: function () {
            return { style: { color: 'red' } }
          },
        },
        {
          title: '排名',
          dataIndex: 'auditRank',
          width: 130,
          scopedSlots: { customRender: 'auditRank' },
          customHeaderCell: function () {
            return { style: { color: 'red' } }
          },
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
        },{
          title: '初审状态',
          dataIndex: 'auditState',
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">审核一待审核</a-tag>
              case 1:
                return <a-tag color="green">审核二待审核</a-tag>
              case 2:
                return <a-tag color="red">审核三待审核</a-tag>
              case 3:
                return <a-tag color="#f50">审核四待审核</a-tag>
                case 4:
                return <a-tag color="#f50">审核五待审核</a-tag>
                  case 5:
                return <a-tag color="#f50">审核六待审核</a-tag>
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
          width: 180
        }]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
