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
                        label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['name', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="微信用户ID">
                        <a-input placeholder="请输入微信用户ID" v-decorator="['vxCode', {}]" />
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
        name: 'VMsgVendorauditEdit',
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
            vMsgVendoraudit: {}
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
            setFormValues ({...vMsgVendoraudit}) {
                let fields = [  'code'     ,  'name'     ,  'vxCode'   ]
                let fieldDates = [         ]
                Object.keys(vMsgVendoraudit).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(vMsgVendoraudit[key]!=='') {
                            obj[key] = moment(vMsgVendoraudit[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = vMsgVendoraudit[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.vMsgVendoraudit.id = vMsgVendoraudit.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let vMsgVendoraudit = this.form.getFieldsValue()
                        vMsgVendoraudit.id = this.vMsgVendoraudit.id
                    this.$put('vMsgVendoraudit', {
                        ...vMsgVendoraudit
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
