<template>
  <a-spin :spinning="loading">
    <a-card
      :bordered="false"
      class="card-area"
    >
      <a-button
        type="dashed"
        @click="seniorSelect"
      >
        中级申报
      </a-button>
      <a-button
        type="dashed"
        @click="addvanceSelect"
      >
        高级申报
      </a-button>
      <a-modal
        title="中级职称选择"
        :visible="modelSeniorVisiable"
        @cancel="cancelAudit"
        @ok="handleOk"
      >
        <a-radio-group
          v-model="seniorValue"
          :options="seniorOptions"
          @change="onSeniorChange"
        />
      </a-modal>
      <a-modal
        title="高级职称选择"
        :visible="modelAddvanceVisiable"
        @cancel="cancelAaavance"
        @ok="handleAddvanceOk"
        width="800px"
      >
        <!-- <span slot="title">Finished</span> -->
        <a-row>
          <a-col :span="6">
            <a-radio-group
              name="radioGroup"
              :default-value="1"
              @change="selectAddvanceChange"
            >
              <a-radio
                :style="radioStyle"
                :value="1"
              >
                教学
              </a-radio>
              <a-radio
                :style="radioStyle"
                :value="2"
              >
                临床
              </a-radio>
            </a-radio-group>
          </a-col>
          <a-col :span="18">
            <a-radio-group
              v-model="addvanceValue"
              :options="isClinic?addvanceClinicalOptions:addvanceTeachOptions"
              @change="onSeniorChange"
            />
          </a-col>
        </a-row>
      </a-modal>
      <a-modal
        title="申报表预览"
        :visible="pdfVisiable"
        @cancel="cancelPdf"
        @ok="handlePdfOk"
        width="70%"
      >

        <dcaB-titleapply1 ref="apply1">
        </dcaB-titleapply1>

      </a-modal>
    </a-card>
  </a-spin>
</template>

<script>
import DcaBTitleapply1 from './DcaTitleapply1'
const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBTitleapply',
  data () {
    return {
      addVanceYear: '2019',
      seniorYear: '2020',
      isAddvanceOpen: false,//是否开启高级申报
      isSeniorOpen: false,//是否开启中级申报
      loading: false,
      bordered: true,
      seniorOptions: [],
      modelSeniorVisiable: false,
      seniorValue: '',
      addvanceTeachOptions: [],
      modelAddvanceVisiable: false,
      addvanceValue: '',
      addvanceClinicalOptions: [],
      isClinic: false,
      appHtml: {},
      radioStyle: {
        display: 'block',
        height: '30px',
        lineHeight: '30px',
      },
      pdfVisiable: false
    }
  },
  components: { DcaBTitleapply1 },
  computed: {


  },
  mounted () {
    this.fetch()
  },
  methods: {
    handlePdfOk () {
      //  this.$post('dcaBTitleapply/pdf', {
      //    html: this.$refs.apply1.$el.innerHTML
      //  }).then((r) => {

      //  })
      // this.$getPdf({element:document.querySelector('#printpdf')})
      this.$refs.apply1.generatePdf()
    },
    cancelPdf () {
      this.pdfVisiable = false
    },
    handleOk () {//中级申报保存
      this.pdfVisiable = true
    },
    handleAddvanceOk () {

    },
    onSeniorChange () {

    },
    onAddvanceChange () {

    },
    seniorSelect () {
      this.modelSeniorVisiable = true
    },
    cancelAaavance () {
      this.modelAddvanceVisiable = false
    },
    addvanceSelect () {
      this.modelAddvanceVisiable = true
    },
    selectAddvanceChange (e) {
      console.log(e)
      this.isClinic = e.target.value == 2
    },
    cancelAudit () {
      this.modelSeniorVisiable = false
    },
    fetch () {
      this.loading = true

      this.$get('dcaDDeclaremodules', {
        page: 1,
        pageSize: 100
      }).then((r) => {
        let data = r.data

        this.loading = false
        const dataSource = data.rows
        const seniorRows = dataSource.filter(p => p.parentId == '10002')
        seniorRows.forEach(element => {
          this.seniorOptions.push({ label: element.name, value: element.code })
        })
        const addvanceRows1 = dataSource.filter(p => p.parentId == '10001002')
        addvanceRows1.forEach(element => {
          this.addvanceClinicalOptions.push({ label: element.name, value: element.code })
        })
        const addvanceRows2 = dataSource.filter(p => p.parentId == '10001001')
        addvanceRows2.forEach(element => {
          this.addvanceTeachOptions.push({ label: element.name, value: element.code })
        })
      }
      )
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
