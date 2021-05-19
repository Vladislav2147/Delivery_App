<template>
    <v-app>
        <v-app-bar app color="primary" class="white--text">
            <v-toolbar-title style="cursor: pointer" @click="$router.push('/')">Delivery App</v-toolbar-title>
            <v-spacer></v-spacer>
            <router-link to="/" class="mx-4 white--text">Users</router-link>
            <router-link to="/orders" class="mx-4 white--text">Orders</router-link>
            <span v-if="$route.fullPath !== '/orders' && $route.fullPath !== '/'"/>
            <span v-else-if="!profile">
                You're not authorized!
                <v-btn icon @click="$router.push('login')">
                    <v-icon style="color: white">login</v-icon>
                </v-btn>
            </span>
            <span v-else>
                {{ profile.firstName + ' ' + profile.secondName}}
                <v-btn icon style="color: white" @click="logout()">
                    <v-icon>exit_to_app</v-icon>
                </v-btn>
            </span>

        </v-app-bar>
        <v-main>
            <router-view v-if="profile || this.$route.fullPath === '/login' || this.$route.fullPath === '/registration' || this.$route.fullPath === '/orderstate'"></router-view>
            <v-container v-else bg fill-height fluid>
                <v-row align-center justify="center">
                    <v-card
                            class="pa-md-4 mx-lg-auto"
                            elevation="2">
                        <v-card-title primary-title>
                            <h4>You are not authenticated!<br />
                                You should login to continue.</h4>
                        </v-card-title>
                        <v-btn
                                @click="$router.push('/login')"
                                color=""
                        >
                            Login
                        </v-btn>
                    </v-card>
                </v-row>
            </v-container>
        </v-main>
    </v-app>

</template>

<script>


    export default {
        created() {
            this.initProfile()
        },
        data() {
            return {
                profile: null,
            }
        },
        watch: {
            $route(to, from) {
                console.log("route changed")
                this.initProfile()
            }
        },
        methods: {
            logout() {
                this.$resource('/logout').get().then(result => {
                    if (result.ok) {
                        this.profile = null
                        this.$router.replace()
                    }
                })

            },
            initProfile() {
                this.$resource('/profile').get().then(result => {
                    if (result.ok) {
                        result.json().then(profileJSON => this.profile = profileJSON)
                    } else {
                        this.profile = null
                    }
                })
            }
        }

    }
</script>

<style>

</style>