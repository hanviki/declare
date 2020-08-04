<template>
  <div>
    <a-row>
      <a-col :span="12">
        <a-form-item
          label="院区"
          :labelCol="{span: 8}"
          :wrapperCol="{span: 15, offset: 1}"
        >
          <a-select
            v-model="firstProvince"
            style="width: 100%"
            @change="handleProvinceChange"
            dropdownMenuStyle="width:200%"
          >
            <a-select-option
              v-for="province in provinceData"
              :key="province.id"
            >{{
        province.name
      }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
      <a-col :span="12">
        <a-form-item
          label="库房"
          :labelCol="{span: 8}"
          :wrapperCol="{span: 15, offset: 1}"
        >
          <a-select
            v-model="secondCity"
            style="width: 100%"
            @change="handleCityChange"
            dropdownMenuStyle="width:200%"
          >
            <a-select-option
              v-for="city in cities"
              :key="city.code"
            >{{ city.name }}</a-select-option>
          </a-select>
        </a-form-item>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  data () {
    return {
      areaData: [],
      provinceData: [],
      firstProvince: '',
      cities: [],
      secondCity: ''
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    reset () {
      this.firstProvince = ''
      this.secondCity = ''
    },
    handleProvinceChange (value) {
      if (value === '0') {
        this.cities = []
      }
      else {
        this.cities = this.areaData.filter(item => item.parentId === value)
      }
      this.secondCity = ''
      this.$emit("werks", value)
    },
    handleCityChange (value) {
      this.$emit("lgort", value)
    },
    fetch () {
      this.$get('scmDArea/getAreas', {
      }).then((r) => {
        this.areaData = r.data.data

        this.provinceData = this.areaData.filter(item => item.parentId === '0')
        this.provinceData.push({ id: '0', parentId: '0', name: '全部' })

      })
    }
  }
}
</script>
