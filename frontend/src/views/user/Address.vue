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
 
//地址列表数据模型
const address = ref([
{
    "id": '',
    "userId": '',
    "consignee": "",
    "phone": "",
    "sex": "",
    "collegeId": '',
    "dormitoryId": '',
    "detail": "",
    "label": "",
    "isDefault": '',
    "togetherAddress": "", 
    "updateTime": ""
  }
])

//修改表单数据模型
const addressModel = ref({ 
    consignee: '',
    phone:'',
    sex:'',
    collegeId:'',
    dormitoryId:'',
    detail:'',
    label:'',
    updateTime:'',
})

//清空模型的数据
const clearData = () => {
    addressModel.value.id= '';
    addressModel.value.consignee= '';
    addressModel.value.phone= '';
    addressModel.value.sex= '';
    addressModel.value.collegeId= '';
    addressModel.value.dormitoryId= '';
    addressModel.value.detail= '';
    addressModel.value.label= '';
    addressModel.value.updateTime= '';
}
    //回显学院分类
    import { collegeListService} from '@/api/college.js'
    import { dormitoryListService} from '@/api/dormitory.js'
    import { addressAddService,userAddressListService,addressUpdateService,addressDeleteService,userStartStopService} from '@/api/address.js'
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
    addressModel.value.avatar = result.data;
}
    
    //获取地址列表数据
    const addressList = async () => {
        let result = await userAddressListService();
        //渲染视图
        address.value = result.data;
    
        //处理数据,给数据模型扩展一个属性collegeName,分类名称
        for (let i = 0; i < address.value.length; i++) {
            let address = address.value[i];
            for (let j = 0; j < college.value.length; j++) {
                if (address.collegeId == college.value[j].id) {
                    address.collegeName = college.value[j].name;
                }
            }
        }
        //处理数据,给数据模型扩展一个属性dormitoryName,分类名称
        for (let i = 0; i < address.value.length; i++) {
            let address = address.value[i];
            for (let j = 0; j < dormitory.value.length; j++) {
                if (address.dormitoryId == dormitory.value[j].id) {
                    address.dormitoryName = dormitory.value[j].name;
                }
            }
        }
    }
    
    collegeCategoryList();
    dormitoryCategoryList();
    addressList(); 

import {Plus} from '@element-plus/icons-vue'
//控制抽屉是否显示
const visibleDrawer = ref(false)

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')

//展示编辑弹窗
const showDialog = (row) => {
    visibleDrawer.value = true; title.value = '修改地址'
    //数据拷贝
    addressModel.value.consignee= row.consignee;
    addressModel.value.phone=row.phone;
    addressModel.value.detail=row.detail;
    addressModel.value.sex=row.sex;
    addressModel.value.label=row.label;
    addressModel.value.updateTime=row.updateTime;
    addressModel.value.collegeId=row.collegeId;
    addressModel.value.dormitoryId=row.dormitoryId; 
    
    //扩展id属性,将来需要传递给后台,完成分类的修改
    addressModel.value.id = row.id
} 


//新增地址
const addAddress = async () => {
    //调用接口
    let result = await addressAddService(addressModel.value);

    ElMessage.success(result.msg ? result.msg : '新增成功')
    //调用获取所有分类的函数
    addressList();

    //隐藏弹窗
    visibleDrawer.value = false;
}

//修改地址
const updateAddress = async () => {
    //调用接口
    let result = await addressUpdateService(addressModel.value);

    ElMessage.success(result.msg ? result.msg : '修改成功')
    //调用获取所有分类的函数
    addressList();

    //隐藏弹窗
    visibleDrawer.value = false;
}


//删除地址
import {ElMessageBox} from 'element-plus'
const deleteAddress = (row) => { 

    //提示地址  确认框
  ElMessageBox.confirm(
    '你确认要删除该地址信息吗?',
    '温馨提示',
    {
        confirmButtonText: '确认', 
        cancelButtonText: '取消',
      type: 'warning',
    }
  )
    .then(async () => { 
         //调用接口
        let result = await addressDeleteService(row.id);
      ElMessage({
        type: 'success',
        message: '删除成功',
      })
        //刷新列表
        addressList();
    })
    .catch(() => {
      ElMessage({
        type: 'info',
        message: '地址取消了删除',
      })
    })
}
//启用禁用
const lockinguser = (row,locking) => {
    //提示地址  确认框
    if(locking == 0){
    ElMessageBox.confirm(
        '你确认要设置为普通地址吗?',
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
                message: '设置成功',
            })
            //刷新列表
            addressList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '您取消了设置',
            })
        })
} else{
    ElMessageBox.confirm(
        '你确认要设置为默认地址吗？',
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
                message: '设置成功',
            })
            //刷新列表
            addressList();
        })
        .catch(() => {
            ElMessage({
                type: 'info',
                message: '您取消了设置',
            })
        })
}
}
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>地址管理</span>
                                <div class="extra">
                <el-button type="primary" @click="visibleDrawer = true; title = '添加地址';clearData()">添加地址</el-button>
                </div>
            </div>
        </template>
        
        <!-- 地址列表 -->
        <el-table :data="address" style="width: 100%">
            <el-table-column label="收货人" width="80px" prop="consignee"></el-table-column>
            <el-table-column label="性别" width="60px" prop="sex">
                <template #default="scope">
                    {{scope.row.sex == 1 ? '男':'女'}}
                </template>
            </el-table-column>
            <el-table-column label="手机号" width="130px" prop="phone"></el-table-column>
            <el-table-column label="拼接地址" width="450px" prop="togetherAddress"></el-table-column>
            <el-table-column label="标签"  width="90px" prop="label"></el-table-column>
            <el-table-column label="默认状态"  width="90px"  prop="isDefault">
                <template #default="scope">
                    {{scope.row.isDefault == 1 ? '默认':'普通'}}
                </template>
            </el-table-column>
            <el-table-column label="更新时间" prop="updateTime" width="160"></el-table-column>
            <el-table-column label="操作" width="150"> 
                <template #default="{ row }">
                    <el-button :icon="Edit" circle plain type="primary"  @click="showDialog(row)"></el-button>
                    <el-button :icon="Delete" circle plain type="danger" @click="deleteAddress(row)"></el-button>
                    <el-button v-if="row.isDefault == 1" :icon="Unlock" circle plain type="success" @click="lockinguser(row,0)"></el-button>
                    <el-button v-if="row.isDefault == 0" :icon="Lock" circle plain type="info" @click="lockinguser(row,1)"></el-button>
                </template>
            </el-table-column>
            <template #empty>
                <el-empty description="没有数据" />
            </template>
        </el-table> 
            <!-- 抽屉 -->
        <el-drawer v-model="visibleDrawer"  :title="title" direction="rtl" size="50%">
            <!-- 修改地址表单 -->
            <el-form :model="addressModel" label-width="100px" >
                <el-form-item label="收货人" >
                    <el-input v-model="addressModel.consignee" placeholder="请输入收货人"></el-input>
                </el-form-item>
                <el-form-item label="性别" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="addressModel.sex">
                    <el-option label="先生" value="1"></el-option>
                    <el-option label="女士" value="0"></el-option>
                </el-select>
                </el-form-item>  
                <el-form-item label="手机号" >
                    <el-input v-model="addressModel.phone" placeholder="请输入手机号"></el-input>
                </el-form-item>    
                <el-form-item label="学院分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="addressModel.collegeId">
                        <el-option v-for="c in college" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>    
                <el-form-item label="宿舍分类" >
                    <el-select style="width: 200px"  placeholder="请选择" v-model="addressModel.dormitoryId">
                        <el-option v-for="c in dormitory" :key="c.id" :label="c.name" :value="c.id">
                        </el-option>
                    </el-select>
                </el-form-item>   
                <el-form-item label="详细地址" >
                    <el-input v-model="addressModel.detail" placeholder="请输入详细地址"></el-input>
                </el-form-item>   
                <el-form-item label="标签" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="addressModel.label">
                    <el-option label="宿舍" value="宿舍"></el-option>
                    <el-option label="教室" value="教室"></el-option>
                    <el-option label="校园" value="校园"></el-option>
                </el-select>
                </el-form-item> 
                <el-form-item>
                    <el-button @click="visibleDrawer = false">取消</el-button>
                    <el-button type="primary" @click=" title =='添加地址'?addAddress(): updateAddress()">确认</el-button>

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