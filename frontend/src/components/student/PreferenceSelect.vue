<template>
    <el-main>
      <el-row>
        <h2>2020年复旦大学计算机科学技术学院和软件学院全国优秀大学生夏令营复试志愿填报</h2>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div class="base_info">
            <p>报名号：{{student['ticket-number']}}</p>
            <p>考生姓名：{{student['name']}}</p>
            <p>报考一级学科：{{student['degree-type']}}</p>
          </div>
        </el-col>

        <el-col :span="12">
          <div>
            <span style="padding: 5%; font-size: 1.17em; margin-block-start: 1em; margin-block-end: 1em; margin-inline-start: 0px;margin-inline-end: 0px;font-weight: bold;">名额与填报志愿人数</span>
            <el-button type="primary" style="background: #afb4db; border: none" size="mini" @click="getChoicesOverview">刷新</el-button>
            <el-table :data="choiceInfo">
              <el-table-column prop="major" label="学科方向"></el-table-column>
              <el-table-column label="直博生">
                <el-table-column prop="phd-spots" label="名额"></el-table-column>
                <el-table-column prop="phd-first-choice-major-numbers" label="第一志愿人数"></el-table-column>
              </el-table-column>
              <el-table-column label="学术学位硕士生">
                <el-table-column prop="research-spots" label="名额"></el-table-column>
                <el-table-column prop="research-first-choice-major-numbers" label="第一志愿人数"></el-table-column>
              </el-table-column>
              <el-table-column label="专业学位硕士生">
                <el-table-column prop="prof-spots" label="名额"></el-table-column>
                <el-table-column prop="prof-first-choice-major-numbers" label="第一志愿人数"></el-table-column>
              </el-table-column>
            </el-table>
          </div>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <div style="padding-left: 10px">
            <h3>相关文件列表</h3>
            <el-table :data="filesInfo">
              <el-table-column prop="name" label="文件名"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <el-button
                    style="background: #afb4db; border: none"
                    size="mini"
                    @click="getFileById(scope.$index, scope.row)">下载</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-col>

        <el-col :span="12">
          <div>
            <h3>请选择第一志愿</h3>
            <el-select v-model="firstChoice" placeholder="第一志愿" @change="changeFirstChoice">
              <el-option v-for="item in majorOne"
                         :key="item.display"
                         :label="item.display"
                         :value="item.display"
              ></el-option>
            </el-select>
            <br/>

            <h3>请选择第二志愿</h3>
            <el-select v-model="secondChoice" placeholder="第二志愿" @change="changeSecChoice">
              <el-option v-for="item in majorTwo"
                         :key="item.display"
                         :label="item.display"
                         :value="item.display"
              ></el-option>
            </el-select>
            <br/>

            <div>
              <el-checkbox v-model="student['adjust-degree-type']">愿意调剂同一志愿学科方向的其他学位类型（直博生、学术学位硕士生、专业学位硕士生）</el-checkbox>
              <el-checkbox v-model="student['adjust-major']">愿意调剂到其他学科方向</el-checkbox>
            </div>

            <br/>

            <el-button type="primary"
                       style="background: #afb4db; border: none"
                       v-on:click="submitInfo">提交</el-button>
          </div>
        </el-col>
      </el-row>

      <el-row>
        <div v-if="student['last-modify-time']">
          <p v-if="student['first-choice-direction'] !== null && student['first-choice-direction'] !== ''">当前提交第一志愿：{{student['first-choice-major']}} - {{student['first-choice-direction']}}</p>
          <p v-else>当前提交第一志愿：{{student['first-choice-major']}}</p>

          <p v-if="student['second-choice-direction'] !== null && student['second-choice-direction'] !== ''">当前提交第二志愿：{{student['second-choice-major']}} - {{student['second-choice-direction']}}</p>
          <p v-else>当前提交第二志愿：{{student['second-choice-major']}}</p>

          <p v-if="adjustMajor === true">愿意调剂到其他学科方向</p>
          <p v-else style="color: firebrick">不愿意调剂到其他学科方向</p>
          <p v-if="adjustDegreeType === true">愿意调剂同一志愿学科方向的其他学位类型（直博生、学术学位硕士生、专业学位硕士生）</p>
          <p v-else-if="adjustDegreeType === false" style="color: firebrick">不愿意调剂同一志愿学科方向的其他学位类型（直博生、学术学位硕士生、专业学位硕士生）</p>

          <p>当前志愿提交时间：{{student['last-modify-time']}}</p>
        </div>
      </el-row>

      <br/>
      <br/>
    </el-main>
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
      majorOne: [], // 第一志愿信息
      majorTwo: [], // 第二志愿信息
      modifiedForm: {}, // 提交的信息
      adjustDegreeType: '', // 上次提交的是否愿意调剂为其他学位类型
      adjustMajor: '' // 上次提交的是否愿意调剂学科方向
    }
  },
  created () {
    this.student = JSON.parse(this.$store.state.curStudent)
    this.adjustDegreeType = this.student['adjust-degree-type']
    this.adjustMajor = this.student['adjust-major']

    this.modifiedForm['first-choice-major'] = this.student['first-choice-major']
    this.modifiedForm['second-choice-major'] = this.student['second-choice-major']

    this.modifiedForm['first-choice-direction'] = this.student['first-choice-direction']
    this.modifiedForm['second-choice-direction'] = this.student['second-choice-direction']

    if (this.student['last-modify-time'] !== '') {
      if (this.student['first-choice-direction'] !== '' && this.student['first-choice-direction'] !== null) {
        this.firstChoice = this.student['first-choice-major'] + '－' + this.student['first-choice-direction']
      } else {
        this.firstChoice = this.student['first-choice-major']
      }

      if (this.student['second-choice-direction'] !== '' && this.student['second-choice-direction'] !== null) {
        this.secondChoice = this.student['second-choice-major'] + '－' + this.student['second-choice-direction']
      } else {
        this.secondChoice = this.student['second-choice-major']
      }
    }

    this.getMajorsInfo()
    this.getFilesInfo()
    this.getChoicesOverview()
  },
  methods: {
    getPersonInfo () {
      this.$axios.get('/student/info')
        .then(resp => {
          this.$store.commit('setCurStudent', resp.data)
          this.student = Object.assign({}, this.student, JSON.parse(this.$store.state.curStudent))
          this.adjustDegreeType = this.student['adjust-degree-type']
          this.adjustMajor = this.student['adjust-major']
        })
        .catch(error => {
          console.log(error)
        })
    },
    getChoicesOverview () {
      // 获取志愿填报状况
      this.$axios.get('/student/choices-overview')
        .then(resp => {
          this.choiceInfo = resp.data
          for (let i = 0; i < this.choiceInfo.length; i++) {
            if (this.choiceInfo[i]['major'] === '直博生') {
              this.choiceInfo[i]['phd-first-choice-major-numbers'] = this.choiceInfo[i]['first-choice-major-numbers']
              this.choiceInfo[i]['phd-spots'] = this.choiceInfo[i]['spots']
            } else if (this.choiceInfo[i]['major'].contains('学术')) {
              this.choiceInfo[i]['research-spots'] = this.choiceInfo[i]['spots']
            } else if (this.choiceInfo[i]['major'].contains('专业')) {
              this.choiceInfo[i]['prof-spots'] = this.choiceInfo[i]['spots']
            }
          }
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
          this.filesInfo = resp.data
        })
        .catch(error => {
          console.log(error)
        })
    },
    getFileById (index, row) {
      // 下载相关文件
      this.$axios.get('/student/files/' + row['id'], {
        headers: {
          'Content-Type': 'application/octet-stream'
        },
        responseType: 'blob'
      })
        .then(resp => {
          // 获得的是文件流
          this.downloadFile(resp.data, row['name'])
        })
        .catch(error => {
          console.log(error)
        })
    },
    downloadFile (data, name) {
      // 文件数据打包
      const blob = new Blob([data], {type: 'application/pdf'})
      // const blob = data
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
      this.$axios.get('/student/major')
        .then(resp => {
          console.log(resp)
          // 后端确认返回给我的只会是对应一级学科的专业目录
          this.majorInfo = resp.data

          for (let i = 0; i < this.majorInfo.length; i++) {
            this.majorOne.push(this.majorInfo[i])
            this.majorTwo.push(this.majorInfo[i])
          }
        })
        .catch(error => {
          console.log(error)
        })
    },
    submitInfo () {
      this.modifiedForm['first-choice-major'] = this.student['first-choice-major'] // 学科方向
      this.modifiedForm['first-choice-direction'] = this.student['first-choice-direction'] // 学位类型

      if (this.modifiedForm['first-choice-major'] === this.modifiedForm['second-choice-major']) {
        this.$message.error('第一志愿和第二志愿的学科方向应不同！请重新选择！')
      }

      // 提交志愿填报信息
      this.modifiedForm['adjust-major'] = this.student['adjust-major']
      // 专硕默认无法调剂
      this.modifiedForm['adjust-degree-type'] = this.student['adjust-degree-type']
      this.$axios.post('/student/choice', this.modifiedForm)
        .then(resp => {
          this.$message.success('提交成功')
          // 刷新个人信息
          this.getPersonInfo()
          this.getChoicesOverview()
        })
        .catch(error => {
          console.log(error)
          this.$message.error('提交失败')
        })
    },
    changeFirstChoice (val) {
      let firstMajor = val.split('－')[0].trim()
      // 修改第二志愿列表，第二志愿强制重新选择
      this.majorTwo = []
      for (let i = 0; i < this.majorInfo.length; i++) {
        if (this.majorInfo[i]['major'] !== firstMajor) {
          this.majorTwo.push(this.majorInfo[i])
        }
      }
      if (this.secondChoice !== null && firstMajor === this.secondChoice.split('－')[0].trim()) {
        this.$message.warning('第一志愿和第二志愿的学科方向应不同！请重新选择！')
      }
      // 更新学生的信息
      this.modifiedForm['first-choice-major'] = firstMajor
      if (val.split('－').length === 1) {
        this.modifiedForm['first-choice-direction'] = ''
      } else {
        this.modifiedForm['first-choice-direction'] = val.split('－')[1].trim()
      }
    },
    changeSecChoice (val) {
      // 改变第二志愿
      let secMajor = val.split('－')[0].trim()
      this.majorOne = []

      for (let i = 0; i < this.majorInfo.length; i++) {
        if (this.majorInfo[i]['major'] !== secMajor) {
          this.majorOne.push(this.majorInfo[i])
        }
      }

      if (this.firstChoice !== null && secMajor === this.firstChoice.split('－')[0].trim()) {
        this.$message.warning('第一志愿和第二志愿的学科方向应不同！请重新选择！')
      } else {
        // 更新学生的信息
        this.modifiedForm['second-choice-major'] = secMajor

        if (val.split('－').length === 1) {
          this.modifiedForm['second-choice-direction'] = ''
        } else {
          this.modifiedForm['second-choice-direction'] = val.split('－')[1].trim()
        }
      }
    }
  }
}
</script>

<style scoped>
</style>
