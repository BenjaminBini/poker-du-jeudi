<template lang="pug">
  b-container(v-if="items.length > 0")
    b-row
      b-col.mb-4
        h1 {{items.length}} session{{items.length > 1 ? 's' : ''}}
    b-table(:items="items", :fields="fields", striped, stacked='md'
      sort-by="date", sort-desc=true)
      template(#cell(date)="session")
        router-link(:to="'/sessions/' + session.item.sessionId")
          | {{format(new Date(session.item.date), 'EEEE d MMMM yyyy', {locale: locale})}}
</template>

<script>
import SessionService from '../services/session-service';
import {format} from 'date-fns';
import { fr } from 'date-fns/locale'


export default {
  name: "Sessions",
  data: function() {
    return {
      format: format,
      locale: fr,
      fields: [{
        key: 'date',
        label: 'Date',
        sortable: true,
      }, {
        key: 'playersCount',
        label: 'Joueurs',
        formatter: (value, key, item) => {
          return item.playerResults.length;
        },
        sortByFormatted: true,
        sortable: true,
      }, {
        key: 'place',
        label: 'Lieu',
        formatter: (value, key, item) => {
          return value.name;
        },
        sortByFormatted: true,
        sortable: true,
      }, {
        key: 'winner',
        label: 'Vainqueur',
        formatter: (value, key, item) => {
          const winner = item.playerResults.reduce((acc, curr) => curr.result > acc.result ? curr : acc, {result: -1});
          return winner.player.firstName + ' ' + '(' + winner.result + '€)';
        },
      }, {
        key: 'loser',
        label: 'Perdant',
        formatter: (value, key, item) => {
          const loser = item.playerResults.reduce((acc, curr) => curr.result < acc.result ? curr : acc, {result: 2000});
          return loser.player.firstName + ' ' + '(' + loser.result + '€)';
        }
      }],
      items: []
    }
  },
  mounted() {
    SessionService.getSessions().then(response => {
      this.items = response.data;
    });
  }
}
</script>

<style scoped>

</style>