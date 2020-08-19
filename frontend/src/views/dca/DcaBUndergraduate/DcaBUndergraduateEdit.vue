<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item
                        v-bind="formItemLayout"
                        label="课程名称">
                        <a-input placeholder="请输入课程名称" v-decorator="['courseName', {rules: [{ required: true, message: '课程名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="自何年月">
                        <a-date-picker v-decorator="[ 'ugStartDate', {rules: [{ required: true, message: '自何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="至何年月">
                        <a-date-picker v-decorator="[ 'ugEndDate', {rules: [{ required: true, message: '至何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="课程类别">
                        <a-input placeholder="请输入课程类别" v-decorator="['courseType', {rules: [{ required: true, message: '课程类别不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="学生人数">
                        <a-input placeholder="请输入学生人数" v-decorator="['studentNumber', {rules: [{ required: true, message: '学生人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="总学时">
                        <a-input placeholder="请输入总学时" v-decorator="['totalTime', {rules: [{ required: true, message: '总学时不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="个人承担学时">
                        <a-input placeholder="请输入个人承担学时" v-decorator="['personTime', {rules: [{ required: true, message: '个人承担学时不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="教学评分">
                        <a-input placeholder="请输入教学评分" v-decorator="['teachScore', {rules: [{ required: true, message: '教学评分不能为空' }] }]" />
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBUndergraduateEdit',
        props: {
            editVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
            dcaBUndergraduate: {}
        }
        },
        methods: {
            reset () {
                this.loading = false
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            setFormValues ({...dcaBUndergraduate}) {
                let fields = [   'courseName'    , 'ugStartDate'    , 'ugEndDate'    , 'courseType'    , 'studentNumber'    , 'totalTime'    , 'personTime'    , 'teachScore'          ]
                let fieldDates = [     'ugStartDate'       , 'ugEndDate'               ]
                Object.keys(dcaBUndergraduate).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBUndergraduate[key]!=='') {
                            obj[key] = moment(dcaBUndergraduate[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBUndergraduate[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBUndergraduate. id                                 = dcaBUndergraduate. id                                            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBUndergraduate = this.form.getFieldsValue()
                    dcaBUndergraduate. id                                = this.dcaBUndergraduate.id
                    this.$put('dcaBUndergraduate', {
                        ...dcaBUndergraduate
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            }
        }
    }
</script>
