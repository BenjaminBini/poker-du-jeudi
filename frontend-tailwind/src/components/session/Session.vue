<template>
  <div>
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
      <stat-card
        label="Vainqueur"
        :loading="loading"
        :stat="!loading ? results[0].player.firstName : ''"
        :change="!loading ? results[0] : ''"
        color-class="bg-green-500"
      >
        <StarIcon class="w-6 h-6 text-white"></StarIcon>
      </stat-card>
      <stat-card
        label="Perdant"
        :loading="loading"
        :stat="!loading ? results[results.length - 1].player.firstName : ''"
        :change="!loading ? results[results.length - 1] : ''"
        color-class="bg-red-500"
      >
        <ChevronDoubleDownIcon
          class="w-6 h-6 text-white"
        ></ChevronDoubleDownIcon>
      </stat-card>
      <stat-card
        label="Lieu"
        :loading="loading"
        :stat="!loading ? session.place.name : ''"
        color-class="bg-green-500"
      >
        <HomeIcon class="w-6 h-6 text-white"></HomeIcon>
      </stat-card>
      <stat-card
        label="Argent misé"
        v-if="loading || results[0].buyIns > 0"
        :loading="loading"
        :stat="
          !loading
            ? results.map((r) => r.buyIns).reduce((a, b) => a + b) * 10 + ' €'
            : ''
        "
        color-class="bg-green-500"
      >
        <CashIcon class="w-6 h-6 text-white"></CashIcon>
      </stat-card>
    </dl>
    <div class="grid grid-cols-1 gap-5 mt-14 sm:grid-cols-4">
      <div class="sm:col-span-4 lg:col-span-2">
        <tailwind-card title="Résultat de la session">
          <session-result-chart-container
            :loading="loading"
            :results="session.playerResults"
          ></session-result-chart-container>
        </tailwind-card>
      </div>
      <div class="sm:col-span-2 lg:col-span-1">
        <session-result-ranking
          :loading="loading"
          :results="session.playerResults"
          title="Classement de la session"
        ></session-result-ranking>
        <tailwind-button
          class="flex flex-col items-stretch mt-4"
          type="primary"
          size="sm"
          @buttonClick="openAddPlayerDialog()"
        >
          Ajouter un joueur
        </tailwind-button>
        <add-player-panel
          :is-open="addPlayerDialogOpened"
          @close="addPlayerDialogOpened = false"
          :player-list="playerList"
        ></add-player-panel>
      </div>
      <div class="sm:col-span-2 lg:col-span-1">
        <RankingComponent
          :loading="loading"
          :rankings="
            !loading
              ? session.rankings.filter((r) => !r.rankingKey.general)
              : []
          "
          :active-player-ids="
            !loading
              ? session.playerResults.map((pr) => pr.player.playerId)
              : []
          "
          :season-ranking="true"
          :title="
            !loading
              ? `Classement ${session.season.name}`
              : 'Classement de la saison'
          "
        ></RankingComponent>
      </div>
    </div>
  </div>
</template>

<script>
import SessionService from "../../services/session-service";
import PlayerService from "../../services/player-service";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import StatCard from "@/components/player/player-detail/StatCard";
import {
  CashIcon,
  ChevronDoubleDownIcon,
  HomeIcon,
  StarIcon,
} from "@vue-hero-icons/outline";
import RankingComponent from "@/components/ranking/RankingComponent";
import TailwindCard from "@/components/ui/TailwindCard.vue";
import SessionResultChartContainer from "./SessionResultChartContainer.vue";
import SessionResultRanking from "@/components/session/SessionResultRanking.vue";
import TailwindButton from "@/components/ui/TailwindButton";
import AddPlayerPanel from "@/components/session/AddPlayerPanel";

export default {
  name: "Session",
  components: {
    AddPlayerPanel,
    CashIcon,
    ChevronDoubleDownIcon,
    HomeIcon,
    RankingComponent,
    StarIcon,
    StatCard,
    TailwindCard,
    SessionResultChartContainer,
    SessionResultRanking,
    TailwindButton,
  },
  data: () => ({
    session: Object,
    formattedDate: String,
    loading: true,
    playerListLoading: true,
    addPlayerDialogOpened: false,
    playerList: [],
  }),
  computed: {
    results: function () {
      if (this.loading) return [];
      return [...this.session.playerResults].sort(
        (r1, r2) => r2.result - r1.result
      );
    },
  },
  watch: {
    $route: "fetchData",
  },
  methods: {
    async fetchData() {
      this.loading = true;
      let sessionId = this.$route.params.id;
      const response = await SessionService.getSession(sessionId);
      this.session = response.data;
      this.formattedDate = format(
        new Date(this.session.date),
        "EEEE d MMMM yyyy",
        { locale: fr }
      );
      this.$store.commit("setPageTitle", "Session du " + this.formattedDate);

      let actions = [];
      if (this.session.previousSession) {
        actions = [
          ...actions,
          {
            target: `/sessions/${this.session.previousSession.sessionId}`,
            label: "Session précédente",
          },
        ];
      }
      if (this.session.nextSession) {
        actions = [
          ...actions,
          {
            target: `/sessions/${this.session.nextSession.sessionId}`,
            label: "Session suivante",
          },
        ];
      }
      this.$store.commit("setPageActions", actions);
      this.loading = false;
    },
    fetchPlayerList: async function () {
      this.playerListLoading = true;
      const response = await PlayerService.getPlayers();
      this.playerList = response.data;
    },
    openAddPlayerDialog: function () {
      this.addPlayerDialogOpened = true;
    },
  },
  mounted() {
    this.fetchData();
    this.fetchPlayerList();
  },
};
</script>