import axios, { } from 'axios';
//定义一个拦截器
const request = axios.create({
    baseURL: '//http://localhost:8081/',
    timeout: 1000 * 60,
    headers: {
      'Content-Type': 'application/json;charset=UTF-8'
    }
  });
  
  //添加一个请求拦截器
  request.interceptors.request.use(
    config => {
      //从本地存储中获取Token
      const token = localStorage.getItem("token");
      console.log("发送请求")
      console.log(localStorage.getItem("token"))
      if (token) {
        //在请求头中携带Token
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    error => Promise.reject(error)
  );

 
  //发送请求
//   request.post('/users', {
//     name: 'John Doe',
//     age: 30
//   });