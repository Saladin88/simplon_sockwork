import { createRouter, createWebHistory } from 'vue-router'
import ErrorViews from '../views/404ErrorView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/:catchAll(.*)', // ou aussi /:pathMatch(.*) 
      name: 'notFound',
      component: () => import('../views/404ErrorView.vue'),
    },
    {
      path: '/',
      alias: [
        '/home',
        '/about',
        '/creer-compte'
      ],
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AccountCreateView.vue'),
    },
    {
      path:'/se-connecter',
      name:'connexion',
      component: () => import('../views/LogInView.vue')
    },
  ],
})

export default router
