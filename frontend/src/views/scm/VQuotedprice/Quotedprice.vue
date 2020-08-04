<template>
  <a-modal
    title="报价信息"
    :visible="oneVisiable"
    :width="1200"
    :footer="null"
    @cancel="handleOk"
  >
  <p style="font-size:18px;">药品名称:{{scmBQueryprice.txz01}} 药品编码：{{scmBQueryprice.matnr}} 规格：{{scmBQueryprice.spec}} 计量单位：{{scmBQueryprice.mseht}} 采购数量：{{scmBQueryprice.amount}}</p>
    <a-tabs
      defaultActiveKey="1"
      @change="callback"
    >
      <a-tab-pane
        tab="报价详情"
        key="1"
      >
        <quotePrice-one
          ref="quoteprice1"
          :oneVisiable="oneVisiable"
          :baseId="baseId"
        >
        </quotePrice-one>
      </a-tab-pane>
      <a-tab-pane
        tab="市场价格表"
        key="2"
        forceRender
      >
        <quotePrice-third
          ref="quoteprice3"
          :oneVisiable="oneVisiable"
          :baseId="baseId"
        >
        </quotePrice-third>
      </a-tab-pane>
      <a-tab-pane
        tab="各医院价格表"
        key="3"
        forceRender
      >
        <quotePrice-second
          ref="quoteprice2"
          :oneVisiable="oneVisiable"
          :baseId="baseId"
        >
        </quotePrice-second>
      </a-tab-pane>
    </a-tabs>
  </a-modal>
</template>
<script>
import QuotePriceOne from './quote1'
import QuotePriceSecond from './quote2'
import QuotePriceThird from './quote3'

export default {
  components: { QuotePriceOne, QuotePriceSecond, QuotePriceThird },
  data () {
    return {

    };
  },
  props: {
    oneVisiable: {
      default: false
    },
    baseId: '',
    scmBQueryprice: {
      required: true
    },
  },
  methods: {
    reset () {
      if (this.$refs.quoteprice3) {
        this.$refs.quoteprice3.reset()
      }
      if (this.$refs.quoteprice2) {
        this.$refs.quoteprice2.reset()      }
      if (this.$refs.quoteprice1) {
        this.$refs.quoteprice1.reset()
      }
    },
    callback (key) {
      console.log(key);
    },
    handleOk (e) {
      this.reset()
      this.$emit("close")
    }

  },
}
</script>