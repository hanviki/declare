<template>
  <a-card title="申请专利情况">
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
      :scroll="{x:1500}"
    >
      <template
        slot="patentCode"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentCode')"
            :value="record.patentCode"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentName')"
            :value="record.patentName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentType"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentType')"
            :value="record.patentType"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text==""?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'patentDate')"
          />
        </div>
      </template>
      <template
        slot="patentRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'patentRanknum')"
            :value="record.patentRanknum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="isAuthority"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'isAuthority')"
            :value="record.isAuthority"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="isZhuanrang"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'isZhuanrang')"
            :value="record.isZhuanrang"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentGood"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentGood')"
            :value="record.patentGood"
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
          patentCode: '',
          patentName: '',
          patentType: '',
          patentDate: '',
          patentRanknum: '',
          isAuthority: '',
          isZhuanrang: '',
          patentGood: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSource = [...this.dataSource]
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.patentCode != '' || element.patentName != '' || element.patentType != '' || element.patentDate != '' || element.patentRanknum != '' || element.isAuthority != '' || element.isZhuanrang != '' || element.patentGood != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBPatent/addNew', {
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
            if (element.patentCode != '' || element.patentName != '' || element.patentType != '' || element.patentDate != '' || element.patentRanknum != '' || element.isAuthority != '' || element.isZhuanrang != '' || element.patentGood != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBPatent/addNew', {
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
      this.$get('dcaBPatent/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            patentCode: '',
            patentName: '',
            patentType: '',
            patentDate: '',
            patentRanknum: '',
            isAuthority: '',
            isZhuanrang: '',
            patentGood: '',
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
        title: '专利号',
        dataIndex: 'patentCode',
        width: 200,
        scopedSlots: { customRender: 'patentCode' }
      },
      {
        title: '专利名称',
        dataIndex: 'patentName',
        width: 200,
        scopedSlots: { customRender: 'patentName' }
      },
      {
        title: '专利类别',
        dataIndex: 'patentType',
        width: 130,
        scopedSlots: { customRender: 'patentType' }
      },
      {
        title: '批准年月',
        dataIndex: 'patentDate',
        width: 130,
        scopedSlots: { customRender: 'patentDate' }
      },
      {
        title: '本人排名',
        dataIndex: 'patentRanknum',
        width: 130,
        scopedSlots: { customRender: 'patentRanknum' }
      },
      {
        title: '是否授权',
        dataIndex: 'isAuthority',
        width: 130,
        scopedSlots: { customRender: 'isAuthority' }
      },
      {
        title: '是否转让',
        dataIndex: 'isZhuanrang',
        width: 130,
        scopedSlots: { customRender: 'isZhuanrang' }
      },
      {
        title: '转让效益',
        dataIndex: 'patentGood',
        width: 130,
        scopedSlots: { customRender: 'patentGood' }
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
