import Vue from 'vue'
import App from './App.vue'
import router from './router'
import BootstrapVue from 'bootstrap-vue'

import './assets/bootstrap-override.scss'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import SessionResult from "@/components/charts/SessionResult";
import RankingChart from "@/components/charts/RankingChart";

Vue.config.productionTip = false;

// Bootstrap
Vue.use(BootstrapVue);

// Register components not bound to views
Vue.component('session-result', SessionResult);
Vue.component('ranking-chart', RankingChart);


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');

