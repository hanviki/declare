<template>
  <a-modal
    title="核心人力资源考核表"
    :visible="infoVisiable"
    :footer="null"
    @cancel="cancelInfo"
    :maskClosable="false"
    width="90%"
  >
  
    <a-card title="考核结果" >
      <a-table
        :columns="Columns"
        :pagination="false"
        :data-source="dataSource"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
      <template slot="extra">
          <a href="#" @click="exportCustomExcel">导出</a>
      </template>
    </a-card>
  </a-modal>

</template>
  
  <script>
  import moment from 'moment' 
export default {
  name: "userInfo",
  data () {
    return {
      dataSource: [],
    }
  },
  props: {
    infoVisiable: {
      default: false
    },
    userAccount: {
      default: ''
    },
    dcaYear: {
      default: ''
    },
    zyjsgw: {
      default: ''
    },
    ks: {
      default: ''
    },
    userAccountName: {
      default: ''
    }
  },
  watch: {
    infoVisiable () {
      if (this.infoVisiable) {
        this.fetch(this.userAccount,this.dcaYear)
      }
    }
  },
  computed: {
    Columns () {
      return [
        {
        title: '考核年度',
        dataIndex: 'dcaYear',
        width: 80
      },
      {
        title: '考核项目及满分',
        dataIndex: 'moudleName',
        width: 200
      },
      {
        title: '打分科室',
        dataIndex: 'ks',
        width: 100
      },
      {
        title: '总分',
        dataIndex: 'totalNum',
        width: 100
      },
       {
        title: '个人得分',
        dataIndex: 'titleResult',
        width: 100
      },
      {
        title: '打分处室负责人签字',
        dataIndex: 'ksLeader',
        width: 100
      },
      {
        title: '备注',
        dataIndex: 'note',
        width: 200
      }
      ]
    },

  },
  methods: {
    reset () {
      this.dataSource = []
    },
    exportCustomExcel () {
     let exprotJson=  [
      {
        title: '考核项目及满分',
        dataIndex: 'moudleName',
        width: 200
      },
      {
        title: '总分',
        dataIndex: 'totalNum',
        width: 100
      },
       {
        title: '个人得分',
        dataIndex: 'titleResult',
        width: 100
      }
      ]
      let dataJson = JSON.stringify(exprotJson)
      this.$download('checkDReport/excel', {
        userAccount:this.userAccount,
        dcaYear:this.dcaYear,
        dataJson: dataJson,
        zyjsgw: this.zyjsgw,
        ks: this.ks,
        userAccountName: this.userAccountName
      },this.userAccountName+"_"+this.dcaYear+"_"+'核心人力资源考核表'+'.xls')
    },
    fetch (userAccount,dcaYear) {
      this.$get('checkDReport/getPersonInfo',{
        userAccount,
        dcaYear
      }).then((r) => {
        let data = r.data
        this.dataSource = data
      })
    },
    cancelInfo () {
      this.reset()
      this.$emit('close')
    }


  }
}
  </script>
  
  <style>
</style>