<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="主键ID">
                        <a-input placeholder="请输入主键ID" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料描述">
                        <a-input placeholder="请输入物料描述" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['gysaccount', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料编码">
                        <a-input placeholder="请输入物料编码" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="规格">
                        <a-input placeholder="请输入规格" v-decorator="['spec', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="拼音码">
                        <a-input placeholder="请输入拼音码" v-decorator="['spellCode', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="生产厂家">
                        <a-input placeholder="请输入生产厂家" v-decorator="['produceArea', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['gysname', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="批次号">
                        <a-input placeholder="请输入批次号" v-decorator="['charg', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="退货数量">
                        <a-input placeholder="请输入退货数量" v-decorator="['amount', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['comments', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="退货部门">
                        <a-input placeholder="请输入退货部门" v-decorator="['deptId', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="部门名称">
                        <a-input placeholder="请输入部门名称" v-decorator="['deptIdName', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
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
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'VScmBSalereturnAdd',
        props: {
            addVisiable: {
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
                this.vScmBSalereturn = {}
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    this.setFields()
                    this.$post('vScmBSalereturn', {
                        ...this.vScmBSalereturn
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            },
            setFields () {
                let values = this.form.getFieldsValue([  'id'     ,  'txz01'     ,  'gysaccount'     ,  'matnr'     ,  'spec'     ,  'spellCode'     ,  'produceArea'     ,  'gysname'     ,  'charg'     ,  'amount'     ,  'comments'     ,  'deptId'     ,  'deptIdName'     ,  'state'   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.vScmBSalereturn[_key] = values[_key] })
                }
            }
        }
    }
</script>
