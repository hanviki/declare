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
                        label="编码=主键">
                        <a-input placeholder="请输入编码=主键" v-decorator="['code', {rules: [{ required: true, message: '编码=主键不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['name', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="父ID">
                        <a-input placeholder="请输入父ID" v-decorator="['parentId', {rules: [{ required: true, message: '父ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="排序">
                        <a-input placeholder="请输入排序" v-decorator="['orderNum', {rules: [{ required: true, message: '排序不能为空' }] }]" />
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
        name: 'DcaDDeclaremodulesEdit',
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
            dcaDDeclaremodules: {}
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
            setFormValues ({...dcaDDeclaremodules}) {
                let fields = [  'code'    , 'name'    , 'parentId'          , 'orderNum'    ]
                let fieldDates = [           ]
                Object.keys(dcaDDeclaremodules).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaDDeclaremodules[key]!=='') {
                            obj[key] = moment(dcaDDeclaremodules[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaDDeclaremodules[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaDDeclaremodules. id                       = dcaDDeclaremodules. id                                  },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaDDeclaremodules = this.form.getFieldsValue()
                    dcaDDeclaremodules. id                      = this.dcaDDeclaremodules.id
                    this.$put('dcaDDeclaremodules', {
                        ...dcaDDeclaremodules
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
