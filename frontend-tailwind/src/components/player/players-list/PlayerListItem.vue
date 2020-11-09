<template lang="pug">
    router-link.block.transition.duration-150.ease-in-out(:to="'/players/' + player.playerId" class="hover:bg-gray-50 focus:outline-none focus:bg-gray-50")
      .flex.items-center.px-4.py-4(class="sm:px-6")
        .min-w-0.flex-1.flex.items-start(class="lg:items-center")
          player-picture.mr-6.flex-shrink-0.hidden
          .flex.flex-col.min-w-0(class="lg:flex-row lg:flex-grow lg:items-center")
            .flex.flex-col.min-w-0(class="lg:w-64")
              .text-sm.leading-5.font-medium.text-indigo-600.truncate {{player.firstName}}
              session-count.mt-2.min-w-0(:session-count="player.playerResults.length")
              player-last-session.mt-1.min-w-0(:session-date="getLastSessionDate(player)")
            .flex.flex-col(class="lg:flex-row lg:flex-grow lg:justify-around")
              player-total-result.flex-2.mt-4(:result="getPlayerTotalResult(player)" class="lg:mt-0 lg:w-32")
              .flex.flex-col.flex-4.justify-center.mt-2(class="lg:mt-0 lg:w-96")
                .text-sm.leading-5.text-gray-500 5 dernières sessions
                .flex.flex-wrap.items-center.text-sm.leading-5.text-gray-500
                  result-badge.mt-1(v-for="(result, index) in getLastNResults(player, 5)" :key="index" :player-result="result")
        div
          // Heroicon name: chevron-right
          svg.h-5.w-5.text-gray-400(xmlns="http://www.w3.org/2000/svg" viewbox="0 0 20 20" fill="currentColor")
            path(fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd")

</template>

<script>
import PlayerLastSession from "../PlayerLastSession";
import {format} from "date-fns";
export default {
  name: "PlayerListItem",
  components: {PlayerLastSession},
  props: {
    player: Object,
  },
  data: () => ({
    result: undefined,
  }),
  methods: {
    getLastSessionDate: function(player) {
      if (!player || !player.playerResults || player.playerResults.length === 0) {
        return null;
      }
      let sessions = player.playerResults.map(pr => pr.session);
      sessions.sort((s1, s2) => new Date(s2.date) - new Date(s1.date));
      return format(new Date(sessions[0].date), 'dd/MM/yyyy')
    },
    getLastNResults: function(player, n) {
      if (!player || !player.playerResults || player.playerResults.length === 0) {
        return null;
      }
      let results = [...player.playerResults];
      results.sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date));
      return results.slice(0, n);
    },
    getPlayerTotalResult: function(player) {
      if (!this.result) {
        this.result = player.playerResults.map(pr => pr.result).reduce((acc, curr) => acc + curr);
      }
      return this.result;
    }
  }
}
</script>

<style scoped>

</style>