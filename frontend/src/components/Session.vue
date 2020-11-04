<template lang="pug">
  b-container(v-if="session && ranking")
    b-row
      b-col
        .section-title.mb-4
          h2 Session du {{sessionDate}}
    b-row
      b-col.mb-3(col md="6")
        .card
          .card-header
            h4.text-center Résultats de la session
          .card-body
              session-result(:results="session.playerResults", type="result")
        .card.mt-3
          .card-header
            h4.text-center Nombre de buy-ins
          .card-body
            session-result(:results="session.playerResults", type="buyIns", style="max-height: 300px; position: relative;")
      b-col.mb-3(col md="6")
        .card
          .card-header
            h4.text-center Classement général (après la session)
          .card-body
            ranking-chart(:ranking="ranking")
        .card.mt-3
          .card-header
            h4.text-center Informations
          .card-body
            ul
              li Chez {{session.place.name}}
              li {{session.playerResults.length}} joueurs
              li {{- session.playerResults.reduce((acc, curr) => acc + curr.result, 0)}} € de monnaie
</template>

<script>
import SessionService from '../services/session-service';
import RankingService from '../services/ranking-service';
import {format} from 'date-fns';
import { fr } from 'date-fns/locale'

export default {
  name: "Session",
  data: function() {
    return {
      session: null,
      sessionDate: null,
      ranking: null,
    }
  },
  mounted() {
    let sessionId = this.$route.params.id;
    SessionService.getSession(sessionId).then(response => {
      this.session = response.data;
      this.sessionDate = format(new Date(this.session.date), 'EEEE d MMMM yyyy', {locale: fr});
    });
    RankingService.getUntil(sessionId).then(response => {
      this.ranking = response.data;
    });
  }
}
</script>

<style scoped>
  .section-title {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .section-title h2 {
    margin-bottom: 0;
    padding: 0.5rem 1.5rem;
    background: #258ce8;
    color: white;
    font-style: italic;
    text-align: center;
  }

  .card-header {
    background: #ec4444;
    color: white;
    border-radius: 0 !important;
    border-bottom: 0;
  }

  .card-header h4 {
    margin-bottom: 0;
  }

  .card {
    border-radius: 0;
    border: 0;
  }

  .card .card-header.small {
    padding: .4rem .8rem;
  }

  .card-body {
    border: 1px solid rgba(0, 0, 0, .125);
  }

  .card-header + .card-body {
    border-top: 0;
  }

</style>