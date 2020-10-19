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
                        label="职称表ID">
                        <a-input placeholder="请输入职称表ID" v-decorator="['baseId', {rules: [{ required: true, message: '职称表ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="格式化字符川">
                        <a-input placeholder="请输入格式化字符川" v-decorator="['remarks', {rules: [{ required: true, message: '格式化字符川不能为空' }] }]" />
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
        name: 'DcaBTitleapplyDEdit',
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
            dcaBTitleapplyD: {}
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
            setFormValues ({...dcaBTitleapplyD}) {
                let fields = [    'baseId'    , 'remarks'          ]
                let fieldDates = [           ]
                Object.keys(dcaBTitleapplyD).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTitleapplyD[key]!=='') {
                            obj[key] = moment(dcaBTitleapplyD[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTitleapplyD[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTitleapplyD. id                       = dcaBTitleapplyD. id                                  },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTitleapplyD = this.form.getFieldsValue()
                    dcaBTitleapplyD. id                      = this.dcaBTitleapplyD.id
                    this.$put('dcaBTitleapplyD', {
                        ...dcaBTitleapplyD
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
