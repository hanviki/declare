<template>
  <a-card
    :bordered="true"
    class="card-area"
  >
    <a-select
      show-search
      style="width: 200px"
      @change="handleChange"
    >
      <a-select-option
        v-for="d in yearArr"
        :key="d.value"
      >
        {{ d.text }}
      </a-select-option>
    </a-select>
  </a-card>
</template>

<script>
export default {
  data () {
    return {
    }
  },
  computed: {
    yearArr () {
      let arr = []
      var myDate = new Date()
      var startYear = myDate.getFullYear() - 5//起始年份
      var endYear = myDate.getFullYear() + 1//结束年份
      for (var i = startYear; i <= endYear; i++) {
        arr.push({ value: i, text: i })
      }
      return arr
    }
  },
  mounted () {

  },
  methods: {

  }
}
</script>

<style>
</style>