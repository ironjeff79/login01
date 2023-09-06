<template>
    <!-- 组件模板 -->
    <div>
      <el-table :data="filterTableData" style="width: 100%">
        <el-table-column prop="commentId" label="评论ID"></el-table-column>
        <el-table-column prop="userId" label="用户ID"></el-table-column>
        <el-table-column prop="rate" label="评分"></el-table-column>
        <el-table-column prop="content" label="内容"></el-table-column>
        <el-table-column prop="createtime" label="创建时间"></el-table-column>
        <el-table-column prop="currentUsername" label="用户名"></el-table-column>
        <el-table-column align="right">
        <template #default="scope">
          <!-- <el-button size="small" type="danger" @click="deleteComment(scope.row.commentId)">删除</el-button> -->
        </template>
      </el-table-column>
      </el-table>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  import { ref, onMounted, computed } from 'vue';
  import { ElMessage } from 'element-plus';
  
  
  export default {
    data() {
        return { 
            data1: {},
            filterTableData:[],
        };

    },
created(){
            axios({
                method: 'post',
                url: this.$http + "/comment",
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                // data: JSON.stringify(this.data1)
            })
                .then((response) => {
                    var data3 = response.data;
                    console.log(data3);
                    if (data3.code == "success") {
                      this.filterTableData = data3.comments
                    }
                })
                .catch(function (error) {
                    console.log(error);
                });
        },
        
  }
  // interface Comment {
  //   commentId: number;
  //   userId: number;
  //   rate: number;
  //   content: string;
  //   foreignId: number;
  //   target: null | string;
  //   pid: null | string;
  //   createtime: string;
  //   children: null | Comment[];
  //   currentUsername: string;
  //   targetName: null | string;
  // };
  
  // export default {
  //   name: 'CommentList',
  //   setup() {
  //     const tableData = ref<Comment[]>([]);
  
  //     const filterTableData = computed(() => {
  //       // 使用搜索关键字过滤表格数据，这里略去具体实现
  //       return tableData.value;
  //     });
  //     const fetchComments = () => {
  //       axios
  //         .get('/comment/all')
  //         .then((response) => {
  //           const responseData = response.data;
  
  //           if (responseData.code === 20000) {
  //             // 将返回的数据封装为指定格式
  //             tableData.value = responseData.data.map((item: Comment) => {
  //               const formattedTime = new Date(item.createtime).toLocaleString('zh-CN', { hour12: false });
  //               return {
  //                 ...item,
  //                 createtime: formattedTime,
  //               };
  //             });
  //           } else {
  //             console.error('Error:', responseData.message);
  //           }
  //         })
  //         .catch((error) => {
  //           console.error('Error:', error);
  //         });
  //     }
  
  //     onMounted(fetchComments);

  //     const deleteComment = (commentId: number) => {
  //       axios.post(`/comment/delete/${commentId}`)
  //       .then(res => {
  //         const responseData = res.data;
  
  //           if (responseData.code === 20000) {
  //             ElMessage.success(responseData.message)
  //             fetchComments()
  //           }
  //       })
  //       .catch(err => {
  //         console.error(err); 
  //       })
  //     }
  
  //     return {
  //       filterTableData,
  //       deleteComment
  //     };
  //   },
  // };
  </script>