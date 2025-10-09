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
    StarFilled,
    List,
    HelpFilled,
    CirclePlusFilled ,
    RemoveFilled,
} from '@element-plus/icons-vue'

</script>

<template>
    <el-container class="layout-container">
        <!-- 左侧菜单 -->
        <el-aside width="200px">
            <el-menu active-text-color="#ffd04b" background-color="#73b8fe"  text-color="#fff"
                router>
                 <el-menu-item index = "/user/person/info">
                         <el-icon>
                            <UserFilled />
                        </el-icon>
                    <span>基本资料</span>
                </el-menu-item>
                <el-menu-item  index = "/user/person/myGoods">
                    <el-icon>
                        <List />
                    </el-icon>
                    <span>我的商品</span>
                </el-menu-item>

                <el-menu-item  index = "/user/person/mySeek">
                    <el-icon>
                        <HelpFilled />
                    </el-icon>
                    <span>我的求购</span> 
                </el-menu-item>

                <el-menu-item  index = "/user/person/buyOrders">
                    <el-icon>
                        <CirclePlusFilled  />
                    </el-icon>
                    <span>买家订单</span>
                </el-menu-item>

                <el-menu-item  index = "/user/person/sellOrders">
                    <el-icon>
                        <RemoveFilled />
                    </el-icon>
                    <span>卖家订单</span>
                </el-menu-item>

                <el-menu-item  index = "/user/person/address">
                  <el-icon>
                    <Promotion />
                    </el-icon>
                  <span>地址管理</span>
                </el-menu-item>                
                <el-menu-item  index = "/user/person/collection">
                  <el-icon>
                    <StarFilled />
                    </el-icon>
                  <span>我的收藏</span>
                </el-menu-item>
                <el-menu-item  index = "/user/person/resetPassword">
            <el-icon>
              <EditPen />
            </el-icon>
                  <span>修改密码</span>
                </el-menu-item>
            </el-menu>
        </el-aside>
        <!-- 右侧主区域 -->
        <el-container>
            <!-- 中间区域 -->
            <el-main>
           <router-view></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<style lang="scss" scoped>
$main-orange: #ff6700;
$main-orange-dark: #e55300;
$main-orange-light: #fff3e0;
$sidebar-gray: #f7f8fa;
$sidebar-border: #ececec;
$sidebar-active: #ff9800;
$sidebar-text: #333;

.layout-container {
    height: 116vh;
    .el-aside {
        background: linear-gradient(180deg, $main-orange-light 80%, #f7f8fa 100%);
        box-shadow: 2px 0 16px rgba(255, 103, 0, 0.06);
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
        display: flex;
        flex-direction: column;
        align-items: center;
        padding-top: 0;
        min-width: 210px;
        border-right: 1.5px solid $sidebar-border;
        .el-menu {
            border-right: 0;
            background: transparent;
            width: 100%;
            margin-top: 0;
            .el-menu-item {
                margin: 12px 0;
                border-radius: 0;
                font-size: 18px;
                font-weight: 500;
                color: $sidebar-text;
                background: transparent;
                transition: background 0.2s, color 0.2s;
                display: flex;
                align-items: center;
                padding: 0 18px;
                height: 48px;
                line-height: 48px;
                .el-icon {
                  font-size: 22px;
                  margin-right: 12px;
                  color: $main-orange;
                }
                &:hover {
                    background: $main-orange-light;
                    color: $main-orange;
                }
            }
            .el-menu-item.is-active {
                background: $sidebar-active;
                color: #fff;
                font-weight: bold;
                box-shadow: 0 2px 8px #ffecd2;
                .el-icon {
                  color: #fff;
                }
            }
        }
    }
}
</style>