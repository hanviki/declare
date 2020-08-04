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
                    label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['name', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {rules: [{ required: true, message: '供应商账号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="药品编码">
                        <a-input placeholder="请输入药品编码" v-decorator="['materId', {rules: [{ required: true, message: '药品编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="">
                        <a-input placeholder="请输入" v-decorator="['matnr', {rules: [{ required: true, message: '不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="批次号">
                        <a-input placeholder="请输入批次号" v-decorator="['charge', {rules: [{ required: true, message: '批次号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="附件ID">
                        <a-input placeholder="请输入附件ID" v-decorator="['fileId', {rules: [{ required: true, message: '附件ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料类型">
                        <a-input placeholder="请输入物料类型" v-decorator="['mtart', {rules: [{ required: true, message: '物料类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="单位描述">
                        <a-input placeholder="请输入单位描述" v-decorator="['mseht', {rules: [{ required: true, message: '单位描述不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料描述">
                        <a-input placeholder="请输入物料描述" v-decorator="['txz01', {rules: [{ required: true, message: '物料描述不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="生产厂家">
                        <a-input placeholder="请输入生产厂家" v-decorator="['produceArea', {rules: [{ required: true, message: '生产厂家不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="规格">
                        <a-input placeholder="请输入规格" v-decorator="['spec', {rules: [{ required: true, message: '规格不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="拼音码">
                        <a-input placeholder="请输入拼音码" v-decorator="['spellCode', {rules: [{ required: true, message: '拼音码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核原因">
                        <a-input placeholder="请输入审核原因" v-decorator="['AuditCause', {rules: [{ required: true, message: '审核原因不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="院区ID">
                        <a-input placeholder="请输入院区ID" v-decorator="['werks', {rules: [{ required: true, message: '院区ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['werkt', {rules: [{ required: true, message: '院区不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="库房ID">
                        <a-input placeholder="请输入库房ID" v-decorator="['lgort', {rules: [{ required: true, message: '库房ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="库房">
                        <a-input placeholder="请输入库房" v-decorator="['lgortName', {rules: [{ required: true, message: '库房不能为空' }] }]" />
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
        name: 'VScmBGyspicUserAdd',
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
                vScmBGyspicUser: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.vScmBGyspicUser = {}
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
                    this.$post('vScmBGyspicUser', {
                        ...this.vScmBGyspicUser
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
                let values = this.form.getFieldsValue([                    'code'    ,                     'name'    ,                     'gysaccount'    ,                     'materId'    ,                     'matnr'    ,                     'charge'    ,                     'fileId'    ,                     'mtart'    ,                     'mseht'    ,                     'txz01'    ,                     'produceArea'    ,                     'spec'    ,                     'spellCode'    ,                     'AuditCause'    ,                     'werks'    ,                     'werkt'    ,                     'lgort'    ,                     'lgortName'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.vScmBGyspicUser[_key] = values[_key] })
                }
            }
        }
    }
</script>
