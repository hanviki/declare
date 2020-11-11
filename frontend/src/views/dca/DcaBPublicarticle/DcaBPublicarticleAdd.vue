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
                    label="著作类型">
                        <a-input placeholder="请输入著作类型" v-decorator="['zzlx', {rules: [{ required: true, message: '著作类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="著作名称">
                        <a-input placeholder="请输入著作名称" v-decorator="['zzmc', {rules: [{ required: true, message: '著作名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="著者身份">
                        <a-input placeholder="请输入著者身份" v-decorator="['zzsf', {rules: [{ required: true, message: '著者身份不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出版时间">
                        <a-date-picker v-decorator="[ 'cbDate', {rules: [{ required: true, message: '出版时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="第几版">
                        <a-input placeholder="请输入第几版" v-decorator="['ranknum', {rules: [{ required: true, message: '第几版不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label=" 第几次印刷">
                        <a-input placeholder="请输入 第几次印刷" v-decorator="['printnum', {rules: [{ required: true, message: ' 第几次印刷不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label=" 书号（ISNB号）">
                        <a-input placeholder="请输入 书号（ISNB号）" v-decorator="['bookNo', {rules: [{ required: true, message: ' 书号（ISNB号）不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出版社名称">
                        <a-input placeholder="请输入出版社名称" v-decorator="['cbsmc', {rules: [{ required: true, message: '出版社名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="作为第一编写人编写章节名称">
                        <a-input placeholder="请输入作为第一编写人编写章节名称" v-decorator="['bxzjmc', {rules: [{ required: true, message: '作为第一编写人编写章节名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="作为第一编写人编写章节起止页">
                        <a-input placeholder="请输入作为第一编写人编写章节起止页" v-decorator="['bxwzqzy', {rules: [{ required: true, message: '作为第一编写人编写章节起止页不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="作为第一编写人编写字数合计">
                        <a-input placeholder="请输入作为第一编写人编写字数合计" v-decorator="['bxwzzshj', {rules: [{ required: true, message: '作为第一编写人编写字数合计不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="作为第一编写人编写字数合计（单位：万字）">
                        <a-input placeholder="请输入作为第一编写人编写字数合计（单位：万字）" v-decorator="['cdzs', {rules: [{ required: true, message: '作为第一编写人编写字数合计（单位：万字）不能为空' }] }]" />
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
        name: 'DcaBPublicarticleAdd',
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
                dcaBPublicarticle: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBPublicarticle = {}
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
                    this.$post('dcaBPublicarticle', {
                        ...this.dcaBPublicarticle
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
                let values = this.form.getFieldsValue([                    'zzlx'    ,                     'zzmc'    ,                     'zzsf'    ,                     'cbDate'    ,                     'ranknum'    ,                     'printnum'    ,                     'bookNo'    ,                     'cbsmc'    ,                     'bxzjmc'    ,                     'bxwzqzy'    ,                     'bxwzzshj'    ,                     'cdzs'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBPublicarticle[_key] = values[_key] })
                }
            }
        }
    }
</script>
