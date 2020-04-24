<template>
    <div>
        <el-main>
            <el-form-item prop="file" label="考生信息添加（仅支持excel表）">
                <input type="file" accept="application/vnd.ms-excel" @change="updateStudentInfo($event)"/>
            </el-form-item>
            <h3>考生信息</h3>
            <el-table :data="studentData">
                <el-table-column prop="ticket-number" label="准考证号" width="140"></el-table-column>
                <el-table-column prop="name" label="考生姓名" width="140"></el-table-column>
                <el-table-column prop="degree-type" label="学位类型" width="140"></el-table-column>
                <el-table-column prop="department" label="学位类型" width="140"></el-table-column>
                <el-table-column prop="major" label="报考专业" width="140"></el-table-column>
                <el-table-column prop="direction" label="研究方向" width="140"></el-table-column>
                <el-table-column prop="id-number" label="证件号码" width="140"></el-table-column>
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
        name: "StudentInfo",
        created() {
            this.getStudentInfo()
            this.totalItems = this.studentData.length
            if(this.totalItems > this.pageSize){
                for(let index = 0; index < this.pageSize; index++){
                    this.studentDataEnd.push(this.studentData[index])
                }
            } else{
                tihs.studentDataEnd = this.studentData
            }
        },
        data() {
            return{
                studentData: [],
                studentDataEnd: [],
                currentPage: 1,
                pageSize: 2,
                totalItems: 0
            }
        },
        methods: {
            getStudentInfo() {
                this.$axios.get('/admin/students')
                    .then(resp => {
                        this.studentData = resp.data.date
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            handleSizeChange(val) {
                console.log('每页${val}条')
                this.pageSize = val
                this.handleCurrentChange(this.currentPage)
            },
            handleCurrentChange(val) {
                console.log('当前页：${val}')
                this.currentPage = val
                this.currentChangePage(this.studentData)
            },
            currentChangePage(list){
                let from = (this.currentPage - 1) * this.pageSize
                let to = this.currentPage * this.pageSize
                this.studentDataEnd = [];
                for(; from < to; from++){
                    if(list[from]){
                        this.studentDataEnd.push(list[from])
                    }
                }
            },
            updateStudentInfo(e){
                var formData = new FormData()
                formData.append('temp-file', e.target.files[0])
                let config = {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
                this.$axios.post('/admin/students', formData, config)
                    .then(resp => {
                        this.$message.success(resp.data.message)
                        this.getStudentInfo()
                    })
                    .catch(error => {
                        console.log()
                        this.$message.error('考生信息添加失败')
                    })
            }
        }
    }
</script>

<style scoped>

</style>
