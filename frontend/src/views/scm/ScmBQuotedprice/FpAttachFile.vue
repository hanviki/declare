<template>
  <a-upload
    accept=".png,.jpg,.pdf,.bmp,.gif,.jpeg"
    :fileList="fileList"
    :remove="handleRemove"
    :beforeUpload="beforeUpload"
    @change="handleUpload"
  >
    <a-button v-if="fileList.length === 0 && showType===0">
      <a-icon type="upload"  /> 发票附件 </a-button>
    <!-- <a-button
      type="primary"
      @click="handleUpload"
      :disabled="fileList.length === 0 ||isShow===0"
      :loading="uploading"
    >
      {{uploading ? '上传中' : '开始上传' }} 
    </a-button>-->
  </a-upload>
</template>
<script>
import moment from 'moment'

export default {
  name: "file",
  data () {
    return {
      isShow: 1,
      fileList: [],
      uploading: false
    }
  },
  props: {
    itemIndex: 0,//报价索引
    displayIndex: 0, //医院索引
    isRequire: false,
    fileId: '',
    fileName: '',
    serverName: '',
    clientName: '',
    showType:0
  },
  methods: {
    handleRemove (file) {
      const index = this.fileList.indexOf(file)
      const newFileList = this.fileList.slice()
      newFileList.splice(index, 1)
      this.fileList = newFileList
      this.isShow = 1
      this.$emit('fp', this.itemIndex, this.displayIndex, '')//清空
    },
    onChange (date, dateString) {
      console.log(date, dateString);
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

      // You can use any AJAX library you like
      this.$upload('comFile/upload', formData).then((r) => {
        let fileId2 = r.data.data
        //this.fileList = []
        this.isShow = 0
        this.uploading = false
        this.$message.success('上传成功.')
        this.$emit('fp', this.itemIndex, this.displayIndex, fileId2)
      }).catch(() => {
        this.uploading = false
        this.fileList = []
        this.$message.error('上传失败.')
      })
    }
  },
  watch: {
    // one () {
    //   console.info(2222)
    //   if (!this.fileId&&this.fileId!=''&&this.one==1) {
    //     this.fileList=[]
    //     this.fileList.push({
    //       uid: this.fileId,
    //       name: this.clientName,
    //       status: 'done',
    //       url: this.$baseUrl + 'uploadFile/' + this.serverName
    //     })
    //   }
    //   else{
    //     if(this.one==1){//没选择
    //        this.fileList=[]
    //     }
    //   }
    // }
  },
  mounted () {
  }
}
</script>
