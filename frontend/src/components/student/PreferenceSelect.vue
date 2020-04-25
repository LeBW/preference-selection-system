<template>
    <div>
        <h2>2020年复旦大学计算机科学技术学院研究生复试志愿填报</h2>
        <div class="base_info">
            <p>准考证号：{{student['ticket-number']}}</p>
            <p>考生姓名：{{student['name']}}</p>
            <p>报考学位类型：{{student['degree-type']}}</p>
            <p>报考专业：{{student['major']}}</p>
        </div>

        <p v-if="student['last-modify-time']">上次修改时间：{{student['last-modify-time']}}</p>

        <br>
        <h3>请选择第一志愿</h3>
        <p v-if="student['degree-type'] === '学术学位'">{{student['major']}}</p>
        <el-select v-else v-model="secondChoice" placeholder="第一志愿" @change="changeFirstChoice">
            <el-option v-for="item in profMajor"
                       :key="item.display"
                       :label="item.display"
                       :value="item.display"
            ></el-option>
        </el-select>
        <br/>

        <h3>请选择第二志愿</h3>
        <el-select v-if="student['degree-type'] === '学术学位'" v-model="firstChoice" placeholder="第二志愿" @change="changeFirstChoice">
            <el-option v-for="item in researchMajor"
                       :key="item.display"
                       :label="item.display"
                       :value="item.display"
            ></el-option>
        </el-select>
        <el-select e-else v-model="secondChoice" placeholder="第二志愿" @change="changeSecChoice">
            <el-option v-for="item in profMajor"
                       :key="item.display"
                       :label="item.display"
                       :value="item.display"
            ></el-option>
        </el-select>
        <br/>

        <el-checkbox v-model="student['adjust-major']">愿意调剂到其他学科方向</el-checkbox>
        <el-checkbox v-model="student['adjust-degree-type']" v-if="student['degree-type'] === '学术学位'">愿意调剂为专业学位硕士</el-checkbox>

        <br/>
        <el-form-item style="width: 100%">
            <el-button type="primary"
                       style="width: 40%; background: #afb4db; border: none"
                       v-on:click="submitInfo">提交</el-button>
        </el-form-item>
        <br/>

        <h3>相关文件列表</h3>
        <el-table :data="filesInfo">
            <el-table-column prop="name" label="文件名" width="140"></el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            @click="getFileById(scope.$index, scope.row)">下载</el-button>
                </template>
            </el-table-column>
        </el-table>
        <br/>

        <h3>名额与填报志愿人数</h3>
        <el-button size="mini" @click="getChoicesOverview">刷新</el-button>
        <el-table :data="choiceInfo">
            <el-table-column v-if="student['degree-type'] === '学术学位'" prop="major" label="专业"></el-table-column>
            <el-table-column v-else prop="major" label="学科方向"></el-table-column>
            <el-table-column prop="spots"></el-table-column>
            <el-table-column prop="first-choice-major-numbers"></el-table-column>
        </el-table>
    </div>
</template>

<script>
export default {
  name: 'PreferenceSelect',
  data () {
    return {
      student: {},
      firstChoice: '',
      secondChoice: '',
      choiceOverview: [], // 名额与填报信息
      majorInfo: [], // 全部专业信息
      filesInfo: [], // 文件信息
      choiceInfo: [], // 志愿信息
      researchMajor: [], // 学硕专业信息
      profMajor: [], // 专硕专业信息
      modifiedForm: {} // 提交的信息
    }
  },
  created () {
    this.student = this.$store.state.curStudent
    this.getMajorsInfo()
    this.getFilesInfo()
    this.getChoicesOverview()
  },
  methods: {
    getPersonInfo () {
      this.$axios.get('/student/info')
        .then(resp => {
          this.$store.commit('setCurStudent', resp.data)
        })
        .catch(error => {
          console.log(error)
        })
    },
    getChoicesOverview () {
      // 获取志愿填报状况
      this.$axios.get('/student/choices-overview')
        .then(resp => {
          this.choiceInfo = resp.data.data
        })
        .catch(error => {
          console.log(error)
          this.$message.alert('刷新失败！')
        })
    },
    getFilesInfo () {
      // 获取文件列表
      this.$axios.get('/student/files')
        .then(resp => {
          this.filesInfo = resp.data.date
        })
        .catch(error => {
          console.log(error)
        })
    },
    getFileById (index, row) {
      // 下载相关文件
      this.$axios.get('/student/files/' + row['id'])
        .then(resp => {
          // 获得的是文件流
          this.downloadFile(resp.data.content, resp.data.name)
        })
        .catch(error => {
          console.log(error)
        })
    },
    downloadFile (data, name) {
      // 文件数据打包
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
    getMajorsInfo () {
      // 获取已有的专业目录
      this.$axios.get('/student/majors')
        .then(resp => {
          // todo: 存疑
          this.majorInfo = resp.data.data

          // 专硕和学硕信息分开
          for (let i = 0; i < this.majorInfo.length; i++) {
            if (this.majorInfo[i]['degree-type'] === '学术学位') {
              this.researchMajor.push(this.majorInfo[i])
            } else {
              this.profMajor.push(this.majorInfo[i])
            }
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    submitInfo () {
      // 为学硕补上第一志愿信息
      if (this.student['degree-type'] === '学术学位') {
        this.modifiedForm['first-choice-major'] = this.student['first-choice-major']
        this.modifiedForm['first-choice-direction'] = this.student['first-choice-direction']
      }
      // 提交志愿填报信息
      this.modifiedForm['adjust-major'] = this.student['adjust-major']
      // 专硕默认无法调剂
      this.modifiedForm['adjust-degree-type'] = this.student['adjust-degree-type']
      this.$axios.post('student/personal-info', this.modifiedForm)
        .then(resp => {
          this.$message.success('提交成功')
          // 刷新个人信息
          this.getPersonInfo()
        })
        .catch(error => {
          console.log(error)
          this.$message.error('提交失败')
        })
    },
    changeFirstChoice (val) {
      // 只有专硕可以改变第一志愿
      let firstMajor = val.split('-')[0].trim()
      if (firstMajor === this.secondChoice.split('-')[0].trim()) {
        this.$message.warning('第一志愿和第二志愿的学科方向应不同！请重新选择')
      } else {
        // 更新学生的信息
        this.modifiedForm['first-choice-major'] = firstMajor
        this.modifiedForm['first-choice-direction'] = val.split('-')[1].trim()
      }
    },
    changeSecChoice (val) {
      // 改变第二志愿
      let secMajor = val.split('-')[0].trim()

      if (this.student['degree-type'] === '专业学位') {
        if (secMajor === this.firstChoice.split('-')[0].trim()) {
          this.$message.warning('第一志愿和第二志愿的学科方向应不同！请重新选择')
        } else {
          // 更新学生的信息
          this.modifiedForm['second-choice-major'] = secMajor
          this.modifiedForm['second-choice-direction'] = val.split('-')[1].trim()
        }
      } else {
        // 学硕 不与报考专业相同
        if (secMajor === this.student['major']) {
          this.$message.warning('第一志愿和第二志愿的专业应不同！请重新选择')
        } else {
          // 更新学生的信息
          this.modifiedForm['second-choice-major'] = secMajor
          this.modifiedForm['second-choice-direction'] = val.split('-')[1].trim()
        }
      }
    }
  }
}
</script>

<style scoped>

</style>
