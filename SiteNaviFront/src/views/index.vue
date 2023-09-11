<template>
  <div class="page-container">
    <div class="sidebar-menu toggle-others fixed">
      <div class="sidebar-menu-inner">
        <header class="logo-env">
          <!-- logo -->
          <div class="logo">
            <a href="javascript:void(0)" class="logo-expanded">
              <img src="../assets/images/logo@2x.png" width="100%" alt="" />
            </a>
            <a href="javascript:void(0)" class="logo-collapsed">
              <img src="../assets/images/logo-collapsed@2x.png" width="40" alt="" />
            </a>
          </div>
          <div class="mobile-menu-toggle visible-xs">
            <a href="javascript:void(0)" data-toggle="user-info-menu">
              <i class="linecons-cog"></i>
            </a>
            <a href="javascript:void(0)" data-toggle="mobile-menu">
              <i class="fa-bars"></i>
            </a>
          </div>
        </header>
        <!-- 侧边栏 -->
        <ul id="main-menu" class="main-menu">
          <li v-for="(menu, idx) in items" :key="idx">
            <a :href="'#' + transName(menu)" class="smooth">
              <i :class="menu.icon"></i>
              <span class="title">{{ transName(menu) }}</span>
            </a>
            <ul v-if="menu.children">
              <li v-for="(submenu, idx) in menu.children" :key="idx">
                <a :href="'#' + transName(submenu)" class="smooth">
                  <span class="title">{{ transName(submenu) }}</span>
                  <span v-show="submenu.is_hot" class="label label-pink pull-right hidden-collapsed">Hot</span>
                </a>
              </li>
            </ul>
          </li>
          <li class="submit-tag">
            <router-link to="/about">
              <i class="linecons-heart"></i>
              <span class="tooltip-blue">关于本站</span>
              <span class="label label-Primary pull-right hidden-collapsed">♥︎</span>
            </router-link>
          </li>
        </ul>
      </div>
    </div>

    <div class="main-content">
      <nav class="navbar user-info-navbar" role="navigation">
        <ul class="user-info-menu left-links list-inline list-unstyled">
          <li class="hidden-sm hidden-xs">
            <a href="javascript:void(0)" data-toggle="sidebar"><i class="fa-bars"></i></a>
          </li>
          <li class="dropdown hover-line language-switcher">
            <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
              <img :src="lang.flag" /> {{ lang.name }}
            </a>
            <ul class="dropdown-menu languages">
              <li :class="{ active: langItem.key === lang.key }" v-for="langItem in langList" :key="langItem.key">

                <a href="javascript:void(0)" @click="lang = langItem">
                  <img :src="langItem.flag" /> {{ langItem.name }}
                </a>
              </li>
            </ul>
          </li>
        </ul>
        <ul class="user-info-menu right-links list-inline list-unstyled">
          <li class="hidden-sm hidden-xs">

            <div class="toolbar" v-if="hasLogin">

              <el-button type="success" plain style="margin-right: 20px; margin-top: 20px" @click="infoForm('ruleForm')">
                <el-icon style="margin-right: 8px; margin-top: 1px">
                  <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-ea893728="">
                  </svg>
                </el-icon>
                <span>{{ userId }}さん</span>
              </el-button>

            </div>
            <div class="toolbar" v-else>
              <el-button type="success" plain style="margin-right: 20px; margin-top:20px" size="default" class="button1"
                @click="submitForm('ruleForm')">ログイン</el-button>
            </div>



          </li>
        </ul>
      </nav>

      <div v-if="!hasSelected">
        <div v-for="(item, idx) in items" :key="idx">
          <div v-if="item.web" :id=transName(item)>
            <WebItem :item="item" :transName="transName" @func="getInfoFromSon" />
          </div>
          <div v-else v-for="(subItem, idx) in item.children" :key="idx">
            <WebItem :item="subItem" :transName="transName" />
          </div>
        </div>
      </div>
      <div v-else>
        <WebPage :web="targetWeb" @backFunc="getInfoFromPage"></WebPage>
      </div>

      <Footer />
    </div>
  </div>
</template>

<script>
import WebItem from "../components/WebItem.vue";
import WebPage from "../components/webPage.vue";
import Footer from "../components/Footer.vue";
import itemsData from "../assets/data.json";
import { loadJs } from '../assets/js/app.js';
import Cookies from "js-cookie";
import router from "../router/router";

export default {
  computed: {
    userId() {
      return this.$route.query.userId;
      // return localStorage.getItem('userId');
    }
  },

  name: "Index",
  components: {
    WebItem,
    WebPage,
    Footer,
  },
  data() {
    return {
      showname: false,
      hasLogin: false,
      hasSelected: false,
      items: itemsData,
      lang: {},
      langList: [
        {
          key: "zh",
          name: "简体中文",
          flag: "./assets/images/flags/flag-cn.png",
        },
        {
          key: "en",
          name: "English",
          flag: "./assets/images/flags/flag-us.png",
        },
      ],
    };
  },
  created() {

    this.lang = this.langList[0];
    loadJs();
    if (this.$route.query.loginState === "true") {
      this.hasLogin = true;
      this.userId = this.$route.query.userId;
    }

  },
  methods: {
    transName(webItem) {
      return this.lang.key === "en" ? webItem.en_name : webItem.name;
    },

    submitForm() {
      location.href = "/signIn";
    },
    infoForm() {
      if(this.userId === "admin"){
                router.push("/admin")
            }
            else{location.href = "/login?userId=" + this.userId;}
      
    },
    getInfoFromSon(Web = Object) {
      this.targetWeb = Web;
      this.hasSelected = true;
    },
    getInfoFromPage(isBack = boolean) {
      this.targetWeb = {};
      this.hasSelected = false;
    },
    output(idx = number, index = string) {
      console.log(idx + '-' + index);
    },

  },

};
</script>

<style></style>
