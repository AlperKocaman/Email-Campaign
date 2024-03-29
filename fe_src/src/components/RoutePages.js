import React from "react";
import {Router} from "@reach/router";
import Dashboard from "./Dashboard";
import Contact from "./Contact"
import Group from "./Group"
import Campaign from "./Campaign"
import Mail from "./Mail"
import Click from "./Click";

function RoutePages() {
    const NotFound = () => window.location.assign('/');
    return (
        <Router>
            <Dashboard path="/"/>
            <Contact path="/contacts"/>
            <Group path="/groups"/>
            <Campaign path="/campaigns"/>
            <Mail path="/sendmail"/>
            <Click path="/click/:id"/>
            <NotFound default />
        </Router>

    );
}

export default RoutePages;
