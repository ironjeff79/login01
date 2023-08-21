<template>
    <el-card>
    <el-row :gutter="20">
      <el-col :span="8">
        <!-- 搜索与添加区域 -->
          <el-input placeholder="ユーザー名入力してください" >
          <template #append>
            <el-icon><search /></el-icon>
         
          </template>
          </el-input>
      </el-col>
      <el-col :span="4">
        <!-- <el-button type="primary">添加用户</el-button> -->
      </el-col>
    </el-row>
    </el-card>
    <el-table :data="filterTableData" style="width: 100%">
      <el-table-column label="用户名" prop="username" />
      <el-table-column label="邮箱" prop="email" />
      <el-table-column label="电话号码" prop="phone" /> 
      <el-table-column label="状态" prop="status">
        <template #default="{ row }">
          <el-button :type="getStatusButton(row.status)" disabled>{{
            getStatusText(row.status)
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否删除" prop="deleted">
        <template #default="{ row }">
          <el-button :type="getDeletedButton(row.deleted)" disabled>{{
            getDeletedText(row.deleted)
          }}</el-button>
        </template>
      </el-table-column>
      <el-table-column align="right">
        <!-- <template #header>
          <el-input v-model="search" size="small" placeholder="搜索用户" />
        </template> -->
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  
    <el-dialog v-model="editFormVisible" title="编辑用户" width="50%" style="z-index: 9999;">
      <el-form :model="editForm" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="editForm.email" />
        </el-form-item>
        <el-form-item label="电话号码">
          <el-input v-model="editForm.phone" />
        </el-form-item>
        <!-- 其他表单项 -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editFormVisible = false">返回</el-button>
        <el-button type="primary" @click="saveEdit">保存</el-button>
      </span>
    </el-dialog>
  
    <!-- <el-dialog v-model="deleteFormVisible" title="删除用户" width="30%" center>
      <p>确定要删除该用户吗？</p>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deleteFormVisible = false">取消</el-button>
        <el-button type="danger" @click="confirmDelete">确认删除</el-button>
      </div>
    </el-dialog> -->
  </template>
  <script >
  // import { computed, onMounted, ref } from 'vue';
  // import axios from 'axios';
  
  // interface User {
  //   username: string;
  //   email: string;
  //   phone: string;
  //   status: number;
  //   deleted: number;
  // }
  
  // export default {
  //   setup() {
  //     const search = ref('');
  //     const tableData = ref<User[]>([]);
  //     const editFormVisible = ref(false); // 控制编辑弹窗的显示隐藏
  //     const deleteFormVisible = ref(false); // 控制删除对话框的显示隐藏
  //     const editForm = ref<User>({
  //       username: '',
  //       email: '',
  //       phone: '',
  //       status: 0,
  //       deleted: 0
  //     }); // 当前编辑的用户信息
  //     let oldUsername = ''; // 老的用户名
  
  //     const filterTableData = computed(() =>
  //       tableData.value.filter(
  //         (data) =>
  //           !search.value ||
  //           data.username.toLowerCase().includes(search.value.toLowerCase())
  //       )
  //     );
  
  //     const handleEdit = (index: number, row: User) => {
  //       editForm.value = { ...row }; // 将当前行数据复制到编辑表单中
  //       oldUsername = row.username; // 获取老的用户名
  //       editFormVisible.value = true; // 显示编辑弹窗
  //     };
  
  //     const confirmDelete = () => {
  //       // 发送请求将用户的删除状态转变为已删除
  //       axios.post(`/user/delete/${oldUsername}`)
  //         .then(response => {
  //           console.log('Delete success:', response.data);
  //           // 更新tableData中对应行的数据
  //           const deletedUserIndex = tableData.value.findIndex(user => user.username === oldUsername);
  //           if (deletedUserIndex !== -1) {
  //             // 更新对应行的deleted属性为 1（已删除）
  //             tableData.value[deletedUserIndex].deleted = 1;
  //           }
  //           // 隐藏删除对话框
  //           deleteFormVisible.value = false;
  //         })
  //         .catch(error => {
  //           console.error('Error:', error);
  //         });
  //     };
  
  //     const handleDelete = (index: number, row: User) => {
  //       editForm.value = { ...row }; // 将当前行数据复制到编辑表单中
  //       oldUsername = row.username; // 获取老的用户名
  //       deleteFormVisible.value = true; // 显示删除对话框
  //     };
  
  //     const getStatusButton = (status: number) => {
  //       return status === 1 ? 'success' : 'warning';
  //     };
  
  //     const getStatusText = (status: number) => {
  //       return status === 1 ? '已激活' : '未激活';
  //     };
  
  //     const getDeletedButton = (deleted: number) => {
  //       return deleted === 0 ? 'success' : 'danger';
  //     };
  
  //     const getDeletedText = (deleted: number) => {
  //       return deleted === 0 ? '正常' : '已删除';
  //     };
  
  //     const saveEdit = () => {
  //       // 在这里发送保存编辑后的用户信息的请求
  //       axios.post(`/user/edit/${oldUsername}`, editForm.value)
  //         .then(response => {
  //           // 根据接口返回的结果做处理
  //           console.log('Edit success:', response.data);
  //           // 更新tableData中对应行的数据
  //           const updatedUserIndex = tableData.value.findIndex(user => user.username === oldUsername);
  //           if (updatedUserIndex !== -1) {
  //             tableData.value.splice(updatedUserIndex, 1, { ...editForm.value });
  //           }
  //           // 隐藏编辑弹窗
  //           editFormVisible.value = false;
  //         })
  //         .catch(error => {
  //           console.error('Error:', error);
  //         });
  //     };
  
  //     onMounted(() => {
  //       axios.get('/user/all')
  //         .then(response => {
  //           const responseData = response.data;
  //           // 将返回的数据封装为指定格式
  //           tableData.value = responseData.data.map((item: User) => {
  //             return {
  //               username: item.username,
  //               email: item.email,
  //               phone: item.phone,
  //               status: item.status,
  //               deleted: item.deleted
  //             };
  //           });
  //         })
  //         .catch(error => {
  //           console.error('Error:', error);
  //         });
  //     });
  
  //     return {
  //       search,
  //       filterTableData,
  //       handleEdit,
  //       handleDelete,
  //       getStatusButton,
  //       getStatusText,
  //       getDeletedButton,
  //       getDeletedText,
  //       editFormVisible,
  //       deleteFormVisible,
  //       editForm,
  //       saveEdit,
  //       confirmDelete
  //     };
  //   }
  // };
  </script>