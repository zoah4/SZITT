import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Register from '../views/Register.vue'
import Profil from '../views/Profil.vue'
import Instrukcije from '../views/Instrukcije.vue'
import ProfilPolaznik from '../views/ProfilPolaznik.vue'

const routes = [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/profil', component: Profil },
    { path: '/profilPolaznik', component: ProfilPolaznik },
    { path: '/instrukcije', component: Instrukcije }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
