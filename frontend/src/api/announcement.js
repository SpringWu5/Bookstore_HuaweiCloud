import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//学院列表查询
export const announcementListService = ()=>{
    return request.get('/admin/announcement')
}

//学院分类添加
export const announcementAddService = (categoryData)=>{
    return request.post('/admin/announcement',categoryData)
}

//学院分类修改
export const announcementUpdateService = (categoryData)=>{
   return  request.put('/admin/announcement',categoryData)
}

//学院分类删除
export const announcementDeleteService = (id)=>{
    return request.delete('/admin/announcement?id='+id)
}

//启用禁用学院分类
export const announcementStartStopService = (id,status)=>{
    return request.post('/admin/announcement/status?id=' + id + '&status=' + status) 
}

//学院列表查询(用户端)
export const userAnnouncementListService = ()=>{
    return request.get('/user/announcement')
}
