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
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
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
                    label="拟聘岗位职务">
                        <a-input placeholder="请输入拟聘岗位职务" v-decorator="['npPositionName', {rules: [{ required: true, message: '拟聘岗位职务不能为空' }] }]" />
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
                    label="来校工作时间">
                        <a-date-picker v-decorator="[ 'schoolDate', {rules: [{ required: true, message: '来校工作时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现专业技术岗位">
                        <a-input placeholder="请输入现专业技术岗位" v-decorator="['zyjsgw', {rules: [{ required: true, message: '现专业技术岗位不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="现从事专业及专长">
                        <a-input placeholder="请输入现从事专业及专长" v-decorator="['xcszyjzc', {rules: [{ required: true, message: '现从事专业及专长不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="聘任时间">
                        <a-date-picker v-decorator="[ 'appointedDate', {rules: [{ required: true, message: '聘任时间不能为空' }] }]" />
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
                <a-form-item v-bind="formItemLayout"
                    label="审核人">
                        <a-input placeholder="请输入审核人" v-decorator="['auditMan', {rules: [{ required: true, message: '审核人不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核人姓名">
                        <a-input placeholder="请输入审核人姓名" v-decorator="['auditManName', {rules: [{ required: true, message: '审核人姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'auditDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否用于本次评审">
                        <a-input placeholder="请输入是否用于本次评审" v-decorator="['IsUse', {rules: [{ required: true, message: '是否用于本次评审不能为空' }] }]" />
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
        name: 'DcaBUserAdd',
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
                dcaBUser: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBUser = {}
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
                    this.$post('dcaBUser', {
                        ...this.dcaBUser
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
                let values = this.form.getFieldsValue([                    'userAccountName'    ,                     'userAccount'    ,                     'deptName'    ,                     'positionName'    ,                     'npPositionName'    ,                     'sexName'    ,                     'birthday'    ,                     'schoolDate'    ,                     'zyjsgw'    ,                     'xcszyjzc'    ,                     'appointedDate'    ,                     'patentRanknum'    ,                     'isAuthority'    ,                     'isZhuanrang'    ,                     'patentGood'    ,                     'auditMan'    ,                     'auditManName'    ,                     'auditDate'    ,                     'auditSuggestion'    ,                     'IsUse'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBUser[_key] = values[_key] })
                }
            }
        }
    }
</script>
