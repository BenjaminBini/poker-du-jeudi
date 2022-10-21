<template>
  <div>
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 xl:grid-cols-4">
      <player-total-result
        :results="playerLoading ? [] : player.playerResults"
      ></player-total-result>
      <player-season-result
        :results="playerLoading ? [] : player.playerResults"
        :year="selectedYear"
        :last-year-played="yearsPlayed[0]"
      ></player-season-result>
      <player-participations
        :results="filteredResults"
        :year="selectedYear"
      ></player-participations>
      <player-first-places
        :results="filteredResults"
        :year="selectedYear"
      ></player-first-places>
    </dl>
    <dl
      class="grid grid-cols-1 gap-5 mt-5 xl:mt-14 sm:grid-cols-2 xl:grid-cols-4"
    >
      <player-best-session
        :results="playerLoading ? [] : player.playerResults"
      ></player-best-session>
      <player-worst-session
        :results="playerLoading ? [] : player.playerResults"
      ></player-worst-session>
      <player-positive-sessions
        :results="filteredResults"
        :year="selectedYear"
      ></player-positive-sessions>
      <player-last-places
        :results="filteredResults"
        :year="selectedYear"
      ></player-last-places>
    </dl>
    <result-chart-container
      class="mt-7"
      :loading="playerLoading"
      :results="player.playerResults"
      :selected-year="selectedYear"
      :years-played="yearsPlayed"
      @year-selected="yearSelected"
    ></result-chart-container>
    <div class="lg:space-x-6 lg:flex">
      <div class="lg:w-1/2">
        <h2
          class="p-6 pb-0 pl-3 mb-6 text-xl font-medium tracking-wide text-gray-600"
        >
          Classements
        </h2>
        <div class="space-y-8 sm:flex sm:space-x-8 sm:space-y-0">
          <div class="sm:w-1/2">
            <ranking-component
              :loading="generalRankingsLoading"
              :rankings="generalRankings"
              :previous-rankings="previousRankings"
              :active-player-id="player.playerId"
              :season-ranking="false"
              title="Classement général"
            ></ranking-component>
          </div>
          <div class="sm:w-1/2">
            <ranking-component
              :loading="seasonRankingsLoading"
              :rankings="rankings"
              :previous-rankings="previousRankings"
              :active-player-id="player.playerId"
              :season-ranking="true"
              :title="`Classement ${
                selectedYear === 0 ? yearsPlayed[0] : selectedYear
              }`"
            ></ranking-component>
          </div>
        </div>
      </div>
      <div class="lg:w-1/2">
        <h2
          class="p-6 pb-0 pl-3 text-xl font-medium tracking-wide text-gray-600"
        >
          Sessions du joueur<span v-if="selectedYear > 0"
            >&nbsp;en {{ selectedYear }}</span
          >
        </h2>
        <div
          class="grid grid-cols-1 gap-5 mt-6 sm:gap-6 sm:grid-cols-2"
          v-if="playerLoading"
        >
          <player-session-card :loading="true"></player-session-card>
        </div>
        <div
          class="grid grid-cols-1 gap-5 mt-6 sm:gap-6 sm:grid-cols-2"
          v-if="!playerLoading"
        >
          <player-session-card
            v-for="result in showAll
              ? sortedResults
              : sortedResults.slice(0, 12)"
            :result="result"
            :key="result.session.sessionId"
          ></player-session-card>
        </div>
        <div
          class="relative mt-6"
          v-if="!playerLoading && filteredResults.length > 12"
        >
          <div class="absolute inset-0 flex items-center" aria-hidden="true">
            <div class="w-full border-t border-gray-300"></div>
          </div>
          <div class="relative flex justify-center">
            <button
              type="button"
              @click="showAll = !showAll"
              class="inline-flex items-center shadow-sm px-4 py-1.5 border border-gray-300 text-sm leading-5 font-medium rounded-full text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              <!-- Heroicon name: plus-sm -->
              <plus-sm-icon
                :is="showAll ? 'minus-sm-icon' : 'plus-sm-icon'"
                class="-ml-1.5 mr-1 h-5 w-5 text-gray-400"
              ></plus-sm-icon>
              <span>{{ showAll ? "Voir moins" : "Voir tout" }}</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PlayerService from "../../../services/player-service";
import SessionService from "../../../services/session-service";
import ResultChartContainer from "./ResultChartContainer";
import PlayerSessionCard from "./PlayerSessionCard";
import RankingComponent from "../../ranking/RankingComponent";
import PlayerTotalResult from "@/components/stat-cards/PlayerTotalResult.vue";
import PlayerSeasonResult from "@/components/stat-cards/PlayerSeasonResult.vue";
import PlayerParticipations from "@/components/stat-cards/PlayerParticipations.vue";
import PlayerFirstPlaces from "@/components/stat-cards/PlayerFirstPlaces.vue";
import PlayerBestSession from "@/components/stat-cards/PlayerBestSession.vue";
import PlayerWorstSession from "@/components/stat-cards/PlayerWorstSession.vue";
import PlayerPositiveSessions from "@/components/stat-cards/PlayerPositiveSessions.vue";
import PlayerLastPlaces from "@/components/stat-cards/PlayerLastPlaces.vue";
import { MinusSmIcon, PlusSmIcon } from "@vue-hero-icons/outline";

export default {
  name: "Player",
  components: {
    RankingComponent,
    ResultChartContainer,
    PlayerSessionCard,
    PlayerTotalResult,
    PlayerSeasonResult,
    PlayerParticipations,
    PlayerFirstPlaces,
    PlayerBestSession,
    PlayerWorstSession,
    PlayerPositiveSessions,
    PlayerLastPlaces,
    PlusSmIcon,
    MinusSmIcon,
  },
  data: () => ({
    player: Object,
    playerLoading: false,
    rankingLoading: false,
    selectedYear: 0,
    yearsPlayed: [],
    rankings: Array,
    generalRankings: Array,
    previousRankings: Array,
    generalRankingsLoading: false,
    seasonRankingsLoading: false,
    showAll: false,
  }),
  computed: {
    filteredResults: function () {
      if (!this.player.playerResults) {
        return [];
      }
      return this.player.playerResults.filter(
        (pr) =>
          pr.session.season.year === this.selectedYear ||
          this.selectedYear === 0
      );
    },
    sortedResults: function () {
      if (!this.player.playerResults) return [];
      return [...this.player.playerResults]
        .filter(
          (r) =>
            r.session.season.year === this.selectedYear ||
            this.selectedYear === 0
        )
        .sort(
          (r1, r2) => new Date(r2.session.date) - new Date(r1.session.date)
        );
    },
  },
  watch: {
    $route: "fetchPlayerData",
    selectedYear: "fetchSeasonRanking",
  },
  methods: {
    async fetchPlayerData() {
      this.playerLoading = true;
      this.generalRankingsLoading = true;
      this.seasonRankingsLoading = true;
      let playerId = this.$route.params.id;
      const response = await PlayerService.getPlayer(playerId);
      this.player = response.data;
      this.$store.commit("setPageTitle", this.player.firstName);
      const setOfYears = new Set(
        this.player.playerResults.map((pr) => pr.session.season.year)
      );
      this.yearsPlayed = [...setOfYears].sort((y1, y2) => y2 - y1);
      this.playerLoading = false;
      this.fetchGeneralRanking();
      this.fetchSeasonRanking();
    },
    async fetchGeneralRanking() {
      this.generalRankingsLoading = true;
      const latestSessionResponse = await SessionService.getLatestSession();
      this.generalRankings = [...latestSessionResponse.data.rankings];
      this.generalRankingsLoading = false;
    },
    async fetchSeasonRanking() {
      this.seasonRankingsLoading = true;
      this.rankings = [];
      const latestSessionByYearResponse = await SessionService.getLatestSessionByYear(
        this.selectedYear === 0 ? this.yearsPlayed[0] : this.selectedYear
      );
      this.rankings = latestSessionByYearResponse.data.rankings;
      if (latestSessionByYearResponse.data.previousSession) {
        this.previousRankings =
          latestSessionByYearResponse.data.previousSession.rankings;
      }
      this.seasonRankingsLoading = false;
    },
    yearSelected(_, year) {
      this.selectedYear = year;
    },
  },
  mounted() {
    this.fetchPlayerData();
  },
};
</script>
