<template>
  <div>
    <a-spin :spinning="loading">
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        bordered
        :scroll="scroll"
      >
        <template
          slot="splitHang"
          slot-scope="text, record"
        >
          <p
            style="width:100%;"
            v-for="item in splitStr(text)"
          >{{item}}</p>
        </template>
        <template
          slot="userAccount"
          slot-scope="text, record"
        >
          <a @click="showUserInfo(text)">{{text}}</a>
        </template>
        <template
          slot="action"
          slot-scope="text, record"
        >
          <div v-if="record.state==2">
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDeclareReport(record)"
            >
              导出职称申报表
            </a-button>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportAttachReport(record)"
            >
              导出附件材料
            </a-button>
          </div>
          <div v-else-if="record.state==1">
            <a-button
              v-hasNoPermission="['dca:audit']"
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              退回
            </a-button>
          </div>
        </template>
        <template
          slot="auditMan"
          slot-scope="text, record"
        >
          <div v-if="text=='正常'">
            <a-tag
              color="green"
              @click="showUserInfoRight(record)"
            >正常</a-tag>
          </div>
          <div v-else>
            <a-tag
              color="red"
              @click="showUserInfoRight(record)"
            >异常</a-tag>
          </div>
        </template>
      </a-table>
      <audit-userInfo
        ref="userinfo"
        @close="onCloseUserInfo"
        :visibleUserInfo="visibleUserInfo"
        :userAccount="userAccount"
      ></audit-userInfo>
      <audit-resultInfo
        ref="userinfo"
        @close="onCloseUserInfoRight"
        :visibleUserInfo="visibleUserInfo_right"
        :userAccount="userAccount_right"
        :dcaYear="deaYear"
      ></audit-resultInfo>
    </a-spin>
  </div>
</template>

<script>
import moment from 'moment';
import AuditUserInfo from '../../common/AuditUserInfo'
import AuditResultInfo from '../../common/AuditResultInfo'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
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
        x: 2600,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      userAccount_right: '',
      visibleUserInfo_right: false,
      deaYear: ''
    }
  },
  components: { AuditUserInfo, AuditResultInfo },
  props: {
    state: {
      default: 1
    },
    activeKey: {
      default: 1
    }
  },
  mounted () {
    this.fetch2(this.queryParams)
  },
  methods: {
    moment,
    splitStr (text) {
      return text.split('#')
    },
    search () {
      let { sortedInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch2({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
      })
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
    },
    fetch2 (params = {}) {
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
      params.sortField = "user_account"
      params.sortOrder = "ascend"
      this.$get('dcaBReport', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    ExportDeclareReport (record) {
      this.$download('dcaBCopyUser/excel', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName,
        sexName: record.gwdj //岗位等级
      },record.userAccount+".pdf")
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName
      },record.year+record.userAccount+".pdf")
    },
    handleSave (record) {

      // let jsonStr = JSON.stringify(record)
      let vRecord = {}
      vRecord.id = record.id
      vRecord.userAccount = record.userAccount
      vRecord.state = 0
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true
      this.$put('dcaBReport', {
        ...vRecord
      }).then(() => {
        // this.reset()
        this.$message.success('保存成功')
        this.search()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    showUserInfoRight (record) {
      //debugger
      this.visibleUserInfo_right = true
      this.userAccount_right = record.userAccount
      this.deaYear = record.state==2?record.year :''
    },
    onCloseUserInfoRight () {
      this.visibleUserInfo_right = false
    },
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.paginationInfo.defaultCurrent
      }
      params.sortField = "user_account"
      params.sortOrder = "ascend"
      params.userAccount = userAccount
      this.$get('dcaBReport', {
        state: this.state,
        ...params
      }).then((r) => {
        this.loading = false
        let data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.rows
        this.pagination = pagination
      }
      )
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "user_account",
        sortOrder: "ascend",
        ...this.queryParams
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
          title: '序号',
          dataIndex: 'indexHao'
        },
        {
          title: '顺序号1',
          dataIndex: 'confirmIndex'
        },

        {
          title: '报名档案顺序号2',
          dataIndex: 'baomingIndex'
        },
        {
          title: '系列3',
          dataIndex: 'xl',
        },
        {
          title: '评审分组4',
          dataIndex: 'pingshenfenzu'
        },
        {
          title: '双报标志5',
          dataIndex: 'ifshuangbao'
        },
        {
          title: '人事编号6',
          dataIndex: 'userAccount',
          width: 80
        },

        {
          title: '申报等级7',
          dataIndex: 'gwdj'
        },
        {
          title: '科室8',
          dataIndex: 'ks',
        },
        {
          title: '科室分类9',
          dataIndex: 'kslb',
          width: 130
        },
        {
          title: '姓名10',
          dataIndex: 'userAccountName'
        },
        {
          title: '出生年月11',
          dataIndex: 'birthdaystr'
        },
        {
          title: '年龄12',
          dataIndex: 'age'
        },
        {
          title: '性别13',
          dataIndex: 'sexName'
        },
        {
          title: '学历(位)14',
          dataIndex: 'edu'
        },
        {
          title: '毕业时间15',
          dataIndex: 'eduDate'
        },

        {
          title: '现职务名称16',
          dataIndex: 'positionName',
          width: 100
        },
        {
          title: '聘任时间17',
          dataIndex: 'zygwDate',
          width: 100
        },

        {
          title: '申报职称18',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间19',
          dataIndex: 'schoolDate',
          width: 100
        },

        {
          title: '是否起带头或骨干作用20',
          dataIndex: 'ifdaitou',
          width: 80,
          scopedSlots: { customRender: 'ifdaitou' }
        },
        {
          title: '医疗评分21',
          dataIndex: 'ylpfbfz',
          width: 80
        },
        {
          title: '教学评分22',
          dataIndex: 'jxpf',
          width: 80
        },
        {
          title: '教学科研项目或获奖情况是否符合',
          dataIndex: 'iffuhekeyan',

        },
        {
          title: '第一作者论文情况是否符合',
          dataIndex: 'iffuhediyi',
          width: 100,
          scopedSlots: { customRender: 'iffuhediyi' }
        },
        {
          title: '是否符合必备条件',
          dataIndex: 'iffuhebibei',
          width: 80,
          scopedSlots: { customRender: 'iffuhebibei' }
        },
        {
          title: '名称23',
          dataIndex: 'sciName'
        },
        {
          title: '等级24',
          dataIndex: 'sciDengji'
        },
        {
          title: '排名25',
          dataIndex: 'sciRanknum',
        },

        {
          title: '名称26',
          dataIndex: 'teachName',

        },
        {
          title: '等级27',
          dataIndex: 'teachDengji',
        },
        {
          title: '排名28',
          dataIndex: 'teachRanknum',
        },

        {
          title: '项数29',
          dataIndex: 'patentNum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '实施转让费30',
          dataIndex: 'patentFund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },


        {
          title: 'A 类',
          dataIndex: 'publishA',
          width: 100
        },
        {
          title: 'B 类',
          dataIndex: 'publishB',
          width: 100
        },
        {
          title: 'C 类',
          dataIndex: 'publishC',
          width: 100
        },
        {
          title: 'D 类',
          dataIndex: 'publishD',
          width: 100
        },
        {
          title: 'E 类',
          dataIndex: 'publishE',
          width: 100
        },
        {
          title: 'F 类',
          dataIndex: 'publishF',
          width: 100
        },
        {
          title: 'D类以上',
          dataIndex: 'publishDup',
          width: 100,
        },
        {
          title: 'E类以上',
          dataIndex: 'publishEup',
          width: 100,
        },
        {
          title: 'F类以上',
          dataIndex: 'publishFup',
          width: 100,
        },



        {
          title: '出版书类别',
          dataIndex: 'publicarticle1',
          width: 100
        },
        {
          title: '承担字数(万)',
          dataIndex: 'publicarticle2',
          width: 100
        },



        {
          title: '名称32',
          dataIndex: 'schoolprizeName',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '等级33',
          dataIndex: 'schoolprizeDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名34',
          dataIndex: 'schoolprizeRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间35',
          dataIndex: 'schoolprizeDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },


        {
          title: '等级36',
          dataIndex: 'courseDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名37',
          dataIndex: 'courseRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间38',
          dataIndex: 'courseDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '奖项级别39',
          dataIndex: 'youngName',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '等级40',
          dataIndex: 'youngDengji',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名41',
          dataIndex: 'youngRanknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '时间42',
          dataIndex: 'youngDate',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '类别43',
          dataIndex: 'sciDjlb',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '经费44',
          dataIndex: 'sciDjfund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名45',
          dataIndex: 'sciDjranknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '类别46',
          dataIndex: 'sciJflb',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '经费47',
          dataIndex: 'sciJffund',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '排名48',
          dataIndex: 'sciJfranknum',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },



        {
          title: '等级49',
          dataIndex: 'ylpfdj',
          width: 100
        },
        {
          title: '分数50',
          dataIndex: 'ylpfbfz',
          width: 80,

        },
        {
          title: '等级51',
          dataIndex: 'jxpfdj',
          width: 100
        }, {
          title: '分数52',
          dataIndex: 'jxpf',
        },

        {
          title: '评分合计53',
          dataIndex: 'pfHeji',
          width: 100
        },

        {
          title: '是否担任一年辅导员或班主任',
          dataIndex: 'tutor',
          width: 100
        },
        {
          title: '申报类型',
          dataIndex: 'sblx',
          width: 100,
          scopedSlots: { customRender: 'sblx' }
        },
        {
          title: '达到选择条件一之第几条',
          dataIndex: 'choosepos',
          width: 100,
          scopedSlots: { customRender: 'choosepos' }
        },
        {
          title: '部门审核结果',
          dataIndex: 'auditMan',
          width: 100,
          scopedSlots: { customRender: 'auditMan' }

        },
        {
          title: '材料审核结果',
          dataIndex: 'clshjg',
          width: 100,
          scopedSlots: { customRender: 'clshjg' }
        },
        {
          title: '拟退原因',
          dataIndex: 'ntyy',
          width: 100,
          scopedSlots: { customRender: 'ntyy' }
        },
        {
          title: '科室排名55',
          dataIndex: 'ksrank',
          width: 100,
          scopedSlots: { customRender: 'ksrank' }
        },
        {
          title: '教师资格证',
          dataIndex: 'teacherQualify',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '内聘情况',
          dataIndex: 'npqk',
          width: 100,
        },
        {
          title: '出国情况',
          dataIndex: 'borad',
          width: 150,
        },
        {
          title: '备注',
          dataIndex: 'note',
          width: 100,
        },
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        }
      ];
      let dataJson = JSON.stringify(json)

      this.$export('dcaUserAudit/excelBigTable', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: this.state,
        dataJson: dataJson,
        ...this.queryParams
      })
    },
  },
  computed: {
    columns () {

      let clm = [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
        },
        {
          title: '顺序号',
          dataIndex: 'confirmIndex',
          width: 130,
          scopedSlots: { customRender: 'confirmIndex' }
        },

        {
          title: '报名档案顺序号',
          dataIndex: 'baomingIndex',
          width: 130,
          scopedSlots: { customRender: 'baomingIndex' }
        },
        {
          title: '系列',
          dataIndex: 'xl',
          width: 100
        },
        {
          title: '评审分组',
          dataIndex: 'pingshenfenzu',
          width: 150,
          scopedSlots: { customRender: 'pingshenfenzu' }
        },
        {
          title: '双报标志',
          dataIndex: 'ifshuangbao',
          width: 130
        },
        {
          title: '人事编号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
        },

        {
          title: '申报等级',
          dataIndex: 'gwdj',
          width: 100
        },
        {
          title: '科室',
          dataIndex: 'ks',
          width: 100
        },
        {
          title: '科室分类',
          dataIndex: 'kslb',
          width: 130,
          scopedSlots: { customRender: 'kslb' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr',
          width: 100
        },
        {
          title: '年龄',
          dataIndex: 'age',
          width: 100
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 100
        },
        {
          title: '学历(位)',
          dataIndex: 'edu',
          width: 100
        },
        {
          title: '毕业时间',
          dataIndex: 'eduDate',
          width: 100
        },
        {
          title: '现职务',
          children: [
            {
              title: '现职务名称',
              dataIndex: 'positionName',
              width: 100
            },
            {
              title: '聘任时间',
              dataIndex: 'zygwDate',
              width: 100
            },
          ]
        },

        {
          title: '申报职称',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '来院时间',
          dataIndex: 'schoolDate',
          width: 100
        },
        {
          title: '必备条件',
          children: [
            {
              title: '是否起带头或骨干作用',
              dataIndex: 'ifdaitou',
              width: 100,
              scopedSlots: { customRender: 'ifdaitou' }
            },
            {
              title: '医疗评分',
              dataIndex: 'ylpfbfz',
              width: 80
            },
            {
              title: '教学评分',
              dataIndex: 'jxpf',
              width: 80
            },
            {
              title: '教学科研项目或获奖情况是否符合',
              dataIndex: 'iffuhekeyan',
              width: 100,
              scopedSlots: { customRender: 'iffuhekeyan' }
            },
            {
              title: '第一作者论文情况是否符合',
              dataIndex: 'iffuhediyi',
              width: 100,
              scopedSlots: { customRender: 'iffuhediyi' }
            }
          ]
        },

        {
          title: '是否符合必备条件',
          dataIndex: 'iffuhebibei',
          width: 100,
          scopedSlots: { customRender: 'iffuhebibei' }
        },
        {
          title: '选择条件',
          children: [
            {
              title: '1',
              children: [
                {
                  title: '国家、省部级科研奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'sciName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'sciDengji',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciRanknum',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '2',
              children: [
                {
                  title: '国家、省部级教学获奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'teachName',
                      width: 150,
                      scopedSlots: { customRender: 'splitHang' }

                    },
                    {
                      title: '等级',
                      dataIndex: 'teachDengji',
                      width: 80,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'teachRanknum',
                      width: 60,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '3',
              children: [
                {
                  title: '发明专利',
                  children: [{
                    title: '项数',
                    dataIndex: 'patentNum',
                    width: 100,
                    scopedSlots: { customRender: 'splitHang' }
                  },
                  {
                    title: '实施转让费',
                    dataIndex: 'patentFund',
                    width: 100,
                    scopedSlots: { customRender: 'splitHang' }
                  }]
                },
              ]
            },
            {
              title: '4',
              children: [
                {
                  title: '第一作者或通迅作者论文情况',
                  children: [{
                    title: '其中',
                    children: [
                      {
                        title: 'A 类',
                        dataIndex: 'publishA',
                        width: 100
                      },
                      {
                        title: 'B 类',
                        dataIndex: 'publishB',
                        width: 100
                      },
                      {
                        title: 'C 类',
                        dataIndex: 'publishC',
                        width: 100
                      },
                      {
                        title: 'D 类',
                        dataIndex: 'publishD',
                        width: 100
                      },
                      {
                        title: 'E 类',
                        dataIndex: 'publishE',
                        width: 100
                      },
                      {
                        title: 'F 类',
                        dataIndex: 'publishF',
                        width: 100
                      },
                      {
                        title: 'D类以上',
                        dataIndex: 'publishDup',
                        width: 100,
                        scopedSlots: { customRender: 'publishDup' }
                      },
                      {
                        title: 'E类以上',
                        dataIndex: 'publishEup',
                        width: 100,
                        scopedSlots: { customRender: 'publishEup' }
                      },
                      {
                        title: 'F类以上',
                        dataIndex: 'publishFup',
                        width: 100,
                        scopedSlots: { customRender: 'publishFup' }
                      },
                    ]
                  }
                  ]
                },
              ]
            },
            {
              title: '5',
              children: [
                {
                  title: '出版书类别及字数',
                  children: [
                    {
                      title: '出版书类别',
                      dataIndex: 'publicarticle1',
                      width: 100
                    },
                    {
                      title: '承担字数(万)',
                      dataIndex: 'publicarticle2',
                      width: 100
                    },
                  ]
                },
              ]
            },
            {
              title: '6',
              children: [
                {
                  title: '教学质量奖与成果奖',
                  children: [
                    {
                      title: '名称',
                      dataIndex: 'schoolprizeName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'schoolprizeDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'schoolprizeRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'schoolprizeDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
                {
                  title: '精品课程',
                  children: [
                    {
                      title: '等级',
                      dataIndex: 'courseDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'courseRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'courseDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },

                {
                  title: '教学竞赛获奖',
                  children: [
                    {
                      title: '奖项级别',
                      dataIndex: 'youngName',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '等级',
                      dataIndex: 'youngDengji',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'youngRanknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '时间',
                      dataIndex: 'youngDate',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },
              ]
            },
            {
              title: '7',
              children: [
                {
                  title: '科研项目教改项目',
                  children: [
                    {
                      title: '类别',
                      dataIndex: 'sciDjlb',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '经费',
                      dataIndex: 'sciDjfund',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciDjranknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },]
            },
            {
              title: '8',
              children: [
                {
                  title: '实到校单项科研经费',
                  children: [
                    {
                      title: '类别',
                      dataIndex: 'sciJflb',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '经费',
                      dataIndex: 'sciJffund',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                    {
                      title: '排名',
                      dataIndex: 'sciJfranknum',
                      width: 100,
                      scopedSlots: { customRender: 'splitHang' }
                    },
                  ]
                },]
            },
            {
              title: '9',
              children: [
                {
                  title: '医疗评分',
                  children: [
                    {
                      title: '等级',
                      dataIndex: 'ylpfdj',
                      width: 100
                    },
                    {
                      title: '分数',
                      dataIndex: 'ylpfbfz2',
                      width: 80,
                      customRender: (text, row, index) => {
                        return row.ylpfbfz
                      }
                    },

                  ]
                },
                {
                  title: '教学评分',
                  children: [
                    {
                      title: '等级',
                      dataIndex: 'jxpfdj',
                      width: 100
                    }, {
                      title: '分数',
                      dataIndex: 'jxpf2',
                      width: 80,
                      customRender: (text, row, index) => {
                        return row.jxpf
                      }
                    },
                  ]
                },
                {
                  title: '评分合计',
                  dataIndex: 'pfHeji',
                  width: 100
                },
              ]
            },]
        },
        {
          title: '是否担任一年辅导员或班主任',
          dataIndex: 'tutor',
          width: 100
        },
        {
          title: '申报类型',
          dataIndex: 'sblx',
          width: 100,
          scopedSlots: { customRender: 'sblx' }
        },
        {
          title: '达到选择条件一之第几条',
          dataIndex: 'choosepos',
          width: 100,
          scopedSlots: { customRender: 'choosepos' }
        },
        {
          title: '部门审核结果',
          dataIndex: 'auditMan',
          width: 100,
          scopedSlots: { customRender: 'auditMan' }
        },
        {
          title: '材料审核结果',
          dataIndex: 'clshjg',
          width: 100,
          scopedSlots: { customRender: 'clshjg' }
        },
        {
          title: '拟退原因',
          dataIndex: 'ntyy',
          width: 100,
          scopedSlots: { customRender: 'ntyy' }
        },
        {
          title: '科室排名',
          dataIndex: 'ksrank',
          width: 100,
          scopedSlots: { customRender: 'ksrank' }
        },
        {
          title: '教师资格证',
          dataIndex: 'teacherQualify',
          width: 100,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '内聘情况',
          dataIndex: 'npqk',
          width: 100,
          scopedSlots: { customRender: 'npqk' }
        },
        {
          title: '出国情况',
          dataIndex: 'borad',
          width: 150,
          scopedSlots: { customRender: 'splitHang' }
        },
        {
          title: '备注',
          dataIndex: 'note',
          width: 100,
          scopedSlots: { customRender: 'note' }
        },
        {
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },

          width: 120
        }


      ]
      // if(this.state==1){
      //   clm.push({
      //     title: '操作',
      //     dataIndex: 'action',
      //     scopedSlots: { customRender: 'action' },

      //     width: 100
      //   })
      // }
       let filtersCls=['confirmIndex','pingshenfenzu','kslb','iffuhebibei','sblx','choosepos','auditMan','clshjg','ntyy','ksrank','note']
    let permissions= this.$store.state.account.permissions
    //console.info(permissions)
    if(permissions.includes('dca:audit')){
     clm= clm.filter(p=>!filtersCls.includes(p.dataIndex));
    }
      return clm
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>