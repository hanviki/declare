<template>
  <div style="width:100%">
    <a-spin :spinning="loading">
      <!--
      <a-card
        title="中级职称申请"
        :bordered="true"
        class="card-area"
      >
        <a-select
          show-search
          style="width: 200px"
          @change="handleChange"
          v-model="selectSeniorYear"
        >
          <a-select-option
            v-for="d in seniorYearArr"
            :key="d.value"
          >
            {{ d.text }}
          </a-select-option>

        </a-select>
        <a-popconfirm
          title="确定开启申报？"
          @confirm="applyLow()"
          okText="确定"
          cancelText="取消"
          v-if="seniorYear==0"
        >
          <a-button style="margin-right: .8rem">开启申报</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定关闭申报？"
          @confirm="applyLowClose()"
          okText="确定"
          cancelText="取消"
          v-if="seniorYear>0"
        >
          <a-button style="margin-right: .8rem">关闭申报</a-button>
        </a-popconfirm>
      </a-card>
      <a-card
        title="高级职称申请"
        :bordered="true"
        class="card-area"
      >
        <a-select
          show-search
          style="width: 200px"
          @change="handleAddvanceChange"
          v-model="selectAddvanceYear"
        >
          <a-select-option
            v-for="d in addvanceYearArr"
            :key="d.value"
          >
            {{ d.text }}
          </a-select-option>

        </a-select>
        <a-popconfirm
          title="确定开启申报？"
          @confirm="applyAddance(1)"
          okText="确定"
          cancelText="取消"
          v-if="addvanceYear==0"
        >
          <a-button style="margin-right: .8rem">开启申报</a-button>
        </a-popconfirm>
        <a-popconfirm
          title="确定关闭申报？"
          @confirm="applyAddanceClose()"
          okText="确定"
          cancelText="取消"
          v-if="addvanceYear>0"
        >
          <a-button style="margin-right: .8rem">关闭申报</a-button>
        </a-popconfirm>
       
      </a-card>-->
       <dca-titleapply1>
        </dca-titleapply1>
    </a-spin>
  </div>
</template>

<script>
import DcaTitleapply1 from '../DcaBTitleapply/DcaTitleapply1'
export default {
  data () {
    return {
      seniorYear: 0,
      addvanceYear: 0,
      selectSeniorYear: (new Date()).getFullYear(),
      selectAddvanceYear: (new Date()).getFullYear(),
      loading: false,
      addvanceId:'',
      seniorId:''
    }
  },
  components: { DcaTitleapply1 },
  computed: {
    seniorYearArr () {
      if (this.seniorYear > 0) {
        return [{ value: this.seniorYear, text: this.seniorYear }]
      }
      else {
        let arr = []
        var myDate = new Date()
        var startYear = myDate.getFullYear() - 5//起始年份
        var endYear = myDate.getFullYear() + 1//结束年份
        for (var i = startYear; i <= endYear; i++) {
          arr.push({ value: i, text: i })
        }
        return arr
      }
    },
    
    addvanceYearArr () {
      if (this.addvanceYear > 0) {
        return [{ value: this.addvanceYear, text: this.addvanceYear }]
      }
      else {
        let arr = []
        var myDate = new Date()
        var startYear = myDate.getFullYear() - 5//起始年份
        var endYear = myDate.getFullYear() + 1//结束年份
        for (var i = startYear; i <= endYear; i++) {
          arr.push({ value: i, text: i })
        }
        return arr
      }
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    handleChange (value) {
      this.selectSeniorYear = value
    },
    handleAddvanceChange (value) {
      this.selectAddvanceYear = value
    },
    applyAddance () {
      this.loading = true
      this.$post('dcaBYearapply', {
        isAddvance: true,
        isOpen: true,
        year: this.selectAddvanceYear,
        isDeletemark: 1
      }).then(() => {
        this.fetch()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    applyAddanceClose () {
      this.loading = true
      this.$put('dcaBYearapply', {
        isAddvance: true,
        isOpen: false,
        year: this.selectAddvanceYear,
        isDeletemark: 1,
        id: this.addvanceId
      }).then(() => {
        this.addvanceYear = 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    applyLow () {
      this.loading = true
      this.$post('dcaBYearapply', {
        isAddvance: false,
        isOpen: true,
        year: this.selectSeniorYear,
        isDeletemark: 1
      }).then(() => {
        this.fetch()
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    applyLowClose () {
      this.loading = true
      this.$put('dcaBYearapply', {
        isAddvance: false,
        isOpen: false,
        year: this.selectSeniorYear,
        isDeletemark: 1,
        id: this.seniorId
      }).then(() => {
        this.seniorYear = 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    fetch () {
      this.loading = true
      this.$get('dcaBYearapply', {
        page: 1,
        pageSize: 100
      }).then((r) => {
        let data = r.data
        var addvanceYearObj = data.rows.find(p => p.isAddvance == true && p.isOpen == true)
        if (addvanceYearObj != null) {
          this.addvanceYear = addvanceYearObj.year
          this.selectAddvanceYear = addvanceYearObj.year
          this.addvanceId = addvanceYearObj.id
        }
        var seniorYearObj = data.rows.find(p => p.isAddvance == false && p.isOpen == true)
        if (seniorYearObj != null) {
          this.seniorYear = seniorYearObj.year
          this.selectSeniorYear = seniorYearObj.year
          this.seniorId = seniorYearObj.id
        }
        this.loading = false

      })
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>