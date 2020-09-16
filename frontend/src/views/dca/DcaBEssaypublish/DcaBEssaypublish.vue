<template>
  <a-card title="论文出版">
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
      :scroll="{x:1800}"
    >
      <template
        slot="essayName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'essayName')"
            :value="record.essayName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eassyJournalname"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eassyJournalname')"
            :value="record.eassyJournalname"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eassyPublishname"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eassyPublishname')"
            :value="record.eassyPublishname"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eassyStartpage"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'eassyStartpage')"
            :value="record.eassyStartpage"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="eassyEndpage"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'eassyEndpage')"
            :value="record.eassyEndpage"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="eassyJournalcode"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eassyJournalcode')"
            :value="record.eassyJournalcode"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eassyJournalgrade"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eassyJournalgrade')"
            :value="record.eassyJournalgrade"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eassyPublishdate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'eassyPublishdate')"
          />
        </div>
      </template>
      <template
        slot="eassyRankname"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eassyRankname')"
            :value="record.eassyRankname"
          >
          </a-textarea>
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
          essayName: '',
          eassyJournalname: '',
          eassyPublishname: '',
          eassyStartpage: '',
          eassyEndpage: '',
          eassyJournalcode: '',
          eassyJournalgrade: '',
          eassyPublishdate: '',
          eassyRankname: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.essayName != '' || element.eassyJournalname != '' || element.eassyPublishname != '' || element.eassyStartpage != '' || element.eassyEndpage != '' || element.eassyJournalcode != '' || element.eassyJournalgrade != '' || element.eassyPublishdate != '' || element.eassyRankname != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBEssaypublish/addNew', {
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
            if (element.essayName != '' || element.eassyJournalname != '' || element.eassyPublishname != '' || element.eassyStartpage != '' || element.eassyEndpage != '' || element.eassyJournalcode != '' || element.eassyJournalgrade != '' || element.eassyPublishdate != '' || element.eassyRankname != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBEssaypublish/addNew', {
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
      this.$get('dcaBEssaypublish/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            essayName: '',
            eassyJournalname: '',
            eassyPublishname: '',
            eassyStartpage: '',
            eassyEndpage: '',
            eassyJournalcode: '',
            eassyJournalgrade: '',
            eassyPublishdate: '',
            eassyRankname: '',
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
        title: '论著名称',
        dataIndex: 'essayName',
        width: 300,
        scopedSlots: { customRender: 'essayName' }
      },
      {
        title: '期刊名称',
        dataIndex: 'eassyJournalname',
        width: 200,
        scopedSlots: { customRender: 'eassyJournalname' }
      },
      {
        title: '出版社',
        dataIndex: 'eassyPublishname',
        width: 200,
        scopedSlots: { customRender: 'eassyPublishname' }
      },
      {
        title: '起始页码',
        dataIndex: 'eassyStartpage',
        width: 120,
        scopedSlots: { customRender: 'eassyStartpage' }
      },
      {
        title: '截至页码',
        dataIndex: 'eassyEndpage',
        width: 120,
        scopedSlots: { customRender: 'eassyEndpage' }
      },
      {
        title: '刊号',
        dataIndex: 'eassyJournalcode',
        width: 100,
        scopedSlots: { customRender: 'eassyJournalcode' }
      },
      {
        title: '期刊级别',
        dataIndex: 'eassyJournalgrade',
        width: 100,
        scopedSlots: { customRender: 'eassyJournalgrade' }
      },
      {
        title: '发表年月',
        dataIndex: 'eassyPublishdate',
        width: 130,
        scopedSlots: { customRender: 'eassyPublishdate' }
      },
      {
        title: '第几作者',
        dataIndex: 'eassyRankname',
        width: 80,
        scopedSlots: { customRender: 'eassyRankname' }
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
