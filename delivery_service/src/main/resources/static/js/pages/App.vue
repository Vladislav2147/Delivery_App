<template>
    <v-app>
        <v-app-bar app>
            <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">Delivery App</v-toolbar-title>
            <v-spacer></v-spacer>
            <span v-if="!profile">
                You're not authorized!
                <v-btn icon @click="$router.push('login')">
                    <v-icon>login</v-icon>
                </v-btn>
            </span>
            <span v-else>
                {{ profile.firstName + ' ' + profile.secondName}}
                <v-btn icon href="/logout">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </span>

        </v-app-bar>
        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>

</template>

<script>

    export default {
        data() {
            return {
                profile: frontendData.profile,
            }
        }
    }
</script>

<style>

</style>