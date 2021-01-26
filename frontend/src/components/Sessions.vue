<template>
  <div>
    <ul
      class="grid grid-cols-1 gap-5 mt-2 sm:gap-6 sm:grid-cols-2 lg:grid-cols-3"
      v-if="loading"
    >
      <li
        v-for="i in [
          0,
          1,
          2,
          3,
          4,
          5,
          6,
          7,
          8,
          9,
          10,
          11,
          12,
          13,
          14,
          15,
          16,
          17,
          18,
          19,
          20,
          22,
          23,
          24,
        ]"
        :key="i"
      >
        <session-list-item :loading="true"></session-list-item>
      </li>
    </ul>
    <ul
      class="grid grid-cols-1 gap-5 mt-2 sm:gap-6 sm:grid-cols-2 lg:grid-cols-3"
      v-if="!loading"
    >
      <li v-for="session in sessions" :key="session.sessionId">
        <session-list-item :session="session"></session-list-item>
      </li>
    </ul>
  </div>
</template>

<script>
import SessionService from "../services/session-service";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import SessionListItem from "@/components/session/SessionListItem";
import settings from "@/settings";

export default {
  name: "poker-sessions",
  components: { SessionListItem },
  data: () => ({
    loading: false,
    sessions: [],
    cols: [
      {
        field: "date",
        label: "Session",
        formatter: function (value) {
          return format(new Date(value), "EEEE d MMMM yyyy", { locale: fr });
        },
        linkFunction: function (item) {
          return "/sessions/" + item.sessionId;
        },
      },
      {
        field: "playerResults.length",
        label: "Joueurs",
      },
      {
        field: "place.name",
        label: "Lieu",
      },
      {
        fieldCompute: function (item) {
          return (
            item.winner.player.firstName + " (" + item.winner.result + " €)"
          );
        },
        label: "Vainqueur",
      },
      {
        fieldCompute: function (item) {
          return item.loser.player.firstName + " (" + item.loser.result + " €)";
        },
        label: "Perdant",
      },
    ],
  }),
  async mounted() {
    this.loading = true;
    const response = await SessionService.getSessions();
    this.sessions = response.data;
    this.sessions = this.sessions.sort(
      (b, a) => new Date(a.date) - new Date(b.date)
    );
    this.sessions.forEach((s) => {
      s.winner = s.playerResults.reduce(
        (acc, curr) => (curr.result > acc.result ? curr : acc),
        { result: -1 }
      );
      s.loser = s.playerResults.reduce(
        (acc, curr) => (curr.result < acc.result ? curr : acc),
        { result: Number.MAX_VALUE }
      );
    });
    this.loading = false;
    this.$store.commit("setPageTitle", this.sessions.length + " sessions");
    if (this.$store.state.editMode || settings.forceEditMode) {
      const actions = [
        {
          target: "/sessions/add",
          label: "Nouvelle session",
        },
      ];
      this.$store.commit("setPageActions", actions);
    }
    //this.sessions.sort((s1, s2) => s2.compareTo(s2));
  },
};
</script>
