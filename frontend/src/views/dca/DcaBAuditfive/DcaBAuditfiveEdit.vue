<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item
                        v-bind="formItemLayout"
                        label="考核结果">
                        <a-input placeholder="请输入考核结果" v-decorator="['khjg', {rules: [{ required: true, message: '考核结果不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="起止年度">
                        <a-input placeholder="请输入起止年度" v-decorator="['year', {rules: [{ required: true, message: '起止年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="排序">
                        <a-input placeholder="请输入排序" v-decorator="['displayIndex', {rules: [{ required: true, message: '排序不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['adContent', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核人">
                        <a-input placeholder="请输入审核人" v-decorator="['auditMan', {rules: [{ required: true, message: '审核人不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核人姓名">
                        <a-input placeholder="请输入审核人姓名" v-decorator="['auditManName', {rules: [{ required: true, message: '审核人姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'auditDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBAuditfiveEdit',
        props: {
            editVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
            dcaBAuditfive: {}
        }
        },
        methods: {
            reset () {
                this.loading = false
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            setFormValues ({...dcaBAuditfive}) {
                let fields = [   'khjg'    , 'year'    , 'displayIndex'       , 'adContent'          , 'auditMan'    , 'auditManName'    , 'auditDate'    , 'auditSuggestion'    , 'IsUse'    ]
                let fieldDates = [                   'auditDate'      ]
                Object.keys(dcaBAuditfive).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBAuditfive[key]!=='') {
                            obj[key] = moment(dcaBAuditfive[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBAuditfive[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBAuditfive. id                                         = dcaBAuditfive. id                                                    },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBAuditfive = this.form.getFieldsValue()
                    dcaBAuditfive. id                                        = this.dcaBAuditfive.id
                    this.$put('dcaBAuditfive', {
                        ...dcaBAuditfive
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            }
        }
    }
</script>
