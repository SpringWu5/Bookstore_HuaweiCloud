import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//商品列表查询
export const postsListService = (params)=>{
   return  request.get('/admin/posts/page',{params:params})
}

 //商品添加
export const postsAddService = (userData)=>{
    return request.post('/admin/posts',userData);  
} 

//商品修改
export const postsUpdateService = (userData)=>{
    return  request.put('/admin/posts',userData)
 } 

//商品删除
export const postsDeleteService = (id) => {
    return request.delete(`/admin/posts/${id}`, { params: { id: id } });
}

//启用禁用商品
export const userStartStopService = (id,status)=>{
    return request.post('/admin/posts/status?id=' + id + '&status=' + status) 
}
//商品列表查询(用户)
export const userPostsListService = (params)=>{
    return  request.get('/user/posts/page',{params:params})
 }