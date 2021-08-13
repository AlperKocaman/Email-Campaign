import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';

import React, {Component} from 'react';
import {DataTable} from 'primereact/datatable';
import {Toast} from 'primereact/toast';
import {InputText} from 'primereact/inputtext';
import CampaignService from "../service/CampaignService";
import {Column} from "primereact/column";


export class Campaign extends Component {

    emptyCampaign = {
        id: null,
        campaignExplanation: ''
    };

    constructor(props) {
        super(props);
        this.state = {
            campaigns: [],
            globalFilter: null
        }

        this.campaignService = new CampaignService();
    }

    componentDidMount = async () => {
        this.campaignService.getCampaigns().then(res => {
            this.setState({campaigns: res.data});
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
            <div className="datatable-crud-demo">
                <Toast ref={(el) => this.toast = el} />

                <div className="card">

                    <DataTable ref={(el) => this.dt = el} value={this.state.campaigns}
                               dataKey="id" paginator rows={10} rowsPerPageOptions={[5, 10, 25]}
                               paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                               currentPageReportTemplate="Showing {first} to {last} of {totalRecords} contacts"
                               globalFilter={this.state.globalFilter}
                               header={header}>

                        <Column field="id" header="Campaign ID" sortable></Column>
                        <Column field="campaignExplanation" header="Campaign Explanation" sortable></Column>

                    </DataTable>
                </div>
            </div>
        );
    }
}

export default Campaign;