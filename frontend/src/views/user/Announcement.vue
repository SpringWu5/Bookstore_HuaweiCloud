<script setup>
import {
    Edit,
    Delete,
    Lock,
    Unlock
} from '@element-plus/icons-vue'
import { ref } from 'vue'

//引入登录人的个人信息
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();

const userInfo = ref({...userInfoStore.info})

//引入路由
import {useRouter} from 'vue-router'
const router = useRouter();

const announcement = ref([
    {
        "id": '',
        "name": '',
        "content": '',
        "status": '',
        "createTime": '',
        "updateTime": '',
        "createUser": '',
        "updateUser": ''
    }
])

//声明一个异步的函数
import { userAnnouncementListService} from '@/api/announcement.js'
const announcementList = async () => {
    let result = await userAnnouncementListService();
    announcement.value = result.data;

}
announcementList();

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>系统公告</span>
            </div>
        </template>
                <div class="product-container">
    <div class="product-item" v-for="announcements in announcement" :key="announcements.id">
      <div class="right-column">
        <p class="product-title">
  {{ announcements.name }}&nbsp;
   </p>
                                                   
        <div class="product-content">{{ announcements.content }}  </div>
      </div>
    </div>
  </div>
    </el-card>
</template>

<style lang="scss" scoped>
.page-container {
    // min-height: 100%;
    // box-sizing: border-box;

      .header {
    display: flex;
    align-items: center;
    height: 20px; /* 设置高度为20像素 */
    justify-content: space-between;
    font-size: 18px; /* 设置字体大小为16像素 */
    font-weight: bold; /* 设置为加粗 */
    color: black; /* 设置颜色为黑色 */
  }
}

.product-item {
  display: flex;
  align-items: flex-start; /* 将左侧和右侧内容顶部对齐 */
  background-color: #ffffff; /* 设置背景色为浅蓝色 */
  border-radius: 8px; /* 设置边框圆角 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  padding: 15px; /* 添加内边距 */
  margin: 20px 250px; /* 设置外边距为10px */
}

.left-column {
  margin-right: 20px; /* 设置左右间距 */
}

.rounded {
  border-radius: 50%; /* 使用 border-radius 设置圆形 */
  width: 50px; /* 设置头像的宽度 */
  height: 50px; /* 设置头像的高度 */
  
}
.product-title{
        font-size: 20px; /* 设置字体大小为16像素 */
        font-weight: bold; /* 设置为加粗 */
        color: black; /* 设置颜色为黑色 */
}
.product-time{
        font-size: 15px; /* 设置字体大小为16像素 */
        color: grey; /* 设置颜色为灰色 */
}
.product-description{
        font-size: 16px; /* 设置字体大小为16像素 */
        color: black; /* 设置颜色为灰色 */
}

.product-image {
  max-width: 150px; /* 设置图片最大宽度 */
  height: auto; /* 让高度自适应 */
}

  .resolved { color: green; }
  .unresolved { color: red; }
</style>