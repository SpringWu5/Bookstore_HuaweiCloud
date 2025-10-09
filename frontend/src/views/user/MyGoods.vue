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

//商品分类数据模型
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

 
//商品列表数据模型
const goods = ref([
{
    "id": '',
    "name": '',
    "categoryId": '',
    "price": '',
    "image": '',
    "olds": '', 
    "description": " ",
    "status": '',
    "collectionTotal": '',
    "praiseTotal": '',
    "commentTotal": '',
    "lookTotal": '',
    "popularity": '',
    "createTime": '',
    "updateTime": '',
    "createUser": '',
    "updateUser": ''
  }
])

//修改表单数据模型
const goodsModel = ref({
    categoryId: '',
    description:'',
    id:'',
    image:'',
    name:'',
    olds:'',
    price:'',
    popularity: ''
})


//商品搜索时选中的商品分类id
const categoryId=ref('')

//商品搜索时选中的成色id
const olds=ref('')

//商品搜索时选中的状态
const status=ref('')

//商品搜索时输入的姓名
const name=ref('')

//商品搜索时输入的排序方式
const sortBy=ref('')

//商品搜索时输入的排序顺序
const isAsc=ref('')

//分页条数据模型
const page = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
        goodsList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    page.value = num
        goodsList();
}

    //回显商品分类
    import { goodsCategoryListService} from '@/api/goodsCategory.js'
    import { userMyGoodsListService,goodsAddService,goodsUpdateService,goodsDeleteService,userStartStopService} from '@/api/goods.js'
    const goodsCategoryList = async () => {
        let result = await goodsCategoryListService();
        category.value = result.data;
    }    

    //图片上传成功的回调函数
    const uploadSuccess = (result)=>{
    goodsModel.value.image = result.data;
}
    
    //获取商品列表数据
    const goodsList = async () => {
        let params = {
            page: page.value,
            pageSize: pageSize.value,
            name: name.value ? name.value : null,
            olds: olds.value ? olds.value : null,
            categoryId: categoryId.value ? categoryId.value : null,
            status: status.value ? status.value : null,
            sortBy: sortBy.value ? sortBy.value : null,
            isAsc: isAsc.value ? isAsc.value : null
        }
        let result = await userMyGoodsListService(params);
        //渲染视图
        total.value = result.data.total;
        goods.value = result.data.records;
    
        //处理数据,给数据模型扩展一个属性categoryName,分类名称
        for (let i = 0; i < goods.value.length; i++) {
            let good = goods.value[i];
            for (let j = 0; j < category.value.length; j++) {
                if (good.categoryId == category.value[j].id) {
                    good.categoryName = category.value[j].name;
                }
            }
        }
    }
    //查询全部分类
    goodsCategoryList();
   //查询全部商品
    goodsList();

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')


//展示编辑弹窗
const showDialog = (row) => {
    visibleDrawer.value = true; title.value = '修改商品'
    //数据拷贝
    goodsModel.value.description = row.description;
    goodsModel.value.name=row.name;
    goodsModel.value.olds=row.olds;
    goodsModel.value.price=row.price;
    goodsModel.value.image=row.image;
    goodsModel.value.categoryId=row.categoryId;  
    goodsModel.value.popularity=row.popularity;  
    //扩展id属性,将来需要传递给后台,完成分类的修改
    goodsModel.value.id = row.id
} 

//修改商品
const updateGoods = async () => {
    //调用接口
    let result = await goodsUpdateService(goodsModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')
    //调用获取所有分类的函数
    goodsList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//新增商品
const addGoods = async () => {
    //调用接口
    let result = await goodsAddService(goodsModel.value);

    ElMessage.success(result.msg ? result.msg : '新增成功')
    //调用获取所有分类的函数
    goodsList();

    //隐藏弹窗
    visibleDrawer.value = false;
}
 
//清空模型的数据
const clearData = () => {
    goodsModel.value.id= " ";
    goodsModel.value.description= " ";
    goodsModel.value.name='';
    goodsModel.value.olds='';
    goodsModel.value.price='';
    goodsModel.value.image='';
    goodsModel.value.categoryId=''; 
    goodsModel.value.popularity=''; 
}
//删除商品
import {ElMessageBox} from 'element-plus'
const deleteUser = (row) => { 

    //提示商品  确认框
  ElMessageBox.confirm(
    '你确认要删除该商品信息吗?',
    '温馨提示',
    {
        confirmButtonText: '确认', 
        cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { 
         //调用接口
        let result = await goodsDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
        //刷新列表
        goodsList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '商品取消了删除',
      })
    })
}
//启用禁用
const statususer = (row,status) => {
    //提示商品  确认框
    if(status == 0){
    ElMessageBox.confirm(
        '你确认要下架此闲置商品吗?',
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
            goodsList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '您取消了禁用',
            })
        })
} else{
    ElMessageBox.confirm(
        '你确认要上架该闲置物品吗?',
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
            goodsList();
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
                <span>商品管理</span>
                <div class="extra">
                <el-button type="primary" @click="visibleDrawer = true; title = '新增商品';clearData()">新增商品</el-button>
                </div>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="商品分类：">
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
                    <el-option label="已售" value="0"></el-option>
                    <el-option label="上架" value="1"></el-option>
                    <el-option label="下架" value="0"></el-option>
                </el-select>
            </el-form-item>
 
            <el-form-item  style="width: 200px" label="商品名">
                     <el-input v-model="name" placeholder="请输入商品名" clearable />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="goodsList">搜索</el-button>
                <el-button @click="categoryId = '';sortBy= ''; status = '';name='';goodsList()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 商品列表 --> 
        <el-table :data="goods" style="width: 100%">
            <el-table-column label="商品名" width="130px" prop="name"></el-table-column>
            <el-table-column label="图片" prop="image"> 
                <template #default="scope">
                    <img v-if="scope.row.image" :src="scope.row.image" class="image" />
                </template>
            </el-table-column>
            <el-table-column label="价格" width="80px" prop="price"></el-table-column>
            <el-table-column label="成色" width="80px" prop="olds"></el-table-column>
            <el-table-column label="状态"  width="80px" prop="status">
                <template #default="scope">
                    {{scope.row.status != 0 ? 
                    (scope.row.status === 2 ? '已售' : '上架') 
                    : '下架'}}
                </template>
            </el-table-column>
            <el-table-column label="商品分类" prop="categoryName"></el-table-column>
            <el-table-column label="收藏数" width="80px" prop="collectionTotal"></el-table-column>
            <el-table-column label="点赞数" width="80px" prop="praiseTotal"></el-table-column>
            <el-table-column label="评论数" width="80px" prop="commentTotal"></el-table-column>
            <el-table-column label="浏览数" width="80px" prop="lookTotal"></el-table-column>
            <el-table-column label="人气值" width="80px" prop="popularity"></el-table-column>
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
            <!-- 修改/新增商品表单 -->
            <el-form :model="goodsModel" label-width="100px" >
                <el-form-item label="商品名" >
                    <el-input v-model="goodsModel.name" placeholder="请输入商品名"></el-input>
                </el-form-item>
                <el-form-item label="价格" > 
                    <el-input v-model="goodsModel.price" placeholder="请输入价格"></el-input>
                </el-form-item>
                <el-form-item label="成色" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="goodsModel.olds">
                    <el-option label="十成新" value="十成新"></el-option>
                    <el-option label="九成新" value="九成新"></el-option>
                    <el-option label="八成新" value="八成新"></el-option>
                    <el-option label="七成新" value="七成新"></el-option>
                    <el-option label="六成新" value="六成新"></el-option>
                    <el-option label="五成新" value="五成新"></el-option>
                </el-select>
                </el-form-item>        
          
                <el-form-item label="商品头像">

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
                        <img v-if="goodsModel.image" :src="goodsModel.image" class="image" />
                        <el-icon v-else class="image-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>   
                <el-form-item label="商品分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="goodsModel.categoryId">
                        <el-option v-for="c in category" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="商品详细描述">
                    <div class="editor">
                    <quill-editor
                        theme="snow"
                        v-model:content="goodsModel.description"
                        contentType="html"
                        >
                    </quill-editor>
                    </div>
                </el-form-item>

                <el-form-item>
                    <el-button @click="visibleDrawer = false">取消</el-button>
                    <el-button type="primary" @click="title == '新增商品' ?addGoods():updateGoods()">确认</el-button>

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
  width: 50px; /* 调整头像显示的大小 */
  height: 50px;
}
</style>