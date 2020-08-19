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
                    label="论著名称">
                        <a-input placeholder="请输入论著名称" v-decorator="['essayName', {rules: [{ required: true, message: '论著名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="期刊名称">
                        <a-input placeholder="请输入期刊名称" v-decorator="['eassyJournalname', {rules: [{ required: true, message: '期刊名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出版社">
                        <a-input placeholder="请输入出版社" v-decorator="['eassyPublishname', {rules: [{ required: true, message: '出版社不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="起始页码">
                        <a-input placeholder="请输入起始页码" v-decorator="['eassyStartpage', {rules: [{ required: true, message: '起始页码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="截至页码">
                        <a-input placeholder="请输入截至页码" v-decorator="['eassyEndpage', {rules: [{ required: true, message: '截至页码不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="刊号">
                        <a-input placeholder="请输入刊号" v-decorator="['eassyJournalcode', {rules: [{ required: true, message: '刊号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="期刊级别">
                        <a-input placeholder="请输入期刊级别" v-decorator="['eassyJournalgrade', {rules: [{ required: true, message: '期刊级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="发表年月">
                        <a-date-picker v-decorator="[ 'eassyPublishdate', {rules: [{ required: true, message: '发表年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="第几作者">
                        <a-input placeholder="请输入第几作者" v-decorator="['eassyRankname', {rules: [{ required: true, message: '第几作者不能为空' }] }]" />
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
        name: 'DcaBEssaypublishAdd',
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
                dcaBEssaypublish: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBEssaypublish = {}
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
                    this.$post('dcaBEssaypublish', {
                        ...this.dcaBEssaypublish
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
                let values = this.form.getFieldsValue([                    'essayName'    ,                     'eassyJournalname'    ,                     'eassyPublishname'    ,                     'eassyStartpage'    ,                     'eassyEndpage'    ,                     'eassyJournalcode'    ,                     'eassyJournalgrade'    ,                     'eassyPublishdate'    ,                     'eassyRankname'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBEssaypublish[_key] = values[_key] })
                }
            }
        }
    }
</script>
