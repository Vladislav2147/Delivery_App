<template>
    <v-simple-table>
        <template v-slot:default>
            <thead>
            <tr>
                <th class="text-left">
                    ID
                </th>
                <th class="text-left">
                    Amount
                </th>
                <th class="text-left">
                    Name
                </th>
                <th class="text-left">
                    Weight
                </th>
                <th class="text-left">
                    Price
                </th>
            </tr>
            </thead>
            <tbody>
            <tr
                    v-for="item in ordered"
                    :key="item.id"
            >
                <td>{{ item.product.id }}</td>
                <td>{{ item.amount }}</td>
                <td>{{ item.product.name }}</td>
                <td>{{ item.product.weight }}</td>
                <td>{{ item.product.price }}</td>
            </tr>
            </tbody>
        </template>
    </v-simple-table>
</template>

<script>

    export default {
        props: ['ordered']

    }
</script>

<style>

</style>