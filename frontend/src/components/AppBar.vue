<template>
    <div>
        <v-app-bar color="#24292e" dense dark>

            <v-toolbar-title>User database </v-toolbar-title>

            <v-progress-circular size="25" v-if="isLoading" color="white" indeterminate></v-progress-circular>

            <v-spacer></v-spacer>

            <account-info @logout="logout" :credentials="credentials" v-if="isAuthenticated"></account-info>

        </v-app-bar>
    </div>
</template>

<script>
    import AccountInfo from "@/components/AccountInfo";
    export default {
        name: "AppBar",
        components: {AccountInfo},
        props:['isAuthenticated','credentials', 'isLoading'],
        methods:{
            logout:function () {
                this.$emit('logout');
            }
        },
    }
</script>

<style scoped>

</style>
