import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'


// import { API_BASE_URL } from './config';
// Vue.prototype.global = API_BASE_URL;



/* 引入 ElementPlus */
import ElementPlus from 'element-plus'

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
app.use(ElementPlus)


// app.component('location', Location)
// app.component('iconMenu', IconMenu)
// app.component('arrowRight', ArrowRight)
// app.component('search', Search)
// app.config.globalProperties.$message = ElMessage

app.config.globalProperties.$http ='http://localhost:8080'
app.mount('#app')  
