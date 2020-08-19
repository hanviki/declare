<template>
  <a-card title="本科教学情况">
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
        slot="courseName"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseName')"
            :value="record.courseName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ugStartDate"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'ugStartDate')"
        />
      </template>
      <template
        slot="ugEndDate"
        slot-scope="text, record"
      >
        <a-date-picker
          :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
          @change="(e,f) => handleChange(e,f,record,'ugEndDate')"
        />
      </template>
      <template
        slot="courseType"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseType')"
            :value="record.courseType"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="studentNumber"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'studentNumber')"
            :value="record.studentNumber"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="totalTime"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'totalTime')"
            :value="record.totalTime"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="personTime"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'personTime')"
            :value="record.personTime"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="teachScore"
        slot-scope="textw, record"
      >
        <div key="jzContent">
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'teachScore')"
            :value="record.teachScore"
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
          courseName: '',
          ugStartDate: '',
          ugEndDate: '',
          courseType: '',
          studentNumber: '',
          totalTime: '',
          personTime: '',
          teachScore: '',
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.courseName != '' || element.ugStartDate != '' || element.ugEndDate != '' || element.courseType != '' || element.studentNumber != '' || element.totalTime != '' || element.personTime != '' || element.teachScore != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBUndergraduate/addNew', {
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
            if (element.courseName != '' || element.ugStartDate != '' || element.ugEndDate != '' || element.courseType != '' || element.studentNumber != '' || element.totalTime != '' || element.personTime != '' || element.teachScore != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBUndergraduate/addNew', {
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
      this.$get('dcaBUndergraduate/custom', {
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
            courseName: '',
            ugStartDate: '',
            ugEndDate: '',
            courseType: '',
            studentNumber: '',
            totalTime: '',
            personTime: '',
            teachScore: '',
          })
          this.idNums = this.idNums + 4
        }
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '课程名称',
        dataIndex: 'courseName',
        width: 120,
        scopedSlots: { customRender: 'courseName' }
      },
      {
        title: '自何年月',
        dataIndex: 'ugStartDate',
        width: 120,
        scopedSlots: { customRender: 'ugStartDate' }
      },
      {
        title: '至何年月',
        dataIndex: 'ugEndDate',
        width: 120,
        scopedSlots: { customRender: 'ugEndDate' }
      },
      {
        title: '课程类别',
        dataIndex: 'courseType',
        width: 120,
        scopedSlots: { customRender: 'courseType' }
      },
      {
        title: '学生人数',
        dataIndex: 'studentNumber',
        width: 120,
        scopedSlots: { customRender: 'studentNumber' }
      },
      {
        title: '总学时',
        dataIndex: 'totalTime',
        width: 120,
        scopedSlots: { customRender: 'totalTime' }
      },
      {
        title: '个人承担学时',
        dataIndex: 'personTime',
        width: 120,
        scopedSlots: { customRender: 'personTime' }
      },
      {
        title: '教学评分',
        dataIndex: 'teachScore',
        width: 120,
        scopedSlots: { customRender: 'teachScore' }
      },
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
