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
                    label="年度">
                        <a-input placeholder="请输入年度" v-decorator="['year', {rules: [{ required: true, message: '年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="门诊病人量">
                        <a-input placeholder="请输入门诊病人量" v-decorator="['mzbrl', {rules: [{ required: true, message: '门诊病人量不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="管理住院病人量">
                        <a-input placeholder="请输入管理住院病人量" v-decorator="['glzybrl', {rules: [{ required: true, message: '管理住院病人量不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['remarknote', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手术病人量">
                        <a-input placeholder="请输入手术病人量" v-decorator="['ssbrl', {rules: [{ required: true, message: '手术病人量不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核人">
                        <a-input placeholder="请输入审核人" v-decorator="['auditMan', {rules: [{ required: true, message: '审核人不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核人姓名">
                        <a-input placeholder="请输入审核人姓名" v-decorator="['auditManName', {rules: [{ required: true, message: '审核人姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'auditDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否用于本次评审">
                        <a-input placeholder="请输入是否用于本次评审" v-decorator="['IsUse', {rules: [{ required: true, message: '是否用于本次评审不能为空' }] }]" />
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
        name: 'DcaBWorknumAdd',
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
                dcaBWorknum: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBWorknum = {}
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
                    this.$post('dcaBWorknum', {
                        ...this.dcaBWorknum
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
                let values = this.form.getFieldsValue([                    'year'    ,                     'mzbrl'    ,                     'glzybrl'    ,                     'remarknote'    ,                     'ssbrl'    ,                     'auditMan'    ,                     'auditManName'    ,                     'auditDate'    ,                     'auditSuggestion'    ,                     'IsUse'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBWorknum[_key] = values[_key] })
                }
            }
        }
    }
</script>
