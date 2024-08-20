<!--template标签里面写html代码-->
<template>
  <el-table :data="userList" stripe style="width: 100%">
    <el-table-column prop="id" label="ID"/>
    <el-table-column prop="nick" label="昵称"/>
    <el-table-column label="密码">
      <template #default="scope">
        ******
      </template>
    </el-table-column>
    <el-table-column prop="sex" label="性别"/>
    <el-table-column prop="phone" label="手机"/>
    <el-table-column prop="email" label="邮箱"/>
    <el-table-column prop="address" label="住址"/>
    <el-table-column prop="createTime" label="创建时间"/>
    <el-table-column prop="updateTime" label="更新时间"/>
    <el-table-column label="操作">
      <template #default="scope">
        <a href="javascript:void(0)" @click="download(scope.row.id)" v-if="scope.row.userContractDO !== null">下载</a> &nbsp;
        <span v-else>下载</span> &nbsp;
        <a :href="'/edit/'+scope.row.id">编辑</a> &nbsp;
        <a href="javascript:void(0)" @click="delUser(scope.row.id)">删除</a>
      </template>
    </el-table-column>
  </el-table>
</template>

<!--script标签里面写js代码-->
<script setup>
import {doDelete, doGet} from "../http/httpRequest.js";
import {onMounted, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

let userList = ref( [{}] );

onMounted(() => {
  getData();
})

function getData() {
  //alert(123)
  doGet("/api/users", {}).then( (response) => {
    // 处理成功情况
    console.log(response);
    //response.code;
    //response.msg;
    userList.value = response.data.data; // response.data = R 对象
  }).catch( (error) => {
    // 处理错误情况
    console.log(error);
  }).finally( () => {
    // 总是会执行
  });
}

//使用iframe下载文件
function download(id) {
  let iframe = document.createElement("iframe");
  iframe.src = axios.defaults.baseURL + "/api/download/" + id;
  iframe.style.display = "none";
  document.body.appendChild(iframe);
}

//删除
function delUser(id) {
  doDelete("/api/user/" + id, {}).then(resp => {
    if (resp.data.code === 200) {
      //删除成功，提示一下
      ElMessage({
        showClose: true,
        message: '删除成功',
        type: 'success',
      })
      window.location.reload();
    } else {
      //删除失败，提示一下
      ElMessage({
        showClose: true,
        message: '删除失败',
        type: 'error',
      })
    }
  })
}
</script>