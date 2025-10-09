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

//订单数据模型
const order = ref(
 {
    "id": 0,
    "orderNumber": "",
    "orderAmount": 0,
    "orderTime": "",    
    "payMethod":0,
  },
);

//支付数据模型
const payMent = ref(
 {
    "orderNumber": "",
    "payMethod": 0,
  },
);

//拒单退单数据模型
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
//订单失效
const remainTime = ref("");

//支付图片
const imageTemp = ref("");
 
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
    import { buyOrdersListService,ordersCancelService,getByOrdersIDService } from "@/api/orders.js";
    import { payMentService } from "@/api/orders.js";
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
        let result = await buyOrdersListService(params);
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

//查询订单

//弹窗
const dialogVisible3 = ref(false);
const dialogVisible2 = ref(false);

const ordersConfirm = async (row) => {
    let result = await getByOrdersIDService(row.id); 
    order.value =result.data;
    if(order.value.payMethod ==1){
        imageTemp.value = '/src/assets/wx.png';
    }
        if(order.value.payMethod ==2){
        imageTemp.value = '/src/assets/zfb.jpg';
    }
    //定义 orderTime 和当前时间的字符串
let orderTime = order.value.orderTime;
// const isoOrderTime = orderTime.replace(" ", "T");
let now = new Date().toISOString();

// 解析 orderTime 和当前时间的字符串为 Date 对象
let orderDate = new Date(orderTime);
let currentDate = new Date(now);

// 将订单时间加上 15 分钟
orderDate.setMinutes(orderDate.getMinutes() + 15);

// 计算时间差（单位：毫秒）
let timeDiff = orderDate - currentDate;

// 将时间差转换为分钟和秒数
let minutes2 = Math.floor(timeDiff / (1000 * 60));
let seconds2 = Math.floor((timeDiff % (1000 * 60)) / 1000);

// 格式化为指定格式的字符串
  remainTime.value = `${minutes2}:${seconds2}`;
dialogVisible3.value =true;
}

//支付订单
const paySure = async () => {
    payMent.value.orderNumber = order.value.orderNumber;
    payMent.value.payMethod = order.value.payMethod;
    let result = await payMentService(payMent.value);
    ElMessage.success(result.msg ? result.msg : "支付成功");
      //隐藏弹窗
  dialogVisible3.value = false;
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
                    <el-option label="待支付" value="1"></el-option>
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
            <el-table-column label="地址" width="340px" prop="address"></el-table-column>
            <el-table-column label="下单时间" width="144px" prop="orderTime"></el-table-column>
            <el-table-column label="备注" width="90px" prop="remark"></el-table-column>
            <el-table-column label="金额" width="60px" prop="amount"></el-table-column>

            <el-table-column label="操作" width="120"> 
                <template #default="{ row }">
                    <el-button v-if="row.status == 1" type="text" @click="ordersConfirm(row)">去支付</el-button>
                    <el-button v-if="row.status == 2" type="text" >待接单</el-button>
                    <el-button v-if="row.status == 3" type="text" >已接单</el-button>
                    <el-button v-if="row.status == 4" type="text" >运送中</el-button>
                    <el-button v-if="row.status == 5" type="text" >已完成</el-button>
                    <el-button v-if="row.status == 6" type="text" >已取消</el-button>
                    <el-button v-if="row.status == 7" type="text" >已退款</el-button>
                    <el-button v-if="row.status == 1" type="text" @click="dialogVisible2 = true; ids =row.id ;reason=''">取消</el-button>
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
<!-- 退货单子弹窗 --> 
<el-dialog v-model="dialogVisible2" title="取消订单" width="30%">
                  <el-form-item label="取消原因" >
                    <el-select style="width: 200px" placeholder="请选择" v-model="reason">
                    <el-option label="不想买了" value="不想买了"></el-option>
                    <el-option label="无理由" value="无理由"></el-option>
                    <el-option label="嫌贵" value="嫌贵"></el-option>
                </el-select>
                </el-form-item>  
    <template #footer>
        <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary"  @click="ordersCancel(ids,reason)"> 确认 </el-button>
        </span>
    </template>
</el-dialog>
              <!-- 订单详情弹窗 -->
    <el-dialog v-model="dialogVisible3" title="订单详情" width="50%">
<div class="order-container">
  <div class="order-time">
    支付剩余时间：{{remainTime}}
  </div>
  <div class="order-price">
    ¥ {{order.orderAmount}}
  </div>
  <div class="order-number">
    订单号：{{ order.orderNumber }}
  </div>
  <br><br>
  <div>
        <img :src="imageTemp"  class="image-wx" alt="Description of Image">
    </div><br><br>
    <div class="order-sure">
     <el-button type="warning" @click="paySure()">确认支付</el-button>
  </div>
</div>
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

    .order-container {
      text-align: center;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .order-time,
    .order-price,
    .order-number {
      margin: 8px 0; /* 设置各元素之间的间距为15px */
    }
.order-time {
  font-size: 17px;
    }
    .order-number {
  font-size: 17px;
    }
    .order-price {
  font-weight: bold;
  font-size: 28px;
  color: black;
    }
.image-wx {
    width: 250px;
    height: 300px;
}
</style>