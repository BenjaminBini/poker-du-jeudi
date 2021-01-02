<template>
  <div>
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
      <StatCard
        label="Résultat total"
        :loading="playerLoading"
        :stat="playerTotalResult + '€'"
        :change="lastSessionResult"
        :color-class="playerTotalResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
      >
        <CashIcon class="w-6 h-6 text-white"></CashIcon>
      </StatCard>
      <StatCard
        label="Résultat"
        :loading="playerLoading"
        :stat="currentSeasonResult + '€'"
        :color-class="currentSeasonResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
        :neutral-badge="selectedYear"
      >
        <PresentationChartLineIcon
          class="w-6 h-6 text-white"
        ></PresentationChartLineIcon>
      </StatCard>
      <StatCard
        label="Participations"
        :loading="playerLoading"
        :stat="filteredResults.length"
        color-class="bg-green-500"
        :neutralBadge="selectedYear"
      >
        <CalendarIcon class="w-6 h-6 text-white"></CalendarIcon>
      </StatCard>
      <StatCard
        label="Premières places"
        :loading="playerLoading"
        :stat="`${firstPlaces} (${Math.ceil(
          (firstPlaces / filteredResults.length) * 100
        )}%)`"
        color-class="bg-green-500"
        :neutral-badge="selectedYear"
      >
        <AcademicCapIcon class="w-6 h-6 text-white"></AcademicCapIcon>
      </StatCard>
    </dl>
    <dl
      class="grid grid-cols-1 gap-5 mt-5 lg:mt-14 sm:grid-cols-2 lg:grid-cols-4"
    >
      <StatCard
        label="Meilleure session"
        :loading="playerLoading"
        :stat="bestResult.result + '€'"
        color-class="bg-green-500"
        :neutral-badge="bestResult.sessionDate"
      >
        <LightningBoltIcon class="w-6 h-6 text-white"></LightningBoltIcon>
      </StatCard>
      <StatCard
        label="Pire session"
        :loading="playerLoading"
        :stat="worstResult.result + '€'"
        color-class="bg-red-500"
        :neutral-badge="worstResult.sessionDate"
      >
        <ShieldExclamationIcon
          class="w-6 h-6 text-white"
        ></ShieldExclamationIcon>
      </StatCard>
      <StatCard
        label="Sessions positives"
        :loading="playerLoading"
        :stat="
          this.player.playerResults
            ? `${positiveSessions} (${Math.ceil(
                (positiveSessions / filteredResults.length) * 100
              )}%)`
            : ''
        "
        :neutral-badge="selectedYear"
        color-class="bg-green-500"
      >
        <TrendingUpIcon class="w-6 h-6 text-white"></TrendingUpIcon>
      </StatCard>
      <StatCard
        label="Dernières places"
        :loading="playerLoading"
        :stat="`${lastPlaces} (${Math.ceil(
          (lastPlaces / filteredResults.length) * 100
        )}%)`"
        color-class="bg-red-500"
        :neutral-badge="selectedYear"
      >
        <ChevronDoubleDownIcon
          class="w-6 h-6 text-white"
        ></ChevronDoubleDownIcon>
      </StatCard>
    </dl>
    <ResultChartContainer
      class="mt-7"
      :loading="playerLoading"
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
        <div
          class="grid grid-cols-1 gap-5 mt-6 sm:gap-6 sm:grid-cols-2"
          v-if="playerLoading"
        >
          <PlayerSessionCard :loading="true"></PlayerSessionCard>
        </div>
        <div
          class="grid grid-cols-1 gap-5 mt-6 sm:gap-6 sm:grid-cols-2"
          v-if="!playerLoading"
        >
          <PlayerSessionCard
            v-for="result in filteredYears"
            :result="result"
            :key="result.session.sessionId"
          ></PlayerSessionCard>
        </div>
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
              :loading="generalRankingsLoading"
              :rankings="generalRankings"
              :previous-rankings="previousRankings"
              :active-player-id="player.playerId"
              :season-ranking="false"
              title="Classement général"
            ></RankingComponent>
          </div>
          <div class="w-1/2">
            <RankingComponent
              :loading="seasonRankingsLoading"
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
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import PlayerService from "../../../services/player-service";
import SessionService from "../../../services/session-service";
import {
  CalendarIcon,
  CashIcon,
  ChevronDoubleDownIcon,
  LightningBoltIcon,
  PresentationChartLineIcon,
  ShieldExclamationIcon,
  TrendingUpIcon,
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
    TrendingUpIcon,
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
    filteredYears: function () {
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
    playerTotalResult: function () {
      if (this.player) {
        return PlayerService.getPlayerTotalResult(this.player);
      }
      return 0;
    },
    currentSeasonResult: function () {
      if (!this.player.playerResults) return [];
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
      if (this.player.playerResults && this.player.playerResults.length > 0) {
        let results = [...this.player.playerResults];
        return results.sort(
          (r1, r2) => new Date(r2.session.date) - new Date(r1.session.date)
        )[0];
      }
      return undefined;
    },
    firstPlaces: function () {
      if (!this.player.playerResults) return 0;
      return this.filteredResults.filter((pr) => pr.rank === 1).length;
    },
    lastPlaces: function () {
      if (!this.player.playerResults) return 0;
      return this.filteredResults.filter((pr) => pr.last).length;
    },
    positiveSessions: function () {
      if (!this.player.playerResults) return 0;
      return this.filteredResults.filter((pr) => pr.result >= 0).length;
    },
    bestResult: function () {
      if (!this.player.playerResults) return 0;
      let bestResult = this.player.playerResults.reduce((acc, cur) =>
        cur.result > acc.result ? cur : acc
      );

      bestResult.sessionDate = format(
        new Date(bestResult.session.date),
        "dd/MM/yyyy",
        {
          locale: fr,
        }
      );
      return bestResult;
    },
    worstResult: function () {
      if (!this.player.playerResults) return 0;
      let worstResult = this.player.playerResults.reduce((acc, cur) =>
        cur.result < acc.result ? cur : acc
      );
      worstResult.sessionDate = format(
        new Date(worstResult.session.date),
        "dd/MM/yyyy",
        {
          locale: fr,
        }
      );
      return worstResult;
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
      this.selectedYear = [...this.yearsPlayed][0];
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
    yearSelected(event, year) {
      this.selectedYear = year;
    },
  },
  mounted() {
    this.fetchPlayerData();
  },
};
</script>
