<template>

  <a-card
    class="card-area"
    title=""
  >
    <a-spin :spinning="loading">
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
                  label="考核年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.dcaYear" />
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
              <a-upload
                accept=".xls,.xlsx"
                :fileList="fileList"
                :beforeUpload="beforeUpload"
                @change="handleChangeFile"
              >
                <a-button> <a-icon type="upload" /> 上传审核结果 </a-button></a-upload>
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
              v-for="col in listAuditInfo"
              :slot="col.filedName"
              slot-scope="text, record"
            >
              <div :key="col.filedName"  v-if="isShow(col,record)">
                <a-textarea
                  v-if="col.showType==2"
                  @blur="e => inputChange(e.target.value,record,col.filedName)"
                  :value="record[col.filedName]"
                >
                </a-textarea>
                <a-input-number
                  v-if="col.showType==1"
                  @blur="e => inputChange(e.target.value,record,col.filedName)"
                  :value="record[col.filedName]"
                  :max="col.range"
                  :precision="2"
                >
                </a-input-number>
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
                提交
              </a-button>
            </template>
          </a-table>
        </a-tab-pane>
        <a-tab-pane
          key="2"
          tab="已审核"
          :forceRender="true"
        >
          <checkBUser-done
            ref="TableInfo2"
            :state="3"
          >
          </checkBUser-done>
        </a-tab-pane>
     
      </a-tabs>
    </a-spin>
  </a-card>

</template>

<script>
import moment from 'moment';
import CheckBUserDone from './CheckBUserDone'

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
      fileList: [],
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1200,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      listAuditInfo: [{
        fieldName: 'xxy',
        fieldTitle: '显示',
        showType: 4,
      }], // 当前用户包含的审核数据
      activeKey: 1
    }
  },
  components: { CheckBUserDone },
  mounted () {
    this.fetchUseraudit()
    
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
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
      this.freshTabs()
    },
    freshTabs () {
     // this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount
     // this.$refs.TableInfo2.queryParams.dcaYear = this.queryParams.dcaYear
      this.$refs.TableInfo2.fetch2(this.queryParams)
      //this.$refs.TableInfo3.fetch(this.queryParams.userAccount)
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
      this.search()
    },
    handleChangeSearch (value) {
      this.queryParams.ks = value
    },
    handleChangeFile (info) {
      if (info.file.status === 'uploading') {
        this.handleUpload()
      }
    },
    isShow (col,record) {
       if(col.checkPerson=="1"){
          if(col.lb!=undefined&& col.lb.indexOf(record.zjlb)==-1){
             return false
          }
       }
       else if(col.checkPerson!=undefined && col.checkPerson=="2"){
          console.info(this.$store.state.account.user.username)
          if(record.ksLeaderPernr!=this.$store.state.account.user.username){
            return false
          }
       }
       else if(col.checkPerson!=undefined && col.checkPerson=="3"){
          if(record.zgLeaderPernr!=this.$store.state.account.user.username){
            return false
          }
       }
        return true
    },
    handleRemove (file) {
      this.fileList = []
    },
    beforeUpload (file) {
      //console.info(file.type)
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
      let json = [
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
          title: '考核年度',
          dataIndex: 'dcaYear',
          width: 80
        }
      ]
      this.listAuditInfo.forEach(element => {
        json.push({
          title: element.filedTitle,
          dataIndex: element.filedName,
          isDynamic: 1
        });

      });
      let dataJson = JSON.stringify(json)

      const formData = new FormData()
      formData.append('file', fileList[0])
      formData.append('dataJson', dataJson)
      // You can use any AJAX library you like
      this.$upload('checkBAuditresult/importAudit', formData).then((r) => {
        this.$message.success('上传成功.')
        this.search()
      }).catch(() => {
        this.$message.error('上传失败.')
      })
      this.fileList = []
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {

    },
    handleSelectChange (value, option, record, filedName) {
      record[filedName] = value
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    checkedValue (record, fieldName) {
      // console.info(fieldName)
      return record[fieldName] == '是'
    },
    inputCheckChange (blFlag, f, record, filedName) {
      //console.info(blFlag)
      record[filedName] = blFlag ? '是' : '否'
    },
    inputChange (value, record, filedName) {
     // console.info(value)
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
          let dca_b_auditdynamic = []
          that.listAuditInfo.forEach(element2 => {
            dca_b_auditdynamic.push({
              auditTitle: element2.filedTitle,
              auditTitletype: element2.filedName,
              auditResult: record[element2.filedName],
              dcaYear: record.dcaYear,
              ks: element2.ks,
              isOria: element2.isOria,
              userAccount: record.userAccount,
              userAccountName: record.userAccountName
            })
          });
          let jsonStr = JSON.stringify(dca_b_auditdynamic)
          let year = record.dcaYear
          let userAccount2 = record.userAccount
          let userAccountName =record.userAccountName
          that.loading = true
          that.$post('checkBAuditresult/addNew', {
            jsonStr: jsonStr,
            year: year,
            userAccount2: userAccount2,
            userAccountName: userAccountName
          }).then(() => {
            //this.reset()
            that.$message.success('审核成功')
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

    fetchUseraudit () {
      this.$get('checkBSetting/userCheck', {
      }).then((r) => {
        console.info(r.data)
        this.listAuditInfo = r.data

        r.data.forEach(element => {
          this.columns.push({
            title: element.filedTitle,
            dataIndex: element.filedName,
            width: 130,
            scopedSlots: { customRender: element.filedName }
          });

        });
        this.columns.push({
          title: '操作',
          dataIndex: 'action',
          width: 130,
          scopedSlots: { customRender: 'action' }
        });
        this.search()
      })
    },
   
    setDefaultValue (element2) {
      return ''
    },
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
       let state = 1
      if(this.activeKey==1){
         state = 1
      }
       if(this.activeKey==2){
         state = 3
      }
      let json = [
        {
          title: '科室',
          dataIndex: 'ks',
          width: 80
        },
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
          title: '考核年度',
          dataIndex: 'dcaYear',
          width: 80
        },
        {
          title: '出生日期',
          dataIndex: 'birthday'
        },
        {
          title: '专业技术职务',
          dataIndex: 'zyjsgw',
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'appointedDate',
        }
      ];
      this.listAuditInfo.forEach(element => {
        json.push({
          title: element.filedTitle,
          dataIndex: element.filedName,
          isDynamic: 1
        });

      });
      let dataJson = JSON.stringify(json)

      this.$export('checkBUser/excel2', {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson: dataJson,
        state: state,
        ...this.queryParams
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

      this.loading = true
      this.$get('checkBUser/user', {
        ...params,
        state: 1
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.checkBAuditresultList
          console.info(auditList)
          if (auditList == null || auditList.length == 0) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
              // console.info(element2)
              element[element2.filedName] = ''
             // element.auditNote = element2.auditNote
            });
          }
          else {
            this.listAuditInfo.forEach(element2 => {
              if (!auditList.some(p => p.auditTitletype == element2.filedName)) {
                element[element2.filedName] = this.setDefaultValue(element2)
                element.auditNote = element2.auditNote
              }
            });
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult=='null'?"":element2.auditResult
              element.auditNote = element2.auditNote=='null'?"":element2.auditNote
            });
          }

        });
        this.dataSource = data.rows
       // console.info(data.rows)
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '科室',
          dataIndex: 'ks',
          width: 80
        },
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
          title: '考核年度',
          dataIndex: 'dcaYear',
          width: 80
        },
        {
          title: '出生日期',
          dataIndex: 'birthday',
          width: 100,
          customRender: (text, row, index) => {
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '专业技术职务',
          dataIndex: 'zyjsgw',
          width: 130,
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'appointedDate',
          width: 130,
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
