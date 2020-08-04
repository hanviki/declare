<template>
  <div>
    <a-modal
      title="验证码"
      :visible="visible"
      @ok="handleOk"
      :confirmLoading="confirmLoading"
      @cancel="handleCancel"
    >
      <a-select
        defaultValue="药品供应商"
        placeholder="请选择供应商类型"
        style="width:100%; margin-bottom:3px;"
        @change="handleSelectChange"
      >
        <a-select-option value="0">
          药品供应商
        </a-select-option>
        <a-select-option value="1">
          物资供应商
        </a-select-option>
      </a-select>
       <a-input placeholder="请输入验证码"  v-model="vendorCode" />
    </a-modal>
  </div>
</template>
<script>
export default {
  props: {
    visible: {
      default: false
    }
  },
  data () {
    return {
      ModalText: 'Content of the modal',
      confirmLoading: false,
      vendorType: 0,
      vendorCode: ''
    };
  },
  methods: {
    handleOk (e) {
        console.log("vendorCode:"+this.vendorCode)
      this.$emit("confirm",this.vendorType,this.vendorCode);
    },
    handleCancel (e) {
      this.$emit("close");
    },
    handleSelectChange (value) {
      this.vendorType = value
    },
  },
};
</script>
