<template>
  <a-drawer
    title="修改按钮"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="scmDAreaEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        label='院区名称'
        v-bind="formItemLayout"
      >
        <a-input v-decorator="['Name',
                   {rules: [
                    { required: true, message: '院区名称不能为空'},
                    { max: 20, message: '长度不能超过20个字符'}
                  ]}]" />
      </a-form-item>
      <a-form-item
        label='院区排序'
        v-bind="formItemLayout"
      >
        <a-input-number
          v-decorator="['orderNum']"
          style="width: 100%"
        />
      </a-form-item>
      <a-form-item
        label='上级院区'
        style="margin-bottom: 2rem"
        v-bind="formItemLayout"
      >
        <a-tree
          :key="scmDAreaTreeKey"
          :checkable="true"
          :checkStrictly="true"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :defaultCheckedKeys="defaultCheckedKeys"
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
  name: 'scmDAreaEdit',
  props: {
    scmDAreaEditVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      scmDAreaTreeKey: +new Date(),
      scmDArea: {},
      checkedKeys: [],
      expandedKeys: [],
      defaultCheckedKeys: [],
      scmDAreaTreeData: []
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.scmDAreaTreeKey = +new Date()
      this.expandedKeys = this.checkedKeys = this.defaultCheckedKeys = []
      this.button = {}
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
    setFormValues ({ ...scmDArea }) {
      this.form.getFieldDecorator('Name')
      this.form.setFieldsValue({ 'Name': scmDArea.text })
      this.form.getFieldDecorator('orderNum')
      this.form.setFieldsValue({ 'orderNum': scmDArea.order })
      if (scmDArea.parentId !== '0') {
        this.defaultCheckedKeys.push(scmDArea.parentId)
        this.checkedKeys = this.defaultCheckedKeys
        this.expandedKeys = this.checkedKeys
      }
      this.scmDArea.Id = scmDArea.id
    },
    handleSubmit () {
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      if (checkedArr.length > 1) {
        this.$message.error('最多只能选择一个上级院区，请修改')
        return
      }
      if (checkedArr[0] === this.scmDArea.Id) {
        this.$message.error('不能选择自己作为上级院区，请修改')
        return
      }
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          let scmDArea = this.form.getFieldsValue()
          scmDArea.parentId = checkedArr[0]
          if (Object.is(scmDArea.parentId, undefined)) {
            scmDArea.parentId = 0
          }
          scmDArea.Id = this.scmDArea.Id
          this.$put('scmDArea', {
            ...scmDArea
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  },
  watch: {
    scmDAreaEditVisiable () {
      if (this.scmDAreaEditVisiable) {
        this.$get('scmDArea').then((r) => {
          this.scmDAreaTreeData = r.data.rows.children
          this.scmDAreaTreeKey = +new Date()
        })
      }
    }
  }
}
</script>
