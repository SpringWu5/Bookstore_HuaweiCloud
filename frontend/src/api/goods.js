import request from '@/utils/request.js'
import { useTokenStore } from '@/stores/token.js'

//商品列表查询
export const goodsListService = (params)=>{
   return  request.get('/admin/goods/page',{params:params})
}

 //商品添加
export const goodsAddService = (userData)=>{
    return request.post('/admin/goods',userData);  
}

//商品修改
export const goodsUpdateService = (userData)=>{
    return  request.put('/admin/goods',userData)
 } 

//商品删除
export const goodsDeleteService = (id) => {
    return request.delete(`/admin/goods/${id}`, { params: { id: id } });
}

//启用禁用商品
export const userStartStopService = (id,status)=>{
    return request.post('/admin/goods/status?id=' + id + '&status=' + status) 
}

//商品列表查询(用户)
export const userGoodsListService = (params)=>{
    return  request.get('/user/goods/page',{params:params})
 }
 
 //根据id查询商品详情
export const goodsDescriptionService = (id) => {
    return request.get(`/user/goods/${id}`, { params: { id: id } });
}

//用户点赞
export const userUpdatePraiseTotal = (id,praiseTemp)=>{
    return request.post('/user/goods/praise?id=' + id + '&praiseTemp=' + praiseTemp) 
}

//用户收藏
export const userUpdateCollectionTotal = (id,collectionTemp)=>{
    return request.post('/user/goods/collection?id=' + id + '&collectionTemp=' + collectionTemp) 
}

//用户收藏查询
export const goodsCollectionListService = (params)=>{
    return  request.get('/user/goods/collection/page',{params:params})
 }

 //我的商品查询(用户)
export const userMyGoodsListService = (params)=>{
    return  request.get('/user/goods/myGoods/page',{params:params})
 }