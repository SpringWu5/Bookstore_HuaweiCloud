<script setup>
import { ref } from 'vue'

import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();

import {useRouter} from 'vue-router'
const router = useRouter();

const userInfo = ref({...userInfoStore.info})

//定义数据模型
const updatePasswordData = ref({
    "oldPwd": '',
    "newPwd": '',
    "rePwd": ''
})
 
//定义表单校验规则
const rules = {
    oldPwd: [
        { required: true, message: '请输入旧密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    newPwd: [
        { required: true, message: '请输入新密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    rePwd: [
         { required: true, message: '请再次确认密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ]
}

//修改密码
import {passwordUpdateService }  from '@/api/user.js'



import { ElMessage } from 'element-plus'
import {ElMessageBox} from 'element-plus'
const updatePassword = (row) => {
    //提示用户  确认框

    ElMessageBox.confirm(
        '你确认要修改密码吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await passwordUpdateService(updatePasswordData.value)
            ElMessage({
                type: 'success',
                message: '修改成功',
            })
            //退出登录
            //1.清空pinia中存储的token以及个人信息
            tokenStore.removeToken()
            userInfoStore.removeInfo()

            //2.跳转到登录页面
            router.push('/login')
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了修改',
            })
        })
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>重置密码</span>
            </div>
        </template>
        <el-row>
            <el-col :span="12">
                <el-form :model="updatePasswordData" label-width="100px" size="large" :rules="rules">
                    <el-form-item label="旧密码" prop="oldPwd">
                        <el-input  placeholder="请输入旧密码" v-model="updatePasswordData.oldPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="新密码" prop="newPwd">
                        <el-input placeholder="请输入新密码" v-model="updatePasswordData.newPwd"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="rePwd">
                        <el-input placeholder="请再次确认密码" v-model="updatePasswordData.rePwd"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="updatePassword">修改密码</el-button>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
    </el-card>
</template>