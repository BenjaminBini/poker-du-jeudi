
<template>
  <div>
    <div v-if="loading" class="flex justify-center">
      <tw-spinner></tw-spinner>
    </div>
    <div
      v-if="!loading"
      class="flex items-center space-x-2"
      :class="editMode ? 'justify-center' : 'justify-end'"
    >
      <button
        v-if="editMode"
        @click="editBuyIns(-1)"
        class="flex items-center justify-center w-6 h-6 text-red-500 border border-red-500 rounded-md focus:outline-none hover:bg-red-500 hover:text-white"
      >
        <minus-sm-icon class="w-4 h-4"></minus-sm-icon>
      </button>
      <span class="inline-block w-8 text-center">{{ buyIns * 10 }} €</span>
      <button
        v-if="editMode"
        @click="editBuyIns(1)"
        class="flex items-center justify-center w-6 h-6 text-green-500 border border-green-500 rounded-md focus:outline-none hover:text-white hover:bg-green-500"
      >
        <plus-icon class="w-4 h-4"></plus-icon>
      </button>
    </div>
  </div>
</template>

<script>
import { PlusIcon, MinusSmIcon } from "@vue-hero-icons/outline";
export default {
  components: { PlusIcon, MinusSmIcon },
  props: ["editMode", "playerId", "buyIns", "loading"],
  methods: {
    editBuyIns: function (delta) {
      const eventPayload = {
        playerId: this.playerId,
        buyIns: this.buyIns + delta,
      };
      this.$emit("edit-buy-ins", eventPayload);
    },
  },
};
</script>
