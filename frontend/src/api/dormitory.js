import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//宿舍列表查询
export const dormitoryListService = ()=>{
    return request.get('/admin/dormitory')
}

//宿舍分类添加
export const dormitoryAddService = (categoryData)=>{
    return request.post('/admin/dormitory',categoryData)
}

//宿舍分类修改
export const dormitoryUpdateService = (categoryData)=>{
   return  request.put('/admin/dormitory',categoryData)
}

//宿舍分类删除
export const dormitoryDeleteService = (id)=>{
    return request.delete('/admin/dormitory?id='+id)
}

//启用禁用宿舍分类
export const dormitoryStartStopService = (id,status)=>{
    return request.post('/admin/dormitory/status?id=' + id + '&status=' + status) 
}