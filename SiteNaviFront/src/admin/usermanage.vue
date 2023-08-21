<template>
    <div>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb separator-class=ArrowRight>
            <el-breadcrumb-item :to="{ path: '/admin' }">管理システム</el-breadcrumb-item>
            <el-breadcrumb-item>ユーザー管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区域 -->
        <el-card>
            <el-row :gutter="20">
                <el-col :span="8">
                    <!-- 搜索与添加区域 -->
                    <el-input v-model="search" size="small" placeholder="ユーザーIDを入力してください">
                        <template #append>
                            <el-button type="primary" @click="searchForm('ruleForm')">検索</el-button>
                            <el-icon>
                                <search />
                            </el-icon>
                        </template>
                    </el-input>
                </el-col>
                <el-col :span="4">
                    <!-- <el-button type="primary">添加用户</el-button> -->
                </el-col>
            </el-row>
        </el-card>
        <!-- 用户列表区域  -->
        <el-table :data="userlist" border stripe>
            <el-table-column label="番号" type="index"></el-table-column>
            <el-table-column label="ユーザーID" prop="userId"></el-table-column>
            <el-table-column label="メール" prop="mail"></el-table-column>
            <el-table-column label="携帯電話" prop="phoneNum"></el-table-column>
            <!-- <el-table-column label="役割" prop="userId"></el-table-column> -->
            <!-- <el-table-column label="状態" prop="mg_state">
                    <template v-slot="scope">
                        <el-switch v-model="scope.row.mg_state" />
                    </template>
                </el-table-column> -->
            <el-table-column label="操作">
                <!-- <template v-slot = "scope">
                {{ scope.row }}
                <el-button size ="small" type = "primary" :icon="Edit" v-model = "scope.row.Id"></el-button>
                </template> -->
            </el-table-column>
        </el-table>

    </div>
</template>
  
<script >
import axios, { } from 'axios'
import {
    ElButton, ElForm, ElFormItem, ElInput, ElRow, ElMessage, ElContainer, ElHeader, ElAside, ElMain, ElMenu, ElSubmenu, ElMenuItem,
    ElIcon, ElBreadcrumb, ElBreadcrumbItem, ElCard, ElCol, ElTableColumn, ElTable, ElSwitch,
} from 'element-plus'
import{Edit} from '@element-plus/icons-vue'

// import {
//     Location,
//     Menu as IconMenu, Search
// } from '@element-plus/icons'

export default {
    //    setup() {
    // //   const search = ref('');
    // },
    data() {

        return {
            search: "",
            // 获取用户列表的参数对象
            queryInfo: {
                query: '', // 查询参数
                pagenum: 1, // 当前页码
                pagesize: 2 // 每页显示条数
            },
            // 用户列表
            userlist: [],
            // 总数据条数
            total: 0
        }
    },
    created() {
        axios({
            method: 'post',
            url: 'http://localhost:8080/SearchAll',
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        })
            .then((response) => {
                var data3 = response.data;
                if (data3.code == "success") {
                    console.log(response.data);
                    this.userlist = response.data.userList;
                    console.log(this.userlist);
                }
                else if (data3.code == "warning") {
                    console.log(data3);
                    ElMessage.warning(data3.msg);
                }
            })
            .catch(function (error) { // 请求失败处理
                console.log(error);
            });
    },
    methods: {

        searchForm() {
            this.data1 = {
                userId: this.search,
            };
            axios({
                method: 'post',
                url: 'http://localhost:8080/Search',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: JSON.stringify(this.data1)
            })
                .then((response) => {
                    var data3 = response.data;
                    if (data3.code == "success") {
                        console.log(response.data);
                        this.userlist = response.data.userList;
                        console.log(this.userlist);
                    }
                    else if (data3.code == "warning") {
                        console.log(data3);
                        ElMessage.warning(data3.msg);
                    }
                })
                .catch(function (error) { // 请求失败处理
                    console.log(error);
                });

        }
    }

}
</script>
  
<style  scoped>
/* 全局样式表 */
html,
body,
#app {
    /* 高度占100% */
    height: 100%;
    /* margin重置为0 */
    margin: 0;
    /* padding重置为0 */
    padding: 0;
}

.el-breadcrumb {
    /* 设置下拉距 */
    margin-bottom: 15px;
    /* 重置字体大小 */
    font-size: 12px;
}

.el-card {
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
}

.el-table {
    margin-top: 15px;
    font-size: 12px;
}</style>