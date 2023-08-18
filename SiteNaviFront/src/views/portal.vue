<template>
  <div>
    <ul class="feedback">
      <li class="userId">
        {{ userId }}さん！ようこそ 
        <el-button type="warning" style="float:right" class="button1"  @click="changePassButton('ruleForm')">パスワードの変更</el-button>
      </li>
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
    changePassButton() {    
        this.data1 = {
          userId: this.userId
        };
        axios({
          method: 'post',
          url: 'http://localhost:8080/portal',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          data: JSON.stringify(this.data1)
        })
          .then((response) => {
              this.$router.push({name:'changePass', query:response.data});
          })
          .catch(function (error) { // 请求失败处理
            console.log(error);
          });
    },
    logOutButton() {
      location.href = "/";
    }
  },
  created() {
    this.userId = this.$route.query.userId;
    this.pass = this.$route.query.pass;
  }
}
</script>

