import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import ClickService from "../service/ClickService";

const imageName = require('../images/congrats.png');

export class Click extends Component {

    constructor(props) {
        super(props);

        this.clickService = new ClickService();
    }

    componentDidMount = async () => {
        await this.clickService.sendClick(this.props.id);
    }

    render() {
        return (
            <img src={imageName} />
        );
    }
}

export default Click;