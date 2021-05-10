<template>
    <table>
        <user-row v-for="user in users"
                  :key="user.id"
                  :user="user" />
    </table>
</template>

<script>
    import UserRow from "./UserRow.vue";

    export default {
        components: {UserRow},
        props: ['users'],
        created() {
            this.$resource("/admin").get().then(result => {
                result.json().then(usersJSON => usersJSON.forEach(user => this.users.push(user)))
            })
        },
    }
</script>

<style>

</style>