<template>
  <a-modal
    title="用户基本信息"
    :visible="infoVisiable"
    :footer="null"
    @cancel="cancelInfo"
    :maskClosable="false"
    width="80%"
  >
    <a-card title="个人照片">
      <a-row>
        <a-col :span="4"> <img
            :src="picUrl"
            width="120"
            height="120"
          /></a-col>
        <a-col :span="20">
          <a-row>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="姓名"
              >
                {{dcaBUser.userAccountName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="人事编号"
              >
                {{dcaBUser.userAccount}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="所在科室"
              >
                {{dcaBUser.ks}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="联系电话"
              >
                {{dcaBUser.telephone}}
              </a-form-item>
            </a-col>
           
          </a-row>
          <a-row>
 <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="教学岗位"
              >
                {{dcaBUser.zyjsgw}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="聘任时间(教学)"
              >
                {{dcaBUser.appointedDate==null?"":moment(dcaBUser.appointedDate).format('YYYY-MM-DD')}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="临床岗位"
              >
                {{dcaBUser.zyjsgwLc}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="聘任时间(临床)"
              >
                {{dcaBUser.appointedDateLc==null?"":moment(dcaBUser.appointedDateLc).format('YYYY-MM-DD')}}
              </a-form-item>
            </a-col>

            
          </a-row>
          <a-row>
<a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="拟聘岗位职务"
              >
                {{dcaBUser.npPositionName}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="博士毕业时间"
              >
                {{dcaBUser.doctorDesc}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="来院时间"
              >
                {{dcaBUser.schoolDate==null?"":moment(dcaBUser.schoolDate).format('YYYY-MM-DD')}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="职员职级"
              >
                {{dcaBUser.staffGrade}}
              </a-form-item>
            </a-col>
           

          </a-row>
          <a-row>
 <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="职员聘任时间"
              >
                {{dcaBUser.staffDate==null?"":moment(dcaBUser.staffDate).format('YYYY-MM-DD')}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="来院工作时间"
              >
                {{dcaBUser.schoolDate==null?"":moment(dcaBUser.schoolDate).format('YYYY-MM-DD')}}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item
                v-bind="formItemLayout"
                label="手机号"
              >
                {{dcaBUser.telephone}}
              </a-form-item>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
    </a-card>
    <a-card title="学习工作经历">
      <a-table
        :columns="eduColumns"
        :pagination="false"
        :data-source="eduList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="社会兼职">
      <a-table
        :columns="partjobColumns"
        :pagination="false"
        :data-source="partjobList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="奖励与处分">
      <a-table
        :columns="punishOrPrizeColumns"
        :pagination="false"
        :data-source="punishOrPrizeList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="出国情况">
      <a-table
        :columns="boardColumns"
        :pagination="false"
        :data-source="boardList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="考核情况">
      <a-table
        :columns="auditColumns"
        :pagination="false"
        :data-source="auditList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="重要岗位任职及学术影响">
      <a-table
        :columns="acdemicColumns"
        :pagination="false"
        :data-source="acdemicList"
        :rowKey="record => record.id"
        bordered
      >
      </a-table>
    </a-card>
  </a-modal>

</template>
  
  <script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 12 },
  wrapperCol: { span: 12 }
}
const formItemLayout2 = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 }
}
export default {
  name: "userInfo",
  data () {
    return {
      eduList: [],
      partjobList: [],
      punishOrPrizeList: [],
      boardList: [],
      auditList: [],
      acdemicList: [],
      formItemLayout,
      formItemLayout2,
      dcaBUser: {}
    }
  },
  props: {
    infoVisiable: {
      default: false
    },
    userAccount: {
      default: ''
    },
    picUrl: {
      default: ''
    }
  },
  watch: {
    infoVisiable () {
      if (this.infoVisiable) {
        this.fetch(this.userAccount)
        //this.dcaBUser = this.dcaBUser1
        this.getUserInfo(this.userAccount)
      }
    }
  },
  computed: {
    eduColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '自何年月',
          dataIndex: 'expStartTime',
          width: 130,
          customRender: (text, row, index) => {
            if (text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '至何年月',
          dataIndex: 'expEndTime',
          width: 130,
          customRender: (text, row, index) => {
            if (text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '何地',
          dataIndex: 'expAddress',
          width: 130
        },
        {
          title: '何学校',
          dataIndex: 'expSchool',
          width: 130
        },
        {
          title: '何单位职位或学位/学历',
          dataIndex: 'expPosition',
          width: 130
        },
        {
          title: '证明人',
          dataIndex: 'expCertifier',
          width: 130
        },
        {
          title: '是否最高学历',
          dataIndex: 'isHightest',
          width: 100
        }
      ]
    },
    partjobColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '开始时间',
          dataIndex: 'jzStartTime',
          width: 130,
          customRender: (text, row, index) => {
            if (text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: '结束时间',
          dataIndex: 'jzEndTime',
          width: 130,
          scopedSlots: { customRender: 'jzEndTime' }
        },
        {
          title: '所在学会',
          dataIndex: 'jzContent',
          width: 130,
        },
        {
          title: '职务',
          dataIndex: 'jzZw',
          width: 130,
        }
      ]
    },
    punishOrPrizeColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '奖励/处分时间',
          dataIndex: 'ppStartTime',
          width: 130,
          customRender: (text, row, index) => {
            if (text == null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },

        {
          title: '奖励/处分',
          dataIndex: 'ppCategory',
          width: 80,
        },
        {
          title: '奖励/处分名称',
          dataIndex: 'ppContent',
          width: 200
        },
        {
          title: '授奖/处分部门',
          dataIndex: 'ppPartment',
          width: 150
        },
        {
          title: '类别',
          dataIndex: 'ppLb',
          width: 80
        }
      ]
    },
    boardColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '出国时间',
          dataIndex: 'cgsj',
          width: 130
        },
        {
          title: '回国时间',
          dataIndex: 'hgsj',
          width: 130
        },
        {
          title: '留学国家',
          dataIndex: 'lxgj',
          width: 130
        },
        {
          title: '留学单位',
          dataIndex: 'lxdw',
          width: 130
        },
        {
          title: '派出渠道',
          dataIndex: 'qudao',
          width: 150
        }
      ]
    },
    auditColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '考核结果',
          dataIndex: 'khjg',
          width: 130,
          scopedSlots: { customRender: 'khjg' }
        },
        {
          title: '考核年度',
          dataIndex: 'year',
          width: 130,
          scopedSlots: { customRender: 'year' }
        },
        {
          title: '备注',
          dataIndex: 'adContent',
          width: 130,
          scopedSlots: { customRender: 'adContent' }
        }]
    },
    acdemicColumns () {
      return [
        {
          title: '发薪号',
          dataIndex: 'userAccount',
          width: 80,
          scopedSlots: { customRender: 'userAccount' }
        },
        {
          title: '姓名',
          dataIndex: 'userAccountName',
          width: 80
        },
        {
          title: '名称',
          dataIndex: 'academicName',
          width: 300
        },
        {
          title: '任职（获得）时间',
          dataIndex: 'academicDate',
          width: 130
        },
        {
          title: '备注',
          dataIndex: 'academicContent',
          width: 130
        }
      ]
    }


  },
  methods: {
    moment,
    reset () {
      this.eduList = []
      this.partjobList = []
      this.punishOrPrizeList = []
      this.boardList = []
      this.auditList = []
      this.acdemicList = []
    },
    fetch (userAccount) {
      this.$get('dcaBUser/getBaseInfo/' + userAccount).then((r) => {
        let data = r.data
        this.eduList = data.eduList
        this.partjobList = data.partjobList
        this.punishOrPrizeList = data.punishOrPrizeList
        this.boardList = data.boardList
        this.auditList = data.auditList
        this.acdemicList = data.acdemicList
      })
    },
    getUserInfo (userAccount) {
      if (userAccount != '') {
        this.$get('dcaBUser', {
          userAccount: userAccount
        }).then((r) => {
          let data = r.data
          if (data.rows.length > 0
          ) {
            this.dcaBUser = data.rows[0]
            this.mess = ''
          }
        }
        )
      }
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