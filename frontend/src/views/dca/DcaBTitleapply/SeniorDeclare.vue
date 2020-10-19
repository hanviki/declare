<template>
  <a-card
    :bordered="false"
    class="card-area"
    title="中级职称申请"
  >
    <a-radio-group
      v-model="value2"
      :options="options"
      @change="onChange2"
    />
  </a-card>
</template>

<script>
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
const options = [
  { label: '中级1', value: '10002001' },
  { label: '中级1', value: '10002002' },
];
export default {
  name: 'DcaBTitleapply',
  data () {
    return {
      loading: false,
      bordered: true,
      options,
    }
  },
  computed: {


  },
  mounted () {
  },
  methods: {
    onChange2 (e) {
      console.log('radio2 checked', e.target.value);
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
