import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//学院列表查询
export const forumListService = ()=>{
    return request.get('/admin/forum')
}

//学院分类添加
export const forumAddService = (categoryData)=>{
    return request.post('/admin/forum',categoryData)
}

//学院分类修改
export const forumUpdateService = (categoryData)=>{
   return  request.put('/admin/forum',categoryData)
}

//学院分类删除
export const forumDeleteService = (id)=>{
    return request.delete('/admin/forum?id='+id)
}

//启用禁用学院分类
export const forumStartStopService = (id,status)=>{
    return request.post('/admin/forum/status?id=' + id + '&status=' + status) 
}