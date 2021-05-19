import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'
import UserList from "./components/users/UserList";
import Login from "./components/login/Login";
import OrderList from "./components/orders/OrderList";
import Vuetify from "vuetify";
import VueRouter from 'vue-router';
import VueMask from 'v-mask'
import 'vuetify/dist/vuetify.min.css'

import Registration from "./components/login/Registration";
import OrderState from "./components/state/OrderState";
import ShopCart from "./components/shopcart/ShopCart";


Array.prototype.removeIf = function(callback) {
    var i = this.length;
    while (i--) {
        if (callback(this[i], i)) {
            this.splice(i, 1);
        }
    }
};


Vue.use(VueMask);
Vue.use(Vuetify)
Vue.use(VueResource)
Vue.use(VueRouter)

const router = new VueRouter({
    routes: [
        {
            path: '/',
            component: UserList
        },
        {
            path: '/orders',
            component: OrderList
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/registration',
            component: Registration
        },
        {
            path: '/orderstate',
            component: OrderState
        },
        {
            path: '/shopcart',
            component: ShopCart
        }
    ],
})

new Vue({
    router,
    vuetify: new Vuetify({
        icons: {
            iconfont: 'mdi', // 'mdi' || 'mdiSvg' || 'md' || 'fa' || 'fa4' || 'faSvg'
        },
        theme: {
            themes: {
                light: {
                    primary: '#1565C0',
                    secondary: '#b0bec5',
                    accent: '#0D47A1',
                    error: '#b71c1c',
                },
            },
        },
    }),
    el: '#app',
    render: a => a(App)
})

const userApi = Vue.resource('/admin');

