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
                        label="自何年月">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'emStartTime', {rules: [{ required: true, message: '自何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="至何年月">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'emEndTime', {rules: [{ required: true, message: '至何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="讲授课程名称">
                        <a-input placeholder="请输入讲授课程名称" v-decorator="['emCoursename', {rules: [{ required: true, message: '讲授课程名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="其他教学任务">
                        <a-input placeholder="请输入其他教学任务" v-decorator="['emOtherwork', {rules: [{ required: true, message: '其他教学任务不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="学生人数">
                        <a-input placeholder="请输入学生人数" v-decorator="['emStudentcount', {rules: [{ required: true, message: '学生人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="周学时数">
                        <a-input placeholder="请输入周学时数" v-decorator="['emWeektime', {rules: [{ required: true, message: '周学时数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="总学时数">
                        <a-input placeholder="请输入总学时数" v-decorator="['emTotaltime', {rules: [{ required: true, message: '总学时数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['emContent', {rules: [{ required: true, message: '备注不能为空' }] }]" />
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
        name: 'DcaBEmployEdit',
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
            dcaBEmploy: {}
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
            setFormValues ({...dcaBEmploy}) {
                let fields = [   'emStartTime'    , 'emEndTime'    , 'emCoursename'    , 'emOtherwork'    , 'emStudentcount'    , 'emWeektime'    , 'emTotaltime'    , 'emContent'          ]
                let fieldDates = [    'emStartTime'       , 'emEndTime'                ]
                Object.keys(dcaBEmploy).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBEmploy[key]!=='') {
                            obj[key] = moment(dcaBEmploy[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBEmploy[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBEmploy. id                                 = dcaBEmploy. id                                            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBEmploy = this.form.getFieldsValue()
                    dcaBEmploy. id                                = this.dcaBEmploy.id
                    this.$put('dcaBDocEmploy', {
                        ...dcaBEmploy
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
