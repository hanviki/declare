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
                    label="获奖项目名称">
                        <a-input placeholder="请输入获奖项目名称" v-decorator="['spProjectName', {rules: [{ required: true, message: '获奖项目名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="奖项级别">
                        <a-input placeholder="请输入奖项级别" v-decorator="['srProjectGrade', {rules: [{ required: true, message: '奖项级别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="奖项等级">
                        <a-input placeholder="请输入奖项等级" v-decorator="['srProjectLevel', {rules: [{ required: true, message: '奖项等级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="授奖部门">
                        <a-input placeholder="请输入授奖部门" v-decorator="['srPrizeDept', {rules: [{ required: true, message: '授奖部门不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="批准年月">
                        <a-date-picker v-decorator="[ 'srPrizeDate', {rules: [{ required: true, message: '批准年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本人排名">
                        <a-input placeholder="请输入本人排名" v-decorator="['srPrizeRanknum', {rules: [{ required: true, message: '本人排名不能为空' }] }]" />
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
        name: 'DcaBScientificprizeAdd',
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
                dcaBScientificprize: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBScientificprize = {}
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
                    this.$post('dcaBScientificprize', {
                        ...this.dcaBScientificprize
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
                let values = this.form.getFieldsValue([                    'spProjectName'    ,                     'srProjectGrade'    ,                     'srProjectLevel'    ,                     'srPrizeDept'    ,                     'srPrizeDate'    ,                     'srPrizeRanknum'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBScientificprize[_key] = values[_key] })
                }
            }
        }
    }
</script>
