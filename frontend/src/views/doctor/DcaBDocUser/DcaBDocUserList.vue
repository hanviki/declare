<template>

  <a-card
    class="card-area"
    title="基本情况及博士后跟踪"
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
            @click="showUserInfo(record)"
          >{{text}}</a>
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
        userAccount: '',
        auditMan: this.dcaYear,
        auditManName: this.dcaType
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 4000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      userAccount: '',
      infoVisiable: false,
      picUrl: ''
    }
  },
  components: {  },
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
    callback () {

    },
    showUserInfo (record) {
      //debugger
      this.infoVisiable = true
      this.userAccount = record.userAccount
      console.info(record.pictureUrl)
      this.picUrl= record.pictureUrl
    },
    onCloseUserInfo () {
      this.infoVisiable = false
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
      // this.freshTabs()
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
    exportCustomExcel () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      let json = this.columns
      // json.splice(this.columns.length-1,1) //移出第一个
      // console.info(json)
      let dataJson = JSON.stringify(json)

      let queryParams = this.queryParams


      this.$export('dcaBDocUser/excel', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...queryParams
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
      this.$get('dcaBDocUser/audit', {
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
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
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
          width: 80,
          scopedSlots: { customRender: 'yggh' }
        },
        {
          title: '手机号',
          dataIndex: 'telephone',
          width: 120,
          scopedSlots: { customRender: 'telephone' }
        },
        {
          title: '华科人事编号',
          dataIndex: 'hkrsbh',
          width: 100,
          scopedSlots: { customRender: 'hkrsbh' }
        },
        {
          title: '招生类型',
          dataIndex: 'zslx',
          width: 100,
          scopedSlots: { customRender: 'zslx' }
        },
        {
          title: '流动站',
          dataIndex: 'ldz',
          width: 100,
          scopedSlots: { customRender: 'ldz' }
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 60,
          scopedSlots: { customRender: 'sexName' }
        },
        {
          title: '出生年月',
          dataIndex: 'birthday',
          width: 120,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '来院工作时间',
          dataIndex: 'schoolDate',
          width: 120,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '身份',
          dataIndex: 'shenfen',
          width: 120,
          scopedSlots: { customRender: 'shenfen' }
        },
        {
          title: '学历',
          dataIndex: 'xueli',
          width: 100,
          scopedSlots: { customRender: 'xueli' }
        },
        {
          title: '博士导师',
          dataIndex: 'boshidaoshi',
          width: 100,
          scopedSlots: { customRender: 'boshidaoshi' }
        },
        {
          title: '毕业时间',
          dataIndex: 'biyeDate',
          width: 120,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '第几个聘期',
          dataIndex: 'pinqiRanknum',
          width: 90,
          scopedSlots: { customRender: 'pinqiRanknum' }
        },
       
        {
          title: '合作导师',
          dataIndex: 'hezuodaoshi',
          width: 90,
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
          width: 120,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        // {
        //   title: '站内工作目标',
        //   dataIndex: 'goal',
        //   width: 130,
        //   scopedSlots: { customRender: 'goal' }
        // },
        {
          title: '延期时间',
          dataIndex: 'yqDate',
          width: 120,
          customRender: (text, row, index) => {
            if(text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '员工组',
          dataIndex: 'yuangongzu',
          width: 100,
          scopedSlots: { customRender: 'yuangongzu' }
        },
        {
          title: '民族',
          dataIndex: 'nation',
          width: 100,
          scopedSlots: { customRender: 'nation' }
        },
        {
          title: '籍贯',
          dataIndex: 'jiguan',
          width: 150,
          scopedSlots: { customRender: 'jiguan' }
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
          width: 120,
          scopedSlots: { customRender: 'country' }
        },
        {
          title: '身份证号',
          dataIndex: 'idCard',
          scopedSlots: { customRender: 'idCard' },
          width: 180,
        },
        {
          title: '聘期',
          dataIndex: 'pinqi',
          width: 80,
          scopedSlots: { customRender: 'pinqi' }
        },{
          title: '站内工作目标',
          dataIndex: 'goal',
          
          scopedSlots: { customRender: 'goal' }
        }, {
          title: '加进站材料',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 100
        }, 
        {
          title: '加出站材料',
          dataIndex: 'fileIdLc',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrlLc} target="_blank" >查看</a>
            }
            return ''
          },
          width: 100
        }, 
        {
          title: '加进站面试答辩情况',
          dataIndex: 'pictureId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.pictureUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 120
        },
       ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
