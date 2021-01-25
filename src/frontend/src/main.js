import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './vuex'
import {BootstrapVue, BootstrapVueIcons } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueCookie from 'vue-cookie'
import { Viewer } from '@toast-ui/vue-editor'
import naver from 'vue-naver-maps'

Vue.use(BootstrapVue)
Vue.use(BootstrapVueIcons)
Vue.use(VueCookie)
Vue.component('viewer', Viewer)
Vue.config.productionTip = false
Vue.use(naver, {
  clientID: '6dwlecs3gq',
  useGovAPI: false
})

new Vue({
  store,
  router,
  beforeCreate() {
    // let token = store.state.login.isAuthenticated

    // if (token) { // 세션스토리지에 토큰이 있으면 새로고침할 때 유저 정보를 불러옴
    //   store.dispatch('getUserInfo')
    // }
  },
  render: h => h(App),
}).$mount('#app')
