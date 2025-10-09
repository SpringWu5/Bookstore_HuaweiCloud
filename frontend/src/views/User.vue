<script setup>
import {
    Management,
    Promotion,
    UserFilled,
    User,
    Crop,
    EditPen,
    SwitchButton,
    CaretBottom,
    Document,
    Menu as IconMenu,
    Setting,
    Shop,
    ChatDotRound,
    Bell,
    House,
    Trophy,
    Goods,
    CirclePlus,
    Collection,
    MessageBox,
    SoldOut,
    Pointer,
    Tickets,
    Key
} from '@element-plus/icons-vue'
import avatar from '@/assets/default.png'

import {ref} from 'vue'
import {userInfoService} from '@/api/user.js'
import useUserInfoStore from '@/stores/userInfo.js'
import {useTokenStore} from '@/stores/token.js'
import AiAssistant from '@/components/AiAssistant.vue'
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
        router.push('/user/person/'+command) 
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
        <!-- AI Assistant Component -->
        <AiAssistant />
        <!-- 头部和菜单区域 -->
        <el-header class="header-menu-container">
            <div class="el-aside__logo"></div>
             <div class="logo_name">智能二手交易平台</div>
            <el-menu ref="menu" active-text-color="#fff" background-color="#fff" text-color="#232323" router mode="horizontal">
                <el-menu-item index = "/user/popularity">
          
                    <span>热卖专区</span>
                </el-menu-item >
                <el-menu-item index = "/user/shop">
                    <span>社区广场</span>
                </el-menu-item>
                <el-menu-item  index = "/user/seek">
                    <span>求购专区</span>
                </el-menu-item>                 
                <el-menu-item  index = "/user/announcement">
                    <span>系统公告</span>
                </el-menu-item>            
                <!-- <el-menu-item index = "/user/chat">
                    <span>聊天交易</span>
                </el-menu-item>                 -->
                <el-menu-item  index = "/user/person">
                    <span>个人中心</span>
                </el-menu-item>
            </el-menu>
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

        <!-- 右侧主区域 -->
        <el-container>
            <!-- 中间区域 -->
            <el-main>
                <router-view></router-view>
            </el-main>
        </el-container>
          <!-- 底部区域 -->
            
    </el-container>
</template>

<style lang="scss" scoped>
$main-orange: #ff6700;
$main-orange-dark: #e55300;
$main-orange-light: #fff3e0;

.layout-container {
    height: 100vh;
}

.header-menu-container {
    display: flex;
    align-items: center;
    background: linear-gradient(90deg, $main-orange 80%, $main-orange-dark 100%);
    justify-content: space-between;
    box-shadow: 0 2px 12px rgba(255, 103, 0, 0.08);
    border-bottom-left-radius: 0;
    border-bottom-right-radius: 0;
    padding: 0 32px;
    min-height: 75px;
}

.el-aside__logo {
  height: 60px;
  width: 60px;
  background: url('@/assets/logo.png') no-repeat center / contain;
  margin-right: 10px;
}
.logo_name {
  font-size: 26px;
  font-weight: bold;
  color: #fff;
  text-align: left;
  margin-left: 0;
  letter-spacing: 2px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.el-menu {
  background: transparent;
  border: none;
  width: auto;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: none;
  margin-left: 40px;
}

.el-menu-item {
  font-size: 18px;
  margin: 0 36px;
  color: #fff;
  font-weight: 600;
  background: transparent !important;
  border-radius: 0;
  transition: background 0.2s, color 0.2s;
  position: relative;
  padding: 0 18px;
  height: 60px;
  line-height: 60px;
  box-shadow: none;
  &:hover {
    background: $main-orange-dark !important;
    color: #fff;
  }
}
.el-menu-item.is-active {
  background: $main-orange-dark !important;
  color: #fff;
  font-weight: bold;
  &::after {
    content: '';
    display: block;
    position: absolute;
    left: 50%;
    bottom: 0;
    transform: translateX(-50%);
    width: 60%;
    height: 4px;
    background: #fff;
    border-radius: 2px 2px 0 0;
  }
}

.el-dropdown__box {
    display: flex;
    align-items: center;
    margin-left: 32px;
    .el-icon {
        color: #fff;
        margin-left: 10px;
    }
    &:active,
    &:focus {
        outline: none;
    }
}

.el-avatar {
  border: 2px solid #fff3e0;
  box-shadow: 0 2px 8px #ffecd2;
}

.el-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #666;
    background: $main-orange-light;
    border-top-left-radius: 12px;
    border-top-right-radius: 12px;
}

.fullscreen-content {
    width: 100%;
    height: 100%;
    background-color: #f5f7fa;
    padding: 0;
    box-sizing: border-box;
    border: 0;
}
.el-main {
    padding: 0;
}
</style>
