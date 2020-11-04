<template lang="pug">
.min-h-screen.bg-gray-100
    .bg-indigo-800.pb-32
        nav.bg-indigo-800.border-b.border-indigo-400.border-opacity-25(class="lg:border-none")
            .max-w-7xl.mx-auto.px-2(class="sm:px-4 lg:px-8")
                .relative.h-16.flex.items-center.justify-between(class="lg:border-b lg:border-indigo-400 lg:border-opacity-25")
                    .px-2.flex.items-center(class="lg:px-0")
                        .flex-shrink-0
                            img.block.h-8.w-auto(src="/logo.png" alt="Workflow")
                        .hidden(class="lg:block lg:ml-6")
                            .flex
                                a.rounded-md.bg-indigo-900.py-2.px-3.text-sm.leading-5.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700") Dashboard
                                a.ml-4.rounded-md.py-2.px-3.text-sm.leading-5.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700") Team
                                a.ml-4.rounded-md.py-2.px-3.text-sm.leading-5.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700") Projects
                                a.ml-4.rounded-md.py-2.px-3.text-sm.leading-5.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700") Calendar
                    .flex-1.px-2.flex.justify-center(class="lg:ml-6 lg:justify-end")
                        .max-w-lg.w-full(class="lg:max-w-xs")
                            label.sr-only(for="search") Search
                            .relative
                                .pointer-events-none.absolute.inset-y-0.left-0.pl-3.flex.items-center(aria-hidden="true")
                                    svg.h-5.w-5.text-gray-400(fill="currentColor" viewbox="0 0 20 20")
                                        path(fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd")
                                input#search.block.w-full.bg-white.py-2.pl-10.pr-3.border.border-transparent.rounded-md.leading-5.placeholder-gray-500.transition.duration-150.ease-in-out(class="focus:outline-none focus:border-indigo-300 focus:shadow-outline-indigo sm:text-sm" placeholder="Search" type="search")
                    .flex(class="lg:hidden")
                        // Mobile menu button
                        button.p-2.rounded-md.inline-flex.items-center.justify-center.text-indigo-300.transition.duration-150.ease-in-out(class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:bg-indigo-700 focus:text-white" aria-expanded="false")
                            span.sr-only Open main menu
                            // Icon when menu is closed.
                            //
                                Heroicon name: menu
                                Menu open: "hidden", Menu closed: "block"
                            svg.block.flex-shrink-0.h-6.w-6(aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor")
                                path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16")
                            // Icon when menu is open.
                            //
                                Heroicon name: x
                                Menu open: "block", Menu closed: "hidden"
                            svg.hidden.flex-shrink-0.h-6.w-6(aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor")
                                path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12")
                    .hidden(class="lg:block lg:ml-4")
                        .flex.items-center
                            button.flex-shrink-0.border-2.border-transparent.rounded-full.p-1.text-indigo-300.transition.duration-150.ease-in-out(class="hover:text-white focus:outline-none focus:text-white focus:bg-indigo-700")
                                span.sr-only View notifications
                                // Heroicon name: bell
                                svg.h-6.w-6(aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewbox="0 0 24 24" stroke="currentColor")
                                    path(stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9")
                            // Profile dropdown
                            .relative.flex-shrink-0.ml-4
                                div
                                    button#user-menu.rounded-full.flex.text-sm.text-white.transition.duration-150.ease-in-out(class="focus:outline-none focus:shadow-solid" aria-haspopup="true")
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
                                .origin-top-right.absolute.right-0.mt-2.w-48.rounded-md.shadow-lg
                                    .py-1.bg-white.rounded-md.shadow-xs(role="menu" aria-orientation="vertical" aria-labelledby="user-menu")
                                        a.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(href="#" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Your Profile
                                        a.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(href="#" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Settings
                                        a.block.py-2.px-4.text-sm.leading-5.text-gray-700.transition.duration-150.ease-in-out(href="#" class="hover:bg-gray-100 focus:outline-none focus:bg-gray-100" role="menuitem") Sign out
            //
                Mobile menu, toggle classes based on menu state.
                Menu open: "block", Menu closed: "hidden"
            .hidden(class="lg:hidden")
                .px-2.pt-2.pb-3
                    a.block.bg-indigo-900.rounded-md.py-2.px-3.text-base.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="focus:outline-none focus:text-white focus:bg-indigo-700") Dashboard
                    a.block.mt-1.rounded-md.py-2.px-3.text-base.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Team
                    a.block.mt-1.rounded-md.py-2.px-3.text-base.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Projects
                    a.block.mt-1.rounded-md.py-2.px-3.text-base.font-medium.text-white.transition.duration-150.ease-in-out(href="#" class="hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700") Calendar
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
                    | {{ msg }}
    main.-mt-32
        .max-w-7xl.mx-auto.pb-12.px-4(class="sm:px-6 lg:px-8")
            // Replace with your content
            .bg-white.rounded-lg.shadow.px-5.py-6(class="sm:px-6")
                .h-96.border-4.border-dashed.border-gray-200.rounded-lg
            // /End replace

</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  }
}
</script>