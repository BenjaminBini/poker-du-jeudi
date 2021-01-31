<template>
  <stat-card
    :to="loading ? '' : `/sessions/${bestResult.session.sessionId}`"
    label="Meilleure session"
    :loading="loading"
    :stat="bestResult.result + '€'"
    color-class="bg-green-500"
    :neutral-badge="bestResult.sessionDate"
  >
    <lightning-bolt-icon class="w-6 h-6 text-white"></lightning-bolt-icon>
  </stat-card>
</template>

<script>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import StatCard from "@/components/player/player-detail/StatCard";
import { LightningBoltIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    LightningBoltIcon,
    StatCard,
  },
  props: {
    results: Array,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    bestResult: function () {
      if (this.loading) return 0;
      let bestResult = this.results.reduce((acc, cur) =>
        cur.result > acc.result ? cur : acc
      );
      bestResult.sessionDate = format(
        new Date(bestResult.session.date),
        "dd/MM/yyyy",
        {
          locale: fr,
        }
      );
      return bestResult;
    },
  },
};
</script>

<style>
</style>