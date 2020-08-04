<template>
  <a-card
    :title="title"
    :headStyle="tstyle"
  >
    <a-form :form="form">
      <a-row>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="姓名"
          >
            <a-input
              placeholder="请输入姓名"
              v-decorator="['name', { rules: [{ required: true, message: '姓名不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="联系电话"
          >
            <a-input
              placeholder="请输入联系电话"
              v-decorator="['telphone', { rules: [{ required: true, message: '联系电话不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="免冠照片"
          >
            <uploadSingle-file 
              ref="filehead"
              @uploadRemove="removeHead"
              @uploadSuc="uploadHead"
            >
            </uploadSingle-file>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="身份证号"
          >
            <a-input
              placeholder="请输入身份证号"
              v-decorator="['idcard', { rules: [{ required: true, message: '身份证号不能为空' }] }]"
            />
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="正面"
          >
            <uploadSingle-file
              ref="filefront"
              @uploadRemove="removeIDCARD1"
              @uploadSuc="uploadIDCARD1"
            >
            </uploadSingle-file>
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="反面"
          >
            <uploadSingle-file
              ref="fileback"
              @uploadRemove="removeIDCARD2"
              @uploadSuc="uploadIDCARD2"
            >
            </uploadSingle-file>
          </a-form-item>
        </a-col>
      </a-row>
      <a-row>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="委托起始"
          >
            <a-date-picker
              v-decorator="[
          'validDatestart',
          { rules: [{ required:this.isRequire , message: '请输入委托起始时间'}] },
        ]"
              placeholder="请输入委托起始时间"
              @change="onChange"
            />
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="委托结束"
          >
            <a-date-picker
              v-decorator="[
          'validDate',
          { rules: [{ required:this.isRequire , message: '请输入委托结束时间' }] },
        ]"
              placeholder="请输入委托结束时间"
              @change="onChange"
            />
          </a-form-item>
        </a-col>
        <a-col
          :md="8"
          :sm="24"
        >
          <a-form-item
            v-bind="formItemLayout"
            label="委托书"
          >
            <uploadSingle-file
              ref="fileagent"
              @uploadRemove="removeAgent"
              @uploadSuc="uploadAgent"
            >
            </uploadSingle-file>
          </a-form-item>
        </a-col>
      </a-row>
      <div style="color:red;">{{noteu}}</div>
    </a-form>
  </a-card>
</template>
<script>
import UploadSingleFile from './uploadSingleFile'
import { mapState } from 'vuex'
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15 },
};
export default {
  name: "file",
  components :{UploadSingleFile},
  data () {
    return {
      tstyle: { "color": "#0785fd", "font-weight": "bold", "background-color": "#ececec" },
      fileList: [],
      isShow: 1,
      uploading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      vendorUser: {
        name: '',
        validDatestart: '',
        validDate: '',
        fileId: '',
        idcard: '',
        idcardBack: '',
        idcardFront: '',
        telphone: '',
        headImage: '',
        agentImage: ''
      },
      noteu:''
    }
  },
  props: {
    title: '',
    displayIndex: 0,
    isRequire: false
  },
  computed : {
    ...mapState({
      user: state => state.account.user
    })
   },
  methods: {
    uploadHead (fileId) {
      this.vendorUser.headImage = fileId
    },
    removeHead () {
      this.vendorUser.headImage = ''
    },
    uploadIDCARD2 (fileId) {
      this.vendorUser.idcardBack = fileId
    },
    removeIDCARD2 () {
      this.vendorUser.idcardBack = ''
    },
    uploadIDCARD1 (fileId) {
      this.vendorUser.idcardFront = fileId
    },
    removeIDCARD1 () {
      this.vendorUser.idcardFront = ''
    },
    uploadAgent (fileId) {
      this.vendorUser.agentImage = fileId
    },
    removeAgent () {
      this.vendorUser.agentImage = ''
    },
    onChange (date, dateString) {
      console.log(date, dateString);
    },
    setVendorUserFields () {
      let values = this.form.getFieldsValue(['validDatestart', 'validDate', 'name', 'telphone', 'idcard'])
      if (typeof values !== 'undefined') {
        Object.keys(values).forEach(_key => { this.vendorUser[_key] = values[_key] })
      }
    },
    getVendorUserFields (entity) {
      let fields = ['validDatestart', 'validDate', 'name', 'telphone', 'idcard']
      let fieldDates = ['validDatestart', 'validDate']
      let vendorUser = entity.scmDVendoruser
      this.noteu=vendorUser.noteu//变更信息
    
      Object.keys(vendorUser).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (vendorUser[key] !== '') {
              obj[key] = moment(vendorUser[key])
            }
          } else {
            obj[key] = vendorUser[key]
          }
          this.form.setFieldsValue(obj)
        }
      });
      
      this.vendorUser.idcardBack=entity.fileback.id
      this.vendorUser.idcardFront=entity.filefront.id

      this.vendorUser.headImage=entity.filehead.id
      this.vendorUser.agentImage=entity.fileAgent.id

      let that = this
      console.log(entity);
             that.$refs.filehead.setForm(entity.filehead.id,entity.filehead.clientName,entity.filehead.serverName);
             that.$refs.filefront.setForm(entity.filefront.id,entity.filefront.clientName,entity.filefront.serverName);
             that.$refs.fileback.setForm(entity.fileback.id,entity.fileback.clientName,entity.fileback.serverName);
             that.$refs.fileagent.setForm(entity.fileAgent.id,entity.fileAgent.clientName,entity.fileAgent.serverName);
      
    }


  }
}
</script>
