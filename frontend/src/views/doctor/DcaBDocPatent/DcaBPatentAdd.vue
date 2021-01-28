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
                    label="专利号">
                        <a-input placeholder="请输入专利号" v-decorator="['patentCode', {rules: [{ required: true, message: '专利号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="专利名称">
                        <a-input placeholder="请输入专利名称" v-decorator="['patentName', {rules: [{ required: true, message: '专利名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="专利类别">
                        <a-input placeholder="请输入专利类别" v-decorator="['patentType', {rules: [{ required: true, message: '专利类别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="批准年月">
                        <a-date-picker v-decorator="[ 'patentDate', {rules: [{ required: true, message: '批准年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['patentRanknum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否授权">
                        <a-input placeholder="请输入是否授权" v-decorator="['isAuthority', {rules: [{ required: true, message: '是否授权不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否转让">
                        <a-input placeholder="请输入是否转让" v-decorator="['isZhuanrang', {rules: [{ required: true, message: '是否转让不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="转让效益">
                        <a-input placeholder="请输入转让效益" v-decorator="['patentGood', {rules: [{ required: true, message: '转让效益不能为空' }] }]" />
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
        name: 'DcaBPatentAdd',
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
                dcaBPatent: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBPatent = {}
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
                    this.$post('dcaBDocPatent', {
                        ...this.dcaBPatent
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
                let values = this.form.getFieldsValue([                    'patentCode'    ,                     'patentName'    ,                     'patentType'    ,                     'patentDate'    ,                     'patentRanknum'    ,                     'isAuthority'    ,                     'isZhuanrang'    ,                     'patentGood'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBPatent[_key] = values[_key] })
                }
            }
        }
    }
</script>
