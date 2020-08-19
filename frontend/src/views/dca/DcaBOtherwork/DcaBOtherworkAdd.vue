<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="其他工作及成果">
                        <a-input placeholder="请输入其他工作及成果" v-decorator="['otherWork', {rules: [{ required: true, message: '其他工作及成果不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="拟聘岗位工作思路及预期目标">
                        <a-input placeholder="请输入拟聘岗位工作思路及预期目标" v-decorator="['preGoal', {rules: [{ required: true, message: '拟聘岗位工作思路及预期目标不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="个人总结">
                        <a-input placeholder="请输入个人总结" v-decorator="['personal', {rules: [{ required: true, message: '个人总结不能为空' }] }]" />
                </a-form-item>
        </a-form>
        <div class="drawer-bootom-button">
            <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
                <a-button style="margin-right: .8rem">取消</a-button>
            </a-popconfirm>
            <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
        </div>
    </a-drawer>
</template>
<script>
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBOtherworkAdd',
        props: {
            addVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
                dcaBOtherwork: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBOtherwork = {}
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    this.setFields()
                    this.$post('dcaBOtherwork', {
                        ...this.dcaBOtherwork
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            },
            setFields () {
                let values = this.form.getFieldsValue([                    'otherWork'    ,                     'preGoal'    ,                     'personal'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBOtherwork[_key] = values[_key] })
                }
            }
        }
    }
</script>
