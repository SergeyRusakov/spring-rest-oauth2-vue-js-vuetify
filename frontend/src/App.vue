<template>
  <v-app>

      <app-bar :is-loading="isLoading" :credentials="principalData" :is-authenticated="isAuthenticated" @logout="logout"></app-bar>

      <v-content v-if="!isLoading">

          <v-container v-if="isAuthenticated" >

              <advanced-table v-if="principalData.authorities[0].authority==='ROLE_ADMIN'" @deleteUser="deleteUser($event)"
                              @editUser="editUser($event)"
                              @addUser="addUser($event)" :users="users"></advanced-table>

              <simple-table v-else :users="users"></simple-table>

          </v-container>

          <login-component v-else-if="!isAuthenticated" @loginWithGitHub="loginWithGitHub" ></login-component>

          <v-snackbar
                  v-model="snackbar"
                  :timeout="timeout"
          >
              {{ snackbarText }}
              <v-btn
                      color="blue"
                      text
                      @click="snackbar = false"
              >
                  Close
              </v-btn>
          </v-snackbar>

      </v-content>

  </v-app>
</template>

<script>

  import axios from 'axios';
  import AppBar from "@/components/AppBar";
  import AdvancedTable from "@/components/AdvancedTable";
  import LoginComponent from "@/components/LoginComponent";
  import SimpleTable from "@/components/SimpleTable";

  const address = "http://localhost:9000/";

export default {
  name: 'App',


  components: {
      SimpleTable,
    LoginComponent,
    AdvancedTable,
      AppBar,
  },

  data: function () {
      return{
          users:[],
          isAuthenticated:false,
          principalData:null,
          isLoading:true,
          snackbar:false,
          timeout:2000,
          snackbarText:'',
      }
  },

  mounted:function () {
      axios.get(address+'currentuser',{withCredentials:true}).then(response=>{
          this.isAuthenticated=true;
          this.isLoading=false;
          this.principalData=response.data;
          axios.get(address+"users",{withCredentials:true}).then(response=>response.data.forEach(elem=>{
              elem.age=this.calculateUserAge(elem.birthDate);
              elem.maritalStatus=this.maritalStatus(elem.isMarried);
              this.users.push(elem)
          })).catch(error=>{
              console.log(error);
              this.isLoading=false;
              this.isAuthenticated=false;
              this.principalData=null;
          })
      }).catch(error=>{
          console.log(error);
          this.isLoading=false;
          this.isAuthenticated=false;
          this.principalData=null;
      });

  },

  methods:{
    maritalStatus:function (isMarried) {
      return isMarried?"Married":"Single";
    },
    calculateUserAge:function (birthDate) {
      return ((new Date().getTime() - new Date(birthDate)) / (24 * 3600 * 365.25 * 1000)) | 0;
    },
    addUser:function (event) {
      axios.post(address+"users",event,{withCredentials:true}).then(response=>{
        response.data.maritalStatus=this.maritalStatus(response.data.isMarried);
        response.data.age=this.calculateUserAge(response.data.birthDate);
          this.snackbarText = "User "+event.name+" "+event.surname+" added";
          this.snackbar=true;
        this.users.push(response.data)
      }).catch(error=>{
          console.log(error);
          this.snackbarText="Sorry, cannot add new user. An error has occurred";
          this.snackbar=true;
      })
    },
    deleteUser:function (event) {
        axios.delete(address+'users/'+event.id,{withCredentials:true}).then(this.afterDeleteUser(event)).
        catch(error=>{
            console.log(error);
            this.snackbarText="Sorry, cannot delete user. An error has occurred";
            this.snackbar=true;
        });
    },
    editUser:function(event){
        console.log('event:'+JSON.stringify(event));
        axios.put(address+"users",event,{withCredentials:true})
        .then(response=>{
            this.users.forEach(current=>{
                if(current.id===response.data.id){
                    response.data.maritalStatus=this.maritalStatus(response.data.isMarried);
                    response.data.age=this.calculateUserAge(response.data.birthDate);
                    let currentUserIndex = this.users.indexOf(current);
                    this.users.splice(currentUserIndex, 1, response.data);
                }
            });
            this.snackbarText="User updated";
            this.snackbar=true;
        }).catch(error=>{
            console.log(error);
            this.snackbarText="Sorry, cannot update user. An error has occurred";
            this.snackbar=true;
        })
    },
    afterDeleteUser:function(event){
        this.users.splice(this.users.indexOf(event), 1);
        this.snackbarText = "User "+event.name+" "+event.surname+" deleted";
        this.snackbar=true;
    },
    loginWithGitHub:function () {
        window.location.href= address+"redirect";
    },
    logout:function () {
        axios.post(address+"logout",{},{withCredentials:true}).then(this.afterSuccessLogout);
    },
    afterSuccessLogout:function () {
        this.isAuthenticated=false;
        this.principalData=null;
    }
  }

};
</script>
