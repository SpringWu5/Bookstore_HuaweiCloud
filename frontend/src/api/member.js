import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'


//用户列表查询
export const memberListService = (params)=>{
   return  request.get('/user/user/page',{params:params})
}

//用户修改
export const memberUpdateService = (userData)=>{
    return  request.put('/user/user',userData)
 } 

//用户删除
export const memberDeleteService = (id) => {
    return request.delete(`/user/user/${id}`, { params: { id: id } });
}

//启用禁用用户
export const userStartStopService = (id,locking)=>{
    return request.post('/user/user/locking?id=' + id + '&locking=' + locking) 
}