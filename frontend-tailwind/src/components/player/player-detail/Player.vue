<template>
  <div v-if="loaded">
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
      <StatCard
        label="Résultat total"
        :stat="playerTotalResult + '€'"
        :change="lastSessionResult"
        :color-class="playerTotalResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
      >
        <CashIcon class="w-6 h-6 text-white"></CashIcon>
      </StatCard>
      <StatCard
        :label="
          'Résultat de ' + (selectedYear === 0 ? yearsPlayed[0] : selectedYear)
        "
        :stat="currentSeasonResult + '€'"
        :color-class="currentSeasonResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
      >
        <PresentationChartLineIcon
          class="w-6 h-6 text-white"
        ></PresentationChartLineIcon>
      </StatCard>
      <StatCard
        label="Participations"
        :stat="player.playerResults.length"
        color-class="bg-green-500"
      >
        <CalendarIcon class="w-6 h-6 text-white"></CalendarIcon>
      </StatCard>
      <StatCard
        label="Premières places"
        :stat="firstPlaces"
        color-class="bg-green-500"
      >
        <AcademicCapIcon class="w-6 h-6 text-white"></AcademicCapIcon>
      </StatCard>
    </dl>
    <dl
      class="grid grid-cols-1 gap-5 mt-5 lg:mt-14 sm:grid-cols-2 lg:grid-cols-4"
    >
      <StatCard
        label="Meilleure session"
        :stat="bestResult + '€'"
        color-class="bg-green-500"
      >
        <LightningBoltIcon class="w-6 h-6 text-white"></LightningBoltIcon>
      </StatCard>
      <StatCard
        label="Pire session"
        :stat="worstResult + '€'"
        color-class="bg-red-500"
      >
        <ShieldExclamationIcon
          class="w-6 h-6 text-white"
        ></ShieldExclamationIcon>
      </StatCard>
      <StatCard
        label="Sessions positives"
        :stat="positiveSessions"
        color-class="bg-green-500"
      >
        <TrendingUpIcon class="w-6 h-6 text-white"></TrendingUpIcon>
      </StatCard>
      <StatCard
        label="Dernières places"
        :stat="lastPlaces"
        color-class="bg-red-500"
      >
        <ChevronDoubleDownIcon
          class="w-6 h-6 text-white"
        ></ChevronDoubleDownIcon>
      </StatCard>
    </dl>
    <ResultChartContainer
      class="mt-7"
      :results="player.playerResults"
      :selected-year="selectedYear"
      :years-played="yearsPlayed"
      @year-selected="yearSelected"
    ></ResultChartContainer>
    <div class="flex space-x-6">
      <div class="w-1/2">
        <h2
          class="p-6 pb-0 pl-3 text-xl font-medium tracking-wide text-gray-600"
        >
          Sessions du joueur<span v-if="selectedYear > 0"
            >&nbsp;en {{ selectedYear }}</span
          >
        </h2>
        <ul class="grid grid-cols-1 gap-5 mt-6 sm:gap-6 sm:grid-cols-2">
          <PlayerSessionCard
            v-for="result in filteredYears"
            :result="result"
            :key="result.session.sessionId"
          ></PlayerSessionCard>
        </ul>
      </div>
      <div class="w-1/2">
        <h2
          class="p-6 pb-0 pl-3 mb-6 text-xl font-medium tracking-wide text-gray-600"
        >
          Classements
        </h2>
        <div class="flex space-x-8">
          <div class="w-1/2">
            <RankingComponent
              :rankings="generalRankings"
              :previous-rankings="previousRankings"
              :active-player-id="player.playerId"
              :season-ranking="false"
              title="Classement général"
            ></RankingComponent>
          </div>
          <div class="w-1/2">
            <RankingComponent
              :rankings="rankings"
              :previous-rankings="previousRankings"
              :active-player-id="player.playerId"
              :season-ranking="true"
              :title="`Classement ${
                selectedYear === 0 ? yearsPlayed[0] : selectedYear
              }`"
            ></RankingComponent>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PlayerService from "../../../services/player-service";
import SessionService from "../../../services/session-service";
import {
  CalendarIcon,
  CashIcon,
  ChevronDoubleDownIcon,
  LightningBoltIcon,
  PresentationChartLineIcon,
  ShieldExclamationIcon,
} from "@vue-hero-icons/outline";
import { AcademicCapIcon } from "@vue-hero-icons/solid";
import ResultChartContainer from "./ResultChartContainer";
import StatCard from "./StatCard";
import PlayerSessionCard from "./PlayerSessionCard";
import RankingComponent from "../../ranking/RankingComponent";

export default {
  name: "Player",
  components: {
    RankingComponent,
    ResultChartContainer,
    AcademicCapIcon,
    CalendarIcon,
    CashIcon,
    ChevronDoubleDownIcon,
    LightningBoltIcon,
    PlayerSessionCard,
    PresentationChartLineIcon,
    ShieldExclamationIcon,
    StatCard,
  },
  data: () => ({
    player: Object,
    loaded: false,
    selectedYear: 0,
    yearsPlayed: [],
    rankings: Array,
    generalRankings: Array,
    previousRankings: Array,
  }),
  computed: {
    filteredYears: function () {
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
    playerTotalResult: function () {
      if (this.player) {
        return PlayerService.getPlayerTotalResult(this.player);
      }
      return 0;
    },
    currentSeasonResult: function () {
      let currentYear = this.selectedYear;
      if (currentYear === 0) {
        currentYear = this.yearsPlayed[0];
      }
      return this.player.playerResults
        .filter((r) => r.session.season.year === currentYear)
        .map((r) => r.result)
        .reduce((acc, curr) => acc + curr, 0);
    },
    lastSessionResult: function () {
      if (this.player.playerResults.length > 0) {
        let results = [...this.player.playerResults];
        return results.sort(
          (r1, r2) => new Date(r2.session.date) - new Date(r1.session.date)
        )[0];
      }
      return undefined;
    },
    firstPlaces: function () {
      return this.player.playerResults.filter((pr) => pr.rank === 1).length;
    },
    lastPlaces: function () {
      return this.player.playerResults.filter((pr) => pr.last).length;
    },
    positiveSessions: function () {
      return this.player.playerResults.filter((pr) => pr.result >= 0).length;
    },
    bestResult: function () {
      return this.player.playerResults
        .map((pr) => pr.result)
        .reduce((acc, cur) => (cur > acc ? cur : acc), -9999);
    },
    worstResult: function () {
      return this.player.playerResults
        .map((pr) => pr.result)
        .reduce((acc, cur) => (cur < acc ? cur : acc), 9999);
    },
  },
  watch: {
    $route: "fetchPlayerData",
    selectedYear: "fetchRankingData",
  },
  methods: {
    fetchPlayerData() {
      let playerId = this.$route.params.id;
      PlayerService.getPlayer(playerId).then((response) => {
        this.player = response.data;
        this.$store.commit("setPageTitle", this.player.firstName);
        const setOfYears = new Set(
          this.player.playerResults.map((pr) => pr.session.season.year)
        );
        this.yearsPlayed = [...setOfYears].sort((y1, y2) => y2 - y1);
        this.selectedYear = [...this.yearsPlayed][0];
        this.loaded = true;
        this.fetchRankingData();
      });
    },
    fetchRankingData() {
      this.rankings = [];
      SessionService.getLatestSessionByYear(
        this.selectedYear === 0 ? this.yearsPlayed[0] : this.selectedYear
      ).then((response) => {
        this.rankings = response.data.rankings;
        if (response.data.previousSession) {
          this.previousRankings = response.data.previousSession.rankings;
        }
      });
      SessionService.getLatestSession().then((response) => {
        this.generalRankings = [...response.data.rankings];
      });
    },
    yearSelected(event, year) {
      this.selectedYear = year;
    },
  },
  mounted() {
    this.fetchPlayerData();
  },
};
</script>
