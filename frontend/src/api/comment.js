import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'
//评论列表查询
export const commentListService = (foreignName,foreignId)=>{
    return request.get('/user/comment?foreignName=' + foreignName + '&foreignId=' + foreignId) 
}

//评论添加
export const commentAddService = (commentModel)=>{
    return request.post('/user/comment',commentModel)
}