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
                    label="博士学校">
                        <a-input placeholder="请输入博士学校" v-decorator="['doctorSchool', {rules: [{ required: true, message: '博士学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="博士毕业时间">
                        <a-date-picker v-decorator="[ 'doctorDate', {rules: [{ required: true, message: '博士毕业时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="博士专业">
                        <a-input placeholder="请输入博士专业" v-decorator="['doctorZy', {rules: [{ required: true, message: '博士专业不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="硕士学校">
                        <a-input placeholder="请输入硕士学校" v-decorator="['graduateSchool', {rules: [{ required: true, message: '硕士学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="硕士毕业时间">
                        <a-date-picker v-decorator="[ 'graduateDate', {rules: [{ required: true, message: '硕士毕业时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="硕士专业">
                        <a-input placeholder="请输入硕士专业" v-decorator="['graduateZy', {rules: [{ required: true, message: '硕士专业不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本科学校">
                        <a-input placeholder="请输入本科学校" v-decorator="['bkSchool', {rules: [{ required: true, message: '本科学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本科毕业时间">
                        <a-date-picker v-decorator="[ 'bkDate', {rules: [{ required: true, message: '本科毕业时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="本科专业">
                        <a-input placeholder="请输入本科专业" v-decorator="['bkZy', {rules: [{ required: true, message: '本科专业不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="大专学校">
                        <a-input placeholder="请输入大专学校" v-decorator="['dzSchool', {rules: [{ required: true, message: '大专学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="大专毕业时间">
                        <a-date-picker v-decorator="[ 'dzDate', {rules: [{ required: true, message: '大专毕业时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="大专专业">
                        <a-input placeholder="请输入大专专业" v-decorator="['dzZy', {rules: [{ required: true, message: '大专专业不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="中专学校">
                        <a-input placeholder="请输入中专学校" v-decorator="['zzSchool', {rules: [{ required: true, message: '中专学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="中专毕业时间">
                        <a-date-picker v-decorator="[ 'zzDate', {rules: [{ required: true, message: '中专毕业时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="中专专业">
                        <a-input placeholder="请输入中专专业" v-decorator="['zzZy', {rules: [{ required: true, message: '中专专业不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现任专业技术职务">
                        <a-input placeholder="请输入现任专业技术职务" v-decorator="['xrzyzw', {rules: [{ required: true, message: '现任专业技术职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="聘任时间">
                        <a-date-picker v-decorator="[ 'prDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="职员类别">
                        <a-input placeholder="请输入职员类别" v-decorator="['zylb', {rules: [{ required: true, message: '职员类别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现任职员职级">
                        <a-input placeholder="请输入现任职员职级" v-decorator="['zrzyjb', {rules: [{ required: true, message: '现任职员职级不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="聘任时间">
                        <a-date-picker v-decorator="[ 'zrprDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="签订劳动合同时间">
                        <a-date-picker v-decorator="[ 'comployDate', {rules: [{ required: true, message: '签订劳动合同时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="确认时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'confirmDate', {rules: [{ required: true, message: '确认时间不能为空' }] }]" />
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
        name: 'DcaBWorkerAdd',
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
                dcaBWorker: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBWorker = {}
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
                    this.$post('dcaBWorker', {
                        ...this.dcaBWorker
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
                let values = this.form.getFieldsValue([                    'doctorSchool'    ,                     'doctorDate'    ,                     'doctorZy'    ,                     'graduateSchool'    ,                     'graduateDate'    ,                     'graduateZy'    ,                     'bkSchool'    ,                     'bkDate'    ,                     'bkZy'    ,                     'dzSchool'    ,                     'dzDate'    ,                     'dzZy'    ,                     'zzSchool'    ,                     'zzDate'    ,                     'zzZy'    ,                     'xrzyzw'    ,                     'prDate'    ,                     'zylb'    ,                     'zrzyjb'    ,                     'zrprDate'    ,                     'comployDate'    ,                     'confirmDate'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBWorker[_key] = values[_key] })
                }
            }
        }
    }
</script>
