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
                    label="论文名">
                        <a-input placeholder="请输入论文名" v-decorator="['paperName', {rules: [{ required: true, message: '论文名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="期刊名">
                        <a-input placeholder="请输入期刊名" v-decorator="['journalName', {rules: [{ required: true, message: '期刊名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="期刊号">
                        <a-input placeholder="请输入期刊号" v-decorator="['journalCode', {rules: [{ required: true, message: '期刊号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="发表年月">
                        <a-date-picker v-decorator="[ 'paperPublishdate', {rules: [{ required: true, message: '发表年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="收录情况">
                        <a-input placeholder="请输入收录情况" v-decorator="['paperShoulu', {rules: [{ required: true, message: '收录情况不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="影响因子">
                        <a-input placeholder="请输入影响因子" v-decorator="['paperCause', {rules: [{ required: true, message: '影响因子不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否一流期刊">
                        <a-input placeholder="请输入是否一流期刊" v-decorator="['isBest', {rules: [{ required: true, message: '是否一流期刊不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="他引次数">
                        <a-input placeholder="请输入他引次数" v-decorator="['otherTimes', {rules: [{ required: true, message: '他引次数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="第一或通讯作者">
                        <a-input placeholder="请输入第一或通讯作者" v-decorator="['authorRank', {rules: [{ required: true, message: '第一或通讯作者不能为空' }] }]" />
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
        name: 'DcaBPaperspublishAdd',
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
                dcaBPaperspublish: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBPaperspublish = {}
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
                    this.$post('dcaBPaperspublish', {
                        ...this.dcaBPaperspublish
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
                let values = this.form.getFieldsValue([                    'paperName'    ,                     'journalName'    ,                     'journalCode'    ,                     'paperPublishdate'    ,                     'paperShoulu'    ,                     'paperCause'    ,                     'isBest'    ,                     'otherTimes'    ,                     'authorRank'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBPaperspublish[_key] = values[_key] })
                }
            }
        }
    }
</script>
