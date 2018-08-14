<template>
    <div id="sidebar">
        <Sider :style="{position: 'fixed', height: '100vh', left: 0, overflow: 'auto'}">
            <Menu :active-name="activeNavName" :open-names="openNames" width="200" ref="menus"
                  @on-select="goToPage">
                <Submenu name="account">
                    <template slot="title">
                        <Icon type="ios-person"/>
                        <span>账号管理</span>
                    </template>
                    <MenuItem name="account-basic">基本资料</MenuItem>
                    <MenuItem name="account-pwd">修改密码</MenuItem>
                </Submenu>
                <Submenu name="service">
                    <template slot="title">
                        <Icon type="ios-calendar" />
                        <span>我的服务</span>
                    </template>
                    <MenuItem name="service-apply">申请列表</MenuItem>
                    <MenuItem name="service-publish">发布服务</MenuItem>
                </Submenu>
            </Menu>
        </Sider>
    </div>
</template>
<script>
import {hasObject} from '@/libs/util';
import {isAdmin} from '@/libs/account';
export default {
    data () {
        return {
            activeNavName: '',
            openNames: [],
            admin: isAdmin()
        };
    },
    created () {
        this.routeWatch();
    },
    mounted () {
        this.$nextTick(() => {
            this.routeWatch();
        });
    },
    filters: {},
    methods: {
        routeWatch () {
            this.activeNavName = this.$route.name;
            this.openNames = [this.$route.matched[0].name];
        },
        goToPage (name) {
            this.$router.push({name});
        }
    },
    watch: {
        '$route': 'routeWatch'
    }
};
</script>
<style lang="scss">
    /*#sidebar {*/
        /*.ivu-layout-sider{*/
            /*width: 50px!important;*/
            /*min-width: 50px!important;*/
            /*max-width: 50px!important;*/
            /*flex: 0 0 50px!important;*/
            /*.ivu-menu-item{*/
                /*.ivu-icon{*/
                    /*margin-right: 0;*/
                    /*font-size: 20px;*/
                    /*& + span{*/
                        /*display: none;*/
                    /*}*/
                /*}*/
            /*}*/
            /*.ivu-menu-submenu-title{*/
                /*& > .ivu-icon{*/
                    /*margin-right: 0;*/
                    /*font-size: 20px;*/
                    /*& + span{*/
                        /*display: none;*/
                        /*& + i{*/
                            /*display: none;*/
                        /*}*/
                    /*}*/
                /*}*/
                /*& + .ivu-menu{*/
                    /*position: absolute;*/
                    /*width: 200px;*/
                    /*z-index: 1;*/
                /*}*/
            /*}*/
            /*.ivu-menu-vertical .ivu-menu-item, .ivu-menu-vertical .ivu-menu-submenu-title{*/
                /*padding: 14px 0px;*/
                /*text-align: center;*/
            /*}*/
        /*}*/
    /*}*/

    #page-admin {
        .page-body{
            // z-index: -1;
            // width: calc(100% - 50px)!important;
        }
    }
</style>
