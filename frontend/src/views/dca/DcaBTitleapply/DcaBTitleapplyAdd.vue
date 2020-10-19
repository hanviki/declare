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
                    label="编码=主键">
                        <a-input placeholder="请输入编码=主键" v-decorator="['code', {rules: [{ required: true, message: '编码=主键不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['name', {rules: [{ required: true, message: '名字不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="职称ID">
                        <a-input placeholder="请输入职称ID" v-decorator="['titleId', {rules: [{ required: true, message: '职称ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="申请职称">
                        <a-input placeholder="请输入申请职称" v-decorator="['titleName', {rules: [{ required: true, message: '申请职称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="年度">
                        <a-input placeholder="请输入年度" v-decorator="['year', {rules: [{ required: true, message: '年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否高级">
                        <a-input placeholder="请输入是否高级" v-decorator="['IsGj', {rules: [{ required: true, message: '是否高级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="排序">
                        <a-input placeholder="请输入排序" v-decorator="['orderNum', {rules: [{ required: true, message: '排序不能为空' }] }]" />
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
        name: 'DcaBTitleapplyAdd',
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
                dcaBTitleapply: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBTitleapply = {}
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
                    this.$post('dcaBTitleapply', {
                        ...this.dcaBTitleapply
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
                let values = this.form.getFieldsValue([                    'code'    ,                     'name'    ,                     'titleId'    ,                     'titleName'    ,                     'year'    ,                     'IsGj'    ,                     'orderNum'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBTitleapply[_key] = values[_key] })
                }
            }
        }
    }
</script>
