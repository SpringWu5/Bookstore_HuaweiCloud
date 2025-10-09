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

const goodsCategory = ref([
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

//声明一个异步的函数
import { goodsCategoryListService, goodsCategoryAddService, goodsCategoryUpdateService,goodsCategoryDeleteService,goodsCategoryStartStopService } from '@/api/goodsCategory.js'
const goodsCategoryList = async () => {
    let result = await goodsCategoryListService();
    goodsCategory.value = result.data;

}
goodsCategoryList();

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const goodsCategoryModel = ref({
    name: ''
})

//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入商品分类名称', trigger: 'blur' },
    ]
}

//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addgoodsCategory = async () => {
    //调用接口
    let result = await goodsCategoryAddService(goodsCategoryModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功')

    //调用获取所有商品分类的函数
    goodsCategoryList();
    dialogVisible.value = false;
}
 
//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    dialogVisible.value = true; title.value = '修改商品分类'
    //数据拷贝
    goodsCategoryModel.value.name = row.name;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    goodsCategoryModel.value.id = row.id
}

//编辑商品分类
const updategoodsCategory = async () => {
    //调用接口
    let result = await goodsCategoryUpdateService(goodsCategoryModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
goodsCategoryList();

    //隐藏弹窗
    dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
    goodsCategoryModel.value.id = '';
    goodsCategoryModel.value.name = '';
}

//删除分类
import {ElMessageBox} from 'element-plus'
const deletegoodsCategory = (row) => {

    //提示用户  确认框
    ElMessageBox.confirm(
        '你确认要删除该分类信息吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    )
        .then(async () => {
            //调用接口
            let result = await goodsCategoryDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            goodsCategoryList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}
//启用禁用
const lockinggoodsCategory = (row,status) => {
    //提示用户  确认框
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
            let result = await goodsCategoryStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '禁用成功',
            })
            //刷新列表
            goodsCategoryList();
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
            let result = await goodsCategoryStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '启用成功',
            })
            //刷新列表
            goodsCategoryList();
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
                <span>商品分类</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加商品分类'; clearData()">添加商品分类</el-button>
                </div>
            </div>
        </template>
        <el-table :data="goodsCategory" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="商品分类名称" prop="name"></el-table-column>
            <el-table-column label="状态" prop="status">
                <template #default="scope">
                    {{scope.row.status == 1 ? '启用':'禁用'}}
                </template>
            </el-table-column>
            <el-table-column label="更新时间" prop="updateTime"></el-table-column>
            <el-table-column label="操作" width="150px">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deletegoodsCategory(row)"></el-button>
                    <el-button v-if="row.status == 1" :icon="Unlock" circle plain type="success" @click="lockinggoodsCategory(row,0)"></el-button>
                    <el-button v-if="row.status == 0" :icon="Lock" circle plain type="info" @click="lockinggoodsCategory(row,1)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 --> 
<el-dialog v-model="dialogVisible" :title="title" width="30%">
    <el-form :model="goodsCategoryModel" :rules="rules" label-width="120px" style="padding-right: 30px">
        <el-form-item label="商品分类名称" prop="name">
            <el-input v-model="goodsCategoryModel.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>


    </el-form>
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="title == '添加商品分类' ? addgoodsCategory() : updategoodsCategory()"> 确认 </el-button>
        </span>
    </template>
</el-dialog>
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
</style>