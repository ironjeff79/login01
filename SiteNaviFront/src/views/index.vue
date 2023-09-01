<template>
  <el-button type="primary" style="float:right" class="button1" @click="submitForm('ruleForm')">ログイン</el-button>
  <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="190px" class="demo-ruleForm">
    <el-form-item label="ユーザー名" prop="userId">
      <el-input type="username" v-model="ruleForm.userId" autocomplete="off" clearable
        @keyup.enter="submitForm"></el-input>
    </el-form-item>
    <el-form-item label="パスワード" prop="pass">
      <el-input type="password" v-model="ruleForm.pass" autocomplete="off" show-password
        @keyup.enter="submitForm"></el-input>
    </el-form-item>
  </el-form>
</template>

<script>
import axios, { } from 'axios'
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
      rules: {}
    };
  },
  methods: {
    submitForm() {
      location.href = "/signIn";

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


<!-- <template>
  <el-container class="layout-container-demo">
    <el-aside style="background-color: #ffffff;width:200px">
      <img src="../assets/images/logo_dark@2x.png" alt="">
        <el-menu v-for="(menu, idx) in items" :unique-opened="true">
          <a :href="'/#' + menu.en_name">
            <el-menu-item v-if="!menu.children" :index="'${idx}'">
                <el-icon :class="menu.icon"></el-icon>
                <span>{{ menu.name }}</span>
            </el-menu-item>
          </a>
          <el-sub-menu v-if="menu.children" :index="'${idx}'">
            <template #title>
              <el-icon :class="menu.icon"></el-icon>
              <span>{{ menu.name }}</span>
            </template>
              <el-menu-item v-for="(submenu, index) in menu.children" :index="`${idx}-${index}`">
                  <a :href="'/#' + submenu.en_name">
                    {{ submenu.name }}
                  </a>
              </el-menu-item>
          </el-sub-menu>
        </el-menu>
        <el-menu>
          <el-menu-item>
            <router-link to="/about">
              <el-icon class="linecons-heart"></el-icon>
              <span>关于本站</span>
            </router-link>
          </el-menu-item>
        </el-menu>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar" v-if="hasLogin">
          <router-link to="/userpage">
            <el-button type="success" plain>
              <el-icon style="margin-right: 8px; margin-top: 1px">
                <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-ea893728="">
                  <path fill="currentColor"
                    d="M764.416 254.72a351.68 351.68 0 0 1 86.336 149.184H960v192.064H850.752a351.68 351.68 0 0 1-86.336 149.312l54.72 94.72-166.272 96-54.592-94.72a352.64 352.64 0 0 1-172.48 0L371.136 936l-166.272-96 54.72-94.72a351.68 351.68 0 0 1-86.336-149.312H64v-192h109.248a351.68 351.68 0 0 1 86.336-149.312L204.8 160l166.208-96h.192l54.656 94.592a352.64 352.64 0 0 1 172.48 0L652.8 64h.128L819.2 160l-54.72 94.72zM704 499.968a192 192 0 1 0-384 0 192 192 0 0 0 384 0z">
                  </path>
                </svg>
              </el-icon>
              <span>个人主页</span>
            </el-button>
          </router-link>
        </div>
        <div class="toolbar" v-else>
          <router-link to="/login">
            <el-button type="success" plain>
              登录
            </el-button>
          </router-link>
        </div>
      </el-header>

      <el-main>
        <div v-if="!hasSelected">
          <div style="
              position: relative;
              left: 0px;
              top: 0px;
              background-color: #f9f9f9;
            " v-for="(item, idx) in items" :key="idx" class="webBox">
            <div v-if="item.web" :id="item.en_name">
              <WebItem :item="item" @func="getInfoFromSon" />
            </div>
            <div v-else v-for="(subItem, idx) in item.children" :id="subItem.en_name" :key="idx">
              <WebItem :item="subItem" @func="getInfoFromSon" />
            </div>
          </div>
        </div>
        <div v-else>
          <WebPage :web="targetWeb" @backFunc="getInfoFromPage"></WebPage>
        </div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts">
import webItemsData from "../assets/data.json";
import WebItem from "../components/WebItem.vue";
import WebPage from "../components/WebPage.vue";
import Login from "../components/Login.vue";

export default {
  data() {
    return {
      items: webItemsData,
      hasSelected: false,
      targetWeb: {},
      hasLogin: false,
    };
  },
  created() {
    if (this.$route.query.loginState === "true") {
      this.hasLogin = true;
    }
  },
  components: {
    WebItem,
    WebPage,
    Login
  },

  methods: {
    getInfoFromSon(Web: Object) {
      this.targetWeb = Web;
      this.hasSelected = true;
    },
    getInfoFromPage(isBack: boolean) {
      this.targetWeb = {};
      this.hasSelected = false;
    },
    output(idx: number, index: string) {
      console.log(idx + '-' + index);
    },
  },
};
</script>

<style scoped>
a {
  text-decoration: none;
  color: black;
}

html,
body,
.layout-container-demo,
.el-container {
  padding: 0;
  margin: 0;
  height: 100%;
  width: 100%;
}

img {
  margin-top: 15px;
  width: 100%;
  color: black;
}


.el-row {
  margin-bottom: 20px;
}

.el-row:last-child {
  margin-bottom: 0;
}

.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}

.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.el-sub-menu .el-menu-item.is-active {
  color: var(--el-menu-active-color);
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style> -->
