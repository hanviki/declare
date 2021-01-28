<template>
  <a-card
    class="card-area"
    title="任现职以来发表的论文"
  >
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
      :scroll="scroll"
    >
      <template
        slot="paperName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="jzContent">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'paperName')"
              :value="record.paperName"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="journalName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="jzContent">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'journalName')"
              :value="record.journalName"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="journalCode"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="jzContent">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'journalCode')"
              :value="record.journalCode"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="paperPublishdate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text==""|| text==null?"":text.substr(0,10)}}
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
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="jzContent">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'paperShoulu')"
              :value="record.paperShoulu"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="paperCause"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="jzContent">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'paperCause')"
              :value="record.paperCause"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="djzz"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <div key="djzz">
            <a-textarea
              @blur="e => inputChange(e.target.value,record,'djzz')"
              :value="record.djzz"
            >
            </a-textarea>
          </div>
        </div>
      </template>
      <template
        slot="qkjb"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-select
            :value="record.qkjb"
            style="width: 100px"
            @change="(e,f) => handleSelectChange(e,f,record,'qkjb')"
          >
            <a-select-option value="A">
              A
            </a-select-option>
            <a-select-option value="B">
              B
            </a-select-option>
            <a-select-option value="C">
              C
            </a-select-option>
            <a-select-option value="D">
              D
            </a-select-option>
            <a-select-option value="E">
              E
            </a-select-option>
            <a-select-option value="F">
              F
            </a-select-option>
          </a-select>
        </div>
      </template>
      <template
        slot="wzlx"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-select
            :value="record.wzlx"
            style="width: 100%"
            @change="(e,f) => handleSelectChange(e,f,record,'wzlx')"
          >
            <a-select-option value="科研">
              科研
            </a-select-option>
            <a-select-option value="教学">
              教学
            </a-select-option>
          </a-select>
        </div>
      </template>
      <template
        slot="isBest"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>

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
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
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
        <div v-if="record.state==3 || record.state==1">
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
      <template
        slot="fileId"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          <a
            :href="record.fileUrl"
            v-if="text!=null && text !=''"
            target="_blank"
          >查看</a>
        </div>
        <div v-else>
          <a-button
            type="dashed"
            block
            @click="OpenFile(record)"
          >
            {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
          </a-button>
        </div>
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
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </a-card>
</template>

<script>
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000,
      fileVisiable: false,
      editRecord: {
        fileId: ''
      },
      scroll: {
        x: 2000,
        y: window.innerHeight - 200 - 100 - 20 - 80
      },
    }
  },
  components: { TableUploadFile },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    showFile (record) {
      window.location.href = record.fileUrl
    },
    OpenFile (record) {
      this.editRecord = record
      this.fileVisiable = true
      if (record.fileId != undefined && record.fileId != '') {
        this.$refs.upFile.fetch(record.fileId)
      }
    },
    setFileId (fileId, fileUrl) {
      this.fileVisiable = false
      console.log(fileUrl)
      /**
       const dataSource = [...this.dataSource]
       console.log(this.editRecord.id)
       let record=dataSource.filter(p=>p.id===this.editRecord.id)
       console.log(record)*/
      this.editRecord["fileId"] = fileId
      this.editRecord["fileUrl"] = fileUrl
      //this.dataSource =[...dataSource]
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      if (selectedRows.length > 0) {
        if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
          this.selectedRowKeys = selectedRowKeys
        }
      }
      else{
        this.selectedRowKeys = selectedRowKeys
      }
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
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
          wzlx: '',
          qkjb: '',
          djzz: '',
          state: 0,
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSourceAll = [...this.dataSource]
      const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.paperName != '' || element.journalName != '' || element.journalCode != '' || element.paperPublishdate != '' || element.paperShoulu != '' || element.paperCause != '' || element.isBest != '' || element.otherTimes != '' || element.authorRank != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length < 0) {
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
          this.fetch()
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
          const dataSourceAll = [...that.dataSource]
          const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
          let dataAdd = []
          dataSource.forEach(element => {
            if (element.paperName != '' || element.journalName != '' || element.journalCode != '' || element.paperPublishdate != '' || element.paperShoulu != '' || element.paperCause != '' || element.isBest != '' || element.otherTimes != '' || element.authorRank != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length < 0) {
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
              that.fetch()
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
      if (!this.selectedRowKeys.length || this.selectedRowKeys.length>1) {
        this.$message.warning('请选择一条需要删除的记录')
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
          that.$put('dcaBSciencepublish', {
            id: that.selectedRowKeys[0],
            isDeletemark: 0
          }).then(() => {

          }).catch(() => {

          })
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
            wzlx: '',
            qkjb: '',
            djzz: '',
            state: 0,
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
        width: 120,
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
        title: '期刊级别',
        dataIndex: 'qkjb',
        width: 100,
        scopedSlots: { customRender: 'qkjb' }
      },
      {
        title: '第一或通讯作者',
        dataIndex: 'authorRank',
        width: 120,
        scopedSlots: { customRender: 'authorRank' }
      },
      {
        title: '第几作者',
        dataIndex: 'djzz',
        width: 80,
        scopedSlots: { customRender: 'djzz' }
      },
      {
        title: '期刊类型',
        dataIndex: 'wzlx',
        width: 80,
        scopedSlots: { customRender: 'wzlx' }
      }, {
        title: '状态',
        dataIndex: 'state',
        width: 100,
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
      }, {
        title: '附件',
        dataIndex: 'fileId',
        scopedSlots: { customRender: 'fileId' },
        width: 80
      }
      ]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
