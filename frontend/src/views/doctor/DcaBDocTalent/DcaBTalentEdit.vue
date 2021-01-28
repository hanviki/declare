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
                        <a-input placeholder="请输入课程名称" v-decorator="['taletName', {rules: [{ required: true, message: '课程名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="自何年月">
                        <a-date-picker v-decorator="[ 'talentStartDate', {rules: [{ required: true, message: '自何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="至何年月">
                        <a-date-picker v-decorator="[ 'talentEndDate', {rules: [{ required: true, message: '至何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="课程类别">
                        <a-input placeholder="请输入课程类别" v-decorator="['talentType', {rules: [{ required: true, message: '课程类别不能为空' }] }]" />
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
        name: 'DcaBTalentEdit',
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
            dcaBTalent: {}
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
            setFormValues ({...dcaBTalent}) {
                let fields = [   'taletName'    , 'talentStartDate'    , 'talentEndDate'    , 'talentType'    , 'studentNumber'    , 'totalTime'    , 'personTime'          ]
                let fieldDates = [     'talentStartDate'       , 'talentEndDate'              ]
                Object.keys(dcaBTalent).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTalent[key]!=='') {
                            obj[key] = moment(dcaBTalent[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTalent[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTalent. id                               = dcaBTalent. id                                          },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTalent = this.form.getFieldsValue()
                    dcaBTalent. id                              = this.dcaBTalent.id
                    this.$put('dcaBDocTalent', {
                        ...dcaBTalent
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
