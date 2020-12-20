<template>
  <div class="flex w-1/2 space-x-6" v-if="season.length &gt; 0">
    <div class="w-1/2">
      <RankingComponent
        :rankings="rankings"
        :season-ranking="false"
        title="Classement général"
      ></RankingComponent>
    </div>
    <div class="w-1/2">
      <h2 class="pb-0 pl-3 text-xl font-medium tracking-wide text-white"></h2>
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
