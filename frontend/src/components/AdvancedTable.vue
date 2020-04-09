<template>
        <v-data-table must-sort sort-by="creationDate" sort-desc :headers="headers" class="elevation-1"
                      :items="users" item-key="id" :search="search">

            <template v-slot:top>

                <v-toolbar flat color="white">

                    <v-text-field width="50" v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>

                    <v-spacer></v-spacer>

                    <user-adding-dialog @addUser="addUser($event)"></user-adding-dialog>

                </v-toolbar>

            </template>

            <template v-slot:item.actions="{ item }">

                    <user-editing-dialog :user="item" @editUser="editUser($event)"></user-editing-dialog>

<!--                <v-icon title="Edit" small class="mr-2" @click="editItem(item)">mdi-pencil</v-icon>-->
                    <user-deleting-dialog :user="item" @deleteUser="deleteUser($event)"></user-deleting-dialog>

            </template>

        </v-data-table>
</template>

<script>
    import UserAddingDialog from "@/components/UserAddingDialog";
    import UserEditingDialog from "@/components/UserEditingDialog";
    import UserDeletingDialog from "@/components/UserDeletingDialog";
    export default {
        name: "AdvancedTable",
        components: {UserDeletingDialog, UserEditingDialog, UserAddingDialog},
        props:['users'],
        data:function(){
            return{
                search:'',
                headers:[
                    {text:'Name', value:'name'},
                    {text:'Surname', value: 'surname'},
                    {text: 'Age', value: 'age'},
                    {text: 'Email', value: 'email' },
                    {text: 'Marital status', value:'maritalStatus'},
                    {text: 'Time created', value:'creationDate'},
                    {text: 'Time last edited', value:'timeLastEdited'},
                    {text: 'Actions', value: 'actions', sortable: false },
                ] ,

            }
        },
        methods:{
            addUser:function (event) {
                this.$emit('addUser',event);
            },
            deleteUser:function (item) {
                this.$emit('deleteUser',item);
            },
            editUser:function (item) {
                this.$emit('editUser',item);
            }
        }
    }

</script>

<style scoped>

</style>
