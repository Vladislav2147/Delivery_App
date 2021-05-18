<template>
    <v-container bg fill-height fluid>
        <v-row align-center justify="center">
            <v-card
                    class="pa-md-4 mx-lg-auto"
                    min-width="300"
                    elevation="2">
                <v-card-title primary-title>
                    <h4>Order #{{state.id}}</h4>
                </v-card-title>
                <v-card-text>
                    Address: {{state.address}}
                    <v-divider/>
                    Courier: {{state.courier}}
                    <v-divider/>
                    State: {{state.state}}
                    <v-divider/>
                </v-card-text>

            </v-card>
        </v-row>
    </v-container>
</template>

<script>
    export default {
        created() {
            this.$http.post(
                '/ws/orderstate',
                '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"\n' +
                '\t\t\t\t  xmlns:gs="https://github.com/Vladislav2147/Delivery_App">\n' +
                '   <soapenv:Header/>\n' +
                '   <soapenv:Body>\n' +
                '      <gs:getOrderStateRequest>\n' +
                '         <gs:id>' + this.$route.query.id + '</gs:id>\n' +
                '      </gs:getOrderStateRequest>\n' +
                '   </soapenv:Body>\n' +
                '</soapenv:Envelope>',
                {
                    headers: {
                        'Content-Type': 'text/xml',
                        'Accept': '*/*'
                    }
                }
            ).then(result => {
                if(result.ok) {
                    let xml = result.data;
                    let xmlDoc = (new DOMParser()).parseFromString(xml, "text/xml").documentElement;
                    let body = xmlDoc.getElementsByTagNameNS("http://schemas.xmlsoap.org/soap/envelope/",'Body')[0];
                    let gs = "https://github.com/Vladislav2147/Delivery_App";
                    this.state.id = body.getElementsByTagNameNS(gs, 'id')[0].textContent
                    this.state.address = body.getElementsByTagNameNS(gs, 'address')[0].textContent
                    this.state.courier = body.getElementsByTagNameNS(gs, 'courier')[0].textContent
                    this.state.state = body.getElementsByTagNameNS(gs, 'state')[0].textContent
                }
            })
        },
        data() {
            return {
                state: {
                    id: 0,
                    address: '',
                    courier: '',
                    state: ''
                }
            }
        }
    }
</script>

<style>

</style>