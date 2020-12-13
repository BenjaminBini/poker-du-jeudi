<template lang="pug">
  li(class="col-span-1 flex shadow-sm rounded-md")
    div(class="flex-shrink-0 flex items-center justify-center w-16 text-white text-sm font- rounded-l-md"
      :class="result.result < 0 ? 'bg-red-500' : 'bg-green-500'") {{result.result}} €
    div(class="flex-1 flex items-center justify-between border-t border-r border-b border-gray-200 bg-white rounded-r-md truncate")
      div(class="flex-1 px-4 py-2 text-sm truncate")
        a(href="#" class="text-gray-900 font-medium hover:text-gray-600") {{formatDate(result.session.date)}}
        div(class="text-gray-500 flex items-center")
          StarIcon(v-if="result.rank === 1" class="mr-1 h-6 w-6 text-yellow-400")
          ArrowCircleDownIcon(v-if="result.last" class="mr-1 h-5 w-5 text-red-500")
          | {{result.last ? result.playersCount : result.rank}}e sur {{result.playersCount}} joueurs

</template>

<script>
import {format} from "date-fns";
import {fr} from "date-fns/locale";
import {StarIcon} from "@vue-hero-icons/solid";
import {ArrowCircleDownIcon} from "@vue-hero-icons/outline";

export default {
  name: "PlayerSessionCard",
  components: {
    ArrowCircleDownIcon,
    StarIcon
  },
  props: ["result"],
  methods: {
    formatDate: function(date) {
      let formattedDate = format(new Date(date), 'EEEE d MMMM yyyy', {locale: fr});
      return formattedDate.charAt(0).toUpperCase() + formattedDate.slice(1);
    }
  }
}
</script>

<style scoped>

</style>