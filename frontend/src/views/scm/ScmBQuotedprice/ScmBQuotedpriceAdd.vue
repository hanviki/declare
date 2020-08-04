<template>
  <a-drawer
    title="报价列表"
    :maskClosable="false"
    width=1200
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-layout>
      <a-layout-header>
        <p style="font-size:18px;">药品名称:{{scmBQueryprice.txz01}} 药品编码：{{scmBQueryprice.matnr}} 规格：{{scmBQueryprice.spec}} 计量单位：{{scmBQueryprice.mseht}} 采购数量：{{scmBQueryprice.amount}}</p>
      </a-layout-header>
      <a-layout-content>
        <a-form :form="form">
          <a-card
            v-for="(item) in quoteList"
            :key="item.displayIndex"
            :title="item.name"
            :ref="'quote'+item.displayIndex"
          >
            <template slot="extra">
              <a-button
                icon="delete"
                v-show="showType==0"
                @click="deleteByindex(item.displayIndex)"
              >删除该报价</a-button>
              <a-button
                style="margin-left: 8px"
                v-show="showType==0"
                icon="plus-circle"
                @click="addHos(item)"
              >添加更多参考医院</a-button>
            </template>
            <a-row>
              <a-col :span="8">
                <a-form-item
                  v-bind="formItemLayout"
                  label="供货厂家"
                >
                  <a-input
                    v-model="item.productName"
                    placeholder="请输入供货厂家"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <a-form-item
                  v-bind="formItemLayout"
                  label="报价价格"
                >
                  <a-input-number
                    v-model="item.quotedPrice"
                    placeholder="请输入报价价格"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="6">
                <a-form-item
                  v-bind="formItemLayout"
                  label="可供数量"
                >
                  <a-input-number
                    v-model="item.supplyAmount"
                    placeholder="请输入供货数量"
                  />
                </a-form-item>
              </a-col>
              <a-col :span="5">
                <a-form-item
                  v-bind="formItemLayout"
                  label="备注"
                >
                  <a-input
                    v-model="item.comments"
                    placeholder="请输入备注"
                  />
                </a-form-item>
              </a-col>
            </a-row>
            <template v-for="(hitem) in item.hospital">
              <a-row :key="item.displayIndex+'@'+hitem.displayIndex">
                <a-col :span="8">
                  <a-form-item
                    v-bind="formItemLayout"
                    label="该厂家其他供货医院"
                  >
                    <a-input
                      v-model="hitem.hospitalName"
                      placeholder="请输入供货医院"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="6">
                  <a-form-item
                    v-bind="formItemLayout"
                    label="该医院目前供货价格"
                  >
                    <a-input-number
                      v-model="hitem.price"
                      placeholder="请输入供货价格"
                    />
                  </a-form-item>
                </a-col>
                <a-col :span="6">
                  <fpAttach-file
                    :ref="item.displayIndex+'$'+hitem.displayIndex"
                    :itemIndex="item.displayIndex"
                    :displayIndex="hitem.displayIndex"
                    :fileId="hitem.comFileId"
                    :serverName="hitem.serverName"
                    :clientName="hitem.clientName"
                    @fp="getFpFileId"
                    :showType="showType"
                  >
                  </fpAttach-file>
                </a-col>
                <a-col :span="4">
                  <a-form-item
                    v-bind="formItemLayout"
                    label="备注"
                  >
                    <a-input
                      v-model="hitem.commentsD"
                      placeholder="请输入备注"
                    />
                  </a-form-item>
                </a-col>
              </a-row>
            </template>
          </a-card>
          <a-button
            size="large"
            type="dashed"
            icon="plus-square"
            @click="addQuote"
            v-show="showType==0"
          >新增报价</a-button>
        </a-form>
      </a-layout-content>
    </a-layout>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
        v-show="showType==0"
      >
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button
        @click="handleSubmit(1)"
        type="primary"
        :loading="loading"
        v-show="showType==0"
      >保存并提交</a-button>
      <a-button
        @click="handleSubmit(0)"
        type="primary"
        :loading="loading"
        v-show="showType==0"
      > 保存</a-button>
      <a-button
        @click="onClose"
        type="primary"
        v-show="showType==1"
      > 关闭</a-button>
    </div>
  </a-drawer>
</template>
<script>
import FpAttachFile from './FpAttachFile'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 13 }
}
export default {
  name: 'ScmBQuotedpriceAdd',
  components: { FpAttachFile },
  props: {
    addVisiable: {
      default: false
    },
    scmBQueryprice: {
      required: true
    },
    showType: 0
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      quoteList: [
        {
          displayIndex: 1,
          name: '报价1',
          productName: '',
          quotedPrice: '',
          supplyAmount: '',
          comments: '',
          hosCount: 1,
          hospital: [
            {
              displayIndex: 1,
              hospitalName: '',
              price: 0,
              commentsD: '',
              comFileId: '',
              serverName: '',
              clientName: ''
            }
          ]
        }
      ],
      indexCalc: 1

    }
  },
  watch: {
    addVisiable () {
      if (this.addVisiable) {
        this.fetch(this.scmBQueryprice.id)
      }
    }
  },
  methods: {
    reset () {
      this.loading = false

      this.quoteList = [
        {
          displayIndex: 1,
          name: '报价1',
          productName: '',
          quotedPrice: '',
          supplyAmount: '',
          comments: '',
          hosCount: 1,
          hospital: [
            {
              displayIndex: 1,
              hospitalName: '',
              price: 0,
              commentsD: '',
              comFileId: '',
              serverName: '',
              clientName: ''
            }
          ]
        }
      ]
      this.indexCalc = 1
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit (state) {
      let that = this
      let arrJson = JSON.stringify(this.quoteList)
      that.$post('scmBQuotedprice/add', { jsonString: arrJson, baseid: this.scmBQueryprice.id, state: state }).then(() => {
        that.$message.success('操作成功')
        that.selectedRowKeys = []
        that.reset()
        that.$emit('success')
      })
    },
    deleteByindex (index) {
      if (this.quoteList.length > 1) {
        this.quoteList = this.quoteList.filter(p => p.displayIndex !== index)
      }
      else {
        this.$message.warning('请至少保留一条报价')
        return
      }
    },
    getFpFileId (itemIndex, hostIndex, fileId)//上传附件赋值给报价
    {
      console.info(itemIndex,hostIndex)
      for (let item of this.quoteList) {
        if (item.displayIndex == itemIndex) {
          for (let hos of item.hospital) {
            if (hos.displayIndex == hostIndex) {
              hos.comFileId = fileId
              return
            }
          }
          return
        }
      }
      //this.quoteList[itemIndex].hospital[hostIndex].comFileId = fileId
    },
    addQuote () {
      this.indexCalc = this.indexCalc + 1
      this.quoteList.push(
        {
          displayIndex: this.indexCalc,
          name: `报价${this.indexCalc}`,
          productName: '',
          quotedPrice: '',
          supplyAmount: '',
          comments: '',
          hosCount: 1,
          hospital: [
            {
              displayIndex: 1,
              hospitalName: '',
              price: 0,
              commentsD: '',
              comFileId: '',
              serverName: '',
              clientName: ''
            }
          ]
        }
      )
    },
    addHos (item) {
      item.hosCount += 1
      item.hospital.push({
        displayIndex: item.hosCount,
        hospitalName: '',
        price: 0,
        commentsD: '',
        comFileId: '',
        serverName: '',
        clientName: ''
      }
      )
    },
    setFields () {
      let values = this.form.getFieldsValue(['id', 'code', 'baseId', 'productName', 'quotedPrice', 'supplyAmount', 'comments', 'state', 'displayIndex', 'gysname', 'gysaccount', 'isDeletemark', 'createTime', 'modifyTime', 'createUserId', 'modifyUserId'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBQuotedprice[_key] = values[_key] })
      }
    },
    fetch (baseid) {
      this.loading = true
      this.$get(`scmBQuotedprice/GetList/${baseid}`, {
      }).then((r) => {
        let data = r.data.data
        if (data.length > 0) {
          var quote_list = []
          this.indexCalc = data.length
          let index = 1
          for (let item of data) {
            var obj = {
              displayIndex: index,
              name: `报价${index}`,
              productName: item.productName,
              quotedPrice: item.quotedPrice,
              supplyAmount: item.supplyAmount,
              comments: item.comments,
              hosCount: 1,
              hospital: [
                {
                  displayIndex: 1,
                  hospitalName: '',
                  price: 0,
                  commentsD: '',
                  comFileId: '',
                  serverName: '',
                  clientName: ''
                }
              ]
            }

            if (item.hospital.length > 0) {
              obj.hosCount = item.hospital.length;
              obj.hospital = []
              let hoIndex = 1
              for (let hitem of item.hospital) {
                obj.hospital.push({
                  displayIndex: hoIndex,
                  hospitalName: hitem.hospitalName == undefined ? '' : hitem.hospitalName,
                  price: hitem.price == undefined ? 0 : hitem.price,
                  commentsD: hitem.commentsD == undefined ? '' : hitem.commentsD,
                  comFileId: hitem.comFileId == undefined ? '' : hitem.comFileId,
                  serverName: hitem.serverName == undefined ? '' : hitem.serverName,
                  clientName: hitem.clientName == undefined ? '' : hitem.clientName
                })

                hoIndex += 1
              }
            }
            quote_list.push(obj)

            index += 1
          }
          this.quoteList = quote_list;
          let that = this
          setTimeout(function () {
            for (let pitem of that.quoteList) {
              for (let sp of pitem.hospital) {
                console.info(pitem.displayIndex + '$' + sp.displayIndex)
                console.info(that.$refs[pitem.displayIndex + '$' + sp.displayIndex][0])
                that.$refs[pitem.displayIndex + '$' + sp.displayIndex][0].fileList = []
                if (sp.comFileId != '') {
                  that.$refs[pitem.displayIndex + '$' + sp.displayIndex][0].fileList.push({
                    uid: sp.comFileId,
                    name: sp.clientName,
                    status: 'done',
                    url: that.$baseUrl + 'uploadFile/' + sp.serverName
                  })
                }
              }
            }
          }, 200)
        }
        this.loading = false
      })
    }
  }
}
</script>
<style>
.ant-layout-header {
  background: #fff !important;
}
</style>

