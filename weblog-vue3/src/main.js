import {createApp} from 'vue' // 引入 createApp 方法
import App from './App.vue'     // 引入 App.vue 组件

// 引入 main.css 样式文件
import './assets/main.css'

import router from "@/router/index.js";


// 创建应用，并将 App 根组件挂载到 <div id="#app"></div> 中
// createApp(App).mount('#app')

const app = createApp(App)

app.use(router)
app.mount('#app')
