<template lang="pug">
  router-link.block.transition.duration-150.ease-in-out(:to="'/sessions/' + session.sessionId" class="hover:bg-gray-50 focus:outline-none focus:bg-gray-50")
    div(class="px-4 py-4 sm:px-6")
      div(class="min-w-0 flex-1 flex items-start lg:items-center")
        div(class="flex flex-col items-start")
          div(class="text-indigo-600 capitalize") {{formattedDate}}
          div(class="mt-2 text-sm text-gray-500")
            div(class="flex items-center")
              HomeIcon(class="h-5 w-5 mr-1.5 text-gray-500")
              span(class="") {{session.place.name}}
</template>

<script>
import {format} from "date-fns";
import {fr} from "date-fns/locale";
import { HomeIcon } from "@vue-hero-icons/outline"

export default {
  components: {HomeIcon},
  props: ["session"],
  data: () => ({
    formattedDate: String,
  }),
  mounted() {
    this.formattedDate = format(new Date(this.session.date), 'EEEE d MMMM yyyy', {locale: fr});
  }
}
</script>

<style scoped>

</style>