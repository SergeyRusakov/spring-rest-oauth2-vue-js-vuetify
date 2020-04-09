<template>
    <v-dialog v-model="dialog" max-width="500px">

        <template v-slot:activator="{ on }">
            <v-btn color="#28a745" dark class="mb-2" v-on="on"><v-icon left>mdi-plus</v-icon> New user</v-btn>
        </template>

        <v-card>

            <v-card-title>
                    <span class="headline">New user</span>
            </v-card-title>

            <v-card-text>

                <v-form ref="form" v-model="valid" lazy-validation>

                    <v-text-field v-model="newUser.name" :counter="15"
                                  :rules="rules.nameRules" label="Name" required></v-text-field>

                    <v-text-field v-model="newUser.surname" :counter="15"
                                  :rules="rules.nameRules" label="Surname" required></v-text-field>

                    <v-text-field v-model="newUser.email"
                                  :rules="rules.emailRules" label="E-mail" required></v-text-field>

                    <birth-date-picker @saveDate="saveDate($event)" :date="newUser.birthDate"></birth-date-picker>

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
        components: {BirthDatePicker},
        data: () => ({
            dialog: false,
            valid:true,
            newUser:{
                name:null,
                surname:null,
                birthDate:null,
                email:null,
                isMarried:null,
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
                this.newUser.birthDate=null;
                this.newUser.isMarried=null;
                this.reset();

            },
            save () {
                if(this.valid && this.newUser.birthDate!=null){

                    console.log('Valid');
                    let sendingUser = Object.assign({},this.newUser);
                    this.newUser.isMarried==='true'?sendingUser.isMarried=true:sendingUser.isMarried=false;
                    sendingUser.name=this.processName(sendingUser.name);
                    sendingUser.surname=this.processName(sendingUser.surname);
                    console.log(sendingUser);
                    this.$emit('addUser',sendingUser);
                    this.close();
                }
                else{
                    console.log('Not valid');
                }
            },
            reset () {
                this.$refs.form.reset()
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
            }
        },
    }
</script>

<style scoped>

</style>
