<template>
    <v-container>
    <v-card elevation="10" class="mx-auto" max-width="344" max-height="344">

        <v-card-title>Please, sign in</v-card-title>

        <v-card-text>With your GitHub account</v-card-text>

        <v-card-actions>
            <v-btn dark color="#24292e" block @click="loginWithGitHub"><v-icon right>fab fa-github</v-icon>Sign in with GitHub</v-btn>
        </v-card-actions>

        <v-card-actions>
            <v-btn block dark color="red lighten-1" @click="exit">Exit</v-btn>
        </v-card-actions>

    </v-card>
    </v-container>
</template>

<script>
    export default {
        name: "LoginComponent",
        methods:{
            loginWithGitHub:function () {
                this.$emit('loginWithGitHub');
            },
            exit:function () {
                window.location.href="https://www.google.com/";
            }
        }
    }
</script>

<style scoped>

</style>
