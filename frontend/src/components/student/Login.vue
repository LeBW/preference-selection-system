<template>
    <div id="base_login">
        <el-form :model="loginForm"
                 :rules="rules"
                 class="login_container"
                 label-position="left"
                 label-width="0px"
                 v-loading="loading">
            <h3 class="login_title">登录</h3>
            <el-form-item prop="ticketNumber">
                <el-input type="text"
                          v-model="loginForm.ticketNumber"
                          auto-complete="off"
                          placeholder="准考证号"></el-input>
            </el-form-item>
            <el-form-item prop="name">
                <el-input type="text"
                          v-model="loginForm.name"
                          auto-complete="off"
                          placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item prop="idNumber">
                <el-input type="text"
                          v-model="loginForm.idNumber"
                          auto-complete="off"
                          placeholder="证件号码"></el-input>
            </el-form-item>
            <el-form-item style="width: 100%">
                <el-button type="primary"
                           style="width: 40%; background: #afb4db; border: none; height: 40%"
                           v-on:click="login">登录</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      loginForm: {
        ticketNumber: '',
        name: '',
        idNumber: ''
      },
      rules: {
        ticketNumber: [{required: true, message: '请输入准考证号！', trigger: 'blur'}],
        name: [{required: true, message: '请输入姓名！', trigger: 'blur'}],
        idNumber: [{required: true, message: '请输入证件证号！', trigger: 'blur'}]
      },
      loading: false
    }
  },
  methods: {
    login () {
      this.$axios.post('/student/login', {
        'ticket-number': this.loginForm.ticketNumber,
        name: this.loginForm.name,
        'id-number': this.loginForm.idNumber
      })
        .then(resp => {
          if (resp.status === 200) {
            this.$store.commit('login', resp.data.token)
            this.$store.commit('setCurStudent', resp.data.student)
            // 跳转
            this.$router.replace({path: '/'})
          } else {
            this.$message.error('登录失败')
          }
        })
        .catch(error => {
          console.log(error)
          this.$message.error('登录失败')
        })
    }
  }
}
</script>

<style scoped>
    #base_login{
        background-image: url("~img/xueyuan-jxl.jpg");
        background-size: cover;
        background-position: center;
        height: 100%;
        width: 100%;
        background-size: cover;
        position: fixed;
    }
    body{
        margin: 0px;
        padding: 0px;
    }
    .login_container{
        border-radius: 15px;
        background-clip: padding-box;
        margin: 90px auto;
        width: 50%;
        height: 60%;
        padding: 35px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        box-shadow: 0 0 25px #cac6c6;
    }
    .login_title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #494e8f;
    }
</style>
