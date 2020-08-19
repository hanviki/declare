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
                        label="">
                        <a-input placeholder="请输入" v-decorator="['muduleName', {rules: [{ required: true, message: '不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="状态">
                        <a-input placeholder="请输入状态" v-decorator="['state', {rules: [{ required: true, message: '状态不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="父ID">
                        <a-input placeholder="请输入父ID" v-decorator="['parentId', {rules: [{ required: true, message: '父ID不能为空' }] }]" />
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
        name: 'DcaDMudulesEdit',
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
            dcaDMudules: {}
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
            setFormValues ({...dcaDMudules}) {
                let fields = [  'muduleName'    , 'state'         , 'parentId'    ]
                let fieldDates = [         ]
                Object.keys(dcaDMudules).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaDMudules[key]!=='') {
                            obj[key] = moment(dcaDMudules[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaDMudules[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaDMudules. id                   = dcaDMudules. id                              },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaDMudules = this.form.getFieldsValue()
                    dcaDMudules. id                  = this.dcaDMudules.id
                    this.$put('dcaDMudules', {
                        ...dcaDMudules
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
