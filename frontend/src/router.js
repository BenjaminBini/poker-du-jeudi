import Vue from 'vue'
import Router from 'vue-router'

import Home from "@/components/Home";
import Players from "@/components/Players";
import Player from "@/components/Player";
import Sessions from "@/components/Sessions";
import Session from "@/components/Session";
import Stats from "@/components/Stats";

Vue.use(Router);

const router = new Router({
    mode: 'history', // uris without hashes #, see https://router.vuejs.org/guide/essentials/history-mode.html#html5-history-mode
    routes: [
        { path: '/', component: Home },
        { path: '/players', component: Players },
        { path: '/players/:id', component: Player },
        { path: '/sessions', component: Sessions },
        { path: '/sessions/:id', component: Session },
        { path: '/stats', component: Stats },
        { path: '*', redirect: '/' }
    ]
});


export default router;
