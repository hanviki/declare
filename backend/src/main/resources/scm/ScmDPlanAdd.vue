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
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="编码">
                        <a-input placeholder="请输入编码" v-decorator="['code', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['name', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="订单号">
                        <a-input placeholder="请输入订单号" v-decorator="['ebeln', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="项目号">
                        <a-input placeholder="请输入项目号" v-decorator="['ebelp', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="供应商账号">
                        <a-input placeholder="请输入供应商账号" v-decorator="['lifnr', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="药品编码">
                        <a-input placeholder="请输入药品编码" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="药品名称">
                        <a-input placeholder="请输入药品名称" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="院区编码">
                        <a-input placeholder="请输入院区编码" v-decorator="['werks', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="院区">
                        <a-input placeholder="请输入院区" v-decorator="['werkst', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="药房">
                        <a-input placeholder="请输入药房" v-decorator="['lgort', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="订单数量">
                        <a-input placeholder="请输入订单数量" v-decorator="['menge', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="单位编码">
                        <a-input placeholder="请输入单位编码" v-decorator="['meins', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="单位">
                        <a-input placeholder="请输入单位" v-decorator="['mseht', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['netpr', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['eindt', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['bedat', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['status', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['freeuse1', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['freeuse2', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['freeuse3', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['comments', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['state', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['createUserId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-date-picker v-decorator="[ 'createTime', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['createDepartId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['modifyUserId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-date-picker v-decorator="[ 'modifyTime', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['modifyDepartId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'dataTimestamp', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['dataOrganiseId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['allmenge', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['suremenge', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="订单类型">
                        <a-input placeholder="请输入订单类型" v-decorator="['bsart', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="送货单位ID">
                        <a-input placeholder="请输入送货单位ID" v-decorator="['sendDeaprtId', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="送货单位">
                        <a-input placeholder="请输入送货单位" v-decorator="['sendDeaprtName', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="联系方式">
                        <a-input placeholder="请输入联系方式" v-decorator="['sendDeaprtContact', {}]" />
                </a-form-item>
                <a-form-item
                        :labelCol="labelCol"
                        :wrapperCol="wrapperCol"
                        label="电话号码">
                        <a-input placeholder="请输入电话号码" v-decorator="['sendDeaprtPhone', {}]" />
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
        name: 'ScmDPlanAdd',
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
                dict: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.scmDPlan = {}
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
                    this.$post('scmDPlan', {
                        ...this.scmDPlan
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
                let values = this.form.getFieldsValue([  'id'     ,  'code'     ,  'name'     ,  'ebeln'     ,  'ebelp'     ,  'lifnr'     ,  'matnr'     ,  'txz01'     ,  'werks'     ,  'werkst'     ,  'lgort'     ,  'menge'     ,  'meins'     ,  'mseht'     ,  'netpr'     ,  'eindt'     ,  'bedat'     ,  'status'     ,  'freeuse1'     ,  'freeuse2'     ,  'freeuse3'     ,  'comments'     ,  'state'     ,  'createUserId'     ,  'createTime'     ,  'createDepartId'     ,  'modifyUserId'     ,  'modifyTime'     ,  'modifyDepartId'     ,  'dataTimestamp'     ,  'dataOrganiseId'     ,  'allmenge'     ,  'suremenge'     ,  'bsart'     ,  'sendDeaprtId'     ,  'sendDeaprtName'     ,  'sendDeaprtContact'     ,  'sendDeaprtPhone'   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.scmDPlan[_key] = values[_key] })
                }
            }
        }
    }
</script>
