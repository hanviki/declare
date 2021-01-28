<template>
  <a-card
    :bordered="bordered"
    class="card-area"
    title="个人思想政治及师德师风表现情况"
  >
   <p style="color:red">（限3000字）</p>
    <a-textarea
      :value="psContent"
      @blur="e => inputChange(e.target.value)"
      :rows="12"
    ></a-textarea>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-card>
</template>

<script>

const formItemLayout = {
  labelCol: { span: 8 },
  wrapperCol: { span: 15, offset: 1 }
}
export default {
  name: 'DcaBPolitalshow',
  data () {
    return {
      dataSource: [],
      psContent: '',
      loading: false,
      bordered: true,
      CustomVisiable: false
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    inputChange (value) {
      this.psContent = value
    },
    handleSave () {
      if (this.psContent.trim() === '') {
        this.$message.warning('请填写数据！！！')
      }
      else {
        this.loading = true
        this.$post('dcaBDocPolitalshow', {
          psContent: this.psContent.trim(),
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleSubmit () {
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将不能修改',
        centered: true,
        onOk () {
          if (that.psContent.trim() === '') {
            that.$message.warning('请填写数据！！！')
          }
          else {
            that.loading = true

            that.$post('dcaBDocPolitalshow', {
              psContent: that.psContent.trim(),
              state: 1
            }).then(() => {
              // this.reset()
              that.$message.success('保存成功')
              that.CustomVisiable = false
              that.loading = false
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {

        }
      })


    },
    fetch () {
      this.$get('dcaBDocPolitalshow/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows
        if (data.rows.length > 0
        ) {
          if (data.rows[0].state === 0) {
            this.CustomVisiable = true
          }
          this.psContent = data.rows[0].psContent
        }
        else {
          this.CustomVisiable = true
        }
      }
      )
    }
  }
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
