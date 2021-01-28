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
                    label="获奖名称">
                        <a-input placeholder="请输入获奖名称" v-decorator="['prizeName', {rules: [{ required: true, message: '获奖名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="奖项级别">
                        <a-input placeholder="请输入奖项级别" v-decorator="['prizeJb', {rules: [{ required: true, message: '奖项级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="获奖等级">
                        <a-input placeholder="请输入获奖等级" v-decorator="['prizeGrade', {rules: [{ required: true, message: '获奖等级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="排名">
                        <a-input placeholder="请输入排名" v-decorator="['ranknum', {rules: [{ required: true, message: '排名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="时间">
                        <a-date-picker v-decorator="[ 'prizeDate', {rules: [{ required: true, message: '时间不能为空' }] }]" />
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
        name: 'DcaBYoungprizeAdd',
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
                dcaBYoungprize: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBYoungprize = {}
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
                    this.$post('dcaBDocYoungprize', {
                        ...this.dcaBYoungprize
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
                let values = this.form.getFieldsValue([                    'prizeName'    ,                     'prizeJb'    ,                     'prizeGrade'    ,                     'ranknum'    ,                     'prizeDate'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBYoungprize[_key] = values[_key] })
                }
            }
        }
    }
</script>
