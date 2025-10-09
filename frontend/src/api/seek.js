import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//求购列表查询
export const seekListService = (params)=>{
   return  request.get('/admin/seek/page',{params:params})
}

 //求购添加
export const seekAddService = (userData)=>{
    return request.post('/admin/seek',userData);  
}

//求购修改
export const seekUpdateService = (userData)=>{
    return  request.put('/admin/seek',userData)
 } 

//求购删除
export const seekDeleteService = (id) => {
    return request.delete(`/admin/seek/${id}`, { params: { id: id } });
}

//启用禁用求购
export const userStartStopService = (id,status)=>{
    return request.post('/admin/seek/status?id=' + id + '&status=' + status) 
}
//求购列表查询(用户)
export const userSeekListService = (params)=>{
    return  request.get('/user/seek/page',{params:params})
 }

 //求购列表查询(用户)
 export const seekMyListService = (params)=>{
    return  request.get('/user/seek/mySeek/page',{params:params})
 }