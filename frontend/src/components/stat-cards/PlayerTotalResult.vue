<template>
  <stat-card
    label="Résultat total"
    :loading="loading"
    :stat="playerTotalResult + '€'"
    :change="lastSessionResult"
    :color-class="playerTotalResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
  >
    <cash-icon class="w-6 h-6 text-white"></cash-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { CashIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    CashIcon,
    StatCard,
  },
  props: {
    results: Array,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    playerTotalResult: function () {
      if (this.loading) return 0;
      return this.results
        .map((pr) => pr.result)
        .reduce((acc, curr) => acc + curr);
    },
    lastSessionResult: function () {
      if (this.loading) return 0;
      let resultsCopy = [...this.results];
      return resultsCopy.sort(
        (r1, r2) => new Date(r2.session.date) - new Date(r1.session.date)
      )[0];
    },
  },
};
</script>

<style>
</style>