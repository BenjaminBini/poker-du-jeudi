<script>
import { Line } from 'vue-chartjs';

const color = function(context) {
  const index = context.dataIndex;
  const value = context.dataset.data[index];
  const previousValue = index > 0 ? context.dataset.data[index - 1] : 0;
  if (value >= previousValue) {
    return '#10B981';
  } else {
    return '#EF4444';
  }
}

let options = {
  responsive: true,
  maintainAspectRatio: false,
  legend: {
    display: false,
  },
  scales: {
    yAxes: [{
      ticks: {
        beginAtZero: true,
      }
    }],
    xAxes: [{
      type: "time",
      distribution: 'series',
      time: {
        unit: 'year',
      },
      ticks: {
          source: null,
      }
    }],
  }
};

const updateChart = function(results, renderFunction) {
  const resultsData = results
      .sort((r1, r2) => new Date(r1.session.date) - new Date(r2.session.date))
      .map(pr => pr.result);
  let sum;
  const cumulatedResult = resultsData.map(r => sum = (sum || 0) + r);
  let resultLabels = results.map(pr => new Date(pr.session.date));
  if (resultLabels.length < 50) {
    options.scales.xAxes[0].time.unit = 'month';
    options.scales.xAxes[0].ticks.minRotation = 40;
  } else {
    options.scales.xAxes[0].time.unit = 'year';
    options.scales.xAxes[0].ticks.minRotation = 0;
  }
  let data = {
    datasets: [{
      data: cumulatedResult,
      backgroundColor: 'rgba(0, 0, 0, 0)',
      borderColor: 'rgb(79, 70, 229)',
      pointStyle: 'circle',
      radius: 3,
      pointBorderColor: color,
      pointBackgroundColor: color,
    }],
    labels: resultLabels,
  };
  renderFunction(data, options);
}

export default {
  name: "ResultChart",
  extends: Line,
  props: ['results'],
  watch: {
    results: function() {
      updateChart(this.results, this.renderChart);
    }
  },
  mounted() {
    updateChart(this.results, this.renderChart);
  }
}
</script>

<style scoped>
</style>