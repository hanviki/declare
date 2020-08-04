<template>
  <div class="editable-cell">
    <div
      v-if="editable"
      class="editable-cell-input-wrapper"
    >
      <a-date-picker :defaultValue="value==null?'':moment(value, dateFormat)" @change="handleChange"  @pressEnter="check"/>
      <a-icon
        type="check"
        class="editable-cell-icon-check"
        @click="check"
      />
    </div>
    <div
      v-else
      class="editable-cell-text-wrapper"
    >
      {{ value  }}
      <a-icon
        type="edit"
        class="editable-cell-icon-check"
        @click="edit"
      />
    </div>
  </div>
</template>
<script>
import moment from 'moment';

export default {
  props: {
    text: String,
  },
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      value: this.text,
      editable: false,
    };
  },
  methods: {
    handleChange (date,dateStr) {
      this.value = dateStr;
      this.check()
    },
    check () {
      this.editable = false;
      this.$emit('change', this.value);
    },
    edit () {
      this.editable = true;
    },
    moment,
  },
};
</script>