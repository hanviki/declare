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
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="ExportDocReport(record)"
            >
              合同制确认表
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
        userAccount: '',
        ks: '中级,初级',
        yuangongzu: '合同制'
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
      if (text == null) return ''
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
      }, record.userAccount + ".pdf")
    },
     ExportDocReport (record) {
      this.$download('dcaBCopyUser/doc', {
       userAccount: record.userAccount,
        year: record.year,
        npPositionName: record.npPositionName,
        gwdj: record.gwdj//岗位等级
      },record.year+'_'+record.userAccount+'_合同制确认表'+".docx")
    },
    ExportAttachReport (record) {
      this.$download('dcaBCopyUser/attach', {
        userAccount: record.userAccount,
        dcaYear: record.year,
        npPositionName: record.npPositionName
      }, record.year + record.userAccount + ".pdf")
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
      this.deaYear = record.state == 2 ? record.year : ''
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
          title: '顺序号',
          dataIndex: 'confirmIndex',

        },

        {
          title: '报名档案顺序号',
          dataIndex: 'baomingIndex',

        },
        {
          title: '职员代码',
          dataIndex: 'userAccount',

        },
        {
          title: '人员类别',
          dataIndex: 'yuangongzu',

        },
        {
          title: '系列',
          dataIndex: 'xl',

        },
        {
          title: '评审分组',
          dataIndex: 'pingshenfenzu',

        },
        {
          title: '申报等级',
          dataIndex: 'gwdj',

        },
        {
          title: '科室',
          dataIndex: 'ks',

        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',

        },
        {
          title: '出生年月',
          dataIndex: 'birthdaystr',

        },

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
        {
          title: '申报职称',
          dataIndex: 'npPositionName',
          width: 100
        },
        {
          title: '入职前最高学历',
          dataIndex: 'rzqedu',
          width: 100
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

        },



        {
          title: '著作或教材',
          dataIndex: 'publicarticle1',

        },
        {
          title: '承担字数(万)',
          dataIndex: 'publicarticle2',

        },

        {
          title: '名称',
          dataIndex: 'sciName',

        },
        {
          title: '等级',
          dataIndex: 'sciDengji',

        },
        {
          title: '排名',
          dataIndex: 'sciRanknum',

        },



        {
          title: '级别',
          dataIndex: 'sciDjlb',

        },
        {
          title: '金额万元',
          dataIndex: 'sciDjfund',

        },
        {
          title: '排名',
          dataIndex: 'sciDjranknum',

        },




        {
          title: '等级',
          dataIndex: 'ylpfdj',
          width: 100
        },
        {
          title: '分数',
          dataIndex: 'ylpfbfz',

        },
        {
          title: '法定资质',
          dataIndex: 'fdzz',

        },

        {
          title: '等级',
          dataIndex: 'jxpfdj',

        }, {
          title: '分数',
          dataIndex: 'jxpf',

        },

        {
          title: '业技术职务资格名称',
          dataIndex: 'zyjszwzg',

        },
        {
          title: '业技术职务资格名称时间',
          dataIndex: 'zyjszwzgsj',

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


        },
         {
          title: '联系方式',
          dataIndex: 'telephone',

        },
        {
          title: '是否符合基本条件',
          dataIndex: 'clshjg',

        },
       
       
        {
          title: '申报类型',
          dataIndex: 'sblx',

        }

      ];
      let dataJson = JSON.stringify(json)

      this.$export('dcaUserAudit/excelBigTable', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        state: this.state,
        dataJson: dataJson,
        excelIndex: 2, 
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
          width: 100,
          scopedSlots: { customRender: 'confirmIndex' },
          // fixed: 'left',
        },

        {
          title: '报名档案顺序号',
          dataIndex: 'baomingIndex',
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
          title: '入职前最高学历',
          dataIndex: 'rzqedu',
          width: 100
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
          title: '法定资质',
          dataIndex: 'fdzz',
          width: 100
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
      // if(this.state==1){
      //   clm.push({
      //     title: '操作',
      //     dataIndex: 'action',
      //     scopedSlots: { customRender: 'action' },

      //     width: 100
      //   })
      // }
      let filtersCls = ['confirmIndex', 'pingshenfenzu', 'kslb', 'iffuhebibei', 'sblx', 'choosepos', 'auditMan', 'clshjg', 'ntyy', 'ksrank', 'note']
      let permissions = this.$store.state.account.permissions
      //console.info(permissions)
      if (permissions.includes('dca:audit')) {
        clm = clm.filter(p => !filtersCls.includes(p.dataIndex));
      }
      return clm
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>