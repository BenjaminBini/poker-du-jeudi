<template lang="pug">
  b-container
    b-row
      b-col
        .section-title.mb-4
          h2 Session du {{sessionDate}}
    b-row
      b-col.mb-3(col md="6")
        .card
          .card-header
            h4.text-center Résultats de la session
          .card-body Test
        .card.mt-3
          .card-header
            h4.text-center Nombre de buy-ins
          .card-body Test
      b-col.mb-3(col md="6")
        .card
          .card-header
            h4.text-center Classement général (après la session)
          .card-body Test
        .card.mt-3
          .card-header
            h4.text-center Informations
          .card-body Test
</template>

<script>
import SessionService from '../services/session-service';
import {format} from 'date-fns';
import { fr } from 'date-fns/locale'

export default {
  name: "Session",
  data: function() {
    return {
      session: null,
      sessionDate: null,
    }
  },
  mounted() {
    SessionService.getSession(this.$route.params.id).then(response => {
      this.session = response.data;
      this.sessionDate = format(new Date(this.session.date), 'EEEE d MMMM yyyy', {locale: fr});
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