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
                        label="发票号码">
                        <a-input placeholder="请输入发票号码" v-decorator="['fpHm', {rules: [{ required: true, message: '发票号码不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票编码">
                        <a-input placeholder="请输入发票编码" v-decorator="['fpBm', {rules: [{ required: true, message: '发票编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票金额">
                        <a-input placeholder="请输入发票金额" v-decorator="['fpAmount', {rules: [{ required: true, message: '发票金额不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核原因">
                        <a-input placeholder="请输入审核原因" v-decorator="['AuditCause', {rules: [{ required: true, message: '审核原因不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="院区ID">
                        <a-input placeholder="请输入院区ID" v-decorator="['werks', {rules: [{ required: true, message: '院区ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['werkt', {rules: [{ required: true, message: '院区不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="库房ID">
                        <a-input placeholder="请输入库房ID" v-decorator="['lgort', {rules: [{ required: true, message: '库房ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="库房">
                        <a-input placeholder="请输入库房" v-decorator="['lgortName', {rules: [{ required: true, message: '库房不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商发票">
                        <a-input placeholder="请输入供应商发票" v-decorator="['materId', {rules: [{ required: true, message: '供应商发票不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="药厂发票">
                        <a-input placeholder="请输入药厂发票" v-decorator="['fileId', {rules: [{ required: true, message: '药厂发票不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商名称">
                        <a-input placeholder="请输入供应商名称" v-decorator="['GysName', {rules: [{ required: true, message: '供应商名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {rules: [{ required: true, message: '供应商账号不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票日期">
                        <a-date-picker v-decorator="[ 'fprq', {rules: [{ required: true, message: '发票日期不能为空' }] }]" />
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
        name: 'VScmbgysfpuserEdit',
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
            vScmbgysfpuser: {}
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
            setFormValues ({...vScmbgysfpuser}) {
                let fields = [     'fpHm'       ,  'fpBm'       ,  'fpAmount'       ,  'AuditCause'       ,  'werks'       ,  'werkt'       ,  'lgort'       ,  'lgortName'           ,  'materId'       ,  'fileId'       ,  'GysName'       ,  'gysaccount'                 ,  'fprq'    ]
                let fieldDates = [                                                                                        'fprq'      ]
                Object.keys(vScmbgysfpuser).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(vScmbgysfpuser[key]!=='') {
                            obj[key] = moment(vScmbgysfpuser[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = vScmbgysfpuser[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.vScmbgysfpuser.id = vScmbgysfpuser.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let vScmbgysfpuser = this.form.getFieldsValue()
                        vScmbgysfpuser.id = this.vScmbgysfpuser.id
                    this.$put('vScmbgysfpuser', {
                        ...vScmbgysfpuser
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
