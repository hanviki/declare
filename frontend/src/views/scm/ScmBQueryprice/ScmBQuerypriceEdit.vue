<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-row>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="药品编码"
          >
            <a-input
              :disabled="true"
              placeholder="请输入药品编码"
              v-decorator="['matnr', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="计量单位"
          >
            <a-input
              :disabled="true"
              placeholder="请输入计量单位"
              v-decorator="['mseht', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="药品名称"
          >
            <a-input
              :disabled="true"
              placeholder="请输入药品名称"
              v-decorator="['txz01', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="规格"
          >
            <a-input
              :disabled="true"
              placeholder="请输入规格"
              v-decorator="['spec', {}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="采购数量"
          >
            <a-input-number
              placeholder="请输入采购数量"
              v-decorator="['amount', {rules: [{ required: true, message: '采购数量不能为空' }]}]"
            />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="询价日期"
          >
            <a-date-picker v-decorator="[ 'queryDate', {rules: [{ required: true, message: '询价日期不能为空' }]}]" />
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item
            v-bind="formItemLayout"
            label="结束日期"
          >
            <a-date-picker v-decorator="[ 'endDate', {rules: [{ required: true, message: '询价日期不能为空' }]}]" />
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
    <div>
      <div class="operator">
        <a-button
          type="primary"
          ghost
          @click="addVendor"
        >新增供应商</a-button>
        <a-button @click="batchDeleteVendor">删除供应商</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfoVendor"
        :columns="columnsVendor"
        :rowKey="record => record.gysaccount"
        :dataSource="dataSourceVendor"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeysVendor, onChange: onSelectChangeVendor}"
        :bordered="bordered"
        :scroll="{ x: 500 }"
      >
      </a-table>
      <vendor-add
        @close="handleVendorAddClose"
        @ok="handleVendorAddSuccess"
        :addVendorVisiable="addVendorVisiable"
      >
      </vendor-add>
    </div>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
       <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'
import VendorAdd from './VendorAdd'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 }
}
export default {
  name: 'ScmBQuerypriceEdit',
  props: {
    baseId: '',
    editVisiable: {
      default: false
    }
  },
  components: { VendorAdd },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBQueryprice: {},
      dataSourceVendor: [],//供应商数据
      selectedRowKeysVendor: [],
      addVendorVisiable: false,
      bordered: true
    }
  },
  computed: {
    columnsVendor () {
      return [{
        title: '供应商名称',
        dataIndex: 'gysname'
      }, {
        title: '供应商账号',
        dataIndex: 'gysaccount'
      }]
    }

  },
  watch: {
    editVisiable () {
      if (this.editVisiable) {
        this.fetch(this.baseId)
      }
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.selectedRowKeysVendor = []
      this.dataSourceVendor = []
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    onSelectChangeVendor (selectedRowKeys) {
      this.selectedRowKeysVendor = selectedRowKeys
    },
    batchDeleteVendor () {
       if (!this.selectedRowKeysVendor.length) {
        this.$message.warning('请选择删除的供应商')
        return
      }

      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          var dataSourceVendor = [...that.dataSourceVendor];
          for (let d of that.selectedRowKeysVendor) {
            dataSourceVendor = dataSourceVendor.filter(item => item.id !== d)
          }
          that.dataSourceVendor=dataSourceVendor
          that.selectedRowKeysVendor = []
        },
        onCancel () {
          that.selectedRowKeysVendor = []
        }
      })
    },
    handleVendorAddSuccess (rows) {
      const dataSourceVendor = [...this.dataSourceVendor];
      var arrData=[];
      for (let element of rows) {
        const target = dataSourceVendor.find(item => item.id === element.code);
        const IsRepeat=arrData.find(item => item.id === element.code);
        if (!target && !IsRepeat) {
          const newData = {
            gysname: element.name,
            gysaccount: element.code,
            id: element.code
          }
         arrData.push(newData)
        }
      }
     // 
      this.dataSourceVendor = [...dataSourceVendor, ...arrData];
      arrData=[]
      this.addVendorVisiable = false
    },
    handleVendorAddClose () {
      this.addVendorVisiable = false
    },
    addVendor () {
      this.addVendorVisiable = true
    },
    setFormValues ({ ...scmBQueryprice }) {
      let fields = ['matnr', 'mseht', 'txz01', 'spec', 'amount', 'queryDate', 'endDate']
      let fieldDates = ['queryDate', 'endDate', 'createTime', 'modifyTime']
      Object.keys(scmBQueryprice).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (!(scmBQueryprice[key] == null || scmBQueryprice[key] === '')) {
              obj[key] = moment(scmBQueryprice[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = scmBQueryprice[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.scmBQueryprice.id = scmBQueryprice.id
    },
    handleSave () {
      const dataSourceVendor = [...this.dataSourceVendor];
      if (dataSourceVendor.length == 0) {
        this.$message.warning("请选择供应商")
        return
      }

      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBQueryprice = this.form.getFieldsValue()
          scmBQueryprice.id = this.scmBQueryprice.id
          scmBQueryprice.queryState = 0
          this.$put('scmBQueryprice/xjEdit', {
            ...scmBQueryprice,
            gys: JSON.stringify(dataSourceVendor),
            state: 0
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    handleSubmit () {
      const dataSourceVendor = [...this.dataSourceVendor];
      if (dataSourceVendor.length == 0) {
        this.$message.warning("请选择供应商")
        return
      }

      this.form.validateFields((err, values) => {
        if (!err) {
          let scmBQueryprice = this.form.getFieldsValue()
          scmBQueryprice.id = this.scmBQueryprice.id
          scmBQueryprice.queryState = 1
          this.$put('scmBQueryprice/xjEdit', {
            ...scmBQueryprice,
            gys: JSON.stringify(dataSourceVendor),
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    fetch (baseId) {
      var params = {}
      this.loading = true
      params.pageSize = 100
      params.pageNum = 1
      params.baseId = baseId
      this.$get('scmBQuerypriceD', {
        ...params
      }).then((r) => {
        let data = r.data
        this.loading = false
        this.dataSourceVendor = data.rows
      })
    }
  }
}
</script>
