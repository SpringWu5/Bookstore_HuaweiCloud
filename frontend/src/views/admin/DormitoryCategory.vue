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

const dormitory = ref([
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
import { dormitoryListService, dormitoryAddService, dormitoryUpdateService,dormitoryDeleteService,dormitoryStartStopService } from '@/api/dormitory.js'
const dormitoryList = async () => {
    let result = await dormitoryListService();
    dormitory.value = result.data;

}
dormitoryList();

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const dormitoryModel = ref({
    name: ''
})

//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入宿舍名称', trigger: 'blur' },
    ]
}

//调用接口,添加表单
import { ElMessage } from 'element-plus'
const adddormitory = async () => {
    //调用接口
    let result = await dormitoryAddService(dormitoryModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功')

    //调用获取所有文章分类的函数
    dormitoryList();
    dialogVisible.value = false;
}
 
//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    dialogVisible.value = true; title.value = '修改宿舍'
    //数据拷贝
    dormitoryModel.value.name = row.name;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    dormitoryModel.value.id = row.id
}

//编辑宿舍
const updatedormitory = async () => {
    //调用接口
    let result = await dormitoryUpdateService(dormitoryModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
dormitoryList();

    //隐藏弹窗
    dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
    dormitoryModel.value.id = '';
    dormitoryModel.value.name = '';
}

//删除分类
import {ElMessageBox} from 'element-plus'
const deletedormitory = (row) => {

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
            let result = await dormitoryDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            dormitoryList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}
const lockingdormitory = (row,status) => {
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
            let result = await dormitoryStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '禁用成功',
            })
            //刷新列表
            dormitoryList();
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
            let result = await dormitoryStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '启用成功',
            })
            //刷新列表
            dormitoryList();
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
                <span>宿舍分类</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加宿舍'; clearData()">添加宿舍</el-button>
                </div>
            </div>
        </template>
        <el-table :data="dormitory" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="宿舍名称" prop="name"></el-table-column>
            <el-table-column label="状态" prop="status">
                <template #default="scope">
                    {{scope.row.status == 1 ? '启用':'禁用'}}
                </template>
            </el-table-column>
            <el-table-column label="更新时间" prop="updateTime"></el-table-column>
            <el-table-column label="操作" width="150px">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deletedormitory(row)"></el-button>
                    <el-button v-if="row.status == 1" :icon="Unlock" circle plain type="success" @click="lockingdormitory(row,0)"></el-button>
                    <el-button v-if="row.status == 0" :icon="Lock" circle plain type="info" @click="lockingdormitory(row,1)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 --> 
<el-dialog v-model="dialogVisible" :title="title" width="30%">
    <el-form :model="dormitoryModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="宿舍名称" prop="name">
            <el-input v-model="dormitoryModel.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>


    </el-form>
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="title == '添加宿舍' ? adddormitory() : updatedormitory()"> 确认 </el-button>
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