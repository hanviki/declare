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
                        label="微信用户ID">
                        <a-input placeholder="请输入微信用户ID" v-decorator="['vxCode', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料ID">
                        <a-input placeholder="请输入物料ID" v-decorator="['matnr', {}]" />
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
        name: 'VMsgPlanundoEdit',
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
            vMsgPlanundo: {}
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
            setFormValues ({...vMsgPlanundo}) {
                let fields = [  'vxCode'     ,  'id'     ,  'gysaccount'     ,  'txz01'     ,  'matnr'   ]
                let fieldDates = [               ]
                Object.keys(vMsgPlanundo).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(vMsgPlanundo[key]!=='') {
                            obj[key] = moment(vMsgPlanundo[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = vMsgPlanundo[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.vMsgPlanundo.id = vMsgPlanundo.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let vMsgPlanundo = this.form.getFieldsValue()
                        vMsgPlanundo.id = this.vMsgPlanundo.id
                    this.$put('vMsgPlanundo', {
                        ...vMsgPlanundo
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
