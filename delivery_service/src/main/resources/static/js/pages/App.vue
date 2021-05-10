<template>
    <div>
        <div v-if="!profile">
            You should authorize <a href="/login">Login</a>
        </div>
        <div v-else>
            <div>{{ profile.firstName }}<a href="/logout">Logout</a></div>
        </div>
        <users-list :users="users"></users-list>
    </div>

</template>

<script>
    import UsersList from "../components/users/UserList.vue"

    export default {
        components: {UsersList},
        data() {
            return {
                profile: frontendData.profile,
                users: []
            }
        }
    }
</script>

<style>

</style>