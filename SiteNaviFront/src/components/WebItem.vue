<template>
    <div>
      <h4 class="text-gray">
        <i class="linecons-tag" :id="transName(item)"></i>{{transName(item)}}
      </h4>
      <div class="row">
        <div class="col-sm-3" v-for="(web, idx) in item.web" :key="idx">
          <div class="xe-widget xe-conversations box2 label-info" title=""
            @click="sendMsg(web)"
            data-toggle="tooltip" 
            data-placement="bottom" 
            :data-original-title="web.url">
  
            <div class="xe-comment-entry">
              <a class="xe-user-img">
                <img :src="web.logo" class="lozad img-circle" width="40">
              </a>
              <div class="xe-comment">
                <a href="#" class="xe-user-name overflowClip_1">
                  <strong>{{web.title}}</strong>
                </a>
                <p class="overflowClip_2">{{web.desc}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <br />
    </div>
  </template>
  
  <script>
  export default {
    name: 'WebItem',
    props: {
      item: Object,
      transName: Function
    },
    methods: {
      sendMsg(web) {
        this.targetPage = web
        this.$emit('func', this.targetPage)
        const params = {
          foreignId: web.foreignId,
          title: web.title
        };
      },
      openweb(url) {
        window.open(url, '_blank');
      }
    },
      
    }
  
  </script>
  
  <style scoped>
  i {
    margin-right: 7px;
  }
  </style> 
  
  <!-- <template>
    <div class="container">
      <h4>
        {{ item?.name }}
      </h4>
      <div class="row">
        <div v-for="(web, idx) in item?.web" :key="idx" class="web-block">
          <el-card shadow="hover" :body-style="{ padding: '10px' }" class="box-card" @click=sendMsg(web)>
            <div>
              <div>
                <img :src="web.logo" width="40" style="margin-right: 5%;" />
              </div>
              <p class="card-description">
                <strong>{{ web.title }}</strong>
                <br />
                {{ web.desc }}
              </p>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import axios from "axios";
  
  export default {
    name: "WebItem",
    props: {
      item: Object,
    },
    data() {
      return {
        targetPage: {}
      }
    },
  
  
    methods: {
      sendMsg(web) {
        this.targetPage = web
        this.$emit('func', this.targetPage)
        const params = {
          foreignId: web.foreignId,
          title: web.title
        };
        // 每一个点击事件都将增加一次该网页的点击量
        axios.post("/webinfo/addCount", null,{params})
          .then(res => {
            console.log(res)
          })
          .catch(err => {
            console.error(err);
          })
      },
    },
  };
  </script>
  
  <style scoped>
  i {
    margin-right: 7px;
  }
  
  .container {
    padding-right: 10px;
    padding-left: 10px;
    padding-top: 1px;
  }
  
  .el-card {
    overflow: hidden;
  }
  
  .card-description {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  
  img {
    float: left;
  }
  
  .text_line {
    clear: both;
    margin-bottom: 2px;
  }
  
  .row {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
  }
  
  .web-block {
    width: 25%;
    /* 每个网站元素所占的宽度修改为 25% */
    box-sizing: border-box;
    /* 添加盒模型属性 */
    padding-left: 10px;
    /* 为每个网站元素添加左内边距 */
    padding-right: 10px;
    /* 为每个网站元素添加右内边距 */
    margin-bottom: 20px;
  }
  </style> -->