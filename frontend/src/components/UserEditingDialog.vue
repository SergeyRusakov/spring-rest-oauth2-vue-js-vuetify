<template>
    <v-dialog v-model="dialog" max-width="500px">

        <template v-slot:activator="{ on }">
            <v-icon @click="setData" title="Edit" small class="mr-2" v-on="on">mdi-pencil</v-icon>
        </template>

        <v-card>

            <v-card-title>
                <span class="headline">Edit user</span>
            </v-card-title>

            <v-card-text>

                <v-form ref="editForm" v-model="valid" lazy-validation>

                    <v-text-field v-model="newUser.name" :counter="15"
                                  :rules="rules.nameRules" label="Name" required></v-text-field>

                    <v-text-field v-model="newUser.surname" :counter="15"
                                  :rules="rules.nameRules" label="Surname" required></v-text-field>

                    <v-text-field v-model="newUser.email"
                                  :rules="rules.emailRules" label="E-mail" required></v-text-field>

                    <birth-date-picker :date-prop="this.newUser.birthDate" @saveDate="saveDate($event)" ></birth-date-picker>

                    <v-radio-group v-model="newUser.isMarried" row mandatory>
                        <v-radio label="Married" value="true"></v-radio>
                        <v-radio label="Single" value="false"></v-radio>
                    </v-radio-group>

                </v-form>

            </v-card-text>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close">Cancel</v-btn>
                <v-btn color="blue darken-1" text @click="save">Save</v-btn>
            </v-card-actions>

        </v-card>

    </v-dialog>
</template>

<script>
    import BirthDatePicker from "@/components/BirthDatePicker";
    export default {
        name: "UserEditingDialog",
        props:['user'],
        components: {BirthDatePicker},
        data: () => ({
            dialog: false,
            valid:true,
            newUser:{
            },
            rules:{
                nameRules:[
                    v => !!v || 'Name is required',
                    v => (v && v.length <= 15) || 'Name must be less than 15 characters',
                    v => /^[a-zA-Zа-яА-Я-'/. ]+$/.test(v) || 'Name must contain only letters',
                ],
                emailRules:[
                    v => !!v || 'E-mail is required',
                    v => /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(v) || 'E-mail must be valid',
                ],
                surnameRules:[
                    v => !!v || 'Surname is required',
                    v => (v && v.length <= 15) || 'Surname must be less than 15 characters',
                    v => /^[a-zA-Zа-яА-Я-'/. ]+$/.test(v) || 'Surname must contain only letters',
                ]
            }
        }),

        watch: {
            dialog (val) {
                val || this.close()
            },
        },

        methods: {
            close () {
                this.dialog = false;
            },
            save () {
                if(this.valid && this.newUser.birthDate!=null){
                    let sendingUser = Object.assign({},this.newUser);
                    this.newUser.isMarried==='true'?sendingUser.isMarried=true:sendingUser.isMarried=false;
                    sendingUser.name=this.processName(sendingUser.name);
                    sendingUser.surname=this.processName(sendingUser.surname);
                    sendingUser.married=sendingUser.isMarried;
                    console.log(sendingUser);
                    this.$emit('editUser',sendingUser);
                    this.close();
                }
                else{
                    console.log('Not valid');
                }

            },

            saveDate:function (date) {
                console.log(date);
                this.newUser.birthDate=date;
            },
            processName:function (name) {
                let correctName=name.charAt(0).toUpperCase();
                if(name.length>1){
                    correctName = correctName+name.substring(1,name.length).toLowerCase();
                }
                return correctName;
            },
            setData:function () {
                this.newUser=Object.assign({},this.user);
                this.newUser.isMarried=this.user.isMarried?"true":"false";
            }
        },
    }
</script>

<style scoped>

</style>
