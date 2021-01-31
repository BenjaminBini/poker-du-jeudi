<template>
  <stat-card
    label="Participations"
    :loading="loading"
    :stat="results.length"
    color-class="bg-green-500"
    :neutralBadge="year > 0 ? year : ''"
  >
    <calendar-icon class="w-6 h-6 text-white"></calendar-icon>
  </stat-card>
</template>

<script>
import StatCard from "@/components/player/player-detail/StatCard";
import { CalendarIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    CalendarIcon,
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
  },
};
</script>

<style>
</style>