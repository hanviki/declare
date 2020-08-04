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
                        label="主键ID">
                        <a-input placeholder="请输入主键ID" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料描述">
                        <a-input placeholder="请输入物料描述" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料编码">
                        <a-input placeholder="请输入物料编码" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="规格">
                        <a-input placeholder="请输入规格" v-decorator="['spec', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="拼音码">
                        <a-input placeholder="请输入拼音码" v-decorator="['spellCode', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="生产厂家">
                        <a-input placeholder="请输入生产厂家" v-decorator="['produceArea', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['gysname', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="批次号">
                        <a-input placeholder="请输入批次号" v-decorator="['charg', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="退货数量">
                        <a-input placeholder="请输入退货数量" v-decorator="['amount', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['comments', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="退货部门">
                        <a-input placeholder="请输入退货部门" v-decorator="['deptId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="部门名称">
                        <a-input placeholder="请输入部门名称" v-decorator="['deptIdName', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="0是未提交1是提交2是审核完成3是供应商确认4退货完成">
                        <a-input placeholder="请输入0是未提交1是提交2是审核完成3是供应商确认4退货完成" v-decorator="['state', {}]" />
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
        name: 'VScmBSalereturnEdit',
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
            vScmBSalereturn: {}
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
            setFormValues ({...vScmBSalereturn}) {
                let fields = [  'id'     ,  'txz01'     ,  'gysaccount'     ,  'matnr'     ,  'spec'     ,  'spellCode'     ,  'produceArea'     ,  'gysname'     ,  'charg'     ,  'amount'     ,  'comments'     ,  'deptId'     ,  'deptIdName'     ,  'state'   ]
                let fieldDates = [                                          ]
                Object.keys(vScmBSalereturn).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(vScmBSalereturn[key]!=='') {
                            obj[key] = moment(vScmBSalereturn[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = vScmBSalereturn[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.vScmBSalereturn.id = vScmBSalereturn.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let vScmBSalereturn = this.form.getFieldsValue()
                        vScmBSalereturn.id = this.vScmBSalereturn.id
                    this.$put('vScmBSalereturn', {
                        ...vScmBSalereturn
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
