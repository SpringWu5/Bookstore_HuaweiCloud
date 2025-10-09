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

//学院分类数据模型
const college = ref([
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
//宿舍分类数据模型
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
 
//用户列表数据模型
const members = ref([
{
    "id": '',
    "name": '',
    "username": '',
    "phone": '',
    "sex": '',
    "email": '',
    "avatar": '',
    "collegeId": '',
    "dormitoryId": '',
    "status": '',
    "locking": '',
    "createTime": '',
    "updateTime": '',
    "updateUser": ''
  }
])

//修改表单数据模型
const memberModel = ref({
    username: '',
    name:'',
    phone:'',
    sex:'',
    email:'',
    avatar:'',
    collegeId:'',
    dormitoryId:'',
    status:'',
    locking:'',
})


//用户搜索时选中的学院id
const collegeId=ref('')

//用户搜索时选中的宿舍id
const dormitoryId=ref('')

//用户搜索时选中的状态
const locking=ref('')

//用户搜索时输入的姓名
const name=ref('')

//用户搜索时输入的排序方式
const sortBy=ref('')

//用户搜索时输入的排序顺序
const isAsc=ref('')

//分页条数据模型
const page = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
        memberList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    page.value = num
        memberList();
}

    //回显学院分类
    import { collegeListService} from '@/api/college.js'
    import { dormitoryListService} from '@/api/dormitory.js'
    import { memberListService,memberUpdateService,memberDeleteService,userStartStopService} from '@/api/member.js'
    const collegeCategoryList = async () => {
        let result = await collegeListService();
        college.value = result.data;
    }    

    //回显宿舍分类
    const dormitoryCategoryList = async () => {
        let result = await dormitoryListService();
        dormitory.value = result.data;
    }

    //图片上传成功的回调函数
    const uploadSuccess = (result)=>{
    memberModel.value.avatar = result.data;
}
    
    //获取用户列表数据
    const memberList = async () => {
        let params = {
            page: page.value,
            pageSize: pageSize.value,
            name: name.value ? name.value : null,
            collegeId: collegeId.value ? collegeId.value : null,
            dormitoryId: dormitoryId.value ? dormitoryId.value : null,
            locking: locking.value ? locking.value : null,
            sortBy: sortBy.value ? sortBy.value : null,
            isAsc: isAsc.value ? isAsc.value : null
        }
        let result = await memberListService(params);
        //渲染视图
        total.value = result.data.total;
        members.value = result.data.records;
    
        //处理数据,给数据模型扩展一个属性collegeName,分类名称
        for (let i = 0; i < members.value.length; i++) {
            let member = members.value[i];
            for (let j = 0; j < college.value.length; j++) {
                if (member.collegeId == college.value[j].id) {
                    member.collegeName = college.value[j].name;
                }
            }
        }
        //处理数据,给数据模型扩展一个属性dormitoryName,分类名称
        for (let i = 0; i < members.value.length; i++) {
            let member = members.value[i];
            for (let j = 0; j < dormitory.value.length; j++) {
                if (member.dormitoryId == dormitory.value[j].id) {
                    member.dormitoryName = dormitory.value[j].name;
                }
            }
        }
    }
    
    collegeCategoryList();
    dormitoryCategoryList();
    memberList();

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    visibleDrawer.value = true; title.value = '修改用户'
    //数据拷贝
    memberModel.value.username= row.username;
    memberModel.value.name=row.name;
    memberModel.value.phone=row.phone;
    memberModel.value.sex=row.sex;
    memberModel.value.email=row.email;
    memberModel.value.avatar=row.avatar;
    memberModel.value.collegeId=row.collegeId;
    memberModel.value.dormitoryId=row.dormitoryId;   
    //扩展id属性,将来需要传递给后台,完成分类的修改
    memberModel.value.id = row.id
} 

//修改用户
const updateUser = async () => {
    //调用接口
    let result = await memberUpdateService(memberModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')
    //调用获取所有分类的函数
    memberList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//清空模型的数据
const clearData = () => {
    memberModel.value.id= '';
    memberModel.value.username= '';
    memberModel.value.name='';
    memberModel.value.phone='';
    memberModel.value.sex='';
    memberModel.value.email='';
    memberModel.value.avatar='';
    memberModel.value.collegeId='';
    memberModel.value.dormitoryId='';   
}
//删除用户
import {ElMessageBox} from 'element-plus'
const deleteUser = (row) => { 

    //提示用户  确认框
  ElMessageBox.confirm(
    '你确认要删除该用户信息吗?',
    '温馨提示',
    {
        confirmButtonText: '确认', 
        cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { 
         //调用接口
        let result = await memberDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
        //刷新列表
        memberList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '用户取消了删除',
      })
    })
}
//启用禁用
const lockinguser = (row,locking) => {
    //提示用户  确认框
    if(locking == 0){
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
            let result = await userStartStopService(row.id,locking); 
            ElMessage({
                type: 'success',
                message: '禁用成功',
            })
            //刷新列表
            memberList();
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
            let result = await userStartStopService(row.id,locking); 
            ElMessage({
                type: 'success',
                message: '启用成功',
            })
            //刷新列表
            memberList();
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
                <span>用户管理</span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline>
            <el-form-item label="学院分类：">
                <el-select style="width: 160px" placeholder="请选择" v-model="collegeId">
                    <el-option 
                        v-for="c in college" 
                        :key="c.id" 
                        :label="c.name"
                        :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item width="80px" label="宿舍分类：">
                <el-select style="width: 160px" placeholder="请选择" v-model="dormitoryId">
                    <el-option 
                        v-for="c in dormitory" 
                        :key="c.id" 
                        :label="c.name"
                        :value="c.id">
                    </el-option>
                </el-select>
            </el-form-item>  

            <el-form-item label="权限状态：">
                <el-select style="width: 160px" placeholder="请选择" v-model="locking">
                    <el-option label="启用" value="1"></el-option>
                    <el-option label="禁用" value="0"></el-option>
                </el-select>
            </el-form-item>
 
            <el-form-item  style="width: 200px" label="姓名">
                     <el-input v-model="name" placeholder="请输入姓名" clearable />
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="memberList">搜索</el-button>
                <el-button @click="collegeId = '';dormitoryId = ''; locking = '';name='';    memberList();">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 用户列表 -->
        <el-table :data="members" style="width: 100%">
            <el-table-column label="姓名" width="80px" prop="name"></el-table-column>
            <el-table-column label="头像" prop="avatar"> 
                <template #default="scope">
                    <img v-if="scope.row.avatar" :src="scope.row.avatar" class="avatar" />
                </template>
            </el-table-column>
            <el-table-column label="性别" width="80px" prop="sex">
                <template #default="scope">
                    {{scope.row.sex == 1 ? '男':'女'}}
                </template>
            </el-table-column>
            <el-table-column label="手机号" width="150px" prop="phone"></el-table-column>
            <el-table-column label="邮箱" width="200px" prop="email"></el-table-column>
            <el-table-column label="状态" prop="locking">
                <template #default="scope">
                    {{scope.row.locking == 1 ? '启用':'禁用'}}
                </template>
            </el-table-column>
            <el-table-column label="学院名称" prop="collegeName"></el-table-column>
            <el-table-column label="宿舍名称" prop="dormitoryName"></el-table-column>
            <el-table-column label="更新时间" prop="updateTime" width="200"></el-table-column>
            <el-table-column label="操作" width="150"> 
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteUser(row)"></el-button>
                    <el-button v-if="row.locking == 1" :icon="Unlock" circle plain type="success" @click="lockinguser(row,0)"></el-button>
                    <el-button v-if="row.locking == 0" :icon="Lock" circle plain type="info" @click="lockinguser(row,1)"></el-button>
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
            <!-- 修改用户表单 -->
            <el-form :model="memberModel" label-width="100px" >
                <el-form-item label="用户名" >
                    <el-input v-model="memberModel.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item label="姓名" > 
                    <el-input v-model="memberModel.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="性别" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="memberModel.sex">
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="0"></el-option>
                </el-select>
                </el-form-item>       
                <el-form-item label="手机号" >
                    <el-input v-model="memberModel.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>    
                <el-form-item label="邮箱" >
                    <el-input v-model="memberModel.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>  
                <el-form-item label="用户头像">

                    <!-- 
                        auto-upload:设置是否自动上传
                        action:设置服务器接口路径
                        name:设置上传的文件字段名
                        headers:设置上传的请求头
                        on-success:设置上传成功的回调函数
                     -->
                   
                    <el-upload class="avatar-uploader" :auto-upload="true" :show-file-list="false"
                    action="/api/admin/common/upload"
                    name="file"
                    :headers="{'token':tokenStore.token}"
                    :on-success="uploadSuccess"
                    >
                        <img v-if="memberModel.avatar" :src="memberModel.avatar" class="avatar" />
                        <el-icon v-else class="avatar-uploader-icon">
                            <Plus />
                        </el-icon>
                    </el-upload>
                </el-form-item>   
                <el-form-item label="学院分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="memberModel.collegeId">
                        <el-option v-for="c in college" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="宿舍分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="memberModel.dormitoryId">
                        <el-option v-for="c in dormitory" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item>
                    <el-button @click="visibleDrawer = false">取消</el-button>
                    <el-button type="primary" @click="updateUser()">确认</el-button>

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
.avatar-uploader {
    :deep() {
        .avatar {
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

        .el-icon.avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 278px;
            height: 278px;
            text-align: center;
        }
    }
}
.avatar {
  width: 50px; /* 调整头像显示的大小 */
  height: 50px;
}
</style>