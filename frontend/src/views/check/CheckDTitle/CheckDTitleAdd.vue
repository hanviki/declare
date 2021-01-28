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
                    label="指标ID">
                        <a-input placeholder="请输入指标ID" v-decorator="['filedName', {rules: [{ required: true, message: '指标ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="指标名称">
                        <a-input placeholder="请输入指标名称" v-decorator="['filedTitle', {rules: [{ required: true, message: '指标名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {rules: [{ required: true, message: '编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="类别显示编码">
                        <a-input placeholder="请输入类别显示编码" v-decorator="['showCode', {rules: [{ required: true, message: '类别显示编码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="显示类型1数字2文本">
                        <a-input placeholder="请输入显示类型1数字2文本" v-decorator="['showType', {rules: [{ required: true, message: '显示类型1数字2文本不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="值范围">
                        <a-input placeholder="请输入值范围" v-decorator="['range', {rules: [{ required: true, message: '值范围不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="正负">
                        <a-input placeholder="请输入正负" v-decorator="['isOria', {rules: [{ required: true, message: '正负不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="1是账号2科主任3主管领导">
                        <a-input placeholder="请输入1是账号2科主任3主管领导" v-decorator="['checkPerson', {rules: [{ required: true, message: '1是账号2科主任3主管领导不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="父ID">
                        <a-input placeholder="请输入父ID" v-decorator="['parentId', {rules: [{ required: true, message: '父ID不能为空' }] }]" />
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
        name: 'CheckDTitleAdd',
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
                checkDTitle: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.checkDTitle = {}
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
                    this.$post('checkDTitle', {
                        ...this.checkDTitle
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
                let values = this.form.getFieldsValue([                    'filedName'    ,                     'filedTitle'    ,                     'code'    ,                     'showCode'    ,                     'showType'    ,                     'range'    ,                     'isOria'    ,                     'checkPerson'    ,                     'parentId'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.checkDTitle[_key] = values[_key] })
                }
            }
        }
    }
</script>
