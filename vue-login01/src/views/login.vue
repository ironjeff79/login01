<template>
  <div>
    <ul class="feedback">
      <li class="userId">
        {{ userId }}さん！ようこそ
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
    submitForm() {
      console.log("ユーザーIDuserId");
      console.log(this.userId);
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
  created() {
    this.a = this.$route.query.a;
    this.userId = this.$route.query.userId;
    this.pass = this.$route.query.pass;
  }
}
</script>

