import Vue from 'vue';
import iView from 'iview';
import VueRouter from 'vue-router';
import {updateSiteTitle} from './libs/util';
import Entry from './views/entry';
Vue.use(iView);
Vue.use(VueRouter);

const router = new VueRouter({
    base: '/',
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'home',
            meta: {
                title: ''
            },
            redirect: {
                name: 'account-basic'
            },
            component: Entry,
            children: [
                {
                    path: 'app',
                    name: 'switch-app',
                    meta: {
                        title: '选择应用'
                    },
                    component: (resolve) => require(['./views/home/home.vue'], resolve)
                }
            ]
        },
        {
            path: '/account',
            name: 'account',
            meta: {
                title: '账号管理'
            },
            redirect: {
                name: 'account-basic'
            },
            component: Entry,
            children: [
                {
                    path: 'basic',
                    name: 'account-basic',
                    meta: {
                        title: '基本资料'
                    },
                    component: (resolve) => require(['./views/account/basic.vue'], resolve)
                },
                {
                    path: 'pwd',
                    name: 'account-pwd',
                    meta: {
                        title: '修改密码'
                    },
                    component: (resolve) => require(['./views/account/pwd.vue'], resolve)
                }
            ]
        },
        {
            path: '/my-service',
            name: 'my-service',
            meta: {
                title: '我的服务'
            },
            redirect: {
                name: 'account-basic'
            },
            component: Entry,
            children: [
                {
                    path: 'apply',
                    name: 'service-apply',
                    meta: {
                        title: '申请列表'
                    },
                    component: (resolve) => require(['./views/service/apply.vue'], resolve)
                },
                {
                    path: 'publish',
                    name: 'service-publish',
                    meta: {
                        title: '发布服务'
                    },
                    component: (resolve) => require(['./views/service/publish.vue'], resolve)
                }
            ]
        }
    ]
});

router.beforeEach((to, from, next) => {
    console.log(to);
    // if (to.name !== 'login') {
    //     // 检查是否登录
    //     let loginData = localStorage.getItem('loginData');
    //     if (!loginData) {
    //         // 未登录
    //         router.push({
    //             name: 'login'
    //         });
    //         iView.Notice.error({
    //             title: '未登录',
    //             desc: '请先登录！'
    //         });
    //         return;
    //     }
    // }
    iView.LoadingBar.start();
    updateSiteTitle(to.meta.pageTitle != null ? to.meta.pageTitle : to.meta.title);
    next();
});

router.afterEach(() => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});

export default router;
