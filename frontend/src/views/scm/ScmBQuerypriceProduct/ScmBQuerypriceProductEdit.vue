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
                        label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="父ID">
                        <a-input placeholder="请输入父ID" v-decorator="['baseId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="厂家">
                        <a-input placeholder="请输入厂家" v-decorator="['productName', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否删除">
                        <a-input placeholder="请输入是否删除" v-decorator="['isDeletemark', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="创建时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createTime', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="修改时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'modifyTime', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="创建人">
                        <a-input placeholder="请输入创建人" v-decorator="['createUserId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="修改人">
                        <a-input placeholder="请输入修改人" v-decorator="['modifyUserId', {}]" />
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
        name: 'ScmBQuerypriceProductEdit',
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
            scmBQuerypriceProduct: {}
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
            setFormValues ({...scmBQuerypriceProduct}) {
                let fields = [  'id'     ,  'baseId'     ,  'productName'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ]
                let fieldDates = [               'createTime'         , 'modifyTime'           ]
                Object.keys(scmBQuerypriceProduct).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(scmBQuerypriceProduct[key]!=='') {
                            obj[key] = moment(scmBQuerypriceProduct[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = scmBQuerypriceProduct[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.scmBQuerypriceProduct.id = scmBQuerypriceProduct.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let scmBQuerypriceProduct = this.form.getFieldsValue()
                        scmBQuerypriceProduct.id = this.scmBQuerypriceProduct.id
                    this.$put('scmBQuerypriceProduct', {
                        ...scmBQuerypriceProduct
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
