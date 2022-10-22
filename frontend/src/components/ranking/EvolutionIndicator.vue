<template>
  <div
    v-if="evolution != 0"
    class="inline-flex items-center rounded-md ml-1"
    :class="
      isActive
        ? 'text-white'
        : evolution > 0
        ? 'text-green-500 '
        : 'text-red-500'
    "
  >
    <ChevronUpIcon class="w-5 h-5" v-if="evolution > 0"></ChevronUpIcon>

    <ChevronDownIcon class="w-5 h-5" v-if="evolution < 0"></ChevronDownIcon>
    <ChevronRightIcon class="w-5 h-5" v-if="evolution == 0"></ChevronRightIcon>
    <div v-if="evolution > 0" class="mr-1">
      {{ evolution }}
    </div>
    <div v-if="evolution < 0" class="mr-1">
      {{ -evolution }}
    </div>
  </div>
</template>
<script>
import { ChevronUpIcon, ChevronDownIcon } from "@vue-hero-icons/solid";

export default {
  name: "RankingComponent",
  props: ["evolution", "isActive"],
  components: {
    ChevronUpIcon,
    ChevronDownIcon,
  },
};
</script>
