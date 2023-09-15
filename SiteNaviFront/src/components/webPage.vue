<!-- 小组件内评论评分页面 -->
<template>
  <div>
    <div class="btn">
      <el-button @click="backToIndex" type="info" plain>返回</el-button>
    </div>
    <div class="pageBox">
      <div class="logo">
        <el-card class="box-card" :body-style="{ padding: '5px' }" shadow="always">
          <img :src="web?.logo" class="image" />
        </el-card>
      </div>
      <div class="web-item">
        <h3 class="title">{{ web?.title }}</h3>
        <div class="rateBox">
          大众评分：
          <el-rate v-model="value" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" allow-half
            :texts="['极差', '失望', '一般', '满意', '惊喜']" show-text disabled />
        </div>
        <p class="desc">网站简介：{{ web?.desc }}</p>
        <el-button type="success">
          <el-icon style="vertical-align: middle">
            <Link />
          </el-icon>
          <span style="vertical-align: middle">
            <a class="url" :href="web?.url" target="_blank">链接直达</a>
          </span>
        </el-button>
        <el-button type="warning" style="margin-left: 30px;">
          <el-icon style="vertical-align: middle">
            <Warning />
          </el-icon>
          <span style="vertical-align: middle" @click="feedback"> 反馈 </span>
        </el-button>
      </div>
    </div>

    <div class="commentBox">
      <!-- 附上icon -->
      <div class="title">
        <el-icon size="30" style="vertical-align: middle;">
          <ChatLineRound />
        </el-icon>
        <span style="vertical-align: middle;font-size: large;">
          <strong>评论区</strong>
        </span>
      </div>
      <div class="rateBox">
        您的评分：
        <el-rate v-model="commentedMessage.rate" :colors="['#99A9BF', '#F7BA2A', '#FF9900']" allow-half
          :texts="['极差', '失望', '一般', '满意', '惊喜']" show-text />
      </div>
      <el-input type="textarea" placeholder="请输入评论" v-model="commentedMessage.content"></el-input>
      <div style="text-align: right;margin: 10px 0;">
        <el-button type="primary" @click="submit">提交</el-button>
      </div>
      <div class="pastComment">
        历史评论
      </div>
      <div style="margin: 20px 0;text-align: left;" v-if="comments.length != 0">
        <div class="comBox" v-for="item in comments" :key="item.commentId">
          <div style="display: flex;">
            <div style="width: 70px;"><img class="user-avatar"
                :src="'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif'" alt=""></div>
            <div style="flex: 1;">
              <div style="display: flex; justify-content: space-between;">
                <b>{{ item.currentUsername }}</b>
                <span>{{ item.createtime.replace(/T/g, ' ') }}</span>
              </div>
              <div style="margin-top: 10px;color: #2c2c2c;">{{ item.content }}</div>
              <!--多级回复-->
              <div style="text-align: right;padding: 6px 0;">
                <el-button type="default" @click="reply(item.commentId, item.userId)">回复</el-button>
              </div>
              <!--回复列表-->
              <div v-if="item.children">
                <div v-for="sub in item.children" :key="sub.commentId">
                  <div style="border-left: 2px dashed #666;padding-left: 20px;">
                    <b style="cursor: pointer;margin-right: 5px;" @click="reply(sub.pid, sub.userId)">{{
                      sub.currentUsername
                    }}</b>
                    <span>回复 <span style="color: cornflowerblue;">@{{ sub.targetName }}：</span><span
                        style="color: #666;margin-left: 10px;">{{ sub.content }}</span></span>
                    <span style="float: right; color: #868484;">{{ sub.createtime.replace(/T/g, ' ') }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-else>
        <div style="color: #868484;">暂无评论</div>
      </div>


      <el-dialog v-model="dialogFormVisible" title="回复" width="40%">
        <el-form :model="replyComment">
          <el-form-item label="内容" :label-width="100">
            <el-input v-model="replyComment.content" autocomplete="off" style="width:80% ;" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveReply">
              保 存
            </el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  </div>
</template>
  
<script>
import { ref } from "vue";
import { ChatLineRound, Warning, Link } from '@element-plus/icons-vue'
import { ElMessage } from "element-plus";

import axios from 'axios'
const mytoken = ref("");

export default {
  name: "WebPage",
  props: {
    web: Object,
  },
  computed: {
    userId() {
      return this.$route.query.userId;
    }
  },
  data() {
    return {
      data1: {},
      isBack: false,
      value: 0,
      commentedMessage: {
        rate: 0,
        content: '',
        user_id: undefined,
        foreignId: this.web.foreignId,
        target: -1
      },
      replyComment: {
        rate: 0,
        content: '',
        user_id: 0,
        foreignId: this.web.foreignId,
        pid: -1,
        target: -1
      },
      comments: [],
      dialogFormVisible: false,
      user: {
        id: -1,
        name: '',
        avatar: ''
      },
      hasLogin: false
    };
  },
  mounted() {
    this.load();
  },
  methods: {
    feedback() {
      ElMessage.success('反馈成功');
    },
    backToIndex() {
      this.isBack = true;
      this.$emit("backFunc", this.isBack);
    },
    load() {
      this.data1 = {
        foreignId: this.commentedMessage.foreignId
      };
      axios({
        method: 'post',
        url: this.$http + "/comment",
        headers: { 'Content-Type': 'application/json;charset=UTF-8' },
        data: this.data1
      })
        .then(res => {
          // this.user.name = response.data.data.name;
          // this.user.avatar = response.data.data.avatar;
          // this.replyComment.user_id = this.user.id;
          // this.commentedMessage.user_id = this.user.id;
          // this.hasLogin = true;
          this.comments = res.data.comments;
          this.value = res.data.rate;
          this.$nextTick(() => {
            this.commentedMessage.content = ''; 
          });

        })
        .catch(err => {
          console.error(err);
        })
    },
    reply(ppid, target) {
      this.replyComment = { pid: ppid, user_id: this.$route.query.userId, foreignId: this.web.foreignId, target: target }
      this.dialogFormVisible = true
    },
    //回复区
    saveReply() {
      if (this.hasLogin === true) {
        this.data1 = {
          rate: this.replyComment.rate,
          content: this.replyComment.content,
          userId: this.replyComment.user_id,
          foreignId: this.web.foreignId,
          pid: this.replyComment.pid,
          target: this.replyComment.target
        };
        axios({
          method: 'post',
          url: this.$http + "/saveReply",
          headers: { 'Content-Type': 'application/json;charset=UTF-8' },
          data: this.data1
        })
          .then(res => {
            ElMessage.success('评论发布成功')
            this.$nextTick(() => {
              this.replyComment.content = ''; 
            });
            this.load()
            this.dialogFormVisible = false
          })
          .catch(err => {
            console.error(err);
          })
      } else {
        ElMessage.error('请先登录再进行评论.')
      }
    },
    //评论区评分以及评论
    submit() {
      if (!this.commentedMessage.content) {
        ElMessage.warning('请进行评论！')
      } else {
        if (this.hasLogin === true) {
          console.log(this.commentedMessage.rate, this.commentedMessage.content, this.$route.query.userId, this.commentedMessage.foreignId)
          this.data1 = {
            rate: this.commentedMessage.rate,
            content: this.commentedMessage.content,
            userId: this.$route.query.userId,
            foreignId: this.commentedMessage.foreignId
          };
          axios({
            method: 'post',
            url: this.$http + "/commentPosted",
            headers: { 'Content-Type': 'application/json;charset=UTF-8' },
            data: this.data1
          })
            .then(res => {
              ElMessage.success('评论发布成功')
              this.load()
            })
            .catch(err => {
              console.error(err);
            })
        } else {
          ElMessage.error('请先登录再进行评论.')
        }
      }
    }
  },

  created() {
    if (this.$route.query.userId != null) {
      this.userId = this.$route.query.userId;
      this.hasLogin = true;
    }
  },
};
</script>
  
<style scoped>
.pageBox {
  padding-top: 3%;
  padding-left: 3%;
  padding-right: 3%;
  padding-bottom: 3%;
  display: flex;
  user-select: none;
}

.logo {
  flex-basis: 150px;
}

.box-card {
  width: 130px;
}

.web-item {
  flex-grow: 1;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 16px;
}

.btn {
  margin-right: 80%;
  margin-top: 10px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 8px;
}

.desc {
  font-size: 16px;
  line-height: 1.5;
  margin-bottom: 8px;
}

.url {
  color: white;
  font-size: 16px;
  text-decoration: none;
}

.url:hover {
  text-decoration: underline;
}

.commentBox {
  width: 880px;
  margin: 0 auto;

}

.pastComment {
  margin: 10px 0;
  font-size: 16px;
  padding: 10px 0;
  border-bottom: 1px solid #ccc;
  text-align: left;
}

.comBox {
  padding: 10px 0;
}

.user-avatar {
  width: 50px;
  height: 50px;
  border-radius: 50%;
}

.rateBox {
  margin-bottom: 10px;
}</style>
  