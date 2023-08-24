<template>
  <div class="layout">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" style="max-width: 400px">
      <el-form-item label="ユーザー名" prop="userId">
        <span style="color:red;display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="username" v-model="ruleForm.userId" autocomplete="off"
          clearable @keyup.enter="registerForm"></el-input>
      </el-form-item>
      <el-form-item label="メール" prop="mail">
        <span style="color:red;display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="mail" v-model="ruleForm.mail" autocomplete="off"
          clearable @keyup.enter="registerForm"></el-input>
      </el-form-item>
      <el-form-item label="携帯電話" prop="phoneNum">
        <span style="color:rgb(255, 255, 255);display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="phoneNum" v-model="ruleForm.phoneNum" autocomplete="off"
          clearable @keyup.enter="registerForm"></el-input>
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
        <el-button type="info" @click="registerForm('ruleForm')">アカウント登録</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios, {} from 'axios';
import { ElMessage } from 'element-plus';

export default {
  data() {
    return {
      data2: {},
      ruleForm: {
        pass: '',
        checkPass: '',
        userId: '',
        mail: '',
        phoneNum: '',
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
      else if (5 > (this.ruleForm.userId.trim().length) || (this.ruleForm.userId.trim().length) > 10) {
        ElMessage.warning('ユーザー名は5桁から10桁の間に入力してください');
        return false;
      } else {
      }
      if (!this.ruleForm.mail) {
        ElMessage.warning('メールを入力してください');
        return false;
      }
      else if (this.ruleForm.mail.match(/^\w+@\w+\.\w+$/i)) {
      } else {
        ElMessage.warning('有効なメールを入力してください！');
        return false;
      }
      if (!this.ruleForm.phoneNum) {
      }
      else if (!this.ruleForm.phoneNum.match(/^(050|070|080|090)\d{4}\d{4}$/)) {
        ElMessage.warning('有効な携帯番号を入力してください');
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
          userId: this.ruleForm.userId,
          mail: this.ruleForm.mail,
          phoneNum: this.ruleForm.phoneNum,
          password: this.ruleForm.pass
        };
        axios({
          method: 'post',
          url: this.$http +"/register",
          headers: { 'Content-Type': 'application/x-www-form-urlencoded'},
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
          .catch(function (error) { 
            console.log(error);
          });
      }
    }
  },
  created() {
  },
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