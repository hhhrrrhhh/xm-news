import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    redirect: '/front/home',  // 默认重定向到新闻首页
  },
  {
    path: '/login',
    name: 'Login',
    meta: { name: '登录' },
    component: () => import('../views/Login.vue')  // 登录页面
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/front/Home.vue') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person.vue') },
      { path: 'newsDetail', name: 'FNewsDetail', component: () => import('../views/front/NewsDetail.vue') },
      { path: 'provinceNews', name: 'ProvinceNews', component: () => import('../views/front/ProvinceNews.vue') },
    ]
  },
  {
    path: '/manager',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403.vue') },
      { path: 'home', name: 'ManagerHome', meta: { name: '系统首页' }, component: () => import('../views/manager/Home.vue') }, // 改名为ManagerHome
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin.vue') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson.vue') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password.vue') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice.vue') },
      { path: 'provinces', name: 'Provinces', meta: { name: '省份信息' }, component: () => import('../views/manager/Provinces.vue') },
      { path: 'news', name: 'News', meta: { name: '新闻公告' }, component: () => import('../views/manager/News.vue') },
    ]
  },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  console.log("当前路径: ", to.path);

  if (to.path === '/') {
    next('/front/home');
  } else if (to.path.startsWith('/front')) {
    next();
  } else if (to.path.startsWith('/manager')) {
    const admin = JSON.parse(localStorage.getItem("xm-admin") || '{}');
    if (admin.role === 'ADMIN') {
      next();
    } else {
      next('/login');
    }
  } else if (to.path === '/login') {
    next();
  } else {
    next('/login');
  }
})

export default router;
