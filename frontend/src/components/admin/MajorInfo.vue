<template>
    <div>
        <el-main>
            <el-form-item prop="file" label="招生目录和名额文件导入（仅支持excel表）">
                <input type="file" accept="application/vnd.ms-excel" @change="updateMajorInfo($event)"/>
            </el-form-item>
            <h3>招生目录</h3>
            <el-table :data="majorData">
                <el-table-column prop="degree-type" label="学位类型" width="140"></el-table-column>
                <el-table-column prop="major" label="学科专业" width="140"></el-table-column>
                <el-table-column prop="direction" label="研究方向" width="140"></el-table-column>
                <el-table-column prop="display" label="填报志愿时显示" width="140"></el-table-column>
            </el-table>
            <br/>
            <h3>招生名额</h3>
            <el-table :data="spotData">
                <el-table-column prop="major" label="学科专业" width="140"></el-table-column>
                <el-table-column prop="spots" label="招生名额" width="140"></el-table-column>
            </el-table>
        </el-main>
    </div>
</template>

<script>
export default {
  name: 'MajorInfo',
  created () {
    this.getMajorInfo()
  },
  data () {
    return {
      majorData: [],
      spotData: [] // 招生名额
    }
  },
  methods: {
    calSpot () {
      for (let i = 0; i < this.majorData.length; i++) {
        let major = this.majorData[i]['major']
        if (this.spotData.hasOwnProperty(major)) {
          this.spotData[major] += 1
        } else {
          this.spotDate[major] = 0
        }
      }
    },
    getMajorInfo () {
      // 获取已有的专业目录
      this.$axios.get('/admin/majors')
        .then(resp => {
          // todo: 存疑
          this.majorData = resp.data.data
          this.calSpot()
        })
        .catch(error => {
          console.log(error)
        })
    },
    updateMajorInfo (e) {
      var formData = new FormData()
      formData.append('temp-file', e.target.files[0])
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('/admin/majors', formData, config)
        .then(resp => {
          this.$message.success(resp.data.message)
          this.getMajorInfo()
        })
        .catch(error => {
          console.log()
          this.$message.error('招生目录和名额文件导入失败')
        })
    }
  }
}
</script>

<style scoped>

</style>
