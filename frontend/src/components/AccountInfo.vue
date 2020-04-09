<template>
    <div class="text-center">
        <v-menu v-model="menu" :close-on-content-click="false" offset-y>

            <template v-slot:activator="{on}">
                <v-avatar alt="You" size="37px"  v-on="on">

                    <img :src="credentials.principal.attributes.avatar_url">
                </v-avatar>
            </template>

            <v-card align="center">
                <v-list>
                    <v-list-item align="center">
                        <v-list-item-content>
                            <v-avatar align="center" size="90px">
                                <img alt="You" :src="credentials.principal.attributes.avatar_url"></v-avatar>
                        </v-list-item-content>
<!--                        <v-list-item-avatar size="90px"><img alt="You" :src="credentials.principal.attributes.avatar_url"></v-list-item-avatar>-->
                    </v-list-item>

                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title>Singed in as</v-list-item-title>
                            <v-list-item-subtitle>{{credentials.principal.attributes.login}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>

                </v-list>

                <v-divider></v-divider>

                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                            <v-list-item-title>Status</v-list-item-title>
                            <v-list-item-subtitle>{{accountStatus}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>

                <v-divider></v-divider>
                <v-list>
                    <v-list-item>
                        <v-list-item-content>
                        <v-btn dark color="red lighten-1" @click="logout">Sign out</v-btn>
                        </v-list-item-content>
                    </v-list-item>
                </v-list>
            </v-card>

        </v-menu>
    </div>
</template>

<script>
    export default {
        name: "AccountInfo",
        props:['credentials'],
        data:function () {
            return{
                menu:false,
            }
        },
        methods:{
            logout:function () {
                this.$emit('logout');
            }
        },
        computed:{
            accountStatus:function () {
                return this.credentials.authorities[0].authority==='ROLE_ADMIN'?'Administrator':'User';
            }
        }
    }
</script>

<style scoped>

</style>
