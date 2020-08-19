<template>
  <a-card title="任职培养">
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
      :scroll="{ x: 1200 }"
    >
      <template
        slot="emStartTime"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'emStartTime')"
        />
      </template>
      <template
        slot="emEndTime"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'emEndTime')"
        />
      </template>
      <template
        slot="emCoursename"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'emCoursename')"
            :value="record.emCoursename"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="emOtherwork"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'emOtherwork')"
            :value="record.emOtherwork"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="emStudentcount"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'emStudentcount')"
            :value="record.emStudentcount"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="emWeektime"
        slot-scope="textw, record"
      >
        <div key="emWeektime">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'emWeektime')"
            :value="record.emWeektime"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="emTotaltime"
        slot-scope="textw, record"
      >
        <div key="emTotaltime">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'emTotaltime')"
            :value="record.emTotaltime"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="emContent"
        slot-scope="textw, record"
      >
        <div key="emContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'emContent')"
            :value="record.emContent"
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
          emStartTime: '',
          emEndTime: '',
          emCoursename: '',
          emOtherwork: '',
          emStudentcount: '',
          emWeektime: '',
          emTotaltime: '',
          emContent: '',
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.emStartTime != '' || element.emEndTime != '' || element.emCoursename != '' || element.emOtherwork != '' || element.emStudentcount != '' || element.emWeektime != '' || element.emTotaltime != '' || element.emContent != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBEmploy/addNew', {
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
            if (element.emStartTime != '' || element.emEndTime != '' || element.emCoursename != '' || element.emOtherwork != '' || element.emStudentcount != '' || element.emWeektime != '' || element.emTotaltime != '' || element.emContent != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBEmploy/addNew', {
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
      this.$get('dcaBEmploy/custom', {
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
            emStartTime: '',
            emEndTime: '',
            emCoursename: '',
            emOtherwork: '',
            emStudentcount: '',
            emWeektime: '',
            emTotaltime: '',
            emContent: '',
          })
          this.idNums = this.idNums + 4
        }
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '自何年月',
        dataIndex: 'emStartTime',
        width: 120,
        scopedSlots: { customRender: 'emStartTime' }
      },
      {
        title: '至何年月',
        dataIndex: 'emEndTime',
        width: 120,
        scopedSlots: { customRender: 'emEndTime' }
      },
      {
        title: '讲授课程名称',
        dataIndex: 'emCoursename',
        width: 120,
        scopedSlots: { customRender: 'emCoursename' }
      },
      {
        title: '其他教学任务',
        dataIndex: 'emOtherwork',
        width: 120,
        scopedSlots: { customRender: 'emOtherwork' }
      },
      {
        title: '学生人数',
        dataIndex: 'emStudentcount',
        width: 120,
        scopedSlots: { customRender: 'emStudentcount' }
      },
      {
        title: '周学时数',
        dataIndex: 'emWeektime',
        width: 120,
        scopedSlots: { customRender: 'emWeektime' }
      },
      {
        title: '总学时数',
        dataIndex: 'emTotaltime',
        width: 120,
        scopedSlots: { customRender: 'emTotaltime' }
      },
      {
        title: '备注',
        dataIndex: 'emContent',
        width: 120,
        scopedSlots: { customRender: 'emContent' }
      },
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
