import { createRouter, createWebHistory } from 'vue-router'

const login = () => import('@/views/login.vue')
const portal = () => import('@/views/portal.vue')
const register = () => import('@/views/register.vue')
const changePass = () => import('@/views/changePass.vue')

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'login',
      component: login
    },
    {
      path: '/login',
      name: 'portal',
      component: portal
    },
    {
      path: '/register',
      name: 'register',
      component: register
    },{
      path: '/changePass',
      name: 'changePass',
      component: changePass
    }

  ]
})
export default router

