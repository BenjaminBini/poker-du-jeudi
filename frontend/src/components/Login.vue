<template>
  <div class="sm:w-full sm:max-w-md">
    <div class="px-4 py-8 bg-white shadow rounded-lg sm:px-10">
      <form @submit="login" class="space-y-6" method="POST">
        <div>
          <label for="username" class="block text-sm font-medium text-gray-700">
            Nom d'utilisateur
          </label>
          <div class="mt-1">
            <input
              v-model="username"
              id="username"
              name="username"
              type="text"
              required
              class="block w-full px-3 py-2 placeholder-gray-400 border border-gray-300 rounded-md shadow-sm appearance-none focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
        </div>

        <div>
          <label for="password" class="block text-sm font-medium text-gray-700">
            Mot de passe
          </label>
          <div class="mt-1">
            <input
              v-model="password"
              id="password"
              name="password"
              type="password"
              autocomplete="current-password"
              required
              class="block w-full px-3 py-2 placeholder-gray-400 border border-gray-300 rounded-md shadow-sm appearance-none focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="flex justify-center w-full px-4 py-2 text-sm font-medium text-white bg-indigo-600 border border-transparent rounded-md shadow-sm hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            :class="loading ? '' : ''"
          >
            <tw-spinner v-if="loading"></tw-spinner>
            <template v-else>Connexion</template>
          </button>
        </div>
      </form>
    </div>
    <tw-modal
      :is-open="showErrorModal"
      title="Connexion échouée"
      text="Mauvaise combinaison nom d'utilisateur / mot de passe"
      cancelLabel="OK"
      @cancel="showErrorModal = false"
    ></tw-modal>
  </div>
</template>

<script>
import AuthService from "@/services/auth-service";
export default {
  data: function () {
    return {
      username: undefined,
      password: undefined,
      loading: false,
      showErrorModal: false,
    };
  },
  methods: {
    login: async function (e) {
      e.preventDefault();
      this.loading = true;
      try {
        await AuthService.login(this.username, this.password);
        this.loading = false;
        this.$store.commit("enableEditMode");
        const returnURL = this.$route.query.returnURL;
        if (returnURL) {
          this.$router.push(returnURL);
        } else {
          this.$router.push("/");
        }
      } catch (err) {
        this.loading = false;
        this.showErrorModal = true;
        this.$store.commit("disableEditMode");
      }
    },
  },
};
</script>

<style>
</style>