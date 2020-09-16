<template>
  <a-card title="社会兼职">
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
    >
      <template
        slot="jzStartTime"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'jzStartTime')"
          />
        </div>
      </template>
      <template
        slot="jzEndTime"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'jzEndTime')"
          />
        </div>
      </template>
      <template
        slot="jzContent"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'jzContent')"
            :value="record.jzContent"
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
      dcaBParttimeVisiable: false,
      idNums: 10000
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    onSelectChange (selectedRowKeys, selectedRows) {
      console.log(selectedRows)
      if (selectedRows[0].state != 3) {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    handleAdd () {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          isUse: false
          // jzStartTime: '',
          // jzEndTime: '',
          // jzContent: ''
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.jzStartTime != '' || element.jzEndTime != '' || element.jzContent != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBParttimejob/addNew', {
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
            if (element.jzStartTime != '' || element.jzEndTime != '' || element.jzContent != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBParttimejob/addNew', {
              jsonStr: jsonStr,
              state: 1
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
              this.fetch()
              that.dcaBParttimeVisiable = false //提交之后 不能再修改
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
      this.$get('dcaBParttimejob', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows
        if (data.rows.length > 0) {
          if (data.rows[0].state === 0) {
            this.dcaBParttimeVisiable = true
          }
          //this.idNums = data.rows[data.rows.length - 1].id
        }
        else {
          this.dcaBParttimeVisiable = true
        }
        for (let i = 0; i < 4; i++) {
          this.dataSource.push({ id: (this.idNums + i + 1).toString(), jzStartTime: '', jzEndTime: '', jzContent: '' , isUse: false })
        }
        this.idNums = this.idNums + 4
      }
      )
    }
  },
  computed: {
    columns () {
      return [
        {
          title: '开始时间',
          dataIndex: 'jzStartTime',
          width: 130,
          scopedSlots: { customRender: 'jzStartTime' }
        },
        {
          title: '结束时间',
          dataIndex: 'jzEndTime',
          scopedSlots: { customRender: 'jzEndTime' },
          width: 130
        },
        {
          title: '工作内容',
          dataIndex: 'jzContent',
          scopedSlots: { customRender: 'jzContent' }
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
          dataIndex: 'auditSuggestion',
          width: 120
        },
        {
          title: '是否用于本次评审',
          dataIndex: 'isUse',
          scopedSlots: { customRender: 'isUse' },
          width: 80
        },
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
