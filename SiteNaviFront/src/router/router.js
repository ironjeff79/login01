import { createRouter, createWebHistory } from 'vue-router';


const login = () => import('@/views/login.vue')
const portal = () => import('@/views/portal.vue')
const register = () => import('@/views/register.vue')
const changePass = () => import('@/views/changePass.vue')
const changeInfo = () => import('@/views/changeInfo.vue')
const admin =() => import('@/admin.vue')
const usermanage = () => import('@/admin/usermanage.vue')
const webmanage = () => import('@/admin/webmanage.vue')

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
      path: '/changePass/',
      name: 'changePass',
      component: changePass,
      query:undefined
    },
    {
      path: '/changeInfo/',
      name: 'changeInfo',
      component: changeInfo,
      query:undefined
    },
    {
      path: '/admin',
      name: 'admin',
      component: admin,
      children: [
        { path: 'usermanage', component: usermanage },
        { path: 'webmanage', component: webmanage },
    //     { path: 'commentmanage', component: commentmanage },
      ]
    }
  ]
})
export default router

