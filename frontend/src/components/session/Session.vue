<template>
  <div>
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
      <session-winner :results="loading ? [] : results"></session-winner>
      <session-loser :results="loading ? [] : results"></session-loser>
      <session-place :session="loading ? {} : session"></session-place>
      <session-amount :results="loading ? [] : results"></session-amount>
    </dl>
    <div class="grid grid-cols-12 gap-5 mt-14">
      <div
        class="col-span-12"
        :class="showEditControls ? 'lg:col-span-5' : 'lg:col-span-6'"
      >
        <tailwind-card title="Résultat de la session">
          <session-result-chart-container
            :loading="loading"
            :results="session.playerResults"
            :show-buy-ins="false"
          ></session-result-chart-container>
        </tailwind-card>
      </div>
      <div
        class="col-span-12 md:col-span-6"
        :class="showEditControls ? 'lg:col-span-4' : 'lg:col-span-3'"
      >
        <session-result-ranking
          :loading="loading"
          :loading-player-results="loadingPlayerResults"
          :loading-player-buy-ins="loadingPlayerBuyIns"
          :loading-players="loadingSessionRankingLines"
          :edit-mode="showEditControls"
          :results="session.playerResults"
          title="Classement de la session"
          @edit-result="editResult"
          @delete-result="deleteResult"
        ></session-result-ranking>
        <tailwind-button
          v-if="showEditControls"
          class="flex flex-col items-stretch mt-4"
          type="primary"
          size="sm"
          @buttonClick="openAddPlayerDialog()"
        >
          <plus-icon class="w-5 h-5"></plus-icon>
          <span class="pl-1">Ajouter un joueur</span>
        </tailwind-button>
        <tailwind-button
          v-if="showEditControls"
          class="flex flex-col items-stretch mt-4"
          type="alert"
          size="sm"
          @buttonClick="showDeleteModal = true"
        >
          <x-icon class="w-5 h-5"></x-icon>
          <span class="pl-1">Supprimer la session</span>
        </tailwind-button>
        <add-player-panel
          v-if="showEditControls"
          :is-open="addPlayerDialogOpened"
          @close="addPlayerDialogOpened = false"
          @player-clicked="addPlayer"
          :player-list="nonPlayingPlayers"
        ></add-player-panel>
      </div>
      <div class="col-span-12 md:col-span-6 lg:col-span-3">
        <ranking-component
          :loading="loading"
          :rankings="
            !loading
              ? session.rankings.filter((r) => !r.rankingKey.general)
              : []
          "
          :previous-rankings="
            !loading &&
            session.previousSession &&
            session.season.seasonId === session.previousSession.season.seasonId
              ? session.previousSession.rankings.filter(
                  (r) => !r.rankingKey.general
                )
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
        ></ranking-component>
      </div>
    </div>
    <tw-modal
      title="Confirmer la suppression"
      :text="`Êtes-vous certain de vouloir supprimer complètement la session ?`"
      confirm-label="Oui, supprimer"
      cancel-label="Non"
      :loading="deleteLoading"
      :is-open="showDeleteModal"
      @confirm="confirmDeleteSession"
      @cancel="showDeleteModal = false"
    ></tw-modal>
  </div>
</template>

<script>
import SessionService from "../../services/session-service";
import PlayerService from "../../services/player-service";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import { PlusIcon, XIcon } from "@vue-hero-icons/outline";
import RankingComponent from "@/components/ranking/RankingComponent";
import TailwindCard from "@/components/ui/TailwindCard.vue";
import SessionResultChartContainer from "./SessionResultChartContainer.vue";
import SessionResultRanking from "@/components/session/SessionResultRanking.vue";
import TailwindButton from "@/components/ui/TailwindButton";
import AddPlayerPanel from "@/components/session/AddPlayerPanel";
import settings from "@/settings";
import SessionWinner from "@/components/stat-cards/SessionWinner.vue";
import SessionLoser from "@/components/stat-cards/SessionLoser.vue";
import SessionPlace from "@/components/stat-cards/SessionPlace.vue";
import SessionAmount from "@/components/stat-cards/SessionAmount.vue";

export default {
  name: "Session",
  components: {
    AddPlayerPanel,
    PlusIcon,
    RankingComponent,
    TailwindCard,
    SessionResultChartContainer,
    SessionResultRanking,
    TailwindButton,
    XIcon,
    SessionLoser,
    SessionWinner,
    SessionPlace,
    SessionAmount,
  },
  data: () => ({
    session: Object,
    formattedDate: String,
    loading: true,
    deleteLoading: false,
    playerListLoading: true,
    addPlayerDialogOpened: false,
    editSessionPanelOpened: false,
    playerList: [],
    loadingPlayerResults: [],
    loadingPlayerBuyIns: [],
    loadingSessionRankingLines: [],
    showDeleteModal: false,
  }),
  computed: {
    showEditControls: function () {
      return this.$store.state.editMode || settings.forceEditMode;
    },
    results: function () {
      if (this.loading) return [];
      return [...this.session.playerResults].sort(
        (r1, r2) => r2.result - r1.result
      );
    },
    nonPlayingPlayers: function () {
      if (this.loading) return [];
      return this.playerList.filter((p) =>
        this.session.playerResults
          .map((r) => r.player.playerId)
          .every((id) => id !== p.playerId)
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
    openEditSessionPanel: function () {
      this.editSessionPanelOpened = true;
    },
    addPlayer: async function (event) {
      this.addPlayerDialogOpened = false;
      this.loadingSessionRankingLines.push(-1);
      this.session.playerResults.push({
        player: {
          playerId: -1,
        },
        result: 0,
      });
      const response = await SessionService.updatePlayerSessionResult(
        this.session.sessionId,
        event.playerId,
        0,
        1
      );
      this.loadingSessionRankingLines = this.loadingPlayerResults.filter(
        (id) => id != -1
      );
      this.session = response.data;
    },
    deleteResult: async function (event) {
      this.loadingSessionRankingLines.push(event.playerId);
      const response = await SessionService.deletePlayerSessionResult(
        this.session.sessionId,
        event.playerId
      );
      this.session = response.data;
      this.loadingSessionRankingLines = this.loadingPlayerResults.filter(
        (id) => id != event.playerId
      );
    },
    editResult: async function (event) {
      switch (event.type) {
        case "buy-ins":
          this.loadingPlayerBuyIns.push(event.playerId);
          break;
        case "result":
          this.loadingPlayerResults.push(event.playerId);
          break;
      }
      const response = await SessionService.updatePlayerSessionResult(
        this.session.sessionId,
        event.playerId,
        event.result,
        event.buyIns
      );
      switch (event.type) {
        case "buy-ins":
          this.loadingPlayerBuyIns = this.loadingPlayerBuyIns.filter(
            (id) => id != event.playerId
          );
          break;
        case "result":
          this.loadingPlayerResults = this.loadingPlayerResults.filter(
            (id) => id != event.playerId
          );
          break;
      }
      this.session = response.data;
    },
    confirmDeleteSession: async function () {
      this.deleteLoading = true;
      await SessionService.deleteSession(this.session.sessionId);
      this.$router.push("/sessions");
    },
  },
  mounted() {
    this.fetchData();
    this.fetchPlayerList();
  },
};
</script>