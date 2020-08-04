<template>
  <a-range-picker
    :key="id"
    ref="rangeDate"
    @change="onChange"
    style="width: 100%"
    :default-value="[startDate, endDate]"
  ></a-range-picker>
</template>

<script>
import moment from 'moment'

export default {
  name: 'RangeDate',
  data () {
    return {
      id: +new Date(),
    }
  },
  props: {
    startDate: moment().subtract(1, "years"),
    endDate: moment()
  },
  methods: {
    onChange (date, dateString) {
      this.$emit('change', dateString)
    },
    reset () {
      this.id = +new Date()
    },
    moment
  }
}
</script>
