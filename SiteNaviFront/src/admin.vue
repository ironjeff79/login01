<template>
  <el-container class="layout-container-demo" style="height: 100%">
    <el-aside width="200px">
      <el-scrollbar>
        <h4 style="text-align: center;">管理システム</h4>
        <el-menu class="el-menu-vertical-demo">
          <el-menu-item index="1" @click="jump('usermanage')">
            <el-icon><icon-menu /></el-icon>
            <span>ユーザー管理</span>
          </el-menu-item>
          <el-menu-item index="2" @click="jump('webmanage')">
            <el-icon>
              <document />
            </el-icon>
            <span>ページ管理</span>
          </el-menu-item>
          <el-menu-item index="3" @click="jump('commentmanage')">
            <el-icon>
              <setting />
            </el-icon>
            <span>コメント管理</span>
          </el-menu-item>
        </el-menu>
      </el-scrollbar>
    </el-aside>
    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <setting />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="output()">ログアウト</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>Admin</span>
        </div>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>
</template>
  
<script setup>
import { onMounted } from "vue";
import { Menu as IconMenu, Setting } from "@element-plus/icons-vue";
import router from './router/router';
import { ElMessage, ElMessageBox } from 'element-plus'
import axios, { } from 'axios'

function jump(url = string) {
  router.push("/admin/" + url);
}

function output() {
  // 退出登录，清除 token 并跳转到首页     
  axios({
    method: 'post',
    url: "http://localhost:8080/logOut",
    headers: { 'Content-Type': 'application/json;charset=UTF-8' }
  })
    .then((response) => {
      var data3 = response.data;
      console.log(data3)
      console.log(data3.sessionId)
      localStorage.setItem("sessionId", "0")
      console.log(localStorage.getItem("sessionId",))
      location.href = "/";
    })
    .catch(function (error) {
      console.log(error);
    });
}

onMounted(() => {
  console.log(localStorage.getItem("sessionId",))
  const str = localStorage.getItem("sessionId",)
  if (str == 0) {
    ElMessage.warning('登录状态已过期，请重新登录')
    location.href = "/signIn";
  }

  else {
    jump("usermanage");
  }
})


</script>
  
<style scoped>
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
</style>

