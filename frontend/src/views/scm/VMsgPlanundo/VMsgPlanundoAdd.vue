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
                    label="微信用户ID">
                        <a-input placeholder="请输入微信用户ID" v-decorator="['vxCode', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="">
                        <a-input placeholder="请输入" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
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
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'VMsgPlanundoAdd',
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
                vMsgPlanundo: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.vMsgPlanundo = {}
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
                    this.$post('vMsgPlanundo', {
                        ...this.vMsgPlanundo
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
                let values = this.form.getFieldsValue([  'vxCode'     ,  'id'     ,  'gysaccount'     ,  'txz01'     ,  'matnr'   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.vMsgPlanundo[_key] = values[_key] })
                }
            }
        }
    }
</script>
