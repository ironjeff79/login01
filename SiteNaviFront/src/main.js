import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'


// import { API_BASE_URL } from './config';
// Vue.prototype.global = API_BASE_URL;



/* 引入 ElementPlus */
import ElementPlus from 'element-plus'

import 'element-plus/dist/index.css'

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
