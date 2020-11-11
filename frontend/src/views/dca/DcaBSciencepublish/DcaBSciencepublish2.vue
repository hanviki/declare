<template>
    <a-card title="任现职以来发表的论文、出版著作">
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
                    slot="wzlx"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'wzlx')"
                      :value="record.wzlx"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="djzz"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'djzz')"
                      :value="record.djzz"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="qkjb"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'qkjb')"
                      :value="record.qkjb"
              >
              </a-textarea>
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
                        :precision="2"
                >
                </a-input-number>
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
                    slot="isJxzcsb"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'isJxzcsb')"
                      :value="record.isJxzcsb"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="isLczcsb"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'isLczcsb')"
                      :value="record.isLczcsb"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="auditQkjb"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'auditQkjb')"
                      :value="record.auditQkjb"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="jxzcsl"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'jxzcsl')"
                      :value="record.jxzcsl"
              >
              </a-textarea>
          </div>
            </template>
      <template
                    slot="lczcsl"
                    slot-scope="text, record"
            >
          <div v-if="record.state==3">
              {{text}}
                </div>
          <div v-else>
              <a-textarea
                      @blur="e => inputChange(e.target.value,record,'lczcsl')"
                      :value="record.lczcsl"
              >
              </a-textarea>
          </div>
            </template>
            <template
                    slot="fileId"
                    slot-scope="text, record"
            >
                <div v-if="record.state==3">
                    <a :href="record.fileUrl" v-if="text!=null && text !=''" target="_blank" >查看</a>
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
            }
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
                if (record.fileId!= undefined && record.fileId != '') {
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
                        state: 0,
                        paperName: '',
                        journalName: '',
                        journalCode: '',
                        paperPublishdate: '',
                        wzlx: '',
                        djzz: '',
                        qkjb: '',
                        cdzs: '',
                        paperShoulu: '',
                        paperCause: '',
                        isBest: '',
                        authorRank: '',
                        otherTimes: '',
                        isJxzcsb: '',
                        isLczcsb: '',
                        auditQkjb: '',
                        jxzcsl: '',
                        lczcsl: '',
                        isUse: false
                    })
                }
                this.idNums = this.idNums + 4
            },
            handleSave () {
                const dataSource = [...this.dataSource]
                let dataAdd = []
                dataSource.forEach(element => {
   if(                        element.paperName!= ''       ||    element.journalName!= ''       ||    element.journalCode!= ''       ||    element.paperPublishdate!= ''       ||    element.wzlx!= ''       ||    element.djzz!= ''       ||    element.qkjb!= ''       ||    element.cdzs!= ''       ||    element.paperShoulu!= ''       ||    element.paperCause!= ''       ||    element.isBest!= ''       ||    element.authorRank!= ''       ||    element.otherTimes!= ''       ||    element.isJxzcsb!= ''       ||    element.isLczcsb!= ''       ||    element.auditQkjb!= ''       ||    element.jxzcsl!= ''       ||    element.lczcsl!= ''                                    ){
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
   if(                        element.paperName!= ''       ||    element.journalName!= ''       ||    element.journalCode!= ''       ||    element.paperPublishdate!= ''       ||    element.wzlx!= ''       ||    element.djzz!= ''       ||    element.qkjb!= ''       ||    element.cdzs!= ''       ||    element.paperShoulu!= ''       ||    element.paperCause!= ''       ||    element.isBest!= ''       ||    element.authorRank!= ''       ||    element.otherTimes!= ''       ||    element.isJxzcsb!= ''       ||    element.isLczcsb!= ''       ||    element.auditQkjb!= ''       ||    element.jxzcsl!= ''       ||    element.lczcsl!= ''                                    ){
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
                this.$get('dcaBSciencepublish/custom', {
                }).then((r) => {
                    let data = r.data
                    this.dataSource = data.rows

                for (let i = 0; i < 4; i++) {
                    this.dataSource.push({
                        id: (this.idNums + i + 1).toString(),
                        state: 0,
                            paperName: '' ,
                            journalName: '' ,
                            journalCode: '' ,
                            paperPublishdate: '' ,
                            wzlx: '' ,
                            djzz: '' ,
                            qkjb: '' ,
                            cdzs: '' ,
                            paperShoulu: '' ,
                            paperCause: '' ,
                            isBest: '' ,
                            authorRank: '' ,
                            otherTimes: '' ,
                            isJxzcsb: '' ,
                            isLczcsb: '' ,
                            auditQkjb: '' ,
                            jxzcsl: '' ,
                            lczcsl: '' ,
                        isUse: false
                })
                    this.idNums = this.idNums + 4
                }
            })
            }
        },
        computed: {
            columns () {
            return [                        {
                                title: '论文名',
                            dataIndex: 'paperName',
                            width: 130,
                            scopedSlots: { customRender: 'paperName' }
                        },
                        {
                                title: '期刊名',
                            dataIndex: 'journalName',
                            width: 130,
                            scopedSlots: { customRender: 'journalName' }
                        },
                        {
                                title: '期刊号',
                            dataIndex: 'journalCode',
                            width: 130,
                            scopedSlots: { customRender: 'journalCode' }
                        },
                        {
                                title: '发表年月',
                            dataIndex: 'paperPublishdate',
                            width: 130,
                            scopedSlots: { customRender: 'paperPublishdate' }
                        },
                        {
                                title: '文章类型',
                            dataIndex: 'wzlx',
                            width: 130,
                            scopedSlots: { customRender: 'wzlx' }
                        },
                        {
                                title: '第几作者',
                            dataIndex: 'djzz',
                            width: 130,
                            scopedSlots: { customRender: 'djzz' }
                        },
                        {
                                title: '期刊级别',
                            dataIndex: 'qkjb',
                            width: 130,
                            scopedSlots: { customRender: 'qkjb' }
                        },
                        {
                                title: '承担字数（万）',
                            dataIndex: 'cdzs',
                            width: 130,
                            scopedSlots: { customRender: 'cdzs' }
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
                                title: '第一或通讯作者',
                            dataIndex: 'authorRank',
                            width: 130,
                            scopedSlots: { customRender: 'authorRank' }
                        },
                        {
                                title: '他引次数',
                            dataIndex: 'otherTimes',
                            width: 130,
                            scopedSlots: { customRender: 'otherTimes' }
                        },
                        {
                                title: '是否能用于教学职称申报',
                            dataIndex: 'isJxzcsb',
                            width: 130,
                            scopedSlots: { customRender: 'isJxzcsb' }
                        },
                        {
                                title: '是否能用于临床职称申报',
                            dataIndex: 'isLczcsb',
                            width: 130,
                            scopedSlots: { customRender: 'isLczcsb' }
                        },
                        {
                                title: '期刊级别',
                            dataIndex: 'auditQkjb',
                            width: 130,
                            scopedSlots: { customRender: 'auditQkjb' }
                        },
                        {
                                title: '教学职称数量',
                            dataIndex: 'jxzcsl',
                            width: 130,
                            scopedSlots: { customRender: 'jxzcsl' }
                        },
                        {
                                title: '临床职称数量',
                            dataIndex: 'lczcsl',
                            width: 130,
                            scopedSlots: { customRender: 'lczcsl' }
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
                    title: '是否用于本次评审',
                    dataIndex: 'isUse',
                    scopedSlots: { customRender: 'isUse' },
                    width: 80
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
