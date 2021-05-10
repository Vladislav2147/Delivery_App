import Vue from 'vue'
import VueResource from 'vue-resource'
import App from 'pages/App.vue'

Array.prototype.removeIf = function(callback) {
    var i = this.length;
    while (i--) {
        if (callback(this[i], i)) {
            this.splice(i, 1);
        }
    }
};

Vue.use(VueResource)

new Vue({
    el: '#app',
    render: a => a(App)
})

const userApi = Vue.resource('/admin');
/*


Vue.component('message-row', {
    props: ['item'],
    template: '<div>{{ item.id }} {{ item.message }}</div>'
});

Vue.component('messages-list', {
    props: ['messages'],
    template: '<div><message-row v-for="item in messages" :key="item.id" :item="item"/></div>',
    created: function () {
        messageApi.get().then(result => {
            result.json().then(data => {
                data.forEach(courier => {
                    const message = {};
                    message.id = courier.id;
                    message.message = courier.firstName + " " + courier.secondName;
                    this.messages.push(message)
                })
            })
        })
    }
});

var app = new Vue({
    el: '#app',
    template: '<messages-list :messages="messages" />',
    data: {
        messages: []
    }
});*/
