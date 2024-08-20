<template>
  <div style="margin: auto; text-align: center; font-size: 18px; font-weight: bold; padding: 15px" >
    编辑用户
  </div>
  <el-form
      label-width="auto"
      :model="userInfo"
      style="max-width: 600px; margin: auto;">

    <el-form-item label="昵称">
      <el-input v-model="userInfo.nick" />
    </el-form-item>

    <el-form-item label="密码">
      <el-input v-model="userInfo.password" />
    </el-form-item>

    <el-form-item label="性别">
      <el-input v-model="userInfo.sex" />
    </el-form-item>

    <el-form-item label="手机">
      <el-input v-model="userInfo.phone" />
    </el-form-item>

    <el-form-item label="邮箱">
      <el-input v-model="userInfo.email" />
    </el-form-item>

    <el-form-item label="住址">
      <el-input v-model="userInfo.address" />
    </el-form-item>

    <el-form-item label="上传头像">
      <el-upload
          ref="uploadImageRef"
          class="avatar-uploader"
          :action="'http://localhost:8080/api/user/image?id='+id"
          accept="image/jpg,image/png,image/jpeg"
          limit="1"
          :on-success="imageSuccess"
          :on-error="imageError"
          :file-list="imageFileList"
          :show-file-list="true"
          :auto-upload="false">
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
      <span style="color: forestgreen;"> &nbsp; {{imageTip}} </span>
    </el-form-item>

    <el-form-item label="上传合同">
      <el-upload
          ref="uploadContractRef"
          class="avatar-uploader"
          :action="'http://localhost:8080/api/user/contract?id='+id"
          accept="application/pdf"
          limit="1"
          :on-success="contractSuccess"
          :on-error="contractError"
          :file-list="contractFileList"
          :show-file-list="true"
          :auto-upload="false">
        <img v-if="imageUrl" :src="imageUrl" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
      <span style="color: forestgreen;"> &nbsp; {{contractTip}} </span>
    </el-form-item>

    <el-form-item label="&nbsp;">
      <el-button type="success" @click="submitUpload">提 交</el-button>
      <el-button type="success" plain @click="goBack">返 回</el-button>
    </el-form-item>

  </el-form>
</template>

<script setup>
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {doGet, doPut} from "../http/httpRequest.js";

let userInfo = ref( {} )

let uploadImageRef = ref( {} )
let uploadContractRef = ref( {} )

let imageFileList = ref([])
let contractFileList = ref([])

let imageTip = ref("")
let contractTip = ref("")

//获取动态路由地址中的id参数 (route获取路由信息的，比如路由的路径，路由的参数)
let route = useRoute()
let id = route.params.id

//用于调用路由对象的方法
let router = useRouter()

onMounted(() => {
  //根据id查询用户信息
  doGet("/api/user/" + id, {}).then(resp => {
    if (resp.data.code === 200) {
      userInfo.value = resp.data.data
    }
  })
})

function submitUpload() {
  //提交用户信息
  let formData = new FormData();
  for (let field in userInfo.value) {
    if (field !== 'userImageDO' && field !== 'userContractDO') {
      if (userInfo.value[field]) {
        formData.append(field, userInfo.value[field]);
      }
    }

  }
  doPut("/api/user", formData).then(resp => {
    if (resp.data.code === 200) {
      console.log("更新成功......")
    }
  })

  uploadImageRef.value.submit()
  uploadContractRef.value.submit()
}

function imageSuccess() {
  imageFileList.value = []
  //提示一下
  imageTip = "头像上传成功"
}

function imageError() {
  imageFileList.value = []
  //提示一下
  imageTip = "头像上传失败"
}

function contractSuccess() {
  contractFileList.value = []
  //提示一下
  contractTip = "合同上传成功"
}

function contractError() {
  contractFileList.value = []
  //提示一下
  contractTip = "合同上传失败"
}

//返回
function goBack() {
  router.go(-1)
}
</script>

<style>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}
.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}
.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>