<template>
  <div
    class="w-full space-y-6 sm:space-y-0 sm:space-x-6 sm:flex lg:w-1/2"
    v-if="season.length &gt; 0"
  >
    <div class="w-full sm:w-1/2">
      <RankingComponent
        :rankings="rankings"
        :season-ranking="false"
        title="Classement général"
      ></RankingComponent>
    </div>
    <div class="w-full sm:w-1/2">
      <h2 class="pb-0 text-xl font-medium tracking-wide text-white"></h2>
      <RankingComponent
        :rankings="rankings"
        :season-ranking="true"
        :title="`Classement ${season}`"
      ></RankingComponent>
    </div>
  </div>
</template>

<script>
import SessionService from "@/services/session-service";
import RankingComponent from "@/components/ranking/RankingComponent";

export default {
  name: "Home",
  components: {
    RankingComponent,
  },
  data: () => ({
    rankings: Array,
    season: "",
  }),
  mounted: async function () {
    const response = await SessionService.getLatestSession();
    this.season = response.data.season.name;
    this.rankings = [...response.data.rankings];
  },
};
</script>
