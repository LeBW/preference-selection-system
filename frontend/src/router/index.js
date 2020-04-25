import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import PreferenceSelect from "@/components/student/PreferenceSelect";
import Login from "@/components/student/Login";

Vue.use(Router)

export const router = new Router({
  routes: [
    {
      path: '/preference',
      name: 'Preference',
      component: PreferenceSelect,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    }
  ]
})

// 前端登录拦截
router.beforeEach(function (to, from, next) {
  if (to.matched.some(record => record.meta.requireAuth)) {
    if (store.state.token) {
      next()
    } else {
      next({
        path: '/login',
        query: {redirect: to.fullPath} // 登录成功之后重新跳转到该路由
      })
    }
  }
})
