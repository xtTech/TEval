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
            component: Entry
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
                    path: 'account-basic',
                    name: 'account-basic',
                    meta: {
                        title: '基本资料'
                    },
                    component: (resolve) => require(['./views/account/basic.vue'], resolve)
                },
                {
                    path: 'account-pwd',
                    name: 'account-pwd',
                    meta: {
                        title: '修改密码'
                    },
                    component: (resolve) => require(['./views/account/pwd.vue'], resolve)
                }
            ]
        },
        {
            path: '/service',
            name: 'service',
            meta: {
                title: '我的服务'
            },
            redirect: {
                name: 'service-apply'
            },
            component: Entry,
            children: [
                {
                    path: 'service-apply',
                    name: 'service-apply',
                    meta: {
                        title: '申请列表'
                    },
                    component: (resolve) => require(['./views/service/apply.vue'], resolve)
                },
                {
                    path: 'service-publish',
                    name: 'service-publish',
                    meta: {
                        title: '发布服务'
                    },
                    component: (resolve) => require(['./views/service/publish.vue'], resolve)
                },
                {
                    path: 'service-users',
                    name: 'service-users',
                    meta: {
                        title: '使用列表'
                    },
                    component: (resolve) => require(['./views/service/users.vue'], resolve)
                },
                {
                    path: 'service-create',
                    name: 'service-create',
                    meta: {
                        title: '创建服务'
                    },
                    component: (resolve) => require(['./views/service/create.vue'], resolve)
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
