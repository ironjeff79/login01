<template>
    <div class="layout">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" style="max-width: 400px">
        <el-form-item label="ユーザー名" prop="userId">
          <span style="color:rgb(255, 255, 255);display: inline;">*</span>
          <el-input style="display: inline;width:280px" type="text" v-model="ruleForm.userId"  :disabled = "true" clearable @keyup.enter="registerForm">
            </el-input>
        </el-form-item>
        
        <el-form-item label="パスワード" prop="pass">
          <span style="color:red;display: inline;">*</span>
          <el-input style="display: inline;width:280px" type="password" v-model="ruleForm.pass" autocomplete="off"
            show-password @keyup.enter="registerForm"></el-input>
        </el-form-item>
        <el-form-item label="再入力" prop="checkPass">
          <span style="color:red;display: inline;">*</span>
          <el-input style="display: inline;width:280px" type="password" v-model="ruleForm.checkPass" autocomplete="off"
            show-password @keyup.enter="registerForm"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="registerForm('ruleForm')">変更</el-button>
          <el-button type="info" @click="backForm('ruleForm')">戻る</el-button>
        </el-form-item>
      </el-form>
    </div>
  </template>
  
  <script>
  import axios, {} from 'axios';
  import { ElMessage } from 'element-plus';
  
  export default {
    props: [
    ],
    data() {
      return {
        userId: '',
        data2: {},
        ruleForm: {
          pass: '',
          checkPass: '',
          userId: '',
        },
      rules: {
      }
      };
    },
  
    methods: {
      validForm() {
        if (!this.ruleForm.userId ) {
        }
        else if (5 > (this.ruleForm.userId.trim().length) || (this.ruleForm.userId.trim().length) > 10) {
          ElMessage.warning('ユーザー名は5桁から10桁の間に入力してください');
          return false;
        } else {
        }
        if (this.ruleForm.pass === '') {
          ElMessage.warning('パスワードを入力してください');
          return false;
        } else {
          if (this.ruleForm.pass !== '' && this.ruleForm.pass.length < 5 || (this.ruleForm.pass.length) > 10) {
            ElMessage.warning('パスワードを5桁から10桁の間に入力してください');
            return false;
          }
        }
        if (this.ruleForm.checkPass === '') {
          ElMessage.warning('もう一回入力してください');
          return false;
        } else if (this.ruleForm.checkPass !== this.ruleForm.pass) {
          ElMessage.warning('パスワード入力が不一致!');
          return false;
        } else {
        }
        return true;
      },
      registerForm() {
        if (this.validForm() == true) {
          this.data2 = {
            userId : this.ruleForm.userId,
            password: this.ruleForm.pass,
            phoneNum: this.ruleForm.phoneNum,
            mail:this.ruleForm.mail
          };
          axios({
            method: 'post',
            url: 'http://localhost:8080/changePass',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            data: JSON.stringify(this.data2)
          })
            .then((response) => {
              var data3 = response.data;
              if (data3.code == "success") {
                this.$router.push({ path: '/' });
                alert(data3.msg);
              }
              else if (data3.code == "warning") {
                alert(data3.msg);
              }
            })
            .catch(function (error) { // 请求失败处理
              console.log(error);
            });
        }
      },
      backForm() {
        this.$router.back();
    }
    },
    created() {
      this.ruleForm.mail = this.$route.query.mail;
    this.ruleForm.userId = this.$route.query.userId;
    this.ruleForm.phoneNum = this.$route.query.phoneNum;
    }
  }
  </script>
  
  <style scoped>
  .layout {
    position: absolute;
    left: calc(50% - 200px);
    top: 20%;
    width: 400px;
  }
  </style>