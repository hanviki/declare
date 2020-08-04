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
                        label="">
                        <a-input placeholder="请输入" v-decorator="['code', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['name', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料ID">
                        <a-input placeholder="请输入物料ID" v-decorator="['matnrId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料编码">
                        <a-input placeholder="请输入物料编码" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="单位">
                        <a-input placeholder="请输入单位" v-decorator="['mseht', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料名称">
                        <a-input placeholder="请输入物料名称" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="规格">
                        <a-input placeholder="请输入规格" v-decorator="['spec', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="创建日期">
                        <a-date-picker v-decorator="[ 'createDate', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="旧价格">
                        <a-input placeholder="请输入旧价格" v-decorator="['oldPrice', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="调整价格">
                        <a-input placeholder="请输入调整价格" v-decorator="['adjustPrice', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['comments', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="状态">
                        <a-input placeholder="请输入状态" v-decorator="['state', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商名称">
                        <a-input placeholder="请输入供应商名称" v-decorator="['gysname', {}]" />
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
        name: 'ScmBPriceadjustEdit',
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
            scmBPriceadjust: {}
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
            setFormValues ({...scmBPriceadjust}) {
                let fields = [  'id'     ,  'code'     ,  'name'     ,  'matnrId'     ,  'matnr'     ,  'mseht'     ,  'txz01'     ,  'spec'     ,  'createDate'     ,  'oldPrice'     ,  'adjustPrice'     ,  'comments'     ,  'state'     ,  'gysaccount'     ,  'gysname'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ]
                let fieldDates = [                           'createDate'                              , 'createTime'         , 'modifyTime'           ]
                Object.keys(scmBPriceadjust).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(scmBPriceadjust[key]!=='') {
                            obj[key] = moment(scmBPriceadjust[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = scmBPriceadjust[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.scmBPriceadjust.id = scmBPriceadjust.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let scmBPriceadjust = this.form.getFieldsValue()
                        scmBPriceadjust.id = this.scmBPriceadjust.id
                    this.$put('scmBPriceadjust', {
                        ...scmBPriceadjust
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
