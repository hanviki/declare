<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <a-spin :spinning="loading">
      <div>
        <a-form layout="horizontal">
          <a-row>
            <div>
              <a-col
                :md="6"
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
                :md="6"
                :sm="24"
              >
                <a-form-item
                  label="申请年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.dcaYear" />
                </a-form-item>
              </a-col>
              <a-col
                :md="6"
                :sm="24"
              >
                <a-form-item
                  label="岗位等级"
                  v-bind="formItemLayout"
                >
                  <a-select
                    mode="multiple"
                    style="width: 100%"
                    @change="handleChangeSearch"
                  >

                    <a-select-option value="中级">
                      中级
                    </a-select-option>
                    <a-select-option value="初级">
                      初级
                    </a-select-option>

                  </a-select>
                </a-form-item>
              </a-col>
              <a-col
                :md="6"
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
                      key="1"
                      value="1"
                    >已提交</a-select-option>
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
                type="primary"
                @click="exportExcel"
              >导出</a-button>
              <a-button
                style="margin-left: 8px"
                @click="reset"
              >重置</a-button>
            </span>
          </a-row>
        </a-form>
      </div>

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
          slot="action"
          slot-scope="text, record"
        >
          <a-button
            v-hasNoPermission="['dca:audit']"
            type="dashed"
            block
            v-if="record.state==1"
            @click="handleAudit(record)"
          >
            退回
          </a-button>
        </template>
      </a-table>
    </a-spin>
  </a-card>
</template>

<script>
import moment from 'moment';


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
  components: {},
  mounted () {
    //this.fetchUseraudit()
    this.fetch()
  },
  methods: {
    moment,
    callback () {

    },
    handleChangeState (value) {
      this.queryParams.state = value
    },
    handleChangeSearch (value) {
      this.queryParams.gwdj = value
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
      json.splice(this.columns.length - 1, 1) //移出第一个
      console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams = this.queryParams

      this.$export('dcaBUserapplyzc/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...queryParams
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
        sortField: "user_account",
        sortOrder: "descend",
        ...this.queryParams
      })
      // this.freshTabs()
    },
    freshTabs () {
      // this.$refs.TableInfo2.queryParams.userAccount = this.queryParams.userAccount
      // this.$refs.TableInfo2.fetch2()
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
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "user_account",
        sortOrder: "descend",
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
        content: '当您点击确定按钮后，此记录将退回',
        centered: true,
        onOk () {
          that.loading = true
          that.$put('dcaBUserapplyzc/state', {
            state: 2,
            id: record.id,
            userAccount: record.userAccount
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
    handleAuditNo (record) {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将取消审核',
        centered: true,
        onOk () {
          that.loading = true
          that.$put('dcaBUserapplyzc/state', {
            state: 1,
            id: record.id
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
      this.$get('dcaBUserapplyzc', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total


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
          width: 100
        },
        {
          title: '申报年度',
          dataIndex: 'dcaYear',
          width: 100
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
                return <a-tag color="#f50">已退回</a-tag>
              default:
                return text
            }
          }
        }, {
          title: '操作',
          dataIndex: 'action',
          width: 130,
          scopedSlots: { customRender: 'action' }
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
