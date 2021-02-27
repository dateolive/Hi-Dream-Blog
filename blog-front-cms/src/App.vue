<template>
    <div id="app">
        <router-view v-if="isRouterAlive"></router-view>
    </div>
</template>
<script>
export default {
    name: 'App',
    provide() {
        //父组件中通过provide来提供变量，在子组件中通过inject来注入变量。
        return {
            reload: this.reload
        };
    },
    data() {
        return {
            isRouterAlive: true //控制视图是否显示的变量
        };
    },
    methods: {
        reload() {
            this.isRouterAlive = false; //先关闭，
            this.$nextTick(function() {
                this.isRouterAlive = true; //再打开
            });
        }
    }
};
</script>
<style>
@import './assets/css/main.css';
@import './assets/css/color-dark.css'; /*深色主题*/
/*@import "./assets/css/theme-green/color-green.css";   浅绿色主题*/
</style>