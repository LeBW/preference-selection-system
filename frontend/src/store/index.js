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
      state.token = data
    },
    logout (state) {
      // 移除token
      localStorage.removeItem('token')
      localStorage.removeItem('curStudent')
      state.token = null
      state.curStudent = null
    },
    setCurStudent (state, data) {
      // 记录当前student信息
      localStorage.setItem('curStudent', JSON.stringify(data))
      state.curStudent = data
    }
  },
  actions: {
  }
})
