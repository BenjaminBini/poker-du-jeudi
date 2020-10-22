<template lang="pug">
  div
    b-navbar.bg-dark.mb-4(toggleable="lg", type="dark")
      .container
        b-navbar-brand(href='/')
          img(src='/logo.png', style='width: 45px; margin-right: 15px')
          | Poker du Jeudi
        b-navbar-toggle(target="nav-collapse")
        b-collapse#nav-collapse(is-nav)
          b-navbar-nav
            b-nav-item(to="/", active-class="active", exact) Accueil
            b-nav-item(to="/players", active-class="active") Joueurs
            b-nav-item(to="/sessions", active-class="active") Sessions
            b-nav-item(to="/stats", active-class="active") Stats

          b-navbar-nav.ml-auto
            - var isLoggedIn = false
            if isLoggedIn
              a.nav-link(href="/logout") Logout
            else
              a.nav-link(href="/login") Login
    router-view(:hellomsg="msg")
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      msg: 'Welcome to your Vue.js powered Spring Boot App'
    }
  }
}
</script>

<style lang="scss">
</style>