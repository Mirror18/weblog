
import Index from '@/pages/frontend/index.vue'

import {createRouter,createWebHashHistory}  from "vue-router";

//统一在这里声明所有路由
const routes = [
    {
        //路由地址
        path: '/',
        //对应组件
        component: Index,
        //meta 信息
        meta:{
            // 页面标题
            title: 'weblog 首页'
        }
    }
]

//创建路由
const router = createRouter({
    //指定路由的历史管理方式，hash模式是指URL的路径是通过hash符号进行标识
    history: createWebHashHistory(),
    //routes的缩写
    routes,
})
//es6模块到处语句，用于将router 对象导出，使其他文件可以导入和使用这个对象
export default router;