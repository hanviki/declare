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
        label="物料编码"
      >
        <a-input
          placeholder="请输入物料编码"
          v-decorator="['matnr', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="物料类型"
      >
        <a-input
          placeholder="请输入物料类型"
          v-decorator="['mtart', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="单位"
      >
        <a-input
          placeholder="请输入单位"
          v-decorator="['meins', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="单位描述"
      >
        <a-input
          placeholder="请输入单位描述"
          v-decorator="['mseht', {}]"
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
        label="生产厂家"
      >
        <a-input
          placeholder="请输入生产厂家"
          v-decorator="['produceArea', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="规格"
      >
        <a-input
          placeholder="请输入规格"
          v-decorator="['spec', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="药品类别"
      >
        <a-input
          placeholder="请输入药品类别"
          v-decorator="['bklas', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="拼音码"
      >
        <a-input
          placeholder="请输入拼音码"
          v-decorator="['spellCode', {}]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="供应商账号"
      >
        <a-input
          placeholder="请输入供应商账号"
          v-decorator="['gysaccount', {}]"
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
  name: 'ScmDMaterAdd',
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
      scmDMater: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmDMater = {}
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
          this.$post('scmDMater', {
            ...this.scmDMater
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
      let values = this.form.getFieldsValue(['code', 'name', 'matnr', 'mtart', 'meins', 'mseht', 'txz01', 'produceArea', 'spec', 'bklas', 'spellCode', 'gysaccount'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmDMater[_key] = values[_key] })
      }
    }
  }
}
</script>
