<template lang="pug">
  div(class="flex flex-col mt-6")
    div(class="-my-2 overflow-x-auto sm:-mx-6 lg:-mx-8")
      div(class="py-2 align-middle inline-block min-w-full sm:px-6 lg:px-8")
        div(class="shadow overflow-hidden border-b border-gray-200 sm:rounded-lg")
          nav(class="bg-white flex justify-between -space-x-px")
            div(class="w-full flex justify-evenly items-center")
              button(@click="forceShowReg = true"
                class="py-2 focus:outline-none w-1/2 flex justify-center items-center"
                :class="showOnlyRegs ? 'bg-indigo-500 text-white' : 'bg-white text-indigo-500'")
                UsersIcon(class="h-5 w-5 mr-2")
                span Habitués
              button(@click="forceShowReg = false"
                class="py-2 focus:outline-none w-1/2 flex justify-center items-center"
                :class="!showOnlyRegs ? 'bg-indigo-500 text-white' : 'bg-white text-indigo-500'")
                UserGroupIcon(class="h-5 w-5 mr-2")
                span Tous
          table(class="min-w-full divide-y divide-gray-200 border-t")
            thead
              tr
                th(scope="col" class="px-3 py-2 bg-white text-left text-xs font-medium text-gray-500 uppercase tracking-wider")
                  | #
                th(scope="col" class="px-0 py-3 bg-white text-left text-xs font-medium text-gray-500 uppercase tracking-wider")
                  | Joueur
                th(scope="col" class="px-0 py-3 bg-white text-left text-xs font-medium text-gray-500 uppercase tracking-wider")
                  | Part.
                th(scope="col" class="px-0 py-3 bg-white text-left text-xs font-medium text-gray-500 uppercase tracking-wider")
                  | Total
            tbody
              tr(v-for="(row, index) in table"
                :key="row.playerId"
                :class="{ 'bg-gray-50': index % 2 === 0, 'bg-white': index % 2 !== 0, 'bg-indigo-500': row.playerId === activePlayerId }"
              )
                td(:class="row.playerId === activePlayerId ? 'text-white' : 'text-gray-900'"
                  class="px-3 py-2 whitespace-nowrap text-sm font-medium")
                  | {{row.rank}}
                td(v-if="row.playerId !== activePlayerId"
                  class="px-0 py-3 whitespace-nowrap text-sm font-medium text-indigo-600")
                  router-link(:to="'/players/' + row.playerId") {{row.playerName}}
                td(v-if="row.playerId === activePlayerId"
                  class="px-0 py-3 whitespace-nowrap text-sm font-medium text-white") {{row.playerName}}
                td(:class="row.playerId === activePlayerId ? 'text-white' : 'text-gray-500'"
                  class="px-0 py-3 whitespace-nowrap text-sm font-medium")
                  | {{row.sessionsCount}}
                td(:class="row.playerId === activePlayerId ? 'text-white' : 'text-gray-900'"
                  class="px-0 py-3 whitespace-nowrap text-sm")
                  | {{row.total}} €
</template>

<script>
import { UserGroupIcon, UsersIcon } from "@vue-hero-icons/solid";

export default {
  name: "RankingComponent",
  props: ["rankings", "previousRankings", "activePlayerId", "seasonRanking"],
  components: {
    UserGroupIcon,
    UsersIcon,
  },
  data: () => ({
    forceShowReg: undefined
  }),
  watch: {
    '$route': function() {
      this.forceShowReg = undefined;
    }
  },
  computed: {
    showOnlyRegs: function() {
      if (this.forceShowReg !== undefined) return this.forceShowReg;
      if (!Array.isArray(this.rankings)) return true;
      const activePlayerRanking = this.rankings
          .find(r => r.player.playerId === this.activePlayerId && this.seasonRanking !== r.rankingKey.general);
      return !activePlayerRanking || activePlayerRanking.sessionsCount >= this.sessionsCountToBeReg;
    },
    sessionsCountToBeReg: function() {
      return this.seasonRanking ? 5 : 15;
    },
    table: function() {
      if (!Array.isArray(this.rankings)) return [];
      const rankings = [...this.rankings
          .filter(r => r.rankingKey.general !== this.seasonRanking
          && (!this.showOnlyRegs || r.sessionsCount >= this.sessionsCountToBeReg)
      )];
      rankings.sort((r1, r2) => r2.total - r1.total);
      return rankings.map((r, i) => {
          return {
            total: r.total,
            rank: i + 1,
            playerId: r.player.playerId,
            playerName: r.player.firstName,
            sessionsCount: r.sessionsCount
          }
      });
    }
  }
}
</script>

<style scoped>

</style>