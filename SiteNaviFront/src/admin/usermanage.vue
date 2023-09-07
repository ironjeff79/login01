<template>
    <div>
        <!-- 面包屑导航区域 -->
        <el-breadcrumb class=ArrowRight>
            <el-breadcrumb-item :to="{ path: '/admin' }">管理システム</el-breadcrumb-item>
            <el-breadcrumb-item>ユーザー管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区域 -->
        <el-card>
            <el-row :gutter="20">
                <el-col :span="8">
                    <!-- 搜索与添加区域 -->
                    <el-input v-model="search" size="small" placeholder="ユーザーIDを入力してください" @keyup.enter="searchForm">
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
        <el-table :data="currentPageData" border stripe>
            <el-table-column label="番号" type="index"></el-table-column>
            <el-table-column label="ユーザーID" prop="userId"></el-table-column>
            <el-table-column label="メール" prop="mail"></el-table-column>
            <el-table-column label="携帯電話" prop="phoneNum"></el-table-column>
            <!-- <el-table-column label="役割" prop="userId"></el-table-column> -->
            <el-table-column label="状態" prop="mg_state">
                <template v-slot="scope">
                    <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ff4949" active-value="1"
                        inactive-value="0" @change="userManage(scope.row)" />
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template v-slot="scope">
                    <!-- {{ scope.row }} -->
                    <el-tooltip effect="dark" content="編集" placement="top">
                        <el-button size="small" type="primary" :icon="Edit"
                            @click="showEditDialog(scope.row.password)"></el-button>
                    </el-tooltip>
                    <el-tooltip effect="dark" content="削除" placement="top">
                        <el-button size="small" type="danger" @click="removeUserById(scope.row.userId)" :icon="Delete" />
                    </el-tooltip>
                    <!-- <el-tooltip effect="dark" content="役割分担" placement="top" :enterable="false">
                        <el-button size="small" type="warning" @click="showEditRole(scope.row.password)" :icon="Setting" />
                    </el-tooltip> -->

                </template>
            </el-table-column>
        </el-table>
        <div class="button-container">
            <el-button size="small" type="primary" value="首页" @click="firstPage">トップ</el-button>
            <el-button size="small" type="info" value="前のページ" @click="prevPage">前のページ</el-button>
            <el-button size="small" type="info" value="次のページ" @click="nextPage">次のページ</el-button>
            <el-button size="small" type="primary" value="尾页" @click="lastPage">最後</el-button>
        </div>
        <div>
            <!-- 修改用户的对话框 -->
            <el-dialog title="情報変更" v-model="editDialogVisible" width="50%" @close="editDialogClosed"
                :close-on-click-modal='false'>
                <!-- 内容主体区 -->
                <el-form :model="editUserForm" ref="editUserFormRef" label-width="130px">
                    <el-form-item label="ID" prop="userId">
                        <el-input v-model="editUserForm.userId" @keyup.enter="editUser"></el-input>
                    </el-form-item>
                    <el-form-item label="メール" prop="mail">
                        <el-input v-model="editUserForm.mail" @keyup.enter="editUser"></el-input>
                    </el-form-item>
                    <el-form-item label="携帯" prop="phoneNum">
                        <el-input v-model="editUserForm.phoneNum" @keyup.enter="editUser"></el-input>
                    </el-form-item>
                    <el-form-item label="パスワード" prop="password" class="password">
                        <el-input v-model="editUserForm.password" disabled></el-input>
                    </el-form-item>
                </el-form>
                <!-- 底部区 -->
                <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialogVisible = false">キャンセル</el-button>
                    <el-button type="primary" @click="editUser()">確定</el-button>
                </span>
            </el-dialog>
        </div>
        <div>
            <el-dialog title="情報変更" v-model="editRoleVisible" width="50%" @close="editDialogClosed"
                :close-on-click-modal='false'>
                <el-form :model="editUserForm" ref="editUserFormRef" label-width="30px">
                    <el-form-item label="ID" prop="userId">
                        <el-input v-model="editUserForm.userId" disabled></el-input>
                    </el-form-item>
                    <el-checkbox-group v-model="checkList">
                        <el-checkbox label="管理員" checked="true" />
                        <el-checkbox label="高級ユーザー" checked="true" />
                        <el-checkbox label="一般ユーザー" checked="true" />
                    </el-checkbox-group>
                    <!-- <el-form-item label="Zones" :label-width="formLabelWidth">
                        <el-select v-model="form" placeholder="Please select a role">
                            <el-option label="管理員" value="管理員" />
                            <el-option label="一般ユーザー" value="一般ユーザー" />
                        </el-select>
                    </el-form-item>  -->
                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="editRole()">確定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>
  
<script>
import axios, { } from 'axios'
import {
    ElForm, ElFormItem, ElInput, ElRow, ElMessage, ElContainer, ElHeader, ElAside, ElMain, ElMenu, ElSubmenu, ElMenuItem,
    ElIcon, ElBreadcrumb, ElBreadcrumbItem, ElCard, ElCol, ElTableColumn, ElTable, ElSwitch, ElMessageBox
} from 'element-plus'
import { Edit, Setting, Delete, Search } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'

const checkList = ref(['一般ユーザー', '管理員'])

export default {
    setup() {

        return {
            Edit, Setting, Delete, Search
        }
    },
    data() {
        return {
            ONOFFvalue: '0',
            data1: {},
            userlist: [],
            search: "",
            List: null,
            listLoading: true,
            totalPage: 1,
            currentPage: 1,
            currentPageData: [],
            pageSize:3,

            editDialogVisible: false,
            editRoleVisible: false,


            //修改用户的表单数据
            editUserForm: {
                userId: '',
                mail: '',
                phoneNum: '',
                pass: ''
            },
        };


    },

    methods: {
        //ページングのリクエスト
        getList() {
            axios({
                method: 'post',
                url: this.$http + "/SearchPage",
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: JSON.stringify(this.data1)
            })
                .then((response) => {
                    var data3 = response.data;
                    if (data3.code == "success") {
                        this.currentPageData = data3.maps.userList;
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
                    userId: this.search,
                    pageSize:this.pageSize,
                };
                this.getList();
            }
        },
        // 下一页
        nextPage() {
            if (this.currentPage == this.totalPage) {
                return false;}else{
            this.currentPage++;
            this.data1 = {
                page: this.currentPage,
                userId: this.search,
                pageSize:this.pageSize,
            };
            this.getList();}
        },
        // //尾页
        lastPage() {
            if (this.currentPage == this.totalPage) {
                return false;
            } else {
                this.currentPage = this.totalPage;
                this.data1 = {
                userId: this.search,
                page: this.currentPage,
                pageSize:this.pageSize,
            };
            this.getList();
            }
        },
        //首页
        firstPage() {
            this.currentPage = 1;
            this.data1 = {
                userId: this.search,
                page: this.currentPage,
                pageSize:this.pageSize,
            };
            this.getList();
        },

        //用户激活与否，开关按钮合并
        userManage(row) {
            {
                this.data1 = {
                    userId: row.userId,
                    state: row.state
                };
                axios({
                    method: 'post',
                    url: this.$http + "/changeState",
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    data: JSON.stringify(this.data1)
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            alert(data3.msg);
                        }
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
            }
        },

        validForm() {
            if (!this.editUserForm.userId) {
                ElMessage.warning('ユーザー名を入力してください！');
                return false;
            }
            else if (5 > (this.editUserForm.userId.trim().length) || (this.editUserForm.userId.trim().length) > 10) {
                ElMessage.warning('ユーザー名は5桁から10桁の間に入力してください');
                return false;
            } else {
            }
            if (!this.editUserForm.mail) {
                ElMessage.warning('メールを入力してください');
                return false;
            }
            else if (this.editUserForm.mail.match(/^\w+@\w+\.\w+$/i)) {
            } else {
                ElMessage.warning('有効なメールを入力してください！');
                return false;
            }
            if (!this.editUserForm.phoneNum) {
            }
            else if (!this.editUserForm.phoneNum.match(/^(050|070|080|090)\d{4}\d{4}$/)) {
                ElMessage.warning('有効な携帯番号を入力してください');
                return false;
            } else {
            }
            return true;
        },

        // 关闭编辑用户的对话框
        editDialogClosed() {
            this.$refs.editUserFormRef.resetFields();
        },
        // 展示编辑用户的对话框
        showEditDialog(password) {
            this.editDialogVisible = true
            this.data1 = { password: password };
            axios({
                method: 'post',
                url: this.$http + "/SearchDirect",
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                data: JSON.stringify(this.data1)
            })
                .then((response) => {
                    this.editUserForm = response.data.user;
                })
        },
        // showEditRole(password) {
        //     this.editRoleVisible = true

        //     this.data1 = { password: password };
        //     axios({
        //         method: 'post',
        //         url: this.$http + "/SearchDirect",
        //         headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        //         data: JSON.stringify(this.data1)
        //     })
        //         .then((response) => {
        //             this.editUserForm = response.data.user;
        //         })
        // },
        editUser() {
            if (this.validForm() == true) {
                this.data1 = {
                    userId: this.editUserForm.userId,
                    phoneNum: this.editUserForm.phoneNum,
                    mail: this.editUserForm.mail,
                    password: this.editUserForm.password,
                };
                axios({
                    method: 'post',
                    url: this.$http + "/changeAdmin",
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    data: JSON.stringify(this.data1)
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            alert(data3.msg);
                            this.editDialogVisible = false
                            this.$router.go(0)
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
                return this.$message.info('削除をキャンセルします')
            } else {
                axios({
                    method: 'post',
                    url: this.$http + "/deleteUser",
                    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                    data: JSON.stringify(this.data1)
                })
                    .then((response) => {
                        var data3 = response.data;
                        if (data3.code == "success") {
                            ElMessage.warning(data3.msg);
                            this.$router.go(0)
                        }
                        else if (data3.code == "warning") {
                            ElMessage.warning(data3.msg);
                        }
                    })
            }
        },

        searchForm() {
            this.data1 = {
                userId: this.search,
                page: 1,
                pageSize:this.pageSize,
            };
            this.getList();
        },
    },
    created() {
        this.data1 = {
            page: this.currentPage,
            userId: this.search,
            pageSize:this.pageSize,
        };
        this.getList();
        
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