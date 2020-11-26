<template lang="pug">
  div(v-if="results.length > 1" class="bg-white overflow-hidden shadow rounded-lg divide-y divide-gray-200")
    div(class="bg-white px-4 py-5 sm:px-6")
      div(class="-ml-4 -mt-2 flex items-center justify-between flex-wrap sm:flex-nowrap")
        div(class="ml-4 mt-2")
          h3(class="text-lg leading-6 font-medium text-gray-900")
            | Résultats
        div(v-if="yearsPlayed.length > 1" class="ml-4 mt-2 flex-shrink-0 space-x-3")
          YearFilter(:year="0" label="Tout" :selected-year="selectedYear" @year-selected="yearSelected")
          YearFilter(v-for="year in yearsPlayed" :year="year" :label="year" :selected-year="selectedYear" @year-selected="yearSelected")
      // Content goes here
      // We use less vertical padding on card headers on desktop than on body sections
    div(class="px-4 py-5 sm:p-6")
      ResultChart(:results="filteredResults", class="relative", style="height: 400px")
</template>

<script>
import ResultChart from "./ResultChart";
import YearFilter from "./YearFilter";

export default {
  name: "ResultChartContainer",
  components: {
    YearFilter,
    ResultChart,
  },
  props: ["results", "yearsPlayed", "selectedYear"],
  data: () => ({
  }),
  computed: {
    filteredResults: function() {
      let results = [...this.results];
      return results
          .sort((r1, r2) => new Date(r2.session.date) - new Date(r1.session.date))
          .filter(r => this.selectedYear === 0 || r.session.season.year === this.selectedYear);
    }
  },
  methods: {
    yearSelected(event, year) {
      this.$emit('year-selected', event, year)
    }
  },
  mounted() {
  }
}
</script>

<style scoped>

</style>