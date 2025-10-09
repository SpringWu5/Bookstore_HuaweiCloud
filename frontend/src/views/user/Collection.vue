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
const goods = ref([
{
    "id": '',
    "name": '',
    "categoryId": '',
    "price": '',
    "image": '',
    "olds": '', 
    "goodsId": " ",
    "categoryName": '',
    "collection": '',
    "popularity": '',
  }
])

//用户查询商品详情数据模型
const userGoods = ref([
  {
    id: "",
    name: "",
    categoryId: "",
    price: "",
    image: "",
    olds: "",
    description: " ",
    status: "",
    userId: "",
    collection: "",
    praise: "",
    comment: "",
    look: "",
    collectionTotal: "",
    praiseTotal: "",
    commentTotal: "",
    lookTotal: "",
    popularity: "",
    collegeName: "",
    dormitoryName: "",
    userName: "",
    phone: "",
    updateTime: "",
  },
]);

//地址列表数据模型
const addressList = ref([
  {
    id: "",
    userId: "",
    consignee: "",
    phone: "",
    sex: "",
    collegeId: "",
    dormitoryId: "",
    detail: "",
    label: "",
    isDefault: "",
    togetherAddress: "",
    updateTime: "",
  },
]);

//默认地址列表数据模型
const address = ref([
  {
    id: "",
    userId: "",
    consignee: "",
    phone: "",
    sex: "",
    collegeId: "",
    dormitoryId: "",
    detail: "",
    label: "",
    isDefault: "",
    togetherAddress: "",
    updateTime: "",
  },
]);

//下单数据模型
const orderSubmit = ref(
{
  "addressBookId": '',
  "amount": '',
  "beginDeliveryTime": "",
  "goodsId": '',
  "payMethod": '',
  "remark": "",
  "sellId": ''
},
);

//订单数据模型
const order = ref(
 {
    "id": 0,
    "orderNumber": "",
    "orderAmount": 0,
    "orderTime": ""
  },
);

//支付数据模型
const payMent = ref(
 {
    "orderNumber": "",
    "payMethod": 0,
  },
);

//时间选择器
const startTime = ref('')
const defaultTime = new Date(2000, 1, 1, 12, 0, 0)

//订单失效
const remainTime = ref("");

//支付图片
const imageTemp = ref("");

//备注信息
const remark = ref("");


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
    import { orderSubmitService,payMentService } from "@/api/orders.js";
import { getAddressService } from "@/api/address.js";
import {
    goodsCollectionListService,
  userGoodsListService,
  userUpdatePraiseTotal,
  userUpdateCollectionTotal,
  goodsDescriptionService,
  goodsAddService,
  goodsUpdateService,
  goodsDeleteService,
  userStartStopService,
} from "@/api/goods.js";
    
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
        let result = await goodsCollectionListService(params);
        //渲染视图
        total.value = result.data.total;
        goods.value = result.data.records;
    }
   //查询全部商品
    goodsList();

import {Plus} from '@element-plus/icons-vue'
import {ElMessage} from 'element-plus'
import {ElMessageBox} from 'element-plus'

//查询是否存在默认地址
const getAddress = async () => {
  let result = await getAddressService();
  addressList.value = result.data;
  for (let i = 0; i < addressList.value.length; i++) {
    let temp = addressList.value[i];
    if (temp.isDefault == 1) {
      address.value = temp;
      break; // 找到默认地址后立即结束循环
    }
  }
  showVisible2();
};

const showVisible2 = () => {
  dialogVisible.value = false;
  dialogVisible2.value = true;
};

//修改地址
const updateAddress = () => {
  dialogVisible2.value = false;
  router.push('/user/person/address')
};

//控制商品详情页面是否显示
const dialogVisible = ref(false);
const dialogVisible2 = ref(false);
const dialogVisible3 = ref(false);

//用户点赞
const updatePraiseTotal = async (userGoods, praiseTemp) => {
  let id = userGoods.id;
  let result = await userUpdatePraiseTotal(id, praiseTemp);

  showVisibleTemp(id);
  //查询全部商品
  goodsList();
};

//用户收藏
const updateCollectionTotal = async (userGoods, collectionTemp) => {
  let id = userGoods.id;
  let result = await userUpdateCollectionTotal(id, collectionTemp);

  showVisibleTemp(id);
  //查询全部商品
  goodsList();
};

//展示商品详情弹窗
const showVisibleTemp = async (id) => {
  let result = await goodsDescriptionService(id);
  userGoods.value = result.data;
  userGoods.value.praise = String(userGoods.value.praise);
  userGoods.value.collection = String(userGoods.value.collection);
  removePTag();
  showVisible();
};

//其他处理
const removePTag = () => {
  userGoods.value.description = userGoods.value.description
    .replace(/<p>/g, "")
    .replace(/<\/p>/g, "");
};

//展示商品详情弹窗
const showVisible = () => {
  dialogVisible.value = true;
};
//微信支付
const wxPay = async () => {
  //调用接口
  var year = startTime.value.getFullYear();
var month = ('0' + (startTime.value.getMonth() + 1)).slice(-2);
var day = ('0' + startTime.value.getDate()).slice(-2);
var hours = ('0' + startTime.value.getHours()).slice(-2);
var minutes = ('0' + startTime.value.getMinutes()).slice(-2);
var seconds = ('0' + startTime.value.getSeconds()).slice(-2);
var formattedDateString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  orderSubmit.value.addressBookId = address.value.id,
  orderSubmit.value.amount = userGoods.value.price,
  orderSubmit.value.beginDeliveryTime =formattedDateString,
  orderSubmit.value.goodsId = userGoods.value.id,
  orderSubmit.value.payMethod =1,
  orderSubmit.value.remark = remark.value,
  orderSubmit.value.sellId = userGoods.value.userId;
  let result = await orderSubmitService(orderSubmit.value);
  //隐藏弹窗
  dialogVisible2.value = false;
  order.value = result.data;

  // 定义 orderTime 和当前时间的字符串
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
  imageTemp.value = '/src/assets/wx.png';
  orderTemp();
};
//支付宝支付
const zfbPay = async () => {
 //调用接口
  let year = startTime.value.getFullYear();
let month = ('0' + (startTime.value.getMonth() + 1)).slice(-2);
let day = ('0' + startTime.value.getDate()).slice(-2);
let hours = ('0' + startTime.value.getHours()).slice(-2);
let minutes = ('0' + startTime.value.getMinutes()).slice(-2);
let seconds = ('0' + startTime.value.getSeconds()).slice(-2);
let formattedDateString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  orderSubmit.value.addressBookId = address.value.id,
  orderSubmit.value.amount = userGoods.value.price,
  orderSubmit.value.beginDeliveryTime =formattedDateString,
  orderSubmit.value.goodsId = userGoods.value.id,
  orderSubmit.value.payMethod =2,
  orderSubmit.value.remark = remark.value,
  orderSubmit.value.sellId = userGoods.value.userId;
  let result = await orderSubmitService(orderSubmit.value);
  //隐藏弹窗
  dialogVisible2.value = false;
  order.value = result.data;

  // 定义 orderTime 和当前时间的字符串
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
  imageTemp.value = '/src/assets/zfb.jpg';
  orderTemp();
};

//订单数据
const orderTemp = () => {
 dialogVisible3.value = true;
};

//支付订单
const paySure = async () => {
    payMent.value.orderNumber = order.value.orderNumber;
    payMent.value.payMethod = orderSubmit.value.payMethod;
    let result = await payMentService(payMent.value);
    ElMessage.success(result.msg ? result.msg : "支付成功");
      //隐藏弹窗
  dialogVisible3.value = false;
}

//清空模型的数据
const clearData = () => {
 remark.value = '';
 startTime.value ='';
};
</script>
<template>
    <el-card class="page-container">
        <template #header>
            <div class="header">
                <span>我的收藏</span>
            </div>
        </template>
        <!-- 商品列表 --> 
        <el-table :data="goods" style="width: 100%">
            <el-table-column label="商品名"  prop="name"></el-table-column>
            <el-table-column label="图片" prop="image"> 
                <template #default="scope">
                    <img v-if="scope.row.image" :src="scope.row.image" class="image" />
                </template>
            </el-table-column>
            <el-table-column label="价格" prop="price"></el-table-column>
            <el-table-column label="成色" prop="olds"></el-table-column>
            <el-table-column label="商品分类" prop="categoryName"></el-table-column>
            <el-table-column label="人气值"  prop="popularity"></el-table-column>
            <el-table-column label="操作" width="235px"> 
                <template #default="{ row }">
                    <el-button type="primary"  @click="showVisibleTemp(row.goodsId)">查看详情</el-button>
                    <el-button type="warning" @click="updateCollectionTotal(row, 0)">取消收藏</el-button>
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


           <!-- 商品详情页弹窗 -->
    <el-dialog v-model="dialogVisible" title="商品详情" width="50%">
      <img
        class="userGoods-image"
        :src="userGoods.image"
        :alt="userGoods.name"
      />
      <div class="userGoods-details">
        <div class="userGoods-title">{{ userGoods.name }}</div>
        <div class="userGoods-olds">{{ userGoods.olds }}</div>
        <br />
        <div class="product-popularity">
          浏览 {{ userGoods.lookTotal }} 点赞 {{ userGoods.praiseTotal }} 收藏
          {{ userGoods.collectionTotal }} 评论
          {{ userGoods.commentTotal }} 人气值 {{ userGoods.popularity }}
        </div>
        <br />
        <div class="userGoods-price">¥ {{ userGoods.price }}</div>
        <br />
        <div class="product-popularity">卖家姓名: {{ userGoods.userName }}</div>
        <div class="product-popularity">
          卖家学院: {{ userGoods.collegeName }}
        </div>
        <div class="product-popularity">
          卖家宿舍: {{ userGoods.dormitoryName }}
        </div>
        <div class="product-popularity">卖家电话: {{ userGoods.phone }}</div>
        <br />
        <div class="product-popularity">
          发布日期: {{ userGoods.updateTime }}
        </div>
        <br />
        <el-button
          v-if="userGoods.praise == 1"
          type="success"
          @click="updatePraiseTotal(userGoods, 0)"
          >已点赞</el-button
        >
        <el-button
          v-if="userGoods.praise == 0"
          type="success"
          @click="updatePraiseTotal(userGoods, 1)"
          >点赞</el-button
        >
        <el-button
          v-if="userGoods.collection == 1"
          type="warning"
          @click="updateCollectionTotal(userGoods, 0)"
          >已收藏</el-button
        >
        <el-button
          v-if="userGoods.collection == 0"
          type="warning"
          @click="updateCollectionTotal(userGoods, 1)"
          >收藏</el-button
        >
        <el-button type="danger" @click="getAddress();clearData();">立即购买</el-button>
      </div>

      <p class="userGoods-olds">
        <b>卖家描述：</b> {{ userGoods.description }}
      </p>
      <p class="userGoods-olds"><b>用户评论：</b> 没有用户进行评论！！！</p>
    </el-dialog>

    <!-- 购买详情弹窗 -->
    <el-dialog v-model="dialogVisible2" title="购买详情" width="50%">
      <div class="address-container">
        <div class="address-title">
          <span class="label">{{ address.label }}</span>
          <span class="together-address" v-if="address.togetherAddress">{{
            address.togetherAddress
          }}</span>
          <span class="empty-address" v-else>地址为空</span>
        </div>
        <div class="address-neiRong">
          <span class="consignee">{{ address.consignee }}</span>
          <span class="phone">{{ address.phone }}</span>
          <el-button
            type="warning"
            class="custom-button"
            @click="updateAddress()"
            >修改地址</el-button
          >
        </div>
      </div>
      <div class="orders-time">
        请选择交易时间：
        <el-date-picker
        v-model="startTime"
        type="datetime"
        placeholder="请选择日期和时间"
        :default-time="defaultTime"
      />
      </div>
      <br /><br />
      <img class="orders-image" :src="userGoods.image" :alt="userGoods.name" />
      <div class="userGoods-details">
        <div class="userGoods-title">{{ userGoods.name }}</div>
        <div class="userGoods-olds">{{ userGoods.olds }}</div>
        <div class="userGoods-price">¥ {{ userGoods.price }}</div>
        <br />
      </div>
      <br /><br />
      <el-form-item label="备注">
        <el-input v-model="remark" placeholder="请输入备注" style="width: 500px;"></el-input>
      </el-form-item>
      <br />
<el-button type="success" class="pay-button" @click="wxPay()">微信支付</el-button>
<el-button type="primary" class="pay-button2" @click="zfbPay()">支付宝支付</el-button>
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
.userGoods-container {
  overflow: hidden; /* 清除浮动 */
}

.userGoods-image {
  float: left; /* 图片浮动在左侧 */
  margin-right: 20px; /* 图片右侧留出一些空间 */
  width: 330px; /* 设置图片的最大宽度 */
  height: 415px; /* 允许高度自适应 */
}

.userGoods-details {
  clear: left; /* 清除左侧浮动元素的影响 */
  overflow: hidden; /* 清除浮动 */
  display: inline-block; /* 将详情部分设置为内联块级元素 */
  vertical-align: top; /* 使其与左侧图片顶部对齐 */
  line-height: 1.8; /* 设置较大的行间距 */
  white-space: pre-wrap; /* 保留空白符序列，允许换行 */
}
.userGoods-title {
  font-size: 20px; /* 设置字体大小为16像素 */
  font-weight: bold; /* 设置为加粗 */
  color: black; /* 设置颜色为黑色 */
}
.userGoods-price {
  font-size: 20px; /* 设置字体大小为16像素 */
  font-weight: bold; /* 设置为加粗 */
  color: red; /* 设置颜色为黑色 */
}
.userGoods-olds {
  font-size: 16px; /* 设置字体大小为16像素 */
  color: black; /* 设置字体颜色为深灰色（可以根据需要调整颜色值） */
}
.userGoods-popularity {
  font-size: 16px; /* 设置字体大小为16像素 */
  color: grey; /* 设置字体颜色为深灰色（可以根据需要调整颜色值） */
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
  width: 50px; /* 调整头像显示的大小 */
  height: 50px;
}

.address-title .label {
  background-color: lightgreen;
  font-size: 17px;
  font-weight: normal;
  margin-right: 10px;
}

.address-title .together-address {
  font-weight: bold;
  font-size: 20px;
  color: black;
}

.address-neiRong {
  margin-top: 20px; /* 您可以根据需要调整此值 */
}
.orders-time {
  margin-top: 20px; /* 您可以根据需要调整此值 */
}

.address-neiRong .consignee {
  font-size: 17px;
  font-weight: normal;
  margin-right: 10px;
}

.address-neiRong .phone {
  font-size: 17px;
  font-weight: normal;
  margin-right: 30px;
}

.custom-button {
  width: 80px;
  height: 30px;
}

.orders-image {
  float: left; /* 图片浮动在左侧 */
  margin-right: 20px; /* 图片右侧留出一些空间 */
  width: 100px; /* 设置图片的最大宽度 */
  height: 115px; /* 允许高度自适应 */
}

.empty-address {
  color: #999; /* 灰色字体颜色 */
  font-size: 17px;
  font-style: italic; /* 斜体字体样式 */
  /* 其他样式定义 */
}

.pay-button {
  position: absolute;
  right: 85%;
  transform: translateY(-50%);
}
.pay-button2 {
  position: absolute;
  right: 70%;
  transform: translateY(-50%);
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