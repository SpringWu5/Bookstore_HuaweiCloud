<script setup>
import { ref } from 'vue'
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
//回显数据绑定
const userInfo = ref({...userInfoStore.info})

//引入路由
import {useRouter} from 'vue-router'
const router = useRouter();

//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();
//学院分类数据模型
const college = ref([
    {
        "id": '',
        "name": '',
        "status": '',
        "createTime": '',
        "updateTime": '',
        "createUser": '',
        "updateUser": ''
    }
])
//宿舍分类数据模型
const dormitory = ref([
    {
        "id": '',
        "name": '',
        "status": '',
        "createTime": '',
        "updateTime": '',
        "createUser": '',
        "updateUser": ''
    }
])

 //回显学院分类
    import { collegeListService} from '@/api/college.js'
    import { dormitoryListService} from '@/api/dormitory.js'
    const collegeCategoryList = async () => {
        let result = await collegeListService();
        college.value = result.data;
    }    

    //回显宿舍分类
    const dormitoryCategoryList = async () => {
        let result = await dormitoryListService();
        dormitory.value = result.data;
    }

        //图片上传成功的回调函数
    const uploadSuccess = (result)=>{
    userInfo.value.avatar = result.data;
    }

    collegeCategoryList();
    dormitoryCategoryList();
//修改个人信息
import {memberUpdateService} from '@/api/member.js'
import {ElMessage} from 'element-plus'
const updateUserInfo = async ()=>{
    //调用接口
    let result = await memberUpdateService(userInfo.value);
    ElMessage.success(result.msg? result.msg : '修改成功');
    
    //修改pinia中的个人信息
    userInfoStore.setInfo(userInfo.value)
} 

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>基本资料</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="userInfo"  label-width="100px" size="large">
                    <el-form-item label="用户名">
                        <el-input v-model="userInfo.username" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="用户昵称">
                        <el-input v-model="userInfo.name"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" >
                        <el-select style="width: 200px" placeholder="请选择" v-model="userInfo.sex">
                            <el-option label="男" value="1"></el-option>
                            <el-option label="女" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号">
                        <el-input v-model="userInfo.phone"></el-input>
                    </el-form-item>
                <el-form-item label="学院名称" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="userInfo.collegeId">
                        <el-option v-for="c in college" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="宿舍名称" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="userInfo.dormitoryId">
                        <el-option v-for="c in dormitory" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>  
                <el-form-item label="用户头像">

                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     -->
                   
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/admin/common/upload"
                    name="file"
                    :headers="{'token':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>   
                    <el-form-item>
                        <el-button type="primary" @click="updateUserInfo">提交修改</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>

<style lang="scss" scoped>
/* 抽屉样式 */
.avatar-uploader {
    :deep() {
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
        }

        .el-upload {
            border: 1px dashed var(--el-border-color);
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            transition: var(--el-transition-duration-fast);
        }

        .el-upload:hover {
            border-color: var(--el-color-primary);
        }

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 178px;
            height: 178px;
            text-align: center;
        }
    }
}

.avatar {
  width: 50px; /* 调整头像显示的大小 */
  height: 50px;
}
</style>