<template>
  <div>
       <a-form-item label='模块选择'
                   style="margin-bottom: 2rem"
                   :validateStatus="menuSelectStatus"
                   :help="menuSelectHelp"
                   v-bind="formItemLayout">
        <a-tree
          :key="menuTreeKey"
          ref="menuTree"
          :checkable="true"
          :checkStrictly="checkStrictly"
          :defaultCheckedKeys="defaultCheckedKeys[0]"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :treeData="menuTreeData">
        </a-tree>
      </a-form-item>
  </div>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
 data () {
    return {
      menuTreeKey: +new Date(),
      formItemLayout,
      validateStatus: '',
      menuSelectStatus: '',
      menuSelectHelp: '',
      defaultCheckedKeys: [],
      help: '',
      checkedKeys: [],
      expandedKeys: [],
      menuTreeData: [],
      allTreeKeys: [],
      checkStrictly: true
    }
  },
  methods: {
    reset () {
      this.menuTreeKey = +new Date()
      this.expandedKeys = this.checkedKeys = []
      this.validateStatus = this.help = ''
    },
    expandAll () {
      this.expandedKeys = this.allTreeKeys
    },
    closeAll () {
      this.expandedKeys = []
    },
    enableRelate () {
      this.checkStrictly = false
    },
    disableRelate () {
      this.checkStrictly = true
    },
    handleCheck (checkedKeys) {
      this.checkedKeys = checkedKeys
      let checkedArr = Object.is(checkedKeys.checked, undefined) ? checkedKeys : checkedKeys.checked
      if (checkedArr.length) {
        this.menuSelectStatus = ''
        this.menuSelectHelp = ''
      } else {
        this.menuSelectStatus = 'error'
        this.menuSelectHelp = '请选择相应的权限'
      }
    },
    handleExpand (expandedKeys) {
      this.expandedKeys = expandedKeys
    },
    getAuditKey () {
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      return checkedArr.join(',')
    }
  }
}
</script>

<style>

</style>