<template>
    <a-card
            :bordered="bordered"
            class="card-area"
            title="拟聘岗位"
    >
        <a-textarea
                :value="id"
                @blur="e => inputChange(e.target.value)"
                :rows="12"
        ></a-textarea>
        <a-textarea
                :value="userAccount"
                @blur="e => inputChange(e.target.value)"
                :rows="12"
        ></a-textarea>
        <a-textarea
                :value="adContent"
                @blur="e => inputChange(e.target.value)"
                :rows="12"
        ></a-textarea>
        <a-textarea
                :value="state"
                @blur="e => inputChange(e.target.value)"
                :rows="12"
        ></a-textarea>
        <div>
            <a-button
                    @click="handleSave"
                    type="primary"
                    :loading="loading"
                    v-show="CustomVisiable"
            >保存草稿</a-button>
            <a-button
                    @click="handleSubmit"
                    type="primary"
                    :loading="loading"
                    v-show="CustomVisiable"
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
        name: 'DcaBAuditfive',
        data () {
            return {
                dataSource: [],
        id: '',
        userAccount: '',
        adContent: '',
        state: '',
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
                        this.id = value
                        this.userAccount = value
                        this.adContent = value
                        this.state = value
            },
            handleSave () {
                if (this.id.trim() === '') {
                    this.$message.warning('请填写数据！！！')
                }
                if (this.userAccount.trim() === '') {
                    this.$message.warning('请填写数据！！！')
                }
                if (this.adContent.trim() === '') {
                    this.$message.warning('请填写数据！！！')
                }
                if (this.state.trim() === '') {
                    this.$message.warning('请填写数据！！！')
                }
                else {
                    this.loading = true
                    this.$post('dcaBAuditfive', {
        id: this.id.trim(),
        userAccount: this.userAccount.trim(),
        adContent: this.adContent.trim(),
        state: this.state.trim(),
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
                            if (that.id.trim() === '') {
                                that.$message.warning('请填写数据！！！')
                            }
                            if (that.userAccount.trim() === '') {
                                that.$message.warning('请填写数据！！！')
                            }
                            if (that.adContent.trim() === '') {
                                that.$message.warning('请填写数据！！！')
                            }
                            if (that.state.trim() === '') {
                                that.$message.warning('请填写数据！！！')
                            }
                        else {
                            that.loading = true

                        that.$post('dcaBAuditfive', {
                                id: that.id.trim(),
                                userAccount: that.userAccount.trim(),
                                adContent: that.adContent.trim(),
                                state: that.state.trim(),
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
                this.$get('dcaBAuditfive/custom', {
                }).then((r) => {
                    let data = r.data
                    this.dataSource = data.rows
                    if (data.rows.length > 0
            ) {
                    if (data.rows[0].state === 0) {
                        this.CustomVisiable = true
                    }
                            this.id = data.rows[0].id
                            this.userAccount = data.rows[0].userAccount
                            this.adContent = data.rows[0].adContent
                            this.state = data.rows[0].state
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
