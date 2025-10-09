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

//商品列表数据模型
const orders = ref([
      {
        "id": 0,
        "number": "",
        "status": 0,
        "buyId": 0,
        "sellId": 0,
        "addressBookId": 0,
        "orderTime": "",
        "amount": 0,
        "remark": "",
        "phone": "",
        "address": "",
        "consignee": "",
        "buyName": "",
        "sellName": ""
      }
])


//拒单、退单数据模型
const reason=ref('')
const ids=ref('')

//订单号
const number=ref('')

//手机号
const phone=ref('')

//订单状态
const status=ref('')

//开始时间
const beginTime = ref('')
const defaultTime = new Date(2000, 1, 1, 12, 0, 0)
//结束时间
const endTime=ref('')


//分页条数据模型
const page = ref(1)//当前页
const total = ref(20)//总条数
const pageSize = ref(5)//每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
    pageSize.value = size
        ordersList();
}
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
    page.value = num
        ordersList();
}
    
    //获取商品列表数据
    import { ordersListService,ordersConfirmService,ordersRejectionService,ordersCancelService } from "@/api/orders.js";
    const ordersList = async () => {
        let params = {
            page: page.value,
            pageSize: pageSize.value,
            number: number.value ? number.value : null,
            phone: phone.value ? phone.value : null,
            status: status.value ? status.value : null,
            beginTime: beginTime.value ? beginTime.value: null,
            endTime: endTime.value ? endTime.value : null
        }
        let result = await ordersListService(params);
        //渲染视图
        total.value = result.data.total;
        orders.value = result.data.records;
    
    }
   //查询全部商品
    ordersList();

import {Plus} from '@element-plus/icons-vue'

import {ElMessage} from 'element-plus'
//定义变量,控制标题的展示
const title = ref('')

//接单、派送、完成
const ordersConfirm = async (row,status) => {
    let result = await ordersConfirmService(row.id,status); 
       ordersList();
}

//控制添加分类弹窗
const dialogVisible = ref(false)
const dialogVisible2 = ref(false)

const ordersRejection = async (id,reason) => {
    let result = await ordersRejectionService(id,reason); 
    dialogVisible.value = false;
       ordersList();
}

const ordersCancel = async (id,reason) => {
    let result = await ordersCancelService(id,reason); 
    dialogVisible2.value = false;
       ordersList();
}

</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>订单管理</span>
            </div>
        </template>
        <!-- 搜索表单 -->
        <el-form inline class="custom-inline-form">
            <el-form-item  style="width: 200px" label="订单号">
                     <el-input v-model="number" placeholder="请输入订单号" clearable />
            </el-form-item>
            <el-form-item  style="width: 190px" label="手机号">
                     <el-input v-model="phone" placeholder="请输入手机号" clearable />
            </el-form-item>            
           <el-form-item label="状态" >
                    <el-select style="width: 140px" placeholder="请选择" v-model="status">
                    <el-option label="待接单" value="2"></el-option>
                    <el-option label="待配送" value="3"></el-option>
                    <el-option label="运送中" value="4"></el-option>
                    <el-option label="已完成" value="5"></el-option>
                    <el-option label="已退款" value="7"></el-option>
                </el-select>
            </el-form-item>  
            <el-form-item  style="width: 265px" label="下单时间" >         
             <el-date-picker
                v-model="beginTime"
                type="datetime"
                 placeholder="初始时间"
                 :default-time="defaultTime"
                 format="YYYY/MM/DD HH:mm:ss"
                 value-format="YYYY-MM-DD HH:mm:ss"
                />
                </el-form-item>
                 <el-form-item  style="width: 195px">     
                <el-date-picker
                v-model="endTime"
                type="datetime"
                 placeholder="结束时间"
                 :default-time="defaultTime"
                 format="YYYY/MM/DD HH:mm:ss"
                 value-format="YYYY-MM-DD HH:mm:ss"
                />
                 </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="ordersList">搜索</el-button>
                <el-button @click="number = '';phone= ''; status = '';beginTime='';endTime='';ordersList()">重置</el-button>
            </el-form-item>
        </el-form>
        <!-- 商品列表 --> 
        <el-table :data="orders" style="width: 100%">
            <el-table-column label="订单号" width="135px" prop="number"></el-table-column>
            <el-table-column label="状态"  width="70px" prop="status">
                <template #default="scope">
                    {{scope.row.status != 1 ? 
                    (scope.row.status !=2 ?
                    (scope.row.status !=3 ?
                    (scope.row.status !=4 ? 
                    (scope.row.status !=5 ?
                    (scope.row.status !=6 ? '退款' : '已取消')  : '已完成'): '运送中') : '已接单') : '待接单') 
                    : '待付款'}}
                </template>
            </el-table-column>
            <el-table-column label="买家名" width="90px" prop="buyName"></el-table-column>
            <el-table-column label="卖家名" width="90px" prop="sellName"></el-table-column>
            <el-table-column label="手机号" width="110px" prop="phone"></el-table-column>
            <el-table-column label="地址" width="350px" prop="address"></el-table-column>
            <el-table-column label="下单时间" width="144px" prop="orderTime"></el-table-column>
            <el-table-column label="备注" width="100px" prop="remark"></el-table-column>
            <el-table-column label="金额" width="60px" prop="amount"></el-table-column>

            <el-table-column label="操作" width="105"> 
                <template #default="{ row }">
                    <el-button v-if="row.status == 2" type="text"   @click="ordersConfirm(row,3)">接单</el-button>
                    <el-button v-if="row.status == 3" type="text"  @click="ordersConfirm(row,4)">派送</el-button>
                    <el-button v-if="row.status == 4" type="text"  @click="ordersConfirm(row,5)">完成</el-button>
                    <el-button v-if="row.status == 5" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                    <el-button v-if="row.status == 6" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                    <el-button v-if="row.status == 7" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                    <el-button v-if="row.status == 2" type="text" @click="dialogVisible = true; ids =row.id ;reason=''">拒单</el-button>
                    <el-button v-if="row.status == 3" type="text" @click="dialogVisible = true; ids =row.id ;reason=''">退单</el-button>
                    <el-button v-if="row.status == 4" type="text"  @click="dialogVisible2 = true; ids =row.id ;reason=''">退单</el-button>
                    <el-button v-if="row.status == 5" type="text"  @click="dialogVisible2 = true; ids =row.id ;reason=''">退单</el-button>
                    <el-button v-if="row.status == 6" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
                    <el-button v-if="row.status == 7" type="text">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</el-button>
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

<!-- 拒绝单子弹窗 --> 
<el-dialog v-model="dialogVisible" title="拒单" width="30%">
                  <el-form-item label="拒单原因" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="reason">
                    <el-option label="订单数量较多，暂时无法接单" value="订单数量较多，暂时无法接单"></el-option>
                    <el-option label="最近比较忙，没有时间时间接单" value="最近比较忙，没有时间时间接单"></el-option>
                    <el-option label="我不在学校，交易不了" value="我不在学校，交易不了"></el-option>
                    <el-option label="买家联系取消" value="买家联系取消"></el-option>
                </el-select>
                </el-form-item>  
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary"  @click="ordersRejection(ids,reason)"> 确认 </el-button>
        </span>
    </template>
</el-dialog>
<!-- 退货单子弹窗 --> 
<el-dialog v-model="dialogVisible2" title="退单" width="30%">
                  <el-form-item label="退单原因" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="reason">
                    <el-option label="最近比较忙，没有时间时间交易" value="最近比较忙，没有时间时间交易"></el-option>
                    <el-option label="不想卖了，交易不了" value="不想卖了，交易不了"></el-option>
                    <el-option label="买家联系退单" value="买家联系取消"></el-option>
                </el-select>
                </el-form-item>  
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary"  @click="ordersCancel(ids,reason)"> 确认 </el-button>
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

.custom-inline-form .el-form-item {
  margin-right: 8px; /* 调整为你想要的间隙 */
}
</style>