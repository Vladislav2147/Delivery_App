<template>
    <v-container bg fill-height fluid>
        <v-row align-center justify="center">
            <v-card
                    class="pa-md-4 mx-lg-auto"
                    min-width="300"
                    elevation="2">
                <v-card-title primary-title>
                    <h4>Login</h4>
                </v-card-title>
                <v-form
                        ref="form"
                        v-model="valid"
                        lazy-validation >
                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="password"
                            :rules="passwordRules"
                            label="Password"
                            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="show1 ? 'text' : 'password'"
                            name="input-10-1"
                            counter
                            @click:append="show1 = !show1"
                            required
                    ></v-text-field>

                    <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="validate"
                    >
                        Sign In
                    </v-btn>

                    <v-btn
                            class="mr-4"
                            @click="$router.push('registration')"
                    >
                        Sign Up
                    </v-btn>

                </v-form>
            </v-card>
        </v-row>
    </v-container>
</template>

<script>
    export default {
        data: () => ({
            show1: false,
            valid: true,
            password: '',
            passwordRules: [
                v => !!v || 'Password is required',
                v => (v && v.length >= 4) || 'Password must be not less than 4 characters',
            ],
            email: '',
            emailRules: [
                v => !!v || 'E-mail is required',
                v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
            ],
            select: null,
        }),
        methods: {
            validate () {
                this.$refs.form.validate()

                var formData = new FormData();
                formData.append('username', this.email);
                formData.append('password', this.password);
                formData.append('remember-me', "true");

                this.$http.post('/login', formData).then(response => {
                    if (response.ok) {

                        response.json().then(profileJSON => {
                            if (!profileJSON.roles.find(role => role.name === "ROLE_ADMIN")) {
                                alert("This app only for admins!")
                                this.$resource('/logout').get()
                            } else {
                                this.$router.push('/')
                            }

                        })

                    }
                })
            },
        },
    }
</script>

<style>

</style>