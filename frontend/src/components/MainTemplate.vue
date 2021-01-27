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
              <router-link to="/" class="flex-shrink-0">
                <div class="flex items-center">
                  <img
                    class="block w-8 h-8"
                    src="/logo.png"
                    alt="Logo poker du jeudi"
                  />
                  <span class="pl-3 font-bold text-white text-md"
                    >Poker du jeudi
                  </span>
                </div>
              </router-link>
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
            <div class="hidden px-3 lg:block lg:ml-4">
              <div class="flex items-center">
                <router-link
                  v-if="!editMode"
                  :to="`/login?returnURL=${this.$router.currentRoute.path}`"
                  class="text-sm text-white hover:underline"
                  >Se connecter</router-link
                >
                <a
                  href="#"
                  v-if="editMode"
                  class="text-sm text-white hover:underline"
                  @click="logout"
                  >Se déconnecter</a
                >
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
          <div class="pb-3 border-t border-indigo-700">
            <div class="px-2 mt-3 space-y-1">
              <router-link
                v-if="!editMode"
                :to="`/login?returnURL=${this.$router.currentRoute.path}`"
                class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
                @click.native="isOpen = false"
              >
                Se connecter
              </router-link>
            </div>
            <a
              href="#"
              v-if="editMode"
              class="block px-3 py-2 text-base font-medium text-white rounded-md hover:bg-indigo-500 hover:bg-opacity-75"
              @click="logout"
              >Se déconnecter</a
            >
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
import AuthService from "@/services/auth-service";

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
    editMode() {
      return this.$store.state.editMode;
    },
  },
  methods: {
    logout() {
      this.isOpen = false;
      AuthService.logout();
      this.$store.commit("disableEditMode");
    },
  },
};
</script>