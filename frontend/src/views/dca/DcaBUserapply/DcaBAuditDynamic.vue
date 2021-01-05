<template>
  <div>
    <a-spin :spinning="loading">
      <a-card title="部门审核结果">
        <div>
          <a-form layout="horizontal">
            <a-row>
              <div>
                <a-col
                  :md="8"
                  :sm="24"
                >
                  <a-form-item
                    label="申报年度"
                    v-bind="formItemLayout"
                  >
                    <a-input v-model="queryParams.dcaYear" />
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
            slot="userAccount"
            slot-scope="text, record"
          >
            <a
              href="#"
              @click="showUserInfo(text)"
            >{{text}}</a>
          </template>
        </a-table>

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
        x: 1300,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      activeKey: 1
    }
  },
  components: { AuditUserInfo },
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
        sortField: "userAccount asc,dcaYear",
        sortOrder: "ascend",
        ...this.queryParams
      })
      // this.freshTabs()
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


      this.$export('dcaBAuditdynamic/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...queryParams
      })
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch({
        sortField: "userAccount",
        sortOrder: "ascend",
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
          that.$post('dcaBWorknum/updateNew', {
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
          that.$post('dcaBWorknum/updateNew', {
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
          that.$post('dcaBWorknum/updateNew', {
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
      params.sortField = "user_account asc, dca_year"
      params.sortOrder = "ascend"
      params.userAccount = this.$store.state.account.user.username
      this.loading = true
      this.$get('dcaBUserapply/audit', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total
        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
            auditList.forEach(element2 => {
              element[element2.auditTitletype] = element2.auditResult
              element.auditNote = element2.auditNote
            });
        });
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
          title: '年度',
          dataIndex: 'dcaYear',
          width: 100
        },
        {
          title: '党办',
          children: [
            {
              title: '是否通过医德医风审核',
              dataIndex: 'ydyf',
              width: 100,
            },
            {
              title: '是否存在医德医风一票否决的情况',
              dataIndex: 'ydyffj',
              width: 120
            }
          ]
        },
        {
          title: '党委组织部',
          children: [
            {
              title: '是否通过政治审查',
              dataIndex: 'zzsc',
              width: 100,
            },
            {
              title: '是否存在政治审查一票否决的情况',
              dataIndex: 'zzscypfj',
              width: 120
            }
          ]
        },
        {
          title: '纪委办公室',
          children: [
            {
              title: '是否通过纪律审查',
              dataIndex: 'jlsc',
              width: 100,
            },
            {
              title: '是否存在纪律审查一票否决的情况',
              dataIndex: 'jlscypfj',
              width: 120
            }
          ]
        },
        {
          title: '科研处',
          children: [
            {
              title: '是否通过学术道德审查',
              dataIndex: 'xsddsc',
              width: 100,
            },
            {
              title: '是否存在学术道德一票否决的情况',
              dataIndex: 'xsddscypfj',
              width: 120
            }
          ]
        },
         {
          title: '宣传部',
          children: [
            {
              title: '是否通过意识形态审查',
              dataIndex: 'yyxtsc',
              width: 100,
            },
            {
              title: '是否存在意识形态一票否决的情况',
              dataIndex: 'yyxtypfj',
              width: 120
            }
          ]
        },
        {
          title: '研究生管理办',
          children: [
            {
              title: '是否硕士导师',
              dataIndex: 'sfssds',
              width: 100,
            },
            {
              title: '是否博士导师',
              dataIndex: 'sfbsds',
              width: 120
            },
            {
              title: '是否通过师德师风审查',
              dataIndex: 'sftgsdsf',
              width: 120
            },
             {
              title: '是否存在师德师风一票否决的情况',
              dataIndex: 'sdsfypfj',
              width: 120
            }
          ]
        },
        {
          title: '教学办公室',
          children: [
            {
              title: '教学评分(百分制)',
              dataIndex: 'jxpf',
              width: 100,
            },
            {
              title: '教学评分等级',
              dataIndex: 'jxpfdj',
              width: 120
            },
              {
              title: '是否存在师德师风一票否决的情况',
              dataIndex: 'sdsfypfj2',
              width: 120
            },
             {
              title: '是否担任一年辅导员或班主任并考核合格',
              dataIndex: 'ynjbzr',
              width: 120
            },
             {
              title: '近五年教学工作在本单位总体评价情况（前%）',
              dataIndex: 'j5njxgz',
              width: 150
            },
          ]
        },
         {
          title: '门诊办公室',
          children: [
            {
              title: '门诊医疗评分(百分制)',
              dataIndex: 'mzylpf',
              width: 100,
            },
            {
              title: '门诊医疗评分等级',
              dataIndex: 'mzylpfdj',
              width: 120
            },
              {
              title: '是否门诊医疗事故一票否决的情况',
              dataIndex: 'mzylsgypfj',
              width: 150
            },
             
          ]
        },
         {
          title: '医务处',
          children: [
            {
              title: '医疗评分(百分制)',
              dataIndex: 'ylpfbfz',
              width: 100,
            },
            {
              title: '医疗评分等级',
              dataIndex: 'ylpfdj',
              width: 120
            },
              {
              title: '是否有一票否决的情况',
              dataIndex: 'sfypfjyl',
              width: 120
            },
             
          ]
        },
         {
          title: '护理部',
          children: [
            {
              title: '医疗评分(百分制)',
              dataIndex: 'hlylpf',
              width: 100,
            },
            {
              title: '医疗评分等级',
              dataIndex: 'hlylpfdj',
              width: 120
            },
              {
              title: '教学评分(百分制)',
              dataIndex: 'hljxpfbfz',
              width: 120
            },
            {
              title: '教学评分等级',
              dataIndex: 'hljxpfdl',
              width: 120
            },
              {
              title: '是否有护理责任事故一票否决的情况',
              dataIndex: 'hlhlzrypfj',
              width: 120
            },
             
          ]
        },
          {
          title: '行风建设办公室',
          children: [
            {
              title: '是否存在收受红包的有效投诉',
              dataIndex: 'sshbdts',
              width: 100,
            },
            {
              title: '是否存在收受回扣的有效投诉',
              dataIndex: 'sshkyxts',
              width: 120
            },
              {
              title: '是否违反湖北省医务人员不良执业行为记分管理办法，并被卫生健康部门一次记12分、6分',
              dataIndex: 'blxwjf',
              width: 200
            },
            {
              title: '是否违反《职工守则》有关行风建设规定并被处罚',
              dataIndex: 'wfzgszcf',
              width: 150
            },
            
             
          ]
        },
         {
          title: '科研处',
          children: [
            {
              title: '专业评分（仅研究系列需要填写）(百分制)',
              dataIndex: 'zypfyjxl',
              width: 150,
            },
            {
              title: '专业评分等级（仅研究系列需要填写）',
              dataIndex: 'zypfdjyjxl',
              width: 150
            }
          ]
        },
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
