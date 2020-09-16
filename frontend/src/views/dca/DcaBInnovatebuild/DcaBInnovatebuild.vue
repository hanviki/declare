<template>
  <a-card title="改革及建设项目">
    <div>
      <a-button
        @click="handleAdd"
        type="primary"
        :loading="loading"
      >添加行</a-button>
      <a-button
        @click="handleDelete"
        type="primary"
        :loading="loading"
      >删除行</a-button>
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      bordered
      :scroll="{x:1600}"
    >
      <template
        slot="projectName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectName')"
            :value="record.projectName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="projectType"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectType')"
            :value="record.projectType"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="projectSource"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectSource')"
            :value="record.projectSource"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="contractFund"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'contractFund')"
            :value="record.contractFund"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="realFund"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'realFund')"
            :value="record.realFund"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="auditDate2"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'auditDate2')"
          />
        </div>
      </template>
      <template
        slot="startDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'startDate')"
          />
        </div>
      </template>
      <template
        slot="endDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'endDate')"
          />
        </div>
      </template>
      <template
        slot="rankNum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'rankNum')"
            :value="record.rankNum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="isUse"
        slot-scope="text, record"
      >
        <a-checkbox
          @change="e => onIsUseChange(e,record,'isUse')"
          :checked="text"
        ></a-checkbox>
      </template>
    </a-table>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-card>
</template>

<script>
import moment from 'moment';
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
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
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAdd () {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          projectName: '',
          projectType: '',
          projectSource: '',
          contractFund: '',
          realFund: '',
          auditDate2: '',
          startDate: '',
          endDate: '',
          rankNum: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate2 != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBInnovatebuild/addNew', {
          jsonStr: jsonStr,
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
          this.fetch()
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleSubmit () {
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将不能修改',
        centered: true,
        onOk () {
          const dataSource = [...that.dataSource]
          let dataAdd = []
          dataSource.forEach(element => {
            if (element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate2 != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBInnovatebuild/addNew', {
              jsonStr: jsonStr,
              state: 1
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
              this.fetch()
              that.CustomVisiable = false //提交之后 不能再修改
              that.loading = false
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })


    },
    handleDelete () {
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
          let dcaBPatentIds = that.selectedRowKeys.join(',')
          const dataSource = [...that.dataSource];
          let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
          that.dataSource = new_dataSource
          that.$message.success('删除成功')
          that.selectedRowKeys = []
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    fetch () {
      this.$get('dcaBInnovatebuild/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            projectName: '',
            projectType: '',
            projectSource: '',
            contractFund: '',
            realFund: '',
            auditDate2: '',
            startDate: '',
            endDate: '',
            rankNum: '',
            isUse: false
          })
          this.idNums = this.idNums + 4
        }
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '项目名称',
        dataIndex: 'projectName',
        width: 200,
        scopedSlots: { customRender: 'projectName' }
      },
      {
        title: '项目性质',
        dataIndex: 'projectType',
        width: 130,
        scopedSlots: { customRender: 'projectType' }
      },
      {
        title: '项目来源',
        dataIndex: 'projectSource',
        width: 130,
        scopedSlots: { customRender: 'projectSource' }
      },
      {
        title: '合同经费',
        dataIndex: 'contractFund',
        width: 130,
        scopedSlots: { customRender: 'contractFund' }
      },
      {
        title: '实到经费',
        dataIndex: 'realFund',
        width: 130,
        scopedSlots: { customRender: 'realFund' }
      },
      {
        title: '批准年月',
        dataIndex: 'auditDate2',
        width: 130,
        scopedSlots: { customRender: 'auditDate2' }
      },
      {
        title: '起始日期',
        dataIndex: 'startDate',
        width: 130,
        scopedSlots: { customRender: 'startDate' }
      },
      {
        title: '终止日期',
        dataIndex: 'endDate',
        width: 130,
        scopedSlots: { customRender: 'endDate' }
      },
      {
        title: '本人排名',
        dataIndex: 'rankNum',
        width: 130,
        scopedSlots: { customRender: 'rankNum' }
      },
      {
        title: '状态',
        dataIndex: 'state',
        width: 80,
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
        title: '审核意见',
        dataIndex: 'auditSuggestion'
      },
      {
        title: '是否用于本次评审',
        dataIndex: 'isUse',
        scopedSlots: { customRender: 'isUse' },
        width: 80
      }]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
