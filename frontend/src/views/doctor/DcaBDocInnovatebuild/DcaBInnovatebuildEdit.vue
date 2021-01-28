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
                        label="项目名称">
                        <a-input placeholder="请输入项目名称" v-decorator="['projectName', {rules: [{ required: true, message: '项目名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="项目性质">
                        <a-input placeholder="请输入项目性质" v-decorator="['projectType', {rules: [{ required: true, message: '项目性质不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="项目来源">
                        <a-input placeholder="请输入项目来源" v-decorator="['projectSource', {rules: [{ required: true, message: '项目来源不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="合同经费">
                        <a-input placeholder="请输入合同经费" v-decorator="['contractFund', {rules: [{ required: true, message: '合同经费不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="实到经费">
                        <a-input placeholder="请输入实到经费" v-decorator="['realFund', {rules: [{ required: true, message: '实到经费不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="批准年月">
                        <a-date-picker v-decorator="[ 'auditDate', {rules: [{ required: true, message: '批准年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="起始日期">
                        <a-date-picker v-decorator="[ 'startDate', {rules: [{ required: true, message: '起始日期不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="终止日期">
                        <a-date-picker v-decorator="[ 'endDate', {rules: [{ required: true, message: '终止日期不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['rankNum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
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
        name: 'DcaBInnovatebuildEdit',
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
            dcaBInnovatebuild: {}
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
            setFormValues ({...dcaBInnovatebuild}) {
                let fields = [   'projectName'    , 'projectType'    , 'projectSource'    , 'contractFund'    , 'realFund'    , 'auditDate'    , 'startDate'    , 'endDate'    , 'rankNum'          ]
                let fieldDates = [         'auditDate'       , 'startDate'       , 'endDate'           ]
                Object.keys(dcaBInnovatebuild).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBInnovatebuild[key]!=='') {
                            obj[key] = moment(dcaBInnovatebuild[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBInnovatebuild[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBInnovatebuild. id                                   = dcaBInnovatebuild. id                                              },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBInnovatebuild = this.form.getFieldsValue()
                    dcaBInnovatebuild. id                                  = this.dcaBInnovatebuild.id
                    this.$put('dcaBDocInnovatebuild', {
                        ...dcaBInnovatebuild
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
