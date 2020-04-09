Vue.component('oauth2-user-row',{
    props:['oauth2User'],
   template:`
        <tr>
             <td>{{oauth2User.id}}</td>
             <td>{{oauth2User.name}}</td>
             <td>{{oauth2User.login}}</td>
             <td>
                <select v-model="newRole">
                    <option>Admin</option>
                    <option>User</option>
                </select>
             </td>
             <td><button @click="save">Save</button></td>
        </tr>
   `,
    data:function(){
       return{
           role:null,
           newRole: null,
       }
    },
    mounted:function(){
      this.role=this.oauth2User.role==='ROLE_ADMIN'?'Admin':'User';
      this.newRole=this.oauth2User.role==='ROLE_ADMIN'?'Admin':'User';
    },

    computed:function () {
        return{
            status:function () {
                return this.oauth2User.role==='ROLE_ADMIN'?'Administrator':'User';
            },
        }
    },

    methods:{
        save:function(){
            this.oauth2User.role=this.newRole==='Admin'?'ROLE_ADMIN':'ROLE_USER';
            axios.put("/oauth2Users",this.oauth2User);
        }
    }

});

let app = new Vue({
    el: '#app',
    template:`
    <div>
        <h1>Oauth2 User management</h1>
        <table>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Login</th>
                <th>Role</th>
                <th></th>
            </tr>
            <oauth2-user-row v-for="oauth2User in oauth2Users" :oauth2User="oauth2User">
            </oauth2-user-row>
        </table>
        </div>
    `,
    mounted:function(){
      axios.get("/oauth2Users")
          .then(response=>response.data.forEach(current=>this.oauth2Users.push(current)));
    },
    data:{
        oauth2Users:[],
    }
})