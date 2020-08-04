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
                        label="医院">
                        <a-input placeholder="请输入医院" v-decorator="['hospitalName', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="价格">
                        <a-input placeholder="请输入价格" v-decorator="['price', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['commentsD', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="排序">
                        <a-input placeholder="请输入排序" v-decorator="['displayIndex', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="文件ID">
                        <a-input placeholder="请输入文件ID" v-decorator="['comFileId', {}]" />
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
        name: 'ScmBQuotedpriceDEdit',
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
            scmBQuotedpriceD: {}
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
            setFormValues ({...scmBQuotedpriceD}) {
                let fields = [  'id'     ,  'baseId'     ,  'hospitalName'     ,  'price'     ,  'commentsD'     ,  'displayIndex'     ,  'comFileId'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ]
                let fieldDates = [                           'createTime'         , 'modifyTime'           ]
                Object.keys(scmBQuotedpriceD).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(scmBQuotedpriceD[key]!=='') {
                            obj[key] = moment(scmBQuotedpriceD[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = scmBQuotedpriceD[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.scmBQuotedpriceD.id = scmBQuotedpriceD.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let scmBQuotedpriceD = this.form.getFieldsValue()
                        scmBQuotedpriceD.id = this.scmBQuotedpriceD.id
                    this.$put('scmBQuotedpriceD', {
                        ...scmBQuotedpriceD
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
