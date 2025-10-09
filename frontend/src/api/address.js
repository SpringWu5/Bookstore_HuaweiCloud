import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//地址列表查询
export const addressListService = (params)=>{ 
   return  request.get('/admin/address/page',{params:params})
}

//地址修改
export const addressUpdateService = (userData)=>{
    return  request.put('/admin/address',userData)
 } 

//新增地址
export const addressAddService = (userData)=>{
    return  request.post('/user/address',userData)
 } 
 
//地址删除
export const addressDeleteService = (id) => {
    return request.delete(`/admin/address/${id}`, { params: { id: id } });
}

//设置默认地址
export const userStartStopService = (id,isDefault)=>{
    return request.post('/admin/address/isDefault?id=' + id + '&isDefault=' + isDefault) 
}

//地址列表查询
export const userAddressListService = ()=>{ 
    return  request.get('/user/address')
 }

 //查询是否存在默认地址
export const getAddressService = ()=>{ 
    return  request.get('/user/address/isDefault')
 }