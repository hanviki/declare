<template>
    <a-upload
      accept=".png,.jpg,.pdf,.bmp,.gif,.jpeg"
      :fileList="fileList"
      :remove="handleRemove"
      :beforeUpload="beforeUpload"
      @change="handleUpload"
      :disabled="!(fileList.length === 0)"
    >
      <a-button>
        <a-icon
          type="upload"
          v-show="!showFileOnly"
        /> 选择文件 </a-button>
    </a-upload>
    <!-- <a-button
      type="primary"
      @click="handleUpload"
      :disabled="fileList.length === 0 ||isShow===0"
      :loading="uploading"
      style="margin-top: 16px"
      v-show="!showFileOnly"
    >
      {{uploading ? '上传中' : '开始上传' }}
    </a-button> -->
</template>
<script>
export default {
  name: "file",
  data () {
    return {
      tstyle: { "color": "#0785fd", "font-weight": "bold", "background-color": "#ececec" },
      isShow: 1,
      fileList: [],
      uploading: false,
      fileId: ''
    }
  },
  props: {
    title: '',

    fileName: '',
    showFileOnly: false
  },
  methods: {
    handleRemove (file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.isShow = 1
      this.$emit("uploadRemove")
    },
    beforeUpload (file) {
      this.fileList = [...this.fileList, file]
      return false
    },
    handleChange (info) {
      if (info.file.status === 'done') {
        this.handleUpload()
      }
    },
    setForm (fileID, clientName, serverName) {
      this.fileList=[]
      this.fileList.push({
        uid: fileID,
        name: clientName,
        status: 'done',
        url: this.$baseUrl + 'uploadFile/' + serverName
      });
      this.fileId = fileID
    },
    handleUpload () {
      const { fileList } = this
      const formData = new FormData()
      formData.append('file', fileList[0])
      this.uploading = true

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        let fileId = r.data.data
        //this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
        this.$emit("uploadSuc", fileId)
      }).catch(() => {
        this.uploading = false
        this.$message.error('上传失败.')
      })
    }
  },
  watch: {

  },
  mounted () {
  }
}
</script>
