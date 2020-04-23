<template>
    <div>

    </div>
</template>

<script>
    export default {
        name: "MajorInfo",
        created() {
            // 获取已有的专业目录
            this.$axios.get()
                .then(resp => {

                })
                .catch(error => {
                    console.log(error)
                });
        },
        data() {

        }
    }
</script>

<style scoped>

</style>
