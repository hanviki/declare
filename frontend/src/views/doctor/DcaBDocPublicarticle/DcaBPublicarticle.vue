<template>
  <a-card title="出版著作">
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
        slot="zzlx"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-select
            :value="record.zzlx==null?'':record.zzlx"
            style="width: 100%"
            @change="(e,f) => handleSelectChange(e,f,record,'zzlx')"
          >
            <a-select-option value="专著">
              专著
            </a-select-option>
            <a-select-option value="教材">
              教材
            </a-select-option>
            <a-select-option value="科普">
              科普
            </a-select-option>
            <a-select-option value="其他">
              其他
            </a-select-option>
          </a-select>
        </div>
      </template>
      <template
        slot="zzmc"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'zzmc')"
            :value="record.zzmc"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="zzsf"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-select
            :value="record.zzsf==null?'':record.zzsf"
            style="width: 100%"
            @change="(e,f) => handleSelectChange(e,f,record,'zzsf')"
          >
            <a-select-option value="主编">
              主编
            </a-select-option>
            <a-select-option value="副主编">
              副主编
            </a-select-option>
            <a-select-option value="编委">
              编委
            </a-select-option>
            <a-select-option value="参编">
              参编
            </a-select-option>
            <a-select-option value="其他">
              其他
            </a-select-option>
          </a-select>
        </div>
      </template>
      <template
        slot="cbDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text==""|| text==null?"":text.substr(0,10)}}
        </div>
        <div v-else>
          <a-date-picker
            :defaultValue="(text=='' || text==null)?'':moment(text, dateFormat)"
            @change="(e,f) => handleChange(e,f,record,'cbDate')"
          />
        </div>
      </template>
      <template
        slot="ranknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'ranknum')"
            :value="record.ranknum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="printnum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'printnum')"
            :value="record.printnum"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="bookNo"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'bookNo')"
            :value="record.bookNo"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="cbsmc"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'cbsmc')"
            :value="record.cbsmc"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="bxzjmc"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'bxzjmc')"
            :value="record.bxzjmc"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="bxwzqzy"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'bxwzqzy')"
            :value="record.bxwzqzy"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="bxwzzshj"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'bxwzzshj')"
            :value="record.bxwzzshj"
            :precision="2"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="cdzs"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'cdzs')"
            :value="record.cdzs"
            :precision="0"
          >
          </a-input-number>
        </div>
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
        x: 2100,
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
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
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
          state: 0,
          zzlx: '',
          zzmc: '',
          zzsf: '',
          cbDate: '',
          ranknum: '',
          printnum: '',
          bookNo: '',
          cbsmc: '',
          bxzjmc: '',
          bxwzqzy: '',
          bxwzzshj: '',
          cdzs: '',
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
        if (element.zzlx != '' || element.zzmc != '' || element.zzsf != '' || element.cbDate != '' || element.ranknum != '' || element.printnum != '' || element.bookNo != '' || element.cbsmc != '' || element.bxzjmc != '' || element.bxwzqzy != '' || element.bxwzzshj != '' || element.cdzs != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length < 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBDocPublicarticle/addNew', {
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
          const dataSourceAll = [...that.dataSource]
          const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
          let dataAdd = []
          dataSource.forEach(element => {
            if (element.zzlx != '' || element.zzmc != '' || element.zzsf != '' || element.cbDate != '' || element.ranknum != '' || element.printnum != '' || element.bookNo != '' || element.cbsmc != '' || element.bxzjmc != '' || element.bxwzqzy != '' || element.bxwzzshj != '' || element.cdzs != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length < 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBDocPublicarticle/addNew', {
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
      this.$get('dcaBDocPublicarticle/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            state: 0,
            zzlx: '',
            zzmc: '',
            zzsf: '',
            cbDate: '',
            ranknum: '',
            printnum: '',
            bookNo: '',
            cbsmc: '',
            bxzjmc: '',
            bxwzqzy: '',
            bxwzzshj: '',
            cdzs: '',
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
        title: '著作类型',
        dataIndex: 'zzlx',
        width: 130,
        scopedSlots: { customRender: 'zzlx' }
      },
      {
        title: '著作名称',
        dataIndex: 'zzmc',
        width: 300,
        scopedSlots: { customRender: 'zzmc' }
      },
      {
        title: '著者身份',
        dataIndex: 'zzsf',
        width: 130,
        scopedSlots: { customRender: 'zzsf' }
      },
      {
        title: '出版时间',
        dataIndex: 'cbDate',
        width: 130,
        scopedSlots: { customRender: 'cbDate' }
      },
      {
        title: '第几版',
        dataIndex: 'ranknum',
        width: 130,
        scopedSlots: { customRender: 'ranknum' }
      },
      {
        title: ' 第几次印刷',
        dataIndex: 'printnum',
        width: 130,
        scopedSlots: { customRender: 'printnum' }
      },
      {
        title: ' 书号（ISNB号）',
        dataIndex: 'bookNo',
        width: 130,
        scopedSlots: { customRender: 'bookNo' }
      },
      {
        title: '出版社名称',
        dataIndex: 'cbsmc',
        width: 130,
        scopedSlots: { customRender: 'cbsmc' }
      },
      {
        title: '作为第一编写人编写章节名称',
        dataIndex: 'bxzjmc',
        width: 130,
        scopedSlots: { customRender: 'bxzjmc' }
      },
      {
        title: '作为第一编写人编写章节起止页',
        dataIndex: 'bxwzqzy',
        width: 130,
        scopedSlots: { customRender: 'bxwzqzy' }
      },
      {
        title: '作为第一编写人编写字数合计（单位：万字）',
        dataIndex: 'bxwzzshj',
        width: 130,
        scopedSlots: { customRender: 'bxwzzshj' }
      },
      {
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
        title: '附件',
        dataIndex: 'fileId',
        scopedSlots: { customRender: 'fileId' },
        width: 80
      }]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
