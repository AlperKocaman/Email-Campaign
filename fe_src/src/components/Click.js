import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Toast} from 'primereact/toast';
import {FileUpload} from 'primereact/fileupload';
import {Toolbar} from 'primereact/toolbar';
import {InputText} from 'primereact/inputtext';
import uuid from 'uuid-random';
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