<template lang="pug">
  ul(class="mt-2 grid grid-cols-1 gap-5 sm:gap-6 sm:grid-cols-2 lg:grid-cols-3")
    li(v-for="(session, index) in sessions")
      session-list-item(:session="session")
</template>

<script>
import SessionService from "../services/session-service";
import {format} from 'date-fns';
import { fr } from 'date-fns/locale'
import SessionListItem from "@/components/session/SessionListItem";

export default {
  name: "poker-sessions",
  components: {SessionListItem},
  data: () => ({
    sessions: [],
    cols: [{
      field: 'date',
      label: 'Session',
      formatter: function(value) {
        return format(new Date(value), 'EEEE d MMMM yyyy', {locale: fr});
      },
      linkFunction: function(item) {
        return "/sessions/" + item.sessionId;
      }
    }, {
      field: 'playerResults.length',
      label: 'Joueurs',
    }, {
      field: 'place.name',
      label: 'Lieu',
    }, {
      fieldCompute: function(item) {
        return item.winner.player.firstName + " (" + item.winner.result + " €)";
      },
      label: 'Vainqueur',
    }, {
      fieldCompute: function(item) {
        return item.loser.player.firstName + " (" + item.loser.result + " €)";
      },
      label: 'Perdant',
    }]
  }),
  async mounted() {
    const response = await SessionService.getSessions()
    this.sessions = response.data;
    this.sessions = this.sessions.sort((b, a) => new Date(a.date) - new Date(b.date));
    this.sessions.forEach(s => {
      s.winner = s.playerResults.reduce((acc, curr) => curr.result > acc.result ? curr : acc, {result: -1});
      s.loser = s.playerResults.reduce((acc, curr) => curr.result < acc.result ? curr : acc, {result: Number.MAX_VALUE});
    });
    this.$store.commit('setPageTitle', this.sessions.length + ' sessions');
    //this.sessions.sort((s1, s2) => s2.compareTo(s2));

  },
}
</script>

<style scoped>

</style>