<template>
  <div>
    <li class="userId">
        {{ userId }}さん！ようこそ 
      </li>
    <ul class="feedback">
      <el-button type="warning" style="float:right" class="button1"  @click="UserButton('ruleForm')">ユーザー一覧表</el-button>
      <el-button type="warning" style="float:right" class="button1"  @click="changeInfoButton('ruleForm')">個人情報</el-button> 
      <el-button type="warning" style="float:right" class="button1"  @click="changePassButton('ruleForm')">パスワードの変更</el-button>
    </ul>
    <el-button type="info" style="float:right" class="button1"  @click="logOutButton('ruleForm')">ログアウト</el-button>
  </div> 
</template>

<script>
import axios, { } from 'axios'
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
          url: this.$http +"/portal",
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          data: JSON.stringify(this.data1)
        })
          .then((response) => {
              this.$router.push({name:'changeInfo', query:response.data});
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
          url: this.$http +"/portal",
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          data: JSON.stringify(this.data1)
        })
          .then((response) => {
              this.$router.push({name:'changePass', query:response.data});
          })
          .catch(function (error) { 
            console.log(error);
          });
    },
    UserButton() {
      this.$router.push({ path: '/admin'});
    },
    logOutButton() {
      location.href = "/";
    }
  },
  created() {
    this.userId = this.$route.query.userId;
    this.pass = this.$route.query.pass;
  },

}
</script>

<style scoped>

.feedback {
  display: flex;
  justify-content: flex-end;
}
</style>