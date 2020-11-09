<template>
  <div id="app">
    <main-template :routes="routes" />
  </div>
</template>

<script>
import MainTemplate from './components/MainTemplate.vue'
import routes from "./router/routes";

export default {
  name: 'App',
  components: {
    MainTemplate
  },
  data: () => ({
      routes
  }),
}
</script>