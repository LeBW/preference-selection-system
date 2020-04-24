<template>
    <div>
        <h2>2020年复旦大学计算机科学技术学院研究生复试志愿填报</h2>
        <div class="base_info">

        </div>
        <h3>请选择第一志愿</h3>

        <h3>请选择第二志愿</h3>


        <el-form-item style="width: 100%">
            <el-button type="primary"
                       style="width: 40%; background: #afb4db; border: none"
                       v-on:click="submitInfo">提交</el-button>
        </el-form-item>
        <br/>
        <h3>相关文件列表</h3>
        <el-table></el-table>
        <h3>名额与填报志愿人数</h3>
        <el-table></el-table>
    </div>
</template>

<script>
    export default {
        name: "PreferenceSelect",
        data() {
            return {
                student: {},
                choicesInfo: [],
                majorInfo: []
            }
        },
        created() {
            this.student = this.$store.state.curStudent
            this.getMajorsInfo()
        },
        methods: {
            getFiles() {
                // 获取系统中的相关文件
            },
            getFileById(){
                // 下载相关文件
            },
            getMajorsInfo(){
                // 获取已有的专业目录
                this.$axios.get('/student/majors')
                    .then(resp => {
                        // todo: 存疑
                        this.majorInfo = resp.data.data
                    })
                    .catch(error => {
                        console.log(error)
                    });
            },
            submitInfo(){
                this.$axios.post()
                    .then(resp => {
                        this.$message.success("提交成功")
                    })
                    .catch(error => {
                        this.$message.error("提交失败")
                    })
            }
        }
    }
</script>

<style scoped>

</style>
