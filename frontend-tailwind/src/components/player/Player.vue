<template lang="pug">
  div(v-if="loaded")
    dl(class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4")
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              CashIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Résultat total
              dd(class="flex items-center space-x-3")
                div(class="text-2xl font-semibold text-gray-900") {{playerTotalResult}} €
                result-badge(v-once :player-result="dateOrderedResults[0]")
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              PresentationChartLineIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Résultat de la saison
              dd
                div(class="text-2xl font-semibold text-gray-900") {{currentSeasonResult}} €
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              CalendarIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Participations
              dd(class="flex items-baseline")
                div(class="text-2xl font-semibold text-gray-900") {{player.playerResults.length}}
      div(class="bg-white overflow-hidden shadow rounded-lg")
        div(class="px-4 py-5 sm:p-6")
          div(class="flex items-center")
            div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
              CalendarIcon(class="h-6 w-6 text-white")
            div(class="ml-5 w-0 flex-1")
              dt(class="text-sm font-medium text-gray-500 truncate") Participations
              dd(class="flex items-baseline")
                div(class="text-2xl font-semibold text-gray-900") {{player.playerResults.length}}
    div(v-if="dateOrderedResults.length > 1" class="mt-14 bg-white overflow-hidden shadow rounded-lg divide-y divide-gray-200")
      div(class="bg-white px-4 py-5 sm:px-6")
        div(class="-ml-4 -mt-2 flex items-center justify-between flex-wrap sm:flex-nowrap")
          div(class="ml-4 mt-2")
            h3(class="text-lg leading-6 font-medium text-gray-900")
              | Résultats
          div(v-if="years.length > 1" class="ml-4 mt-2 flex-shrink-0 space-x-3")
            button(@click="selectedYear = 0" type="button" class="relative inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500") Tout
            button(v-for="year in years" @click="selectedYear = year" type="button" class="relative inline-flex items-center px-4 py-2 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500") {{year}}
        // Content goes here
        // We use less vertical padding on card headers on desktop than on body sections
      div(class="px-4 py-5 sm:p-6")
          ResultChart(:results="dateOrderedResults", class="relative", style="height: 400px")
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
    selectedYear: 0,
  }),
  computed: {
    years: function() {
      if (this.player) {
        const setOfYears = new Set(this.player.playerResults.map(pr => pr.session.season.year));
        return [...setOfYears];
      }
      return [];
    },
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
          .sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date))
          .filter(r => this.selectedYear === 0 || r.session.season.year === this.selectedYear);
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