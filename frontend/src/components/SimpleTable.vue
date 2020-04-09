<template>
<v-data-table must-sort sort-by="creationDate" sort-desc :headers="headers" class="elevation-1"
              :items="users" item-key="id" :search="search">

    <template v-slot:top>

        <v-toolbar flat color="white">

            <v-text-field width="50" v-model="search" append-icon="mdi-magnify" label="Search" single-line hide-details></v-text-field>

        </v-toolbar>

    </template>

</v-data-table>
</template>

<script>
    export default {
        name: "SimpleTable",
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
                ] ,

            }
        },
        methods:{

        }
    }

</script>

<style scoped>

</style>
