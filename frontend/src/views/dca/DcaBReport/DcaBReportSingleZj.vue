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
        x: 2600,
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
          title: '职员代码',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' },
          //     fixed: 'left',
        },
        {
          title: '人员类别',
          dataIndex: 'yuangongzu',
          width: 100
        },
        {
          title: '系列',
          dataIndex: 'xl',
          width: 80,
          //     fixed: 'left'
        },
        {
          title: '评审分组',
          dataIndex: 'pingshenfenzu',
          width: 180,
          scopedSlots: { customRender: 'pingshenfenzu' },
          //    fixed: 'left',
        },
        {
          title: '申报等级',
          dataIndex: 'gwdj',
          width: 60,
          //    fixed: 'left',
        },
        {
          title: '科室',
          dataIndex: 'ks',
          width: 80,
          //    fixed: 'left',
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80,
          //    fixed: 'left',
        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr',
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
          title: '最高学历',
          dataIndex: 'edu',
          width: 100
        },
        {
          title: '最高学历毕业时间',
          dataIndex: 'eduDate',
          width: 100
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
          title: '论文',
          children: [
            {
              title: 'SCI',
              dataIndex: 'publishA',
              width: 100
            },
            {
              title: '权威',
              dataIndex: 'publishD',
              width: 100
            },
            {
              title: '核心',
              dataIndex: 'publishE',
              width: 100
            },
            {
              title: '正式',
              dataIndex: 'publishF',
              width: 100
            }
          ]
        },

        {
          title: '著作或教材',
          children: [
            {
              title: '著作或教材',
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
        {
          title: '科研获奖',
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


        {
          title: '科研课题',
          children: [
            {
              title: '级别',
              dataIndex: 'sciDjlb',
              width: 100,
              scopedSlots: { customRender: 'splitHang' }
            },
            {
              title: '金额万元',
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
        },


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
          title: '中级水平能力测试情况',
          dataIndex: 'zjspnlceqk',
          width: 100
        },

        {
          title: '部门审核结果',
          dataIndex: 'auditMan',
          width: 100,
          scopedSlots: { customRender: 'auditMan' }

        },
        {
          title: '是否符合基本条件',
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
          title: '联系方式',
          dataIndex: 'telephone',
          width: 100
        },
        {
          title: '申报类型',
          dataIndex: 'sblx',
          width: 100,
          scopedSlots: { customRender: 'sblx' }
        },
        {
          title: '操作',
          dataIndex: 'action',
          scopedSlots: { customRender: 'action' },

          width: 120
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>