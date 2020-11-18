<template>
 <a-card class="card-area" title="">
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
        tab="待处理"
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
            slot="splitHang"
            slot-scope="text, record"
          >
            <p
              style="width:100%;"
              v-for="item in splitStr(text)"
            >{{item}}</p>
          </template>
          <template
            slot="confirmIndex"
            slot-scope="text, record"
          >
            <div v-if="record.state==3">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'confirmIndex')"
                :value="record.confirmIndex"
                :precision="0"
              >
              </a-input-number>
            </div>
          </template>
          <template
            slot="danganIndex"
            slot-scope="text, record"
          >
            <div v-if="record.state==3">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'danganIndex')"
                :value="record.danganIndex"
                :precision="0"
              >
              </a-input-number>
            </div>
          </template>
          <template
            slot="baomingIndex"
            slot-scope="text, record"
          >
            <div v-if="record.state==3">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'baomingIndex')"
                :value="record.baomingIndex"
                :precision="0"
              >
              </a-input-number>
            </div>
          </template>

          <template
            slot="pingshenfenzu"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'pingshenfenzu')"
                :value="record.pingshenfenzu"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="ifshuangbao"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'ifshuangbao')"
                :value="record.ifshuangbao"
              >
              </a-textarea>
            </div>
          </template>

          <template
            slot="kslb"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'kslb')"
                :value="record.kslb"
              >
              </a-textarea>
            </div>
          </template>
 <template
            slot="note"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'note')"
                :value="record.note"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="ifdaitou"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-switch
                checked-children="是"
                un-checked-children="否"
                @change="(e1,f) => inputCheckChange(e1,f,record,'ifdaitou')"
                :checked="record.ifdaitou=='是'"
              >
              </a-switch>
            </div>
          </template>

          <template
            slot="iffuhekeyan"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-switch
                checked-children="是"
                un-checked-children="否"
                @change="(e1,f) => inputCheckChange(e1,f,record,'iffuhekeyan')"
                :checked="record.iffuhekeyan=='是'"
              >
              </a-switch>
            </div>
          </template>
          <template
            slot="iffuhediyi"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-switch
                checked-children="是"
                un-checked-children="否"
                @change="(e1,f) => inputCheckChange(e1,f,record,'iffuhediyi')"
                :checked="record.iffuhediyi=='是'"
              >
              </a-switch>
            </div>
          </template>
          <template
            slot="iffuhebibei"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'iffuhebibei')"
                :value="record.iffuhebibei"
              >
              </a-textarea>
            </div>
          </template>

          <template
            slot="patentNum"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'patentNum')"
                :value="record.patentNum"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="patentFund"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'patentFund')"
                :value="record.patentFund"
              >
              </a-textarea>
            </div>
          </template>

          <template
            slot="publishDup"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'publishDup')"
                :value="record.publishDup"
                :precision="2"
              >
              </a-input-number>
            </div>
          </template>
          <template
            slot="publishEup"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'publishEup')"
                :value="record.publishEup"
                :precision="2"
              >
              </a-input-number>
            </div>
          </template>
          <template
            slot="publishFup"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-input-number
                @blur="e => inputChange(e.target.value,record,'publishFup')"
                :value="record.publishFup"
                :precision="2"
              >
              </a-input-number>
            </div>
          </template>

          <template
            slot="sblx"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-select
                :value="record.sblx==null?'':record.sblx"
                style="width: 100%"
                @change="(e,f) => handleSelectChange(e,f,record,'sblx')"
              >
                <a-select-option value="顺升">
                  顺升
                </a-select-option>
                <a-select-option value="单靠">
                  单靠
                </a-select-option>
                <a-select-option value="援疆">
                  援疆
                </a-select-option>
                <a-select-option value="援藏">
                  援藏
                </a-select-option>
              </a-select>
            </div>
          </template>
          <template
            slot="choosepos"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'choosepos')"
                :value="record.choosepos"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="clshjg"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-select
                :value="record.clshjg==null?'':record.clshjg"
                style="width: 100%"
                @change="(e,f) => handleSelectChange(e,f,record,'clshjg')"
              >
                <a-select-option value="正常">
                  正常
                </a-select-option>
                <a-select-option value="拟退">
                  拟退
                </a-select-option>
              </a-select>
            </div>
          </template>
          <template
            slot="ntyy"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'ntyy')"
                :value="record.ntyy"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="ksrank"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'ksrank')"
                :value="record.ksrank"
              >
              </a-textarea>
            </div>
          </template>

          <template
            slot="npqk"
            slot-scope="text, record"
          >
            <div v-if="record.state==3 || record.state==1">
              {{text}}
            </div>
            <div v-else>
              <a-textarea
                @blur="e => inputChange(e.target.value,record,'npqk')"
                :value="record.npqk"
              >
              </a-textarea>
            </div>
          </template>
          <template
            slot="action"
            slot-scope="text, record"
          >
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleSave(record)"
            >
              保存
            </a-button>
            <a-button
              style="width:100%;padding-left:2px;padding-right:2px;"
              type="dashed"
              block
              @click="handleAudit(record)"
            >
              推送用户确认
            </a-button>
          </template>
        </a-table>
      </a-tab-pane>
      <a-tab-pane
        key="2"
        tab="待确认"
        :forceRender="true"
      >
        <dcaBReport-unsure
          ref="TableInfo2"
          :state="1"
        >
        </dcaBReport-unsure>
      </a-tab-pane>
      <a-tab-pane
        key="3"
        tab="已确认"
        :forceRender="true"
      >
        <dcaBReport-unsure
          ref="TableInfo3"
          :state="2"
        >
        </dcaBReport-unsure>
      </a-tab-pane>
    </a-tabs>
    <audit-userInfo
      ref="userinfo"
      @close="onCloseUserInfo"
      :visibleUserInfo="visibleUserInfo"
      :userAccount="userAccount"
    ></audit-userInfo>
  </a-spin>
 </a-card>
</template>

<script>
import moment from 'moment';
import DcaBReportUnsure from './DcaBReportUnsure'
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
        userAccount: ''
      },
      sortedInfo: null,
      paginationInfo: null,
      scroll: {
        x: 4000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
      listAuditInfo: [{
        fieldName: 'edu'
      },
      {
        fieldName: 'eduDate',
      },
      {
        fieldName: 'ylpfbfz',
      },
       {
        fieldName: 'ylpfdj',
      },
      {
        fieldName: 'jxpf'

      },
       {
        fieldName: 'jxpfdj'

      },
      {
        fieldName: 'sciName',

      },
      {
        fieldName: 'sciDengji',

      },
      {
        fieldName: 'sciRanknum',

      },
      {
        fieldName: 'teachName',

      },
      {
        fieldName: 'teachDengji',
      },
      {
        fieldName: 'teachRanknum',
      },
      {
        fieldName: 'publishA',

      },
      {
        fieldName: 'publishB',
      },
      {
        fieldName: 'publishA',

      },
      {
        fieldName: 'publishC',
      },
      {
        fieldName: 'publishD',

      },
      {
        fieldName: 'publishE',
      }
        ,
      {
        fieldName: 'publishF',
      }
        ,
        {
        fieldName: 'publishDup',
      }
       ,
        {
        fieldName: 'publishEup',
      } ,
        {
        fieldName: 'publishFup',
      }
        ,
      {
        fieldName: 'publicarticle1',
      }
        ,
      {
        fieldName: 'publicarticle2',
      }
        ,
      {
        fieldName: 'schoolprizeName',
      }
        ,
      {
        fieldName: 'schoolprizeDengji',
      }
        ,
      {
        fieldName: 'schoolprizeRanknum',
      }
        ,
      {
        fieldName: 'schoolprizeDate',
      }
        ,
      {
        fieldName: 'courseName',
      }
        ,
      {
        fieldName: 'courseDengji',
      }
        ,
      {
        fieldName: 'courseRanknum',
      }
        ,
      {
        fieldName: 'courseDate',
      }
        ,
      {
        fieldName: 'youngName',
      }
        ,
      {
        fieldName: 'youngDengji',
      }
        ,
      {
        fieldName: 'youngDate',
      }
        ,
      {
        fieldName: 'youngRanknum',
      }
        ,
      {
        fieldName: 'sciDjlb',
      }
        ,
      {
        fieldName: 'sciDjfund',
      },
      {
        fieldName: 'sciDjranknum',
      },
      {
        fieldName: 'sciJflb',
      },
      {
        fieldName: 'sciJffund',
      },
      {
        fieldName: 'sciJfranknum',
      },
      {
        fieldName: 'pfHeji',
      },
      {
        fieldName: 'tutor',
      },
      {
        fieldName: 'teacherQualify',
      },
      {
        fieldName: 'borad',
      }

      ], // 当前用户包含的审核数据
    }
  },
 components: { DcaBReportUnsure, AuditUserInfo },
  mounted () {
    // this.fetchUseraudit()
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
    splitStr (text) {
      return text.split('#')
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
      this.selectedRowKeys = selectedRowKeys
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
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
    handleSave (record) {
      record.state = 0
      // let jsonStr = JSON.stringify(record)
      let vRecord = record
      delete vRecord.dcaBAuditdynamicList  //移出属性
      delete vRecord.createTime  //移出属性
      delete vRecord.modifyTime  //移出属性
      // vRecord.dcaBAuditdynamicList=''
      this.loading = true
      this.$post('dcaBReport', {
        ...vRecord
      }).then(() => {
        // this.reset()
        this.$message.success('保存成功')
        // this.fetch()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleAudit (record) {
      let that = this
      let vRecord = record
      delete vRecord.dcaBAuditdynamicList  //移出属性
      delete vRecord.createTime
       delete vRecord.modifyTime
      vRecord.state = 1
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将提交申请人确认',
        centered: true,
        onOk () {
          let jsonStr = JSON.stringify(vRecord)
          that.loading = true
          that.$post('dcaBReport', {
            ...record
          }).then(() => {
            //this.reset()
            that.$message.success('提交成功')
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
      return '否'
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

      this.$export('dcaUserAudit/excelBigTable', {
        sortField: 'user_account',
        sortOrder: 'ascend',
        dataJson: dataJson,
        ...this.queryParams
      })
    },
    fetchUseraudit () {
      this.listAuditInfo.forEach(element => {
        this.columns.push({
          title: element.fieldTitle,
          dataIndex: element.fieldName,
          width: 130,
          scopedSlots: { customRender: element.fieldName }
        });

      });
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
      this.$get('dcaUserAudit/userAuditReport', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        const pagination = { ...this.pagination }
        pagination.total = data.total

        data.rows.forEach(element => {
          let auditList = element.dcaBAuditdynamicList
          // console.info(auditList)
          if (auditList.length > 0) {
            // console.info(this.listAuditInfo)
            this.listAuditInfo.forEach(element2 => {
              console.info('element2' + element2)
              let lire = auditList.filter(p => p.auditTitletype == element2.fieldName);
              console.info(lire);
              if (lire.length > 0) {
                element[element2.fieldName] = lire[0].auditResult
              }
              else {
                element[element2.fieldName] = ''
              }
            });
          }
          else {
            this.listAuditInfo.forEach(element2 => {
              element[element2.fieldName] = ''
            });
          }

        });
        this.dataSource = data.rows
        //console.info(data.rows)
        this.pagination = pagination
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '确认顺序号',
          dataIndex: 'confirmIndex',
          width: 100,
          scopedSlots: { customRender: 'confirmIndex' },
        // fixed: 'left',
        },
        {
          title: '档案袋顺序号',
          dataIndex: 'danganIndex',
          width: 100,
          scopedSlots: { customRender: 'danganIndex' },
//fixed: 'left',
        },
        {
          title: '报名顺序号',
          dataIndex: 'baomingIndex',
          width: 100,
     //     fixed: 'left',
          scopedSlots: { customRender: 'baomingIndex' }
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
          width: 100,
          scopedSlots: { customRender: 'pingshenfenzu' },
      //    fixed: 'left',
        },
        {
          title: '双报标志',
          dataIndex: 'ifshuangbao',
          width: 40,
     //     fixed: 'left',
        },
        {
          title: '人事编号',
          dataIndex: 'userAccount',
          width: 80,
     //     fixed: 'left',
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
          title: '科室分类',
          dataIndex: 'kslb',
          width: 130,
          scopedSlots: { customRender: 'kslb' },
       //   fixed: 'left',
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
          title: '年龄',
          dataIndex: 'age',
          width: 60
        },
        {
          title: '性别',
          dataIndex: 'sexName',
          width: 60
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
              width: 80,
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
          width: 80,
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
                      width: 170,
                      scopedSlots: { customRender: 'splitHang' }

                    },
                    {
                      title: '等级',
                      dataIndex: 'teachDengji',
                      width: 60,
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
                    scopedSlots: { customRender: 'patentNum' }
                  },
                  {
                    title: '实施转让费',
                    dataIndex: 'patentFund',
                    width: 100,
                    scopedSlots: { customRender: 'patentFund' }
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
