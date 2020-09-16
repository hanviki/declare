<template>
  <a-card title="教学论文出版教材">
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
        slot="paperName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperName')"
            :value="record.paperName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="journalName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'journalName')"
            :value="record.journalName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="journalCode"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'journalCode')"
            :value="record.journalCode"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="paperPublishdate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'paperPublishdate')"
          />
        </div>
      </template>
      <template
        slot="paperShoulu"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperShoulu')"
            :value="record.paperShoulu"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="paperCause"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperCause')"
            :value="record.paperCause"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="isBest"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'isBest')"
            :value="record.isBest"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="otherTimes"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'otherTimes')"
            :value="record.otherTimes"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="authorRank"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'authorRank')"
            :value="record.authorRank"
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
          paperName: '',
          journalName: '',
          journalCode: '',
          paperPublishdate: '',
          paperShoulu: '',
          paperCause: '',
          isBest: '',
          otherTimes: '',
          authorRank: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.paperName != '' || element.journalName != '' || element.journalCode != '' || element.paperPublishdate != '' || element.paperShoulu != '' || element.paperCause != '' || element.isBest != '' || element.otherTimes != '' || element.authorRank != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBPaperspublish/addNew', {
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
            if (element.paperName != '' || element.journalName != '' || element.journalCode != '' || element.paperPublishdate != '' || element.paperShoulu != '' || element.paperCause != '' || element.isBest != '' || element.otherTimes != '' || element.authorRank != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBPaperspublish/addNew', {
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
      this.$get('dcaBPaperspublish/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            paperName: '',
            journalName: '',
            journalCode: '',
            paperPublishdate: '',
            paperShoulu: '',
            paperCause: '',
            isBest: '',
            otherTimes: '',
            authorRank: '',
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
        title: '论文名',
        dataIndex: 'paperName',
        width: 200,
        scopedSlots: { customRender: 'paperName' }
      },
      {
        title: '期刊名',
        dataIndex: 'journalName',
        width: 200,
        scopedSlots: { customRender: 'journalName' }
      },
      {
        title: '期刊号',
        dataIndex: 'journalCode',
        width: 200,
        scopedSlots: { customRender: 'journalCode' }
      },
      {
        title: '发表年月',
        dataIndex: 'paperPublishdate',
        width: 130,
        scopedSlots: { customRender: 'paperPublishdate' }
      },
      {
        title: '收录情况',
        dataIndex: 'paperShoulu',
        width: 130,
        scopedSlots: { customRender: 'paperShoulu' }
      },
      {
        title: '影响因子',
        dataIndex: 'paperCause',
        width: 130,
        scopedSlots: { customRender: 'paperCause' }
      },
      {
        title: '是否一流期刊',
        dataIndex: 'isBest',
        width: 130,
        scopedSlots: { customRender: 'isBest' }
      },
      {
        title: '他引次数',
        dataIndex: 'otherTimes',
        width: 130,
        scopedSlots: { customRender: 'otherTimes' }
      },
      {
        title: '第一或通讯作者',
        dataIndex: 'authorRank',
        width: 130,
        scopedSlots: { customRender: 'authorRank' }
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
              return <a-tag color="green">审核未通过</a-tag>
            case 3:
              return <a-tag color="green">已审核</a-tag>
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
