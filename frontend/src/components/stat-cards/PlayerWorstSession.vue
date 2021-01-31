<template>
  <stat-card
    :to="loading ? '' : `/sessions/${worstResult.session.sessionId}`"
    label="Pire session"
    :loading="loading"
    :stat="worstResult.result + '€'"
    color-class="bg-red-500"
    :neutral-badge="worstResult.sessionDate"
  >
    <shield-exclamation-icon
      class="w-6 h-6 text-white"
    ></shield-exclamation-icon>
  </stat-card>
</template>

<script>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import StatCard from "@/components/player/player-detail/StatCard";
import { ShieldExclamationIcon } from "@vue-hero-icons/outline";
export default {
  components: {
    ShieldExclamationIcon,
    StatCard,
  },
  props: {
    results: Array,
  },
  computed: {
    loading: function () {
      return !this.results || this.results.length == 0;
    },
    worstResult: function () {
      if (this.loading) return 0;
      let worstResult = this.results.reduce((acc, cur) =>
        cur.result < acc.result ? cur : acc
      );
      worstResult.sessionDate = format(
        new Date(worstResult.session.date),
        "dd/MM/yyyy",
        {
          locale: fr,
        }
      );
      return worstResult;
    },
  },
};
</script>

<style>
</style>