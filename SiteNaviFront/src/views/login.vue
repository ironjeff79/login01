<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="190px" class="demo-ruleForm">
    <el-form-item label="ユーザー名" prop="userId">
      <el-input type="username" v-model="ruleForm.userId" autocomplete="off" clearable
        @keyup.enter="submitForm"></el-input>
    </el-form-item>
    <el-form-item label="パスワード" prop="pass">
      <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password
        @keyup.enter="submitForm"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">ログイン</el-button>
      <el-button type="primary" @click="registerForm('ruleForm')">新規登録</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import axios, { } from 'axios'
import { request } from "@/api/web";
import { ElMessage } from 'element-plus'
import router from '../router/router';
export default {
  data() {
    return {
      data1: {},
      ruleForm: {
        pass: '',
        checkPass: '',
        userId: '',
      },
      rules: {}
    };
  },
  methods: {
    validForm() {
      if (!this.ruleForm.userId) {
        ElMessage.warning('ユーザー名を入力してください！');
        return false;
      }
      if (this.ruleForm.pass === '') {
        ElMessage.warning('パスワードを入力してください');
        return false;
      } else {
      }
      return true;
    },
    submitForm() {
      if (this.validForm() == true) {
        this.data1 = {
          userId: this.ruleForm.userId,
          password: this.ruleForm.pass
        };
        console.log(this.data1)
        axios({
          method: 'post',
          url: this.$http + "/login",
          headers: { 'Content-Type': 'application/json;charset=UTF-8',
          //  'Authorization': `Bearer ${accessToken}` 
        },
          data: this.data1
        })
          .then((response) => {
            var data3 = response.data;
            if (data3.code == "success") {
              // location.href = "/?userId=" + this.ruleForm.userId + "loginState : true";
              router.push({ path: '/', query: { loginState: "true", userId: this.ruleForm.userId } })
              console.log(data3);
              localStorage.setItem('Token', data3.token)
              console.log("login")
              console.log(localStorage.getItem('Token'))

            }
            else if (data3.code == "warning") {
              console.log(data3);
              ElMessage.warning(data3.msg);
            }
          })
          .catch(function (error) {
            console.log(error);
          });
      } else {
        return false;
      }
    },
    registerForm() {
      this.$router.push({ path: '/register' });
    }
  },
  created() {
  }
}

</script>
<style scoped>
.demo-ruleForm {
  position: absolute;
  left: calc(50% - 300px);
  top: 20%;
  width: 400px;
}
</style>
