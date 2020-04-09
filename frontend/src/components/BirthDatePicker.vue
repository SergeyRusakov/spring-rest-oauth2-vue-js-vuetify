<template>

    <v-menu ref="menu" v-model="menu" :close-on-content-click="false"
            transition="scale-transition" offset-y min-width="290px">

        <template v-slot:activator="{ on }">
            <v-text-field prepend-icon="mdi-calendar-range" v-model="date" label="Birth date" readonly v-on="on"></v-text-field>
        </template>

        <v-date-picker ref="picker" v-model="date" :max="new Date().toISOString().substr(0, 10)"
                       min="1950-01-01" @change="save"></v-date-picker>

    </v-menu>

</template>

<script>
    export default {
        props:['dateProp'],
        data: () => ({
            menu: false,
            date:null,
        }),
        mounted:function(){
          this.date=this.dateProp;
        },
        watch: {
            menu (val) {
                val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
            },
        },
        methods: {
            save (date) {
                this.$emit('saveDate',date);
                this.$refs.menu.save(date)
            },
        },
    }
</script>

<style scoped>

</style>
