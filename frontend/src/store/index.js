import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || null,
  },
  mutations: {
    login (state, data) {
      localStorage.setItem('token', data.token)
      state.token = data.token
    },
    logout (state) {
      // 移除token
      localStorage.removeItem('token')
      state.token = null
    }
  },
  actions: {
  }
})
