<script setup>
import { Edit, Delete, Lock, Unlock, Search } from "@element-plus/icons-vue";

import { ref, onMounted } from "vue";

//引入登录人的个人信息
import useUserInfoStore from "@/stores/userInfo.js";
const userInfoStore = useUserInfoStore();

const userInfo = ref({ ...userInfoStore.info });

//导入token
import { useTokenStore } from "@/stores/token.js";
const tokenStore = useTokenStore();

//引入路由
import { useRouter } from "vue-router";
const router = useRouter();

import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";

//商品分类数据模型
const category = ref([
  {
    id: "",
    name: "",
    status: "",
    createTime: "",
    updateTime: "",
    createUser: "",
    updateUser: "",
  },
]);

//商品列表数据模型
const goods = ref([
  {
    id: "",
    name: "",
    categoryId: "",
    price: "",
    image: "",
    olds: "",
    description: " ",
    status: "",
    collectionTotal: "",
    praiseTotal: "",
    commentTotal: "",
    lookTotal: "",
    popularity: "",
    createTime: "",
    updateTime: "",
    createUser: "",
    updateUser: "",
  },
]);

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

//新增表单数据模型
const goodsModel = ref({
  categoryId: "",
  description: "",
  id: "",
  image: "",
  name: "",
  olds: "",
  price: "",
});

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
const orderSubmit = ref({
  addressBookId: "",
  amount: "",
  beginDeliveryTime: "",
  goodsId: "",
  payMethod: "",
  remark: "",
  sellId: "",
});

//订单数据模型
const order = ref({
  id: 0,
  orderNumber: "",
  orderAmount: 0,
  orderTime: "",
});

//支付数据模型
const payMent = ref({
  orderNumber: "",
  payMethod: 0,
});

//新增评论数据模型
const commentModel = ref({
  content: "",
  createTime: "",
  createUser: 0,
  foreignId: 0,
  foreignName: "",
  pid: 0,
  target: "",
  userId: 0,
  userName: "",
});

//评论列表数据模型
const comment = ref([
  {
    id: "",
    content: "",
    createTime: "",
    createUser: 0,
    avatar: "",
    foreignId: 0,
    foreignName: "",
    pid: "",
    target: "",
    userId: 0,
    userName: "",
    "children": [
				{
					"avatar": "",
					"content": "",
					"createTime": "",
					"createUser": 0,
					"foreignId": 0,
					"foreignName": "",
					"id": 0,
					"pid": 0,
					"target": "",
					"userId": 0,
					"userName": ""
				}
			],
  },
]);

//时间选择器
const startTime = ref("");
const defaultTime = new Date(2000, 1, 1, 12, 0, 0);

//订单失效
const remainTime = ref("");

//支付图片
const imageTemp = ref("");

//备注信息
const remark = ref("");

//商品搜索时选中的商品分类id
const categoryId = ref("");

//商品搜索时选中的成色id
const olds = ref("");

//商品搜索时选中的状态
const status = ref("");

//商品搜索时输入的姓名
const name = ref("");

//商品搜索时输入的排序方式
const sortBy = ref("popularity");

//商品搜索时输入的排序顺序
const isAsc = ref("");

//分页条数据模型
const page = ref(1); //当前页
const total = ref(15); //总条数
const pageSize = ref(15); //每页条数

//当每页条数发生了变化，调用此函数
const onSizeChange = (size) => {
  pageSize.value = size;
  goodsList();
};
//当前页码发生变化，调用此函数
const onCurrentChange = (num) => {
  page.value = num;
  goodsList();
};

//回显商品分类
import { userGoodsCategoryListService } from "@/api/goodsCategory.js";
import { orderSubmitService, payMentService } from "@/api/orders.js";
import { commentAddService, commentListService } from "@/api/comment.js";
import { getAddressService } from "@/api/address.js";
import {
  userGoodsListService,
  userUpdatePraiseTotal,
  userUpdateCollectionTotal,
  goodsDescriptionService,
  goodsAddService,
  goodsUpdateService,
  goodsDeleteService,
  userStartStopService,
} from "@/api/goods.js";
const goodsCategoryList = async () => {
  let result = await userGoodsCategoryListService();
  category.value = result.data;
};

//查询全部分类
goodsCategoryList();

//图片上传成功的回调函数
const uploadSuccess = (result) => {
  goodsModel.value.image = result.data;
};

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
    isAsc: isAsc.value ? isAsc.value : null,
  };
  let result = await userGoodsListService(params);
  //渲染视图
  total.value = result.data.total;
  goods.value = result.data.records;

  //处理数据,给数据模型扩展一个属性categoryName,分类名称
  for (let i = 0; i < goods.value.length; i++) {
    let temp = goods.value[i];
    for (let j = 0; j < category.value.length; j++) {
      if (temp.categoryId == category.value[j].id) {
        temp.categoryName = category.value[j].name;
      }
    }
  }
  //查询全部分类
  goodsCategoryList();
};
//查询全部商品
goodsList();

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
  router.push("/user/person/address");
};

import { Plus } from "@element-plus/icons-vue";
//控制抽屉是否显示
const visibleDrawer = ref(false);
//控制商品详情页面是否显示
const dialogVisible = ref(false);
const dialogVisible2 = ref(false);
const dialogVisible3 = ref(false);
const dialogVisible4 = ref(false);
import { ElMessage } from "element-plus";
//定义变量,控制标题的展示
const title = ref("");

const visibleDrawerTemp = () => {
  //删除第一个分类
  category.value.shift();
  visibleDrawer.value = true;
};

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
  //获取评论列表
  let foreignName = "goods";
  let foreignId = userGoods.value.id;
  let result2 = await commentListService(foreignName, foreignId);
  comment.value = result2.data;

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

//修改商品
const updateGoods = async () => {
  //调用接口
  let result = await goodsUpdateService(goodsModel.value);

  ElMessage.success(result.msg ? result.msg : "修改成功");

  //调用获取所有分类的函数
  goodsList();

  //隐藏弹窗
  visibleDrawer.value = false;
};

//发布商品
const addGoods = async () => {
  console.log(goodsModel.value);
  //调用接口
  let result = await goodsAddService(goodsModel.value);

  ElMessage.success(result.msg ? result.msg : "新增成功");
  //调用获取所有分类的函数
  goodsList();

  //隐藏弹窗
  visibleDrawer.value = false;
};

//微信支付
const wxPay = async () => {
  //调用接口
  var year = startTime.value.getFullYear();
  var month = ("0" + (startTime.value.getMonth() + 1)).slice(-2);
  var day = ("0" + startTime.value.getDate()).slice(-2);
  var hours = ("0" + startTime.value.getHours()).slice(-2);
  var minutes = ("0" + startTime.value.getMinutes()).slice(-2);
  var seconds = ("0" + startTime.value.getSeconds()).slice(-2);
  var formattedDateString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  (orderSubmit.value.addressBookId = address.value.id),
    (orderSubmit.value.amount = userGoods.value.price),
    (orderSubmit.value.beginDeliveryTime = formattedDateString),
    (orderSubmit.value.goodsId = userGoods.value.id),
    (orderSubmit.value.payMethod = 1),
    (orderSubmit.value.remark = remark.value),
    (orderSubmit.value.sellId = userGoods.value.userId);
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
  imageTemp.value = "/src/assets/wx.png";
  orderTemp();
};
//支付宝支付
const zfbPay = async () => {
  //调用接口
  let year = startTime.value.getFullYear();
  let month = ("0" + (startTime.value.getMonth() + 1)).slice(-2);
  let day = ("0" + startTime.value.getDate()).slice(-2);
  let hours = ("0" + startTime.value.getHours()).slice(-2);
  let minutes = ("0" + startTime.value.getMinutes()).slice(-2);
  let seconds = ("0" + startTime.value.getSeconds()).slice(-2);
  let formattedDateString = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  (orderSubmit.value.addressBookId = address.value.id),
    (orderSubmit.value.amount = userGoods.value.price),
    (orderSubmit.value.beginDeliveryTime = formattedDateString),
    (orderSubmit.value.goodsId = userGoods.value.id),
    (orderSubmit.value.payMethod = 2),
    (orderSubmit.value.remark = remark.value),
    (orderSubmit.value.sellId = userGoods.value.userId);
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
  imageTemp.value = "/src/assets/zfb.jpg";
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
};

//清空模型的数据
const clearData = () => {
  remark.value = "";
  startTime.value = "";
};

//清空新增表单的数据
const clearData2 = () => {
  goodsModel.value.categoryId= "";
  goodsModel.value.description= " ";
  goodsModel.value.id= "";
  goodsModel.value.image= "";
  goodsModel.value.name= "";
  goodsModel.value.olds= "";
 goodsModel.value. price= "";
};

//删除商品
import { ElMessageBox } from "element-plus";
const deleteUser = (row) => {
  //提示商品  确认框
  ElMessageBox.confirm("你确认要删除该商品信息吗?", "温馨提示", {
    confirmButtonText: "确认",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      //调用接口
      let result = await goodsDeleteService(row.id);
      ElMessage({
        type: "success",
        message: "删除成功",
      });
      //刷新列表
      goodsList();
    })
    .catch(() => {
      ElMessage({
        type: "info",
        message: "商品取消了删除",
      });
    });
};
//启用禁用
const statususer = (row, status) => {
  //提示商品  确认框
  if (status == 0) {
    ElMessageBox.confirm("你确认要禁用该分类信息吗?", "温馨提示", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        //调用接口
        let result = await userStartStopService(row.id, status);
        ElMessage({
          type: "success",
          message: "禁用成功",
        });
        //刷新列表
        goodsList();
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "您取消了禁用",
        });
      });
  } else {
    ElMessageBox.confirm("你确认要启用该分类信息吗?", "温馨提示", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        //调用接口
        let result = await userStartStopService(row.id, status);
        ElMessage({
          type: "success",
          message: "启用成功",
        });
        //刷新列表
        goodsList();
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "您取消了启用",
        });
      });
  }
};

//新增评论
const commentSubmit = async () => {
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "goods";
  commentModel.value.foreignId = userGoods.value.id;
  //调用接口
  let result = await commentAddService(commentModel.value);
  ElMessage.success(result.msg ? result.msg : "评论成功");

  //查询全部商品
  goodsList();
  dialogVisible.value = false;
  commentModel.value.content = "";
  showVisibleTemp(userGoods.value.id);
};

//回复temp
const reply = (pid,target) => {
  commentModel.value.pid = pid;
  commentModel.value.target = target;
  commentModel.value.userId = userInfo.value.id;
  commentModel.value.userName = userInfo.value.name;
  commentModel.value.avatar = userInfo.value.avatar;
  commentModel.value.foreignName = "goods";
  commentModel.value.foreignId = userGoods.value.id;
  dialogVisible4.value = true;
};

//新增回复评论
const saveReply = async () => {
  //调用接口
  let result = await commentAddService(commentModel.value);
  ElMessage.success(result.msg ? result.msg : "回复成功");

  //查询全部商品
  goodsList();
  dialogVisible.value = false;
  dialogVisible4.value = false;
  commentModel.value.content = "";
  commentModel.value.pid = 0;
  commentModel.value.target = "";
  showVisibleTemp(userGoods.value.id);
};

// 分类按钮切换
function selectCategory(id) {
  categoryId.value = id;
  goodsList();
}

function changeSort(type) {
  sortBy.value = type;
  goodsList();
}

</script>
<template>
  <div>
    <!-- 搜索区 -->
    <div class="el-search">
      <el-form inline>
        <el-form-item style="width: 500px; margin-right: 20px">
          <el-input
            v-model="name"
            placeholder="搜索商品"
            clearable
            @clear="goodsList"
            style="height: 40px"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            :icon="Search"
            type="primary"
            @click="goodsList"
            circle
            style="width: 40px; height: 40px"
          />
        </el-form-item>
      </el-form>
    </div>
    <!-- 分类按钮区 -->
    <div class="category-bar">
      <el-button
        v-for="c in category"
        :key="c.id"
        :type="categoryId === c.id ? 'primary' : 'default'"
        class="category-btn"
        @click="selectCategory(c.id)"
      >
        {{ c.name || '全部' }}
      </el-button>
    </div>
    <!-- 商品区（原有内容） -->
    <el-card class="page-container">
      <template #header>
        <div class="header" style="display: flex; align-items: center; justify-content: space-between;">
          <div style="display: flex; align-items: center; gap: 24px;">
            <span style="font-size: 26px; font-weight: bold; color: #ff6700;">商品展示</span>
            <div class="sort-bar" style="display: flex; gap: 12px;">
              <el-button :type="sortBy === 'popularity' ? 'primary' : 'default'" @click="changeSort('popularity')">最热</el-button>
              <el-button :type="sortBy === 'update_time' ? 'primary' : 'default'" @click="changeSort('update_time')">最新</el-button>
            </div>
          </div>
          <div class="extra">
            <el-button type="primary" @click="visibleDrawerTemp(); title = '闲置'; clearData2();">闲置</el-button>
          </div>
        </div>
      </template>

      <!-- 商品列表 -->
      <div class="product-container">
        <div class="product-item" v-for="good in goods" :key="good.id">
          <img
            class="product-image"
            :src="good.image"
            :alt="good.name"
            @click="showVisibleTemp(good.id)"
          />
          <div class="product-title">{{ good.name }} {{ good.olds }}</div>
          <div class="product-price">价格：¥{{ good.price }}</div>
          <div class="product-popularity">人气值 {{ good.popularity }}</div>
        </div>
      </div>
      <!-- 分页条 -->
      <el-pagination
        v-model:current-page="page"
        v-model:page-size="pageSize"
        :page-sizes="[15, 25, 35]"
        layout="jumper, total, sizes, prev, pager, next"
        background
        :total="total"
        @size-change="onSizeChange"
        @current-change="onCurrentChange"
        style="margin-top: 20px; justify-content: flex-end"
      />

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
          <el-button
            type="danger"
            @click="
              getAddress();
              clearData();
            "
            >立即购买</el-button
          >
        </div>

        <p class="userGoods-olds">
          <b>卖家描述：</b> {{ userGoods.description }}
        </p>
        <p class="userGoods-olds"><b>用户评论：</b></p>
        <div>
          <el-input
            type="textarea"
            placeholder="请输入评论"
            v-model="commentModel.content"
          ></el-input>
          <div style="text-align: right; margin: 10px 0">
            <el-button type="primary" @click="commentSubmit()">提交</el-button>
          </div>
        </div>
        <!-- 评论列表 -->
        <div style="margin: 20px 0">
          <div
            style="
              margin: 10px 0;
              font-size: 24px;
              padding: 10px o;
              border-bottom: 1px solid #ccc;
              text-align: left;
            "
          >
            评论列表
          </div> 
          <div style="margin: 20px 0; text-align: left">
            <div
              v-for="item in comment"
              :key="item.id"
              style="padding: 10px 0;  align-items: center"
            >
            <div style="display: flex;">
              <div style="width: 60px; margin-right: 10px">
                <el-avatar :size="50" :src="item.avatar"></el-avatar>
              </div>
              <div style="flex: 1">
                <div style="font-weight: bold; font-size: 18px">
                  {{ item.userName }}<span style="margin-left:10px;font-weight: normal; font-size: 15px">{{ item.createTime}}</span>
                </div>
                <div style="margin-top: 7px; color: #666">{{ item.content }}</div>
            <!-- 多级回复 -->
            <div style="text-align:left;">
              <el-button type="text" @click="reply(item.id,item.userName)">回复</el-button>
            </div>
            <!-- 回复列表 -->
            <div v-if="item.children.length" style="margin-left:40px;background-color:aliceblue;padding:10px;border-radius:10px">
              <div v-for="sub in item.children" :key="sub.id">
                <div style="padding:5px 0"><b style="cursor:pointer" @click="reply(sub.pid,sub.userName)">{{sub.userName}} </b><span> 回复 
                  <span style="color:cornflowerblue">@{{sub.target}}</span>
                 <span style="color:#666;margin-left:10px">{{sub.content}}</span></span>
                 <span style="float:right;font-weight: normal; font-size: 13px;color:#666;margin-top:3px">{{ sub.createTime}}</span>
                 </div>
              </div>
            </div>
              </div>
            </div>
            </div>
          </div>
        </div>
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
          <el-input
            v-model="remark"
            placeholder="请输入备注"
            style="width: 500px"
          ></el-input>
        </el-form-item>
        <br />
        <el-button type="success" class="pay-button" @click="wxPay()"
          >微信支付</el-button
        >
        <el-button type="primary" class="pay-button2" @click="zfbPay()"
          >支付宝支付</el-button
        >
      </el-dialog>

      <!-- 订单详情弹窗 -->
      <el-dialog v-model="dialogVisible3" title="订单详情" width="50%">
        <div class="order-container">
          <div class="order-time">支付剩余时间：{{ remainTime }}</div>
          <div class="order-price">¥ {{ order.orderAmount }}</div>
          <div class="order-number">订单号：{{ order.orderNumber }}</div>
          <br /><br />
          <div>
            <img :src="imageTemp" class="image-wx" alt="Description of Image" />
          </div>
          <br /><br />
          <div class="order-sure">
            <el-button type="warning" @click="paySure()">确认支付</el-button>
          </div>
        </div>
      </el-dialog>
      <!-- 回复评论弹窗 -->
      <el-dialog v-model="dialogVisible4" title="回复" width="30%">
      <el-form :model="commentModel">
        <el-form-item>
          <el-input  type="textarea"  :rows="4" placeholder="请输入内容"  v-model="commentModel.content" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <el-form-item>
            <el-button
              @click="
                dialogVisible4 = false;
                commentModel.content='';
              "
              >取消</el-button
            >
            <el-button type="primary" @click="saveReply()">确定</el-button>
          </el-form-item>
      </el-dialog>

      <!-- 抽屉 -->
      <el-drawer
        v-model="visibleDrawer"
        :title="title"
        direction="rtl"
        size="50%"
      >
        <!-- 修改/发布表单 -->
        <el-form :model="goodsModel" label-width="100px">
          <el-form-item label="商品名">
            <el-input
              v-model="goodsModel.name"
              placeholder="请输入商品名"
            ></el-input>
          </el-form-item>
          <el-form-item label="价格">
            <el-input
              v-model="goodsModel.price"
              placeholder="请输入价格"
            ></el-input>
          </el-form-item>
          <el-form-item label="成色">
            <el-select
              style="width: 200px"
              placeholder="请选择"
              v-model="goodsModel.olds"
            >
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

            <el-upload
              class="image-uploader"
              :auto-upload="true"
              :show-file-list="false"
              action="/api/admin/common/upload"
              name="file"
              :headers="{ token: tokenStore.token }"
              :on-success="uploadSuccess"
            >
              <img
                v-if="goodsModel.image"
                :src="goodsModel.image"
                class="image"
              />
              <el-icon v-else class="image-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="商品分类">
            <el-select
              style="width: 200px"
              placeholder="请选择"
              v-model="goodsModel.categoryId"
            >
              <el-option
                v-for="c in category"
                :key="c.id"
                :label="c.name"
                :value="c.id"
              >
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
            <el-button
              @click="
                visibleDrawer = false;
                goodsCategoryList();
              "
              >取消</el-button
            >
            <el-button type="primary" @click="addGoods()">发布</el-button>
          </el-form-item>
        </el-form>
      </el-drawer>
    </el-card>
  </div>
</template>

<style lang="scss" scoped>
$jd-orange: #ff6700;
$jd-orange-dark: #e55300;
$jd-gray: #f5f5f5;
$jd-border: #ececec;
$jd-shadow: 0 2px 16px rgba(0,0,0,0.06);
$jd-radius: 14px;

.product-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: flex-start;
  background: $jd-gray;
  padding: 32px 16px 16px 16px;
  border-radius: 0;
}
.product-item {
  background: #fff;
  border-radius: 0;
  box-shadow: $jd-shadow;
  padding: 12px 12px 10px 12px;
  width: 220px;
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: box-shadow 0.2s, transform 0.2s;
  border: 1.5px solid $jd-border;
  cursor: pointer;
  &:hover {
    box-shadow: 0 8px 32px rgba(255,103,0,0.13);
    transform: translateY(-6px) scale(1.03);
    border: 1.5px solid $jd-orange;
  }
}
.product-image {
  width: 180px;
  height: 180px;
  object-fit: cover;
  border-radius: 0;
  margin-bottom: 14px;
  background: $jd-gray;
  border: 1px solid $jd-border;
}
.product-title {
  font-size: 17px;
  font-weight: 500;
  color: #222;
  margin-bottom: 6px;
  text-align: center;
  line-height: 1.2;
}
.product-price {
  color: $jd-orange;
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 4px;
}
.product-popularity {
  font-size: 13px;
  color: #888;
  margin-bottom: 2px;
}

.el-search {
  background: none;
  box-shadow: none;
  padding: 32px 0 0 0;
  display: flex;
  align-items: center;
  justify-content: center;
  .el-form {
    background: none;
    box-shadow: none;
    border-radius: 0;
    padding: 0;
    border: none;
    display: flex;
    align-items: center;
    gap: 18px;
  }
}

.page-container {
  background: #fff;
  border-radius: $jd-radius;
  box-shadow: $jd-shadow;
  margin-top: 24px;
  padding: 24px 32px 32px 32px;
  .header {
    display: flex;
    align-items: center;
    height: 36px;
    justify-content: space-between;
    font-size: 22px;
    font-weight: bold;
    color: $jd-orange-dark;
    padding-bottom: 0;
    margin-bottom: 0;
    .extra .el-button {
      background: $jd-orange;
      border: none;
      color: #fff;
      border-radius: 8px;
      font-weight: bold;
      &:hover {
        background: $jd-orange-dark;
      }
    }
  }
}

.el-button,
.pay-button,
.pay-button2,
.custom-button {
  background: $jd-orange;
  border: none;
  color: #fff;
  border-radius: 22px;
  font-weight: bold;
  transition: background 0.2s;
  min-width: 90px;
  min-height: 36px;
  font-size: 15px;
  &:hover {
    background: $jd-orange-dark;
  }
}
.el-button[type="primary"] {
  background: $jd-orange !important;
  border: none !important;
  color: #fff !important;
  border-radius: 8px !important;
  font-weight: bold;
  transition: background 0.2s;
}
.el-button[type="primary"]:hover, .el-button[type="primary"]:focus {
  background: $jd-orange-dark !important;
  color: #fff !important;
}
.el-button[type="success"] {
  background: #ffa726;
  border: none;
  color: #fff;
  &:hover {
    background: #fb8c00;
  }
}
.el-button[type="warning"] {
  background: #ffb300;
  border: none;
  color: #fff;
  &:hover {
    background: #ffa000;
  }
}
.el-button[type="danger"] {
  background: #ff7043;
  border: none;
  color: #fff;
  &:hover {
    background: #d84315;
  }
}

.el-select .el-input__inner:focus {
  border-color: $jd-orange;
}
.el-select-dropdown__item.selected {
  color: $jd-orange-dark;
  font-weight: bold;
}

.userGoods-image {
  float: left;
  margin-right: 32px;
  width: 320px;
  height: 400px;
  border-radius: $jd-radius;
  border: 2px solid $jd-border;
  background: $jd-gray;
}
.userGoods-details {
  clear: left;
  overflow: hidden;
  display: inline-block;
  vertical-align: top;
  line-height: 1.8;
  white-space: pre-wrap;
}
.userGoods-title {
  font-size: 22px;
  font-weight: bold;
  color: $jd-orange-dark;
}
.userGoods-price {
  font-size: 20px;
  font-weight: bold;
  color: $jd-orange;
}
.userGoods-olds {
  font-size: 16px;
  color: #333;
}

.el-dialog__header {
  background: $jd-gray;
  border-radius: $jd-radius $jd-radius 0 0;
}
.el-dialog__title {
  color: $jd-orange-dark;
  font-weight: bold;
}

.el-pagination {
  .el-pager li.active {
    background: $jd-orange;
    color: #fff;
    border-radius: 6px;
  }
  .el-pager li {
    font-size: 15px;
    border-radius: 6px;
    margin: 0 2px;
  }
}

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

.category-bar {
  display: flex;
  gap: 16px;
  margin: 18px 0 24px 0;
  justify-content: center;
  flex-wrap: wrap;
}
.category-btn {
  min-width: 90px;
  border-radius: 22px;
  font-size: 15px;
  font-weight: 500;
  background: #fff;
  color: #222;
  border: 1.5px solid #ececec;
  transition: all 0.2s;
}
.category-btn.el-button--primary {
  background: #ff6700;
  color: #fff;
  border: 1.5px solid #ff6700;
}
.category-btn:hover {
  background: #ffe0cc;
  color: #ff6700;
  border: 1.5px solid #ff6700;
}
</style>