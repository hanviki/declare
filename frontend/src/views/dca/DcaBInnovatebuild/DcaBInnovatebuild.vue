<template>
  <a-card title="改革及建设项目">
    <div>
      <a-button
        @click="handleAdd"
        type="primary"
        :loading="loading"
        v-show="CustomVisiable"
      >添加行</a-button>
      <a-button
        @click="handleDelete"
        type="primary"
        :loading="loading"
        v-show="CustomVisiable"
      >删除行</a-button>
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      bordered
    >
      <template
        slot="projectName"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectName')"
            :value="record.projectName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="projectType"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectType')"
            :value="record.projectType"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="projectSource"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'projectSource')"
            :value="record.projectSource"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="contractFund"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'contractFund')"
            :value="record.contractFund"
            :precision="2"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="realFund"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'realFund')"
            :value="record.realFund"
            :precision="2"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="auditDate"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'auditDate')"
        />
      </template>
      <template
        slot="startDate"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'startDate')"
        />
      </template>
      <template
        slot="endDate"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'endDate')"
        />
      </template>
      <template
        slot="rankNum"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'rankNum')"
            :value="record.rankNum"
            :precision="2"
          >
          </a-input-number>
        </div>
      </template>
    </a-table>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
        v-show="CustomVisiable"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
        v-show="CustomVisiable"
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
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
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
          auditDate: '',
          startDate: '',
          endDate: '',
          rankNum: '',
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
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
            if (element.projectName != '' || element.projectType != '' || element.projectSource != '' || element.contractFund != '' || element.realFund != '' || element.auditDate != '' || element.startDate != '' || element.endDate != '' || element.rankNum != '') {
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
        if (data.rows.length > 0
        ) {
          if (data.rows[0].jzState === 0) {
            this.CustomVisiable = true
          }
          //this.idNums = data.rows[data.rows.length - 1].id
        }
        else {
          this.CustomVisiable = true
        }
        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            projectName: '',
            projectType: '',
            projectSource: '',
            contractFund: '',
            realFund: '',
            auditDate: '',
            startDate: '',
            endDate: '',
            rankNum: '',
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
        width: 120,
        scopedSlots: { customRender: 'projectName' }
      },
      {
        title: '项目性质',
        dataIndex: 'projectType',
        width: 120,
        scopedSlots: { customRender: 'projectType' }
      },
      {
        title: '项目来源',
        dataIndex: 'projectSource',
        width: 120,
        scopedSlots: { customRender: 'projectSource' }
      },
      {
        title: '合同经费',
        dataIndex: 'contractFund',
        width: 120,
        scopedSlots: { customRender: 'contractFund' }
      },
      {
        title: '实到经费',
        dataIndex: 'realFund',
        width: 120,
        scopedSlots: { customRender: 'realFund' }
      },
      {
        title: '批准年月',
        dataIndex: 'auditDate',
        width: 120,
        scopedSlots: { customRender: 'auditDate' }
      },
      {
        title: '起始日期',
        dataIndex: 'startDate',
        width: 120,
        scopedSlots: { customRender: 'startDate' }
      },
      {
        title: '终止日期',
        dataIndex: 'endDate',
        width: 120,
        scopedSlots: { customRender: 'endDate' }
      },
      {
        title: '本人排名',
        dataIndex: 'rankNum',
        width: 120,
        scopedSlots: { customRender: 'rankNum' }
      },
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
