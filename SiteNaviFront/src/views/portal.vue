<template>
  <div>
    <ul class="feedback">
      <li class="userId">
        {{ userId }}さん！ようこそ 
        <el-button type="warning" style="float:right" class="button1"  @click="logOutButton('ruleForm')">ログアウト</el-button>
      </li>
    </ul>
  </div> 
</template>

<script>
export default {
  props: ['Id'],
  data() {
    return {
    };
  },
  methods: {
    logOutButton() {
      location.href = "/";
    }
  },
  created() {
    this.a = this.$route.query.a;
    this.userId = this.$route.query.userId;
    this.pass = this.$route.query.pass;
  }
}
</script>

