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
                        label="获奖名称">
                        <a-input placeholder="请输入获奖名称" v-decorator="['prizeName', {rules: [{ required: true, message: '获奖名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="获奖等级">
                        <a-input placeholder="请输入获奖等级" v-decorator="['prizeGrade', {rules: [{ required: true, message: '获奖等级不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="获奖时间">
                        <a-date-picker v-decorator="[ 'prizeDate', {rules: [{ required: true, message: '获奖时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBTeacherprizeEdit',
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
            dcaBTeacherprize: {}
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
            setFormValues ({...dcaBTeacherprize}) {
                let fields = [       'prizeName'    , 'prizeGrade'    , 'prizeDate'    , 'ranknum'               ]
                let fieldDates = [          'prizeDate'                ]
                Object.keys(dcaBTeacherprize).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTeacherprize[key]!=='') {
                            obj[key] = moment(dcaBTeacherprize[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTeacherprize[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTeacherprize. id                                           = dcaBTeacherprize. id                                                      },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTeacherprize = this.form.getFieldsValue()
                    dcaBTeacherprize. id                                          = this.dcaBTeacherprize.id
                    this.$put('dcaBDocTeacherprize', {
                        ...dcaBTeacherprize
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
