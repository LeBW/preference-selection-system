import Vue from 'vue'
import Router from 'vue-router'
import store from '../store'
import PreferenceSelect from '@/components/student/PreferenceSelect'
import Login from '@/components/student/Login'

Vue.use(Router)

export const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect: '/preference',
      component: PreferenceSelect
    },
    {
      path: '/preference',
      name: 'Preference',
      component: PreferenceSelect,
      meta: {
        requireAuth: false
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
    // 如果需要认证
    if (store.state.token) {
      // 有token
      next()
    } else {
      // 没token
      next({
        path: '/login',
        query: {redirect: to.fullPath} // 登录成功之后重新跳转到该路由
      })
    }
  } else {
    next()
  }
})
