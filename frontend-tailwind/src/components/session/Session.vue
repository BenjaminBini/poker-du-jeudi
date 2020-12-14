<template lang="pug">
</template>

<script>
import SessionService from "../../services/session-service";
import {format} from "date-fns";
import {fr} from "date-fns/locale";

export default {
  data: () => ({
    session: Object,
    formattedDate: String,
  }),
  methods: {
    async fetchData() {
      let sessionId = this.$route.params.id;
      const response = await SessionService.getSession(sessionId);
      this.session = response.data;
      this.formattedDate = format(new Date(this.session.date), 'EEEE d MMMM yyyy', {locale: fr});
      this.$store.commit("setPageTitle", "Session du " + this.formattedDate);
    }
  },
  mounted() {
    this.fetchData();
  }
}
</script>