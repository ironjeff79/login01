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
                            <el-button type="primary" @click="searchForm('ruleForm')" :icon="Search"></el-button>
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
                <template v-slot="scope">
                    {{ scope.row.userId }}
                    <el-tooltip effect="dark" content="編集" placement="top">
                        <el-button size="small" type="primary" :icon="Edit"
                            @click="showEditDialog(scope.row.userId)"></el-button>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="削除" placement="top">
                        <el-button size="small" type="danger" @click="removeUserById(scope.row.userId)" :icon="Delete" />
                    </el-tooltip>
                    <el-tooltip effect="dark" content="役割分担" placement="top" :enterable="false">
                        <el-button size="small" type="warning" :icon="Setting" />
                    </el-tooltip>

                </template>
            </el-table-column>
        </el-table>
        <div>
            <!-- 修改用户的对话框 -->
            <el-dialog title="情報変更" v-model="editDialogVisible" width="50%" @close="editDialogClosed"
                :close-on-click-modal='false'>
                <!-- 内容主体区 -->
                <el-form :model="editUserForm" :rules="editUserFormRules" ref="editUserFormRef" label-width="70px">
                    <el-form-item label="ID" prop="userId">
                        <el-input v-model="editUserForm.userId" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="メール" prop="mail">
                        <el-input v-model="editUserForm.mail"></el-input>
                    </el-form-item>
                    <el-form-item label="携帯" prop="phoneNum">
                        <el-input v-model="editUserForm.phoneNum"></el-input>
                    </el-form-item>
                </el-form>
                <!-- 底部区 -->
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible = false">取 消</el-button>
                    <el-button type="primary" @click="editUser()">确 定</el-button>
                </span>
            </el-dialog>
        </div>






    </div>
</template>
  
<script >
import axios, { } from 'axios'
import {
    ElForm, ElFormItem, ElInput, ElRow, ElMessage, ElContainer, ElHeader, ElAside, ElMain, ElMenu, ElSubmenu, ElMenuItem,
    ElIcon, ElBreadcrumb, ElBreadcrumbItem, ElCard, ElCol, ElTableColumn, ElTable, ElSwitch, ElMessageBox
} from 'element-plus'
import { Edit, Setting, Delete, Search } from '@element-plus/icons-vue'
import { ref } from 'vue'


export default {
    setup() {

        return {
            Edit, Setting, Delete, Search
        }
    },
    data() {
        return {

            data1: {},
            data2: {},
            data5: {},
            data6: {},

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
            total: 0,
            editDialogVisible: false, //控制修改用户对话框的显示与隐藏

            //修改用户的表单数据
            editUserForm: {
                userId: '',
                mail: '',
                phoneNum: '',
                pass: ''
            },
            //修改表单的验证规则对象
            // editUserFormRules: {
            //     email: [{ required: true, message: '请输入邮箱', trigger: 'blur' },],
            //     mobile: [{ required: true, message: '请输入手机号', trigger: 'blur' }]
            // },   
        };


    },


    methods: {
        validForm() {
            if (!this.ruleForm.mail) {
                ElMessage.warning('メールを入力してください');
                return false;
            }
            else if (this.ruleForm.mail.match(/^\w+@\w+\.\w+$/i)) {
            } else {
                ElMessage.warning('有効なメールを入力してください！');
                return false;
            }
            if (!this.ruleForm.phoneNum) {
            }
            else if (!this.ruleForm.phoneNum.match(/^(050|070|080|090)\d{4}\d{4}$/)) {
                ElMessage.warning('有効な携帯番号を入力してください');
                return false;
            } else {
            }
            return true;
        },


        //关闭编辑用户的对话框
        //   editDialogClosed() {
        //     this.$refs.editUserFormRef.resetFields();
        // },
        //展示编辑用户的对话框
        showEditDialog(userId) {
            this.editDialogVisible = true
            console.log(userId);
            this.data5 = { userId: userId };
            axios({
                method: 'post',
                url: 'http://localhost:8080/Search',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: JSON.stringify(this.data5)
            })
                .then((response) => {
                    console.log(response.data);
                    var data3 = response.data.userList;
                // 没法一个个值调出来
                    console.log(data3);
                    if (data3.code == "success") {
                        console.log(response.data); 
                    }
                    else if (data3.code == "warning") {
                        console.log(data3);
                        ElMessage.warning(data3.msg);
                    }
                })


        },
        editUser() {             
            
            if (this.validForm() == true) {
                this.data6 = {
                    userId: this.ruleForm.userId,
                    phoneNum: this.ruleForm.phoneNum,
                    mail: this.ruleForm.mail
                };
                axios({
                    method: 'post',
                    url: 'http://localhost:8080/changeAdmin',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    data: JSON.stringify(this.data6)
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            this.$router.push({ path: '/' });
                            alert(data3.msg);
                        }
                        else if (data3.code == "warning") {
                            alert(data3.msg);
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }

        },


        async removeUserById(userId) {
            this.data1 = {
                userId: userId,
            };
            const confirmResult = await this.$confirm('ユーザーを永久に削除します。よろしいでしょうか?', 'ユーザー削除', {
                confirmButtonText: '確定',
                cancelButtonText: 'キャンセル',
                type: 'warning',
            }).catch(error => error)

            if (confirmResult !== 'confirm') {
                return this.$message.info('已取消删除')
            } else {
                axios({

                    method: 'post',
                    url: 'http://localhost:8080/deleteUser',
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    data: JSON.stringify(this.data1)
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            console.log(data3);
                            ElMessage.warning(data3.msg);
                            this.$router.go(0)
                        }
                        else if (data3.code == "warning") {
                            console.log(data3);
                            ElMessage.warning(data3.msg);
                        }
                    })
            }
        },
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
        },
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
            .catch(function (error) {
                console.log(error);
            });

        // this.editUserForm.mail = this.scope.row.mail;
        // this.editUserForm.userId = this.scope.row.userId;
        // this.editUserForm.phoneNum = this.scope.row.phoneNum;
    },

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

.el-breadcrumb {
    /* 设置下拉距 */
    margin-bottom: 15px;
    /* 字体大小 */
    font-size: 12px;
}

.dialog-footer button:first-child {
    margin-right: 10px;
}

.el-card {
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
}

.el-table {
    margin-top: 15px;
    font-size: 12px;
}
</style>