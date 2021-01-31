<script>
import { Line } from "vue-chartjs";
import { format } from "date-fns";
import { fr } from "date-fns/locale";
const colors = require("tailwindcss/colors");

const color = function (context) {
  const index = context.dataIndex;
  const value = context.dataset.data[index];
  const previousValue = index > 0 ? context.dataset.data[index - 1] : 0;
  if (value >= previousValue) {
    return "#10B981";
  } else {
    return "#EF4444";
  }
};

let options = {
  responsive: true,
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  tooltips: false,
  scales: {
    yAxes: [
      {
        ticks: {
          beginAtZero: true,
        },
      },
    ],
    xAxes: [{}],
  },
};

const updateChart = function (results, renderFunction) {
  const resultsData = results
    .sort((r1, r2) => new Date(r1.session.date) - new Date(r2.session.date))
    .map((pr) => pr.result);
  let sum;
  const cumulatedResult = resultsData.map((r) => (sum = (sum || 0) + r));
  const lowestCumulatedResult = Math.min(...cumulatedResult);
  const highestCumulatedResult = Math.max(...cumulatedResult);
  let resultLabels;
  if (cumulatedResult.length < 50) {
    resultLabels = results.map((pr) =>
      format(new Date(pr.session.date), "dd/MM/yyy", { locale: fr })
    );
    options.scales.xAxes[0] = {};
  } else {
    resultLabels = results.map((pr) => new Date(pr.session.date));
    options.scales.xAxes[0] = {
      type: "time",
      distribution: "series",
      time: {
        unit: "year",
      },
      ticks: {
        source: null,
        minRotation: 0,
      },
    };
  }
  let data = {
    datasets: [
      {
        data: cumulatedResult,
        backgroundColor: "rgba(0, 0, 0, 0)",
        borderColor: "rgb(79, 70, 229)",
        pointStyle: "circle",
        radius: 3,
        pointHitRadius: 8,
        pointBorderColor: color,
        pointBackgroundColor: color,
        datalabels: {
          backgroundColor: colors.white,
          borderColor: color,
          borderWidth: 1,
          borderRadius: 4,
          display: (context) => {
            if (context.active) return "true";
            const value = context.dataset.data[context.dataIndex];
            return (
              value == lowestCumulatedResult || value == highestCumulatedResult
            );
          },
          font: {
            lineHeight: 0.8,
          },
          formatter: function (value) {
            return `${value} €`;
          },
          align: (context) => {
            if (context.dataIndex == 0) return "right";
            if (context.dataIndex == context.dataset.data.length - 1)
              return "left";
            const value = context.dataset.data[context.dataIndex];
            return value >= highestCumulatedResult - 10 ? "start" : "end";
          },
          clip: false,
          clamp: true,
          offset: 8,
          padding: {
            bottom: 2,
          },
        },
      },
    ],
    labels: resultLabels,
  };
  renderFunction(data, options);
};

export default {
  name: "ResultChart",
  extends: Line,
  props: ["results"],
  watch: {
    results: function () {
      updateChart(this.results, this.renderChart);
    },
  },
  mounted() {
    updateChart(this.results, this.renderChart);
  },
};
</script>

<style scoped>
</style>