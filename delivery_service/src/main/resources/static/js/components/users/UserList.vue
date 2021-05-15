<template>
    <v-data-table
            :headers="headers"
            :items="users">
        <template v-slot:[`item.role`]="{ item }">{{ item.roles.map(role => role.name).join(", ") }}</template>
        <template v-slot:[`item.courier`]="{ item }">
            <v-btn x-small @click="verify(item, { id: 1, name: 'ROLE_COURIER'})">
                Grant Courier
            </v-btn>
            <v-btn x-small @click="unverify(item, { id: 1, name: 'ROLE_COURIER'})">
                Revoke Courier
            </v-btn>
        </template>
        <template v-slot:[`item.admin`]="{ item }">
            <v-btn x-small @click="verify(item, { id: 2, name: 'ROLE_ADMIN'})">
                Grant Admin
            </v-btn>
            <v-btn x-small @click="unverify(item, { id: 2, name: 'ROLE_ADMIN'})">
                Revoke Admin
            </v-btn>
        </template>
    </v-data-table>
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
            }
        },
        data() {
            return {
                headers:  [
                    {
                        text: 'ID',
                        value: 'id',
                    },
                    { text: 'First Name', value: 'firstName' },
                    { text: 'Second Name', value: 'secondName' },
                    { text: 'Phone', value: 'phone' },
                    { text: 'Email', value: 'email' },
                    { text: 'Roles', value: 'role' },
                    { text: '', value: 'courier' },
                    { text: '', value: 'admin' },
                ],
                users: []
            }
        },
    }
</script>

<style>

</style>