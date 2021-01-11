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
        bordered
        :scroll="scroll"
      >
        <template
          slot="action"
          slot-scope="text, record"
        >
          <a-button
            type="dashed"
            block
            @click="OpenAudit(record)"
          >
            查看
          </a-button>
        </template>
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
      <a-modal
        title="其他附件"
        :visible="modelVisiable"
        :footer="null"
        @cancel="cancelAudit"
      >
        <a-textarea
          :value="dcaBAttachfile.fileName"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.fileType"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.auditMan"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.auditManName"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.auditDate"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.auditSuggestion"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
        <a-textarea
          :value="dcaBAttachfile.IsUse"
          :rows="12"
          placeholder="其他附件"
        ></a-textarea>
      </a-modal>
    </a-spin>
    <audit-userInfo
      ref="userinfo"
      @close="onCloseUserInfo"
      :visibleUserInfo="visibleUserInfo"
      :userAccount="userAccount"
    ></audit-userInfo>
  </div>
</template>

<script>
import moment from 'moment';
import AuditUserInfo from '../../common/AuditUserInfo'
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
        userAccount: '',
        auditMan: '',
        auditManName: '',
        auditXuhaoE: 0,
        auditXuhaoS: 0,
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 1300,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      modelVisiable: false,
      dcaBAttachfile: {

      },
      visibleUserInfo: false,
      userAccount: ''
    }
  },
  components: { AuditUserInfo },
  props: {
    state: {
      default: 3
    }
  },
  mounted () {
    //this.fetch2()
  },
  methods: {
    moment,
    showUserInfo (text) {
      //debugger
      this.visibleUserInfo = true
      this.userAccount = text
    },

    onCloseUserInfo () {
      this.visibleUserInfo = false
    },
    OpenAudit (record) {
      this.dcaBAttachfile = record
      this.modelVisiable = true
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
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      this.$get('dcaBAttachfile/audit', {
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
    fetch (userAccount) {
      this.loading = true
      this.queryParams.userAccount = userAccount
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
      params.sortField = "userAccount"
      params.sortOrder = "descend"
      params.userAccount = userAccount
      this.$get('dcaBAttachfile/audit', {
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
    cancelAudit () {
      this.modelVisiable = false
    },
    handleTableChange (pagination, filters, sorter) {
      this.sortedInfo = sorter
      this.paginationInfo = pagination
      this.fetch2({
        sortField: "userAccount",
        sortOrder: "descend",
        ...this.queryParams
      })
    },
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
          title: '序号',
          dataIndex: 'auditXuhao',
          width: 60,
        },
        {
          title: '状态',
          dataIndex: 'state',
          width: 100,
          customRender: (text, row, index) => {
            switch (text) {
              case 0:
                return <a-tag color="purple">未提交</a-tag>
              case 1:
                return <a-tag color="green">已提交</a-tag>
              case 2:
                return <a-tag color="red">审核未通过</a-tag>
              case 3:
                return <a-tag color="#f50">已审核</a-tag>
              default:
                return text
            }
          }
        },
         {
          title: '附件名称',
          dataIndex: 'fileName',
          width: 180,
          scopedSlots: { customRender: 'fileName' }
        },
        {
          title: '附件类型',
          dataIndex: 'fileType',
          width: 150,
          scopedSlots: { customRender: 'fileType' }
        },
        {
          title: '审核意见',
          dataIndex: 'auditSuggestion'
        },
        {
          title: '是否用于本次评审',
          dataIndex: 'isUse',
          width: 100
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }, {
          title: '详情',
          key: 'action',
          scopedSlots: { customRender: 'action' },
          width: 100
        }
      ]
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>