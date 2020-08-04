<template>
  <a-drawer
    title="新增用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <div>
      <a-steps :current="current">
        <a-step
          title="基本信息"
          key="base"
        />
        <a-step
          title="院区设置"
          key="ro"
        />
      </a-steps>
      <!-- 新增用户 -->
      <my-area
        ref="aduser"
        :myAreaVisiable="true"
        v-show="current == 0"
      >
      </my-area>
      <a-form-item label='院区选择'
                   style="margin-bottom: 2rem"
                   :validateStatus="menuSelectStatus"
                   :help="menuSelectHelp"
                   v-show="current == 1"
                   v-bind="formItemLayout">
        <a-tree
          :key="menuTreeKey"
          ref="menuTree"
          :checkable="true"
          :checkStrictly="checkStrictly"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :treeData="menuTreeData">
        </a-tree>
      </a-form-item>
    </div>
    <div class="drawer-bootom-button">
      <a-button
        v-if="current < 1"
        type="primary"
        @click="next"
      >
        下一步
      </a-button>
      <a-button
        v-if="current>0"
        style="margin-left: 8px"
        @click="prev"
      >
        上一步
      </a-button>
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
        v-if="current == 1"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>

  </a-drawer>

</template>
<script>
import MyArea from './MyArea'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: { MyArea },
  name: 'UserAdd',
  props: {
    userAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      menuTreeKey: +new Date(),
      loading: false,
      current: 0,
      formItemLayout,
      validateStatus: '',
      menuSelectStatus: '',
      menuSelectHelp: '',
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
      this.$refs.aduser.reset()
      this.loading = false
      this.current = 0
      this.menuTreeKey = +new Date()
      this.expandedKeys = this.checkedKeys = []
      this.validateStatus = this.help = ''
    },
    onClose () {
      this.loading = false
      this.reset()
      this.$emit('close')
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
    handleSubmit () {
      this.$refs.aduser.handleValues()
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      this.$refs.aduser.user.areaId = checkedArr.join(',')
      this.loading = true
      this.$post('user', {
        ...this.$refs.aduser.user
      }).then((r) => {
        this.reset()
        this.$emit('success')
      }).catch(() => {
        this.loading = false
      })
    },
    next () {
      this.current++;
    },
    prev () {
      this.current--;
    }
  },
  watch: {
    userAddVisiable () {
      if (this.userAddVisiable) {
        this.$get('scmDArea').then((r) => {
          this.menuTreeData = r.data.rows.children
          this.allTreeKeys = r.data.ids
        })
      }
    }
  }
}
</script>
