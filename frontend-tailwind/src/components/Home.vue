<template lang="pug">
  div(v-if="season.length > 0" class="w-1/2 flex space-x-6")
    div(class="w-1/2")
      RankingComponent(:rankings="rankings" :season-ranking="false" title="Classement général")

    div(class="w-1/2")
      h2(class="pl-3 pb-0 text-white text-xl font-medium tracking-wide")
      RankingComponent(:rankings="rankings" :season-ranking="true" :title="`Classement ${season}`")
</template>

<script>
import SessionService from "@/services/session-service";
import RankingComponent from "@/components/ranking/RankingComponent";

export default {
  name: "Home",
  components: {
    RankingComponent
  },
  data: () => ({
    rankings: Array,
    season: ""
  }),
  mounted: async function () {
    const response = await SessionService.getLatestSession();
    this.season = response.data.season.name;
    this.rankings = [...response.data.rankings];
  }
}
</script>

<style scoped>

</style>