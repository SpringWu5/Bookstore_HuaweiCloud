<script setup>
import {
    Management,
    HomeFilled ,
    Avatar,
    List,
    Checked,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    Switch,
    CaretBottom,
    Menu,
    HelpFilled,
    Histogram ,
    Comment,
    Grid ,
    InfoFilled
} from '@element-plus/icons-vue' 
import {ref} from 'vue'
import avatar from '@/assets/default.png'

import {userInfoService} from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
const tokenStore = useTokenStore();
const userInfoStore = useUserInfoStore();
const userInfo = ref({...userInfoStore.info})
//调用函数,获取用户详细信息
const getUserInfo = async()=>{
    //调用接口
    let result = await userInfoService();
    //数据存储到pinia中
    userInfoStore.setInfo(result.data);
} 
getUserInfo();

//条目被点击后,调用的函数
import {useRouter} from 'vue-router'
const router = useRouter();
import {ElMessage,ElMessageBox} from 'element-plus'
const handleCommand = (command)=>{
    //判断指令
    if(command === 'logout'){
        //退出登录
        ElMessageBox.confirm(
        '您确认要退出吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //退出登录
            //1.调用后端接口，删除redis
            await userLogout(); //等待userLogout执行完成
            //2.清空pinia中存储的token以及个人信息
            tokenStore.removeToken()
            userInfoStore.removeInfo()
            //3.跳转到登录页面
            router.push('/login')
            ElMessage({
                type: 'success',
                message: '退出登录成功',
            })
            
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了退出登录',
            })
        })
    }else{
        //路由
        router.push('/admin/'+command)
    }
}

//用户退出
import {userLogoutService}  from '@/api/user.js'
const userLogout = async()=>{
    //调用接口
    let result = await userLogoutService();
} 

</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <div class="el-aside__logo"></div>
            <el-menu active-text-color="#ffd04b" background-color="#232323"  text-color="#fff"
                router>
                <!-- <el-menu-item index="/admin/console">
                    <el-icon>
                        <Histogram />
                    </el-icon>
                    <span>工作台</span>  
                </el-menu-item> -->
                <el-menu-item index="/admin/announcement">
                    <el-icon>
                        <InfoFilled />
                    </el-icon>
                    <span>系统公告</span>  
                </el-menu-item>
                   <el-sub-menu index="1">
                    <template #title>
                        <el-icon>
                            <Menu />
                        </el-icon>
                        <span>分类管理</span>
                    </template>

                <el-menu-item index="/admin/college">
                    <el-icon>
                        <Management />
                    </el-icon>
                    <span>学院分类管理</span>
                </el-menu-item>
                 <el-menu-item index="/admin/dormitory">
                    <el-icon>
                        <HomeFilled />
                    </el-icon>
                    <span>宿舍分类管理</span>
                </el-menu-item> 
                 <el-menu-item index="/admin/forum">
                    <el-icon>
                        <Grid  />
                    </el-icon>
                    <span>论坛分类管理</span>
                </el-menu-item> 
                <el-menu-item index="/admin/category">
                    <el-icon>
                        <Menu />
                    </el-icon>
                    <span>商品分类管理</span>
                </el-menu-item>
                </el-sub-menu>

                <el-menu-item index="/admin/member">
                    <el-icon>
                        <Avatar />
                    </el-icon>
                    <span>用户管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/goods"> 
                    <el-icon>
                        <List />
                    </el-icon>
                    <span>商品管理</span>
                </el-menu-item>

                 <el-menu-item index="/admin/seek"> 
                    <el-icon><HelpFilled /></el-icon>
                    <span>求购管理</span>
                </el-menu-item>

                <el-menu-item index="/admin/address"> 
                    <el-icon><Promotion  /></el-icon>
                    <span>地址管理</span>
                </el-menu-item>                     
                <el-menu-item index="/admin/posts"> 
                    <el-icon><Comment  /></el-icon>
                    <span>论坛管理</span>
                </el-menu-item>              
                
                 <el-menu-item index="/admin/orders"> 
                    <el-icon><Checked   /></el-icon>
                    <span>订单管理</span>
                </el-menu-item>
                <el-sub-menu index="2">
                    <template #title>
                        <el-icon>
                            <UserFilled />
                        </el-icon>
                        <span>个人中心</span>
                    </template>

                    <el-menu-item index="/admin/info">
                        <el-icon>
                            <User />
                        </el-icon>
                        <span>基本资料</span>
                    </el-menu-item>
                    <el-menu-item index="/admin/resetPassword">
                        <el-icon>
                            <EditPen />
                        </el-icon>
                        <span>重置密码</span>
                    </el-menu-item>
                </el-sub-menu>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 头部区域 --> 
            <el-header>
                <div style="color:black">欢迎您：<strong>{{ userInfoStore.info.name }}</strong></div>
                <!-- 下拉菜单 -->
                <!-- command: 条目被点击后会触发,在事件函数上可以声明一个参数,接收条目对应的指令 -->
                <el-dropdown placement="bottom-end" @command="handleCommand">
                    <span class="el-dropdown__box">
                        <el-avatar :src="userInfoStore.info.avatar? userInfoStore.info.avatar:avatar" />
                        <el-icon>
                            <CaretBottom />
                        </el-icon>
                    </span>
                    <template #dropdown>
                        <el-dropdown-menu>
                            <el-dropdown-item command="info" :icon="User">基本资料</el-dropdown-item>
                            <el-dropdown-item command="resetPassword" :icon="EditPen">重置密码</el-dropdown-item>
                            <el-dropdown-item command="logout" :icon="SwitchButton">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </template>
                </el-dropdown>
            </el-header>
            <!-- 中间区域 -->
            <el-main>
                <!-- <div style="width: 1290px; height: 570px;border: 1px solid red;">
                    内容展示区
                </div> -->
                <router-view></router-view>
            </el-main>
            <!-- 底部区域 -->
           
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
.layout-container {
    height: 100vh;

    .el-aside {
        background-color: #232323;

        &__logo {
            height: 120px;
            background: url('@/assets/logo.png') no-repeat center / 120px auto;
        }

        .el-menu {
            border-right: none;
        }
    }

    .el-header {
        background-color: #fff;
        display: flex;
        align-items: center;
        justify-content: space-between;

        .el-dropdown__box {
            display: flex;
            align-items: center;

            .el-icon {
                color: #999;
                margin-left: 10px;
            }

            &:active,
            &:focus {
                outline: none;
            }
        }
    }

    .el-footer {
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 14px;
        color: #666;
    }
}
</style>