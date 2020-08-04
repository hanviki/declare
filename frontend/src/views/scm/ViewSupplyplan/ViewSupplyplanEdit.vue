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
                        label="订单号">
                        <a-input placeholder="请输入订单号" v-decorator="['ebeln', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="项目号">
                        <a-input placeholder="请输入项目号" v-decorator="['ebelp', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料ID">
                        <a-input placeholder="请输入物料ID" v-decorator="['matnr', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="物料描述">
                        <a-input placeholder="请输入物料描述" v-decorator="['txz01', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="院区名称">
                        <a-input placeholder="请输入院区名称" v-decorator="['werkst', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="院区ID">
                        <a-input placeholder="请输入院区ID" v-decorator="['werks', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="库房ID">
                        <a-input placeholder="请输入库房ID" v-decorator="['lgort', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="库房名称">
                        <a-input placeholder="请输入库房名称" v-decorator="['lgortName', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="订单数量">
                        <a-input placeholder="请输入订单数量" v-decorator="['menge', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="计量单位ID">
                        <a-input placeholder="请输入计量单位ID" v-decorator="['meins', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="计量单位">
                        <a-input placeholder="请输入计量单位" v-decorator="['mseht', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="单价">
                        <a-input placeholder="请输入单价" v-decorator="['netpr', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="订单开始时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'eindt', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="订单结束时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'bedat', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="类型">
                        <a-input placeholder="请输入类型" v-decorator="['bsart', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="供应数量">
                        <a-input placeholder="请输入供应数量" v-decorator="['gMenge', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="批号">
                        <a-input placeholder="请输入批号" v-decorator="['charge', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="有效期">
                        <a-date-picker v-decorator="[ 'vfdat', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="生产日期">
                        <a-date-picker v-decorator="[ 'hsdat', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票号码">
                        <a-input placeholder="请输入发票号码" v-decorator="['fphm', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票金额">
                        <a-input placeholder="请输入发票金额" v-decorator="['fpjr', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票日期">
                        <a-date-picker v-decorator="[ 'fprq', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="父ID">
                        <a-input placeholder="请输入父ID" v-decorator="['baseId', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="主键">
                        <a-input placeholder="请输入主键" v-decorator="['id', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="状态">
                        <a-input placeholder="请输入状态" v-decorator="['status', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="发票编码">
                        <a-input placeholder="请输入发票编码" v-decorator="['fpbm', {}]" />
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
                        label="包装规格">
                        <a-input placeholder="请输入包装规格" v-decorator="['pkgAmount', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="包装数量">
                        <a-input placeholder="请输入包装数量" v-decorator="['pkgNumber', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['uninformedState', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['informedState', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="订单类型">
                        <a-input placeholder="请输入订单类型" v-decorator="['bsartD', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="联系人">
                        <a-input placeholder="请输入联系人" v-decorator="['linkPerson', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="送达科室">
                        <a-input placeholder="请输入送达科室" v-decorator="['sendDepart', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="联系方式">
                        <a-input placeholder="请输入联系方式" v-decorator="['linkTelephone', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="送货清单号">
                        <a-input placeholder="请输入送货清单号" v-decorator="['sendOrderCode', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="缺货原因">
                        <a-input placeholder="请输入缺货原因" v-decorator="['outCause', {}]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="补货日期">
                        <a-date-picker v-decorator="[ 'outDate', {}]" />
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
        name: 'ViewSupplyplanEdit',
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
            viewSupplyplan: {}
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
            setFormValues ({...viewSupplyplan}) {
                let fields = [  'ebeln'     ,  'ebelp'     ,  'matnr'     ,  'txz01'     ,  'werkst'     ,  'werks'     ,  'lgort'     ,  'lgortName'     ,  'menge'     ,  'meins'     ,  'mseht'     ,  'netpr'     ,  'eindt'     ,  'bedat'     ,  'bsart'     ,  'gMenge'     ,  'charge'     ,  'vfdat'     ,  'hsdat'     ,  'fphm'     ,  'fpjr'     ,  'fprq'     ,  'baseId'     ,  'id'     ,  'status'     ,  'fpbm'     ,  'gysaccount'     ,  'gysname'     ,  'pkgAmount'     ,  'pkgNumber'     ,  'uninformedState'     ,  'informedState'     ,  'bsartD'     ,  'linkPerson'     ,  'sendDepart'     ,  'linkTelephone'     ,  'sendOrderCode'     ,  'outCause'     ,  'outDate'     ,  'isDeletemark'     ,  'createTime'     ,  'modifyTime'     ,  'createUserId'     ,  'modifyUserId'   ]
                let fieldDates = [                                       'eindt'         , 'bedat'                  , 'vfdat'         , 'hsdat'               , 'fprq'                                                         , 'outDate'            , 'createTime'         , 'modifyTime'           ]
                Object.keys(viewSupplyplan).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(viewSupplyplan[key]!=='') {
                            obj[key] = moment(viewSupplyplan[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = viewSupplyplan[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.viewSupplyplan.id = viewSupplyplan.id
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let viewSupplyplan = this.form.getFieldsValue()
                        viewSupplyplan.id = this.viewSupplyplan.id
                    this.$put('viewSupplyplan', {
                        ...viewSupplyplan
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
