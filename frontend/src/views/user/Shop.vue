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

//导入token
import { useTokenStore } from '@/stores/token.js';
const tokenStore = useTokenStore();

//引入路由
import {useRouter} from 'vue-router'
const router = useRouter();

import { QuillEditor } from '@vueup/vue-quill'
import '@vueup/vue-quill/dist/vue-quill.snow.css'

//帖子分类数据模型
const category = ref([
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

 
//帖子列表数据模型
const posts = ref([
{
    "id": '',
    "name": '',
    "categoryId": '',
    "image": '',
    "description": " ",
    "status": '',
    "updateTime": '',
    "createUser": '',
    "userAvatar": '',
    "userName": '',
  }
])

//帖子列表数据模型
const postsById = ref(
{
    "id": '',
    "name": '',
    "categoryId": '',
    "image": '',
    "description": " ",
    "status": '',
    "updateTime": '',
    "createUser": '',
    "userAvatar": '',
    "userName": '',
  }
)

//修改表单数据模型
const postsModel = ref({
    categoryId: '',
    description:'',
    id:'',
    image:'',
    name:'',
    popularity: ''
})


//新增评论数据模型
const commentModel = ref({
  content: "",
  createTime: "",
  createUser: 0,
  foreignId: 0,
  foreignName: "",
  pid: 0,
  target: "",
  userId: 0,
  userName: "",
});

//评论列表数据模型
const comment = ref([
  {
    id: "",
    content: "",
    createTime: "",
    createUser: 0,
    avatar: "",
    foreignId: 0,
    foreignName: "",
    pid: "",
    target: "",
    userId: 0,
    userName: "",
    "children": [
				{
					"avatar": "",
					"content": "",
					"createTime": "",
					"createUser": 0,
					"foreignId": 0,
					"foreignName": "",
					"id": 0,
					"pid": 0,
					"target": "",
					"userId": 0,
					"userName": ""
				}
			],
  },
]);

//查询/新增哪个论坛的评论
const postsId=ref('')

//帖子搜索时选中的帖子分类id
const categoryId=ref('')

//帖子搜索时选中的成色id
const olds=ref('')

//帖子搜索时选中的状态
const status=ref('')

//帖子搜索时输入的姓名
const name=ref('')

//帖子搜索时输入的排序方式
const sortBy=ref('')

//帖子搜索时输入的排序顺序
const isAsc=ref('')

//分页条数据模型
const page = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
        postsList();
} 
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    page.value = num
        postsList();
}

    //回显帖子分类
    import { forumListService} from '@/api/forum.js'
    import { commentAddService, commentListService } from "@/api/comment.js";
    import { userPostsListService ,postsAddService,postsUpdateService,postsDeleteService,userStartStopService} from '@/api/posts.js'
    const forumList = async () => {
        let result = await forumListService();
        category.value = result.data;
    }    

    //图片上传成功的回调函数
    const uploadSuccess = (result)=>{
    postsModel.value.image = result.data;
}
    
    //获取帖子列表数据
    const postsList = async () => {
        let params = {
            page: page.value,
            pageSize: pageSize.value,
            name: name.value ? name.value : null,
            categoryId: categoryId.value ? categoryId.value : null,
            status: status.value ? status.value : null,
            sortBy: sortBy.value ? sortBy.value : null,
            isAsc: isAsc.value ? isAsc.value : null
        }
        let result = await userPostsListService (params);
        //渲染视图
        total.value = result.data.total;
        posts.value = result.data.records;
    
        //处理数据,给数据模型扩展一个属性categoryName,分类名称
        for (let i = 0; i < posts.value.length; i++) {
            posts.value[i].description = posts.value[i].description.replace(/<p>/g, '').replace(/<\/p>/g, '');
            posts.value[i].description = posts.value[i].description.replace(/<\/?[^>]+(>|$)/g, "");
            let good = posts.value[i];
            if(good.status == 1 ) {good.statusName =" 未解决";} 
            if(good.status == 2 ) {good.statusName = "已解决";}
            for (let j = 0; j < category.value.length; j++) {
                if (good.categoryId == category.value[j].id) {
                    good.categoryName = category.value[j].name;
                }
            }
        }
    }
    //查询全部分类
    forumList();
   //查询全部帖子
    postsList();

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog = (row) => {
    visibleDrawer.value = true; title.value = '修改帖子'
    //数据拷贝
    postsModel.value.description = row.description;
    postsModel.value.name=row.name;
    postsModel.value.image=row.image;
    postsModel.value.categoryId=row.categoryId;  
    postsModel.value.popularity=row.popularity;  
    //扩展id属性,将来需要传递给后台,完成分类的修改
    postsModel.value.id = row.id
} 

//修改帖子
const updateGoods = async () => {
    //调用接口
    let result = await postsUpdateService(postsModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')
    //调用获取所有分类的函数
    postsList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//新增帖子
const addGoods = async () => {
    //调用接口
    let result = await postsAddService(postsModel.value);

    ElMessage.success(result.msg ? result.msg : '新增成功')
    //调用获取所有分类的函数
    postsList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//清空模型的数据
const clearData = () => {
    postsModel.value.id= " ";
    postsModel.value.description= " ";
    postsModel.value.name='';
    postsModel.value.image='';
    postsModel.value.categoryId=''; 
    postsModel.value.popularity=''; 
}
//删除帖子
import {ElMessageBox} from 'element-plus'
const deleteUser = (row) => { 

    //提示帖子  确认框
  ElMessageBox.confirm(
    '你确认要删除该帖子信息吗?',
    '温馨提示',
    {
        confirmButtonText: '确认', 
        cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { 
         //调用接口
        let result = await postsDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
        //刷新列表
        postsList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '帖子取消了删除',
      })
    })
}
//启用禁用
const statususer = (row,status) => {
    //提示帖子  确认框
    if(status == 0){
    ElMessageBox.confirm(
        '你确认要禁用该分类信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await userStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '禁用成功',
            })
            //刷新列表
            postsList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '您取消了禁用',
            })
        })
} else{
    ElMessageBox.confirm(
        '你确认要启用该分类信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await userStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '启用成功',
            })
            //刷新列表
            postsList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '您取消了启用',
            })
        })
}
}

//弹窗
const dialogVisible3 = ref(false);
const dialogVisible4 = ref(false);

//获取评论列表(temp,复制帖子数据)
const showCommentTemp = (postss) =>{
  postsById.value = postss;
  showComment(postss.id)
}

//获取评论列表
const showComment = async(postssId) =>{
    //获取评论列表
  let foreignName = "forum";
  let foreignId = postssId;
  postsId.value = postssId;
  let result2 = await commentListService(foreignName, foreignId);
  comment.value = result2.data;
    dialogVisible3.value=true;
}

//新增评论
const commentSubmit = async () => {
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "forum";
  commentModel.value.foreignId = postsId.value;
  //调用接口
  let result = await commentAddService(commentModel.value);
  ElMessage.success(result.msg ? result.msg : "评论成功");
  commentModel.value.content = "";
  showComment(postsId.value);
};

//回复temp
const reply = (pid,target) => {
  commentModel.value.pid = pid;
  commentModel.value.target = target;
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "forum";
  commentModel.value.foreignId = postsId.value;
  dialogVisible4.value = true;
};

//新增回复评论
const saveReply = async () => { 
  //调用接口
  let result = await commentAddService(commentModel.value);
  ElMessage.success(result.msg ? result.msg : "回复成功");

  commentModel.value.content = "";
  commentModel.value.pid = 0;
  commentModel.value.target = "";
  dialogVisible4.value = false;
  showComment(postsId.value);
};

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>论坛专区</span>
                <div class="extra">
                <el-button type="primary" @click="visibleDrawer = true; title = '新增帖子';clearData()">发布帖子</el-button>
                </div>
            </div>
        </template>
        <!-- 帖子列表 --> 
         <div class="product-container">
    <div class="product-item" v-for="postss in posts" :key="postss.id">
      <div class="left-column">
        <img class="product-userAvatar rounded" :src="postss.userAvatar" :alt="postss.userAvatar">
      </div>
      <div class="right-column">
        <div class="product-title">
  {{ postss.name }}&nbsp;
   </div>
                                                   
        <div class="product-time">{{ postss.userName }}  {{ postss.updateTime }}</div>
        <img class="product-image" :src="postss.image" :alt="postss.name">
        <div class="product-description">{{ postss.description }}</div>
        <el-button type="text" @click="showCommentTemp(postss)" >查看详情</el-button>
      </div>
    </div>
  </div>
        <!-- 分页条 -->
       <el-pagination v-model:current-page="page" v-model:page-size="pageSize" :page-sizes="[5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
            <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer"  :title="title" direction="rtl" size="50%">
            <!-- 修改/新增帖子表单 -->
            <el-form :model="postsModel" label-width="100px" >
                <el-form-item label="帖子名" >
                    <el-input v-model="postsModel.name" placeholder="请输入帖子名"></el-input>
                </el-form-item>            
                <el-form-item label="帖子图片">

                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     -->
                   
                    <el-upload class="image-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/admin/common/upload"
                    name="file"
                    :headers="{'token':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="postsModel.image" :src="postsModel.image" class="image" />
                        <el-icon v-else class="image-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>   
                <el-form-item label="帖子分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="postsModel.categoryId">
                        <el-option v-for="c in category" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="帖子详细描述">
                    <div class="editor">
                    <quill-editor
                        theme="snow"
                        v-model:content="postsModel.description"
                        contentType="html"
                        >
                    </quill-editor>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button @click="visibleDrawer = false">取消</el-button>
                    <el-button type="primary" @click="title == '新增帖子' ?addGoods():updateGoods()">确认</el-button>

                </el-form-item>
            </el-form>
        </el-drawer>

          <!-- 帖子评论弹窗 -->
    <el-dialog v-model="dialogVisible3" title="帖子详情" width="60%">
         <!-- //帖子详情 -->
          <div class="product-posts" v-if="postsById">
      <div class="left-posts">
        <img class="product-userAvatar rounded" :src="postsById.userAvatar" :alt="postsById.userAvatar">
      </div>
      <div class="right-posts">
        <div class="posts-title">
  {{ postsById.name }}&nbsp;
   </div>
                                                   
        <div class="posts-time">{{ postsById.userName }}  {{ postsById.updateTime }}</div>
        <img class="posts-image" :src="postsById.image" :alt="postsById.name">
        <div class="posts-description">{{ postsById.description }}</div>
      </div>
    </div>
       <!-- //新增评论 -->
       <p class="userGoods-olds"><b>用户评论：</b></p>
        <div>
        <el-input
          type="textarea"
          placeholder="请输入评论"
          v-model="commentModel.content"
        ></el-input>
        <div style="text-align: right; margin: 10px 0">
          <el-button type="primary" @click="commentSubmit()">提交</el-button>
        </div>
      </div>
      <!-- 评论列表 -->
      
      <div style="margin: 20px 0">
        <div
          style="
            margin: 10px 0;
            font-size: 24px;
            padding: 10px o;
            border-bottom: 1px solid #ccc;
            text-align: left;
          "
        >
          评论列表
        </div> 
        <div style="margin: 20px 0; text-align: left">
          <div
            v-for="item in comment"
            :key="item.id"
            style="padding: 10px 0;  align-items: center"
          >
          <div style="display: flex;">
            <div style="width: 60px; margin-right: 10px">
              <el-avatar :size="50" :src="item.avatar"></el-avatar>
            </div>
            <div style="flex: 1">
              <div style="font-weight: bold; font-size: 18px">
                {{ item.userName }}<span style="margin-left:10px;font-weight: normal; font-size: 15px">{{ item.createTime}}</span>
              </div>
              <div style="margin-top: 7px; color: #666">{{ item.content }}</div>
          <!-- 多级回复 -->
          <div style="text-align:left;">
            <el-button type="text" @click="reply(item.id,item.userName)">回复</el-button>
          </div>
          <!-- 回复列表 -->
          <div v-if="item.children.length" style="margin-left:40px;background-color:aliceblue;padding:10px;border-radius:10px">
            <div v-for="sub in item.children" :key="sub.id">
              <div style="padding:5px 0"><b style="cursor:pointer" @click="reply(sub.pid,sub.userName)">{{sub.userName}} </b><span> 回复 
                <span style="color:cornflowerblue">@{{sub.target}}</span>
               <span style="color:#666;margin-left:10px">{{sub.content}}</span></span>
               <span style="float:right;font-weight: normal; font-size: 13px;color:#666;margin-top:3px">{{ sub.createTime}}</span>
               </div>
            </div>
          </div>
            </div>
          </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <!-- 回复评论弹窗 -->
    <el-dialog v-model="dialogVisible4" title="回复" width="30%">
    <el-form :model="commentModel">
      <el-form-item>
        <el-input  type="textarea"  :rows="4" placeholder="请输入内容"  v-model="commentModel.content" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <el-form-item>
          <el-button
            @click="
              dialogVisible4 = false;
              commentModel.content='';
            "
            >取消</el-button
          >
          <el-button type="primary" @click="saveReply()">确定</el-button>
        </el-form-item>
    </el-dialog>
    </el-card>
</template>
<style lang="scss" scoped>
$main-orange: #ff6700;
$main-orange-dark: #e55300;

.el-button[type="primary"] {
  background: $main-orange !important;
  border: none !important;
  color: #fff !important;
  border-radius: 8px !important;
  font-weight: bold;
  transition: background 0.2s;
}
.el-button[type="primary"]:hover, .el-button[type="primary"]:focus {
  background: $main-orange-dark !important;
  color: #fff !important;
}
.page-container {
    // min-height: 100%;
    // box-sizing: border-box;
     background-color: rgb(245, 245, 245); /* 设置背景颜色 */

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
/* 抽屉样式 */

.editor {
  width: 100%;
  :deep(.ql-editor) {
    min-height: 200px;
  }
}
.image-uploader {
    :deep() {
        .image {
            width: 278px;
            height: 278px;
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

        .el-icon.image-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
.image {
  width: 50px; /* 调整图片显示的大小 */
  height: 50px;
}
// .product-container {
//   display: flex;
//   flex-wrap: nowrap; /* 不换行 */
//   justify-content: center; /* 水平居中 */
// }
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
  

  .product-posts {
  display: flex;
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 10px;
}

.left-posts {
  margin-right: 20px; /* 调整间距以适应布局 */
  flex-shrink: 0; /* 防止头像区域缩小 */
}

.product-userAvatar {
  width: 80px; /* 调整头像大小 */
  height: 80px;
  border-radius: 50%; /* 圆形头像 */
}

.right-posts {
  flex: 1; /* 填充剩余空间 */
}

.posts-title {
  font-weight: bold;
  font-size: 1.2em;
  margin-bottom: 5px;
}

.posts-time {
  color: #999;
  margin-bottom: 10px;
}

.posts-image {
  max-width: 100%;
  height: auto;
  margin: 10px 0;
}

.posts-description {
  margin: 10px 0;
}
</style>