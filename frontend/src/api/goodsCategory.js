import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//学院列表查询
export const goodsCategoryListService = ()=>{
    return request.get('/admin/category')
}
//学院列表查询(用户端)
export const userGoodsCategoryListService = ()=>{
    return request.get('/user/category')
}

//学院分类添加
export const goodsCategoryAddService = (categoryData)=>{
    return request.post('/admin/category',categoryData)
}

//学院分类修改
export const goodsCategoryUpdateService = (categoryData)=>{
   return  request.put('/admin/category',categoryData)
}

//学院分类删除
export const goodsCategoryDeleteService = (id)=>{
    return request.delete('/admin/category?id='+id)
}

//启用禁用学院分类
export const goodsCategoryStartStopService = (id,status)=>{
    return request.post('/admin/category/status?id=' + id + '&status=' + status) 
}