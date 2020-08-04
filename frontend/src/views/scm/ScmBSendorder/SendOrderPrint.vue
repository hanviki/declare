<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :width="1000"
    @cancel="handleCancleClick"
    title="送货清单打印"
  >
    <div
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
    bsart: '',
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
      LODOP.PRINT_INIT("送货清单打印");
      LODOP.ADD_PRINT_HTM(10, 10, this.$refs.printDiv.clientWidth, this.$refs.printDiv.clientHeight, this.printHtml);
      // LODOP.ADD_PRINT_HTM(500, 2000, 100, 100, "<font format='ChineseNum'><span tdata='pageNo'>第##页</span><span tdata='pageCount'>共##页</span></font>")
      // LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
      LODOP.SET_PRINT_PAGESIZE(2, 2100, 2970, "");//横向
      // LODOP.PREVIEW();
      LODOP.PRINTA(); //直接打印在默认打印机
    },
    PrintPreviewPage () {
      let LODOP= this.lodop
      LODOP.PRINT_INIT("送货清单打印");
      LODOP.ADD_PRINT_HTM(10, 10, this.$refs.printDiv.clientWidth, this.$refs.printDiv.clientHeight, this.printHtml);
      // LODOP.ADD_PRINT_HTM(600, 500, 100, 100, "<font format='ChineseNum'><span tdata='pageNo'>第##页</span><span tdata='pageCount'>共##页</span></font>")
      // LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
      LODOP.SET_PRINT_PAGESIZE(2, 2100, 2970, "");
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
      console.info("bsart:" + this.bsart)
      this.$post(`scmBSupplyplan/print`, { id: this.ids, bsart: this.bsart }).then((r) => {
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
