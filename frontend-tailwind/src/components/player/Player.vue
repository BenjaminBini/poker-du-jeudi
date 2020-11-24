<template lang="pug">
  div(v-if="loaded")
    dl(class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3")
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              CashIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Résultat total
              dd
                div(class="text-2xl font-semibold text-gray-900") {{playerTotalResult}} €
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              PresentationChartLineIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Résultat de la saison
              dd(class="flex items-center space-x-3")
                div(class="text-2xl font-semibold text-gray-900") {{currentSeasonResult}} €
                result-badge(:player-result="dateOrderedResults[0]")
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              CalendarIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Participations
              dd(class="flex items-baseline")
                div(class="text-2xl font-semibold text-gray-900") {{player.playerResults.length}}
    div(class="mt-14 bg-white overflow-hidden shadow rounded-lg divide-y divide-gray-200")
      div(class="px-4 py-5 sm:px-6")
        h2.text-xl Résultats
        // Content goes here
        // We use less vertical padding on card headers on desktop than on body sections
      div(class="px-4 py-5 sm:p-6")
        ResultChart(:results="player.playerResults", class="relative", style="height: 60vh")
</template>

<script>
import PlayerService from "../../services/player-service";
import { CalendarIcon, CashIcon, PresentationChartLineIcon } from "@vue-hero-icons/outline"
import ResultChart from "./ResultChart";

export default {
  name: "Player",
  components: {
    CalendarIcon,
    CashIcon,
    PresentationChartLineIcon,
    ResultChart
  },
  data: () => ({
    player: Object,
    loaded: false,
  }),
  computed: {
    playerTotalResult: function() {
      if (this.player) {
        return PlayerService.getPlayerTotalResult(this.player);
      }
      return 0;
    },
    currentSeasonResult: function() {
      if (this.player) {
        return PlayerService.getCurrentSeasonResult(this.player);
      }
      return 0;
    },
    dateOrderedResults: function() {
      let results = [...this.player.playerResults];
      return results
          .sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date));
    }
  },
  mounted() {
    let playerId = this.$route.params.id;
    PlayerService.getPlayer(playerId).then(response => {
      this.player = response.data;
      this.$store.commit('setPageTitle', this.player.firstName);
      this.loaded = true;
    });
  }
}
</script>

<style scoped>

</style>