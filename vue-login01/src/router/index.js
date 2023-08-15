import { createRouter, createWebHistory } from 'vue-router'

const index = () => import('./views/index.vue');
const login = () => import('./views/login.vue');
const register = () => import('./views/register.vue');

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'Index',
      component: index
    },
    {
      path: '/login',
      name: 'login',
      component: login
    },
    {
      path: '/register',
      name: 'register',
      component: register
    }

  ]
})
export default router

