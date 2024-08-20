//从vue框架导入createApp函数功能；
import { createApp } from 'vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'


import router from './router/router.js'


//导入css样式
//import './style.css'

//从一个单文件组件中导入根组件；
import App from './App.vue'

let app = createApp(App)
app.use(ElementPlus).use(router)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.mount('#app')



