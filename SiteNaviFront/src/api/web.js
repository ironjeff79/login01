// import axios from 'axios';
// //定义一个拦截器
// const request = axios.create({
//     baseURL: '//http://localhost:8081/',
//     timeout: 5000,
//     headers: {
//       'Content-Type': 'application/json;charset=UTF-8'
//     }
//   });
  
  //添加一个请求拦截器
  // request.interceptors.request.use(
  //   config => {
  //     //从本地存储中获取Token
  //     const token = localStorage.getItem("token");
  //     console.log("发送请求")
  //     console.log(localStorage.getItem("token"))
  //     if (!config.url.endsWith('/login')) {
  //       //在请求头中携带Token
  //       config.headers.Token =  `Bearer ${token}`;
  //     }
  //     return config;
  //   },
    
  //   error => {
  //     Vue.prototype.$message.error('请求超时')
  //    return Promise.reject(error)}
  // );
// // 将创建好的 axios 实例挂载到 Vue 原型上
// // Vue.prototype.$interceptors = instance;
// // Vue.prototype.$http = axios
