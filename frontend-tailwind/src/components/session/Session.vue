<template>
  <div v-if="loaded">
    <dl class="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
      <StatCard label="Vainqueur" :stat="results[0].player.firstName" :change="results[0]" color-class="bg-green-500">
        <StarIcon class="w-6 h-6 text-white"></StarIcon>
      </StatCard>
      <StatCard label="Perdant" :stat="results[results.length - 1].player.firstName" :change="results[results.length - 1]" color-class="bg-red-500">
        <ChevronDoubleDownIcon class="w-6 h-6 text-white"></ChevronDoubleDownIcon>
      </StatCard>
      <StatCard label="Lieu" :stat="session.place.name" color-class="bg-green-500">
        <HomeIcon class="w-6 h-6 text-white"></HomeIcon>
      </StatCard>
      <StatCard v-if="results[0].buyIns > 0" label="Argent misé" :stat="results.map(r => r.buyIns).reduce((a, b) => a + b) * 10 + ' €'" color-class="bg-green-500">
        <CashIcon class="w-6 h-6 text-white"></CashIcon>
      </StatCard>
    </dl>
    <div class="overflow-hidden bg-white divide-y divide-gray-200 rounded-lg shadow mt-14">
      <h3 class="px-4 py-5 text-xl font-bold leading-6 text-indigo-500">Résultat de la session</h3>
      <div class="px-4 py-5 sm:p-6">
        Test
      </div>
    </div>
    <dl class="grid grid-cols-1 gap-5 mt-14 sm:grid-cols-2 lg:grid-cols-4">
      <div>
        <RankingComponent :rankings="session.rankings.filter(r => !r.rankingKey.general)" :active-player-ids="session.playerResults.map(pr => pr.player.playerId)" :season-ranking="true" :title="`Classement ${session.season.name}`"></RankingComponent>
      </div>
      <div>
        <RankingComponent :rankings="session.rankings.filter(r => r.rankingKey.general)" :active-player-ids="session.playerResults.map(pr => pr.player.playerId)" :season-ranking="false" title="Classement général"></RankingComponent>
      </div>
    </dl>
  </div>
</template>

<script>
import SessionService from "../../services/session-service";
import {format} from "date-fns";
import {fr} from "date-fns/locale";
import StatCard from "@/components/player/player-detail/StatCard";
import { CashIcon, ChevronDoubleDownIcon, HomeIcon, StarIcon } from "@vue-hero-icons/outline"
import RankingComponent from "@/components/ranking/RankingComponent";

export default {
  name: "Session",
  components: { CashIcon, ChevronDoubleDownIcon, HomeIcon, RankingComponent, StarIcon, StatCard },
  data: () => ({
    session: Object,
    formattedDate: String,
    loaded: false,
  }),
  computed: {
    results: function() {
      if (!this.session) return [];
      return [...this.session.playerResults].sort((r1, r2) => r2.result - r1.result);
    }
  },
  methods: {
    async fetchData() {
      let sessionId = this.$route.params.id;
      const response = await SessionService.getSession(sessionId);
      this.session = response.data;
      this.formattedDate = format(new Date(this.session.date), 'EEEE d MMMM yyyy', {locale: fr});
      this.$store.commit("setPageTitle", "Session du " + this.formattedDate);
      this.loaded = true;
    }
  },
  mounted() {
    this.fetchData();
  }
}
</script>