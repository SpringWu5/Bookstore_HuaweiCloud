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

//求购分类数据模型
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

 
//求购列表数据模型
const seek = ref([
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

//求购详情
const seekById = ref([
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

//修改表单数据模型
const seekModel = ref({
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
const seekId=ref('')

//求购搜索时选中的求购分类id
const categoryId=ref('')

//求购搜索时选中的成色id
const olds=ref('')

//求购搜索时选中的状态
const status=ref('')

//求购搜索时输入的姓名
const name=ref('')

//求购搜索时输入的排序方式
const sortBy=ref('')

//求购搜索时输入的排序顺序
const isAsc=ref('')

//分页条数据模型
const page = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
        seekList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    page.value = num
        seekList();
}

    //回显求购分类
    import { goodsCategoryListService} from '@/api/goodsCategory.js'
    import { commentAddService, commentListService } from "@/api/comment.js";
    import { userSeekListService ,seekAddService,seekUpdateService,seekDeleteService,userStartStopService} from '@/api/seek.js'
    const goodsCategoryList = async () => {
        let result = await goodsCategoryListService();
        category.value = result.data;
    }    

    //图片上传成功的回调函数
    const uploadSuccess = (result)=>{
    seekModel.value.image = result.data;
}
    
    //获取求购列表数据
    const seekList = async () => {
        let params = {
            page: page.value,
            pageSize: pageSize.value,
            name: name.value ? name.value : null,
            categoryId: categoryId.value ? categoryId.value : null,
            status: status.value ? status.value : null,
            sortBy: sortBy.value ? sortBy.value : null,
            isAsc: isAsc.value ? isAsc.value : null
        }
        let result = await userSeekListService (params);
        //渲染视图
        total.value = result.data.total;
        seek.value = result.data.records;
    
        //处理数据,给数据模型扩展一个属性categoryName,分类名称
        for (let i = 0; i < seek.value.length; i++) {
            seek.value[i].description = seek.value[i].description.replace(/<p>/g, '').replace(/<\/p>/g, '');
            let good = seek.value[i];
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
    goodsCategoryList();
   //查询全部求购
    seekList();

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog = (row) => {
    visibleDrawer.value = true; title.value = '修改求购'
    //数据拷贝
    seekModel.value.description = row.description;
    seekModel.value.name=row.name;
    seekModel.value.image=row.image;
    seekModel.value.categoryId=row.categoryId;  
    seekModel.value.popularity=row.popularity;  
    //扩展id属性,将来需要传递给后台,完成分类的修改
    seekModel.value.id = row.id
} 

//修改求购
const updateGoods = async () => {
    //调用接口
    let result = await seekUpdateService(seekModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')
    //调用获取所有分类的函数
    seekList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//新增求购
const addGoods = async () => {
    //调用接口
    let result = await seekAddService(seekModel.value);

    ElMessage.success(result.msg ? result.msg : '新增成功')
    //调用获取所有分类的函数
    seekList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//清空模型的数据
const clearData = () => {
    seekModel.value.id= " ";
    seekModel.value.description= " ";
    seekModel.value.name='';
    seekModel.value.image='';
    seekModel.value.categoryId=''; 
    seekModel.value.popularity=''; 
}
//删除求购
import {ElMessageBox} from 'element-plus'
const deleteUser = (row) => { 

    //提示求购  确认框
  ElMessageBox.confirm(
    '你确认要删除该求购信息吗?',
    '温馨提示',
    {
        confirmButtonText: '确认', 
        cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { 
         //调用接口
        let result = await seekDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
        //刷新列表
        seekList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '求购取消了删除',
      })
    })
}
//启用禁用
const statususer = (row,status) => {
    //提示求购  确认框
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
            seekList();
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
            seekList();
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
const showCommentTemp = (seeks) =>{
  seekById.value = seeks;
  showComment(seeks.id)
}

//获取评论列表
const showComment = async(seeksId) =>{
    //获取评论列表
  let foreignName = "seek";
  let foreignId = seeksId;
  seekId.value = seeksId;
  let result2 = await commentListService(foreignName, foreignId);
  comment.value = result2.data;
    dialogVisible3.value=true;
}

//新增评论
const commentSubmit = async () => {
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "seek";
  commentModel.value.foreignId = seekId.value;
  //调用接口
  let result = await commentAddService(commentModel.value);
  ElMessage.success(result.msg ? result.msg : "评论成功");
  commentModel.value.content = "";
  showComment(seekId.value);
};

//回复temp
const reply = (pid,target) => {
  commentModel.value.pid = pid;
  commentModel.value.target = target;
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "seek";
  commentModel.value.foreignId = seekId.value;
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
  showComment(seekId.value);
};
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>求购专区</span>
                <div class="extra">
                <el-button type="primary" @click="visibleDrawer = true; title = '新增求购';clearData()">求购</el-button>
                </div>
            </div>
        </template>
        <!-- 求购列表 --> 
         <div class="product-container">
    <div class="product-item" v-for="seeks in seek" :key="seeks.id">
      <div class="left-column">
        <img class="product-userAvatar rounded" :src="seeks.userAvatar" :alt="seeks.userAvatar">
      </div>
      <div class="right-column">
        <div class="product-title">
  {{ seeks.name }}&nbsp;
  <span :style="{ backgroundColor: seeks.status === 1 ? 'red' : 'green',color: 'white' }">
    {{ seeks.statusName }}
  </span>
</div>
                                                   
        <div class="product-time">{{ seeks.userName }}  {{ seeks.updateTime }}</div>
        <img class="product-image" :src="seeks.image" :alt="seeks.name">
        <div class="product-description">{{ seeks.description }}</div>
        <el-button type="text" @click="showCommentTemp(seeks)" >查看详情</el-button>
      </div>
    </div>
  </div>
        <!-- 分页条 -->
       <el-pagination v-model:current-page="page" v-model:page-size="pageSize" :page-sizes="[5 ,10, 15]"
            layout="jumper, total, sizes, prev, pager, next" background :total="total" @size-change="onSizeChange"
            @current-change="onCurrentChange" style="margin-top: 20px; justify-content: flex-end" />
            <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer"  :title="title" direction="rtl" size="50%">
            <!-- 修改/新增求购表单 -->
            <el-form :model="seekModel" label-width="100px" >
                <el-form-item label="求购名" >
                    <el-input v-model="seekModel.name" placeholder="请输入求购名"></el-input>
                </el-form-item>            
                <el-form-item label="求购图片">

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
                        <img v-if="seekModel.image" :src="seekModel.image" class="image" />
                        <el-icon v-else class="image-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>   
                <el-form-item label="求购分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="seekModel.categoryId">
                        <el-option v-for="c in category" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="求购详细描述">
                    <div class="editor">
                    <quill-editor
                        theme="snow"
                        v-model:content="seekModel.description"
                        contentType="html"
                        >
                    </quill-editor>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button @click="visibleDrawer = false">取消</el-button>
                    <el-button type="primary" @click="title == '新增求购' ?addGoods():updateGoods()">确认</el-button>

                </el-form-item>
            </el-form>
        </el-drawer>

            <!-- 帖子评论弹窗 -->
    <el-dialog v-model="dialogVisible3" title="帖子详情" width="60%">
         <!-- //帖子详情 -->
          <div class="product-seek" v-if="seekById">
      <div class="left-seek">
        <img class="product-userAvatar rounded" :src="seekById.userAvatar" :alt="seekById.userAvatar">
      </div>
      <div class="right-seek">
        <div class="seek-title">
  {{ seekById.name }}&nbsp;
   </div>
                                                   
        <div class="seek-time">{{ seekById.userName }}  {{ seekById.updateTime }}</div>
        <img class="seek-image" :src="seekById.image" :alt="seekById.name">
        <div class="seek-description">{{ seekById.description }}</div>
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
  background-color: #e3f2fd; /* 设置背景色为浅蓝色 */
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

  .seek-title {
  font-weight: bold;
  font-size: 1.2em;
  margin-bottom: 5px;
}

.seek-time {
  color: #999;
  margin-bottom: 10px;
}

.seek-image {
  max-width: 100%;
  height: auto;
  margin: 10px 0;
}

.seek-description {
  margin: 10px 0;
}
</style>