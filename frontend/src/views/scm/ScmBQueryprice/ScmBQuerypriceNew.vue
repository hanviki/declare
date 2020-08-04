<template>
  <a-card :bordered="false" class="card-area">
    <template class="ant-card-actions" slot="actions">
      <a-button @click="handleSubmit" type="primary" :loading="loading">保存并提交</a-button>
      <a-button @click="handleSave" type="primary" :loading="loading">保存</a-button>
    </template>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">新增药品</a-button>
        <a-button @click="batchDelete">删除药品</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfo"
        :columns="columns"
        :rowKey="record => record.matnr"
        :dataSource="dataSource"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      >
        <template slot="amount" slot-scope="text, record">
          <editableNumber-cell :text="text" @change="onCellChange(record.matnr, 'amount', $event)" />
        </template>
        <template slot="productName" slot-scope="text, record">
          <editable-cell :text="text" @change="onCellChange(record.matnr, 'productName', $event)" />
        </template>
        <template slot="endDate" slot-scope="text, record">
          <editableDate-cell :text="text" @change="onCellChange(record.matnr, 'endDate', $event)" />
        </template>
      </a-table>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="addVendor">新增供应商</a-button>
        <a-button @click="batchDeleteVendor">删除供应商</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table
        ref="TableInfoVendor"
        :columns="columnsVendor"
        :rowKey="record => record.id"
        :dataSource="dataSourceVendor"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeysVendor, onChange: onSelectChangeVendor}"
        :bordered="bordered"
        :scroll="{ x: 900 }"
      ></a-table>
      <scmBQuerypriceNew-add
        @close="handleAddClose"
        @ok="handleAddSuccess"
        :addVisiable="addVisiable"
      ></scmBQuerypriceNew-add>
      <vendor-add
        @close="handleVendorAddClose"
        @ok="handleVendorAddSuccess"
        :addVendorVisiable="addVendorVisiable"
      ></vendor-add>
    </div>
  </a-card>
</template>

<script>
import EditableCell from "../../common/EditableCell";
import EditableDateCell from "../../common/EditableDateCell";
import EditableNumberCell from "../../common/EditableNumberCell";
import ScmBQuerypriceNewAdd from "./ScmBQuerypriceNewAdd";
import VendorAdd from "./VendorAdd";
import moment from "moment";

export default {
  name: "scmBQuerypriceNew",
  components: {
    EditableCell,
    EditableDateCell,
    EditableNumberCell,
    ScmBQuerypriceNewAdd,
    VendorAdd
  },
  data() {
    return {
      advanced: false,
      dataSource: [],
      selectedRowKeys: [],
      dataSourceVendor: [], //供应商数据
      selectedRowKeysVendor: [],
      addVisiable: false,
      addVendorVisiable: false,
      loading: false,
      bordered: true
    };
  },
  computed: {
    columns() {
      return [
        {
          title: "药品名称",
          dataIndex: "txz01",
          width: 200,
          fixed: "left"
        },
        {
          title: "药品编码",
          dataIndex: "matnr",
          width: 80
        },
        {
          title: "药品规格",
          dataIndex: "spec",
          width: 100
        },
        {
          title: "计量单位",
          dataIndex: "mseht",
          width: 80
        },
        {
          title: "采购数量",
          dataIndex: "amount",
          scopedSlots: { customRender: "amount" },
          width: 120
        },
        {
          title: "指定厂家(选填)",
          dataIndex: "productName",
          width: 120,
          scopedSlots: { customRender: "productName" }
        },
        {
          title: "询价日期",
          dataIndex: "queryDate",
          customRender: (text, row, index) => {
            return moment(text).format("YYYY-MM-DD");
          },
          width: 100
        },
        {
          title: "截止日期",
          dataIndex: "endDate",
          scopedSlots: { customRender: "endDate" }
        }
      ];
    },
    columnsVendor() {
      return [
        {
          title: "供应商名称",
          dataIndex: "gysname"
        },
        {
          title: "供应商账号",
          dataIndex: "gysaccount"
        }
      ];
    }
  },
  mounted() {},
  methods: {
    reset() {
      this.selectedRowKeys = [];
      this.selectedRowKeysVendor = [];
      this.dataSource = [];
      this.dataSourceVendor = [];
    },
    onSelectChange(selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys;
    },
    onSelectChangeVendor(selectedRowKeys) {
      this.selectedRowKeysVendor = selectedRowKeys;
    },
    onCellChange(key, dataIndex, value) {
      const dataSource = [...this.dataSource];
      const target = dataSource.find(item => item.matnr === key);
      //console.info(target)
      if (target) {
        target[dataIndex] = value;
        this.dataSource = dataSource;
      }
    },
    handleAddSuccess(rows) {
      //console.info(rows)
      const dataSource = [...this.dataSource];
      for (let element of rows) {
        const target = dataSource.find(item => item.matnr === element.matnr);
        if (!target) {
          dataSource.push({
            txz01: element.txz01,
            matnr: element.matnr,
            spec: element.spec,
            mseht: element.mseht,
            id: ""
          });
        }
      }
      this.dataSource = dataSource;
      this.addVisiable = false;
    },
    handleAddClose() {
      this.addVisiable = false;
    },
    add() {
      this.addVisiable = true;
    },
    batchDelete() {
      if (!this.selectedRowKeys.length) {
        this.$message.warning("请选择删除的药品");
        return;
      }
      console.info(this.selectedRowKeys);
      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          var dataSource = [...that.dataSource];
          for (let d of that.selectedRowKeys) {
            dataSource = dataSource.filter(item => item.matnr !== d);
          }
          that.dataSource = dataSource;
          that.selectedRowKeys = [];
        },
        onCancel() {
          that.selectedRowKeys = [];
        }
      });
    },
    batchDeleteVendor() {
      if (!this.selectedRowKeysVendor.length) {
        this.$message.warning("请选择删除的供应商");
        return;
      }

      let that = this;
      this.$confirm({
        title: "确定删除所选中的记录?",
        content: "当您点击确定按钮后，这些记录将会被彻底删除",
        centered: true,
        onOk() {
          var dataSourceVendor = [...that.dataSourceVendor];
          for (let d of that.selectedRowKeysVendor) {
            dataSourceVendor = dataSourceVendor.filter(item => item.id !== d);
          }
          that.dataSourceVendor = dataSourceVendor;
          that.selectedRowKeysVendor = [];
        },
        onCancel() {
          that.selectedRowKeysVendor = [];
        }
      });
    },
    handleVendorAddSuccess(rows) {
      const dataSourceVendor = [...this.dataSourceVendor];
      var arrData = [];
      for (let element of rows) {
        const target = dataSourceVendor.find(item => item.id === element.code);
        const IsRepeat = arrData.find(item => item.id === element.code);
        if (!target && !IsRepeat) {
          const newData = {
            gysname: element.name,
            gysaccount: element.code,
            id: element.code
          };
          arrData.push(newData);
        }
      }
      //
      this.dataSourceVendor = [...dataSourceVendor, ...arrData];
      arrData = [];
      this.addVendorVisiable = false;
    },
    handleVendorAddClose() {
      this.addVendorVisiable = false;
    },
    addVendor() {
      this.addVendorVisiable = true;
    },
    handleSave() {
      let that = this;
      const dataSource = [...that.dataSource];
      if (dataSource.length == 0) {
        this.$message.warning("请选择询价药品");
        return;
      }
      Date.prototype.format = function(fmt) {
        var o = {
          "M+": this.getMonth() + 1, //月份
          "d+": this.getDate(), //日
          "h+": this.getHours(), //小时
          "m+": this.getMinutes(), //分
          "s+": this.getSeconds(), //秒
          "q+": Math.floor((this.getMonth() + 3) / 3), //季度
          S: this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) {
          fmt = fmt.replace(
            RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length)
          );
        }
        for (var k in o) {
          if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(
              RegExp.$1,
              RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length)
            );
          }
        }
        return fmt;
      };
      /*
       * 重写时间的toJSON方法，因为在调用JSON.stringify的时候，时间转换就调用的toJSON，这样会导致少8个小时，所以重写它的toJSON方法
       */
      Date.prototype.toJSON = function() {
        return this.format("yyyy-MM-dd hh:mm:ss"); // util.formatDate是自定义的个时间格式化函数
      };
      const dataSourceVendor = [...that.dataSourceVendor];
      this.$post("scmBQueryprice/xjAdd", {
        maters: JSON.stringify(dataSource),
        gys: JSON.stringify(dataSourceVendor),
        state: 0
      })
        .then(() => {
          this.reset();
          this.$router.push("/scm/ScmBQueryprice/ScmBQueryprice");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    handleSubmit() {
      let that = this;
      const dataSource = [...that.dataSource];
      if (dataSource.length == 0) {
        this.$message.warning("请选择询价药品");
        return;
      }
      var mes = "";
      for (let item of dataSource) {
        if (
          item.amount == undefined ||
          item.amount == null ||
          item.amount == 0 ||
          item.amount == ""
        ) {
          mes += "药品" + item.matnr + ":采购数量必填";
        }
        if (
          item.endDate == undefined ||
          item.endDate == null ||
          item.endDate == ""
        ) {
          mes += "药品" + item.matnr + ":截止日期必填";
        }
      }
      const dataSourceVendor = [...that.dataSourceVendor];
      if (dataSourceVendor.length == 0) {
        mes += "请选择供应商";
      }
      if (mes !== "") {
        this.$message.warning(mes);
        return;
      }
      Date.prototype.format = function(fmt) {
        var o = {
          "M+": this.getMonth() + 1, //月份
          "d+": this.getDate(), //日
          "h+": this.getHours(), //小时
          "m+": this.getMinutes(), //分
          "s+": this.getSeconds(), //秒
          "q+": Math.floor((this.getMonth() + 3) / 3), //季度
          S: this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) {
          fmt = fmt.replace(
            RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length)
          );
        }
        for (var k in o) {
          if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(
              RegExp.$1,
              RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length)
            );
          }
        }
        return fmt;
      };
      /*
       * 重写时间的toJSON方法，因为在调用JSON.stringify的时候，时间转换就调用的toJSON，这样会导致少8个小时，所以重写它的toJSON方法
       */
      Date.prototype.toJSON = function() {
        return this.format("yyyy-MM-dd hh:mm:ss"); // util.formatDate是自定义的个时间格式化函数
      };

      this.$post("scmBQueryprice/xjAdd", {
        maters: JSON.stringify(dataSource),
        gys: JSON.stringify(dataSourceVendor),
        state: 1
      })
        .then(() => {
          this.reset();
          this.$router.push("/scm/ScmBQueryprice/ScmBQueryprice");
        })
        .catch(() => {
          this.loading = false;
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
