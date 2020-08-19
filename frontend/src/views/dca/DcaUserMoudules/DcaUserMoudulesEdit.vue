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
                        label="用户ID">
                        <a-input placeholder="请输入用户ID" v-decorator="['userId', {rules: [{ required: true, message: '用户ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="申报模块ID">
                        <a-input placeholder="请输入申报模块ID" v-decorator="['muduleId', {rules: [{ required: true, message: '申报模块ID不能为空' }] }]" />
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
        name: 'DcaUserMoudulesEdit',
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
            dcaUserMoudules: {}
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
            setFormValues ({...dcaUserMoudules}) {
                let fields = [  'userId'    , 'muduleId'    ]
                let fieldDates = [   ]
                Object.keys(dcaUserMoudules).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaUserMoudules[key]!=='') {
                            obj[key] = moment(dcaUserMoudules[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaUserMoudules[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaUserMoudules. id       = dcaUserMoudules. id                  },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaUserMoudules = this.form.getFieldsValue()
                    dcaUserMoudules. id      = this.dcaUserMoudules.id
                    this.$put('dcaUserMoudules', {
                        ...dcaUserMoudules
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
