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
                    label="获奖等级">
                        <a-input placeholder="请输入获奖等级" v-decorator="['prizeGrade', {rules: [{ required: true, message: '获奖等级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="获奖时间">
                        <a-date-picker v-decorator="[ 'prizeDate', {rules: [{ required: true, message: '获奖时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['ranknum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
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
        name: 'DcaBTeacherprizeAdd',
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
                dcaBTeacherprize: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBTeacherprize = {}
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
                    this.$post('dcaBTeacherprize', {
                        ...this.dcaBTeacherprize
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
                let values = this.form.getFieldsValue([                    'prizeName'    ,                     'prizeGrade'    ,                     'prizeDate'    ,                     'ranknum'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBTeacherprize[_key] = values[_key] })
                }
            }
        }
    }
</script>
