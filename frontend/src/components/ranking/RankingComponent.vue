<template>
  <div
    class="flex flex-col overflow-hidden divide-y divide-gray-200 rounded-lg shadow"
  >
    <div
      class="p-2 pl-3 font-bold tracking-wide text-center text-indigo-600 bg-white text-l"
    >
      <h2>{{ title }}</h2>
    </div>
    <div class="overflow-x-auto sm:-mx-6 lg:-mx-8">
      <div class="inline-block min-w-full align-middle sm:px-6 lg:px-8">
        <div class="overflow-hidden divide-y divide-gray-200">
          <nav
            v-if="!loading && enoughDataToShowOnlyRegs"
            class="flex justify-between -space-x-px bg-white"
          >
            <div class="flex items-center w-full justify-evenly">
              <button
                class="flex items-center justify-center w-1/2 py-2 focus:outline-none"
                @click="forceShowReg = true"
                :class="
                  showOnlyRegs
                    ? 'bg-indigo-500 text-white'
                    : 'bg-white text-indigo-500'
                "
              >
                <users-icon class="w-5 h-5 mr-2"></users-icon
                ><span>Habitués</span>
              </button>
              <button
                class="flex items-center justify-center w-1/2 py-2 focus:outline-none"
                @click="forceShowReg = false"
                :class="
                  !showOnlyRegs
                    ? 'bg-indigo-500 text-white'
                    : 'bg-white text-indigo-500'
                "
              >
                <user-group-icon class="w-5 h-5 mr-2"></user-group-icon
                ><span>Tous</span>
              </button>
            </div>
          </nav>
          <div
            v-if="loading"
            class="flex items-center justify-center py-6 bg-white"
          >
            <tw-spinner></tw-spinner>
          </div>
          <table v-if="!loading" class="min-w-full divide-y divide-gray-200">
            <thead>
              <tr>
                <th
                  class="px-3 py-2 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  #
                </th>
                <th
                  class="px-0 py-3 text-xs font-medium tracking-wider text-left text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Joueur
                </th>
                <th
                  class="px-0 py-3 text-xs font-medium tracking-wider text-right text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Part.
                </th>
                <th
                  class="py-3 pl-0 pr-3 text-xs font-medium tracking-wider text-right text-gray-500 uppercase bg-white"
                  scope="col"
                >
                  Résultat
                </th>
              </tr>
            </thead>
            <tbody>
              <tr
                v-for="(row, index) in table"
                :key="row.playerId"
                :class="{
                  'bg-gray-50': index % 2 === 0,
                  'bg-white': index % 2 !== 0,
                  'bg-indigo-500': row.isActive,
                }"
              >
                <td
                  class="px-3 py-2 text-sm font-medium whitespace-nowrap items-center"
                  :class="row.isActive ? 'text-white' : 'text-gray-900'"
                >
                  <div>{{ row.rank }}</div>
                </td>
                <td
                  class="px-0 py-3 text-sm font-medium text-indigo-600 whitespace-nowrap"
                  v-if="!row.isActive"
                >
                  <div class="inline-flex items-center">
                    <router-link :to="'/players/' + row.playerId">{{
                      row.playerName
                    }}</router-link
                    ><span
                      class="inline-block w-2 h-2 ml-2 bg-green-600 rounded-lg"
                      v-if="row.isPresent"
                    ></span>
                    <EvolutionIndicator
                      :evolution="row.evolution"
                      :isActive="false"
                    ></EvolutionIndicator>
                  </div>
                </td>
                <td
                  class="px-0 py-3 text-sm font-medium text-white whitespace-nowrap"
                  v-if="row.isActive"
                >
                  <div class="inline-flex items-center">
                    {{ row.playerName }}
                    <EvolutionIndicator
                      :evolution="row.evolution"
                      :isActive="true"
                    ></EvolutionIndicator>
                  </div>
                </td>
                <td
                  class="px-0 py-3 text-sm font-medium text-right whitespace-nowrap"
                  :class="row.isActive ? 'text-white' : 'text-gray-500'"
                >
                  {{ row.sessionsCount }}
                </td>
                <td
                  class="py-2 pl-0 pr-3 text-sm text-right whitespace-nowrap"
                  :class="row.isActive ? 'text-white' : 'text-gray-900'"
                >
                  <span
                    class="inline-flex items-center flex-shrink h-6 px-2 text-white rounded-md"
                    :class="{
                      'bg-red-500': row.total < 0,
                      'bg-green-500': row.total >= 0,
                    }"
                    >{{ row.total }} €</span
                  >
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { UserGroupIcon, UsersIcon } from "@vue-hero-icons/solid";
import EvolutionIndicator from "./EvolutionIndicator.vue";
import settings from "@/settings";

export default {
  name: "RankingComponent",
  props: [
    "rankings",
    "previousRankings",
    "activePlayerId",
    "activePlayerIds",
    "seasonRanking",
    "title",
    "loading",
  ],
  components: {
    UserGroupIcon,
    UsersIcon,
    EvolutionIndicator,
  },
  data: () => ({
    forceShowReg: undefined,
  }),
  watch: {
    $route: function() {
      this.forceShowReg = undefined;
    },
  },
  computed: {
    regsRanking: function() {
      if (!Array.isArray(this.rankings)) return [];
      if (this.seasonRanking) {
        return this.rankings.filter(
          (r) =>
            !r.rankingKey.general &&
            r.sessionsCount >= settings.regPlayerMinimumSeasonSessionCount
        );
      } else {
        return this.rankings.filter(
          (r) =>
            r.rankingKey.general &&
            r.sessionsCount >= settings.regPlayerMinimumGeneralSessionCount
        );
      }
    },
    allRanking: function() {
      if (!Array.isArray(this.rankings)) return [];
      return this.rankings.filter(
        (r) => this.seasonRanking !== r.rankingKey.general
      );
    },
    previousRegsRanking: function() {
      if (!Array.isArray(this.previousRankings)) return [];
      if (this.seasonRanking) {
        return this.previousRankings.filter(
          (r) =>
            !r.rankingKey.general &&
            r.sessionsCount >= settings.regPlayerMinimumSeasonSessionCount
        );
      } else {
        return this.previousRankings.filter(
          (r) =>
            r.rankingKey.general &&
            r.sessionsCount >= settings.regPlayerMinimumGeneralSessionCount
        );
      }
    },
    previousAllRanking: function() {
      if (!Array.isArray(this.previousRankings)) return [];
      return this.previousRankings.filter(
        (r) => this.seasonRanking !== r.rankingKey.general
      );
    },
    enoughDataToShowOnlyRegs: function() {
      return this.regsRanking.length > 0;
    },
    showOnlyRegs: function() {
      if (!this.enoughDataToShowOnlyRegs) return false;
      if (this.forceShowReg !== undefined) return this.forceShowReg;
      if (!Array.isArray(this.rankings)) return true;
      const activePlayerRanking = this.rankings.find(
        (r) =>
          r.player.playerId === this.activePlayerId &&
          this.seasonRanking !== r.rankingKey.general
      );
      return (
        !activePlayerRanking ||
        activePlayerRanking.sessionsCount >= this.sessionsCountToBeReg
      );
    },
    sessionsCountToBeReg: function() {
      return this.seasonRanking
        ? settings.regPlayerMinimumSeasonSessionCount
        : settings.regPlayerMinimumGeneralSessionCount;
    },
    table: function() {
      if (!Array.isArray(this.rankings)) return [];
      const rankings = this.showOnlyRegs
        ? [...this.regsRanking]
        : [...this.allRanking];
      let previousRankings = this.showOnlyRegs
        ? [...this.previousRegsRanking]
        : [...this.previousAllRanking];
      previousRankings.sort((r1, r2) => r2.total - r1.total);
      previousRankings = previousRankings.map((r, i) => {
        return {
          rank: i > 0 && previousRankings[i - 1].total === r.total ? i : i + 1,
          playerId: r.player.playerId,
        };
      });
      rankings.sort((r1, r2) => r2.total - r1.total);
      return rankings.map((r, i) => {
        let rank = i > 0 && rankings[i - 1].total === r.total ? i : i + 1;
        const previousRanking = previousRankings.filter(
          (pr) => pr.playerId === r.player.playerId
        );
        const evolution =
          previousRanking.length === 1 ? previousRanking[0].rank - rank : 0;
        return {
          total: r.total,
          rank,
          evolution,
          playerId: r.player.playerId,
          playerName: r.player.firstName,
          sessionsCount: r.sessionsCount,
          isActive: r.player.playerId === this.activePlayerId,
          isPresent:
            this.activePlayerIds &&
            this.activePlayerIds.includes(r.player.playerId),
        };
      });
    },
  },
};
</script>

<style scoped></style>
