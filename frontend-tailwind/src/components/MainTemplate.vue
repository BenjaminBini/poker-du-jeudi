<template>
  <div class="min-h-screen bg-gray-100">
    <div class="pb-32 bg-indigo-600">
      <nav
        class="bg-indigo-600 border-b border-indigo-300 border-opacity-25 lg:border-none"
      >
        <div class="px-2 mx-auto max-w-7xl sm:px-4 lg:px-8">
          <div
            class="relative flex items-center justify-between h-16 lg:border-b lg:border-indigo-400 lg:border-opacity-25"
          >
            <div class="flex items-center px-2 lg:px-0">
              <div class="flex-shrink-0">
                <img
                  class="block w-8 h-8"
                  src="/logo.png"
                  alt="Logo poker du jeudi"
                />
              </div>
              <span
                class="px-1 ml-4 leading-5 text-white bg-red-500 rounded sm:hidden"
                >all</span
              ><span
                class="hidden px-1 ml-4 leading-5 text-white bg-red-500 rounded sm:block md:hidden"
                >sm</span
              >
              <span
                class="hidden px-1 ml-4 leading-5 text-white bg-red-500 rounded md:block lg:hidden"
                >md</span
              ><span
                class="hidden px-1 ml-4 leading-5 text-white bg-red-500 rounded lg:block xl:hidden"
                >lg</span
              ><span
                class="hidden px-1 ml-4 leading-5 text-white bg-red-500 rounded xl:block"
                >xl</span
              >
              <div class="hidden lg:block lg:ml-10">
                <div class="flex space-x-4">
                  <router-link
                    class="px-3 py-2 text-sm font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
                    v-for="route in routes.filter(r =&gt; r.meta.displayInMenu)"
                    :key="route.path"
                    :to="route.path"
                    exact-active-class="bg-indigo-700"
                    @click.native="isOpen = false"
                    >{{ route.meta.label }}</router-link
                  >
                </div>
              </div>
            </div>
            <div class="flex lg:hidden">
              <!-- Mobile menu button--><button
                class="inline-flex items-center justify-center p-2 text-indigo-200 bg-indigo-600 rounded-md hover:text-white hover:bg-indigo-500 hover:bg-opacity-75 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-indigo-600 focus:ring-white"
                @click="isOpen = !isOpen"
                aria-expanded="false"
              >
                <span class="sr-only">Open main menu</span
                ><!--Heroicon name: menu
Menu open: "hidden", Menu closed: "block"--><svg
                  class="block w-6 h-6"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewbox="0 0 24 24"
                  stroke="currentColor"
                  aria-hidden="true"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M4 6h16M4 12h16M4 18h16"
                  ></path></svg
                ><!--Heroicon name: x
Menu open: "block", Menu closed: "hidden"--><svg
                  class="hidden w-6 h-6"
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewbox="0 0 24 24"
                  stroke="currentColor"
                  aria-hidden="true"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M6 18L18 6M6 6l12 12"
                  ></path>
                </svg>
              </button>
            </div>
            <div class="hidden lg:block lg:ml-4">
              <div class="flex items-center">
                <!-- Profile dropdown-->
                <div class="relative flex-shrink-0 ml-3">
                  <div>
                    <button
                      class="flex text-sm text-white bg-indigo-600 rounded-full focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-indigo-600 focus:ring-white"
                      id="user-menu"
                      @click="isOpen = !isOpen"
                      aria-haspopup="true"
                    >
                      <span class="sr-only">Open user menu</span
                      ><img
                        class="w-8 h-8 rounded-full"
                        src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=facearea&amp;facepad=2&amp;w=256&amp;h=256&amp;q=80"
                        alt=""
                      />
                    </button>
                  </div>
                  <!--Profile dropdown panel, show/hide based on dropdown state.
Entering: "transition ease-out duration-100"
From: "transform opacity-0 scale-95"
To: "transform opacity-100 scale-100"
Leaving: "transition ease-in duration-75"
From: "transform opacity-100 scale-100"
To: "transform opacity-0 scale-95"-->
                  <transition
                    enter-active-class="transition duration-100 ease-out"
                    enter-class="transform scale-95 opacity-0"
                    enter-to-class="transform scale-100 opacity-100"
                    leave-active-class="transition duration-75 ease-in"
                    leave-class="transform scale-100 opacity-100"
                    leave-to-class="transform scale-95 opacity-0"
                  ></transition>
                  <div
                    class="absolute right-0 w-48 py-1 mt-2 origin-top-right bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5"
                    v-show="isOpen"
                    role="menu"
                    aria-orientation="vertical"
                    aria-labelledby="user-menu"
                  >
                    <a
                      class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      href="#"
                      role="menuitem"
                      >Your Profile</a
                    ><a
                      class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      href="#"
                      role="menuitem"
                      >Settings</a
                    ><a
                      class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
                      href="#"
                      role="menuitem"
                      >Sign out</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!--Mobile menu, toggle classes based on menu state.
Menu open: "block", Menu closed: "hidden"-->
        <div class="lg:hidden" :class="isOpen ? 'block' : 'hidden'">
          <div class="px-2 pt-2 pb-3 space-y-1">
            <router-link
              class="block px-3 py-2 text-base font-medium text-white transition duration-150 ease-in-out rounded-md"
              v-for="(route, index) in routes.filter(r =&gt; r.meta.displayInMenu)"
              :key="route.path"
              :to="route.path"
              exact-active-class="bg-indigo-900"
              :class="index &gt; 0 ? 'mt-1' : ''"
              @click.native="isOpen = false"
              >{{ route.meta.label }}</router-link
            >
          </div>
          <div class="pt-4 pb-3 border-t border-indigo-700">
            <div class="flex items-center px-5">
              <div class="flex-shrink-0">
                <img
                  class="w-10 h-10 rounded-full"
                  src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&amp;ixid=eyJhcHBfaWQiOjEyMDd9&amp;auto=format&amp;fit=facearea&amp;facepad=2&amp;w=256&amp;h=256&amp;q=80"
                  alt=""
                />
              </div>
              <div class="ml-3">
                <div class="text-base font-medium text-white">Tom Cook</div>
                <div class="text-sm font-medium text-indigo-300">
                  tom@example.com
                </div>
              </div>
            </div>
            <div class="px-2 mt-3 space-y-1">
              <a
                class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
                href="#"
                >Your Profile</a
              ><a
                class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
                href="#"
                >Settings</a
              ><a
                class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
                href="#"
                >Sign out</a
              >
            </div>
          </div>
        </div>
      </nav>
      <header class="py-10">
        <custom-heading
          :page-title="pageTitle"
          :page-actions="pageActions"
        ></custom-heading>
      </header>
    </div>
    <main class="-mt-32">
      <div class="px-4 pb-12 mx-auto max-w-7xl sm:px-6 lg:px-8">
        <!-- Replace with your content-->
        <router-view></router-view>
        <!-- /End replace-->
      </div>
    </main>
  </div>
</template>

<script>
import CustomHeading from "@/components/CustomHeading";

export default {
  name: "main-template",
  components: {
    CustomHeading,
  },
  props: {
    routes: Array,
  },
  data: () => ({
    isOpen: false,
  }),
  computed: {
    pageTitle() {
      return this.$store.state.pageTitle;
    },
    pageActions() {
      return this.$store.state.pageActions;
    },
  },
};
</script>