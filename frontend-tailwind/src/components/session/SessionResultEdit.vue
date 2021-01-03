<template>
  <div>
    <div v-if="loading" class="flex justify-end">
      <tw-spinner></tw-spinner>
    </div>
    <div v-if="!loading" class="flex justify-end space-x-1">
      <span
        v-if="!showEditInput"
        class="inline-flex items-center flex-shrink h-6 px-2 text-white rounded-md"
        :class="{
          'bg-red-500': result < 0,
          'bg-green-500': result >= 0,
        }"
      >
        {{ editedResult }} €
      </span>
      <input
        v-if="showEditInput"
        type="number"
        name="result"
        id="result"
        :value="editedResult"
        @blur="save"
        @keyup.enter="save"
        ref="resultInput"
        class="px-1 py-0 text-center text-white border-0 rounded-md w-14 focus:outline-none"
        :class="{
          'bg-red-500': editedResult < 0,
          'bg-green-500': editedResult >= 0,
        }"
      />
      <button
        v-if="showEditControls && showEditInput"
        @mousedown="clickedOnValidate = true"
        class="flex items-center justify-center w-6 h-6 text-green-500 border border-green-500 rounded-md hover:text-white focus:outline-none hover:bg-green-500"
      >
        <check-icon class="w-4 h-4"></check-icon>
      </button>
      <button
        v-if="showEditControls && !showEditInput"
        @click="toggleEdit"
        class="flex items-center justify-center w-6 h-6 text-indigo-500 border border-indigo-500 rounded-md hover:text-white focus:outline-none hover:bg-indigo-500"
      >
        <pencil-icon class="w-4 h-4"></pencil-icon>
      </button>
    </div>
  </div>
</template>

<script>
import { CheckIcon, PencilIcon } from "@vue-hero-icons/outline";
export default {
  components: { PencilIcon, CheckIcon },
  props: ["result", "playerId", "showEditControls", "loading"],
  data: function () {
    return {
      showEditInput: false,
      clickedOnValidate: false,
      editedResult: this.result,
    };
  },
  methods: {
    save: function () {
      if (this.$refs.resultInput.value != this.editedResult) {
        this.editedResult = this.$refs.resultInput.value;
        const eventPayload = {
          playerId: this.playerId,
          result: this.editedResult,
        };
        this.$emit("edit-result", eventPayload);
      }
      this.showEditInput = false;
    },
    toggleEdit: function () {
      if (this.clickedOnValidate) {
        this.clickedOnValidate = false;
        return;
      }
      this.showEditInput = true;
      var self = this;
      this.$nextTick(() => {
        self.$refs.resultInput.focus();
        self.$refs.resultInput.select();
      });
    },
  },
};
</script>
