<template>
  <div class="option-area">
    
    <a-row>
      <a-col :span="8">
         <a-button
            @click="exportTemplate"
            type="primary"
            :loading="loading"
          >导入模板下载</a-button>
          </a-col>

    <a-col :span="8">
          <a-upload
         :fileList="fileList"
         :remove="handleRemove"
         :disabled="fileList.length === 1"
         :beforeUpload="beforeUpload">
        <a-button>
          <a-icon type="upload" /> 选择.xlsx文件
        </a-button>
      </a-upload>
    </a-col>
    <a-col :span="8">
    <a-button
      @click="handleUpload"
      :disabled="fileList.length === 0"
      :loading="uploading"
    >
      {{uploading ? '导入中' : '导入Excel' }}
    </a-button>
    <import-result
      @close="handleClose"
      :importData="importData"
      :errors="errors"
      :times="times"
      :importResultVisible="importResultVisible"
    >
    </import-result>
    </a-col>
        </a-row>
  </div>
</template>
<script>
import ImportResult from '../others/ImportResult'
export default {
  name: 'importTemplate',
  components: { ImportResult },
  data () {
    return {
      fileList: [],
      importData: [],
      times: '',
      errors: [],
      uploading: false,
      importResultVisible: false,
    }
  },
  props: {
    url: {
      default: ''
    },
    templateUrl: {
      default: ''
    }
  },
  methods: {
    handleClose () {
      this.importResultVisible = false
    },
    exportTemplate () {
      this.$download(this.templateUrl, {}, '导入模板.xlsx')
    },
    handleRemove (file) {
      if (this.uploading) {
        this.$message.warning('文件导入中，请勿删除')
        return
      }
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
    },
    beforeUpload (file) {
      this.fileList = [...this.fileList, file]
      return false
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true
      this.$upload(this.url, formData).then((r) => {
        let data = r.data.data
        if (data.data.length) {
          this.fetch()
        }
        this.importData = data.data
        this.errors = data.error
        this.times = data.time / 1000
        this.uploading = false
        this.fileList = []
        this.importResultVisible = true
      }).catch((r) => {
        console.error(r)
        this.uploading = false
        this.fileList = []
      })
    }
  }
}
</script>
<style lang="less" scoped>
  @import "../../../static/less/Common";
  .option-area {
    display: inline-block;
    width: 100%;
    padding: 0 .9rem;
    margin: .5rem 0;
    .upload-area {
      display: inline;
      float: left;
      width: 50%
    }
    .button-area {
      margin-left: 1rem;
      display: inline;
      float: right;
      width: 30%;
    }
  }
</style>