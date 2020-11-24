<script>
import { Line } from 'vue-chartjs';
import {format} from 'date-fns';
import { fr } from 'date-fns/locale'

export default {
  name: "ResultChart",
  extends: Line,
  props: {
    results: Array,
  },
  mounted() {
    const resultsData = this.results
        .sort((r1, r2) => new Date(r1.session.date) - new Date(r2.session.date))
        .map(pr => pr.result);
    let sum;
    const cumulatedResult = resultsData.map(r => sum = (sum || 0) + r);
    console.log(resultsData);
    console.log(cumulatedResult);
    let resultLabels = this.results.map(pr => format(new Date(pr.session.date), 'dd/MM/yyyy', {locale: fr}));
    let data = {
      datasets: [{
        data: cumulatedResult,
      }],
      labels: resultLabels,
    };
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
        }]
      }
    };
    this.renderChart(data, options);
  }
}
</script>

<style scoped>
</style>