<template>
    <v-container bg fill-height fluid>
        <v-row align-center justify="center">
            <v-card
                    class="pa-md-4 mx-lg-auto"
                    min-width="300"
                    elevation="2">
                <v-card-title primary-title>
                    <h4>Registration</h4>
                </v-card-title>
                <v-form
                        ref="form"
                        v-model="valid"
                        lazy-validation >
                    <v-text-field
                            v-model="firstName"
                            :rules="nameRules"
                            label="First Name"
                            required
                    ></v-text-field>
                    <v-text-field
                            v-model="secondName"
                            :rules="nameRules"
                            label="Second Name"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="email"
                            :rules="emailRules"
                            label="E-mail"
                            required
                    ></v-text-field>

                    <v-text-field
                            v-model="phone"
                            :rules="phoneRules"
                            label="Phone"
                            v-mask="'+375 (##) ###-##-##'"
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

                    <v-text-field
                            v-model="confirmPassword"
                            :rules="passwordRules"
                            label="Confirm Password"
                            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                            :type="show2 ? 'text' : 'password'"
                            name="input-10-2"
                            counter
                            @click:append="show2 = !show2"
                            required
                    ></v-text-field>

                    <v-btn
                            :disabled="!valid"
                            color="success"
                            class="mr-4"
                            @click="validate"
                    >
                        Registrate
                    </v-btn>

                </v-form>
            </v-card>
        </v-row>
        <v-snackbar
                v-model="snackbarSuccess"
        >
            Registration Finished Successfully!
            <template v-slot:action="{ attrs }">
                <v-btn
                        color="blue"
                        text
                        v-bind="attrs"
                        @click="successClicked"
                >
                    Go Back
                </v-btn>
            </template>
        </v-snackbar>
        <v-snackbar
                v-model="snackbarFail"
        >
            {{ failMessage }}
            <template v-slot:action="{ attrs }">
                <v-btn
                        color="blue"
                        text
                        v-bind="attrs"
                        @click="snackbarFail = false"
                >
                    Close
                </v-btn>
            </template>
        </v-snackbar>
    </v-container>
</template>

<script>
    export default {
        data: () => ({
            show1: false,
            show2: false,
            snackbarSuccess: false,
            snackbarFail: false,
            failMessage: 'Registration Failed!',

            valid: true,
            firstName: '',
            secondName: '',

            nameRules: [
                v => !!v || 'Name is Required',
                v => v.length >= 2 && v.length <= 50 || 'Name must have length between 1 and 50',
            ],

            phone: '',
            phoneRules: [
                v => !!v || 'Phone is Required',
                v => /^\+375 \((17|25|29|33|44)\) [0-9]{3}-[0-9]{2}-[0-9]{2}$/.test(v) || 'Phone should be valid'
            ],

            password: '',
            confirmPassword: '',
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


                this.$http.post('/registration', {
                    firstName: this.firstName,
                    secondName: this.secondName,
                    email: this.email,
                    phone: this.phone,
                    password: this.password,
                    confirmPassword: this.confirmPassword
                }).then(response => {
                    if (response.ok) {
                        this.snackbarSuccess = true
                    }
                }).catch(error => {
                    this.failMessage = 'Failed To registrate. Invalid data'
                    this.snackbarFail = true

                })
            },
            successClicked() {
                this.snackbarSuccess = false;
                this.$router.push('login')
            }
        },
    }
</script>

<style>

</style>