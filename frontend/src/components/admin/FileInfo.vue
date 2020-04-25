<template>
    <div>
        <el-main>
            <el-form-item prop="file" label="文件上传">
                <input type="file" @change="addFile($event)"/>
            </el-form-item>
            <h3>系统文件</h3>
            <el-table :data="filesInfo">
                <el-table-column prop="name" label="文件名" width="140"></el-table-column>
                <el-table-column fixed="right" label="操作" width="120">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="handleDownload(scope.$index, scope.row)">下载</el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40, 50]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalItems"></el-pagination>
        </el-main>
    </div>
</template>

<script>
export default {
  name: 'FileInfo',
  data () {
    return {
      filesInfo: [],
      filesInfoEnd: [],
      currentPage: 1,
      pageSize: 2,
      totalItems: 0
    }
  },
  created () {
    this.getFilesInfo()
    this.totalItems = this.filesInfo.length
    if (this.totalItems > this.pageSize) {
      for (let index = 0; index < this.pageSize; index++) {
        this.filesInfoEnd.push(this.filesInfo[index])
      }
    } else {
      this.filesInfoEnd = this.filesInfo
    }
  },
  methods: {
    getFilesInfo () {
      this.$axios.get('/admin/files')
        .then(resp => {
          this.filesInfo = resp.data.date
        })
        .catch(error => {
          console.log(error)
        })
    },
    handleSizeChange (val) {
      console.log('每页' + val + '条')
      this.pageSize = val
      this.handleCurrentChange(this.currentPage)
    },
    handleCurrentChange (val) {
      console.log('当前页：' + val)
      this.currentPage = val
      this.currentChangePage(this.filesInfo)
    },
    currentChangePage (list) {
      let from = (this.currentPage - 1) * this.pageSize
      let to = this.currentPage * this.pageSize
      this.filesInfoEnd = []
      for (; from < to; from++) {
        if (list[from]) {
          this.filesInfo.push(list[from])
        }
      }
    },
    handleDownload (index, row) {
      console.log(index, row)
      this.$axios.get('/admin/files/' + row['id'])
        .then(resp => {
          // 获得的是文件流
          this.downloadFile(resp.data.content, resp.data.name)
        })
        .catch(error => {
          console.log(error)
        })
    },
    downloadFile (data, name) {
      const blob = new Blob([data])
      if ('download' in document.createElement('a')) {
        // 非IE下载
        const elink = document.createElement('a')
        elink.download = name
        elink.style.display = 'none'
        elink.href = URL.createObjectURL(blob)
        document.body.appendChild(elink)
        elink.click()
        URL.revokeObjectURL(elink.href) // 释放URL对象
        document.body.removeChild(elink)
      } else {
        // IE10+下载
        navigator.msSaveBlob(blob, name)
      }
    },
    handleDelete (index, row) {
      console.log(index, row)
      this.$axios.delete('/admin/files/' + row['id'])
        .then(resp => {
          this.$message.success(resp.data.message)
          // todo: 可能会影响分页
          this.filesInfo.splice(index, 1)
        })
        .catch(error => {
          console.log(error)
        })
    },
    addFile (e) {
      var formData = new FormData()
      formData.append('name', e.target.files[0].name)
      formData.append('content', e.target.files[0])
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('/admin/files', formData, config)
        .then(resp => {
          this.$message.success(resp.data.message)
          this.getFilesInfo()
        })
        .catch(error => {
          console.log(error)
          this.$message.error('文件上传失败')
        })
    }
  }
}
</script>

<style scoped>

</style>
