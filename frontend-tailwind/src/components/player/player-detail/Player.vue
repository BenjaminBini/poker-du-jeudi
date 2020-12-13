<template lang="pug">
  div(v-if="loaded")
    dl(class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4")
      StatCard(label="Résultat total" :stat="playerTotalResult + '€'" :change="lastSessionResult" :color-class="playerTotalResult >= 0 ? 'bg-green-500' : 'bg-red-400'")
        CashIcon(class="h-6 w-6 text-white")
      StatCard(:label="'Résultat de ' + (selectedYear === 0 ? yearsPlayed[0] : selectedYear)" :stat="currentSeasonResult + '€'" :color-class="currentSeasonResult >= 0 ? 'bg-green-500' : 'bg-red-400'")
        PresentationChartLineIcon(class="h-6 w-6 text-white")
      StatCard(label="Sessions" :stat="player.playerResults.length" color-class="bg-green-500")
        CalendarIcon(class="h-6 w-6 text-white")
      StatCard(label="Premières places" :stat="firstPlaces" color-class="bg-green-500")
        AcademicCapIcon(class="h-6 w-6 text-white")
    dl(class="lg:mt-14 mt-5 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4")
      StatCard(label="Meilleure session" :stat="bestResult + '€'" color-class="bg-green-500")
        LightningBoltIcon(class="h-6 w-6 text-white")
      StatCard(label="Pire session" :stat="worstResult + '€'" color-class="bg-red-400")
        ShieldExclamationIcon(class="h-6 w-6 text-white")
      StatCard(label="Sessions positives" :stat="positiveSessions" color-class="bg-green-500")
        TrendingUpIcon(class="h-6 w-6 text-white")
      StatCard(label="Dernières places" :stat="lastPlaces" color-class="bg-red-400")
        ChevronDoubleDownIcon(class="h-6 w-6 text-white")
    ResultChartContainer(:results="player.playerResults" :selected-year="selectedYear" :years-played="yearsPlayed" @year-selected="yearSelected" class="mt-7")

    div(class="flex space-x-6")
      div(class="w-1/2")
        h2(class="p-6 pl-3 pb-0 text-gray-600 text-xl font-medium tracking-wide") Sessions du joueur
          span(v-if="selectedYear > 0") &nbsp;en {{selectedYear}}
        ul(class="mt-6 grid grid-cols-1 gap-5 sm:gap-6 sm:grid-cols-2")
          PlayerSessionCard(v-for="result in filteredYears" :result="result" :key="result.session.sessionId")
      div(class="w-1/2 flex space-x-6")
        div(class="w-1/2")
          h2(class="p-6 pl-3 pb-0 text-gray-600 text-xl font-medium tracking-wide") Classement général
          RankingComponent(:rankings="generalRankings" :previous-rankings="previousRankings" :active-player-id="player.playerId" :season-ranking="false")

        div(class="w-1/2")
          h2(class="p-6 pl-3 pb-0 text-gray-600 text-xl font-medium tracking-wide") Classement {{selectedYear === 0 ? yearsPlayed[0] : selectedYear}}
          RankingComponent(:rankings="rankings" :previous-rankings="previousRankings" :active-player-id="player.playerId" :season-ranking="true")

</template>

<script>
import PlayerService from "../../../services/player-service";
import SessionService from "../../../services/session-service";
import { CalendarIcon, CashIcon, ChevronDoubleDownIcon, LightningBoltIcon,
  PresentationChartLineIcon, ShieldExclamationIcon, TrendingDownIcon, TrendingUpIcon } from "@vue-hero-icons/outline"
import { AcademicCapIcon } from "@vue-hero-icons/solid"
import ResultChart from "./ResultChart";
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
    TrendingDownIcon,
    TrendingUpIcon,
    ResultChart,
    StatCard
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
    filteredYears: function() {
      return [...this.player.playerResults]
        .filter(r => r.session.season.year === this.selectedYear || this.selectedYear === 0)
        .sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date));
    },
    playerTotalResult: function() {
      if (this.player) {
        return PlayerService.getPlayerTotalResult(this.player);
      }
      return 0;
    },
    currentSeasonResult: function() {
      let currentYear = this.selectedYear;
      if (currentYear === 0) {
        currentYear = this.yearsPlayed[0];
      }
      return this.player.playerResults
          .filter(r => r.session.season.year === currentYear)
          .map(r => r.result)
          .reduce((acc, curr) => acc + curr, 0);
    },
    lastSessionResult: function() {
      if (this.player.playerResults.length > 0) {
        let results = [...this.player.playerResults];
        return results.sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date))[0];
      }
      return undefined;
    },
    firstPlaces: function() {
      return this.player.playerResults.filter(pr => pr.rank === 1).length;
    },
    lastPlaces: function() {
      return this.player.playerResults.filter(pr => pr.last).length;
    },
    positiveSessions: function() {
      return this.player.playerResults.filter(pr => pr.result >= 0).length;
    },
    bestResult: function() {
      return this.player.playerResults.map(pr => pr.result).reduce((acc, cur) => cur > acc ? cur : acc, -9999);
    },
    worstResult: function() {
      return this.player.playerResults.map(pr => pr.result).reduce((acc, cur) => cur < acc ? cur : acc, 9999);
    }
  },
  watch: {
    '$route': 'fetchPlayerData',
    'selectedYear': 'fetchRankingData',
  },
  methods: {
    fetchPlayerData() {
      let playerId = this.$route.params.id;
      PlayerService.getPlayer(playerId).then(response => {
        this.player = response.data;
        this.$store.commit('setPageTitle', this.player.firstName);
        const setOfYears = new Set(this.player.playerResults.map(pr => pr.session.season.year));
        this.yearsPlayed = [...setOfYears].sort((y1, y2) => y2 - y1);
        this.selectedYear = [...this.yearsPlayed][0];
        this.loaded = true;
        this.fetchRankingData();
      });
    },
    fetchRankingData() {
      this.rankings = [];
      SessionService.getLatestSessionByYear(this.selectedYear === 0 ? this.yearsPlayed[0] : this.selectedYear).then(response => {
        this.rankings = response.data.rankings;
        if (response.data.previousSession) {
          this.previousRankings = response.data.previousSession.rankings;
        }
      });
      SessionService.getLatestSession().then(response => {
        this.generalRankings = [...response.data.rankings];
      });
    },
    yearSelected(event, year) {
      this.selectedYear = year;
    },
  },
  mounted() {
    this.fetchPlayerData();
  }
}
</script>

<style scoped>

</style>