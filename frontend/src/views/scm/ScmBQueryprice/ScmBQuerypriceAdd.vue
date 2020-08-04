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
                    label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="名字">
                        <a-input placeholder="请输入名字" v-decorator="['name', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料ID">
                        <a-input placeholder="请输入物料ID" v-decorator="['matnrId', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料编码">
                        <a-input placeholder="请输入物料编码" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料单位">
                        <a-input placeholder="请输入物料单位" v-decorator="['mseht', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料名称">
                        <a-input placeholder="请输入物料名称" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="物料规格">
                        <a-input placeholder="请输入物料规格" v-decorator="['spec', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="采购数量">
                        <a-input placeholder="请输入采购数量" v-decorator="['amount', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="询价日期">
                        <a-date-picker v-decorator="[ 'queryDate', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="结束日期">
                        <a-date-picker v-decorator="[ 'endDate', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="询价状态">
                        <a-input placeholder="请输入询价状态" v-decorator="['queryState', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="询价类型">
                        <a-input placeholder="请输入询价类型" v-decorator="['queryType', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="部门ID">
                        <a-input placeholder="请输入部门ID" v-decorator="['deptId', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['comments', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="状态">
                        <a-input placeholder="请输入状态" v-decorator="['state', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="是否删除">
                        <a-input placeholder="请输入是否删除" v-decorator="['isDeletemark', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="创建时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'createTime', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="修改时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'modifyTime', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="创建人">
                        <a-input placeholder="请输入创建人" v-decorator="['createUserId', {}]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
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
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'ScmBQuerypriceAdd',
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
                scmBQueryprice: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.scmBQueryprice = {}
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
                    this.$post('scmBQueryprice', {
                        ...this.scmBQueryprice
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
                let values = this.form.getFieldsValue([  'id'     ,  'code'     ,  'name'     ,  'matnrId'     ,  'matnr'     ,  'mseht'     ,  'txz01'     ,  'spec'     ,  'amount'     ,  'queryDate'     ,  'endDate'     ,  'queryState'     ,  'queryType'     ,  'deptId'     ,  'comments'     ,  'state'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.scmBQueryprice[_key] = values[_key] })
                }
            }
        }
    }
</script>
