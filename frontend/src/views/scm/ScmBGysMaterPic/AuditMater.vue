<template>
  <a-modal
    v-model="show"
    :centered="true"
    :keyboard="false"
    :width="300"
    @cancel="handleCancleClick"
    title="审核"
  >
    <a-textarea v-model="v_suggestion">
    </a-textarea>
    <template slot="footer">
      <a-button
        key="back"
        @click="handleCancel"
      >关闭</a-button>
      <a-button
        key="noSubmit"
        type="danger"
        :loading="loading"
        @click="handleOk(2)"
      >
        审核不通过
      </a-button>
      <a-button
        key="submit"
        type="primary"
        :loading="loading"
        @click="handleOk(1)"
      >
        审核
      </a-button>
    </template>
  </a-modal>
</template>
<script>
export default {
  name: 'audit',
  props: {
    auditVisiable: {
      require: true,
      default: false
    },
    id: '',
    url: ''
  },
  data () {
    return {
      v_suggestion: '同意',
      loading: false
    }
  },
  methods: {
    reset () {
      this.v_suggestion = '同意'
      this.loading = false
    },
    handleOk (state) {
      this.loading = true
      console.info("ssss" + this.url)
      this.$put(this.url, {
        id: this.id,
        state: state,
        auditCause: this.v_suggestion
      }).then(() => {
        this.reset()
        this.$emit('success')
      }).catch(() => {
        this.loading = false
      })
    },
    handleCancleClick (e) {
      this.$emit('close')
    },
    handleCancel (e) {
      this.$emit('close')
    }
  },
  computed: {
    show: {
      get: function () {
        return this.auditVisiable
      },
      set: function () {
      }
    }
  }
}
</script>
