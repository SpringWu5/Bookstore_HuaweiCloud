import { createRouter, createWebHistory } from 'vue-router'

//导入组件
import LoginVue from '@/views/Login.vue'
import AdminVue from '@/views/Admin.vue'


import CollegeCategoryVue from '@/views/admin/CollegeCategory.vue'
import DormitoryCategoryVue from '@/views/admin/DormitoryCategory.vue'
import ForumCategoryVue from '@/views/admin/ForumCategory.vue'
import GoodsCategoryVue from '@/views/admin/GoodsCategory.vue'
import MemberManageVue from '@/views/admin/MemberManage.vue'
import AnnouncementManageVue from '@/views/admin/AnnouncementManage.vue'
import GoodsManageVue from '@/views/admin/GoodsManage.vue'
import SeekManageVue from '@/views/admin/SeekManage.vue'
import PostsManageVue from '@/views/admin/PostsManage.vue'
import ConsoleManageVue from '@/views/admin/ConsoleManage.vue'
import AddressManageVue from '@/views/admin/AddressManage.vue'
import OrdersManageVue from '@/views/admin/OrdersManage.vue'
import AdminInfoVue from '@/views/admin/AdminInfo.vue'
import AdminResetPasswordVue from '@/views/admin/AdminResetPassword.vue'


import UserVue from '@/views/User.vue'
import ShopVue from '@/views/user/Shop.vue'
import PopularityVue from '@/views/user/Popularity.vue'
import SeekVue from '@/views/user/Seek.vue'
import ChatVue from '@/views/user/Chat.vue'
import AnnouncementVue from '@/views/user/Announcement.vue'
import AddressVue from '@/views/user/Address.vue'
import CollectionVue from '@/views/user/Collection.vue'
import MyGoodsVue from '@/views/user/MyGoods.vue'
import MySeekVue from '@/views/user/MySeek.vue'
import BuyOrdersVue from '@/views/user/BuyOrders.vue'
import SellOrdersVue from '@/views/user/SellOrders.vue'
import PersonVue from '@/views/user/Person.vue'
import UserInfoVue from '@/views/user/UserInfo.vue'
import UserResetPasswordVue from '@/views/user/UserResetPassword.vue'
//定义路由关系
const routes = [
    { path: '/', component: LoginVue , 
        //重定向
        redirect:'/login'}, 
    { path: '/login', component: LoginVue },
    {
        path: '/admin', component: AdminVue,
        //重定向 
        redirect:'/admin/member',  
        //子路由
        children: [ 
            { path: '/admin/college', component: CollegeCategoryVue },
            { path: '/admin/dormitory', component: DormitoryCategoryVue },
            { path: '/admin/forum', component: ForumCategoryVue },
            { path: '/admin/category', component: GoodsCategoryVue },
            { path: '/admin/announcement', component: AnnouncementManageVue },
            { path: '/admin/member', component: MemberManageVue },
            { path: '/admin/goods', component: GoodsManageVue },
            { path: '/admin/seek', component: SeekManageVue },
            { path: '/admin/posts', component: PostsManageVue },
            { path: '/admin/address', component: AddressManageVue },
            { path: '/admin/orders', component: OrdersManageVue },
            { path: '/admin/console', component: ConsoleManageVue },
            { path: '/admin/info', component: AdminInfoVue },
            { path: '/admin/resetPassword', component: AdminResetPasswordVue }
        ]
    },
    {
        path: '/user', component: UserVue,
        //重定向
        redirect:'/user/popularity', 
        // 子路由
        children: [
            { path: '/user/shop', component: ShopVue },
            { path: '/user/popularity', component: PopularityVue },
            { path: '/user/announcement', component: AnnouncementVue },
            { path: '/user/seek', component: SeekVue },
            { path: '/user/chat', component: ChatVue },
            { path: '/user/person', component: PersonVue ,
                //重定向
                redirect:'/user/person/info', 
                // 子路由
                 children: [
                    { path: '/user/person/info', component: UserInfoVue },
                    { path: '/user/person/address', component: AddressVue },
                    { path: '/user/person/collection', component: CollectionVue },
                    { path: '/user/person/myGoods', component: MyGoodsVue },
                    { path: '/user/person/mySeek', component: MySeekVue },
                    { path: '/user/person/buyOrders', component: BuyOrdersVue },
                    { path: '/user/person/sellOrders', component: SellOrdersVue },
                    { path: '/user/person/resetPassword', component: UserResetPasswordVue },
                ]
            }
        ]
    },
]

//创建路由器
const router = createRouter({
    history: createWebHistory(),
    routes: routes
})

//导出路由
export default router
