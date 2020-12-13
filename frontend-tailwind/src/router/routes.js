import Sessions from "../components/Sessions";
import Players from "../components/player/players-list/Players";
import Player from "../components/player/player-detail/Player";
import Home from "@/components/Home";

const routes = [
    {
        path: "/",
        component: Home,
        meta: {
            label: "Accueil",
            pageTitle: "Poker du jeudi",
            displayInMenu: true
        }
    }, {
        path: "/sessions",
        component: Sessions,
        meta: {
            label: "Sessions",
            displayInMenu: true
        },
    }, {
        path: "/players",
        component: Players,
        meta: {
            label: "Joueurs",
            displayInMenu: true
        },
    }, {
        path: "/players/:id",
        component: Player,
        meta:  {
            displayInMenu: false,
        }
    },
];

export default routes;
