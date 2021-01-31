<template>
  <stat-card
    label="Sessions positives"
    :loading="loading"
    :stat="`${positiveSessions} (${Math.ceil(
      (positiveSessions / results.length) * 100
    )}%)`"
    :neutral-badge="year"
    color-class="bg-green-500"
  >
    <trending-up-icon class="w-6 h-6 text-white"></trending-up-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { TrendingUpIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    TrendingUpIcon,
    StatCard,
  },
  props: {
    results: Array,
    year: Number,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    positiveSessions: function () {
      if (this.loading) return 0;
      return this.results.filter((pr) => pr.result >= 0).length;
    },
  },
};
</script>

<style>
</style>