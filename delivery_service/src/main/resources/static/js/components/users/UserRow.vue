<template>
    <tr>
        <td>{{user.id}}</td>
        <td>{{user.firstName}} {{user.secondName}}</td>
        <td>{{user.email}}</td>
        <td>{{user.phone}}</td>
        <td>{{user.roles.find(role => role.name === 'ROLE_COURIER') ? "Verified" : "Unverified"}}</td>
        <td><input type="button" value="Verify" @click="verify(user)"></td>
        <td><input type="button" value="Unverify" @click="unverify(user)"></td>
    </tr>
</template>

<script>
    const courierRole = {
        id: 1,
        name: 'ROLE_COURIER'
    }
    export default {
        props: ['user'],
        methods: {
            verify(user) {
                this.$resource('/admin/grant/' + user.id).get().then(result => {
                    if (result.ok && !user.roles.find(role => role.name === 'ROLE_COURIER')) {
                        user.roles.push(courierRole)
                    }
                })
            },
            unverify(user) {
                this.$resource('/admin/revoke/' + user.id).get().then(result => {
                    if (result.ok) {
                        user.roles.removeIf(role => role.name === courierRole.name)
                    }
                })
            }
        }
    }
</script>

<style>

</style>