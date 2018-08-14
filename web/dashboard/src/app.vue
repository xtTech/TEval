<template>
    <div id="main">
        <router-view/>
    </div>
</template>
<script>
export default {
    data () {
        return {

        };
    },
    methods: {}
};
</script>
<style lang="scss">
    html, body {
        height: 100%;
    }
    .page-body{
        height: 100%;
        width: calc(100% - 200px);
        float: right;

        position: relative;
        margin: 0;
        padding: 0;
        box-sizing: border-box;
        .page-body-content{
            padding: 0 16px 16px;
        }
        &.hide-slidebar{
            width: 100%;
        }
    }
     .spin-icon-load {
         animation: ani-demo-spin 1s linear infinite;
     }
    .ivu-table-fixed{
        box-shadow: 3px 0px 5px -2px rgba(0, 0, 0, .08) !important;
    }
    .ivu-table-fixed-right{
        box-shadow: -3px 0px 5px -2px rgba(0, 0, 0, .08) !important;
    }
    @keyframes ani-demo-spin {
        from {
            transform: rotate(0deg);
        }
        50% {
            transform: rotate(180deg);
        }
        to {
            transform: rotate(360deg);
        }
    }
</style>
<style scoped lang="scss">
    #main {
        height: 100%;
        .content {
            height: 100%;
            width: calc(100% - 200px);
            float: right;

            position: relative;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        .header {
            height: 64px;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-self: center;
            line-height: 64px;
            background-color: #fff;
            box-shadow: 0 2px 6px 0 rgba(0, 0, 0, 0.05);
            .header__logo {
                height: 100%;
                .logo {
                    display: block;
                    height: 100%;
                    width: auto;
                }
            }
            .header-right {

            }
        }
    }
</style>
