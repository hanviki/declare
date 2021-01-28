<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="">
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
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="申报年度"
                    v-bind="formItemLayout"
                    v-show="!dcaType==''"
                  >
                    <a-input v-model="queryParams.auditMan" />
                  </a-form-item>
                </a-col>
              </div>
              <span style="float: right; margin-top: 3px;">
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
              :scroll="scroll"
            >
              <template
                slot="deptDesc"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'deptDesc')"
                    :value="record.deptDesc"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="yggh"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'yggh')"
                    :value="record.yggh"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="telephone"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'telephone')"
                    :value="record.telephone"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="hkrsbh"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'hkrsbh')"
                    :value="record.hkrsbh"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="zslx"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'zslx')"
                    :value="record.zslx"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="ldz"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'ldz')"
                    :value="record.ldz"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="sexName"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'sexName')"
                    :value="record.sexName"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="birthday"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'birthday')"
                  />
                </div>
              </template>
              <template
                slot="schoolDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'schoolDate')"
                  />
                </div>
              </template>
              <template
                slot="shenfen"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'shenfen')"
                    :value="record.shenfen"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="xueli"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'xueli')"
                    :value="record.xueli"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="boshidaoshi"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'boshidaoshi')"
                    :value="record.boshidaoshi"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="biyeDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'biyeDate')"
                  />
                </div>
              </template>
              <template
                slot="pinqiRanknum"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-input-number
                    @blur="e => inputChange(e.target.value,record,'pinqiRanknum')"
                    :value="record.pinqiRanknum"
                    :precision="0"
                  >
                  </a-input-number>
                </div>
              </template>
              <template
                slot="isAuthority"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'isAuthority')"
                    :value="record.isAuthority"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="isZhuanrang"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'isZhuanrang')"
                    :value="record.isZhuanrang"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="hezuodaoshi"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'hezuodaoshi')"
                    :value="record.hezuodaoshi"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="biyexuexiao"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'biyexuexiao')"
                    :value="record.biyexuexiao"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="zhuanye"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'zhuanye')"
                    :value="record.zhuanye"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="inDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'inDate')"
                  />
                </div>
              </template>
              <template
                slot="goal"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'goal')"
                    :value="record.goal"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="yqDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'yqDate')"
                  />
                </div>
              </template>
              <template
                slot="fileIdLc"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'fileIdLc')"
                    :value="record.fileIdLc"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="fileUrlLc"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'fileUrlLc')"
                    :value="record.fileUrlLc"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="yuangongzu"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'yuangongzu')"
                    :value="record.yuangongzu"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="nation"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'nation')"
                    :value="record.nation"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="xrgwjbprsj"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'xrgwjbprsj')"
                  />
                </div>
              </template>
              <template
                slot="jiguan"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'jiguan')"
                    :value="record.jiguan"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="dcaYear"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'dcaYear')"
                    :value="record.dcaYear"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="politicalStatus"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'politicalStatus')"
                    :value="record.politicalStatus"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="country"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'country')"
                    :value="record.country"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="idCard"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'idCard')"
                    :value="record.idCard"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="staffDate"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text==""|| text==null?"":text.substr(0,10)}}
                </div>
                <div v-else>
                  <a-date-picker
                    :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
                    @change="(e,f) => handleChange(e,f,record,'staffDate')"
                  />
                </div>
              </template>
              <template
                slot="pictureId"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'pictureId')"
                    :value="record.pictureId"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="pictureUrl"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'pictureUrl')"
                    :value="record.pictureUrl"
                  >
                  </a-textarea>
                </div>
              </template>
              <template
                slot="pinqi"
                slot-scope="text, record"
              >
                <div v-if="record.state==3">
                  {{text}}
                </div>
                <div v-else>
                  <a-textarea
                    @blur="e => inputChange(e.target.value,record,'pinqi')"
                    :value="record.pinqi"
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
                  style="width:50%;padding-left:2px;padding-right:2px;"
                  type="dashed"
                  block
                  @click="handleAuditNext(record)"
                >
                  下一轮
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
            <dcaBDocUser-done
              ref="TableInfo2"
              :state="3"
            >
            </dcaBDocUser-done>
          </a-tab-pane>
          <a-tab-pane
            key="3"
            tab="审核未通过"
            :forceRender="true"
          >
            <dcaBDocUser-done
              ref="TableInfo3"
              :state="2"
            >
            </dcaBDocUser-done>
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
import DcaBDocUserDone from './DcaBDocUserDone'
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
        userAccount: '',
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      activeKey: 1
    }
  },
  components: { DcaBDocUserDone, AuditUserInfo },
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
    freshTabs () {
      this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount
      this.$refs.TableInfo2.queryParams.auditMan = this.queryParams.auditMan
      this.$refs.TableInfo2.queryParams.auditManName = this.queryParams.auditManName
      this.$refs.TableInfo3.queryParams.userAccount = this.queryParams.userAccount
      this.$refs.TableInfo3.queryParams.auditMan = this.queryParams.auditMan
      this.$refs.TableInfo3.queryParams.auditManName = this.queryParams.auditManName
      this.$refs.TableInfo2.fetch2(this.$refs.TableInfo2.queryParams)
      this.$refs.TableInfo3.fetch2(this.$refs.TableInfo3.queryParams)
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
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      json.splice(this.columns.length - 1, 1) //移出第一个
      console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams = this.queryParams

      let state = 1
      if (this.activeKey == 1) {
        state = 1
      }
      if (this.activeKey == 2) {
        state = 3
        delete queryParams.auditState
      }
      if (this.activeKey == 3) {
        state = 2
        delete queryParams.auditState
      }
      this.$export('dcaBDocUser/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: state,
        dataJson: dataJson,
        ...queryParams
      })
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
      if (selectedRows[0].state != 3) {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    inputCheckChange (blFlag, f, record, filedName) {
      record[filedName] = blFlag ? '是' : '否'
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
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
          that.$post('dcaBDocUser/updateNew', {
            jsonStr: jsonStr,
            state: 1
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
    handleAudit (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(record)
          that.loading = true
          that.$post('dcaBDocUser/updateNew', {
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
          that.$post('dcaBDocUser/updateNew', {
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
      this.$get('dcaBDocUser/audit', {
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
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '部门描述',
          dataIndex: 'deptDesc',
          width: 130,
          scopedSlots: { customRender: 'deptDesc' }
        },
        {
          title: '员工工号',
          dataIndex: 'yggh',
          width: 130,
          scopedSlots: { customRender: 'yggh' }
        },
        {
          title: '手机号',
          dataIndex: 'telephone',
          width: 130,
          scopedSlots: { customRender: 'telephone' }
        },
        {
          title: '华科人事编号',
          dataIndex: 'hkrsbh',
          width: 130,
          scopedSlots: { customRender: 'hkrsbh' }
        },
        {
          title: '招生类型',
          dataIndex: 'zslx',
          width: 130,
          scopedSlots: { customRender: 'zslx' }
        },
        {
          title: '流动站',
          dataIndex: 'ldz',
          width: 130,
          scopedSlots: { customRender: 'ldz' }
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 130,
          scopedSlots: { customRender: 'sexName' }
        },
        {
          title: '出生年月',
          dataIndex: 'birthday',
          width: 130,
          scopedSlots: { customRender: 'birthday' }
        },
        {
          title: '来院工作时间',
          dataIndex: 'schoolDate',
          width: 130,
          scopedSlots: { customRender: 'schoolDate' }
        },
        {
          title: '身份',
          dataIndex: 'shenfen',
          width: 130,
          scopedSlots: { customRender: 'shenfen' }
        },
        {
          title: '学历',
          dataIndex: 'xueli',
          width: 130,
          scopedSlots: { customRender: 'xueli' }
        },
        {
          title: '博士导师',
          dataIndex: 'boshidaoshi',
          width: 130,
          scopedSlots: { customRender: 'boshidaoshi' }
        },
        {
          title: '毕业时间',
          dataIndex: 'biyeDate',
          width: 130,
          scopedSlots: { customRender: 'biyeDate' }
        },
        {
          title: '第几个聘期',
          dataIndex: 'pinqiRanknum',
          width: 130,
          scopedSlots: { customRender: 'pinqiRanknum' }
        },
        {
          title: '是否授权',
          dataIndex: 'isAuthority',
          width: 130,
          scopedSlots: { customRender: 'isAuthority' }
        },
        {
          title: '是否转让',
          dataIndex: 'isZhuanrang',
          width: 130,
          scopedSlots: { customRender: 'isZhuanrang' }
        },
        {
          title: '合作导师',
          dataIndex: 'hezuodaoshi',
          width: 130,
          scopedSlots: { customRender: 'hezuodaoshi' }
        },
        {
          title: '毕业院校',
          dataIndex: 'biyexuexiao',
          width: 130,
          scopedSlots: { customRender: 'biyexuexiao' }
        },
        {
          title: '专业',
          dataIndex: 'zhuanye',
          width: 130,
          scopedSlots: { customRender: 'zhuanye' }
        },
        {
          title: '进站日期',
          dataIndex: 'inDate',
          width: 130,
          scopedSlots: { customRender: 'inDate' }
        },
        {
          title: '站内工作目标',
          dataIndex: 'goal',
          width: 130,
          scopedSlots: { customRender: 'goal' }
        },
        {
          title: '延期时间',
          dataIndex: 'yqDate',
          width: 130,
          scopedSlots: { customRender: 'yqDate' }
        },
        {
          title: '附件（临床）',
          dataIndex: 'fileIdLc',
          width: 130,
          scopedSlots: { customRender: 'fileIdLc' }
        },
        {
          title: '附件地址（临床）',
          dataIndex: 'fileUrlLc',
          width: 130,
          scopedSlots: { customRender: 'fileUrlLc' }
        },
        {
          title: '员工组',
          dataIndex: 'yuangongzu',
          width: 130,
          scopedSlots: { customRender: 'yuangongzu' }
        },
        {
          title: '民族',
          dataIndex: 'nation',
          width: 130,
          scopedSlots: { customRender: 'nation' }
        },
        {
          title: '现任岗位级别聘任时间',
          dataIndex: 'xrgwjbprsj',
          width: 130,
          scopedSlots: { customRender: 'xrgwjbprsj' }
        },
        {
          title: '籍贯',
          dataIndex: 'jiguan',
          width: 130,
          scopedSlots: { customRender: 'jiguan' }
        },
        {
          title: 'dcaYear',
          dataIndex: 'dcaYear',
          width: 130,
          scopedSlots: { customRender: 'dcaYear' }
        },
        {
          title: '政治面貌',
          dataIndex: 'politicalStatus',
          width: 130,
          scopedSlots: { customRender: 'politicalStatus' }
        },
        {
          title: '国籍',
          dataIndex: 'country',
          width: 130,
          scopedSlots: { customRender: 'country' }
        },
        {
          title: '身份证号',
          dataIndex: 'idCard',
          width: 130,
          scopedSlots: { customRender: 'idCard' }
        },
        {
          title: '职位时间',
          dataIndex: 'staffDate',
          width: 130,
          scopedSlots: { customRender: 'staffDate' }
        },
        {
          title: '照片id',
          dataIndex: 'pictureId',
          width: 130,
          scopedSlots: { customRender: 'pictureId' }
        },
        {
          title: '照片url',
          dataIndex: 'pictureUrl',
          width: 130,
          scopedSlots: { customRender: 'pictureUrl' }
        },
        {
          title: '聘期',
          dataIndex: 'pinqi',
          width: 130,
          scopedSlots: { customRender: 'pinqi' }
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
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion',
          scopedSlots: { customRender: 'auditSuggestion' }
        },
        {
          title: '是否用于本次评审',
          dataIndex: 'isUse',
          scopedSlots: { customRender: 'isUse' },
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
