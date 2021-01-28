<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="任现职以来发表的论文">
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
                 
              </div>
              <span style="float: right; margin-top: 3px;">
                 <a-button
                  type="primary"
                  @click="exportCustomExcel"
                >导出</a-button>
                <a-button
                  type="primary"
                  @click="search2"
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
              :rowClassName="setRowClassName"
              :scroll="scroll"
            >
              <template
                slot="paperName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="jzContent">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'paperName')"
                      :value="record.paperName"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="journalName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="jzContent">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'journalName')"
                      :value="record.journalName"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="journalCode"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="jzContent">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'journalCode')"
                      :value="record.journalCode"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="paperPublishdate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'paperPublishdate')"
                  />
                </div>
              </template>
              <template
                slot="paperShoulu"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="jzContent">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'paperShoulu')"
                      :value="record.paperShoulu"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="paperCause"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="jzContent">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'paperCause')"
                      :value="record.paperCause"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="djzz"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <div key="djzz">
                    <a-textarea
                      @blur="e => inputChange(e.target.value,record,'djzz')"
                      :value="record.djzz"
                    >
                    </a-textarea>
                  </div>
                </div>
              </template>
              <template
                slot="qkjb"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.qkjb==null?'':record.qkjb"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'qkjb')"
                  >
                    <a-select-option value="A">
                      A
                    </a-select-option>
                    <a-select-option value="B">
                      B
                    </a-select-option>
                    <a-select-option value="C">
                      C
                    </a-select-option>
                    <a-select-option value="D">
                      D
                    </a-select-option>
                    <a-select-option value="E">
                      E
                    </a-select-option>
                    <a-select-option value="F">
                      F
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="cdzs"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'cdzs')"
                    :value="record.cdzs"
                    :precision="2"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="wzlx"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.wzlx==null?'':record.wzlx"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'wzlx')"
                  >
                    <a-select-option value="科研">
                      科研
                    </a-select-option>
                    <a-select-option value="教学">
                      教学
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="isBest"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>

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
                slot="otherTimes"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'otherTimes')"
                    :value="record.otherTimes"
                  >
                  </a-textarea>
                </div>
              </template>
               <template
                slot="firstUnitAuthor"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'firstUnitAuthor')"
                    :value="record.firstUnitAuthor"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="authorRank"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'authorRank')"
                    :value="record.authorRank"
                  >
                  </a-textarea>
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
                slot="auditIsfirst"
                slot-scope="text, record"
              >
                <a-checkbox
                  @change="e => onIsUseChange(e,record,'auditIsfirst')"
                  :checked="text"
                ></a-checkbox>
              </template>
               <template
                slot="auditTotalnum"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'auditTotalnum')"
                    :value="record.auditTotalnum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="fileId"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  <a
                    :href="record.fileUrl"
                    v-if="text!=null && text !=''"
                    target="_blank"
                  >查看</a>
                </div>
                <div v-else>
                  <a-button
                    type="dashed"
                    block
                    @click="OpenFile(record)"
                  >
                    {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
                  </a-button>
                </div>
              </template>
              <template
                slot="isJxzcsb"
                slot-scope="text, record"
              >
                <div key="isJxzcsb">

                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="(e1,f) => inputCheckChange(e1,f,record,'isJxzcsb')"
                    :checked="record.isJxzcsb=='是'"
                  >
                  </a-switch>
                </div>
              </template>
              <template
                slot="isLczcsb"
                slot-scope="text, record"
              >
                <div key="isLczcsb">

                  <a-switch
                    checked-children="是"
                    un-checked-children="否"
                    @change="(e1,f) => inputCheckChange(e1,f,record,'isLczcsb')"
                    :checked="record.isLczcsb=='是'"
                  >
                  </a-switch>
                </div>
              </template>
              <template
                slot="auditQkjb"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-select
                    :value="record.auditQkjb==null?'':record.auditQkjb"
                    style="width: 100%"
                    @change="(e,f) => handleSelectChange(e,f,record,'auditQkjb')"
                  >
                    <a-select-option value="A">
                      A
                    </a-select-option>
                    <a-select-option value="B">
                      B
                    </a-select-option>
                    <a-select-option value="C">
                      C
                    </a-select-option>
                    <a-select-option value="D">
                      D
                    </a-select-option>
                    <a-select-option value="E">
                      E
                    </a-select-option>
                    <a-select-option value="F">
                      F
                    </a-select-option>
                  </a-select>
                </div>
              </template>
              <template
                slot="jxzcsl"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'jxzcsl')"
                    :value="record.jxzcsl"
                  >
                  </a-textarea>
                </div>
              </template>

              <template
                slot="lczcsl"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'lczcsl')"
                    :value="record.lczcsl"
                  >
                  </a-textarea>
                </div>
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
                v-hasNoPermission="['dca:audit']"
                  
                  type="dashed"
                  block
                  @click="handleAudit(record)"
                >
                  通过
                </a-button>
                <a-button
                v-hasNoPermission="['dca:audit']"
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
            <dcaBSciencepublish-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBSciencepublish-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
            :forceRender="true"
          >
            <dcaBSciencepublish-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBSciencepublish-done>
          </a-tab-pane>
        </a-tabs>

        <audit-userInfo
          ref="userinfo"
          @close="onCloseUserInfo"
          :visibleUserInfo="visibleUserInfo"
          :userAccount="userAccount"
        ></audit-userInfo>

      </a-card>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import DcaBSciencepublishDone from './DcaBSciencepublishDone'
import AuditUserInfo from '../../common/AuditUserDocInfo'


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
        userAccount: '',
        auditXuhaoE: null,
        auditXuhaoS: null,
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      visibleUserInfo: false,
      paginationInfo: null,
      scroll: {
        x: 3000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      userAccount: '',
      activeKey: 1,
    }
  },
  components: { DcaBSciencepublishDone, AuditUserInfo },
  mounted () {
    this.search()
  },
  props: {
    dcaYear: {
      default: '' //年度
    },
    dcaType: {
      default: '' //中高级
    }
  },
  methods: {
    moment,
    callback (activeKey) {
      this.activeKey = activeKey
    },
    search2 () {
     if (this.paginationInfo) {
       this.paginationInfo.current = this.pagination.defaultCurrent
     }
     this.search()
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
    setRowClassName (record) {
      // return record.id === this.className ? 'clickRowStyl' : ''
      return {
        style: {
          backgroundColor: '#F5222D!important'
        }
      }
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    freshTabs () {
       this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount 
       this.$refs.TableInfo2.queryParams.auditMan = this.queryParams.auditMan 
       this.$refs.TableInfo2.queryParams.auditManName = this.queryParams.auditManName 
      this.$refs.TableInfo3.queryParams.userAccount = this.queryParams.userAccount 
       this.$refs.TableInfo3.queryParams.auditMan = this.queryParams.auditMan 
       this.$refs.TableInfo3.queryParams.auditManName = this.queryParams.auditManName 
        if (this.queryParams.auditXuhaoS !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoS = this.queryParams.auditXuhaoS
        this.$refs.TableInfo3.queryParams.auditXuhaoS = this.queryParams.auditXuhaoS
      }
      if (this.queryParams.auditXuhaoE !== undefined) {
        this.$refs.TableInfo2.queryParams.auditXuhaoE = this.queryParams.auditXuhaoE
        this.$refs.TableInfo3.queryParams.auditXuhaoE = this.queryParams.auditXuhaoE
      }

       
         if (this.$refs.TableInfo2.paginationInfo) {
       this.$refs.TableInfo2.paginationInfo.current = 1
     }
      if (this.$refs.TableInfo3.paginationInfo) {
       this.$refs.TableInfo3.paginationInfo.current = 1
     }
      
      this.$refs.TableInfo2.fetch2(this.$refs.TableInfo2.queryParams)
      this.$refs.TableInfo3.fetch2(this.$refs.TableInfo3.queryParams)
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      json.splice(this.columns.length-1,1) //移出第一个
      console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams= this.queryParams
      
      let state = 1
      if(this.activeKey==1){
         state = 1
      }
       if(this.activeKey==2){
         state = 3
         delete queryParams.auditState
      }
       if(this.activeKey==3){
         state = 2
         delete queryParams.auditState
      }
      this.$export('dcaBDocSciencepublish/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: state,
        dataJson: dataJson,
        ...queryParams
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
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    handleChangeState (state) {
      this.queryParams.auditState = state
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
      console.info(record.auditState)
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将进入下一个审核人',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocSciencepublish/updateNew', {
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
          that.$post('dcaBDocSciencepublish/updateNew', {
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
          that.$post('dcaBDocSciencepublish/updateNew', {
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
      this.$get('dcaBDocSciencepublish/audit', {
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
          title: '论文名',
          dataIndex: 'paperName',
          width: 200,
          scopedSlots: { customRender: 'paperName' },
          fixed: 'left'
        },
        {
          title: '期刊名',
          dataIndex: 'journalName',
          width: 200,
          scopedSlots: { customRender: 'journalName' },
          fixed: 'left'
        },
        {
          title: '期刊号',
          dataIndex: 'journalCode',
          width: 120,
          scopedSlots: { customRender: 'journalCode' },
          fixed: 'left'
        },
        {
          title: '发表年月',
          dataIndex: 'paperPublishdate',
          width: 130,
          scopedSlots: { customRender: 'paperPublishdate' },
          fixed: 'left'
        },
        {
          title: '收录情况',
          dataIndex: 'paperShoulu',
          width: 120,
          scopedSlots: { customRender: 'paperShoulu' }
        },
        {
          title: '影响因子',
          dataIndex: 'paperCause',
          width: 120,
          scopedSlots: { customRender: 'paperCause' }
        },
        {
          title: '是否一流期刊',
          dataIndex: 'isBest',
          width: 120,
          scopedSlots: { customRender: 'isBest' }
        },
        {
          title: '他引次数',
          dataIndex: 'otherTimes',
          width: 120,
          scopedSlots: { customRender: 'otherTimes' }
        },
        {
          title: '期刊级别',
          dataIndex: 'qkjb',
          width: 100,
          scopedSlots: { customRender: 'qkjb' }
        },
        {
          title: '第一或通讯作者',
          dataIndex: 'authorRank',
          width: 120,
          scopedSlots: { customRender: 'authorRank' }
        },
        {
          title: '第几作者',
          dataIndex: 'djzz',
          width: 80,
          scopedSlots: { customRender: 'djzz' }
        },
        
        {
          title: '第一作者第一单位',
          dataIndex: 'firstUnitAuthor',
          width: 80,
          scopedSlots: { customRender: 'firstUnitAuthor' }
        },
        {
          title: '期刊类型',
          dataIndex: 'wzlx',
          width: 80,
          scopedSlots: { customRender: 'wzlx' }
        },
        // {
        //   title: '是否能用于教学职称申报',
        //   dataIndex: 'isJxzcsb',
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   width: 120,
        //   scopedSlots: { customRender: 'isJxzcsb' }
        // }, {
        //   title: '是否能用于临床职称申报',
        //   dataIndex: 'isLczcsb',
        //   width: 120,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'isLczcsb' }
        // }, {
        //   title: '期刊级别',
        //   dataIndex: 'auditQkjb',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'auditQkjb' }
        // }, {
        //   title: '教学职称数量',
        //   dataIndex: 'jxzcsl',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'jxzcsl' }
        // }, {
        //   title: '临床职称数量',
        //   dataIndex: 'lczcsl',
        //   width: 80,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'lczcsl' }
        // },
        //  {
        //   title: '第一作者或通讯作者共几人',
        //   dataIndex: 'auditTotalnum',
        //   width: 100,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'auditTotalnum' }
        // },
        // {
        //   title: '非第一作者或通讯作者',
        //   dataIndex: 'auditIsfirst',
        //   width: 100,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'auditIsfirst' }
        // },-->
        //  {
        //   title: '承担字数（万）',
        //   dataIndex: 'cdzs',
        //   width: 100,
        //   customHeaderCell: function () {
        //     return { style: { color: 'red' } }
        //   },
        //   scopedSlots: { customRender: 'cdzs' }
        // }, 
        {
          title: '状态',
          dataIndex: 'state',
          width: 100,
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
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          scopedSlots: { customRender: 'auditSuggestion' }
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
