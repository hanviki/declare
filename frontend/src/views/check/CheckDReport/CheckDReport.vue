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
                label="人事编号/姓名"
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
        :rowKey="record => record. id                                                    "
        :dataSource="dataSource"
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        :bordered="bordered"
        :scroll="scroll"
      >
         <template
          slot="userAccount"
          slot-scope="text, record"
        >
          <a
            href="#"
            @click="showUserInfo(record)"
          >{{text}}</a>
        </template>
      </a-table>
    </div>
     <person-result
        :infoVisiable="infoVisiable"
        :userAccount="userAccount"
        :dcaYear="dcaYear"
        :zyjsgw="zyjsgw"
        :ks="ks"
        :userAccountName="userAccountName"
        @close="onCloseUserInfo"
      >
      </person-result>
  </a-card>
</template>

<script>
import PersonResult from './PersonResult'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'CheckBUser',
  components: { PersonResult },
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
      userAccount: '',
      infoVisiable: false,
      dcaYear: '',
      zyjsgw: '',
      ks: '',
     userAccountName: '',
       listAuditInfo: [{
        fieldName: 'xxy',
        fieldTitle: '显示',
        showType: 4,
      }], // 当前用户包含的审核数据
       scroll: {
        x: 6000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
       columns: [{
        title: '发薪号',
        dataIndex: 'userAccount',
        width: 80,
        scopedSlots: { customRender: 'userAccount' }
      },
      {
        title: '姓名',
        dataIndex: 'userAccountName',
        width: 80
      }, {
        title: '考核年度',
        dataIndex: 'dcaYear',
        width: 100
      }, {
        title: '科室',
        dataIndex: 'ks',
        width: 100
      },
      {
        title: '专技类别',
        dataIndex: 'zjlb',
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
        width: 100
      },
      {
        title: '手机号',
        dataIndex: 'telephone',
        width: 100
      },
      {
        title: '身份证号',
        dataIndex: 'idCard',
        width: 100
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
        title: '职称',
        dataIndex: 'positionName',
        width: 100
      },
    {
        title: '总得分',
        dataIndex: 'totalNum',
        width: 100
      },
       {
        title: '备注',
        dataIndex: 'deptName',
        width: 100
      }
     ]
    }
  },
  computed: {
   
    
  },
  mounted () {
    this.fetchUseraudit()
  },
  methods: {
        fetchUseraudit () {
      this.$get('checkBSetting/userAll', {
      }).then((r) => {
        console.info(r.data)
        this.listAuditInfo = r.data

        r.data.forEach(element => {
          this.columns.push({
            title: element.filedTitle,
            dataIndex: element.filedName,
            width: 100,
            customRender: (text, row, index) => {
            switch (text) {
              case '未完成':
                return <a-tag color="red">未完成</a-tag>
              case '不审核':
                return <a-tag color="green">不审核</a-tag>
              default:
                return text
            }
          }
          });

        });
        this.search()
      })
    },
       exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
     let json = [
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
      }, {
        title: '考核年度',
        dataIndex: 'dcaYear',
        width: 100
      }, {
        title: '科室',
        dataIndex: 'ks',
        width: 100
      },
      {
        title: '专技类别',
        dataIndex: 'zjlb',
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
        width: 100
      },
      {
        title: '手机号',
        dataIndex: 'telephone',
        width: 100
      },
      {
        title: '身份证号',
        dataIndex: 'idCard',
        width: 100
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
        title: '职称',
        dataIndex: 'positionName',
        width: 100
      },
    {
        title: '总得分',
        dataIndex: 'totalNum',
        width: 100
      },  {
        title: '备注',
        dataIndex: 'deptName',
        width: 100
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
      this.$export('checkBUser/excel', {
        sortField: sortField,
        sortOrder: sortOrder,
        dataJson: dataJson,
        ...this.queryParams
      })
    },
      showUserInfo (record) {
      //debugger
      this.infoVisiable = true
      this.userAccount = record.userAccount
      this.dcaYear= record.dcaYear
      this.zyjsgw= record.zyjsgw
      this.ks= record.ks
      this.userAccountName= record.userAccountName
    },
    onCloseUserInfo () {
      this.infoVisiable = false
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
      this.$refs.checkBUserEdit.setFormValues(record)
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
          let checkBUserIds = that.selectedRowKeys.join(',')
          that.$delete('checkBUser/' + checkBUserIds).then(() => {
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
      this.$get('checkBUser', {
        ...params
      }).then((r) => {
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.loading = false
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
                element[element2.filedName] = ''
               
              }
            });
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult=='null'?"":element2.auditResult
            });
          }

        });
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
