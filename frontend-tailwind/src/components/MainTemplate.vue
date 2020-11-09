<template lang="pug">
.min-h-screen.bg-gray-100
    .bg-indigo-800.pb-32
        nav.bg-indigo-800.border-b.border-indigo-400.border-opacity-25(class="lg:border-none")
            .max-w-7xl.mx-auto.px-2(class="sm:px-4 lg:px-8")
                .relative.h-16.flex.items-center.justify-between(class="lg:border-b lg:border-indigo-400 lg:border-opacity-25")
                    .px-2.flex.items-center(class="lg:px-0")
                        .flex-shrink-0
                            img.block.h-8.w-auto(src="/logo.png" alt="Workflow")
                        span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4(class="sm:hidden") all
                        span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="sm:block md:hidden") sm
                        span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="md:block lg:hidden") md
                        span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="lg:block xl:hidden") lg
                        span.bg-red-500.px-1.rounded.leading-5.text-white.ml-4.hidden(class="xl:block") xl
                        .hidden(class="lg:block lg:ml-6")
                            .flex
                              router-link.ml-4.rounded-md.py-2.px-3.text-sm.leading-5.font-medium.text-white.transition.duration-150.ease-in-out(
                                v-for="(route, index) in routes.filter(r => r.meta.displayInMenu)"
                                :key="route.path"
                                :to="route.path"
                                exact-active-class="bg-indigo-900"
                                :class="index > 0 ? 'ml-4' : ''"
                                @click.native="isOpen = false"
                                class="focus:outline-none focus:text-white focus:bg-indigo-700") {{route.meta.label}}
                    .flex(class="lg:hidden")
                        // Mobile menu button
                        button.p-2.rounded-md.inline-flex.items-center.justify-center.text-indigo-300.transition.duration-150.ease-in-out(
                          class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700 focus:text-white"
                          aria-expanded="false"
                          @click="isOpen = !isOpen"
                        )
                            span.sr-only Open main menu
                            // Icon when menu is closed.
                            //
                                Heroicon name: menu
                                Menu open: "hidden", Menu closed: "block"
                            svg.flex-shrink-0.h-6.w-6(:class="isOpen ? 'hidden' : 'block'"  aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor")
                                path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16")
                            // Icon when menu is open.
                            //
                                Heroicon name: x
                                Menu open: "block", Menu closed: "hidden"
                            svg.flex-shrink-0.h-6.w-6(:class="isOpen ? 'block' : 'hidden'" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor")
                                path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12")
                    .hidden(class="lg:block lg:ml-4")
                        .flex.items-center
                            // Profile dropdown
                            .relative.flex-shrink-0.ml-4
                                div
                                    button#user-menu.rounded-full.flex.text-sm.text-white.transition.duration-150.ease-in-out(class="focus:outline-none focus:shadow-solid" aria-haspopup="true"
                                      @click="isOpen = !isOpen"
                                    )
                                        span.sr-only Open profile menu
                                        img.rounded-full.h-8.w-8(src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="")
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
                                  .origin-top-right.absolute.right-0.mt-2.w-48.rounded-md.shadow-lg(v-show="isOpen")
                                      .py-1.bg-white.rounded-md.shadow-xs(role="menu" aria-orientation="vertical" aria-labelledby="user-menu")
                                          a.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(href="#" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Your Profile
                                          router-link.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(to="/foo" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Settings
                                          router-link.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(to="/bar" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Sign out
            //
                Mobile menu, toggle classes based on menu state.
                Menu open: "block", Menu closed: "hidden"
            div(:class="isOpen ? 'block' : 'hidden'", class="lg:hidden")
                .px-2.pt-2.pb-3
                    router-link.block.rounded-md.py-2.px-3.text-base.font-medium.text-white.transition.duration-150.ease-in-out(
                      v-for="(route, index) in routes.filter(r => r.meta.displayInMenu)"
                      :key="route.path"
                      :to="route.path"
                      exact-active-class="bg-indigo-900"
                      :class="index > 0 ? 'mt-1' : ''"
                      @click.native="isOpen = false"
                      class="focus:outline-none focus:text-white focus:bg-indigo-700") {{route.meta.label}}
                .pt-4.pb-3.border-t.border-indigo-400.border-opacity-25
                    .px-5.flex.items-center
                        .flex-shrink-0
                            img.rounded-full.h-10.w-10(src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="")
                        .ml-3
                            .text-base.font-medium.leading-6.text-white Tom Cook
                            .text-sm.font-medium.leading-5.text-indigo-300 tom@example.com
                    .mt-3.px-2
                        a.block.rounded-md.py-2.px-3.text-base.font-medium.text-indigo-300.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Your Profile
                        a.block.mt-1.rounded-md.py-2.px-3.text-base.font-medium.text-indigo-300.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Settings
                        a.block.mt-1.rounded-md.py-2.px-3.text-base.font-medium.text-indigo-300.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Sign out
        header.py-10
            .max-w-7xl.mx-auto.px-4(class="sm:px-6 lg:px-8")
                h1.text-3xl.leading-9.font-bold.text-white
                    | {{ pageTitle }}
    main.-mt-32
        .max-w-7xl.mx-auto.pb-12.px-4(class="sm:px-6 lg:px-8")
            // Replace with your content
            // .bg-white.rounded-lg.shadow.px-5.py-6(class="sm:px-6")
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