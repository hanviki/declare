<template>
  <a-card title="科研论文">
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
      :scroll="{x:1500}"
    >
      <template
        slot="paperName"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperName')"
            :value="record.paperName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="journalName"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'journalName')"
            :value="record.journalName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="journalCode"
        slot-scope="textw, record"
      >
        <div key="jzContent">
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
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'paperPublishdate')"
        />
      </template>
      <template
        slot="paperShoulu"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperShoulu')"
            :value="record.paperShoulu"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="paperCause"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'paperCause')"
            :value="record.paperCause"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="isBest"
        slot-scope="textw, record"
      >
        <div key="jzContent">

          <a-switch
            checked-children="是"
            un-checked-children="否"
            @change="(e1,f) => inputCheckChange(e1,f,record,'isBest')"
            :checked="record.isBest=='是'"
          >
          </a-switch>
        </div>
      </template>
      <template
        slot="otherTimes"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'otherTimes')"
            :value="record.otherTimes"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="authorRank"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'authorRank')"
            :value="record.authorRank"
          >
          </a-textarea>
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
          paperName: '',
          journalName: '',
          journalCode: '',
          paperPublishdate: '',
          paperShoulu: '',
          paperCause: '',
          isBest: '',
          otherTimes: '',
          authorRank: '',
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
        this.$post('dcaBSciencepublish/addNew', {
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
    inputCheckChange (blFlag, f, record, filedName) {
      record[filedName] = blFlag ? '是' : '否'
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
            that.$post('dcaBSciencepublish/addNew', {
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
      this.$get('dcaBSciencepublish/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows
        if (data.rows.length > 0
        ) {
          if (data.rows[0].state === 0) {
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
            paperName: '',
            journalName: '',
            journalCode: '',
            paperPublishdate: '',
            paperShoulu: '',
            paperCause: '',
            isBest: '',
            otherTimes: '',
            authorRank: '',
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
        width: 120,
        scopedSlots: { customRender: 'paperName' }
      },
      {
        title: '期刊名',
        dataIndex: 'journalName',
        width: 120,
        scopedSlots: { customRender: 'journalName' }
      },
      {
        title: '期刊号',
        dataIndex: 'journalCode',
        width: 120,
        scopedSlots: { customRender: 'journalCode' }
      },
      {
        title: '发表年月',
        dataIndex: 'paperPublishdate',
        width: 120,
        scopedSlots: { customRender: 'paperPublishdate' }
      },
      {
        title: '收录情况',
        dataIndex: 'paperShoulu',
        width: 120,
        scopedSlots: { customRender: 'paperShoulu' }
      },
      {
        title: '影响因子',
        dataIndex: 'paperCause',
        width: 120,
        scopedSlots: { customRender: 'paperCause' }
      },
      {
        title: '是否一流期刊',
        dataIndex: 'isBest',
        width: 120,
        scopedSlots: { customRender: 'isBest' }
      },
      {
        title: '他引次数',
        dataIndex: 'otherTimes',
        width: 120,
        scopedSlots: { customRender: 'otherTimes' }
      },
      {
        title: '第一或通讯作者',
        dataIndex: 'authorRank',
        width: 120,
        scopedSlots: { customRender: 'authorRank' }
      },
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
