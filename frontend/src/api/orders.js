import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//用户下单
export const orderSubmitService = (orderSubmit)=>{
   return  request.post('/user/order/submit',orderSubmit)
}
//用户支付
export const payMentService = (payMent)=>{
   return  request.put('/user/order/payment',payMent)
} 

//订单列表查询
export const ordersListService = (params)=>{
   return  request.get('/admin/order/page',{params:params})
} 
//接单、派送、完成
export const ordersConfirmService = (id,status)=>{
   return request.post('/admin/order/confirm?id=' + id + '&status=' + status) 
}
//拒单
export const ordersRejectionService = (id,reason)=>{
   return request.post('/admin/order/rejection?id=' + id + '&reason=' + reason) 
}
//接单、派送、完成
export const ordersCancelService = (id,reason)=>{
   return request.post('/admin/order/cancel?id=' + id + '&reason=' + reason) 
}

//卖家订单列表查询
export const sellOrdersListService = (params)=>{
   return  request.get('/user/order/sell/page',{params:params})
} 

//卖家订单列表查询
export const buyOrdersListService = (params)=>{
   return  request.get('/user/order/buy/page',{params:params})
} 

//根据id查询订单
export const getByOrdersIDService = (id) => {
   return request.get(`/user/order/${id}`, { params: { id: id } });
}
