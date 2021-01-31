<template>
  <div>
    <div v-if="loading" class="flex items-center justify-center py-48 bg-white">
      <tw-spinner></tw-spinner>
    </div>
    <session-result-chart
      v-if="!loading"
      class="relative"
      :results="results"
      :showBuyIns="showBuyIns"
      style="height: 404px"
    ></session-result-chart>
  </div>
</template>

<script>
import SessionResultChart from "./charts/SessionResultChart.vue";
export default {
  name: "SessionResultChartContainer",
  components: { SessionResultChart },
  props: ["results", "loading", "showBuyIns"],
};
</script>