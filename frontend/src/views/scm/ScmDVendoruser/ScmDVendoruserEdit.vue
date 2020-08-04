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
                        label="供应商主表ID">
                        <a-input placeholder="请输入供应商主表ID" v-decorator="['baseId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="业务员姓名">
                        <a-input placeholder="请输入业务员姓名" v-decorator="['name', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="身份证号">
                        <a-input placeholder="请输入身份证号" v-decorator="['idcard', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="身份证照片反面">
                        <a-input placeholder="请输入身份证照片反面" v-decorator="['idcardBack', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="身份证照片正面">
                        <a-input placeholder="请输入身份证照片正面" v-decorator="['idcardFront', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="联系电话">
                        <a-input placeholder="请输入联系电话" v-decorator="['telphone', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="免冠照片">
                        <a-input placeholder="请输入免冠照片" v-decorator="['headImage', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="委托书">
                        <a-input placeholder="请输入委托书" v-decorator="['agentImage', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="有效期开始时间">
                        <a-date-picker v-decorator="[ 'validDatestart', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="有效期">
                        <a-date-picker v-decorator="[ 'validDate', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="是否有效">
                        <a-input placeholder="请输入是否有效" v-decorator="['isValid', {}]" />
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
        name: 'ScmDVendoruserEdit',
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
            scmDVendoruser: {}
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
            setFormValues ({...scmDVendoruser}) {
                let fields = [  'id'     ,  'baseId'     ,  'name'     ,  'idcard'     ,  'idcardBack'     ,  'idcardFront'     ,  'telphone'     ,  'headImage'     ,  'agentImage'     ,  'validDatestart'     ,  'validDate'     ,  'isValid'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ]
                let fieldDates = [                              'validDatestart'         , 'validDate'               , 'createTime'         , 'modifyTime'           ]
                Object.keys(scmDVendoruser).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(scmDVendoruser[key]!=='') {
                            obj[key] = moment(scmDVendoruser[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = scmDVendoruser[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.scmDVendoruser.id = scmDVendoruser.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let scmDVendoruser = this.form.getFieldsValue()
                        scmDVendoruser.id = this.scmDVendoruser.id
                    this.$put('scmDVendoruser', {
                        ...scmDVendoruser
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
