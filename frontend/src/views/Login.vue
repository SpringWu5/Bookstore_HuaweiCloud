<script setup>
import { User, Lock, Check,Refresh } from '@element-plus/icons-vue'
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
//控制注册与登录表单的显示， 默认显示注册
const isRegister = ref(false)

//定义数据模型
const registerData = ref({
    username: '',
    password: '',
    rePassword: '',
    captcha:''
})

 const correctCaptcha = ref(''); // 全局变量用于存储验证码

//生成验证码
const generateCaptcha = () => {
    // 生成验证码并在 canvas 中绘制
    var canvas = document.getElementById("captchaCanvas");
    var context = canvas.getContext("2d");

    // 清除之前的验证码
    context.clearRect(0, 0, canvas.width, canvas.height);

    // 生成新的验证码
    var captchaCode = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 6; i++) {
        captchaCode += possible.charAt(Math.floor(Math.random() * possible.length));
    }

    // 在 canvas 中绘制验证码文本
    context.font = "28px Arial";
    context.fillStyle = "black";
    context.fillText(captchaCode, 20, 30);

    correctCaptcha.value = captchaCode; // 将验证码存储在全局变量中
    return captchaCode;
}
//刷新验证码
const refreshCaptcha = () =>{
    generateCaptcha();
}

// 页面成功加载时生成验证码
window.onload = function() {
    generateCaptcha();
}

//校验密码的函数
const checkRePassword = (rule, value, callback) => {
    if (value === '') {
        callback(new Error('请再次确认密码'))
    } else if (value !== registerData.value.password) {
        callback(new Error('请确保两次输入的密码一样'))
    } else {
        callback()
    }
}

//定义表单校验规则
const rules = {
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入密码', trigger: 'blur' },
        { min: 5, max: 16, message: '长度为5~16位非空字符', trigger: 'blur' }
    ],      
    captcha: [
        { required: true, message: '请输入验证码', trigger: 'blur' },
        { min: 6, max: 6, message: '长度为6位非空字符', trigger: 'blur' }
    ],    

    rePassword: [
        { validator: checkRePassword, trigger: 'blur' }
    ]
}

//调用后台接口,完成注册
import { userRegisterService, userLoginService} from '@/api/user.js'
const register = async () => {
    //registerData是一个响应式对象,如果要获取值,需要.value
    let result = await userRegisterService(registerData.value);
    ElMessage.success(result.msg ? result.msg : '注册成功')
}

//绑定数据,复用注册表单的数据模型
//表单数据校验
//登录函数
//引入token
import {useTokenStore} from '@/stores/token.js'
import{useRouter} from 'vue-router'
const router = useRouter()
const tokenStore = useTokenStore();

const login =async ()=>{

    // 检查验证码是否正确
    //忽略字母大小写，不管大写小写都转换成小写字母来进行比较
    if (registerData.value.captcha.toLowerCase() === correctCaptcha.value.toLowerCase()) {
        //调用接口,完成登录
        let result =  await userLoginService(registerData.value);
        //alert(result.msg? result.msg : '登录成功')
        ElMessage.success('登录成功')
        //把得到的token存储到pinia中
        tokenStore.setToken(result.data.token)
        //进行权限判断   管理员|普通用户
        let checkState=result.data.status;
        //跳转到首页，路由完成跳转
        if(checkState=="0")
        router.push('/admin')

        if(checkState=="1")
        router.push('/user')
    } else {
        ElMessage.error('验证码输入错误')
        generateCaptcha();

    }
   
}
//定义函数,清空数据模型的数据
const clearRegisterData = ()=>{
    registerData.value={
        username:'',
        password:'',
        rePassword:'',
        captcha:''
    }
}

</script>

<template>
    <el-row class="login-page">
        <el-col :span="0" class="bg"></el-col>
        <el-col :span="6" :offset="9" class="form">
            <!-- 注册表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-if="isRegister"  :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>注册</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item>
                <el-form-item prop="rePassword">
                    <el-input :prefix-icon="Lock" type="password" placeholder="请输入再次密码" v-model="registerData.rePassword"></el-input>
                </el-form-item>
                <!-- 注册按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" @click="register" auto-insert-space>
                        注册
                    </el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = false;  generateCaptcha(); clearRegisterData();">
                        ← 返回
                    </el-link>
                </el-form-item>
            </el-form>
            <!-- 登录表单 -->
            <el-form ref="form" size="large" autocomplete="off" v-else   :model="registerData" :rules="rules">
                <el-form-item>
                    <h1>登录吧</h1>
                </el-form-item>
                <el-form-item prop="username">
                    <el-input :prefix-icon="User" placeholder="请输入用户名" v-model="registerData.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input name="password" :prefix-icon="Lock" type="password" placeholder="请输入密码" v-model="registerData.password"></el-input>
                </el-form-item> 
                <el-form-item prop="captcha">
                    <div class="captcha-row">
                        <el-input :prefix-icon="Check" type="text" style="width: 150px;" placeholder="请输入验证码" v-model="registerData.captcha"></el-input>
                        <canvas id="captchaCanvas" width="140" height="40"></canvas>
                        <el-button class="captcha-refresh-btn" :icon="Refresh" @click="refreshCaptcha()"></el-button>
                    </div>
                </el-form-item>

                <el-form-item class="flex">
                    <div class="flex">
                        <el-checkbox>记住我</el-checkbox>
                        <el-link type="primary" :underline="false">忘记密码？</el-link>
                    </div>
                </el-form-item>
                <!-- 登录按钮 -->
                <el-form-item>
                    <el-button class="button" type="primary" @click="login" auto-insert-space>登录</el-button>
                </el-form-item>
                <el-form-item class="flex">
                    <el-link type="info" :underline="false" @click="isRegister = true;clearRegisterData()">
                        注册 →
                    </el-link>
                </el-form-item>
            </el-form>
        </el-col>
    </el-row>
</template>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #fff7f0 0%, #ffe0b2 100%);
  display: flex;
  align-items: center;
  justify-content: center;
}
.form {
  background: #fff;
  border-radius: 22px;
  box-shadow: 0 8px 32px 0 rgba(255,103,0,0.10), 0 1.5px 6px 0 rgba(255,103,0,0.08);
  padding: 64px 64px 44px 64px;
  margin: 40px 0;
  min-width: 480px;
  max-width: 560px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.form h1 {
  font-size: 2.4rem;
  font-weight: 900;
  color: #ff6700;
  letter-spacing: 2px;
  margin-bottom: 18px;
  text-align: center;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}
.el-form-item {
  margin-bottom: 22px;
}
.el-input__wrapper {
  border-radius: 16px !important;
  box-shadow: 0 2px 8px rgba(255,103,0,0.06);
  font-size: 16px;
}
.el-input__inner {
  border-radius: 16px !important;
  font-size: 16px;
  padding: 12px 16px;
}
.button.el-button--primary {
  background: linear-gradient(90deg, #ffb347 0%, #ff6700 100%);
  border: none;
  color: #fff;
  font-weight: bold;
  font-size: 18px;
  border-radius: 24px;
  padding: 12px 0;
  width: 100%;
  box-shadow: 0 2px 8px rgba(255,103,0,0.10);
  transition: background 0.2s, box-shadow 0.2s;
}
.button.el-button--primary:hover {
  background: linear-gradient(90deg, #ff6700 0%, #e55300 100%);
  box-shadow: 0 4px 16px rgba(255,103,0,0.18);
}
.el-link {
  font-size: 15px;
}
.flex {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}
/* 验证码区域美化 */
.captcha-row {
  display: flex;
  align-items: center;
  gap: 12px;
  width: 100%;
}
#captchaCanvas {
  border-radius: 10px;
  border: 1.5px solid #ffb347;
  background: #fff7f0;
  box-shadow: 0 1px 4px rgba(255,103,0,0.06);
  width: 140px;
  height: 40px;
  margin: 0 8px;
  display: inline-block;
}
.el-button.is-text {
  color: #ff6700;
}
.captcha-refresh-btn {
  margin-left: 8px;
  height: 40px;
  min-width: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 10px;
}
@media (max-width: 600px) {
  .form {
    min-width: 90vw;
    padding: 24px 8vw 18px 8vw;
  }
  .login-page {
    padding: 0;
  }
}
</style>