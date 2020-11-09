<template lang="pug">
  .flex.flex-col
    .-my-2.overflow-x-auto(class="sm:-mx-6 lg:-mx-8")
      .py-2.align-middle.inline-block.min-w-full(class="sm:px-6 lg:px-8")
        .shadow.overflow-hidden.border-b.border-gray-200(class="sm:rounded-lg")
          table.min-w-full.divide-y.divide-gray-200
            thead
              tr
                th.px-6.py-3.bg-gray-50.text-left.text-xs.leading-4.font-medium.text-gray-500.uppercase.tracking-wider(
                  v-for="col in cols"
                ) {{col.label}}
            tbody
              // Odd row
              tr.bg-white(
                v-for="(item, index) in items"
                :class="index % 2 ? 'bg-gray-50' : 'bg-white'"
              )
                td.px-6.py-4.whitespace-no-wrap.text-sm.leading-5.text-gray-500(
                  v-for="col in cols"
                ) {{computeField(col, item)}}
</template>

<script>
export default {
  name: "tailwind-table",
  props: {
    items: Array,
    cols: Array,
  },
  mounted() {

  },
  methods: {
    computeField: function(col, item) {
      if (col.formatter) {
        return col.formatter(item[col.field]);
      }
      if (col.fieldCompute) {
        return col.fieldCompute(item);
      }
      if (col.field.includes(".")) {
        const fieldParts = col.field.split(".");
        let result = item;
        for (let fieldPart of fieldParts) {
          if (result && result[fieldPart]) {
            result = result[fieldPart];
          } else {
            return "";
          }
        }
        return result;
      }
      return item[col.field];
    }
  }
}
</script>