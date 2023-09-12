<template>
  <el-header style="text-align: right; font-size: 28px">
  <div class="toolbar">
    <el-dropdown>
      <el-icon style="margin-right: 8px; margin-top: 1px">
        <setting />
      </el-icon>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="changeInfoButton('ruleForm')">個人情報</el-dropdown-item>
          <el-dropdown-item @click="changePassButton('ruleForm')">パスワード変更</el-dropdown-item>
          <el-dropdown-item @click="logOutButton()">ログアウト</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <span>{{ userId }}</span>
  </div>
</el-header>


  <h1 class="page-title">ホームページ</h1>
  <div class="userId">
    このページでは、{{ userId }}様だけのサービスがご利用いただけます。
  </div>

  <div>
    <ul class="feedback">
      <!-- <el-button type="warning" style="float:middle" class="button1" @click="UserButton('ruleForm')">ユーザー一覧表</el-button> -->
      <!-- <el-button type="warning" style="float:right" class="button1" @click="changeInfoButton('ruleForm')">個人情報</el-button>
      <el-button type="warning" style="float:right" class="button1"
        @click="changePassButton('ruleForm')">パスワードの変更</el-button> -->
    </ul>
    <!-- <el-button type="info" style="float:right" class="button1" @click="logOutButton('ruleForm')">ログアウト</el-button> -->
  </div>
</template>

<script>
import axios, { } from 'axios'
import router from '../router/router';
import { ElMessage } from 'element-plus';
export default {
  props: ['Id'],
  data() {
    return {
      data1: {},
      userId: '',
    };
  },
  methods: {
    changeInfoButton() {
      this.data1 = {
        userId: this.userId
      };
      axios({
        method: 'post',
        url: this.$http + "/portal",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        data: this.data1
      })
        .then((response) => {
          this.$router.push({ name: 'changeInfo', query: response.data });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    changePassButton() {
      this.data1 = {
        userId: this.userId
      };
      axios({
        method: 'post',
        url: this.$http + "/portal",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        data: this.data1
      })
        .then((response) => {
          this.$router.push({ name: 'changePass', query: response.data });
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    UserButton() {
      this.$router.push({ path: '/admin' });
    },
    logOutButton() {
      axios({
        method: 'post',
        url: this.$http + "/logOut",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' }
      })
        .then((response) => {
          var data3 = response.data;
          console.log(data3)
          console.log(data3.sessionId)
          localStorage.setItem("sessionId","0")
          console.log(localStorage.getItem("sessionId",))
          location.href = "/";
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  },
  created() {
    this.userId = this.$route.query.userId;
    this.pass = this.$route.query.pass;
    console.log(localStorage.getItem("sessionId",))
    const str = localStorage.getItem("sessionId",)
    if (str == 0) {
      ElMessage.warning('登录状态已过期，请重新登录')
      location.href = "/signIn";
    }
  },
}
</script>

<style scoped>
.page-title {
  text-align: center;
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
}

.userId {
  text-align: center;
  font-size: 30px;
  color: #666;
  margin-bottom: 10px;
}

.toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 50px;
}

/* .feedback {
  display: block;
  margin: 0 auto
} */

.feedback {
  display: flex;
  justify-content: flex-end;
}
</style>