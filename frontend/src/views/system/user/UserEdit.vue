<template>
  <a-drawer
    title="修改用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-steps :current="current">
      <a-step
        title="基本信息"
        key="base"
      />
      <a-step
        title="模块设置"
        key="ro"
      />
      <a-step
        title="审核设置"
        key="ao"
      />
       <a-step
        title="系列设置"
        key="xl"
      />
    </a-steps>
    <a-form
      :form="form"
      v-show="current == 0"
    >
      <a-form-item
        label='用户名'
        v-bind="formItemLayout"
      >
        <a-input
          readOnly
          v-decorator="['username']"
        />
      </a-form-item>
      <a-form-item
        label='邮箱'
        v-bind="formItemLayout"
      >
        <a-input v-decorator="[
          'email',
          {rules: [
            { type: 'email', message: '请输入正确的邮箱' },
            { max: 50, message: '长度不能超过50个字符'}
          ]}
        ]" />
      </a-form-item>
      <a-form-item
        label="手机"
        v-bind="formItemLayout"
      >
        <a-input v-decorator="['mobile', {rules: [
            { pattern: '^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$', message: '请输入正确的手机号'}
          ]}]" />
      </a-form-item>
      <a-form-item
        label="姓名"
        v-bind="formItemLayout"
      >
        <a-input v-decorator="[
          'realname',
          { rules: [{ max: 50, message: '长度不能超过50个字符' }] }
        ]" />
      </a-form-item>
      <a-form-item
        label='角色'
        v-bind="formItemLayout"
      >
        <a-select
          mode="multiple"
          :allowClear="true"
          style="width: 100%"
          v-decorator="[
            'roleId',
            {rules: [{ required: true, message: '请选择角色' }]}
          ]"
        >
          <a-select-option
            v-for="r in roleData"
            :key="r.roleId.toString()"
          >{{r.roleName}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item
        label='部门'
        v-bind="formItemLayout"
      >
        <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="deptTreeData"
          @change="onDeptChange"
          :value="userDept"
        >
        </a-tree-select>
      </a-form-item>
      <a-form-item
        label='状态'
        v-bind="formItemLayout"
      >
        <a-radio-group v-decorator="[
            'status',
            {rules: [{ required: true, message: '请选择状态' }]}
          ]">
          <a-radio value="0">锁定</a-radio>
          <a-radio value="1">有效</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item
        label='性别'
        v-bind="formItemLayout"
      >
        <a-radio-group v-decorator="[
            'ssex',
            {rules: [{ required: true, message: '请选择性别' }]}
          ]">
          <a-radio value="0">男</a-radio>
          <a-radio value="1">女</a-radio>
          <a-radio value="2">保密</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item
        label='审核部门'
        v-bind="formItemLayout"
      >
       <a-input v-decorator="[
          'code'
        ]" />
      </a-form-item>
    </a-form>
    <a-form v-show="current == 1">
      <a-form-item
        label='模块选择'
        style="margin-bottom: 2rem"
        :validateStatus="menuSelectStatus"
        :help="menuSelectHelp"
        v-bind="formItemLayout"
      >
        <a-tree
          :key="menuTreeKey"
          ref="menuTree"
          :checkable="true"
          :checkStrictly="checkStrictly"
          :defaultCheckedKeys="defaultCheckedKeys[0]"
          @check="handleCheck"
          @expand="handleExpand"
          :expandedKeys="expandedKeys"
          :treeData="menuTreeData"
        >
        </a-tree>
      </a-form-item>

    </a-form>
    <a-form v-show="current == 2">
      <audit-tree ref="auTree">
      </audit-tree>
    </a-form>
      <a-form v-show="current == 3">
      <xl-tree ref="xlTree">
      </xl-tree>
    </a-form>
    <div class="drawer-bootom-button">
      <a-dropdown
        style="float: left"
        :trigger="['click']"
        placement="topCenter"
      >
        <a-menu slot="overlay">
          <a-menu-item
            key="1"
            @click="expandAll"
          >展开所有</a-menu-item>
          <a-menu-item
            key="2"
            @click="closeAll"
          >合并所有</a-menu-item>
          <a-menu-item
            key="3"
            @click="enableRelate"
          >父子关联</a-menu-item>
          <a-menu-item
            key="4"
            @click="disableRelate"
          >取消关联</a-menu-item>
        </a-menu>
        <a-button>
          树操作
          <a-icon type="up" />
        </a-button>
      </a-dropdown>
      <a-button
        v-if="current < 3"
        type="primary"
        @click="next"
      >
        下一步
      </a-button>
      <a-button
        v-if="current>0"
        style="margin-left: .8rem;margin-right: .8rem"
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
        v-if="current == 3"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import { mapState, mapMutations } from 'vuex'
import AuditTree from './AuditTree'
import XlTree from './XlTree'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'EditEdit',
  props: {
    userEditVisiable: {
      default: false
    }
  },
  data () {
    return {
      menuTreeKey: +new Date(),
      current: 0,
      formItemLayout,
      form: this.$form.createForm(this),
      deptTreeData: [],
      roleData: [],
      userDept: [],
      userId: '',
      loading: false,
      validateStatus: '',
      menuSelectStatus: '',
      menuSelectHelp: '',
      help: '',
      checkedKeys: [],
      expandedKeys: [],
      menuTreeData: [],
      defaultCheckedKeys: [],
      allTreeKeys: [],
      checkStrictly: true,
      selectAuditData: {},
      auditData: {},
      selectXlData: {},
      xlData: {}
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    })
  },
  components: { AuditTree, XlTree },
  methods: {
    ...mapMutations({
      setUser: 'account/setUser'
    }),
    onClose () {
      this.loading = false
      this.current = 0
      this.menuTreeKey = +new Date()
      this.expandedKeys = []
      this.checkedKeys = []
      this.defaultCheckedKeys = []
      this.menuSelectStatus = this.menuSelectHelp = ''
      this.$refs.auTree.reset()
      this.$refs.xlTree.reset()
      this.form.resetFields()
      this.$emit('close')
    },
    setFormValues ({ ...user }) {
      this.userId = user.userId
      let fields = ['username', 'email', 'realname', 'status', 'ssex', 'mobile', 'code']
      Object.keys(user).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = user[key]
          this.form.setFieldsValue(obj)
        }
      })
      if (user.roleId) {
        this.form.getFieldDecorator('roleId')
        let roleArr = user.roleId.split(',')
        this.form.setFieldsValue({ 'roleId': roleArr })
      }
      if (user.deptId) {
        this.userDept = [user.deptId]
      }
    },
    onDeptChange (value) {
      this.userDept = value
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
      let checkedArr = Object.is(this.checkedKeys.checked, undefined) ? this.checkedKeys : this.checkedKeys.checked
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          let user = this.form.getFieldsValue()
          user.roleId = user.roleId.join(',')
          user.userId = this.userId
          user.deptId = this.userDept
          user.areaId = checkedArr.join(',')
          user.auditId = this.$refs.auTree.getAuditKey()
          user.xlId = this.$refs.xlTree.getAuditKey()
          this.$put('user', {
            ...user
          }).then((r) => {
            this.loading = false
            this.current = 0
            this.$emit('success')
            // 如果修改用户就是当前登录用户的话，更新其state
            if (user.username === this.currentUser.username) {
              this.$get(`user/${user.username}`).then((r) => {
                this.setUser(r.data)
              })
            }
          }).catch(() => {
            this.loading = false
          })
        }
      })
    },
    next () {
      this.current++;
      let that = this
      if (that.current == 2) {

        that.$refs.auTree.menuTreeData = that.auditData.rows.children
        that.$refs.auTree.allTreeKeys = that.auditData.ids
        setTimeout(function () {
          console.info(that.$refs.auTree.defaultCheckedKeys)
          that.$refs.auTree.defaultCheckedKeys.splice(0, that.$refs.auTree.defaultCheckedKeys.length, that.selectAuditData)
          that.$refs.auTree.checkedKeys = that.selectAuditData
          that.$refs.auTree.expandedKeys = that.selectAuditData
          that.$refs.auTree.menuTreeKey = +new Date()
        }, 200)

      }
      if (that.current == 3) {

        that.$refs.xlTree.menuTreeData = that.xlData.rows.children
        that.$refs.xlTree.allTreeKeys = that.xlData.ids
        setTimeout(function () {
          console.info(that.$refs.xlTree.defaultCheckedKeys)
          that.$refs.xlTree.defaultCheckedKeys.splice(0, that.$refs.xlTree.defaultCheckedKeys.length, that.selectXlData)
          that.$refs.xlTree.checkedKeys = that.selectXlData
          that.$refs.xlTree.expandedKeys = that.selectXlData
          that.$refs.xlTree.menuTreeKey = +new Date()
        }, 200)

      }
    },
    prev () {
      this.current--;
    }
  },
  watch: {
    userEditVisiable () {
      if (this.userEditVisiable) {
        this.$get('role',{pageSize:100,page:1}).then((r) => {
          this.roleData = r.data.rows
        })
        this.$get('dept').then((r) => {
          this.deptTreeData = r.data.rows.children
        })
        this.$get('dcaDMudules/tree').then((r) => {
          this.menuTreeData = r.data.rows.children
          this.allTreeKeys = r.data.ids
          this.$get('dcaUserMoudules/mudules/' + this.userId).then((r) => {
            this.defaultCheckedKeys.splice(0, this.defaultCheckedKeys.length, r.data)
            this.checkedKeys = r.data
            this.expandedKeys = r.data
            this.menuTreeKey = +new Date()
          })
        })
        this.$get('dcaDAuditinfo/tree').then((r) => {
          this.auditData = r.data
          this.$get('dcaUserAudit/mudules/' + this.userId).then((r) => {
            this.selectAuditData = r.data
          })
        })
        this.$get('dcaDXl/tree').then((r) => {
          this.xlData = r.data
          this.$get('dcaUserXl/mudules/' + this.userId).then((r) => {
            this.selectXlData = r.data
          })
        })
      }
    }
  }
}
</script>
