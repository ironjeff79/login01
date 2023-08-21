<template>
  <div class="layout">
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" style="max-width: 400px">
      <el-form-item label="ユーザー名" prop="userId">
        <span style="color:rgb(255, 255, 255);display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="text" v-model="ruleForm.userId" :disabled="true">
          clearable></el-input>
      </el-form-item>
      <el-form-item label="メール" prop="mail">
        <span style="color:rgb(255, 255, 255);display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="text" v-model="ruleForm.mail" clearable >
          </el-input>
      </el-form-item>
      <el-form-item label="携帯電話" prop="phoneNum">
        <span style="color:rgb(255, 255, 255);display: inline;">*</span>
        <el-input style="display: inline;width:280px" type="phoneNum" v-model="ruleForm.phoneNum" autocomplete="off"
          clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="registerForm('ruleForm')">変更</el-button>
        <el-button type="info" @click="backForm('ruleForm')">戻る</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
  
<script>
import axios, { } from 'axios';
import { ElMessage } from 'element-plus';

export default {
  props: [
  ],
  data() {
    return {
      userId: '',
      data2: {},
      ruleForm: {
        userId: '',
        mail: '',
        phoneNum: '',
        pass:''
      },
      rules: {
      }
    };
  },

  methods: {
    validForm() {
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
      return true;
    },
    registerForm() {
      if (this.validForm() == true) {
        this.data2 = {
          userId: this.ruleForm.userId,
          password: this.ruleForm.pass,
          phoneNum: this.ruleForm.phoneNum,
          mail: this.ruleForm.mail
        };
        axios({
          method: 'post',
          url: 'http://localhost:8080/changeInfo',
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
    this.ruleForm.pass = this.$route.query.password;
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