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
export default {
  props: ['Id'],
  data() {
    return {
      userId: '',
    };
  },
  methods: {
    changePassButton() {
      location.href =  "/changePass?userId=" + this.userId;
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

