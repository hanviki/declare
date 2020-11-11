<template>

  <a-card
    class="card-area"
    title="基本资料"
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
            </div>
            <span style="float: right; margin-top: 3px;">
               <a-button
                type="primary"
                @click="exportCustomExcel"
              >导出</a-button>
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
              slot="auditNote"
              slot-scope="text, record"
            >
              <div key="auditNote">
                <a-textarea
                  @blur="e => inputChange(e.target.value,record,'auditNote')"
                  :value="record.auditNote"
                >
                </a-textarea>
              </div>
            </template>
            <template
              v-for="col in listAuditInfo"
              :slot="col.fieldName"
              slot-scope="text, record"
            >
              <div :key="col.fieldName">
                <a-switch
                  v-if="col.showType==1"
                  checked-children="是"
                  un-checked-children="否"
                  @change="(e1,f) => inputCheckChange(e1,f,record,col.fieldName)"
                  :checked="record[col.fieldName]=='是'"
                >
                </a-switch>
                <a-select
                  v-if="col.showType==2"
                  :value="record[col.fieldName]"
                  style="width: 100%"
                  @change="(e,f) => handleSelectChange(e,f,record,col.fieldName)"
                >
                  <a-select-option value="优">
                    优
                  </a-select-option>
                  <a-select-option value="良">
                    良
                  </a-select-option>
                  <a-select-option value="中">
                    中
                  </a-select-option>
                  <a-select-option value="差">
                    差
                  </a-select-option>
                </a-select>
                <a-textarea
                  v-if="col.showType==3"
                  @blur="e => inputChange(e.target.value,record,col.fieldName)"
                  :value="record[col.fieldName]"
                >
                </a-textarea>
                <a-input-number
                  v-if="col.showType==4"
                  @blur="e => inputChange(e.target.value,record,col.fieldName)"
                  :value="record[col.fieldName]"
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
        >
          <dcaBUser-done
            ref="TableInfo2"
            :state="3"
          >
          </dcaBUser-done>
        </a-tab-pane>
      </a-tabs>
    </a-spin>
  </a-card>

</template>

<script>
import moment from 'moment';
import DcaBUserDone from './DcaBUserDone'

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
      listAuditInfo: [{
        fieldName: 'xxy',
        fieldTitle: '显示',
        showType: 4,
      }], // 当前用户包含的审核数据
    }
  },
  components: { DcaBUserDone },
  mounted () {
    this.fetchUseraudit()
    this.search()
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
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
      this.freshTabs()
    },
    freshTabs () {
      this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount
      this.$refs.TableInfo2.fetch2()
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
          let dca_b_auditdynamic = []
          that.listAuditInfo.forEach(element2 => {
            dca_b_auditdynamic.push({
              auditTitle: element2.fieldTitle,
              auditTitletype: element2.fieldName,
              auditResult: record[element2.fieldName],
              auditNote: record.auditNote,
              userAccount: record.userAccount,
              userAccountName: record.userAccountName
            })
          });
          let jsonStr = JSON.stringify(dca_b_auditdynamic)
          that.loading = true
          that.$post('dcaBAuditdynamic/addNew', {
            jsonStr: jsonStr
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
      this.$get('dcaDAuditinfo/userAudit', {
      }).then((r) => {
        console.info(r.data)
        this.listAuditInfo = r.data

        r.data.forEach(element => {
          this.columns.push({
            title: element.fieldTitle,
            dataIndex: element.fieldName,
            width: 130,
            scopedSlots: { customRender: element.fieldName }
          });

        });
        this.columns.push({
          title: '备注',
          dataIndex: 'auditNote',
          width: 130,
          scopedSlots: { customRender: 'auditNote' }
        });
        this.columns.push({
          title: '操作',
          dataIndex: 'action',
          width: 130,
          scopedSlots: { customRender: 'action' }
        });
      })
    },
    getGwdj (text) {
      let name = ''
      switch (text) {
        case '教授主任医师':
          name = '正高'
          break
        case '教授':
          name = '正高'
          break
        case '主任医师':
          name = '正高'
          break
        case '研究员':
          name = '正高'
          break
        case '主任护师':
          name = '正高'
          break
        case '主任技师':
          name = '正高'
          break
        case '主任药师':
          name = '正高'
          break
        case '教授级高级工程师':
          name = '正高'
          break
        case '编审':
          name = '正高'
          break
        case '副教授副主任医师':
          name = '副高'
          break
        case '副教授':
          name = '副高'
          break
        case '副主任医师':
          name = '副高'
          break
        case '副研究员':
          name = '副高'
          break
        case '副主任护师':
          name = '副高'
          break
        case '副主任技师':
          name = '副高'
          break
        case '副主任药师':
          name = '副高'
          break
        case '高级工程师':
          name = '副高'
          break
        case '副编审':
          name = '副高'
          break
      }
      return name
    },
     getXlName(text) {
      let name = ''
      switch (text) {
        case '教授主任医师':
          name = '医师'
          break
        case '教授':
          name = '医师'
          break
        case '主任医师':
          name = '医师'
          break
        case '研究员':
          name = '研究'
          break
        case '主任护师':
          name = '护理'
          break
        case '主任技师':
          name = '医技'
          break
        case '主任药师':
          name = '药技'
          break
        case '教授级高级工程师':
          name = '技术工程'
          break
        case '编审':
          name = '技术编辑'
          break
        case '副教授副主任医师':
          name = '医师'
          break
        case '副教授':
          name = '医师'
          break
        case '副主任医师':
          name = '医师'
          break
        case '副研究员':
          name = '研究'
          break
        case '副主任护师':
          name = '护理'
          break
        case '副主任技师':
          name = '医技'
          break
        case '副主任药师':
          name = '药技'
          break
        case '高级工程师':
          name = '技术工程'
          break
        case '副编审':
          name = '技术编辑'
          break
      }
      return name
    },
    setDefaultValue (element2) {
      if (element2.showType == 1) {
        if (element2.state == 1) {
          return '是'
        }
        else {
          return '否'
        }
      }
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
         let json =[
        {
          title: '科室',
          dataIndex: 'ks',
          width: 80
        },
        {
          title: '系列',
          dataIndex: 'xl',
         
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
          title: '性别',
          dataIndex: 'sexName',
          width: 60
        },
        {
          title: '出生日期',
          dataIndex: 'birthdaystr'
        },
        {
          title: '专业技术职务',
          dataIndex: 'positionName',
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'zygwDate',
        },
        {
          title: '申请岗位等级',
          dataIndex: 'gwdj',
          width: 130
        }
      ];
      this.listAuditInfo.forEach(element => {
          json.push({
            title: element.fieldTitle,
            dataIndex: element.fieldName,
            isDynamic: 1
          });

        });
       let dataJson = JSON.stringify(json)
       
      this.$export('dcaUserAudit/excel2', {
        sortField: sortField,
        sortOrder: sortOrder,
         dataJson: dataJson,
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
      this.$get('dcaUserAudit/user', {
        ...params,
        state: 1
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
          console.info(auditList)
          if (auditList == null) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
              console.info(element2)
              element[element2.fieldName] = this.setDefaultValue(element2)
              element.auditNote = element2.auditNote
            });
          }
          else {
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult
              element.auditNote = element2.auditNote
            });
          }

        });
        this.dataSource = data.rows
        console.info(data.rows)
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
          title: '系列',
          dataIndex: 'xl',
          customRender: (text, row, index) => {
            return this.getXlName(row.npPositionName)
          },
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
          title: '性别',
          dataIndex: 'sexName',
          width: 60
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
          customRender: (text, row, index) => {
            return (row.zyjsgw == null || row.zyjsgw == '' ? "" : row.zyjsgw) + (row.zyjsgwLc == null || row.zyjsgwLc == '' ? "" : row.zyjsgwLc)
          },
        },
        {
          title: '专业技术职务聘任时间',
          dataIndex: 'appointedDate',
          width: 130,
          customRender: (text, row, index) => {
            return (text == null ? '' : moment(text).format('YYYY-MM-DD')) + (text == null ? '' : '/') + (row.appointedDateLc == null ? '' : moment(row.appointedDateLc).format('YYYY-MM-DD'))
          },
        },
        {
          title: '申请岗位等级',
          dataIndex: 'npPositionName',
          width: 130,
          customRender: (text, row, index) => {
            return this.getGwdj(text)
          },
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
