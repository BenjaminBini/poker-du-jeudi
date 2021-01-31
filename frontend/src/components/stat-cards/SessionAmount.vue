<template>
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
  },
};
</script>

<style>
</style>