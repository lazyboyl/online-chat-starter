import Vue from 'vue'
import Router from 'vue-router'
import ChatMain from '@/components/ChatMain'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'ChatMain',
      component: ChatMain
    }
  ]
})
