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
                    label="科室">
                        <a-input placeholder="请输入科室" v-decorator="['ks', {rules: [{ required: true, message: '科室不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="专技类别">
                        <a-input placeholder="请输入专技类别" v-decorator="['zjlb', {rules: [{ required: true, message: '专技类别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="性别">
                        <a-input placeholder="请输入性别" v-decorator="['sexName', {rules: [{ required: true, message: '性别不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出生年月">
                        <a-date-picker v-decorator="[ 'birthday', {rules: [{ required: true, message: '出生年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="手机号">
                        <a-input placeholder="请输入手机号" v-decorator="['telephone', {rules: [{ required: true, message: '手机号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="身份证号">
                        <a-input placeholder="请输入身份证号" v-decorator="['idCard', {rules: [{ required: true, message: '身份证号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="专业技术职务">
                        <a-input placeholder="请输入专业技术职务" v-decorator="['zyjsgw', {rules: [{ required: true, message: '专业技术职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="专业技术职务聘任时间">
                        <a-input placeholder="请输入专业技术职务聘任时间" v-decorator="['appointedDate', {rules: [{ required: true, message: '专业技术职务聘任时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室负责人发薪号申请岗位">
                        <a-input placeholder="请输入科室负责人发薪号申请岗位" v-decorator="['ksLeaderPernr', {rules: [{ required: true, message: '科室负责人发薪号申请岗位不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="主管院领导发薪号">
                        <a-input placeholder="请输入主管院领导发薪号" v-decorator="['zgLeaderPernr', {rules: [{ required: true, message: '主管院领导发薪号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="所在院系">
                        <a-input placeholder="请输入所在院系" v-decorator="['deptName', {rules: [{ required: true, message: '所在院系不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现岗位职务">
                        <a-input placeholder="请输入现岗位职务" v-decorator="['positionName', {rules: [{ required: true, message: '现岗位职务不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="来校工作时间">
                        <a-date-picker v-decorator="[ 'schoolDate', {rules: [{ required: true, message: '来校工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现从事专业及专长">
                        <a-input placeholder="请输入现从事专业及专长" v-decorator="['xcszyjzc', {rules: [{ required: true, message: '现从事专业及专长不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="申报年度">
                        <a-input placeholder="请输入申报年度" v-decorator="['dcaYear', {rules: [{ required: true, message: '申报年度不能为空' }] }]" />
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
        name: 'CheckBUserAdd',
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
                checkBUser: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.checkBUser = {}
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
                    this.$post('checkBUser', {
                        ...this.checkBUser
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
                let values = this.form.getFieldsValue([                    'ks'    ,                     'zjlb'    ,                     'sexName'    ,                     'birthday'    ,                     'telephone'    ,                     'idCard'    ,                     'zyjsgw'    ,                     'appointedDate'    ,                     'ksLeaderPernr'    ,                     'zgLeaderPernr'    ,                     'deptName'    ,                     'positionName'    ,                     'schoolDate'    ,                     'xcszyjzc'    ,                     'dcaYear'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.checkBUser[_key] = values[_key] })
                }
            }
        }
    }
</script>
