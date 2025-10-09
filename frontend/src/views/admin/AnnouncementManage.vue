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
import { announcementListService, announcementAddService, announcementUpdateService,announcementDeleteService,announcementStartStopService } from '@/api/announcement.js'
const announcementList = async () => {
    let result = await announcementListService();
    announcement.value = result.data;

}
announcementList();

//控制添加分类弹窗
const dialogVisible = ref(false)

//添加分类数据模型
const announcementModel = ref({
    name: '',
    content: '',
})

//添加分类表单校验
const rules = {
    name: [
        { required: true, message: '请输入公告标题', trigger: 'blur' },
    ]  ,  
    content: [
        { required: true, message: '请输入公告内容', trigger: 'blur' },
    ]
}

//调用接口,添加表单
import { ElMessage } from 'element-plus'
const addannouncement = async () => {
    //调用接口
    let result = await announcementAddService(announcementModel.value);
    ElMessage.success(result.msg ? result.msg : '添加成功')

    //调用获取所有公告分类的函数
    announcementList();
    dialogVisible.value = false;
}
 
//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    dialogVisible.value = true; title.value = '修改公告'
    //数据拷贝
    announcementModel.value.name = row.name;
    announcementModel.value.content = row.content;
    //扩展id属性,将来需要传递给后台,完成分类的修改
    announcementModel.value.id = row.id
}

//编辑公告
const updateannouncement = async () => {
    //调用接口
    let result = await announcementUpdateService(announcementModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')

    //调用获取所有分类的函数
announcementList();

    //隐藏弹窗
    dialogVisible.value = false;
}

//清空模型的数据
const clearData = () => {
    announcementModel.value.id = '';
    announcementModel.value.name = '';
    announcementModel.value.content = '';
}

//删除分类
import {ElMessageBox} from 'element-plus'
const deleteannouncement = (row) => {

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
            let result = await announcementDeleteService(row.id);
            ElMessage({
                type: 'success',
                message: '删除成功',
            })
            //刷新列表
            announcementList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '用户取消了删除',
            })
        })
}
//启用禁用
const lockingannouncement = (row,status) => {
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
            let result = await announcementStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '禁用成功',
            })
            //刷新列表
            announcementList();
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
            let result = await announcementStartStopService(row.id,status); 
            ElMessage({
                type: 'success',
                message: '启用成功',
            })
            //刷新列表
            announcementList();
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
                <span>公告管理</span>
                <div class="extra">
                    <el-button type="primary" @click="dialogVisible = true; title = '添加公告'; clearData()">添加公告</el-button>
                </div>
            </div>
        </template>
        <el-table :data="announcement" style="width: 100%">
            <el-table-column label="序号" width="100" type="index"> </el-table-column>
            <el-table-column label="公告标题" prop="name"></el-table-column>
            <el-table-column label="公告内容" prop="content"></el-table-column>
            <el-table-column label="状态" prop="status">
                <template #default="scope">
                    {{scope.row.status == 1 ? '启用':'禁用'}}
                </template>
            </el-table-column>
            <el-table-column label="更新时间" prop="updateTime"></el-table-column>
            <el-table-column label="操作" width="150px">
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteannouncement(row)"></el-button>
                    <el-button v-if="row.status == 1" :icon="Unlock" circle plain type="success" @click="lockingannouncement(row,0)"></el-button>
                    <el-button v-if="row.status == 0" :icon="Lock" circle plain type="info" @click="lockingannouncement(row,1)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table>

        <!-- 添加分类弹窗 --> 
<el-dialog v-model="dialogVisible" :title="title" width="30%">
    <el-form :model="announcementModel" :rules="rules" label-width="100px" style="padding-right: 30px">
        <el-form-item label="公告标题" prop="name">
            <el-input v-model="announcementModel.name" minlength="1" maxlength="10"></el-input>
        </el-form-item>        
        <el-form-item label="公告内容" prop="content">
             <el-input
      type="textarea"
      v-model="announcementModel.content"
      :rows="5"
      minlength="1"
      maxlength="255"
      placeholder="请输入公告内容"
    ></el-input>
        </el-form-item>
    </el-form>
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="title == '添加公告' ? addannouncement() : updateannouncement()"> 确认 </el-button>
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