<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :width="800"
    @cancel="handleCancleClick"
    title="供应计划打印"
  >
    <div style="height:300px; overflow-y: scroll;"
      ref="printDiv"
      v-html="printHtml"
    >
    </div>
    <template slot="footer">
      <a-button
        key="back"
        @click="handleCancel"
      >关闭</a-button>
      <a-button
        key="noSubmit"
        type="primary"
        @click="PrintPrintPage"
      >
        打印
      </a-button>
      <a-button
        key="submit"
        type="primary"
        @click="PrintPreviewPage"
      >
        预览
      </a-button>
    </template>
  </a-modal>
</template>
<script>


export default {
  name: 'print',
  props: {
    printVisiable: {
      require: true,
      default: false
    },
    ids: '',
    lodop: {}
  },
  data () {
    return {
      printHtml: '',
      loading: false
    }
  },
  methods: {
    reset () {
      this.printHtml = ''
      this.loading = false
    },
    PrintPrintPage () {
      let LODOP= this.lodop
      LODOP.PRINT_INIT("供应计划打印");
      LODOP.ADD_PRINT_HTM(15, 6, this.$refs.printDiv.clientWidth, this.$refs.printDiv.clientHeight, this.printHtml);
      LODOP.SET_PRINT_PAGESIZE(1,  1020, 650, "");
      // LODOP.PREVIEW();
      LODOP.PRINTA(); //直接打印在默认打印机
    },
    PrintPreviewPage () {
      let LODOP =this.lodop
      LODOP.PRINT_INIT("供应计划打印");
      LODOP.ADD_PRINT_HTM(15, 6, this.$refs.printDiv.clientWidth, this.$refs.printDiv.clientHeight, this.printHtml);
      LODOP.SET_PRINT_PAGESIZE(1,  1020, 650, "");
      LODOP.PREVIEW();
      //LODOP.PRINT();
    },
    handleCancleClick (e) {
      this.printHtml = ''
      this.$emit('close')
    },
    handleCancel (e) {
      this.printHtml = ''
      this.$emit('close')
    },
    printContent () {
      this.$post(`scmBSupplyplan/printPlan`, { ids: this.ids }).then((r) => {
        let data = r.data.data
        this.printHtml = data
      })
    }
  },
  computed: {
    show: {
      get: function () {
        return this.printVisiable
      },
      set: function () {
      }
    }
  },
  watch: {
    printVisiable () {
      if (this.printVisiable) {
        this.printContent()
      }
    }
  }
}
</script>
