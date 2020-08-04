<template>
  <a-drawer
    title="新增院区"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="scmDAreaAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        label='院区名称'
        v-bind="formItemLayout"
      >
        <a-input v-decorator="['name',
                   {rules: [
                    { required: true, message: '院区名称不能为空'},
                    { max: 20, message: '长度不能超过20个字符'}
                  ]}]" />
      </a-form-item>
      <a-form-item
        label='院区编码'
        v-bind="formItemLayout"
      >
        <a-input v-decorator="['code',
                   {rules: [
                    { required: true, message: '院区编码不能为空'},
                    { max: 20, message: '长度不能超过20个字符'}
                  ]}]" />
      </a-form-item>
      <a-form-item
        label='类别'
        v-bind="formItemLayout"
      >
        <a-radio-group v-decorator="['state',{rules: [{ required: true, message: '请选择类别'}]}]">
          <a-radio value="0">院区</a-radio>
          <a-radio value="1">科室</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item
        label='院区排序'
        v-bind="formItemLayout"
      >
        <a-input-number
          v-model="scmDArea.orderNum"
          style="width: 100%"
        />
      </a-form-item>
      <a-form-item
        label='上级院区'
        style="margin-bottom: 2rem"
        v-bind="formItemLayout"
      >
        <a-tree
          :key="scmDAreaTreeKeys"
          :checkable="true"
          :checkStrictly="true"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :treeData="scmDAreaTreeData"
        >
        </a-tree>
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
  name: 'scmDAreaAdd',
  props: {
    scmDAreaAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmDArea: {},
      checkedKeys: [],
      expandedKeys: [],
      scmDAreaTreeData: [],
      scmDAreaTreeKeys: +new Date()
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmDAreaTreeKeys = +new Date()
      this.expandedKeys = this.checkedKeys = []
      this.scmDArea = {}
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleCheck (checkedKeys) {
      this.checkedKeys = checkedKeys
    },
    handleExpand (expandedKeys) {
      this.expandedKeys = expandedKeys
    },
    handleSubmit () {
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      if (checkedArr.length > 1) {
        this.$message.error('最多只能选择一个上级部门，请修改')
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setscmDAreaFields()
          this.loading = true
          if (checkedArr.length) {
            this.scmDArea.parentId = checkedArr[0]
          } else {
            this.scmDArea.parentId = ''
          }
          this.$post('scmDArea', {
            ...this.scmDArea
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    setscmDAreaFields () {
      let values = this.form.getFieldsValue(['name', 'code', 'state'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.scmDArea[_key] = values[_key] })
      }
    }
  },
  watch: {
    scmDAreaAddVisiable () {
      if (this.scmDAreaAddVisiable) {
        this.$get('scmDArea').then((r) => {
          this.scmDAreaTreeData = r.data.rows.children
        })
      }
    }
  }
}
</script>
