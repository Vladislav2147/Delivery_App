<template>
    <div>
        <v-data-table
                :headers="headers"
                :items="users"
                @click:row="getStats"
        >
            <template v-slot:[`item.role`]="{ item }">{{ item.roles.map(role => role.name).join(", ") }}</template>
            <template v-slot:[`item.courier`]="{ item }">
                <v-btn x-small @click.stop="verify(item, { id: 1, name: 'ROLE_COURIER'})">
                    Grant Courier
                </v-btn>
                <v-btn x-small @click.stop="unverify(item, { id: 1, name: 'ROLE_COURIER'})">
                    Revoke Courier
                </v-btn>
            </template>
            <template v-slot:[`item.admin`]="{ item }">
                <v-btn x-small @click.stop="verify(item, { id: 2, name: 'ROLE_ADMIN'})">
                    Grant Admin
                </v-btn>
                <v-btn x-small @click.stop="unverify(item, { id: 2, name: 'ROLE_ADMIN'})">
                    Revoke Admin
                </v-btn>
            </template>
        </v-data-table>
        <v-dialog
                v-model="dialog"
                max-width="290"
        >
            <v-card>
                <v-card-title class="headline">
                    Stats for user #{{currentUserStats.courierId}}
                </v-card-title>

                <v-card-text>
                    Delivered orders: {{currentUserStats.deliveredOrdersCount}}
                    <v-divider/>
                    Delivered orders in time: {{currentUserStats.deliveredInTimeCount}}
                    <v-divider/>
                    Total delivered price: {{currentUserStats.deliveredTotalPrice.toFixed(2)}}
                    <v-divider/>
                    Delivered products: {{currentUserStats.deliveredProductsCount}}
                    <v-divider/>
                </v-card-text>

                <v-card-actions>
                    <v-spacer></v-spacer>

                    <v-btn
                            color="green darken-1"
                            text
                            @click="dialog = false"
                    >
                        Ok
                    </v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </div>
</template>

<script>

    export default {
        created() {
            this.$resource("/admin").get().then(result => {
                result.json().then(usersJSON => usersJSON.forEach(user => this.users.push(user)))
            })
        },
        methods: {
            verify(user, role) {
                this.$resource('/admin/grant/' + user.id, {
                    role: role.name
                }).get().then(result => {
                    if (result.ok && !user.roles.find(currentRole => currentRole.name === role.name)) {
                        user.roles.push(role)
                    }
                })
            },
            unverify(user, role) {
                this.$resource('/admin/revoke/' + user.id, {
                    role: role.name
                }).get().then(result => {
                    if (result.ok) {
                        user.roles.removeIf(currentRole => currentRole.name === role.name)
                    }
                })
            },
            getStats(user) {
                this.$resource('/profile/stats/' + user.id).get().then(result => {
                    if (result.ok) {
                        result.json().then(userStatsJSON => this.currentUserStats = userStatsJSON)
                        this.dialog = true;
                    }
                })


            }
        },
        data() {
            return {
                headers:  [
                    { text: 'ID', value: 'id' },
                    { text: 'First Name', value: 'firstName' },
                    { text: 'Second Name', value: 'secondName' },
                    { text: 'Phone', value: 'phone' },
                    { text: 'Email', value: 'email' },
                    { text: 'Roles', value: 'role' },
                    { text: '', value: 'courier' },
                    { text: '', value: 'admin' },
                ],
                users: [],
                dialog: false,
                currentUserStats: {
                    courierId: 0,
                    deliveredOrdersCount: 0,
                    deliveredInTimeCount: 0,
                    deliveredTotalPrice: 0.0,
                    deliveredProductsCount: 0
                },
            }
        },

    }
</script>

<style>

</style>