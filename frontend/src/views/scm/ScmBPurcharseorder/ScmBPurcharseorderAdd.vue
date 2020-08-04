<template>
  <a-drawer
    title="新增"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="主键"
      >
        <a-input
          placeholder="请输入主键"
          v-decorator="['id', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="编码"
      >
        <a-input
          placeholder="请输入编码"
          v-decorator="['code', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="名字"
      >
        <a-input
          placeholder="请输入名字"
          v-decorator="['name', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="订单号"
      >
        <a-input
          placeholder="请输入订单号"
          v-decorator="['ebeln', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="项目号"
      >
        <a-input
          placeholder="请输入项目号"
          v-decorator="['ebelp', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="供应计划号"
      >
        <a-input
          placeholder="请输入供应计划号"
          v-decorator="['lifnr', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="物料ID"
      >
        <a-input
          placeholder="请输入物料ID"
          v-decorator="['matnr', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="物料描述"
      >
        <a-input
          placeholder="请输入物料描述"
          v-decorator="['txz01', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="院区名称"
      >
        <a-input
          placeholder="请输入院区名称"
          v-decorator="['werkst', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="院区ID"
      >
        <a-input
          placeholder="请输入院区ID"
          v-decorator="['werks', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="库房ID"
      >
        <a-input
          placeholder="请输入库房ID"
          v-decorator="['lgort', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="库房名称"
      >
        <a-input
          placeholder="请输入库房名称"
          v-decorator="['lgortName', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="订单数量"
      >
        <a-input
          placeholder="请输入订单数量"
          v-decorator="['menge', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="计量单位ID"
      >
        <a-input
          placeholder="请输入计量单位ID"
          v-decorator="['meins', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="计量单位"
      >
        <a-input
          placeholder="请输入计量单位"
          v-decorator="['mseht', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="单价"
      >
        <a-input
          placeholder="请输入单价"
          v-decorator="['netpr', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="订单开始时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'eindt', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="订单结束时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'bedat', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="状态"
      >
        <a-input
          placeholder="请输入状态"
          v-decorator="['status', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="备注"
      >
        <a-input
          placeholder="请输入备注"
          v-decorator="['comments', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="供应数量"
      >
        <a-input
          placeholder="请输入供应数量"
          v-decorator="['allmenge', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="收货数量"
      >
        <a-input
          placeholder="请输入收货数量"
          v-decorator="['suremenge', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="类型"
      >
        <a-input
          placeholder="请输入类型"
          v-decorator="['bsart', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="送货部门ID"
      >
        <a-input
          placeholder="请输入送货部门ID"
          v-decorator="['sendDeaprtId', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="送货部门名称"
      >
        <a-input
          placeholder="请输入送货部门名称"
          v-decorator="['sendDeaprtName', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系人"
      >
        <a-input
          placeholder="请输入联系人"
          v-decorator="['sendDeaprtContact', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="联系电话"
      >
        <a-input
          placeholder="请输入联系电话"
          v-decorator="['sendDeaprtPhone', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="是否删除"
      >
        <a-input
          placeholder="请输入是否删除"
          v-decorator="['isDeletemark', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="创建时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'createTime', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="修改时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'modifyTime', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="创建人"
      >
        <a-input
          placeholder="请输入创建人"
          v-decorator="['createUserId', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="修改人"
      >
        <a-input
          placeholder="请输入修改人"
          v-decorator="['modifyUserId', {}]"
        />
      </a-form-item>
    </a-form>
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
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'ScmBPurcharseorderAdd',
  props: {
    addVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmBPurcharseorder: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmBPurcharseorder = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields()
          this.$post('scmBPurcharseorder', {
            ...this.scmBPurcharseorder
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    setFields () {
      let values = this.form.getFieldsValue(['id', 'code', 'name', 'ebeln', 'ebelp', 'lifnr', 'matnr', 'txz01', 'werkst', 'werks', 'lgort', 'lgortName', 'menge', 'meins', 'mseht', 'netpr', 'eindt', 'bedat', 'status', 'comments', 'allmenge', 'suremenge', 'bsart', 'sendDeaprtId', 'sendDeaprtName', 'sendDeaprtContact', 'sendDeaprtPhone', 'isDeletemark', 'createTime', 'modifyTime', 'createUserId', 'modifyUserId'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmBPurcharseorder[_key] = values[_key] })
      }
    }
  }
}
</script>
