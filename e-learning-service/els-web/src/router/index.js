import {createRouter, createWebHistory} from 'vue-router'
import LoginPage from "@/pages/LoginPage.vue";

const routes = [
    {path: '/', component: LoginPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;