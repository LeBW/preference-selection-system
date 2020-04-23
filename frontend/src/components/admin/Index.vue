<template>
    <el-container style="height: 100%;">
        <el-header width="100%" style="z-index: 99; padding-left: 15px;">
            <div class="left">
                <h2 style="margin: 0; font-size: 16px; width: 54px">admin</h2>
            </div>
            <div class="right">
                <div class="login">
                    <span class="txt"
                          v-on:click="quit">退出系统</span>
                </div>
            </div>
        </el-header>
        <div>
            <el-menu :default-active="$route.path" class="el-menu-vertical-demo" unique-opened router
                     style="width: 200px;height: 100%;background-color: #fafbfc">
                <el-menu-item index="/MajorInfo"><i class="el-icon-user icon"></i><span>专业目录</span></el-menu-item>
                <el-menu-item index="/StudentInfo"><i class="el-icon-files icon"></i><span>考生信息</span></el-menu-item>
                <el-menu-item index="/StudentChoice"><i class="el-icon-document icon"></i><span>考生志愿</span></el-menu-item>
                <el-menu-item index="/Setting"><i class="el-icon-message icon"></i><span>系统设置</span></el-menu-item>
            </el-menu>
        </div>
        <router-view></router-view>
    </el-container>
</template>

<script>
export default {
  name: 'Index',
  data () {

  },
  methods: {
      quit() {
          this.$confirm('', '确定要退出系统吗？', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              center: 'true',
              confirmButtonClass: 'messageButton',
              cancelButtonClass: 'messageButton'
          }).then(() => {
              this.$store.commit('logout');
              this.$router.replace('/admin/login');
          }).catch(() => {
          });
      }
  }
}
</script>

<style scoped>

</style>
