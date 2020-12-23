<template>
  <div>
    <div
      v-if="loading"
      class="flex items-center justify-center flex-shrink-0 py-6 text-sm text-white bg-white rounded-md shadow-sm"
    >
      <tw-spinner></tw-spinner>
    </div>
    <router-link
      v-if="!loading"
      class="block transition duration-150 ease-in-out hover:bg-gray-50 focus:outline-none focus:bg-gray-50"
      :to="'/players/' + player.playerId"
    >
      <div class="flex items-center px-4 py-4 sm:px-6">
        <div class="flex items-start flex-1 min-w-0 lg:items-center">
          <player-picture class="flex-shrink-0 hidden mr-6"></player-picture>
          <div
            class="flex flex-col min-w-0 lg:flex-row lg:flex-grow lg:items-center"
          >
            <div class="flex flex-col min-w-0 lg:w-64">
              <div class="text-indigo-600 truncate">{{ player.firstName }}</div>
              <session-count
                class="min-w-0 mt-2"
                :session-count="player.playerResults.length"
              ></session-count>
              <player-last-session
                class="min-w-0 mt-1"
                :session-date="getLastSessionDate(player)"
              ></player-last-session>
            </div>
            <div
              class="flex flex-col lg:flex-row lg:flex-grow lg:justify-around"
            >
              <player-total-result
                class="mt-4 flex-2 lg:mt-0 lg:w-32"
                :result="playerTotalResult"
              ></player-total-result>
              <div
                class="flex flex-col justify-center mt-2 flex-4 lg:mt-0 lg:w-96"
              >
                <div class="text-sm leading-5 text-gray-500">
                  5 dernières sessions
                </div>
                <div
                  class="flex flex-wrap items-center text-sm leading-5 text-gray-500"
                >
                  <result-badge
                    class="mt-1"
                    v-for="(result, index) in getLastNResults(player, 5)"
                    :key="index"
                    :player-result="result"
                  ></result-badge>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <!-- Heroicon name: chevron-right--><svg
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
      </div>
    </router-link>
  </div>
</template>

<script>
import PlayerService from "../../../services/player-service";
import PlayerLastSession from "../PlayerLastSession";

import { format } from "date-fns";
export default {
  name: "PlayerListItem",
  components: { PlayerLastSession },
  props: {
    player: Object,
    loading: Boolean,
  },
  data: () => ({
    result: undefined,
  }),
  computed: {
    playerTotalResult: function () {
      return PlayerService.getPlayerTotalResult(this.player);
    },
  },
  methods: {
    getLastSessionDate: function (player) {
      if (
        !player ||
        !player.playerResults ||
        player.playerResults.length === 0
      ) {
        return null;
      }
      let sessions = player.playerResults.map((pr) => pr.session);
      sessions.sort((s1, s2) => new Date(s2.date) - new Date(s1.date));
      return format(new Date(sessions[0].date), "dd/MM/yyyy");
    },
    getLastNResults: function (player, n) {
      if (
        !player ||
        !player.playerResults ||
        player.playerResults.length === 0
      ) {
        return null;
      }
      let results = [...player.playerResults];
      results.sort(
        (r1, r2) => new Date(r2.session.date) - new Date(r1.session.date)
      );
      return results.slice(0, n);
    },
  },
};
</script>

<style scoped>
</style>