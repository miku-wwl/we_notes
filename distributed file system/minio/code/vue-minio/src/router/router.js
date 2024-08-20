//创建路由对象，并切配置好路由，然后把路由对象导出
import { createRouter, createWebHistory } from "vue-router";

//创建router路由对象
const router = createRouter({
    //路由历史
    history: createWebHistory(),

    //配置路由（可以配置多个路由地址，所以是一个数组）
    routes: [
        {
            //路由路径
            path: '/',
            //访问该路由路径时，渲染哪个vue页面
            component : () => import('../view/UserView.vue'),
        },
        {
            //动态路由，id是变量
            path:'/edit/:id',
            component : () => import('../view/UserEditView.vue'),
        }
    ]
})
//导出router路由对象
export default router;