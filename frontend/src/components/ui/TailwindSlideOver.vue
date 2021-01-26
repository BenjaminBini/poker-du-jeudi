<template>
  <div :class="displayComponent ? 'fixed inset-0 overflow-hidden' : 'hidden'">
    <div
      :class="displayComponent ? 'absolute inset-0 overflow-hidden' : 'hidden'"
    >
      <!--
      Background overlay, show/hide based on slide-over state.

      Entering: "ease-in-out duration-500"
        From: "opacity-0"
        To: "opacity-100"
      Leaving: "ease-in-out duration-500"
        From: "opacity-100"
        To: "opacity-0"
    -->
      <transition
        v-on:before-enter="beforeEnter"
        v-on:after-leave="afterLeave"
        enter-active-class="duration-500 ease-in-out"
        enter-class="opacity-0"
        enter-to-class="opacity-100"
        leave-active-class="duration-500 ease-in-out"
        leave-class="opacity-100"
        leave-to-class="opacity-0"
      >
        <div
          v-show="isOpen || forceShow"
          class="absolute inset-0 transition-opacity bg-gray-500 bg-opacity-75"
          aria-hidden="true"
        ></div
      ></transition>

      <section
        class="absolute inset-y-0 right-0 flex max-w-full pl-10"
        aria-labelledby="slide-over-heading"
      >
        <!--
        Slide-over panel, show/hide based on slide-over state.

        Entering: "transform transition ease-in-out duration-500 sm:duration-700"
          From: "translate-x-full"
          To: "translate-x-0"
        Leaving: "transform transition ease-in-out duration-500 sm:duration-700"
          From: "translate-x-0"
          To: "translate-x-full"
      -->
        <transition
          enter-active-class="transition duration-500 ease-in-out transform sm:duration-700"
          enter-class="translate-x-full"
          enter-to-class="translate-x-0"
          leave-active-class="transition duration-500 ease-in-out transform sm:duration-700"
          leave-class="translate-x-0"
          leave-to-class="translate-x-full"
        >
          <div v-show="isOpen || forceShow" class="w-screen max-w-md">
            <div
              class="flex flex-col h-full pt-6 overflow-y-scroll bg-white shadow-xl"
            >
              <div class="px-4 sm:px-6">
                <div class="flex items-start justify-between">
                  <h2
                    id="slide-over-heading"
                    class="text-lg font-medium text-gray-900"
                  >
                    {{ title }}
                  </h2>
                  <div class="flex items-center ml-3 h-7">
                    <button
                      @click="close()"
                      class="text-gray-400 bg-white rounded-md hover:text-gray-500 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                    >
                      <span class="sr-only">Close panel</span>
                      <!-- Heroicon name: x -->
                      <svg
                        class="w-6 h-6"
                        xmlns="http://www.w3.org/2000/svg"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                        aria-hidden="true"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M6 18L18 6M6 6l12 12"
                        />
                      </svg>
                    </button>
                  </div>
                </div>
              </div>
              <div class="relative flex-1 mt-6">
                <!-- Replace with your content -->
                <div class="absolute inset-0">
                  <div class="h-full overflow-auto" aria-hidden="true">
                    <slot></slot>
                  </div>
                </div>
                <!-- /End replace -->
              </div>
            </div>
          </div>
        </transition>
      </section>
    </div>
  </div>
</template>

<script>
export default {
  name: "TailwindSlideOver",
  props: ["isOpen", "title", "forceShow"],
  data: function () {
    return {
      displayComponent: false || this.forceShow,
    };
  },
  methods: {
    close: function () {
      this.$emit("close");
    },
    beforeEnter: function () {
      this.displayComponent = true;
    },
    afterLeave: function () {
      this.displayComponent = false;
    },
  },
};
</script>
