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
                  label="申报年度"
                  v-bind="formItemLayout"
                >
                  <a-input v-model="queryParams.year" />
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
      <a-table
        ref="TableInfo"
        :columns="columns"
        :data-source="dataSource"
        :rowKey="record => record.id"
        :pagination="pagination"
        @change="handleTableChange"
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
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDocReport(record)"
            >
              导出职称确认表
            </a-button>
          </div>
          <div v-else>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              确认
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
    </a-spin>
    <audit-resultInfo
      ref="userinfo"
      @close="onCloseUserInfoRight"
      :visibleUserInfo="visibleUserInfo_right"
      :userAccount="userAccount_right"
      :dcaYear="dcaYear"
    ></audit-resultInfo>
  </a-card>
</template>

<script>
import AuditResultInfo from '../../common/AuditResultInfo'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
import moment from 'moment';
import { mapState, mapMutations } from 'vuex'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
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
        //userAccount: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 3600,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      visibleUserInfo: false,
      userAccount: '',
      formItemLayout,
      state2: 1,
      userAccount_right: '',
      visibleUserInfo_right: false,
      dcaYear: ''
    }
  },
  components: { AuditResultInfo },
  props: {

  },
  mounted () {
    this.fetch2()
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
      //this.freshTabs()
    },
    showUserInfoRight (record) {
      //debugger
      this.visibleUserInfo_right = true
      this.userAccount_right = record.userAccount
      console.info(record.year)
      this.dcaYear = record.state==2?record.year :''
    },
    onCloseUserInfoRight () {
      this.visibleUserInfo_right = false
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
      let username = this.currentUser.username
      console.info(username)
      this.$get('dcaBReport', {
        userAccount: username,
        isSingel: '1',
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
    reset () {

    },
    handleSave (record) {
      // record.state= 2
      // let jsonStr = JSON.stringify(record)
      let vRecord = {}
      vRecord.id = record.id
      vRecord.state = 2
      vRecord.userAccount = record.userAccount
      vRecord.year= record.year

      let that = this
      that.$confirm({
        title: '是否确认此记录?',
        content: '当您点击确定按钮后，所有数据无法进行更改，请仔细核对进行确认',
        centered: true,
        onOk () {
          that.loading = true
          that.$put('dcaBReport', {
            ...vRecord
          }).then(() => {
            // this.reset()
            that.$message.success('保存成功')
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
    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName,
      },record.year+record.userAccount+".pdf")
    },
    ExportDeclareReport (record) {
      this.$download('dcaBCopyUser/excel', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName,
        sexName: record.gwdj //岗位等级
      },record.userAccount+".pdf")
    },
    ExportDocReport (record) {
      this.$download('dcaBCopyUser/doc', {
        userAccount: record.userAccount,
        year: record.year,
        npPositionName: record.npPositionName,
        gwdj: record.gwdj//岗位等级
      },record.year+'_'+record.userAccount+'_职称表'+".docx")
    },
    fetch () {
      this.loading = true
      //this.queryParams.userAccount = userAccount
      let params = {}
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.pageSize
        params.pageNum = 1
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = 1
      }
      params.sortField = "user_account"
      params.sortOrder = "ascend"
      // params.userAccount = userAccount
      let username = this.currentUser.username

      this.$get('dcaBReport', {
        userAccount: username,
        state: 1,
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
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [
        {
          title: '申报年度',
          dataIndex: 'year',
          width: 100
        },

        {
          title: '系列',
          dataIndex: 'xl',
          width: 100
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
          title: '入职前最高学历',
          dataIndex: 'rzqedu',
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
          title: '中专毕业时间',
          dataIndex: 'zzbysj',
          width: 100
        },
        {
          title: '大专毕业时间',
          dataIndex: 'dzbysj',
          width: 100
        },
        {
          title: '本科毕业时间',
          dataIndex: 'bkbysj',
          width: 100
        },
        {
          title: '硕士毕业时间',
          dataIndex: 'ssbysj',
          width: 100
        },
        {
          title: '博士毕业时间',
          dataIndex: 'bsbysj',
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
          title: '法定资质',
          dataIndex: 'fdzz',
          width: 100
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
          title: '取得湖北省相应专业技术职务资格及时间',
          children: [
            {
              title: '业技术职务资格名称',
              dataIndex: 'zyjszwzg',
              width: 100
            }, {
              title: '时间',
              dataIndex: 'zyjszwzgsj',
              width: 80,
            }
          ]
        },

        {
          title: '岗前培训情况',
          dataIndex: 'gqpxqk',
          width: 100
        },
        {
          title: '规范化医师培训情况',
          dataIndex: 'gfhyspxqk',
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
          title: '支援情况',
          dataIndex: 'help',
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
          fixed: 'right',
          width: 150
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>