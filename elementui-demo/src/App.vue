<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app'
}
</script>

<style>
  @import "./assets/css/main.css";
  @import "./assets/css/color-dark.css"; 
  
 body {
    background: url('images/login.jpg') repeat;
}

</style>
