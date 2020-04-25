import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || null,
    curStudent: localStorage.getItem('curStudent') || null
  },
  mutations: {
    login (state, data) {
      // 记录token
      localStorage.setItem('token', data)
      state.token = data.token
    },
    logout (state) {
      // 移除token
      localStorage.removeItem('token')
      state.token = null
    },
    setCurStudent (state, data) {
      // 记录当前student信息
      localStorage.setItem('curStudent', data)
      state.curStudent = data.student
    }
  },
  actions: {
  }
})
