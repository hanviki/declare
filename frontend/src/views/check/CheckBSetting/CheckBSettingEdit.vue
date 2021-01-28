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
                        label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['ks', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="指标ID">
                        <a-input placeholder="请输入指标ID" v-decorator="['titleId', {rules: [{ required: true, message: '指标ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="指标编码">
                        <a-input placeholder="请输入指标编码" v-decorator="['titleCode', {rules: [{ required: true, message: '指标编码不能为空' }] }]" />
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
                        label="指标考核类别">
                        <a-input placeholder="请输入指标考核类别" v-decorator="['lb', {rules: [{ required: true, message: '指标考核类别不能为空' }] }]" />
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
        name: 'CheckBSettingEdit',
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
            checkBSetting: {}
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
            setFormValues ({...checkBSetting}) {
                let fields = [  'ks'    , 'titleId'    , 'titleCode'    , 'auditTitle'    , 'auditTitletype'    , 'lb'          ]
                let fieldDates = [             ]
                Object.keys(checkBSetting).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(checkBSetting[key]!=='') {
                            obj[key] = moment(checkBSetting[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = checkBSetting[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.checkBSetting. id                           = checkBSetting. id                                      },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let checkBSetting = this.form.getFieldsValue()
                    checkBSetting. id                          = this.checkBSetting.id
                    this.$put('checkBSetting', {
                        ...checkBSetting
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
