//导入request.js请求工具
import request from '@/utils/request.js'

//提供调用注册接口的函数
export const userRegisterService = (registerData)=>{
    return request.post('/user/user/register',registerData);
}

//提供调用登录接口的函数
export const userLoginService = (loginData)=>{
    return request.post('/user/user/login',loginData)
}
 

//获取用户详细信息
export const userInfoService = ()=>{
    return request.get('/user/user/userInfo')
}

//修改密码
export const passwordUpdateService = (updatePasswordData)=>{
    return request.patch('/user/user/updatePwd',updatePasswordData)
 }

 //用户退出
export const userLogoutService = ()=>{
    return request.post('/user/user/logout')
 }
 