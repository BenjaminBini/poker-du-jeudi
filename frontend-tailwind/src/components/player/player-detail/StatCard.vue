<template lang="pug">
  div(class="bg-white overflow-hidden shadow rounded-lg")
    div(class="px-4 py-5 sm:p-6")
      div(class="flex items-center")
        div(class="flex-shrink-0 bg-indigo-500 rounded-md p-3")
          slot
        div(class="ml-5 w-0 flex-1")
          dt(class="text-sm font-medium text-gray-500 truncate") {{label}}
          dd(class="flex items-center space-x-3")
            div(class="text-2xl font-semibold text-gray-900") {{stat}}
            result-badge(v-if="change" :player-result="change")
</template>

<script>
export default {
  name: "StatCard",
  props: ["label", "stat", "change"]
}
</script>

<style scoped>

</style>