import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//学院列表查询
export const collegeListService = ()=>{
    return request.get('/admin/college')
}

//学院分类添加
export const collegeAddService = (categoryData)=>{
    return request.post('/admin/college',categoryData)
}

//学院分类修改
export const collegeUpdateService = (categoryData)=>{
   return  request.put('/admin/college',categoryData)
}

//学院分类删除
export const collegeDeleteService = (id)=>{
    return request.delete('/admin/college?id='+id)
}

//启用禁用学院分类
export const collegeStartStopService = (id,status)=>{
    return request.post('/admin/college/status?id=' + id + '&status=' + status) 
}