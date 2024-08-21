import {createRouter, createWebHistory} from 'vue-router'
import LoginPage from "@/pages/LoginPage.vue";
import CoursePage from "@/pages/CoursePage.vue";

const routes = [
    {path: '/', component: LoginPage},
    {path: '/courses', component: CoursePage},
]

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;