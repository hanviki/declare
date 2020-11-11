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
                    label="出国时间">
                        <a-date-picker v-decorator="[ 'cgsj', {rules: [{ required: true, message: '出国时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="回国时间">
                        <a-date-picker v-decorator="[ 'hgsj', {rules: [{ required: true, message: '回国时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="留学国家">
                        <a-input placeholder="请输入留学国家" v-decorator="['lxgj', {rules: [{ required: true, message: '留学国家不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="留学单位">
                        <a-input placeholder="请输入留学单位" v-decorator="['lxdw', {rules: [{ required: true, message: '留学单位不能为空' }] }]" />
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
        name: 'DcaBExportcountryAdd',
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
                dcaBExportcountry: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBExportcountry = {}
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
                    this.$post('dcaBExportcountry', {
                        ...this.dcaBExportcountry
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
                let values = this.form.getFieldsValue([                    'cgsj'    ,                     'hgsj'    ,                     'lxgj'    ,                     'lxdw'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBExportcountry[_key] = values[_key] })
                }
            }
        }
    }
</script>
