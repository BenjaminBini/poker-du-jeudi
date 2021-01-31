<template>
  <stat-card
    label="Résultat"
    :loading="loading"
    :stat="currentSeasonResult + '€'"
    :color-class="currentSeasonResult >= 0 ? 'bg-green-500' : 'bg-red-500'"
    :neutral-badge="year > 0 ? year : lastYearPlayed"
  >
    <presentation-chart-line-icon
      class="w-6 h-6 text-white"
    ></presentation-chart-line-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { PresentationChartLineIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    PresentationChartLineIcon,
    StatCard,
  },
  props: {
    results: Array,
    year: Number,
    lastYearPlayed: Number,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    currentSeasonResult: function () {
      if (this.loading) return [];
      let currentYear = this.year == 0 ? this.lastYearPlayed : this.year;
      return this.results
        .filter((r) => r.session.season.year === currentYear)
        .map((r) => r.result)
        .reduce((acc, curr) => acc + curr, 0);
    },
  },
};
</script>

<style>
</style>