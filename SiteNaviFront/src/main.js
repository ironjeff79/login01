import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import * as echarts from "echarts"
import axios, { } from 'axios';

// import { API_BASE_URL } from './config';
// Vue.prototype.global = API_BASE_URL;


/* 引入 ElementPlus */
import ElementPlus from 'element-plus'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import 'element-plus/dist/index.css'
import './assets/css/fonts/linecons/css/linecons.css'
import './assets/css/fonts/fontawesome/css/font-awesome.min.css'
import './assets/css/bootstrap.css'
import './assets/css/xenon-core.css'
import './assets/css/xenon-components.css'
import './assets/css/xenon-skins.css'
import './assets/css/nav.css'

const app = createApp(App);

app.use(router)
app.use(echarts)
app.use(ElementPlus)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }
  
// app.component('location', Location)
// app.component('iconMenu', IconMenu)
// app.component('arrowRight', ArrowRight)
// app.component('search', Search)
// app.config.globalProperties.$message = ElMessage

app.config.globalProperties.$http ='http://localhost:8080'
app.mount('#app')  


axios.interceptors.request.use(
  config => {
    //从本地存储中获取Token
    const token = localStorage.getItem('Token');
    console.log("发送请求！！！")
    console.log(localStorage.getItem('Token'))
    if (!config.url.endsWith('/login')) {
      //在请求头中携带Token
      config.headers.Token =  `Bearer ${token}`;
    }
    return config;
  },
  
  error => {
    Vue.prototype.$message.error('请求超时')
   return Promise.reject(error)}
);