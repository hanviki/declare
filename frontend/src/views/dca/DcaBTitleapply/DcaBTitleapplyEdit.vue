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
                        label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['name', {rules: [{ required: true, message: '名字不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="职称ID">
                        <a-input placeholder="请输入职称ID" v-decorator="['titleId', {rules: [{ required: true, message: '职称ID不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="申请职称">
                        <a-input placeholder="请输入申请职称" v-decorator="['titleName', {rules: [{ required: true, message: '申请职称不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="年度">
                        <a-input placeholder="请输入年度" v-decorator="['year', {rules: [{ required: true, message: '年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否高级">
                        <a-input placeholder="请输入是否高级" v-decorator="['IsGj', {rules: [{ required: true, message: '是否高级不能为空' }] }]" />
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
        name: 'DcaBTitleapplyEdit',
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
            dcaBTitleapply: {}
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
            setFormValues ({...dcaBTitleapply}) {
                let fields = [  'code'    , 'name'      , 'titleId'    , 'titleName'    , 'year'    , 'IsGj'           , 'orderNum'    ]
                let fieldDates = [                 ]
                Object.keys(dcaBTitleapply).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTitleapply[key]!=='') {
                            obj[key] = moment(dcaBTitleapply[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTitleapply[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTitleapply. id                                   = dcaBTitleapply. id                                              },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTitleapply = this.form.getFieldsValue()
                    dcaBTitleapply. id                                  = this.dcaBTitleapply.id
                    this.$put('dcaBTitleapply', {
                        ...dcaBTitleapply
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
