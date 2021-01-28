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
                        label="当前年度">
                        <a-input placeholder="请输入当前年度" v-decorator="['dcaYear', {rules: [{ required: true, message: '当前年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核内容">
                        <a-input placeholder="请输入审核内容" v-decorator="['auditTitle', {rules: [{ required: true, message: '审核内容不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="显示标题id">
                        <a-input placeholder="请输入显示标题id" v-decorator="['auditTitletype', {rules: [{ required: true, message: '显示标题id不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="显示结果">
                        <a-input placeholder="请输入显示结果" v-decorator="['auditResult', {rules: [{ required: true, message: '显示结果不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['auditNote', {rules: [{ required: true, message: '备注不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['ks', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="正负">
                        <a-input placeholder="请输入正负" v-decorator="['isOria', {rules: [{ required: true, message: '正负不能为空' }] }]" />
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
        name: 'CheckBAuditresultEdit',
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
            checkBAuditresult: {}
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
            setFormValues ({...checkBAuditresult}) {
                let fields = [    'dcaYear'    , 'auditTitle'    , 'auditTitletype'    , 'auditResult'    , 'auditNote'          , 'ks'    , 'isOria'    ]
                let fieldDates = [                ]
                Object.keys(checkBAuditresult).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(checkBAuditresult[key]!=='') {
                            obj[key] = moment(checkBAuditresult[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = checkBAuditresult[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.checkBAuditresult. id                                 = checkBAuditresult. id                                            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let checkBAuditresult = this.form.getFieldsValue()
                    checkBAuditresult. id                                = this.checkBAuditresult.id
                    this.$put('checkBAuditresult', {
                        ...checkBAuditresult
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
