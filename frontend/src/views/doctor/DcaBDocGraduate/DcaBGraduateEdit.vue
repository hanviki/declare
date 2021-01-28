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
                        label="博士在读人数">
                        <a-input placeholder="请输入博士在读人数" v-decorator="['doctorNumber', {rules: [{ required: true, message: '博士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="硕士在读人数">
                        <a-input placeholder="请输入硕士在读人数" v-decorator="['graduateNumber', {rules: [{ required: true, message: '硕士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="博士在读人数">
                        <a-input placeholder="请输入博士在读人数" v-decorator="['doctorDoneNumber', {rules: [{ required: true, message: '博士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="硕士在读人数">
                        <a-input placeholder="请输入硕士在读人数" v-decorator="['graduateDoneNumber', {rules: [{ required: true, message: '硕士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="已毕业获奖情况">
                        <a-input placeholder="请输入已毕业获奖情况" v-decorator="['prizeContent', {rules: [{ required: true, message: '已毕业获奖情况不能为空' }] }]" />
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
        name: 'DcaBGraduateEdit',
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
            dcaBGraduate: {}
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
            setFormValues ({...dcaBGraduate}) {
                let fields = [   'doctorNumber'    , 'graduateNumber'    , 'doctorDoneNumber'    , 'graduateDoneNumber'    , 'prizeContent'          ]
                let fieldDates = [             ]
                Object.keys(dcaBGraduate).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBGraduate[key]!=='') {
                            obj[key] = moment(dcaBGraduate[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBGraduate[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBGraduate. id                           = dcaBGraduate. id                                      },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBGraduate = this.form.getFieldsValue()
                    dcaBGraduate. id                          = this.dcaBGraduate.id
                    this.$put('dcaBDocGraduate', {
                        ...dcaBGraduate
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
