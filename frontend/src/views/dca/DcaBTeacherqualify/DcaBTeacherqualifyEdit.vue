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
                        label="资格编号">
                        <a-input placeholder="请输入资格编号" v-decorator="['tqCode', {rules: [{ required: true, message: '资格编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="获得时间">
                        <a-date-picker v-decorator="[ 'tqReceiveDate', {rules: [{ required: true, message: '获得时间不能为空' }] }]" />
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
        name: 'DcaBTeacherqualifyEdit',
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
            dcaBTeacherqualify: {}
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
            setFormValues ({...dcaBTeacherqualify}) {
                let fields = [   'tqCode'    , 'tqReceiveDate'          ]
                let fieldDates = [     'tqReceiveDate'          ]
                Object.keys(dcaBTeacherqualify).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTeacherqualify[key]!=='') {
                            obj[key] = moment(dcaBTeacherqualify[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTeacherqualify[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTeacherqualify. id                     = dcaBTeacherqualify. id                                },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTeacherqualify = this.form.getFieldsValue()
                    dcaBTeacherqualify. id                    = this.dcaBTeacherqualify.id
                    this.$put('dcaBTeacherqualify', {
                        ...dcaBTeacherqualify
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
