import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      component: resolve => require(['../components/user/Login.vue'], resolve)
    },
    {
      path: '/home',
      component: resolve => require(['../components/Home.vue'], resolve),
      children: [
        {
          path: '/',
          component: resolve => require(['../components/Index.vue'], resolve)
        },
        {
          path: '/users',
          component: resolve => require(['../components/user/Users.vue'], resolve)
        },
        {
          path: '/user/borrow',
          component: resolve => require(['../components/user/UserBorrow.vue'], resolve)
        },
        {
          path: '/user/log',
          component: resolve => require(['../components/user/Booklog.vue'], resolve)
        }
        , {
          path: '/book/list',
          component: resolve => require(['../components/bookmanage/Index.vue'], resolve)
        },
        {
          path: '/book/manage',
          component: resolve => require(['../components/bookmanage/Manage.vue'], resolve)
        },
        {
          path: '/borrow/log',
          component: resolve => require(['../components/borrow/Index.vue'], resolve)
        }

      ]
    }

  ]
})