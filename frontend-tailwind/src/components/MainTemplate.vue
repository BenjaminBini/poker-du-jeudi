<template lang="pug">
  div(class="min-h-screen bg-gray-100")
    div(class="bg-indigo-600 pb-32")
      nav(class="bg-indigo-600 border-b border-indigo-300 border-opacity-25 lg:border-none")
        div(class="max-w-7xl mx-auto px-2 sm:px-4 lg:px-8")
          div(class="relative h-16 flex items-center justify-between lg:border-b lg:border-indigo-400 lg:border-opacity-25")
            div(class="px-2 flex items-center lg:px-0")
              div(class="flex-shrink-0")
                img(class="block h-8 w-8" src="/logo.png" alt="Logo poker du jeudi")
              span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4(class="sm:hidden") all
              span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="sm:block md:hidden") sm
              span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="md:block lg:hidden") md
              span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="lg:block xl:hidden") lg
              span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="xl:block") xl
              div(class="hidden lg:block lg:ml-10")
                div(class="flex space-x-4")
                  router-link(
                    v-for="(route, index) in routes.filter(r => r.meta.displayInMenu)"
                    :key="route.path"
                    :to="route.path"
                    exact-active-class="bg-indigo-700"
                    @click.native="isOpen = false"
                    class="rounded-md py-2 px-3 text-sm font-medium text-white hover:bg-indigo-500 hover:bg-opacity-75") {{route.meta.label}}
            div(class="flex lg:hidden")
              // Mobile menu button
              button(@click="isOpen = !isOpen" class="bg-indigo-600 p-2 rounded-md inline-flex items-center justify-center text-indigo-200 hover:text-white hover:bg-indigo-500 hover:bg-opacity-75 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-indigo-600 focus:ring-white" aria-expanded="false")
                span(class="sr-only") Open main menu
                //
                  Heroicon name: menu
                  Menu open: "hidden", Menu closed: "block"
                svg(class="block h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor" aria-hidden="true")
                  path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16")
                //
                  Heroicon name: x
                  Menu open: "block", Menu closed: "hidden"
                svg(class="hidden h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor" aria-hidden="true")
                  path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12")
            div(class="hidden lg:block lg:ml-4")
              div(class="flex items-center")
                // Profile dropdown
                div(class="ml-3 relative flex-shrink-0")
                  div
                    button#user-menu(@click="isOpen = !isOpen" class="bg-indigo-600 rounded-full flex text-sm text-white focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-indigo-600 focus:ring-white" aria-haspopup="true")
                      span(class="sr-only") Open user menu
                      img(class="rounded-full h-8 w-8" src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="")
                  //
                    Profile dropdown panel, show/hide based on dropdown state.
                    Entering: "transition ease-out duration-100"
                    From: "transform opacity-0 scale-95"
                    To: "transform opacity-100 scale-100"
                    Leaving: "transition ease-in duration-75"
                    From: "transform opacity-100 scale-100"
                    To: "transform opacity-0 scale-95"
                  transition(
                    enter-active-class="transition ease-out duration-100"
                    enter-class="transform opacity-0 scale-95"
                    enter-to-class="transform opacity-100 scale-100"
                    leave-active-class="transition ease-in duration-75"
                    leave-class="transform opacity-100 scale-100"
                    leave-to-class="transform opacity-0 scale-95"
                  )
                  div(v-show="isOpen" class="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5" role="menu" aria-orientation="vertical" aria-labelledby="user-menu")
                    a(href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100" role="menuitem")
                      | Your Profile
                    a(href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100" role="menuitem")
                      | Settings
                    a(href="#" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100" role="menuitem")
                      | Sign out
        //
          Mobile menu, toggle classes based on menu state.
          Menu open: "block", Menu closed: "hidden"
        div(:class="isOpen ? 'block' : 'hidden'", class="lg:hidden")
          div(class="px-2 pt-2 pb-3 space-y-1")
            router-link(
              v-for="(route, index) in routes.filter(r => r.meta.displayInMenu)"
              :key="route.path"
              :to="route.path"
              exact-active-class="bg-indigo-900"
              :class="index > 0 ? 'mt-1' : ''"
              @click.native="isOpen = false"
              class="block rounded-md py-2 px-3 text-base font-medium text-white transition duration-150 ease-in-out") {{route.meta.label}}
          div(class="pt-4 pb-3 border-t border-indigo-700")
            div(class="px-5 flex items-center")
              div(class="flex-shrink-0")
                img(class="rounded-full h-10 w-10" src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="")
              div(class="ml-3")
                div(class="text-base font-medium text-white") Tom Cook
                div(class="text-sm font-medium text-indigo-300") tom@example.com
            div(class="mt-3 px-2 space-y-1")
              a(href="#" class="block rounded-md py-2 px-3 text-base font-medium text-white hover:bg-indigo-500 hover:bg-opacity-75")
                | Your Profile
              a(href="#" class="block rounded-md py-2 px-3 text-base font-medium text-white hover:bg-indigo-500 hover:bg-opacity-75")
                | Settings
              a(href="#" class="block rounded-md py-2 px-3 text-base font-medium text-white hover:bg-indigo-500 hover:bg-opacity-75")
                | Sign out
      header(class="py-10")
        div(class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8")
          h1(class="text-3xl font-bold text-white")
            | {{ pageTitle }}
    main(class="-mt-32")
      div(class="max-w-7xl mx-auto pb-12 px-4 sm:px-6 lg:px-8")
        // Replace with your content
        router-view
        // /End replace
</template>

<script>

export default {
  name: 'main-template',
  props: {
    routes: Array
  },
  data: () => ({
    isOpen: false,
  }),
  computed: {
    pageTitle() {
      return this.$store.state.pageTitle
    },
  }
}
</script>