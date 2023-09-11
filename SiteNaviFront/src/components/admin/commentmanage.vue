<template>
    <div>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb class=ArrowRight>
            <el-breadcrumb-item :to="{ path: '/admin' }">管理システム</el-breadcrumb-item>
            <el-breadcrumb-item>コメント管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区域 -->
        <el-card>
            <el-row :gutter="20">
                <el-col :span="8">
                    <!-- 搜索与添加区域 -->
                    <!-- <el-input v-model="search" size="small" placeholder="ユーザーIDを入力してください" @keyup.enter="searchForm">
                        <template #append>
                            <el-button type="primary" @click="searchForm('ruleForm')" :icon="Search"></el-button>
                        </template>
                    </el-input> -->
                </el-col>
            </el-row>
        </el-card>
        <el-table :data="filterTableData" style="width: 100%">
            <el-table-column prop="commentId" label="评论ID"></el-table-column>
            <el-table-column prop="userId" label="用户ID"></el-table-column>
            <el-table-column prop="rate" label="评分"></el-table-column>
            <el-table-column prop="content" label="内容"></el-table-column>
            <el-table-column prop="createtime" label="创建时间"></el-table-column>
            <el-table-column prop="userName" label="用户名"></el-table-column>
            <el-table-column align="right">
                <template #default="scope">
                    <el-button size="small" type="danger" @click="deleteComment(scope.row.commentId)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="button-container">
            <el-button size="small" type="primary" value="首页" @click="firstPage">トップ</el-button>
            <el-button size="small" type="info" value="前のページ" @click="prevPage">前のページ</el-button>
            <el-button size="small" type="info" value="次のページ" @click="nextPage">次のページ</el-button>
            <el-button size="small" type="primary" value="尾页" @click="lastPage">最後</el-button>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import { ref, onMounted, computed } from 'vue';
import { ElMessage } from 'element-plus';


export default {
    data() {
        return {
            totalPage: 1,
            currentPage: 1,
            pageSize: 5,
            data1: {},
            filterTableData: [],
        };
    },
    methods: {
        //ページングのリクエスト
        getList() {
            axios({
                method: 'post',
                url: this.$http + "/commentPage",
                headers: { 'Content-Type': 'application/json;charset=UTF-8' },
                data: this.data1
            })
                .then((response) => {
                    var data3 = response.data;
                    if (data3.code == "success") {
                        this.filterTableData = data3.maps.comments;
                        this.totalPage = data3.maps.totalPage;
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        //上一页
        prevPage() {
            if (this.currentPage == 1) {
                return false;
            } else {
                this.currentPage--;
                this.data1 = {
                    page: this.currentPage,
                    // userId: this.search,
                    pageSize: this.pageSize,
                };
                this.getList();
            }
        },
        // 下一页
        nextPage() {
            if (this.currentPage == this.totalPage) {
                return false;
            } else {
                this.currentPage++;
                this.data1 = {
                    page: this.currentPage,
                    // userId: this.search,
                    pageSize: this.pageSize,
                };
                this.getList();
            }
        },
        // //尾页
        lastPage() {
            if (this.currentPage == this.totalPage) {
                return false;
            } else {
                this.currentPage = this.totalPage;
                this.data1 = {
                    // userId: this.search,
                    page: this.currentPage,
                    pageSize: this.pageSize,
                };
                this.getList();
            }
        },
        //首页
        firstPage() {
            this.currentPage = 1;
            this.data1 = {
                // userId: this.search,
                page: this.currentPage,
                pageSize: this.pageSize,
            };
            this.getList();
        },
        async deleteComment(commentId) {
            const confirmResult = await this.$confirm('ユーザーを永久に削除します。よろしいでしょうか?', 'ユーザー削除', {
                confirmButtonText: '確定',
                cancelButtonText: 'キャンセル',
                type: 'warning',
            }).catch(error => error)
            if (confirmResult !== 'confirm') {
                return this.$message.info('削除をキャンセルします')
            } else {
                axios({
                    method: 'post',
                    url: this.$http + "/deleteComment",
                    headers: { 'Content-Type': 'application/json;charset=UTF-8' },
                    data: commentId
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            ElMessage.warning("コメントが削除しました！");
                            location.href = "/admin/commentmanage"
                        }
                        else if (data3.code == "warning") {
                            ElMessage.warning(data3.msg);
                        }
                    })
            }
        }
    },

    created() {
        this.data1 = {
            page: this.currentPage,
            // userId: this.search,
            pageSize: this.pageSize,
        };
        this.getList();

    }
}

</script>

<style  scoped>
/* 全局样式表 */
html,
body,
#app {
    height: 100%;
    margin: 0;
    padding: 0;
}

.button-container {
    text-align: center;
    padding-top: 15px
}

.el-breadcrumb {
    /* 设置下拉距 */
    margin-bottom: 15px;
    /* 字体大小 */
    font-size: 12px;
}

.dialog-footer {
    margin: 0 auto;

}

.el-card {
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
}

.el-table {
    margin-top: 15px;
    font-size: 12px;
}

.password {
    width: 10px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    cursor: pointer;
}

.ArrowRight {
    padding-top: 15px;
    padding-left: 8px;
    text-align: center;
}
</style>