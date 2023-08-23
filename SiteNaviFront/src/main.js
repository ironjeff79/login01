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
app.mount('#app')
app.use(ElButton)
app.use(ElForm)
app.use(ElFormItem)
app.use(ElInput)
app.use(ElRow)
app.use(ElContainer)
app.use(ElHeader)
app.use(ElAside)
app.use(ElMain)
app.use(ElMenu)
app.use(ElSubmenu)
app.use(ElMenuItem)
app.use(ElIcon)
app.use(ElBreadcrumb)
app.use(ElBreadcrumbItem)
app.use(ElCard)
app.use(ElCol)
app.use(ElTable)
app.use(ElTableColumn)
app.use(ElSwitch)
app.component('location', Location)
app.component('iconMenu', IconMenu)
app.component('arrowRight', ArrowRight)
app.component('search', Search)
app.config.globalProperties.$message = ElMessage