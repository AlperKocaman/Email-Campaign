import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import ContactService from '../service/ContactService';
import {Toast} from 'primereact/toast';
import {FileUpload} from 'primereact/fileupload';
import {Toolbar} from 'primereact/toolbar';
import {InputText} from 'primereact/inputtext';
import GroupService from "../service/GroupService";


export class Group extends Component {

    emptyGroup = {
        id: null,
        contactsIdList: []
    };

    constructor(props) {
        super(props);
        this.state = {
            groups: [],
            globalFilter: null
        }

        this.groupService = new GroupService();
    }

    componentDidMount = async () => {
        this.groupService.getGroups().then(res => {
            this.setState({groups: res.data});
        });
    }

    render() {
        const header = (
            <div className="table-header">
                <span className="p-input-icon-left">
                    <i className="pi pi-search" />
                    <InputText type="search" onInput={(e) => this.setState({ globalFilter: e.target.value })} placeholder="Search..." />
                </span>
            </div>
        );

        return (
            <div>
                {this.state.groups.map(list => (
                    <div key={list.id}>
            <pre>{
                JSON.stringify(list, null, 2)
            }</pre>
                    </div>
                ))}
            </div>
        );
    }
}

export default Group;