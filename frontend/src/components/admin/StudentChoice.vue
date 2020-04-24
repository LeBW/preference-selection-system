<template>
    <div>
        <el-form>
            <h3>学硕专硕志愿统计信息</h3>
            <el-button type="primary" icon="el-icon-download" @click="exportChoiceInfo($event)">考生志愿信息导出</el-button>
            <el-table :data="choicesData">
                <el-table-column prop="ticket-number" label="准考证号" width="140"></el-table-column>
                <el-table-column prop="name" label="考生姓名" width="140"></el-table-column>
                <el-table-column prop="degree-type" label="学位类型" width="140"></el-table-column>
                <el-table-column prop="department" label="学位类型" width="140"></el-table-column>
                <el-table-column prop="major" label="报考专业" width="140"></el-table-column>
                <el-table-column prop="direction" label="研究方向" width="140"></el-table-column>
                <el-table-column prop="id-number" label="证件号码" width="140"></el-table-column>
                <el-table-column prop="first-choice-major" label="第一志愿学科专业" width="140"></el-table-column>
                <el-table-column prop="first-choice-direction" label="第一志愿研究方向" width="140"></el-table-column>
                <el-table-column prop="second-choice-major" label="第二志愿学科专业" width="140"></el-table-column>
                <el-table-column prop="second-choice-direction" label="第二志愿研究方向" width="140"></el-table-column>
                <el-table-column prop="adjust-major" label="是否愿意调剂到其他学科方向" width="140"></el-table-column>
                <el-table-column prop="adjust-degree-type" label="是否愿意调剂为专业硕士" width="140"></el-table-column>
            </el-table>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[10, 20, 30, 40, 50]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="totalItems"></el-pagination>
        </el-form>
    </div>

</template>

<script>
    export default {
        name: "StudentChoice",
        data() {
            return {
                choicesData:[],
                choicesDataEnd: [],
                currentPage: 1,
                pageSize: 2,
                totalItems: 0
            }
        },
        created() {
            this.getChoiceInfo()
            this.totalItems = this.choicesData.length
            if(this.totalItems > this.pageSize){
                for(let index = 0; index < this.pageSize; index++){
                    this.choicesDataEnd.push(this.choicesData[index])
                }
            } else{
                this.choicesDataEnd = this.choicesData
            }
        },
        methods: {
            getChoiceInfo() {
                this.$axios.get('/admin/choices')
                    .then(resp => {
                        this.choicesData = resp.data.date
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            handleSizeChange() {
                console.log('每页${val}条')
                this.pageSize = val
                this.handleCurrentChange(this.currentPage)
            },
            handleCurrentChange() {
                console.log('当前页：${val}')
                this.currentPage = val
                this.currentChangePage(this.choicesData)
            },
            currentChangePage(list){
                let from = (this.currentPage - 1) * this.pageSize
                let to = this.currentPage * this.pageSize
                this.choicesDataEnd = [];
                for(; from < to; from++){
                    if(list[from]){
                        this.choicesDataEnd.push(list[from])
                    }
                }
            },
            exportChoiceInfo(e) {
                let config = {
                    responseType: 'blob'
                }
                this.$axios.get('/admin/export-choices', config)
                    .then(resp => {
                        // 获取的是文件流
                        console.log(resp)
                        this.downloadExcel(resp.data["temp-file"])
                    })
                    .catch(error => {
                        console.log(error)
                    })
            },
            downloadExcel(data){
                const blob = new Blob([data])
                const fileName = "考生志愿信息"
                if ('download' in document.createElement('a')){
                    // 非IE下载
                    const elink = document.createElement('a')
                    elink.download = fileName
                    elink.style.display = 'none'
                    elink.href = URL.createObjectURL(blob)
                    document.body.appendChild(elink)
                    elink.click()
                    URL.revokeObjectURL(elink.href) // 释放URL对象
                    document.body.removeChild(elink)
                }
                else {
                    // IE10+下载
                    navigator.msSaveBlob(blob, fileName)
                }
            }
        }
    }
</script>

<style scoped>

</style>
