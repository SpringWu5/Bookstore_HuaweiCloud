import './assets/main.scss'

import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import locale from 'element-plus/dist/locale/zh-cn.js'
import App from './App.vue'
import router from '@/router';

import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'

const app = createApp(App)
const pinia = createPinia()
const persist=createPersistedState()

app.use(router)
app.use(ElementPlus,{locale})
app.mount('#app')
app.use(pinia)

pinia.use(persist)