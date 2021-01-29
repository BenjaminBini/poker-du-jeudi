<template>
  <div
    class="grid w-full grid-cols-1 space-y-6 lg:grid-cols-3 xl:grid-cols-4 lg:space-y-0 lg:space-x-6"
  >
    <div class="xl:col-span-2">
      <tailwind-card
        :title="`Dernière session ${session ? ' - ' + formattedDate : ''}`"
        :to="session ? `/sessions/${session.sessionId}` : ''"
      >
        <session-result-chart-container
          :loading="loading"
          :results="session ? session.playerResults : []"
        ></session-result-chart-container>
      </tailwind-card>
    </div>
    <div>
      <h2 class="pb-0 text-xl font-medium tracking-wide text-white"></h2>
      <RankingComponent
        :loading="loading"
        :rankings="rankings"
        :season-ranking="true"
        :title="session ? 'Classement' : `Classement ${season}`"
      ></RankingComponent>
    </div>
    <div>
      <RankingComponent
        :loading="loading"
        :rankings="rankings"
        :season-ranking="false"
        title="Classement général"
      ></RankingComponent>
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

export default {
  name: "Home",
  components: {
    RankingComponent,
    SessionResultChartContainer,
    TailwindCard,
  },
  data: () => ({
    rankings: Array,
    season: "",
    session: undefined,
    loading: true,
  }),
  computed: {
    formattedDate: function () {
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
  mounted: async function () {
    const response = await SessionService.getLatestSession();
    this.session = response.data;
    this.season = response.data.season.name;
    this.rankings = [...response.data.rankings];
    this.loading = false;
  },
};
</script>
