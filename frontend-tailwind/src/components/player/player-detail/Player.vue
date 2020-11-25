<template lang="pug">
  div(v-if="loaded")
    dl(class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4")
      StatCard(label="Résultat total" :stat="playerTotalResult + '€'" :change="lastSessionResult")
        CashIcon(class="h-6 w-6 text-white")
      StatCard(label="Résultat de la saison" :stat="currentSeasonResult + '€'")
        PresentationChartLineIcon(class="h-6 w-6 text-white")
      StatCard(label="Sessions" :stat="player.playerResults.length")
        CalendarIcon(class="h-6 w-6 text-white")
      StatCard(label="Premières places" :stat="firstPlaces")
        AcademicCapIcon(class="h-6 w-6 text-white")
    dl(class="mt-14 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4")
      StatCard(label="Meilleure session" :stat="bestResult + '€'")
        LightningBoltIcon(class="h-6 w-6 text-white")
      StatCard(label="Pire session" :stat="worstResult + '€'")
        ShieldExclamationIcon(class="h-6 w-6 text-white")
      StatCard(label="Sessions positives" :stat="positiveSessions")
        TrendingUpIcon(class="h-6 w-6 text-white")
      StatCard(label="Dernières places" :stat="lastPlaces")
        ChevronDoubleDownIcon(class="h-6 w-6 text-white")
    ResultChartContainer(:results="player.playerResults" class="mt-7")
</template>

<script>
import PlayerService from "../../../services/player-service";
import { CalendarIcon, CashIcon, ChevronDoubleDownIcon, LightningBoltIcon,
  PresentationChartLineIcon, ShieldExclamationIcon, TrendingDownIcon, TrendingUpIcon } from "@vue-hero-icons/outline"
import { AcademicCapIcon } from "@vue-hero-icons/solid"
import ResultChart from "./ResultChart";
import ResultChartContainer from "./ResultChartContainer";
import StatCard from "./StatCard";

export default {
  name: "Player",
  components: {
    ResultChartContainer,
    AcademicCapIcon,
    CalendarIcon,
    CashIcon,
    ChevronDoubleDownIcon,
    LightningBoltIcon,
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