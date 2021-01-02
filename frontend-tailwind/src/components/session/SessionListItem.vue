<template>
  <div>
    <div
      v-if="loading"
      class="flex items-center justify-center flex-shrink-0 text-sm text-white bg-white rounded-md shadow-sm py-11"
    >
      <tw-spinner></tw-spinner>
    </div>
    <div
      v-if="!loading"
      class="col-span-1 overflow-hidden bg-white shadow sm:rounded-md"
    >
      <router-link
        class="block transition duration-150 ease-in-out hover:bg-gray-50 focus:outline-none focus:bg-gray-50"
        :to="'/sessions/' + session.sessionId"
      >
        <div class="flex items-center px-4 py-4 sm:px-6">
          <div class="flex flex-col items-start flex-grow">
            <div class="text-indigo-600 capitalize">{{ formattedDate }}</div>
            <div class="flex flex-col lg:flex-row lg:space-x-12">
              <div class="mt-2 space-y-1 text-sm text-gray-500">
                <div class="flex items-center">
                  <HomeIcon class="h-5 w-5 mr-1.5 text-gray-500"></HomeIcon
                  ><span>{{ session.place.name }}</span>
                </div>
                <div class="flex flex-center">
                  <UserGroupIcon
                    class="h-5 w-5 mr-1.5 text-gray-500"
                  ></UserGroupIcon
                  ><span>{{ session.playerResults.length }} joueurs</span>
                </div>
              </div>
              <div class="mt-2 space-y-1 text-sm text-gray-500">
                <div class="flex items-center">
                  <StarIcon class="w-5 h-5 mr-1 text-yellow-400"></StarIcon>
                  <div>
                    {{ winner.player.firstName }} ({{ winner.result }} €)
                  </div>
                </div>
                <div class="flex">
                  <ArrowCircleDownIcon
                    class="w-5 h-5 mr-1 text-red-500"
                  ></ArrowCircleDownIcon>
                  <div>{{ loser.player.firstName }} ({{ loser.result }} €)</div>
                </div>
              </div>
            </div>
          </div>
          <svg
            class="w-5 h-5 text-gray-400"
            xmlns="http://www.w3.org/2000/svg"
            viewbox="0 0 20 20"
            fill="currentColor"
          >
            <path
              fill-rule="evenodd"
              d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z"
              clip-rule="evenodd"
            ></path>
          </svg>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import {
  ArrowCircleDownIcon,
  HomeIcon,
  UserGroupIcon,
} from "@vue-hero-icons/outline";
import { StarIcon } from "@vue-hero-icons/solid";

export default {
  components: { ArrowCircleDownIcon, HomeIcon, StarIcon, UserGroupIcon },
  props: ["session", "loading"],
  data: () => ({
    formattedDate: String,
  }),
  computed: {
    winner: function () {
      if (!this.session) return;
      return [...this.session.playerResults].sort(
        (pr1, pr2) => pr2.result - pr1.result
      )[0];
    },
    loser: function () {
      if (!this.session) return;
      return [...this.session.playerResults].sort(
        (pr1, pr2) => pr1.result - pr2.result
      )[0];
    },
  },
  mounted() {
    if (this.session) {
      this.formattedDate = format(
        new Date(this.session.date),
        "EEEE d MMMM yyyy",
        { locale: fr }
      );
    }
  },
};
</script>

<style scoped>
</style>