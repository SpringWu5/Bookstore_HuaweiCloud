<script setup>
import {
    Edit,
    Delete,
     Lock,
    Unlock,
    CircleCheck
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
    "status": 1,
    "commentTotal": '',
    "popularity": '',
    "createTime": '',
    "updateTime": '',
    "createUser": '',
    "updateUser": ''
  }
])

//修改表单数据模型
const seekModel = ref({
    categoryId: '',
    description:'',

    image:'',
    name:'',
    status:1,
    popularity: ''
})


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
    import { seekListService,seekAddService,seekUpdateService,seekDeleteService,userStartStopService} from '@/api/seek.js'
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
        let result = await seekListService(params);
        //渲染视图
        total.value = result.data.total;
        seek.value = result.data.records;
    
        //处理数据,给数据模型扩展一个属性categoryName,分类名称
        for (let i = 0; i < seek.value.length; i++) {
            let good = seek.value[i];
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
    seekModel.value.status=String(row.status);  
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
    seekModel.value.status=''; 
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
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>求购管理</span>
                <div class="extra">
                <el-button type="primary" @click="visibleDrawer = true; title = '新增求购';clearData()">新增求购</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="求购分类：">
                <el-select style="width: 160px" placeholder="请选择" v-model="categoryId">
                    <el-option 
                        v-for="c in category" 
                        :key="c.id" 
                        :label="c.name"
                        :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item width="80px" label="排序：">
                <el-select style="width: 160px" placeholder="请选择" v-model="sortBy">
                    <el-option label="热度" value="popularity"></el-option>
                    <el-option label="最新" value="update_time"></el-option>
                </el-select>
            </el-form-item>  

            <el-form-item label="权限状态：">
                <el-select style="width: 160px" placeholder="请选择" v-model="status">
                    <el-option label="已解决" value=2></el-option>
                    <el-option label="未解决" value=1></el-option>
                    <el-option label="取消" value=0></el-option>
                </el-select>
            </el-form-item>
 
            <el-form-item  style="width: 200px" label="求购名">
                     <el-input v-model="name" placeholder="请输入求购名" clearable />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="seekList">搜索</el-button>
                <el-button @click="categoryId = '';sortBy= ''; status = '';name='';seekList()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 求购列表 --> 
        <el-table :data="seek" style="width: 100%">
            <el-table-column label="求购名"  prop="name"></el-table-column>
            <el-table-column label="图片" prop="image"> 
                <template #default="scope">
                    <img v-if="scope.row.image" :src="scope.row.image" class="image" />
                </template>
            </el-table-column>
            <el-table-column label="状态"   prop="status">
                <template #default="scope">
                    {{scope.row.status != 0 ? 
                    (scope.row.status === 2 ? '已解决' : '未解决') 
                    : '取消'}}
                </template>
            </el-table-column>
            <el-table-column label="求购分类" prop="categoryName"></el-table-column>
            <el-table-column label="评论数"  prop="commentTotal"></el-table-column>
            <el-table-column label="人气值" prop="popularity"></el-table-column>
            <el-table-column label="更新时间" prop="updateTime" width="150"></el-table-column>
            <el-table-column label="操作" width="150"> 
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteUser(row)"></el-button>
                    <el-button v-if="row.status == 1" :icon="Unlock" circle plain type="warning" @click="statususer(row,0)"></el-button>
                    <el-button v-if="row.status == 0" :icon="Lock" circle plain type="info" @click="statususer(row,1)"></el-button>
                    <el-button v-if="row.status == 2" :icon="CircleCheck" circle plain type="success"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>
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
                <el-form-item label="人气值" > 
                    <el-input v-model="seekModel.popularity" placeholder="请输入人气值"></el-input>
                </el-form-item>
                 <el-form-item label="权限状态：">
                <el-select style="width: 160px" placeholder="请选择" v-model="seekModel.status">
                    <el-option label="已解决" value="2"></el-option>
                    <el-option label="未解决" value="1"></el-option>
                    <el-option label="取消" value="0"></el-option>
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
    </el-card>
</template>
<style lang="scss" scoped>
.page-container {
    // min-height: 100%;
    // box-sizing: border-box;

    .header {
        display: flex;
        align-items: center;
        justify-content: space-between;
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
</style>