<template>
    <v-data-table
            :headers="headers"
            :items="orders">

        <template v-slot:[`item.name`]="{ item }">
            {{ item.customer.firstName + ' ' + item.customer.secondName }}
        </template>

        <template v-slot:[`item.totalPrice`]="{ item }">
            <b>{{ orderPrice(item) }}</b>
        </template>

        <template v-slot:[`item.customOrdered`]="{ item }">
            <product-list :ordered="item.ordered"></product-list>
        </template>

        <template v-slot:[`item.cancel`]="{ item }">
            <v-btn x-small @click="setState(item, 'Canceled')">
                Set Cancelled
            </v-btn>
        </template>

        <template v-slot:[`item.ordered`]="{ item }">
            <v-btn x-small @click="setState(item, 'Ordered')">
                Set Ordered
            </v-btn>
        </template>

    </v-data-table>
</template>

<script>

    import ProductList from "./ProductList";

    export default {
        components: {ProductList},
        created() {
            this.$resource("/order").get().then(result => {
                result.json().then(ordersJSON => ordersJSON.forEach(order => this.orders.push(order)))
            })
        },
        methods: {
            setState(order, newState) {
                this.$http.put(
                    '/order/updateState/?id=' + order.id + '&state=' + newState
                ).then(result => {
                    if (result.ok) {
                        order.state = newState
                        if (newState == 'Canceled') {
                            order.courierId = null
                        }
                    }
                })
            },
            orderPrice(order) {
                let sum = 0.0;
                order.ordered.forEach(ordered => sum += ordered.amount * ordered.product.price)
                return sum
            }
        },
        data() {
            return {
                headers:  [
                    { text: 'ID', value: 'id' },
                    { text: 'Products', value: 'customOrdered' },
                    { text: 'Address', value: 'address' },
                    { text: 'Info', value: 'info' },
                    { text: 'Phone', value: 'customer.phone' },
                    { text: 'Customer name', value: 'name' },
                    { text: 'Courier ID', value: 'courierId' },
                    { text: 'Total price', value: 'totalPrice' },
                    { text: 'State', value: 'state' },
                    { text: 'Cancel', value: 'cancel' },
                    { text: 'Order', value: 'ordered' },
                ],
                orders: []
            }
        },

    }
</script>

<style>

</style>