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
                        label="是否高级">
                        <a-input placeholder="请输入是否高级" v-decorator="['isAddvance', {rules: [{ required: true, message: '是否高级不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否开启">
                        <a-input placeholder="请输入是否开启" v-decorator="['isOpen', {rules: [{ required: true, message: '是否开启不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="当前年度">
                        <a-input placeholder="请输入当前年度" v-decorator="['year', {rules: [{ required: true, message: '当前年度不能为空' }] }]" />
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
        name: 'DcaBYearapplyEdit',
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
            dcaBYearapply: {}
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
            setFormValues ({...dcaBYearapply}) {
                let fields = [  'isAddvance'    , 'isOpen'    , 'year'          ]
                let fieldDates = [          ]
                Object.keys(dcaBYearapply).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBYearapply[key]!=='') {
                            obj[key] = moment(dcaBYearapply[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBYearapply[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBYearapply. id                     = dcaBYearapply. id                                },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBYearapply = this.form.getFieldsValue()
                    dcaBYearapply. id                    = this.dcaBYearapply.id
                    this.$put('dcaBYearapply', {
                        ...dcaBYearapply
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
