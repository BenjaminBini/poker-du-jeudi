<template>
  <div
    class="grid w-full grid-cols-1 space-y-6 lg:grid-cols-3 xl:grid-cols-4 lg:space-y-0 lg:space-x-6"
  >
    <div class="space-y-4 xl:col-span-2">
      <tailwind-card
        :title="`Dernière session ${session ? ' - ' + formattedDate : ''}`"
        :to="session ? `/sessions/${session.sessionId}` : ''"
      >
        <session-result-chart-container
          :loading="loading"
          :results="session ? session.playerResults : []"
          :show-buy-ins="false"
        ></session-result-chart-container>
      </tailwind-card>
      <div
        class="grid grid-cols-1 space-x-0 space-y-4 sm:space-y-0 sm:space-x-4 sm:grid-cols-2 xl:space-x-4 lg:grid-cols-1 lg:space-y-4 lg:space-x-0 xl:grid-cols-2 xl:space-y-0"
      >
        <session-winner :results="results"></session-winner>
        <session-loser :results="results"></session-loser>
      </div>
    </div>
    <div>
      <h2 class="pb-0 text-xl font-medium tracking-wide text-white"></h2>
      <ranking-component
        :loading="loading"
        :rankings="rankings"
        :season-ranking="true"
        :title="`Classement ${season}`"
        :previous-rankings="
          session &&
          session.previousSession &&
          session.season.seasonId === session.previousSession.season.seasonId
            ? session.previousSession.rankings.filter(
                (r) => !r.rankingKey.general
              )
            : []
        "
      ></ranking-component>
    </div>
    <div>
      <ranking-component
        :loading="loading"
        :rankings="rankings"
        :previous-rankings="
          session &&
          session.previousSession &&
          session.season.seasonId === session.previousSession.season.seasonId
            ? session.previousSession.rankings.filter(
                (r) => r.rankingKey.general
              )
            : []
        "
        :season-ranking="false"
        title="Classement général"
      ></ranking-component>
    </div>
  </div>
</template>

<script>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import SessionResultChartContainer from "@/components/session/SessionResultChartContainer.vue";
import SessionService from "@/services/session-service";
import RankingComponent from "@/components/ranking/RankingComponent";
import TailwindCard from "@/components/ui/TailwindCard.vue";
import SessionWinner from "@/components/stat-cards/SessionWinner.vue";
import SessionLoser from "@/components/stat-cards/SessionLoser.vue";

export default {
  name: "Home",
  components: {
    RankingComponent,
    SessionResultChartContainer,
    TailwindCard,
    SessionWinner,
    SessionLoser,
  },
  data: () => ({
    rankings: Array,
    season: "",
    session: undefined,
    loading: true,
  }),
  computed: {
    results: function() {
      if (this.loading) return [];
      return [...this.session.playerResults].sort(
        (r1, r2) => r2.result - r1.result
      );
    },
    formattedDate: function() {
      if (!this.session) return;
      const formattedDate = format(
        new Date(this.session.date),
        "EEEE d MMMM yyyy",
        {
          locale: fr,
        }
      );
      return formattedDate.charAt(0).toUpperCase() + formattedDate.slice(1);
    },
  },
  mounted: async function() {
    const response = await SessionService.getLatestSession();
    this.session = response.data;
    this.season = response.data.season.name;
    this.rankings = [...response.data.rankings];
    this.loading = false;
  },
};
</script>
