<template>
  <li class="flex col-span-1 rounded-md shadow-sm">
    <div
      class="flex items-center justify-center flex-shrink-0 w-16 text-sm text-white font- rounded-l-md"
      :class="result.result &lt; 0 ? 'bg-red-500' : 'bg-green-500'"
    >
      {{ result.result }} €
    </div>
    <div
      class="flex items-center justify-between flex-1 truncate bg-white border-t border-b border-r border-gray-200 rounded-r-md"
    >
      <div class="flex-1 px-4 py-2 text-sm truncate">
        <router-link
          class="font-medium text-gray-900 hover:text-gray-600"
          :to="'/sessions/' + result.session.sessionId"
          >{{ formatDate(result.session.date) }}</router-link
        >
        <div class="flex items-center text-gray-500">
          <StarIcon
            class="w-6 h-6 mr-1 text-yellow-400"
            v-if="result.rank === 1"
          ></StarIcon>
          <ArrowCircleDownIcon
            class="w-5 h-5 mr-1 text-red-500"
            v-if="result.last"
          ></ArrowCircleDownIcon
          >{{ result.last ? result.playersCount : result.rank }}e sur
          {{ result.playersCount }} joueurs
        </div>
      </div>
    </div>
  </li>
</template>

<script>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import { StarIcon } from "@vue-hero-icons/solid";
import { ArrowCircleDownIcon } from "@vue-hero-icons/outline";

export default {
  name: "PlayerSessionCard",
  components: {
    ArrowCircleDownIcon,
    StarIcon,
  },
  props: ["result"],
  methods: {
    formatDate: function (date) {
      let formattedDate = format(new Date(date), "EEEE d MMMM yyyy", {
        locale: fr,
      });
      return formattedDate.charAt(0).toUpperCase() + formattedDate.slice(1);
    },
  },
};
</script>
