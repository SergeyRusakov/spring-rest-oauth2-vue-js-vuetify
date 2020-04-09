<template>
    <v-dialog v-model="dialog" max-width="350px">

        <template v-slot:activator="{ on }">
            <v-icon title="delete" small class="mr-2" v-on="on">mdi-delete</v-icon>
        </template>

        <v-card>

            <v-card-title>
                <span class="headline">Delete {{user.name}} {{user.surname}}?</span>
            </v-card-title>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="deleteUser">Delete</v-btn>
            </v-card-actions>

        </v-card>

    </v-dialog>
</template>

<script>
    export default {
        name: "UserDeletingDialog",
        props:['user'],
        data:function () {
            return{
                dialog:false,
            }
        },
        methods:{
            deleteUser:function () {
                this.$emit('deleteUser',this.user);
            },
            close () {
                this.dialog = false;
            },
        },
        watch: {
            dialog (val) {
                val || this.close()
            },
        },

    }
</script>

<style scoped>

</style>
