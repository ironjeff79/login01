<template>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
    <el-form-item label="ユーザー名" prop="userId">
      <el-input type="username" v-model="ruleForm.userId" autocomplete="off" clearable></el-input>
    </el-form-item>
    <el-form-item label="パスワード" prop="pass">
      <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('ruleForm')">ログイン</el-button>
      <el-button type="primary" @click="registerForm('ruleForm')">新規登録</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
import axios, {} from 'axios'
import { ElMessage } from 'element-plus'
export default {
  data() {
    return {
      data1: {},
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
        axios({
          method: 'post',
          url: 'http://localhost:8080/login',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          data: JSON.stringify(this.data1)
        })
          .then((response) => {
            var data3 = response.data;
            if (data3.code == "success") {
              location.href = "/login?userId=" + this.ruleForm.userId;
            }
            else if (data3.code == "warning") {
              console.log(data3);
              ElMessage.warning(data3.msg);
            }
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
      } else {
        return false;
      }
    }, registerForm() {
      this.$router.push({ path: '/register'});

    },

    // goPortal() {
    //   location.href = "/login?userId=" + this.ruleForm.userId;
    // },
  }
}

</script>
<style scoped>
.demo-ruleForm {
  position: absolute;
  left: calc(50% - 200px);
  top: 20%;
  width: 400px;
}
</style>
