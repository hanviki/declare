<template>
  <div>
    <a-form :form="form">
      <h2>请仔细填写相关内容</h2>
      <a-form-item
        label="供应商类别"
        :label-col="{ span: 6 }"
        :wrapper-col="{ span: 17 }"
      >
        <a-select 
          v-decorator="[
          'lb',
          { rules: [{ required: true, message: '请选择供应商' }],initialValue:'药品供应商' },
        ]"
          placeholder="请选择供应商"
          @change="handleSelectChange"
        >
          <a-select-option value="0" >
            药品供应商
          </a-select-option>
          <a-select-option value="1">
            物资供应商
          </a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
    <medicine-vendor @regist="returnLogin" v-show="isVisable == 0">
    </medicine-vendor>
    <mater-vendor @regist="returnLogin" v-show="isVisable == 1">
    </mater-vendor>
  </div>
</template>

<script>
import MedicineVendor from './MedicineVendor'
import MaterVendor from './MaterVendor'
export default {
  components: { MedicineVendor, MaterVendor },
  name: 'Regist',
  data () {
    return {
      isVisable: 0,
      formLayout: 'horizontal',
      form: this.$form.createForm(this, { name: 'form' })
    };
  },
  methods: {
    handleSelectChange (value) {
      this.isVisable = value
    },
    returnLogin ()
    {
      this.$emit('regist', 'Login')
    }
  }
};
</script>
